package com2027.group9_cw.sk00806.fitme_group9;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by christopher on 23/04/2018.
 */

public class CalorieDay implements Parcelable {

    private int breakfast_calories;
    private int lunch_calories;
    private int dinner_calories;
    private int snack_calories;

    private int breakfast_goal;
    private int lunch_goal;
    private int dinner_goal;
    private int snack_goal;

    private String date;

    public CalorieDay(){
        this.breakfast_calories = 0;
        this.lunch_calories = 0;
        this.dinner_calories = 0;
        this.snack_calories = 0;

        this.breakfast_goal = 0;
        this.lunch_goal = 0;
        this.dinner_goal = 0;
        this.snack_goal = 0;
        this.date = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

    }

    public CalorieDay(int breakfast_goal, int lunch_goal, int dinner_calories, int snack_calories){
        this.breakfast_calories = 0;
        this.lunch_calories = 0;
        this.dinner_calories = 0;
        this.snack_calories = 0;

        this.breakfast_goal = breakfast_goal;
        this.lunch_goal = lunch_goal;
        this.dinner_goal = dinner_calories;
        this.snack_goal = snack_calories;

        this.date = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());;
    }

    public CalorieDay(Parcel in){
        this.breakfast_calories = in.readInt();
        this.lunch_calories = in.readInt();
        this.dinner_calories = in.readInt();
        this.snack_calories = in.readInt();

        this.breakfast_goal = in.readInt();
        this.lunch_goal = in.readInt();
        this.dinner_goal = in.readInt();
        this.snack_goal = in.readInt();

        this.date = in.readString();
    }

    public static final Creator<CalorieDay> CREATOR = new Creator<CalorieDay>() {
        @Override
        public CalorieDay createFromParcel(Parcel in) {
            return new CalorieDay(in);
        }

        @Override
        public CalorieDay[] newArray(int size) {
            return new CalorieDay[size];
        }
    };

    public int getBreakfast_calories() {
        return breakfast_calories;
    }

    public void setBreakfast_calories(int breakfast_calories) {
        this.breakfast_calories = breakfast_calories;
    }

    public int getLunch_calories() {
        return lunch_calories;
    }

    public void setLunch_calories(int lunch_calories) {
        this.lunch_calories = lunch_calories;
    }

    public int getDinner_calories() {
        return dinner_calories;
    }

    public void setDinner_calories(int dinner_calories) {
        this.dinner_calories = dinner_calories;
    }

    public int getSnack_calories() {
        return snack_calories;
    }

    public void setSnack_calories(int snack_calories) {
        this.snack_calories = snack_calories;
    }

    public int getBreakfast_goal() {
        return breakfast_goal;
    }

    public void setBreakfast_goal(int breakfast_goal) {
        this.breakfast_goal = breakfast_goal;
    }

    public int getLunch_goal() {
        return lunch_goal;
    }

    public void setLunch_goal(int lunch_goal) {
        this.lunch_goal = lunch_goal;
    }

    public int getDinner_goal() {
        return dinner_goal;
    }

    public void setDinner_goal(int dinner_goal) {
        this.dinner_goal = dinner_goal;
    }

    public int getSnack_goal() {
        return snack_goal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setSnack_goal(int snack_goal) {
        this.snack_goal = snack_goal;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.breakfast_calories);
        dest.writeInt(this.lunch_calories);
        dest.writeInt(this.dinner_calories);
        dest.writeInt(this.snack_calories);

        dest.writeInt(this.breakfast_goal);
        dest.writeInt(this.lunch_goal);
        dest.writeInt(this.dinner_goal);
        dest.writeInt(this.snack_goal);

        dest.writeString(date);
    }
}
