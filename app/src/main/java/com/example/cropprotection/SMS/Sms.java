package com.example.cropprotection.SMS;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import android.widget.Toast;

import com.example.cropprotection.R;

public class Sms extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loadFragment(new MessageFragment());
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
    }
    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.navigation_home:
                try {
                    fragment = new MessageFragment();
                    break;
                }catch(Exception Ex){
                    Toast.makeText(Sms.this, Ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.navigation_dashboard:
                try {
                    fragment = new sentFragment();
                    break;
                }catch(Exception Ex){
                    Toast.makeText(Sms.this, Ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.navigation_notifications:
                try {
                    fragment = new RecievedSMSFragment();
                    break;
                }catch(Exception Ex){
                    Toast.makeText(Sms.this, Ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return loadFragment(fragment);
    }
}
