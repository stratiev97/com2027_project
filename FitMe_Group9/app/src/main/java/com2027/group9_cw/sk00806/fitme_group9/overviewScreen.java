package com2027.group9_cw.sk00806.fitme_group9;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class overviewScreen extends AppCompatActivity{
    final int GALLERY_CODE = 11;

    private ArrayList<WeightDay> weightDays;
    private ArrayList<CalorieDay> calorieDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.overviewscreen);

        Intent intent = getIntent();

        this.calorieDays = intent.getParcelableArrayListExtra("CalorieDays");
        this.weightDays = intent.getParcelableArrayListExtra("WeightDays");




    }
}
