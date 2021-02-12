package com.example.attymath;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import pl.droidsonroids.gif.GifImageView;

public class GameDriver extends AppCompatActivity {
    int generatednum1;
    int generatednum2;
    int calculatedanswer;
    int usernumber;
    int amountcompleted;
    private int highscore = 0;
    char mathoperator;

    EditText userAnswer;
    TextView confirmation;
    TextView operatorSymbolText;
    TextView topConfirm;
    TextView timerText;
    GifImageView confetti;
    Switch timedrunToggle;
    CountDownTimer mathTimer;
    Button nextButton;
    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        if(b != null)
            mathoperator = b.getChar("mathoperator");
        mPrefs = getSharedPreferences("savehighscore",0);
        highscore = mPrefs.getInt("HIGH_SCORE"+mathoperator,0);

        setContentView(R.layout.activity_gamedriver);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userAnswer = (EditText) findViewById(R.id.userAnswer);
        userAnswer.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER )
                {
                    return checkAnswer(v);
                }
                return false;
            }
        });

        confirmation = (TextView) findViewById(R.id.confirmUserAnswer);
        confirmation.setTextColor(0xFF228B22);
        confirmation.setVisibility(View.GONE);

        operatorSymbolText = (TextView) findViewById(R.id.operatorSymbol);
        operatorSymbolText.setText(String.valueOf(mathoperator));

        topConfirm = (TextView) findViewById(R.id.topConfirm);

        timerText = (TextView) findViewById(R.id.timerText);
        timerText.setVisibility(View.GONE);

        timedrunToggle =  (Switch)findViewById(R.id.timerSwitch);

        confetti = (GifImageView) findViewById(R.id.confettiGIF);
        confetti.setVisibility(View.GONE);

        configureNextButton();
        newEquation();

        timedrunToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showMessage();
                } else {
                    // The toggle is disabled
                    if (mathTimer != null){
                        mathTimer.cancel();
                        timerText.setText("");
                        timerText.setVisibility(View.GONE);
                        topConfirm.setText("");
                        amountcompleted = 0;
                    }
                }
            }
        });


    }

    private void configureNextButton() {
        nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setVisibility(View.GONE);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newEquation();
                clearFields();
                nextButton.setVisibility(View.GONE);
                userAnswer.setVisibility(View.VISIBLE);

            }
        });
    }

    private void clearFields(){
        userAnswer.setText("", EditText.BufferType.EDITABLE);
        userAnswer.setVisibility(View.VISIBLE);
        //TODO: could probably take out the EDITABLE
        topConfirm.setText("",EditText.BufferType.EDITABLE);
        confirmation.setVisibility(View.GONE);
        confetti.setVisibility(View.GONE);
        nextButton.setVisibility(View.GONE);
    }

    private void newEquation(){
        LogicalMath logicalMath = new LogicalMath(mathoperator);
        calculatedanswer = logicalMath.doMath();
        generatednum1 = logicalMath.getNumbers()[0];
        generatednum2 = logicalMath.getNumbers()[1];
        // this pads the number with a space to assure symbol doesn't make it look like
        // a negative number. If random bound number is increased into thousands this will not work
        TextView editText2 = (TextView) findViewById(R.id.bottomNum);
        if(generatednum1 >= 10 && generatednum2 <= 9){
            editText2.setText(" " + generatednum2, EditText.BufferType.EDITABLE);
        }else{
            editText2.setText("" + generatednum2, EditText.BufferType.EDITABLE);
        }
        TextView editText = (TextView) findViewById(R.id.topNum);
        editText.setText("" + generatednum1, EditText.BufferType.EDITABLE);
    }

    private boolean checkAnswer(View v){
        confirmation.setText(""+ calculatedanswer,EditText.BufferType.EDITABLE);
        // try-catch for handling no input crash
        try{
            usernumber = Integer.valueOf(userAnswer.getText().toString());
        }catch(Exception e){
            return false;
        }
        if (usernumber != calculatedanswer) {
            // Answer is WRONG
            topConfirm.setTextColor(0xFFBB2222);
            topConfirm.setText("Sorry Atty, " + usernumber + " is not the right answer", EditText.BufferType.EDITABLE);
        } else {
            // Answer is CORRECT
            amountcompleted++;
            topConfirm.setTextColor(0xff99cc00);
            topConfirm.setText("Great work Atty! " + usernumber + " is the right answe!", EditText.BufferType.EDITABLE);
            userAnswer.setVisibility(View.GONE);
            nextButton.setVisibility(View.VISIBLE);

            confirmation.setVisibility(View.VISIBLE);
            confetti.setVisibility(View.VISIBLE);
            hideKeyboard(v);
        }
        return true;

    }

    public void hideKeyboard(View view) {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch(Exception ignored) {
        }
    }
    // When time toggle activates show message explaining timer
    private void showMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Hey Atty! Lets see how fast you are!")
                .setMessage("You have 2 minutes to solve as many problems as you can. I'll start" +
                        " the time as soon as you click the start button.")
                .setPositiveButton("Start", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        clearFields();
                        newEquation();
                        nextButton.setVisibility(View.GONE);
                        amountcompleted = 0;
                        timerText.setText("Ready?");
                        timerText.setVisibility(View.VISIBLE);
                        dialog.cancel();
                        timedrun();

                        topConfirm.setTextColor(0xFFBB2222);
                        topConfirm.setText("The timer is running!", EditText.BufferType.EDITABLE);
                    }
                })
                // This is to react to touch away from box to dismiss. For now dont allow it.
//                .setOnDismissListener(new DialogInterface.OnDismissListener() {
//                    @Override
//                    public void onDismiss(DialogInterface dialog) {
//                        topConfirm.setTextColor(0xFFBB2222);
//                        topConfirm.setText("you didnt click the box", EditText.BufferType.EDITABLE);
//                    }
//                })
                // not allowing touch away from box
                .setCancelable(false);
                AlertDialog dialog = builder.create();
                dialog.show();

    }

    /**
     * I believe this may cause a stack overflow if the user tries again multiple times. This is
     * because the finish method in timedrun calls goAgainDialog and goAgainDialog calls timedrun.
     * Not sure if this is totally the case but needs to be looked into. Best way is probably to see
     * if CountDownTimer has a pid that we could check and create dialog on return but would need to
     * be synchronous because we still need to be able to compute problems at the same time.
     */
    private void goAgainDialog(){
        String highscorestring = "Your highscore is " + highscore;
        if(amountcompleted > highscore){
            int oldhighscore = highscore;
            highscore = amountcompleted;
            highscorestring = "That beats your record of " + oldhighscore;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Great work Atty!")
                .setMessage("You solved " +amountcompleted + " in just 2 minutes! "
                        + highscorestring + " Do you want to try again?")
                .setPositiveButton("Again!", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        amountcompleted = 0;
                        clearFields();
                        newEquation();
                        topConfirm.setTextColor(0xFFBB2222);
                        topConfirm.setText("GO! G0! G0!", EditText.BufferType.EDITABLE);
                        topConfirm.setVisibility(View.VISIBLE);
                        timedrun();
                        dialog.cancel();
                    }

                })
                .setNegativeButton("I'm Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        amountcompleted = 0;
                        timedrunToggle.setChecked(false);
                        dialog.cancel();
                    }
                })
                .setCancelable(false);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Starts a timer. Will be used to keep track of how many problems are solved in a specified
     * amount of time.
     */
    private void timedrun(){
        mathTimer = new CountDownTimer(2*60000, 1000) {
            public void onTick(long millisUntilFinished) {
                timerText.setText("Time remaining: " +new SimpleDateFormat("mm:ss").format(new Date( millisUntilFinished)));
            }

            public void onFinish() {
                goAgainDialog();

            }
        }.start();
    }
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        outState.putString("HIGH_SCORE", String.valueOf(highscore));
//        super.onSaveInstanceState(outState);
//    }
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        highscore = Integer.valueOf(savedInstanceState.getString("HIGH_SCORE"));
//    }

    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor ed = mPrefs.edit();
        ed.putInt("HIGH_SCORE"+mathoperator, highscore);
        ed.commit();
    }
    protected void onResume(){
        super.onResume();
        mPrefs = getSharedPreferences("savehighscore",0);
        highscore = mPrefs.getInt("HIGH_SCORE"+mathoperator,0);
    }

}


