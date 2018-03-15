package com2027.group9_cw.sk00806.fitme_group9;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;


public class mainScreen extends AppCompatActivity {
    /** Fields */
    ImageButton caloriesButton;
    ImageButton weightButton;
    ImageButton activityButton;
    ImageButton globalButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //This hides the Title Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //Set Content View
        setContentView(R.layout.mainscreen);

        /** Calories, Weight and Activity Button initialisations */
        caloriesButton = (ImageButton) findViewById(R.id.calories_button);
        weightButton = (ImageButton) findViewById(R.id.weight_button);
        activityButton = (ImageButton) findViewById(R.id.activity_button);
        globalButton = (ImageButton) findViewById(R.id.global_button);


        /** Calories, Weight and Activity Button Change of Image when touched */
        // Calories Button Listener
        caloriesButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){      // If Calories Button is pressed
                    caloriesButton.setImageResource(R.drawable.calories_pressed);   // Image Resource is set to 'calories_pressed'
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP){        // If Calories button is released
                    caloriesButton.setImageResource(R.drawable.calories);           // Image Resource is set to 'calories'
                    startActivity(new Intent(mainScreen.this, calorieScreen.class)); //Calories Activity Starts
                    return true;
                }
                return false;
            }
        });

        // Weight Button Listener
        weightButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){      // If Weight Button is pressed
                    weightButton.setImageResource(R.drawable.weight_pressed);   // Image Resource is set to 'weight_pressed'
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP){        // If Weight button is released
                    weightButton.setImageResource(R.drawable.weight);           // Image Resource is set to 'weight'
                    startActivity(new Intent(mainScreen.this, weightScreen.class)); //Weight Activity Starts
                    return true;
                }
                return false;
            }
        });

        // Activity Button Listener
        activityButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){      // If Activity Button is pressed
                    activityButton.setImageResource(R.drawable.activity_pressed);   // Image Resource is set to 'activity_pressed'
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP){        // If Activity button is released
                    activityButton.setImageResource(R.drawable.activity);           // Image Resource is set to 'activity'
                    startActivity(new Intent(mainScreen.this, activityScreen.class)); //Activities Activity Starts
                    return true;
                }
                return false;
            }
        });

        // Global Button Listener
        globalButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){      // If Global Button is pressed
                    globalButton.setImageResource(R.drawable.global_pressed);   // Image Resource is set to 'global_pressed'
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP){        // If Global button is released
                    globalButton.setImageResource(R.drawable.global);           // Image Resource is set to 'global'
                    startActivity(new Intent(mainScreen.this, globalScreen.class)); //Global Activity Starts
                    return true;
                }
                return false;
            }
        });
    };
}
