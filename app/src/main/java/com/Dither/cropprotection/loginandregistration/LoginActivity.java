package com.Dither.cropprotection.loginandregistration;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.Dither.cropprotection.MainActivity;
import com.Dither.cropprotection.R;
import com.Dither.cropprotection.activity.RegisterActivity;
import com.Dither.cropprotection.loginandregistration.app.AppConfig;
import com.Dither.cropprotection.loginandregistration.app.MySingleton;
import com.Dither.cropprotection.loginandregistration.helper.SQLiteHandler;
import com.Dither.cropprotection.loginandregistration.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnLogin;
    private Button btnLinkToRegister;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                try{
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                // Check for empty data in the form
                if (!email.isEmpty() && !password.isEmpty()) {
                    // login user
                    checkLogin(email, password);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }
                } catch (Exception Ex) {
                    Toast.makeText(getApplicationContext(), Ex.getMessage().toString(), Toast.LENGTH_LONG);
                }
            }

        });

        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    /**
     * function to verify login details in mysql db
     * */
    private void checkLogin(final String email, final String password) {
        // Tag used to cancel the request
        try{
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    String err =jObj.getString("error");

                    if(err.contentEquals("Password User Name Do not Match any Account")) {
                        Toast.makeText(getApplicationContext(), "Password User Name Do not Match any Account", Toast.LENGTH_LONG).show();
                    }else if(err.contentEquals("Something Un Expected Happened, Try Again Later")) {
                    Toast.makeText(getApplicationContext(), "Something Un Expected Happened, Try Again Later", Toast.LENGTH_LONG).show();
                }else if(err.contentEquals("Email or Meter No. Do not Match any Account")) {
                    Toast.makeText(getApplicationContext(), "Email or Meter No. Do not Match any Account", Toast.LENGTH_LONG).show();
                }
                    else if(err.contentEquals("Correct Info")) {
                        session.setLogin(true);
                        String uid = jObj.getString("uid");
                        String name = jObj.getString("name");
                        String email =jObj.getString("email");
                        String created_at = jObj.getString("created_at");
                        String phone = jObj.getString("phone");
                        String regimeterno = jObj.getString("meterno");
                        // Inserting row in users table
                        db.addUser(name, email, uid, created_at,phone,regimeterno);

                        // Launch main activity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        MySingleton.getmInstance(LoginActivity.this).addTorequestque(strReq);
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
}
