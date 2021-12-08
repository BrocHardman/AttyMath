package com.example.attymath;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;


public class MainActivity extends AppCompatActivity {
    // This has to be class level for the dialog to get user name
    //###################################
    String userName = "";
    private SharedPreferences mPrefs;
    //###################################

    private static final int STORAGE_PERMISSION_CODE = 101;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configureAdditionButton();
        configureSubtractionButton();
        configureMultiplicationButton();
        configureDivisionButton();
        //checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
        //#######################################
        mPrefs = getSharedPreferences("saveUserName", 0);
        if (userName == null || userName.equals("")) {
            userName = mPrefs.getString("USER_NAME", "");
        }
        //#######################################

        ImageView logo = (ImageView) findViewById(R.id.AttymathLogo);
        File tempFile = new File(getExternalFilesDir(null), "AttyMath.png");
        if (tempFile.exists()) {
            logo.setImageBitmap(BitmapFactory.decodeFile(tempFile.getAbsolutePath()));
        } else {
            showMessage();
        }


    }

    // Function to check and request permission.
    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{permission},
                    requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this,
                        "Storage Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(MainActivity.this,
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void showMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);

        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setTitle("Hey there!")
                .setMessage("Have we met before? Maybe my memory is just really bad. Either way " +
                        "it would be nice to know your name. Please " +
                        "type your name and then click OK")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String userNameInput = input.getText().toString();
                        boolean badinput = true;
                        for (int i = 0; i < userNameInput.length() && badinput; i++) {
                            if (userNameInput.charAt(i) != ' ')
                                badinput = false;
                        }

                        if (!badinput) {
                            userName = userNameInput;
                            getSupportActionBar().setTitle(userName + "Math");
                            String urlthing = getString(R.string.LogoURL);
                            File tempFile = new File(getExternalFilesDir(null), "AttyMath.png");
                            urlthing = urlthing.replace("ChangeMe", userName);
                            new RetrieveTitleImage().execute(urlthing, tempFile.getAbsolutePath());
                            dialog.cancel();
                        }
                    }
                })
                .setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void configureAdditionButton() {
        ImageButton additionButton = (ImageButton) findViewById(R.id.additionActivity);
        additionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameDriver.class);
                Bundle b = new Bundle(); // create a bundle to send to created activity class
                b.putChar("mathoperator", '+'); // in that bundle make a key and a value
                b.putString("USER_NAME", userName);
                intent.putExtras(b); // place bundle inside intent
                startActivity(intent);
            }
        });
    }

    private void configureSubtractionButton() {
        ImageButton subtractionButton = (ImageButton) findViewById(R.id.subtractionActivity);
        subtractionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameDriver.class);
                Bundle b = new Bundle(); // create a bundle to send to created activity class
                b.putChar("mathoperator", '-'); // in that bundle make a key and a value
                b.putString("USER_NAME", userName);
                intent.putExtras(b); // place bundle inside intent
                startActivity(intent);
            }
        });
    }

    private void configureMultiplicationButton() {
        ImageButton multiplicationButton = (ImageButton) findViewById(R.id.multiplicationActivity);
        multiplicationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameDriver.class);
                Bundle b = new Bundle(); // create a bundle to send to created activity class
                b.putChar("mathoperator", 'x'); // in that bundle make a key and a value
                b.putString("USER_NAME", userName);
                intent.putExtras(b); // place bundle inside intent
                startActivity(intent);
            }
        });
    }

    private void configureDivisionButton() {
        ImageButton divisionButton = (ImageButton) findViewById(R.id.divisionActivity);
        divisionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameDriver.class);
                Bundle b = new Bundle(); // create a bundle to send to created activity class
                b.putChar("mathoperator", '/'); // in that bundle make a key and a value
                b.putString("USER_NAME", userName);
                intent.putExtras(b); // place bundle inside intent
                startActivity(intent);
            }
        });
    }

    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor ed = mPrefs.edit();
        ed.putString("USER_NAME", userName);
        ed.commit();

    }

    protected void onResume() {
        super.onResume();
        ImageView logo;
        logo = (ImageView) findViewById(R.id.AttymathLogo);
        File tempFile = new File(getExternalFilesDir(null), "AttyMath.png");
        if (tempFile.exists()) {
            logo.setImageBitmap(BitmapFactory.decodeFile(tempFile.getAbsolutePath()));
        }
        mPrefs = getSharedPreferences("saveUserName", 0);
        userName = mPrefs.getString("USER_NAME", "");
        getSupportActionBar().setTitle(userName + "Math");
    }

}
