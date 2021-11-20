package com.example.cropprotection.ServiceProvider;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.cropprotection.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProviderDetail extends AppCompatActivity {
    String providerid=null;
    TextView providernames,providercontacts,provideremails,providerservices,descriptiontext;
    ImageView thumbnails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        providernames=(TextView) findViewById(R.id.providernames);
        providercontacts=(TextView) findViewById(R.id.providercontacts);
        provideremails=(TextView) findViewById(R.id.provideremails);
        providerservices=(TextView) findViewById(R.id.providerservices);
        descriptiontext=(TextView) findViewById(R.id.descriptiontext);
        thumbnails=(ImageView)findViewById(R.id.thumbnails);
        try{
        Bundle extras = getIntent().getExtras();
             providerid = extras.getString("providerid");
        } catch (Exception EX) {
            Toast.makeText(ProviderDetail.this, EX.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
        getData();
    }
    private void getData() {
        try {
            String URL_PROVIDERS_SINGLE = "https://riwua.org/AndroidFiles/serviceproviderssingle.php?providerid="+providerid;
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_PROVIDERS_SINGLE, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            int me=jsonObject.getInt("providerid");
                            providernames.setText(jsonObject.getString("providername"));
                            providercontacts.setText(jsonObject.getString("contact"));
                            provideremails.setText(jsonObject.getString("email"));
                            providerservices.setText(jsonObject.getString("services"));
                            descriptiontext.setText(jsonObject.getString("description"));
                            String imageurl=jsonObject.getString("imageurl");
                            Glide.with(getApplicationContext()).load(imageurl).apply(RequestOptions.circleCropTransform()).into( thumbnails);
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
            Toast.makeText(ProviderDetail.this, EX.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }
}
