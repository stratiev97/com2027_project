package com2027.group9_cw.sk00806.fitme_group9;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by christopher on 23/04/2018.
 */

public class ActivityDay implements Parcelable {

    private double totaldistance;
    private String date;

    public ActivityDay(){
        this.totaldistance = 0;
        this.date = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

    }
    public ActivityDay(Parcel in){
        this.totaldistance = in.readDouble();
        this.date = in.readString();
    }



    public static final Creator<ActivityDay> CREATOR = new Creator<ActivityDay>() {
        @Override
        public ActivityDay createFromParcel(Parcel in) {
            return new ActivityDay(in);
        }

        @Override
        public ActivityDay[] newArray(int size) {
            return new ActivityDay[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeDouble(this.totaldistance);
        dest.writeString(date);
    }

    public void addDistance (double dist){
        totaldistance+=dist;
    }

    public double getTotaldistance() {
        return totaldistance;
    }

    public void setTotaldistance(double totaldistance) {
        this.totaldistance = totaldistance;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
