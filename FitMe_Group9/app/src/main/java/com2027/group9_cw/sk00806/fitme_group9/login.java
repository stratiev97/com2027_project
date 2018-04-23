package com2027.group9_cw.sk00806.fitme_group9;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import java.util.ArrayList;

public class login extends AppCompatActivity {
    /** Fields */
    ImageButton loginButton;
    ImageButton registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //This hides the Title Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //Set Content View
        setContentView(R.layout.activity_login);

        /** Play and Score Button initialisations */
        loginButton = (ImageButton) findViewById(R.id.login_button);

        /** Login and Register Button Change of Image when touched */
        // Login Button Listener
        loginButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){      // If Login Button is pressed
                    loginButton.setImageResource(R.drawable.google_button_pressed);   // Image Resource is set to 'login_pressed'
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP){        // If Login button is released
                    loginButton.setImageResource(R.drawable.google_button);           // Image Resource is set to 'login'
                    startActivity(new Intent(login.this, mainScreen.class)); //MainScreen Activity Starts
                    return true;
                }
                return false;
            }
        });

    }
}
