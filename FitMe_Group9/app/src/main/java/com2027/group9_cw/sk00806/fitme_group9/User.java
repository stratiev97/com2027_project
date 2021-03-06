package com2027.group9_cw.sk00806.fitme_group9;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * Created by christopher on 23/04/2018.
 *
 * Heavily modded by Lukas
 */

public class User implements Parcelable{
    private int points;
    private String email;
    private int breakfastCalorieGoal;
    private int lunchCalorieGoal;
    private int dinnerCalorieGoal;
    private int snackCalorieGoal;
    private double weight;
    private double targetWeight;
    private ArrayList<CalorieDay> calorieDays;
    private ArrayList<WeightDay> weightDays;
    private ArrayList<ProgressPicture> images;
    private ArrayList<ActivityDay> activityDays;


    public User(String email){
        this.email = email;
        targetWeight=0;
        this.breakfastCalorieGoal=0;
        this.lunchCalorieGoal=0;
        this.dinnerCalorieGoal=0;
        this.snackCalorieGoal=0;
        this.weight=0;
        this.calorieDays = new ArrayList<>();
        this.weightDays = new ArrayList<>();
        this.images = new ArrayList<>();
        this.activityDays = new ArrayList<>();



    }
    public User(){
        this.email = "";
        targetWeight=0;
        this.breakfastCalorieGoal=0;
        this.lunchCalorieGoal=0;
        this.dinnerCalorieGoal=0;
        this.snackCalorieGoal=0;
        this.weight=0;
        this.calorieDays = new ArrayList<>();
        this.weightDays = new ArrayList<>();
        this.images = new ArrayList<>();
        this.activityDays = new ArrayList<>();



    }



    protected User(Parcel in) {
        email = in.readString();
        breakfastCalorieGoal = in.readInt();
        lunchCalorieGoal = in.readInt();
        dinnerCalorieGoal = in.readInt();
        snackCalorieGoal = in.readInt();
        weight = in.readDouble();
        targetWeight = in.readDouble();
        calorieDays = in.createTypedArrayList(CalorieDay.CREATOR);
        weightDays = in.createTypedArrayList(WeightDay.CREATOR);
        images = in.readArrayList(String.class.getClassLoader());
        activityDays = in.createTypedArrayList(ActivityDay.CREATOR);

    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public void setId(String id) {
        //this.id = id;
        //I'm making this useless because it would mess everything up.


    }

    public int getBreakfastCalorieGoal() {
        return breakfastCalorieGoal;
    }

    public void setBreakfastCalorieGoal(int breakfastCalorieGoal) {
        this.breakfastCalorieGoal = breakfastCalorieGoal;
    }

    public double getWeightLost(){
        Collections.sort(weightDays);
        if(weightDays.size()>0){
            return weightDays.get(0).getWeight() - weightDays.get(weightDays.size()-1).getWeight();
        }
        return 0.0;
    }

    public double getAvgDailyCalories(){
        int total = 0;
        if(calorieDays.size()>0){
            for(int i = 0; i<calorieDays.size(); i++){
                total+= calorieDays.get(i).getTotalCalories();
            }
            total/=calorieDays.size();
        }
        return total;
    }

    public int getLunchCalorieGoal() {
        return lunchCalorieGoal;
    }

    public void setLunchCalorieGoal(int lunchCalorieGoal) {
        this.lunchCalorieGoal = lunchCalorieGoal;
    }

    public ArrayList<CalorieDay> getCalorieDays() {
        return calorieDays;
    }

    public void setCalorieDays(ArrayList<CalorieDay> calorieDays) {
        this.calorieDays = calorieDays;
    }

    public ArrayList<ProgressPicture> getImages() {

        return images;
    }

    public void setImages(ArrayList<ProgressPicture> images) {
        this.images = images;
    }

    public int getDinnerCalorieGoal() {
        return dinnerCalorieGoal;
    }

    public void setDinnerCalorieGoal(int dinnerCalorieGoal) {
        this.dinnerCalorieGoal = dinnerCalorieGoal;
    }

    public int getSnackCalorieGoal() {
        return snackCalorieGoal;
    }

    public double getTargetWeight() {
        return targetWeight;
    }

    public void setSnackCalorieGoal(int snackCalorieGoal) {
        this.snackCalorieGoal = snackCalorieGoal;
    }

    public Double getWeight() {
        if(weightDays.size()>0){
            return weightDays.get(weightDays.size()-1).getWeight();
        }else{
            return 0.0;
        }
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void addWeightDay(WeightDay day){
        Boolean set = false;
        if(weightDays.size()>0){
            for(int i=0; i<this.weightDays.size(); i++){
                if(this.weightDays.get(i).getDate().equals(day.getDate())){
                    this.weightDays.set(i,day);
                    set = true;
                }
            }
        }

        if(!set){
            this.weightDays.add(day);
        }
    }

    public void addCalorieDay(CalorieDay day){
        Boolean set = false;
        if(calorieDays.size()>0){
            for(int i=0; i<this.calorieDays.size(); i++){
                if(this.calorieDays.get(i).getDate().equals(day.getDate())){
                    this.calorieDays.set(i,day);
                    set = true;
                }
            }
        }

        if(!set){
            this.calorieDays.add(day);
        }
    }
    public void addActivityDay(ActivityDay day){
        Boolean set = false;
        if(activityDays.size()>0){
            for(int i=0; i<this.activityDays.size(); i++){
                if(this.activityDays.get(i).getDate().equals(day.getDate())){
                    this.activityDays.set(i,day);
                    set = true;
                }
            }
        }

        if(!set){
            this.activityDays.add(day);
        }
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public double getAvgDistanceTravelled(){
        double total = 0;

        if(activityDays.size()>0){
            for(int i = 0; i<activityDays.size(); i++){
                total+= activityDays.get(i).getTotaldistance();
            }
            total/=activityDays.size();
        }
        return total;
    }

    public WeightDay getWeightDay(String date){
        for(WeightDay day: weightDays){
            if(day.getDate().equals(date)){
                return day;
            }
        }
        return new WeightDay();
    }

    public CalorieDay getCalorieDay(String date){
        for(CalorieDay day: calorieDays){
            if(day.getDate().equals(date)){
                return day;
            }
        }
        return new CalorieDay();
    }
    public ActivityDay getActivityDay(String date){
        for(ActivityDay day: activityDays){
            if(day.getDate().equals(date)){
                return day;
            }
        }
        return new ActivityDay();
    }
    public ArrayList<WeightDay> getWeightDays(){
        return new ArrayList<>(weightDays);
    }

    public void setTargetWeight(double targetWeight) {
        this.targetWeight = targetWeight;
    }
    @Override
    public int describeContents() {
        return 0;
    }


    public void setWeight2(double weight) {
        this.weight = weight;
    }

    public void setWeightDays(ArrayList<WeightDay> weightDays) {
        this.weightDays = weightDays;
    }

    public ArrayList<ActivityDay> getActivityDays() {
        return activityDays;
    }

    public void setActivityDays(ArrayList<ActivityDay> activityDays) {
        this.activityDays = activityDays;
    }

    public String getEmail() {
        return email.replaceAll("[.#$]", "");
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.email);
        dest.writeInt(this.breakfastCalorieGoal);
        dest.writeInt(this.lunchCalorieGoal);
        dest.writeInt(this.dinnerCalorieGoal);
        dest.writeInt(this.snackCalorieGoal);
        dest.writeDouble(this.weight);
        dest.writeDouble(this.targetWeight);
        dest.writeList(weightDays);
        dest.writeList(calorieDays);
        dest.writeList(images);
        dest.writeList(activityDays);


    }
}
