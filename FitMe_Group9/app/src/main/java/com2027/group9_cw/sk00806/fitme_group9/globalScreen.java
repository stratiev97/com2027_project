package com2027.group9_cw.sk00806.fitme_group9;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class globalScreen extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //This hides the Title Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //Set Content View
        setContentView(R.layout.globalscreen);

        //Alert box pops up showing Info


        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        Spinner spinner3 = (Spinner) findViewById(R.id.spinner3);

        List<String> options = new ArrayList<>();
        options.add("My Friends");
        options.add("My Age");
        options.add("My Weight");
        options.add("My Area");
        options.add("Global");

        ArrayAdapter<String> optionsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, options);

        spinner1.setAdapter(optionsAdapter);
        spinner2.setAdapter(optionsAdapter);
        spinner3.setAdapter(optionsAdapter);
    }
}
