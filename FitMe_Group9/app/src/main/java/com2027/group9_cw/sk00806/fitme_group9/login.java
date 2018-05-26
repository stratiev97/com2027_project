package com2027.group9_cw.sk00806.fitme_group9;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.ArrayList;

public class login extends AppCompatActivity {
    /** Fields */

    SignInButton signInButton; // This is the Google Sign in Button
    Button guestButton;
    FirebaseAuth mAuth; // This is the Firebase Authorisation File
    private static final int RC_SIGN_IN = 2; // This is the Sign In request Sign In Code
    GoogleApiClient mGoogleApiClient; // This is the Google API Client
    FirebaseAuth.AuthStateListener mAuthListener; // This is an Authorisation State Listener - Used to determine if an account is signed In

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener); // As soon as App is created Authentication Listener is created
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //This hides the Title Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //Set Content View
        setContentView(R.layout.activity_login);

        guestButton = (Button) findViewById(R.id.guest_signin);
        guestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent guestLogin = new Intent(login.this, mainScreen.class);
                guestLogin.putExtra("Online", false);
                startActivity(guestLogin); //MainScreen Activity Starts

            }
        });

        /** Initialise Google Sign Button */
        signInButton = (SignInButton) findViewById(R.id.google_login_button);  // Maps signInButton to the Physical Button on the Layout

        /** Initialise mAuth */
        mAuth = FirebaseAuth.getInstance();

        /** Authentication Listener Set Up */
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null) {
                    startActivity(new Intent(login.this, mainScreen.class)); //MainScreen Activity Starts
                }
            }
        };

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
                        Toast.makeText(login.this, "Something Went Wrong", Toast.LENGTH_SHORT).show(); // This displays a toast on Connection Failed that reads "Something Went Wrong"
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        /** Button onClick Listener - Once the button is clicked, signIn */
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(); // Signs the user in. Once this happens, the mAuthListener redirects to new screen.
            }
        });

    }

    /** Google Sign In Method */
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    /** Override for onActivityResult */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // If connection unsuccessful, show error Message
                Toast.makeText(login.this, "Authorization Went Wrong", Toast.LENGTH_SHORT).show(); // This displays a toast on Connection Failed that reads "Something Went Wrong"

            }
        }
    }

    /** Method for Authorising with Google */
    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(login.this, "Authentication Failed.",
                                Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }
}
