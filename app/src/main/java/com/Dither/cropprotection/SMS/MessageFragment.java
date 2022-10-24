package com.Dither.cropprotection.SMS;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.Dither.cropprotection.R;
import com.Dither.cropprotection.loginandregistration.app.AppConfig;
import com.Dither.cropprotection.loginandregistration.app.MySingleton;
import com.Dither.cropprotection.loginandregistration.helper.SQLiteHandler;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MessageFragment extends Fragment {

    private Button btnsubmit;
    private EditText messagecontact,messagedraft;
    private ProgressDialog pDialog;
    private SQLiteHandler db;
    HttpClient client;
    HttpPost post;

    private RecyclerView mList;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<SentContacts> messageList;
    private RecyclerView.Adapter adapter;
    public MessageFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_message, null);
        try{
        btnsubmit= (Button) v.findViewById(R.id.btnsubmit);
        messagecontact= (EditText) v.findViewById(R.id.messagecontact);
        messagedraft= (EditText) v.findViewById(R.id.messagedraft);
        db = new SQLiteHandler(getActivity());
        pDialog = new ProgressDialog(getContext());
        pDialog.setCancelable(false);

        mList = v.findViewById(R.id.main_list);
        db = new SQLiteHandler(getActivity());
        messageList= new ArrayList<>();
        adapter = new ContactsRecyclerViewAdapter(getContext(),messageList,getFragmentManager());

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);
            getData();
            Bundle bundle=getArguments();
            String strtext = bundle.getString("contact");
                messagecontact.setText(strtext);


        btnsubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {


                String message = messagedraft.getText().toString().trim();
                String contact = messagecontact.getText().toString().trim();
                String names = db.getUserDetails().get("name");
                String emails = db.getUserDetails().get("email").trim();
                if (!message.isEmpty() && !contact.isEmpty()) {
                    Submitdata(message, contact,emails,names);
                    Submitdata();
                } else {
                    Toast.makeText(getActivity(), "Please enter your details!", Toast.LENGTH_LONG).show();
                }
            }
        });

    } catch (Exception Ex) {
        //Submitdata();
        Toast.makeText(getActivity(), Ex.getMessage().toString(), Toast.LENGTH_LONG);
    }
        return v;
    }
    private void Submitdata(final String message, final String contact,final String email,final String name) {
        // Tag used to cancel the request
        try {
            String tag_string_req = "req_register";

            pDialog.setMessage("Submitting ...");
            showDialog();
            StringRequest strReq = new StringRequest(Request.Method.POST,
                    AppConfig.URL_SMSSEND, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    hideDialog();

                    try {
                        JSONObject jObj = new JSONObject(response);
                        String err =jObj.getString("error");
                        if(err.contentEquals("You Already Submitted on this SMS")) {
                            Toast.makeText(getActivity(), "You Already Submitted SMS", Toast.LENGTH_LONG).show();
                        }else if(err.contentEquals("Something Un Expected Happened, Try Again Later")) {
                            Toast.makeText(getActivity(), "Something Un Expected Happened, Try Again Later", Toast.LENGTH_LONG).show();
                        }
                        else if(err.contentEquals("Correct Info")) {
                            Toast.makeText(getActivity(), "SMS sent registered!", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                    //Toast.makeText(getActivity(), "Cant submit, Vollley problem", Toast.LENGTH_LONG).show();
                    hideDialog();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    // Posting params to register url
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("message", message);
                    params.put("contact", contact);
                    params.put("email", email);
                    params.put("name", name);

                    return params;
                }

            };

            MySingleton.getmInstance(getContext()).addTorequestque(strReq);
        } catch (Exception Ex) {
            //Submitdata();
            Toast.makeText(getActivity(), Ex.getMessage().toString(), Toast.LENGTH_LONG);
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
    private void Submitdata() {
        try {
            String message = messagedraft.getText().toString().trim();
            String contact = messagecontact.getText().toString().trim();
            RequestQueue queue = Volley.newRequestQueue(getContext());
            String url ="http://geniussmsgroup.com/api/http/messagesService/get?username=riwua&password=Jesus@lord1&senderid=Geniussms&message="+ message+ "&numbers=" + contact;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            queue.add(stringRequest);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

    }
    private void getData() {
        String contacts = messagecontact.getText().toString().trim();
        if(contacts.matches("")) {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(AppConfig.URL_CONTACTS, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);

                            SentContacts SentContacts = new SentContacts();
                            SentContacts.setnames(jsonObject.getString("FullNames"));
                            SentContacts.settelephone(jsonObject.getString("Telephone"));
                            SentContacts.setprofilepic(jsonObject.getString("ProfilePic"));

                            messageList.add(SentContacts);
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
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(jsonArrayRequest);
        }
        else {
        }
    }
}
