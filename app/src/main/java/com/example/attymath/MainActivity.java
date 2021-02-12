package com.example.attymath;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configureAdditionButton();
        configureSubtractionButton();
        configureMultiplicationButton();
    }
//    private void configureAdditionButton(){
//        ImageButton additionButton = (ImageButton) findViewById(R.id.additionActivity);
//        additionButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                startActivity(new Intent(MainActivity.this, Addition.class));
//            }
//        });
//    }
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
