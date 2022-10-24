package com.Dither.cropprotection.SMS;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class sentFragment extends Fragment {

    private RecyclerView mList;
    private SQLiteHandler db;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<SentMessage> messageList;
    private RecyclerView.Adapter adapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sent_list, container, false);

        mList = view.findViewById(R.id.list);
        db = new SQLiteHandler(getActivity());
        messageList= new ArrayList<>();
        adapter = new MysentRecyclerViewAdapter(getContext(),messageList);

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);

        getData();
        return view;
    }
    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String emails = db.getUserDetails().get("email").trim();
         String URL_SMSRETRIEVE = "https://riwua.org/AndroidFiles/retrievesms.php?email="+emails;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_SMSRETRIEVE, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        SentMessage sentmessage = new SentMessage();
                        sentmessage.setSender(jsonObject.getString("Sender"));
                        sentmessage.setMessage(jsonObject.getString("Message"));
                        sentmessage.settimesent(jsonObject.getString("Timesent"));

                        messageList.add(sentmessage);
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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());requestQueue.add(jsonArrayRequest);
    }
}