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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class activityScreen extends AppCompatActivity implements OnMapReadyCallback {

    /** Fields */
    ImageButton startButton;
    private GoogleMap mMap;
    private TextView distanceText;
    int buttonState = 0;
    double distance = 0;
    double distance2 = 0;
    Location initial = null;
    ArrayList<Location> listLocsToDraw = new ArrayList<Location>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //This hides the Title Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //Set Content View
        setContentView(R.layout.activityscreen);

        /** Start Button initialisation */
        startButton = (ImageButton) findViewById(R.id.start_button);

        /** Distance TextView initialization */
        distanceText = (TextView) findViewById(R.id.distanceText);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
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
                    Toast.makeText(activityScreen.this, "You covered a total of: " + distance2 + " m                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            ",
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
                    distance = GetDistanceFromLatLonInm(initial.getLatitude(), initial.getLongitude(), arg0.getLatitude(), arg0.getLongitude());

                    double temp = round(distance, 2); // Keep only 2 dp
                    if (temp >= distance) {
                        distance2 = temp;
                    }
                    /** Set TextView to distance Text */
                    distanceText.setText(Double.toString(distance2));
                }

                /** If Stop button Pressed */
                if (buttonState == 0) {
                    mMap.clear();
                    distance = 0;
                    distanceText.setText(Double.toString(distance));
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
        final int R = 6371000;
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

}
