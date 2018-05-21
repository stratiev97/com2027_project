package com2027.group9_cw.sk00806.fitme_group9;

import android.os.Parcel;
import android.os.Parcelable;

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
 */

public class User implements Parcelable{

    private String displayName;
    private int id;
    private int breakfastCalorieGoal;
    private int lunchCalorieGoal;
    private int dinnerCalorieGoal;
    private int snackCalorieGoal;
    private double weight;
    private double targetWeight;
    private ArrayList<CalorieDay> calorieDays;
    private ArrayList<WeightDay> weightDays;
    private DatabaseReference databaseRef;


    public User(String displayName){
        targetWeight=0;
        this.id=1;
        this.breakfastCalorieGoal=0;
        this.lunchCalorieGoal=0;
        this.dinnerCalorieGoal=0;
        this.snackCalorieGoal=0;
        this.weight=0;
        this.calorieDays = new ArrayList<>();
        this.weightDays = new ArrayList<>();
        this.displayName = displayName;

        //Should call the DB for messing with.
        this.databaseRef = FirebaseDatabase.getInstance().getReference();

        //Check if DB exists. In the future. I've been poking at similar things since 4am and this one seems most likely to wrok, but right now I need sleep.
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //if dataSnapshot.
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //Going to set this to check if it exists but can't figure out why
        if(null == null) {
            //The TRUE result is going to in the future read from the database and populate it with those values. Right now it's set to create this for debugging.
            databaseRef.child("Database").child("User").child("UserName").setValue(this.displayName);
            this.databaseRef = databaseRef.child("Database").child("User").child("UserName").child(this.displayName);
            databaseRef.child("Blah").setValue("PREVIOUS DATA FOUND");

        } else{
            //THE BELOW CODE SHOULD BE RIGGED UP TO BE CALLED ONLY IF IT DOESNT ALREADY EXIST.
            databaseRef.child("Database").child("User").child("UserName").setValue(this.displayName);
            this.databaseRef = databaseRef.child("Database").child("User").child("UserName").child(this.displayName);
            databaseRef.child("Blah").setValue("PREVIOUS DATA NOT FOUND");
        }

    }


    protected User(Parcel in) {
        displayName = in.readString();
        id = in.readInt();
        breakfastCalorieGoal = in.readInt();
        lunchCalorieGoal = in.readInt();
        dinnerCalorieGoal = in.readInt();
        snackCalorieGoal = in.readInt();
        weight = in.readDouble();
        targetWeight = in.readDouble();
        calorieDays = in.createTypedArrayList(CalorieDay.CREATOR);
        weightDays = in.createTypedArrayList(WeightDay.CREATOR);


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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        databaseRef.child("Database").child("User").child("UserName").child("ID").setValue(Integer.toString(id));

    }

    public int getBreakfastCalorieGoal() {
        return breakfastCalorieGoal;
    }

    public void setBreakfastCalorieGoal(int breakfastCalorieGoal) {
        this.breakfastCalorieGoal = breakfastCalorieGoal;
        databaseRef.child("Goals").child("BreakfastCalorieGoal").setValue(Integer.toString(breakfastCalorieGoal));
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

    public double getAvgDistanceTravelled(){
        return 0;
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

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.displayName);
        dest.writeInt(this.id);
        dest.writeInt(this.breakfastCalorieGoal);
        dest.writeInt(this.lunchCalorieGoal);
        dest.writeInt(this.dinnerCalorieGoal);
        dest.writeInt(this.snackCalorieGoal);
        dest.writeDouble(this.weight);
        dest.writeDouble(this.targetWeight);
        dest.writeList(weightDays);
        dest.writeList(calorieDays);


    }
}
