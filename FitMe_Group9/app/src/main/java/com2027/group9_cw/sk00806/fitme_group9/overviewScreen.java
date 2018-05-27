package com2027.group9_cw.sk00806.fitme_group9;


import android.content.Entity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class overviewScreen extends AppCompatActivity{
    final int GALLERY_CODE = 11;


    private ArrayList<WeightDay> weightDays;
    private ArrayList<CalorieDay> calorieDays;
    private ArrayList<ActivityDay> activityDays;
    private TextView avgweightlost;
    private TextView weightlost;
    private TextView avgdisttravelled;
    private TextView disttravelled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.overviewscreen);
        Intent intent = getIntent();

        this.calorieDays = intent.getParcelableArrayListExtra("CalorieDays");
        this.weightDays = intent.getParcelableArrayListExtra("WeightDays");
        this.activityDays = intent.getParcelableArrayListExtra("ActivityDays");

        NumberFormat formatter = new DecimalFormat("#0.0");
        avgweightlost = (TextView)findViewById(R.id.overview_avgweightlost);
        weightlost = (TextView)findViewById(R.id.overview_weightlost);
        avgdisttravelled = (TextView)findViewById(R.id.overview_avgdisttravelled);
        disttravelled = (TextView)findViewById(R.id.overview_disttravelled);
        double avw = intent.getDoubleExtra("avgweightlost", 0);
        double w = intent.getDoubleExtra("weightlost", 0);
        double avd = intent.getDoubleExtra("avgdisttravelled", 0);
        double d = intent.getDoubleExtra("disttravelled", 0);
        avgweightlost.setText("User Average Weight Lost: " + formatter.format(avw) + "kg");
        weightlost.setText("Your Weight Lost: " + formatter.format(w) + "kg");
        avgdisttravelled.setText("User Averarage Distance Travelled Per Day: " + formatter.format(avd) + "km");
        disttravelled.setText("Your Averarage Distance Travelled Per Day: " + formatter.format(d) + "km");


        if(this.calorieDays==null){
            this.calorieDays = new ArrayList<>();
        }
        if(this.weightDays==null){
            this.calorieDays = new ArrayList<>();
        }
        if(this.activityDays==null){
            this.calorieDays = new ArrayList<>();
        }

        Collections.sort(activityDays);
        Collections.sort(calorieDays);
        Collections.sort(weightDays);

        setupDistanceGraph();
        setupCalorieGraph();
        setupWeightGraph();
    }

    private Date getDateFromString(String dateString){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date date;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            date = null;
            e.printStackTrace();
        }

        return date;
    }

    private void setupDistanceGraph(){
        int distanceFill = Color.parseColor("#b5eeff");

        LineChart chart = (LineChart) findViewById(R.id.distanceChart);
        chart.setBorderColor(Color.YELLOW);
        chart.setDragEnabled(true);
        chart.setPinchZoom(true);
        List<Entry> entries = new ArrayList<Entry>();
        for(ActivityDay day:activityDays){
           entries.add(new Entry(new Long(getDateFromString(day.getDate()).getTime()).floatValue(), (float)day.getTotaldistance()));
        }
        if(entries.size()>0){
            LineDataSet dataSet = new LineDataSet(entries, "Distance Travelled(km) per day"); // add entries to dataset
            dataSet.setColor(Color.WHITE);
            dataSet.setValueTextColor(Color.WHITE);
            dataSet.setFillColor(distanceFill);
            YAxis distanceYR = chart.getAxisRight();
            distanceYR.setDrawLabels(false);
            YAxis distanceYL = chart.getAxisLeft();
            distanceYL.setTextColor(Color.WHITE);
            distanceYL.setAxisMinimum(0);
            distanceYL.setValueFormatter(new DistanceFormatter());
            XAxis distanceX = chart.getXAxis();
            distanceX.setDrawLabels(false);
            dataSet.setDrawFilled(true);
            LineData lineData = new LineData(dataSet);
            chart.setData(lineData);
            chart.getLegend().setEnabled(false);
            chart.setAutoScaleMinMaxEnabled(true);
            chart.getDescription().setEnabled(false);
            chart.animateY(3000, Easing.EasingOption.EaseOutQuad);
        }


    }

    private void setupCalorieGraph(){
        int distanceFill = Color.parseColor("#ffb4b4");

        LineChart chart = (LineChart) findViewById(R.id.calorieChart);
        chart.setBorderColor(Color.YELLOW);
        chart.setDragEnabled(true);
        chart.setPinchZoom(true);
        List<Entry> entries = new ArrayList<Entry>();
        for(CalorieDay day:calorieDays){
            entries.add(new Entry(new Long(getDateFromString(day.getDate()).getTime()).floatValue(), (float)day.getTotalCalories()));
        }
        if(entries.size()>0){
            LineDataSet dataSet = new LineDataSet(entries, "Distance Travelled(km) per day"); // add entries to dataset
            dataSet.setColor(Color.WHITE);
            dataSet.setValueTextColor(Color.WHITE);
            dataSet.setFillColor(distanceFill);
            YAxis distanceYR = chart.getAxisRight();
            distanceYR.setDrawLabels(false);
            YAxis distanceYL = chart.getAxisLeft();
            distanceYL.setTextColor(Color.WHITE);
            distanceYL.setAxisMinimum(0);
            XAxis distanceX = chart.getXAxis();
            distanceX.setDrawLabels(false);
            dataSet.setDrawFilled(true);
            LineData lineData = new LineData(dataSet);
            chart.setData(lineData);
            chart.getLegend().setEnabled(false);
            chart.setAutoScaleMinMaxEnabled(true);
            chart.getDescription().setEnabled(false);
            chart.animateY(3000, Easing.EasingOption.EaseOutQuad);
        }


    }


    private void setupWeightGraph(){
        int distanceFill = Color.parseColor("#b3ffb6");

        LineChart chart = (LineChart) findViewById(R.id.weightChart);
        chart.setBorderColor(Color.YELLOW);
        chart.setDragEnabled(true);
        chart.setPinchZoom(true);
        List<Entry> entries = new ArrayList<Entry>();




        for(WeightDay day:weightDays){
            entries.add(new Entry(new Long(getDateFromString(day.getDate()).getTime()).floatValue(), (float)day.getWeight()));
        }
        if(entries.size()>0){
            LineDataSet dataSet = new LineDataSet(entries, "Distance Travelled(km) per day"); // add entries to dataset
            dataSet.setColor(Color.WHITE);
            dataSet.setValueTextColor(Color.WHITE);
            dataSet.setFillColor(distanceFill);
            YAxis distanceYR = chart.getAxisRight();
            distanceYR.setDrawLabels(false);
            YAxis distanceYL = chart.getAxisLeft();
            distanceYL.setTextColor(Color.WHITE);
            distanceYL.setAxisMinimum(0);

            double minval = 100000;
            if(weightDays.size()>0){
                for(WeightDay day:weightDays){
                    if(day.getWeight()<minval){
                        minval = day.getWeight();
                    }
                }
            }else{
                minval=0;
            }

            distanceYL.setAxisMinimum((float)minval);


            distanceYL.setValueFormatter(new WeightFormatter());
            XAxis distanceX = chart.getXAxis();
            distanceX.setDrawLabels(false);
            dataSet.setDrawFilled(true);
            LineData lineData = new LineData(dataSet);
            chart.setData(lineData);
            chart.getLegend().setEnabled(false);
            chart.setAutoScaleMinMaxEnabled(true);
            chart.getDescription().setEnabled(false);
            chart.animateY(3000, Easing.EasingOption.EaseOutQuad);
        }


    }

}

class DistanceFormatter implements IAxisValueFormatter {

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // Simple version. You should use a DateFormatter to specify how you want to textually represent your date.



        return Float.toString(value)+ "km" ;
    }
    // ...
}

class WeightFormatter implements IAxisValueFormatter {

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // Simple version. You should use a DateFormatter to specify how you want to textually represent your date.


        NumberFormat formatter = new DecimalFormat("#0.0");

        return formatter.format(value)+ "kg" ;
    }
    // ...
}