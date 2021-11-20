package com.example.cropprotection.service;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.example.cropprotection.Config;
import com.example.cropprotection.PrefManager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();
    private PrefManager prefManager;

    @Override
    public void onTokenRefresh() {
        try {
            super.onTokenRefresh();
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();

            // Saving reg id to shared preferences
            storeRegIdInPref(refreshedToken);

            // sending reg id to your server
            sendRegistrationToServer(refreshedToken);
          //  prefManager.saveDeviceToken(refreshedToken);
            // Notify UI that registration has completed, so the progress indicator can be hidden.
            Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
            registrationComplete.putExtra("token", refreshedToken);
            LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
        }catch(Exception Ex){
            Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
        }
    }

    private void sendRegistrationToServer(final String token) {
        // sending gcm token to server
        Log.e(TAG, "sendRegistrationToServer: " + token);
    }

   private void storeRegIdInPref(String token) {
       try {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId", token);
        editor.commit();
   }catch(Exception Ex){
        Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
    }
    }
}
