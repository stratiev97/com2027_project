package com2027.group9_cw.sk00806.fitme_group9;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class mainScreen extends AppCompatActivity {
    /** Fields */
    ImageButton caloriesButton;
    ImageButton weightButton;
    ImageButton activityButton;
    ImageButton globalButton;
    ArrayList<CalorieDay> calorieData;

    final int CALORIE_CODE = 1;


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


        if(savedInstanceState!=null) {
            if (savedInstanceState.containsKey("CalorieData")) {
                this.calorieData = savedInstanceState.getParcelableArrayList("CalorieData");
            }else{
            }
        }
        if(this.calorieData==null){
            this.calorieData = new ArrayList<CalorieDay>();

        }
        System.out.println("uuh" + calorieData.size());

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
                    Intent calorieIntent = new Intent(mainScreen.this, calorieScreen.class);
                    for(CalorieDay calDay: calorieData){
                        System.out.println(calDay.getDate());
                        System.out.println(new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime()));
                        System.out.println(calDay.getDate().equals(new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime())));

                        if(calDay.getDate().equals(new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime()))){
                            calorieIntent.putExtra("CalorieDay", calDay);
                        }

                    }
                    System.out.println("hi2");
                    System.out.println(calorieData.size());

                    startActivityForResult(calorieIntent, CALORIE_CODE);
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

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        outState.putParcelableArrayList("CalorieData", this.calorieData);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CALORIE_CODE){
            if(resultCode==RESULT_OK){
                System.out.println("ohio");
                CalorieDay calDay = data.getParcelableExtra("CalorieDay");
                System.out.println(calDay.getDate());
                Boolean set = false;
                for(int i=0; i<calorieData.size(); i++){
                    if(calorieData.get(i).getDate().equals(calDay.getDate())){
                        calorieData.set(i,calDay);
                        set = true;
                    }
                }
                if(!set){
                    System.out.println("ohio2");

                    calorieData.add(calDay);
                }
                System.out.println(calDay.getBreakfast_goal());

                System.out.println(calorieData.size());
            }
        }

    }
}
