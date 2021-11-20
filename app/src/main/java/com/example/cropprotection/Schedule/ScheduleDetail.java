package com.example.cropprotection.Schedule;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.cropprotection.Advise.AdviseDetail;
import com.example.cropprotection.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ScheduleDetail extends AppCompatActivity {
    String ids=null;
    TextView schedulesubject,venuetext,fromdatetext,todatetext,starttimetext,endtimetext,descriptiontext;
    private ProgressDialog pDialog;
    public static final int progress_bar_type = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        schedulesubject = (TextView) findViewById(R.id.schedulesubject);
        venuetext = (TextView) findViewById(R.id.venuetext);
        fromdatetext = (TextView) findViewById(R.id.fromdatetext);
        todatetext = (TextView) findViewById(R.id.todatetext);
        starttimetext = (TextView) findViewById(R.id.starttimetext);
        endtimetext = (TextView) findViewById(R.id.endtimetext);
        descriptiontext = (TextView) findViewById(R.id.descriptiontext);

        try {
            Bundle extras = getIntent().getExtras();
            ids = extras.getString("ids");
        } catch (Exception EX) {
            Toast.makeText(ScheduleDetail.this, EX.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
        getData();
    }
    private void getData() {
        try {
            String URL_PROVIDERS_SINGLE = "https://riwua.org/AndroidFiles/Schedulesingle.php?ids="+ids;
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_PROVIDERS_SINGLE, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            int me=jsonObject.getInt("IDs");
                            schedulesubject.setText(jsonObject.getString("Subject"));
                            venuetext.setText(jsonObject.getString("Venue"));
                            fromdatetext.setText(jsonObject.getString("FromDate"));
                            todatetext.setText(jsonObject.getString("ToDate"));
                            starttimetext.setText(jsonObject.getString("StartingTime"));
                            endtimetext.setText(jsonObject.getString("EndingTime"));
                            descriptiontext.setText(jsonObject.getString("Description"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                    progressDialog.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(jsonArrayRequest);
        } catch (Exception EX) {
            Toast.makeText(ScheduleDetail.this, EX.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }
}