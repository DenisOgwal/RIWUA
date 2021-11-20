package com.example.cropprotection.Advise;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.cropprotection.R;
import com.example.cropprotection.loginandregistration.app.AppConfig;
import com.example.cropprotection.loginandregistration.helper.SQLiteHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class Advise extends AppCompatActivity implements AdviseRecyclerViewAdpter.adviseAdapterListener {
    private RecyclerView mList;
    private SQLiteHandler db;
    private SearchView searchView;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<SentAdvise> messageList;
    private AdviseRecyclerViewAdpter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advise);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        try {

            // white background notification bar
            //whiteNotificationBar(mList);
            mList = findViewById(R.id.main_list);
            db = new SQLiteHandler(getApplicationContext());
            messageList = new ArrayList<>();
            adapter = new AdviseRecyclerViewAdpter(this, messageList);

            linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

            mList.setHasFixedSize(true);
            mList.setLayoutManager(linearLayoutManager);
            mList.setItemAnimator(new DefaultItemAnimator());
            mList.addItemDecoration(dividerItemDecoration);
            mList.setAdapter(adapter);
        } catch (Exception EX) {
            Toast.makeText(Advise.this, EX.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
        getData();
    }

    //is helps in getting all the advise for display
    private void getData() {
        try {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(AppConfig.URL_ADVISE, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);

                            SentAdvise SentAdvise = new SentAdvise();
                            SentAdvise.setAttachmentid(jsonObject.getInt("Adviseid"));
                            SentAdvise.setSubject(jsonObject.getString("Subject"));
                            SentAdvise.setSender(jsonObject.getString("Sender"));
                            SentAdvise.setMessage(jsonObject.getString("Message"));
                            SentAdvise.settimesent(jsonObject.getString("Timesent"));
                            messageList.add(SentAdvise);
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
            Toast.makeText(Advise.this, EX.getMessage().toString(), Toast.LENGTH_LONG).show();
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
    public void onContactSelected(SentAdvise SentAdvise) {

    }
}
