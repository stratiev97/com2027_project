package com2027.group9_cw.sk00806.fitme_group9;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class mainScreen extends AppCompatActivity {
    /** Fields */
    ImageButton caloriesButton;
    ImageButton weightButton;
    ImageButton activityButton;
    ImageButton globalButton;
    ImageButton overviewButton;
    TextView currentWeight;
    TextView targetWeight;
    TextView distanceTravelled;
    TextView avgDailyCalories;
    TextView weightLost;
    Button dummyData;
    User user;
    Button resetData;
    Button logoutButton;
    FirebaseAuth mAuth;
    ImageButton progressButton;
    GoogleApiClient mGoogleApiClient;
    private boolean online;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    final int CALORIE_CODE = 1;
    final int WEIGHT_CODE = 2;
    final int OVERVIEW_CODE = 3;
    final int PICTURES_CODE = 4;
    final int ACTIVITY_CODE = 5;
    FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //This hides the Title Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //Set Content View
        setContentView(R.layout.mainscreen);


        /** Calories, Weight and Activity Button initialisations */
        caloriesButton = (ImageButton) findViewById(R.id.calories_button);
        weightButton = (ImageButton) findViewById(R.id.weight_button);
        activityButton = (ImageButton) findViewById(R.id.activity_button);
        overviewButton = (ImageButton) findViewById(R.id.overview_button);
        progressButton = (ImageButton) findViewById(R.id.progress_pictures_button);

        targetWeight = (TextView) findViewById(R.id.mainscreen_targetweight);
        distanceTravelled = (TextView) findViewById(R.id.mainscreen_distancetravelled);
        avgDailyCalories = (TextView) findViewById(R.id.mainscreen_avgdailycalories);
        weightLost = (TextView) findViewById(R.id.mainscreen_totalweightlost);
        currentWeight = (TextView) findViewById(R.id.mainscreen_currentweight);
        dummyData = (Button) findViewById(R.id.mainscreen_dummydata);
        resetData = (Button) findViewById(R.id.mainscreen_reset);
        Intent intent = getIntent();
        online = intent.getBooleanExtra("Online", true);

        if(online){
            mAuth = FirebaseAuth.getInstance();
            String authUsername = this.mAuth.getCurrentUser().getDisplayName().toString();
            final String username = authUsername;
        }
        if(savedInstanceState!=null) {
            this.user = savedInstanceState.getParcelable("User");

            if (savedInstanceState.containsKey("Online")) {
                this.online = savedInstanceState.getBoolean("Online", true);
            }
        }else{

        }
        if(this.user==null){
            this.user = createUser(currentUser);

        }

        /** Calories, Weight and Activity Button Change of Image when touched */
        // Calories Button Listener
        caloriesButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){      // If Calories Button is pressed
                    caloriesButton.setImageResource(R.drawable.calories_pressed);   // Image Resource is set to 'calories_pressed'
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP){        // If Calories button is released
                    caloriesButton.setImageResource(R.drawable.calories);           // Image Resource is set to 'calories'
                    Intent calorieIntent = new Intent(mainScreen.this, calorieScreen.class);
                    String currentDate = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
                    calorieIntent.putExtra("CalorieDay", user.getCalorieDay(currentDate));
                    calorieIntent.putExtra("BreakfastGoal", user.getBreakfastCalorieGoal());
                    calorieIntent.putExtra("LunchGoal", user.getLunchCalorieGoal());
                    calorieIntent.putExtra("DinnerGoal", user.getDinnerCalorieGoal());
                    calorieIntent.putExtra("SnackGoal", user.getSnackCalorieGoal());


                    startActivityForResult(calorieIntent, CALORIE_CODE);
                    return true;
                }
                return false;
            }
        });

        // Weight Button Listener
        weightButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){      // If Weight Button is pressed
                    weightButton.setImageResource(R.drawable.weight_pressed);   // Image Resource is set to 'weight_pressed'
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP){        // If Weight button is released
                    weightButton.setImageResource(R.drawable.weight);           // Image Resource is set to 'weight'
                    Intent weightIntent = new Intent(mainScreen.this, weightScreen.class);
                    String currentDate = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
                    weightIntent.putParcelableArrayListExtra("WeightDays", user.getWeightDays());
                    weightIntent.putExtra("TargetWeight", user.getTargetWeight());

                    startActivityForResult(weightIntent, WEIGHT_CODE);
                    return true;
                }
                return false;
            }
        });

        // Activity Button Listener
        activityButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){      // If Activity Button is pressed
                    activityButton.setImageResource(R.drawable.activity_pressed);   // Image Resource is set to 'activity_pressed'
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP){        // If Activity button is released
                    activityButton.setImageResource(R.drawable.activity);           // Image Resource is set to 'activity'

                    Intent activityIntent = new Intent(mainScreen.this, activityScreen.class);
                    String currentDate = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
                    activityIntent.putExtra("ActivityDays", user.getActivityDay(currentDate));

                    startActivityForResult(activityIntent, ACTIVITY_CODE);
                    return true;
                }
                return false;
            }
        });



        // Overview Button Listener
        overviewButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){      // If Overview Button is pressed
                    overviewButton.setImageResource(R.drawable.overview_pressed);   // Image Resource is set to 'overview_pressed'
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP){        // If Overview button is released
                    overviewButton.setImageResource(R.drawable.overview);           // Image Resource is set to 'overview'
                    Intent overviewIntent = new Intent(mainScreen.this, overviewScreen.class);
                    overviewIntent.putParcelableArrayListExtra("WeightDays", user.getWeightDays());
                    overviewIntent.putParcelableArrayListExtra("CalorieDays", user.getCalorieDays());
                    overviewIntent.putParcelableArrayListExtra("ActivityDays", user.getActivityDays());
                    startActivityForResult(overviewIntent, OVERVIEW_CODE);


                    return true;



                }

                return false;

            }
        });

        progressButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){      // If progress pictures Button is pressed
                    progressButton.setImageResource(R.drawable.button_progress_pressed);   // Image Resource is set to 'button_progress_pressed'
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {        // If progress pictures button is released
                    progressButton.setImageResource(R.drawable.button_progress);           // Image Resource is set to 'button_progress'
                    Intent overviewIntent = new Intent(mainScreen.this, progresspicScreen.class);
                    overviewIntent.putParcelableArrayListExtra("Pictures", user.getImages());
                    startActivityForResult(overviewIntent, PICTURES_CODE);
                    return true;
                }
                return false;

            }
        });

        dummyData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.addWeightDay(new WeightDay(80,"20180515"));
                user.addWeightDay(new WeightDay(77.8,"20180516"));
                user.addWeightDay(new WeightDay(77.6,"20180517"));
                user.addWeightDay(new WeightDay(77.7,"20180518"));
                user.addWeightDay(new WeightDay(77.7,"20180519"));
                user.addWeightDay(new WeightDay(77.6,"20180520"));
                user.addWeightDay(new WeightDay(77.4,"20180521"));
                user.addWeightDay(new WeightDay(77.3,"20180522"));
                user.addWeightDay(new WeightDay(77.2,"20180523"));
                user.setTargetWeight(75);
                user.setBreakfastCalorieGoal(200);
                user.setLunchCalorieGoal(400);
                user.setDinnerCalorieGoal(1000);
                user.setSnackCalorieGoal(400);
                user.addCalorieDay(new CalorieDay(170,300,1200,200,200,400,1000,400,"20180415"));
                user.addCalorieDay(new CalorieDay(212,412,1000,222,200,400,1000,400,"20180416"));
                user.addCalorieDay(new CalorieDay(160,422,1700,333,200,400,1000,400,"20180417"));
                user.addCalorieDay(new CalorieDay(221,455,890,111,200,400,1000,400,"20180418"));
                user.addCalorieDay(new CalorieDay(100,435,770,444,200,400,1000,400,"20180419"));
                user.addCalorieDay(new CalorieDay(0,453,700,111,200,400,1000,400,"20180420"));
                user.addCalorieDay(new CalorieDay(232,342,1300,222,200,400,1000,400,"20180421"));
                user.addCalorieDay(new CalorieDay(444,324,200,333,200,400,1000,400,"20180422"));
                user.addCalorieDay(new CalorieDay(123,311,1200,444,200,400,1000,400,"20180423"));
                user.addActivityDay(new ActivityDay("20180515", 15.0));
                user.addActivityDay(new ActivityDay("20180516", 17.0));
                user.addActivityDay(new ActivityDay("20180517", 18.0));
                user.addActivityDay(new ActivityDay("20180519", 15.0));
                user.addActivityDay(new ActivityDay("20180520", 11.0));
                user.addActivityDay(new ActivityDay("20180521", 9.0));

                updateUser(user);
                updateViews();

            }
        });


        resetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = createUser(currentUser);
                updateUser(user);
                updateViews();

            }
        });



        if(online){

            /** Configure sign-in to request the user's ID, email address and basic profile.
             *  ID and basic profile are included in DEFAULT_SIGN_IN
             */
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();

            /** Build a GoogleAPIclient with access to the Google Sign-In API and the options specified by gso. */
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this /* Fragment Activity */, new GoogleApiClient.OnConnectionFailedListener() {
                        @Override //This is a Listener for a Failed Connection.
                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                            Toast.makeText(mainScreen.this, "Something Went Wrong", Toast.LENGTH_SHORT).show(); // This displays a toast on Connection Failed that reads "Something Went Wrong"
                        }
                    })
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();





            /** On Logout Click Initialisation and  Listener */
            mAuth = FirebaseAuth.getInstance();
            logoutButton = (Button) findViewById(R.id.logoutButton);
            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    signOut(); // Signs out, redirects to log in screen
                }
            });

            currentUser = mAuth.getCurrentUser();

            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference();


            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String email = stripEmail(currentUser.getEmail());
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        user = ds.child(email).getValue(User.class);
                        updateViews();

                    }
                    if(user==null){
                        user = createUser(currentUser);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }




        if(this.user==null){
            this.user = createUser(currentUser);
        }
        updateViews();

    }

    public String stripEmail(String email) {
        return email.replaceAll("[.#$]", "");
    }

    @Override
    protected void onPause() {

        super.onPause();


    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        outState.putParcelable("User", this.user);
        outState.putBoolean("Online", this.online);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("User", this.user);
        outState.putBoolean("Online", this.online);


    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState!=null) {
            this.user = savedInstanceState.getParcelable("User");

            if (savedInstanceState.containsKey("Online")) {
                this.online = savedInstanceState.getBoolean("Online", true);

            }
        }else{

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CALORIE_CODE){
            if(resultCode==RESULT_OK){
                CalorieDay calDay = data.getParcelableExtra("CalorieDay");
                if(calDay!=null){
                    this.user.addCalorieDay(calDay);
                    this.user.setBreakfastCalorieGoal(calDay.getBreakfast_goal());
                    this.user.setLunchCalorieGoal(calDay.getLunch_goal());
                    this.user.setDinnerCalorieGoal(calDay.getDinner_goal());
                    this.user.setSnackCalorieGoal(calDay.getSnack_goal());
                    updateUser(user);

                }
            }
        }else if(requestCode==WEIGHT_CODE){
            if(resultCode==RESULT_OK){
                WeightDay weightDay = data.getParcelableExtra("WeightDay");
                double targetWeight = data.getDoubleExtra("TargetWeight", 0.00);
                if(weightDay!=null){
                    this.user.addWeightDay(weightDay);
                }
                this.user.setTargetWeight(targetWeight);
                updateUser(user);
            }
        }else if(requestCode==PICTURES_CODE){
            if(resultCode==RESULT_OK){
                ArrayList<ProgressPicture> pics = data.getParcelableArrayListExtra("Pictures");

                if(pics!=null){
                    this.user.setImages(pics);
                    updateUser(user);
                }

            }
        }else if(requestCode==ACTIVITY_CODE){
            if(resultCode==RESULT_OK){
                ActivityDay activityDay = data.getParcelableExtra("ActivityDays");
                if(activityDay!=null){
                    this.user.addActivityDay(activityDay);
                    updateUser(user);


                }

            }
        }


        updateViews();

    }

    public void updateViews(){
        if(user!=null){
            NumberFormat formatter = new DecimalFormat("#0.0");

            currentWeight.setText("Current Weight: "+ Double.toString(user.getWeight()) + "kg");
            targetWeight.setText("Target Weight: " + Double.toString(user.getTargetWeight()) +"kg");
            weightLost.setText("Weight Lost: "+formatter.format(user.getWeightLost())+"kg");
            distanceTravelled.setText("Avg Distance Travelled: " + formatter.format(user.getAvgDistanceTravelled()*1000)+"m");

            avgDailyCalories.setText("Avg Daily Calories: "+ formatter.format(user.getAvgDailyCalories()));


        }
    }


    /** SignOut Method */
    private void signOut() {
        if(online){
            mAuth.signOut();

            // Google sign out
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                            //redirect back to log in screen.
                            startActivity(new Intent(mainScreen.this, login.class));
                        }
                    });
        }else{
            startActivity(new Intent(mainScreen.this, login.class));

        }

    }

    private User createUser(FirebaseUser fbUser){
        if(fbUser==null){
            return new User();
        }else{
            return new User(fbUser.getEmail());
        }


    }

    private void updateUser(User user){
        if(online){
            databaseReference.child("users").child(user.getEmail()).setValue(user);
        }


    }


}
