package com.example.cropprotection.Reporting;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.cropprotection.R;
import com.example.cropprotection.WaterBills.WaterBillDetails;
import com.example.cropprotection.loginandregistration.app.AppConfig;
import com.example.cropprotection.loginandregistration.app.MySingleton;
import com.example.cropprotection.loginandregistration.helper.SQLiteHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Report extends AppCompatActivity {

    // LogCat tag
    private static final String TAG = Report.class.getSimpleName();
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE1 =300;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private Uri fileUri; // file url to store image/video

    private ProgressDialog pDialog;
    private Button btnCapturePicture, btnRecordVideo, btnsubmit;
    private EditText story,subject,location;
    private SQLiteHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        btnCapturePicture = (Button) findViewById(R.id.btnRecordImage);
        btnRecordVideo = (Button) findViewById(R.id.btnRecordVideo);

        btnsubmit = (Button) findViewById(R.id.btnsubmit);
        story=(EditText) findViewById(R.id.story);
        subject=(EditText) findViewById(R.id.subject);
        location=(EditText) findViewById(R.id.location);
        db = new SQLiteHandler(getApplicationContext());
        /**
         * Capture image button click event
         */
        btnsubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String storys = story.getText().toString().trim();
                String subjects = subject.getText().toString().trim();
                String locations = location.getText().toString().trim();
                String names = db.getUserDetails().get("name");
                String emails = db.getUserDetails().get("email").trim();
                if (!storys.isEmpty() && !subjects.isEmpty() && !locations.isEmpty()) {
                    reports(storys, subjects, locations,emails,names);
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter your details!", Toast.LENGTH_LONG).show();
                }

            }
        });
        btnCapturePicture = (Button) findViewById(R.id.btnRecordImage);
        btnRecordVideo = (Button) findViewById(R.id.btnRecordVideo);

        /**
         * Capture image button click event
         */
        btnCapturePicture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // capture picture
                String subjectsmades = subject.getText().toString().trim();
                String locationsmades = location.getText().toString().trim();
                try{
                    if (ActivityCompat.checkSelfPermission(Report.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(Report.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                    } else {
                        if (!subjectsmades.isEmpty() && !locationsmades.isEmpty()) {
                            captureImage();
                        } else {
                            Toast.makeText(getApplicationContext(), "Please enter your details that are exact like the subject and location you submitted!", Toast.LENGTH_LONG).show();
                        }
                    }

                }catch(Exception EX){
                    Toast.makeText(getApplicationContext(), EX.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

        /**
         * Record video button click event
         */
        btnRecordVideo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // record video
                String subjectsmades = subject.getText().toString().trim();
                String locationsmades = location.getText().toString().trim();
                try{
                    if (ActivityCompat.checkSelfPermission(Report.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(Report.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                    } else {
                        if (!subjectsmades.isEmpty() && !locationsmades.isEmpty()) {
                            recordVideo();
                        } else {
                            Toast.makeText(getApplicationContext(), "Please enter your details that are exact like the subject and location you submitted!", Toast.LENGTH_LONG).show();
                        }
                    }

                }catch(Exception EX){
                    Toast.makeText(getApplicationContext(), EX.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

        // Checking camera availability
        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(), "Sorry! Your device doesn't support camera", Toast.LENGTH_LONG).show();
            // will close the app if the device does't have camera
            finish();
        }

    }

    private void reports(final String story, final String subject, final String location,final String email,final String name) {
        // Tag used to cancel the request
        try {
            String tag_string_req = "req_register";

            pDialog.setMessage("Submitting ...");
            showDialog();
            StringRequest strReq = new StringRequest(Request.Method.POST,
                    AppConfig.URL_REPORT, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Register Response: " + response.toString());

                    hideDialog();

                    try {
                        JSONObject jObj = new JSONObject(response);
                        String err =jObj.getString("error");
                        if(err.contentEquals("You Already Submitted on this subject")) {
                            Toast.makeText(getApplicationContext(), "You Already Submitted on this subject", Toast.LENGTH_LONG).show();
                        }else if(err.contentEquals("Something Un Expected Happened, Try Again Later")) {
                            Toast.makeText(getApplicationContext(), "Something Un Expected Happened, Try Again Later", Toast.LENGTH_LONG).show();
                        }
                        else if(err.contentEquals("Correct Info")) {
                            // Inserting row in users table
                            Toast.makeText(getApplicationContext(), "Report successfully registered!", Toast.LENGTH_LONG).show();
                            // Launch login activity
                            //Intent intent = new Intent(Report.this, UploadImage.class);
                            //startActivity(intent);
                            //finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Registration Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_LONG).show();
                    hideDialog();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    // Posting params to register url
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("story", story);
                    params.put("subject", subject);
                    params.put("location", location);
                    params.put("email", email);
                    params.put("name", name);

                    return params;
                }

            };

            MySingleton.getmInstance(Report.this).addTorequestque(strReq);
        } catch (Exception Ex) {
            Toast.makeText(getApplicationContext(), Ex.getMessage().toString(), Toast.LENGTH_LONG);
        }
    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    private boolean isDeviceSupportCamera() {

        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    /**
     * Launching camera app to capture image
     */
    private void captureImage() {


        try{
            /*StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);*/
            final CharSequence[] options = {"Take Photo", "Choose From Gallery","Cancel"};
            android.support.v7.app.AlertDialog.Builder builders = new android.support.v7.app.AlertDialog.Builder(this);
            builders.setTitle("Select Option");
            builders.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (options[item].equals("Take Photo")) {
                        dialog.dismiss();
                      StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                      StrictMode.setVmPolicy(builder.build());
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        // start the image capture Intent
                        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);

                    } else if (options[item].equals("Choose From Gallery")) {
                        dialog.dismiss();
                        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                        StrictMode.setVmPolicy(builder.build());
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        //fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

                       // Uri selectedImage = intent.getData();;
                       // String imgPath = getRealPathFromURI(selectedImage);
                        //fileUri = Uri.parse(imgPath);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent,  CAMERA_CAPTURE_IMAGE_REQUEST_CODE1);
                    } else if (options[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builders.show();
        }catch(Exception EX){
            Toast.makeText(getApplicationContext(), EX.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Launching camera app to record video
     */
    private void recordVideo() {
        try{
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);

        // set video quality
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file
        // name
        // start the video capture Intent

        startActivityForResult(intent, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);
        }catch(Exception EX){
            Toast.makeText(getApplicationContext(), EX.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Here we store the file url as it will be null after returning from camera
     * app
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
try{
        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
}catch(Exception EX){
    Toast.makeText(getApplicationContext(), EX.getMessage().toString(), Toast.LENGTH_LONG).show();
}
    }



    /**
     * Receiving activity result method will be called after closing the camera
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        try{
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                
                launchUploadActivity(true);
            }
            else if (resultCode == RESULT_CANCELED) {

                // user cancelled Image capture
                Toast.makeText(getApplicationContext(), "User cancelled image capture", Toast.LENGTH_SHORT).show();

            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }

        }
        else if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE1) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();;
                String imgPath = getRealPathFromURI(selectedImage);
                fileUri = Uri.parse(imgPath);
                launchUploadActivity(true);
            }
            else if (resultCode == RESULT_CANCELED) {

                // user cancelled Image capture
                Toast.makeText(getApplicationContext(), "User cancelled image capture", Toast.LENGTH_SHORT).show();

            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }else if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // video successfully recorded
                // launching upload activity
                launchUploadActivity(false);

            } else if (resultCode == RESULT_CANCELED) {

                // user cancelled recording
                Toast.makeText(getApplicationContext(),
                        "User cancelled video recording", Toast.LENGTH_SHORT)
                        .show();

            } else {
                // failed to record video
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to record video", Toast.LENGTH_SHORT)
                        .show();
            }
        }
        }catch(Exception EX){
            Toast.makeText(getApplicationContext(), EX.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void launchUploadActivity(boolean isImage){
        String subjectsmade = subject.getText().toString().trim();
        String locationsmade = location.getText().toString().trim();
        try{
        Intent i = new Intent(Report.this, UploadActivity.class);
        i.putExtra("filePath", fileUri.getPath());
        i.putExtra("isImage", isImage);
        i.putExtra("subj",subjectsmade);
        i.putExtra("loc",locationsmade);
        startActivity(i);
        }catch(Exception EX){
            Toast.makeText(getApplicationContext(), EX.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * ------------ Helper Methods ----------------------
     * */

    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
   public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), AppConfig.IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create " + AppConfig.IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }
}