package com.example.cropprotection.WaterBills;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
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
import com.example.cropprotection.loginandregistration.helper.SQLiteHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WaterBillDetails extends AppCompatActivity {
    String meterno = null;
    String billingid = null;
    String callstring = null;
    private SQLiteHandler db;
    TextView billno, meterreading, waterunits, amountpayable, amountpaid, balance, billingdate;
    Button btnairtel, btnmtn;
    private ProgressDialog pDialog;
    public static final int progress_bar_type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_bill_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        try {
            db = new SQLiteHandler(getApplicationContext());
            billno = (TextView) findViewById(R.id.billno);
            meterreading = (TextView) findViewById(R.id.meterreading);
            waterunits = (TextView) findViewById(R.id.waterunits);
            amountpayable = (TextView) findViewById(R.id.amountpayable);
            amountpaid = (TextView) findViewById(R.id.amountpaid);
            balance = (TextView) findViewById(R.id.balance);
            billingdate = (TextView) findViewById(R.id.billingdate);
            btnairtel = (Button) findViewById(R.id.btnairtel);
            btnmtn = (Button) findViewById(R.id.btnmtn);
            meterno = db.getUserDetails().get("meterno");
            try {
                Bundle extras = getIntent().getExtras();
                billingid = extras.getString("billingid");
            } catch (Exception EX) {
                Toast.makeText(WaterBillDetails.this, EX.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception EX) {
            Toast.makeText(WaterBillDetails.this, EX.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
        getData();

        btnmtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    String UssdCode = "*165*3#";
                    //*185*9# Airtel
                    //check if its a valid ussd code
                    if (UssdCode.startsWith("*") && UssdCode.endsWith("#")) {

//we want to remove the last # from the ussd code as we need to encode it. so *555# becomes *555
                        UssdCode = UssdCode.substring(0, UssdCode.length() - 1);

                        String UssdCodeNew = UssdCode + Uri.encode("#");

//request for permission
                        if (ActivityCompat.checkSelfPermission(WaterBillDetails.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(WaterBillDetails.this, new String[]{Manifest.permission.CALL_PHONE}, 1);

                        } else {
//dial Ussd code
                            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + UssdCodeNew)));

                        }


                    } else {
                        Toast.makeText(WaterBillDetails.this, "Please enter a valid ussd code", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception EX) {
                    Toast.makeText(WaterBillDetails.this, EX.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }

        });
        btnairtel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    String UssdCode = "*185*9#";
                    //*185*9# Airtel
                    //check if its a valid ussd code
                    if (UssdCode.startsWith("*") && UssdCode.endsWith("#")) {

//we want to remove the last # from the ussd code as we need to encode it. so *555# becomes *555
                        UssdCode = UssdCode.substring(0, UssdCode.length() - 1);

                        String UssdCodeNew = UssdCode + Uri.encode("#");

//request for permission
                        if (ActivityCompat.checkSelfPermission(WaterBillDetails.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(WaterBillDetails.this, new String[]{Manifest.permission.CALL_PHONE}, 1);

                        } else {
//dial Ussd code
                            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + UssdCodeNew)));

                        }


                    } else {
                        Toast.makeText(WaterBillDetails.this, "Please enter a valid ussd code", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception EX) {
                    Toast.makeText(WaterBillDetails.this, EX.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    private void getData() {
        try {
            String URL_PROVIDERS_SINGLE = "https://riwua.org/AndroidFiles/WaterBillsingle.php?meterno=" + meterno + "&billno=" + billingid;
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_PROVIDERS_SINGLE, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            int me = jsonObject.getInt("IDDesc");
                            String billnos = "";
                            if (jsonObject.getInt("IDDesc") < 10) {
                                billnos = "#00";
                            } else if (jsonObject.getInt("IDDesc") < 100) {
                                billnos = "#0";
                            } else {
                                billnos = "#";
                            }
                            billno.setText("Bill No: " + billnos + jsonObject.getInt("IDDesc"));
                            meterreading.setText("Meter Reading: " + jsonObject.getString("MeterReading"));
                            waterunits.setText("Water Units: " + jsonObject.getString("WaterUnits"));
                            amountpayable.setText("Amount Payable: " + jsonObject.getString("AmountPayable"));
                            amountpaid.setText("Amount Paid: " + jsonObject.getString("AmountPaid"));
                            balance.setText("Balance: " + jsonObject.getString("Balance"));
                            billingdate.setText("Billing Date: " + jsonObject.getString("BillingDate"));

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
            Toast.makeText(WaterBillDetails.this, EX.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }
}