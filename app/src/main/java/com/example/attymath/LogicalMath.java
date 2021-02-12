package com.example.attymath;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

import pl.droidsonroids.gif.GifImageView;

public class LogicalMath {
   private int generatednum1;
   private int generatednum2;
   private int maxNumber = 200;
   private int maxNumberMultiplication = 12;
   private char mathoperator;
   public LogicalMath(char mathoperator){
        this.mathoperator = mathoperator;
   }

    public int doMath (){
        Random rand = new Random();
        if(mathoperator == 'x'){
            maxNumber = maxNumberMultiplication;
        }
        generatednum1 = rand.nextInt(maxNumber);
        generatednum2 = rand.nextInt(maxNumber);

       int calculatedanswer = -1;

        switch (mathoperator){
            case '+':
                calculatedanswer = generatednum1 + generatednum2;
                break;
            case '-':
                checkNumbers();
                calculatedanswer = generatednum1 - generatednum2;
                break;
            case 'x':
                calculatedanswer = generatednum1 * generatednum2;
                break;
            case '/':
                break;

        }
        return calculatedanswer;
    }
    private void checkNumbers(){
        if(generatednum1 < generatednum2){
            int hold = generatednum1;
            generatednum1 = generatednum2;
            generatednum2 = hold;
        }
    }
    public int[] getNumbers() {
        return new int[]{generatednum1,generatednum2};
    }
}
