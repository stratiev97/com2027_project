package com2027.group9_cw.sk00806.fitme_group9;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class weightScreen extends AppCompatActivity {
    final Context context = this;

    final int ADD_WEIGHT = 0;
    final int SET_TARGET = 1;

    ListView weightListView;
    ArrayList<WeightDay> weightDays;
    Button addWeight;
    WeightDay weightDay;
    double targetWeight=0;
    TextView currentWeightLabel;
    TextView totalWeightLostLabel;
    TextView targetWeightLabel;
    Button setTargetWeight;
    WeightListAdapter adapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //This hides the Title Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //Set Content View
        setContentView(R.layout.weightscreen);

        Intent intent = getIntent();

        this.weightDays = intent.getParcelableArrayListExtra("WeightDays");
        this.targetWeight = intent.getDoubleExtra("TargetWeight",0.00);

        if(this.weightDays == null){
            this.weightDays = new ArrayList<>();
        }

        weightListView = (ListView) findViewById(R.id.weightscreen_listview);

        adapter = new WeightListAdapter(this, R.layout.weightitem, weightDays);
        weightListView.setAdapter(adapter);

        addWeight = (Button) findViewById(R.id.weightscreen_addweight);
        addWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalorieDialog(ADD_WEIGHT);
            }
        });

        setTargetWeight = (Button) findViewById(R.id.weightscreen_settargetweight);
        setTargetWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalorieDialog(SET_TARGET);
            }
        });

        progressBar = (ProgressBar) findViewById(R.id.weightscreen_progressbar);
        targetWeightLabel = (TextView) findViewById(R.id.weightscreen_targetweight);
        currentWeightLabel = (TextView) findViewById(R.id.weightscreen_currentweight);
        totalWeightLostLabel = (TextView) findViewById(R.id.weightscreen_totalweightlost);
        updateView();
    }

    private void showCalorieDialog(final int target){
        LayoutInflater inflater = LayoutInflater.from(context);
        View promptsView = inflater.inflate(R.layout.weightinputdialog, null);

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
                                    switch(target){
                                        case ADD_WEIGHT:
                                            double weightValue = Double.parseDouble(userInput.getText().toString());
                                            weightDay = new WeightDay(weightValue);
                                            addWeightDay(weightDay);
                                            break;
                                        case SET_TARGET:
                                            weightValue = Double.parseDouble(userInput.getText().toString());
                                            targetWeight = weightValue;
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

    private void addWeightDay(WeightDay day){
        Boolean set = false;
        for(int i=0; i<this.weightDays.size(); i++){
            if(this.weightDays.get(i).getDate().equals(day.getDate())){
                this.weightDays.set(i,day);
                set = true;
            }
        }
        if(!set){
            this.weightDays.add(day);
        }
    }
    public boolean isNumeric(String s) {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }

    public void updateView(){

        if(weightDays.size()>0){

            Collections.sort(weightDays);

            double currentWeight = weightDays.get(weightDays.size()-1).getWeight();
            double startingWeight = weightDays.get(0).getWeight();
            double weightToLose = startingWeight - targetWeight;
            double weightLost = startingWeight - currentWeight;


            currentWeightLabel.setText("Current Weight: " + currentWeight + "kg");
            targetWeightLabel.setText("Target Weight: " + targetWeight + "kg");
            totalWeightLostLabel.setText("Total Weight Lost: " + weightLost + "kg");
            Collections.sort(weightDays);
            adapter.notifyDataSetChanged();

            progressBar.setMax((int) weightToLose);
            progressBar.setProgress((int) weightLost);

            progressBar.getProgressDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);


        }

    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        intent.putExtra("WeightDay",weightDay);
        intent.putExtra("TargetWeight",targetWeight);

        setResult(RESULT_OK, intent);

        super.onBackPressed();
    }
}
