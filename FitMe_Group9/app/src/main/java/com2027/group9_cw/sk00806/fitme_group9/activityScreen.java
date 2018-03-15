package com2027.group9_cw.sk00806.fitme_group9;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

public class activityScreen extends AppCompatActivity{

    /** Fields */
    ImageButton startButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //This hides the Title Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //Set Content View
        setContentView(R.layout.activityscreen);

        /** Start Button initialisation */
        startButton = (ImageButton) findViewById(R.id.start_button);

        // Start Button Listener
        startButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){      // If Start Button is pressed
                    startButton.setImageResource(R.drawable.start_pressed);   // Image Resource is set to 'start_pressed'
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP){        // If Start button is released
                    startButton.setImageResource(R.drawable.start);           // Image Resource is set to 'start'
                    return true;
                }
                return false;
            }
        });
    }
}
