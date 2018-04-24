package com2027.group9_cw.sk00806.fitme_group9;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by christopher on 23/04/2018.
 */

public class WeightDay implements Parcelable,Comparable {


    private double weight;
    private String date;

    public WeightDay(){
        this.weight = 0;
        this.date = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

    }
    public WeightDay(double weight){
        this.weight = weight;
        this.date = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

    }
    public WeightDay(double weight, String date){
        this.weight = weight;
        this.date = date;

    }

    public WeightDay(Parcel in){
        this.weight = in.readDouble();
        this.date = in.readString();
    }


    public static final Creator<WeightDay> CREATOR = new Creator<WeightDay>() {
        @Override
        public WeightDay createFromParcel(Parcel in) {
            return new WeightDay(in);
        }

        @Override
        public WeightDay[] newArray(int size) {
            return new WeightDay[size];
        }
    };

    public String getDate() {
        return date;
    }

    public String getFormattedDate(){
        return date.substring(6,8)+ "/" + date.substring(4,6) + "/" + date.substring(0,4);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(weight);
        dest.writeString(date);
    }

    @Override
    public int compareTo(@NonNull Object o) {
        int date1 = Integer.parseInt(date);
        WeightDay weightDay = (WeightDay) o;
        int date2 = Integer.parseInt(weightDay.getDate());
        return date1-date2;
    }
}
