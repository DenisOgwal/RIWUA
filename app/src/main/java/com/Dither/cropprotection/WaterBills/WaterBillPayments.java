package com.Dither.cropprotection.WaterBills;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.Dither.cropprotection.R;
import com.Dither.cropprotection.loginandregistration.helper.SQLiteHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class WaterBillPayments extends AppCompatActivity implements WaterBillPaymentsRecyclerViewAdpter.WaterBillPaymentAdapterListener {
private RecyclerView mList;
private SQLiteHandler db;
private SearchView searchView;
private LinearLayoutManager linearLayoutManager;
private DividerItemDecoration dividerItemDecoration;
private List<SentWaterBillPayments> messageList;
private WaterBillPaymentsRecyclerViewAdpter adapter;
String meterno=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_bill_payments);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        try {
            mList = findViewById(R.id.waterbillpayments_list);
            db = new SQLiteHandler(getApplicationContext());
            messageList = new ArrayList<>();
            adapter = new WaterBillPaymentsRecyclerViewAdpter(this, messageList);

            linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

            mList.setHasFixedSize(true);
            mList.setLayoutManager(linearLayoutManager);
            mList.setItemAnimator(new DefaultItemAnimator());
            mList.addItemDecoration(dividerItemDecoration);
            mList.setAdapter(adapter);
            meterno = db.getUserDetails().get("meterno");
            //Toast.makeText(WaterBills.this, meterno, Toast.LENGTH_SHORT).show();
        } catch (Exception EX) {
            Toast.makeText(WaterBillPayments.this, EX.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
        getData();
    }
    private void getData() {
        try {
            String URL_PROVIDERS_SINGLE = "https://riwua.org/AndroidFiles/WaterbillPayments.php?meterno="+meterno;
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_PROVIDERS_SINGLE, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);

                            SentWaterBillPayments SentWaterBillPayments = new SentWaterBillPayments();
                            SentWaterBillPayments.setpaymentid(jsonObject.getInt("IDs"));
                            SentWaterBillPayments.setBillNumber(jsonObject.getInt("BillNo"));
                            SentWaterBillPayments.setamountpaid(jsonObject.getString("AmountPaid"));
                            SentWaterBillPayments.setPaidBy(jsonObject.getString("PaidBy"));
                            SentWaterBillPayments.setContact(jsonObject.getString("Contact"));
                            SentWaterBillPayments.setPaymentDate(jsonObject.getString("PaymentDate"));
                            messageList.add(SentWaterBillPayments);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                    adapter.notifyDataSetChanged();
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
            Toast.makeText(WaterBillPayments.this, EX.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchfilter, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                adapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.GREEN);
        }
    }
    @Override
    public void onContactSelected(SentWaterBillPayments SentWaterBillPayments) {

    }
}