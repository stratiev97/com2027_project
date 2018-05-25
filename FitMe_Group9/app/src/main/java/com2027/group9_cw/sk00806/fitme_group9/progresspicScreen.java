package com2027.group9_cw.sk00806.fitme_group9;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.File;
import android.provider.MediaStore;
import android.os.Environment;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class progresspicScreen extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 3;
    private static final int MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE = 2;
    private static final int TAKE_PICTURE_CODE = 1;
    /* Location of the image file in memory */
    private Uri fileUri;
    /* Location of the potential new image file in memory */
    private Uri tempFileUri;
    ArrayList<ProgressPicture> pictures = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    Button takepic;
    Context context;
    String mCurrentPhotoPath;
    Button buto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progresspic_screen);

        Intent intent = getIntent();
        this.pictures = intent.getParcelableArrayListExtra("Pictures");

        if(pictures==null){
            if(savedInstanceState!=null) {
                if (savedInstanceState.containsKey("Pictures")) {
                    this.pictures = savedInstanceState.getParcelableArrayList("Pictures");
                }
            }

        }

        if(this.pictures == null) {
            this.pictures = new ArrayList<>();
            Log.e("URI", "grr");
        }

        context = getBaseContext();
        recyclerView = (RecyclerView)findViewById(R.id.progress_pictures_recyclerview);
        layoutManager = new GridLayoutManager(this,2 );
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            //If we do not have this permission, request it.
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);

        }

        takepic = (Button)findViewById(R.id.progress_pictures_takepic);
        takepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        fileUri = FileProvider.getUriForFile(context,
                                "com.example.android.fileprovider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(takePictureIntent, TAKE_PICTURE_CODE);
                    }
                }

            }
        });


        adapter = new RecyclerAdapter(pictures, progresspicScreen.this);
        recyclerView.setAdapter(adapter);



    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, TAKE_PICTURE_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //If the intent was to take a picture.
        if (requestCode == TAKE_PICTURE_CODE) {
            //If the intent was performed correctly.
            if (resultCode == RESULT_OK) {
                //Set the file Uri to the Uri of the image.
                Log.e("URI",fileUri.toString());
                String temp = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

                ProgressPicture progressPicture = new ProgressPicture(fileUri.toString(), temp);
                pictures.add(progressPicture);
                adapter.notifyDataSetChanged();

            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("aaa",Integer.toString(pictures.size()));

        outState.putParcelableArrayList("Pictures", pictures);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("Pictures",pictures);
        setResult(RESULT_OK, intent);

        super.onBackPressed();

    }
}
