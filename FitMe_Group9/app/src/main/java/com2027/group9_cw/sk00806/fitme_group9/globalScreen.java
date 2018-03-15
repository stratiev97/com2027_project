package com2027.group9_cw.sk00806.fitme_group9;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

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
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Opt Out of Global Scores")
                .setMessage("You can choose to opt out of Global Scores and keep your results and data to yourself")
                .setPositiveButton("Opt In", null) //null denotes an onClickListener -- Could be used to never ask again
                .setNegativeButton("Opt Out", null) //null denotes an onClickListener -- Could be used to not Upload data or Back off Screen
                .create();

        dialog.show();
    }
}
