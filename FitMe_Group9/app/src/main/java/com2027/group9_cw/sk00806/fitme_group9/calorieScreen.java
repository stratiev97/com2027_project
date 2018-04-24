package com2027.group9_cw.sk00806.fitme_group9;


import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class calorieScreen extends AppCompatActivity {

    public final static int ADD_TO_BREAKFAST = 0;
    public final static int ADD_TO_LUNCH = 1;
    public final static int ADD_TO_DINNER = 2;
    public final static int ADD_TO_SNACKS = 3;

    final Context context = this;

    private int daily_goal = 0;
    private int daily_progress = 0;
    private TextView goalLabel;
    private TextView progressLabel;

    private ProgressBar progressBar;

    private CalorieDay calorieDay;
    private Button breakfastAddButton;
    private Button lunchAddButton;
    private Button dinnerAddButton;
    private Button snackAddButton;

    private Button setGoalsButton;

    private Button breakfastResetButton;
    private Button lunchResetButton;
    private Button dinnerResetButton;
    private Button snackResetButton;

    private TextView breakfastCurrentLabel;
    private TextView lunchCurrentLabel;
    private TextView dinnerCurrentLabel;
    private TextView snackCurrentLabel;

    private TextView breakfastGoalLabel;
    private TextView lunchGoalLabel;
    private TextView dinnerGoalLabel;
    private TextView snackGoalLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //Set Content View
        setContentView(R.layout.caloriescreen);

        Intent intent = getIntent();

        this.calorieDay = intent.getParcelableExtra("CalorieDay");


        if(this.calorieDay == null){
            this.calorieDay = new CalorieDay();
        }

        this.calorieDay.setBreakfast_goal(intent.getIntExtra("BreakfastGoal",0));
        this.calorieDay.setLunch_goal(intent.getIntExtra("LunchGoal",0));
        this.calorieDay.setDinner_goal(intent.getIntExtra("DinnerGoal",0));
        this.calorieDay.setSnack_goal(intent.getIntExtra("SnackGoal",0));

        this.breakfastCurrentLabel = (TextView) findViewById(R.id.calorieScreen_breakfastCurrentLabel);
        this.lunchCurrentLabel = (TextView) findViewById(R.id.calorieScreen_lunchCurrentLabel);
        this.dinnerCurrentLabel = (TextView) findViewById(R.id.calorieScreen_dinnerCurrentLabel);
        this.snackCurrentLabel = (TextView) findViewById(R.id.calorieScreen_snackCurrentLabel);

        this.breakfastGoalLabel = (TextView) findViewById(R.id.calorieScreen_breakfastGoalLabel);
        this.lunchGoalLabel = (TextView) findViewById(R.id.calorieScreen_lunchGoalLabel);
        this.dinnerGoalLabel = (TextView) findViewById(R.id.calorieScreen_dinnerGoalLabel);
        this.snackGoalLabel = (TextView) findViewById(R.id.calorieScreen_snackGoalLabel);

        this.breakfastAddButton = (Button) findViewById(R.id.calorieScreen_breakfastAddButton);
        breakfastAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalorieDialog(ADD_TO_BREAKFAST);
            }
        });

        this.lunchAddButton = (Button) findViewById(R.id.calorieScreen_lunchAddButton);
        lunchAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalorieDialog(ADD_TO_LUNCH);
            }
        });

        this.dinnerAddButton = (Button) findViewById(R.id.calorieScreen_dinnerAddButton);
        dinnerAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalorieDialog(ADD_TO_DINNER);
            }
        });

        this.snackAddButton = (Button) findViewById(R.id.calorieScreen_snackAddButton);
        snackAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalorieDialog(ADD_TO_SNACKS);
            }
        });

        this.setGoalsButton = (Button) findViewById(R.id.calorieScreen_calorieGoalButton);
        setGoalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGoalDialog();
            }
        });

        this.breakfastResetButton = (Button) findViewById(R.id.calorieScreen_breakfastResetButton);
        breakfastResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calorieDay.setBreakfast_calories(0);
                updateView();
            }
        });

        this.lunchResetButton = (Button) findViewById(R.id.calorieScreen_lunchResetButton);
        lunchResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calorieDay.setLunch_calories(0);
                updateView();

            }
        });

        this.dinnerResetButton = (Button) findViewById(R.id.calorieScreen_dinnerResetButton);
        dinnerResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calorieDay.setDinner_calories(0);
                updateView();

            }
        });

        this.snackResetButton = (Button) findViewById(R.id.calorieScreen_snackResetButton);
        snackResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calorieDay.setSnack_calories(0);
                updateView();

            }
        });
        this.goalLabel = (TextView) findViewById(R.id.calorieScreen_dailyGoalLabel);
        this.progressLabel = (TextView) findViewById(R.id.calorieScreen_soFarTodayLabel);

        this.progressBar = (ProgressBar) findViewById(R.id.calorieScreen_progressBar);

        updateView();

    }

    private void showCalorieDialog(final int valueToUpdate){
        LayoutInflater inflater = LayoutInflater.from(context);
        View promptsView = inflater.inflate(R.layout.calorieinputdialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                if(isNumeric(userInput.getText().toString())){
                                    int value = Integer.parseInt(userInput.getText().toString());
                                    switch(valueToUpdate){
                                        case ADD_TO_BREAKFAST:
                                            calorieDay.setBreakfast_calories(calorieDay.getBreakfast_calories() + value);
                                            break;
                                        case ADD_TO_LUNCH:
                                            calorieDay.setLunch_calories(calorieDay.getLunch_calories() + value);
                                            break;
                                        case ADD_TO_DINNER:
                                            calorieDay.setDinner_calories(calorieDay.getDinner_calories() + value);
                                            break;
                                        case ADD_TO_SNACKS:
                                            calorieDay.setSnack_calories(calorieDay.getSnack_calories() + value);
                                            break;
                                    }
                                    updateView();
                                }

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }


    private void showGoalDialog(){
        LayoutInflater inflater = LayoutInflater.from(context);
        View promptsView = inflater.inflate(R.layout.caloriegoalupdatedialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        alertDialogBuilder.setView(promptsView);

        final EditText userInput1 = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput1);

        final EditText userInput2 = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput2);

        final EditText userInput3 = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput3);

        final EditText userInput4 = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput4);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                if(isNumeric(userInput1.getText().toString())){
                                    int breakfastValue = Integer.parseInt(userInput1.getText().toString());
                                    calorieDay.setBreakfast_goal(breakfastValue);
                                }
                                if(isNumeric(userInput2.getText().toString())){
                                    int lunchValue = Integer.parseInt(userInput2.getText().toString());
                                    calorieDay.setLunch_goal(lunchValue);
                                }
                                if(isNumeric(userInput3.getText().toString())){
                                    int dinnerValue = Integer.parseInt(userInput3.getText().toString());
                                    calorieDay.setDinner_goal(dinnerValue);
                                }
                                if(isNumeric(userInput4.getText().toString())){
                                    int snackValue = Integer.parseInt(userInput4.getText().toString());
                                    calorieDay.setSnack_goal(snackValue);
                                }



                                updateView();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }


    private void updateView(){
        breakfastGoalLabel.setText("Goal: " + calorieDay.getBreakfast_goal());
        breakfastCurrentLabel.setText("Current: " + calorieDay.getBreakfast_calories());

        lunchGoalLabel.setText("Goal: " + calorieDay.getLunch_goal());
        lunchCurrentLabel.setText("Current: " + calorieDay.getLunch_calories());

        dinnerGoalLabel.setText("Goal: " + calorieDay.getDinner_goal());
        dinnerCurrentLabel.setText("Current: " + calorieDay.getDinner_calories());

        snackGoalLabel.setText("Goal: " + calorieDay.getSnack_goal());
        snackCurrentLabel.setText("Current: " + calorieDay.getSnack_calories());

        daily_goal = calorieDay.getBreakfast_goal() + calorieDay.getDinner_goal() + calorieDay.getLunch_goal() + calorieDay.getSnack_goal();
        daily_progress = calorieDay.getBreakfast_calories() + calorieDay.getDinner_calories() + calorieDay.getLunch_calories() + calorieDay.getSnack_calories();


        progressBar.setMax(daily_goal);
        progressBar.setProgress((daily_progress));


        if(daily_progress < daily_goal*0.8){
            progressBar.getProgressDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
        }else if(daily_progress <= daily_goal){
            progressBar.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        }
        else if(daily_progress < daily_goal*1.1){
            progressBar.getProgressDrawable().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);
        }else{
            progressBar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        }
        goalLabel.setText("Today's Goal: "+ daily_goal + " Calories");
        progressLabel.setText("Today's Progress: "+ daily_progress + " Calories");

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        System.out.println("um " + calorieDay.getBreakfast_goal());
        intent.putExtra("CalorieDay",calorieDay);

        setResult(RESULT_OK, intent);

        super.onBackPressed();

    }

    public boolean isNumeric(String s) {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }
}
