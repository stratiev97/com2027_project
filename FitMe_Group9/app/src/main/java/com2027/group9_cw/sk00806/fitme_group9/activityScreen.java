package com2027.group9_cw.sk00806.fitme_group9;

import android.*;
import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Text;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class activityScreen extends AppCompatActivity implements OnMapReadyCallback {

    /** Fields */
    ImageButton startButton;
    private GoogleMap mMap;
    private TextView distanceText;
    private TextView pointsText;
    private TextView paceText;
    int buttonState = 0;
    double distance = 0;
    double distance2 = 0;
    double speed = 0;
    int points = 0;
    int dtemp = 0;
    int ptemp = 0;
    int ctemp = 0;
    double stemp = 0;
    Circle circle1;
    Circle circle2;
    Circle circle3;
    double calculatedSpeed;
    Location initial = null;
    Location lastlocation = null;
    ArrayList<Location> listLocsToDraw = new ArrayList<Location>();
    ActivityDay activityDay;
    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //This hides the Title Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //Set Content View
        setContentView(R.layout.activityscreen);


        Intent intent = getIntent();
        this.activityDay = intent.getParcelableExtra("ActivityDays");

        if(activityDay==null){
            if(savedInstanceState!=null) {
                if (savedInstanceState.containsKey("ActivityDays")) {
                    this.activityDay = savedInstanceState.getParcelable("ActivityDays");
                }
            }
        }
        if(this.activityDay == null) {
            this.activityDay = new ActivityDay();
        }





        /** Start Button initialisation */
        startButton = (ImageButton) findViewById(R.id.start_button);

        /** Distance TextView initialization */
        distanceText = (TextView) findViewById(R.id.distanceText);

        /** Points TextView initialization */
        pointsText = (TextView) findViewById(R.id.pointsText);

        /** Pace TextView initialization */
        paceText = (TextView) findViewById(R.id.speedText);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Start Button Listener
        startButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN && buttonState == 0) {      // If Start Button is pressed
                    startButton.setImageResource(R.drawable.start_pressed);   // Image Resource is set to 'start_pressed'
                    buttonState = 1;
                    return true;
                }

                if (event.getAction() == MotionEvent.ACTION_UP && buttonState == 1) {        // If Start button is released
                    startButton.setImageResource(R.drawable.stop);           // Image Resource is set to 'stop'
                    buttonState = 2;

                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN && buttonState == 2) {      // If Stop Button is pressed
                    startButton.setImageResource(R.drawable.stop_pressed);   // Image Resource is set to 'stop_pressed'
                    buttonState = 3;
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP && buttonState == 3) {        // If Stop button is released
                    startButton.setImageResource(R.drawable.start);           // Image Resource is set to 'start'
                    buttonState = 0;
                    //Display total Distance.
                    Toast.makeText(activityScreen.this, "You covered a total of: " + distance2 + " km                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ",
                            Toast.LENGTH_LONG).show();
                    return true;
                }

                return false;
            }
        });

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        int n = 0;
        mMap = googleMap;

        /** User needs to accept Permissions before we access location */
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(activityScreen.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, n);
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            }
        }

        /** Ui is set */
        mMap.getUiSettings().setZoomControlsEnabled(true); //(+/-) buttons on screen used for zoom.
        mMap.getUiSettings().setMyLocationButtonEnabled(true); // My Location button

        /** Location is set and focus on location */
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

            @Override
            public void onMyLocationChange(Location arg0) {
                // TODO Auto-generated method stub

                CameraUpdate center= CameraUpdateFactory.newLatLng(new LatLng(arg0.getLatitude(), arg0.getLongitude()));
                CameraUpdate zoom=CameraUpdateFactory.zoomTo(19);

                mMap.moveCamera(center);
                mMap.animateCamera(zoom);

                /** If Start Button is Pressed - Start tracking */
                if (buttonState == 2){
                    /** Set Initial Location */ //This should be moved to onstartclick?
                    if (initial == null) {
                        initial = arg0;
                    }

                    /** Add every location change to List */
                    listLocsToDraw.add(arg0);
                    /** Draw Line of location */
                    drawPrimaryLinePath(listLocsToDraw);
                    /** Calculate Distance and update Text */
                    //For loop to calculate distance
                    if (listLocsToDraw.size() < 2) {
                        //do nothing
                    }
                    else{
                        distance += GetDistanceFromLatLonInm(listLocsToDraw.get(dtemp).getLatitude(), listLocsToDraw.get(dtemp).getLongitude(), listLocsToDraw.get(dtemp+1).getLatitude(), listLocsToDraw.get(dtemp+1).getLongitude());
                        dtemp++;
                    }


                    double temp = round(distance, 2); // Keep only 2 dp
                    if (temp >= distance) {
                        distance2 = temp;
                    }
                    /** Set TextView to distance Text */
                    distanceText.setText(Double.toString(distance2));

                    /** Create Circles */
                    if (ctemp == 0) {
                        /** Add 3 random Geofence Maps - 1000 points each time you get it */
                        Random rand = new Random();
                        double rand1 = 0.0001 + (0.0002 - 0.0001) * rand.nextDouble();
                        double rand2 = 0.0001 + (0.0002 - 0.0001) * rand.nextDouble();
                        double rand3 = 0.0001 + (0.0002 - 0.0001) * rand.nextDouble();
                        double rand4 = 0.0001 + (0.0002 - 0.0001) * rand.nextDouble();
                        double rand5 = 0.0001 + (0.0002 - 0.0001) * rand.nextDouble();
                        double rand6 = 0.0001 + (0.0002 - 0.0001) * rand.nextDouble();

                        circle1 = mMap.addCircle(new CircleOptions().center(new LatLng(arg0.getLatitude() + rand1, arg0.getLongitude() - rand2)).radius(8));
                        circle2 = mMap.addCircle(new CircleOptions().center(new LatLng(arg0.getLatitude() - rand3, arg0.getLongitude() + rand4)).radius(8));
                        circle3 = mMap.addCircle(new CircleOptions().center(new LatLng(arg0.getLatitude() - rand5, arg0.getLongitude() - rand6)).radius(8));
                        ctemp = 1;
                    }

                    /** On collision to circles */
                    if (circle1.getCenter().latitude > arg0.getLatitude() - 0.000080 && circle1.getCenter().longitude > arg0.getLongitude() - 0.000080 && circle1.getCenter().latitude < arg0.getLatitude() + 0.000080 && circle1.getCenter().longitude < arg0.getLongitude() + 0.000080) {
                        Random rand = new Random();
                        double rand1 = 0.0001 + (0.0003 - 0.0001) * rand.nextDouble();
                        double rand2 = 0.0001 + (0.0003 - 0.0001) * rand.nextDouble();
                        circle1.remove();
                        circle1 = mMap.addCircle(new CircleOptions().center(new LatLng(arg0.getLatitude() + rand1, arg0.getLongitude() - rand2)).radius(8));
                        points += 1000; // 1000 points for each circle you overlap
                        updatePoints(points);
                    }

                    if (circle2.getCenter().latitude > arg0.getLatitude() - 0.000080 && circle2.getCenter().longitude > arg0.getLongitude() - 0.000080 && circle2.getCenter().latitude < arg0.getLatitude() + 0.000080 && circle2.getCenter().longitude < arg0.getLongitude() + 0.000080) {
                        Random rand = new Random();
                        double rand1 = 0.0001 + (0.0003 - 0.0001) * rand.nextDouble();
                        double rand2 = 0.0001 + (0.0003 - 0.0001) * rand.nextDouble();
                        circle2.remove();
                        circle2 = mMap.addCircle(new CircleOptions().center(new LatLng(arg0.getLatitude() + rand1, arg0.getLongitude() - rand2)).radius(8));
                        points += 1000; // 1000 points for each circle you overlap
                        updatePoints(points);
                    }

                    if (circle3.getCenter().latitude > arg0.getLatitude() - 0.000080 && circle3.getCenter().longitude > arg0.getLongitude() - 0.000080 && circle3.getCenter().latitude < arg0.getLatitude() + 0.000080 && circle3.getCenter().longitude < arg0.getLongitude() + 0.000080) {
                        Random rand = new Random();
                        double rand1 = 0.0001 + (0.0003 - 0.0001) * rand.nextDouble();
                        double rand2 = 0.0001 + (0.0003 - 0.0001) * rand.nextDouble();
                        circle3.remove();
                        circle3 = mMap.addCircle(new CircleOptions().center(new LatLng(arg0.getLatitude() + rand1, arg0.getLongitude() - rand2)).radius(8));
                        points += 1000; // 1000 points for each circle you overlap
                        updatePoints(points);
                    }

                    /** Check Speed */
                    if (lastlocation != null) {
                        double elapsedTime = (arg0.getTime() - lastlocation.getTime()) / 1_000; // Convert milliseconds to seconds
                        calculatedSpeed = lastlocation.distanceTo(arg0) / elapsedTime;
                    }
                    if (listLocsToDraw.size() < 3) {
                        // Do nothing
                    }
                    else {
                        lastlocation = listLocsToDraw.get(listLocsToDraw.size()-3);
                    }

                    speed = arg0.hasSpeed() ? arg0.getSpeed() : calculatedSpeed;
                    stemp = round(speed, 2);
                    speed = stemp;
                    updatePace(speed);

                }


                /** If Stop button Pressed */
                //Reset Everything
                if (buttonState == 0) {
                    activityDay.addDistance(distance);
                    mMap.clear();
                    distance = 0;
                    distanceText.setText(Double.toString(distance));
                    listLocsToDraw.clear();
                    dtemp = 0;
                    ptemp = 0;
                    ctemp = 0;
                    points = 0;
                    speed = 0;
                    updatePace(speed);
                    updatePoints(points);
                }

                /** Assign points on various distances */
                if (distance > 0.02 && distance < 0.03 && ptemp == 0) {
                    points += 100; //Add points when reaching 200m
                    updatePoints(points);
                    ptemp = 1;
                }
                if (distance > 0.5 && distance < 0.51 && ptemp == 1) {
                    points += 500; //Add points when reaching 500m
                    updatePoints(points);
                    ptemp = 2;
                }
                if (distance > 1.0 && distance < 1.01 && ptemp == 2) {
                    points += 1000; //Add points when reaching 1km
                    updatePoints(points);
                    ptemp = 3;
                }
                if (distance > 5.0 && distance < 5.01 && ptemp == 3) {
                    points += 5000; //Add points when reaching 5km
                    updatePoints(points);
                    ptemp = 4;
                }

                /** Assign Points on various speeds */
                if (speed > 3.0 && speed < 5.0) {
                    points += 100; //Add points when moving between 3-5 m/s
                    updatePoints(points);
                }
                if (speed > 5.0 && speed < 7.0) {
                    points += 200; //Add points when moving between 5-7 m/s
                    updatePoints(points);
                }
                if (speed > 7.0 && speed < 10.0) {
                    points += 500; //Add points when moving between 7-10 m/s
                    updatePoints(points);
                }
                if (speed > 10.0 && speed < 12.5) {
                    points += 1000; //Add points when moving between 10-12.5 m/s
                    updatePoints(points);
                }
                if (speed > 12.5) {
                    //Display total Distance.
                    Toast.makeText(activityScreen.this, "Driving Detected",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    /** Method to draw Path along all the path travelled */
    private void drawPrimaryLinePath( ArrayList<Location> List )
    {
        if ( mMap == null )
        {
            return;
        }

        if ( listLocsToDraw.size() < 2 )
        {
            return;
        }

        PolylineOptions options = new PolylineOptions();

        options.color( Color.parseColor( "#160000FF" ) );
        options.width( 5 );
        options.visible( true );

        for ( Location locRecorded : listLocsToDraw )
        {
            options.add( new LatLng( locRecorded.getLatitude(),
                    locRecorded.getLongitude() ) );
        }

        mMap.addPolyline( options );

    }

    /** Method to calculate Distance from path travelled */
    public double GetDistanceFromLatLonInm(double lat1, double lon1, double lat2, double lon2)
    {
        final int R = 6371;
        // Radius of the earth in m
        double dLat = deg2rad(lat2 - lat1);
        // deg2rad below
        double dLon = deg2rad(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c;
        // Distance in km
        return d;
    }
    private double deg2rad(double deg)
    {
        return deg * (Math.PI / 180);
    }

    /** Method to round double to keep only specific decimal places */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    /** Method to update Point Text */
    public void updatePoints(int points) {
        pointsText.setText(Integer.toString(points));
    }


    /** Method to update Pace Text */
    public void updatePace(double pace) {
        round(pace, 2);
        paceText.setText(Double.toString(pace));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("ActivityDays", activityDay);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState.containsKey("ActivityDays")){
            activityDay = savedInstanceState.getParcelable("ActivityDays");

        }
        if(activityDay==null){
            activityDay = new ActivityDay();
        }
    }

    @Override
    public void onBackPressed() {

            Intent intent = new Intent();
            Log.e("woah", Double.toString(activityDay.getTotaldistance()));
            intent.putExtra("ActivityDays",activityDay);
            setResult(RESULT_OK, intent);
            super.onBackPressed();




    }
}
