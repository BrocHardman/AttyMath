package com.example.attymath;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;

public class MainActivity extends AppCompatActivity {
    // This has to be class level for the dialog to get user name
    String userName = "Atty";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configureAdditionButton();
        configureSubtractionButton();
        configureMultiplicationButton();
        ImageView logo;
        logo = (ImageView)findViewById(R.id.AttymathLogo);
        File tempFile = new File("/sdcard/Android/data/com.example.attymath/files/AttyMath.png");
        if(tempFile.exists()){
            logo.setImageBitmap(BitmapFactory.decodeFile("/sdcard/Android/data/com.example.attymath/files/AttyMath.png"));
        }else{
            showMessage();
            new RetrieveTitleImage().execute(userName);
        }



    }
    private void showMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);

        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);
        builder.setTitle("Hey there!")
                .setMessage("Looks like this is the first time we have met or my memory is really bad. Either way it would be nice to know your name. Please type your name and then click OK")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String userNameInput = input.getText().toString();
                        boolean badinput = true;
                        for(int i = 0; i < userNameInput.length() && badinput; i++){
                            if(userNameInput.charAt(i) != ' ')
                                badinput = false;
                        }

                        if(!badinput)
                            userName = userName;
                            dialog.cancel();
                    }
                })
                .setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void configureAdditionButton(){
    ImageButton additionButton = (ImageButton) findViewById(R.id.additionActivity);
    additionButton.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v){
            Intent intent = new Intent(MainActivity.this, GameDriver.class);
            Bundle b = new Bundle(); // create a bundle to send to created activity class
            b.putChar("mathoperator",'+'); // in that bundle make a key and a value
            intent.putExtras(b); // place bundle inside intent
            startActivity(intent);
        }
    });
}
    private void configureSubtractionButton(){
        ImageButton subtractionButton = (ImageButton) findViewById(R.id.subtractionActivity);
        subtractionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, GameDriver.class);
                Bundle b = new Bundle(); // create a bundle to send to created activity class
                b.putChar("mathoperator",'-'); // in that bundle make a key and a value
                intent.putExtras(b); // place bundle inside intent
                startActivity(intent);
            }
        });
    }
    private void configureMultiplicationButton(){
        ImageButton multiplicationButton = (ImageButton) findViewById(R.id.multiplicationActivity);
        multiplicationButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, GameDriver.class);
                Bundle b = new Bundle(); // create a bundle to send to created activity class
                b.putChar("mathoperator",'x'); // in that bundle make a key and a value
                intent.putExtras(b); // place bundle inside intent
                startActivity(intent);
            }
        });
    }

}
