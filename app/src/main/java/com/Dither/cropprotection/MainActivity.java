package com.Dither.cropprotection;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.Dither.cropprotection.Advise.Advise;
import com.Dither.cropprotection.BiocontrolAgents.BiocontrolAgents;
import com.Dither.cropprotection.Chat.ChatMain;
import com.Dither.cropprotection.CropDiseases.CropDiseases;
import com.Dither.cropprotection.Notifications.Notifications;
import com.Dither.cropprotection.Reporting.Report;
import com.Dither.cropprotection.SMS.Sms;
import com.Dither.cropprotection.Schedule.Schedule;
import com.Dither.cropprotection.ServiceProvider.ServiceProviders;
import com.Dither.cropprotection.Statistics.Statistics;
import com.Dither.cropprotection.WaterBills.WaterBillPayments;
import com.Dither.cropprotection.WaterBills.WaterBills;
import com.Dither.cropprotection.loginandregistration.helper.SQLiteHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private CardView reports, notifications, providers, sms, meetings, paywaterbills;
    private TextView username;
    private SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChatMain.class);
                startActivity(intent);

            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_WIFI_STATE}, 1);
        } else {

        }
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 1);
        } else {

        }
        if (!isConnected(this)) {
            showCustomDialog();
        }
        reports = (CardView) findViewById(R.id.report);
        notifications = (CardView) findViewById(R.id.notifications);
        providers = (CardView) findViewById(R.id.providers);
        sms = (CardView) findViewById(R.id.sms);
        meetings = (CardView) findViewById(R.id.meetings);
        paywaterbills = (CardView) findViewById(R.id.paywaterbills);
        db = new SQLiteHandler(getApplicationContext());
        View headerView = navigationView.getHeaderView(0);
        username = (TextView) headerView.findViewById(R.id.usernames);
        try {
            //username.setText("");
            String usersname = db.getUserDetails().get("name");
            username.setText(usersname);
        } catch (Exception EX) {
            EX.printStackTrace();
        }
        reports.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // capture picture
                Intent intent = new Intent(MainActivity.this, Report.class);
                startActivity(intent);
                //finish();
            }
        });
        notifications.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // capture picture
                try {
                    Intent intent = new Intent(MainActivity.this, Notifications.class);
                    startActivity(intent);
                } catch (Exception EX) {
                    Toast.makeText(MainActivity.this, EX.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
                //finish();
            }
        });
        providers.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // capture picture
                Intent intent = new Intent(MainActivity.this, ServiceProviders.class);
                startActivity(intent);
                //finish();
            }
        });
        sms.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // capture picture
                Intent intent = new Intent(MainActivity.this, Sms.class);
                startActivity(intent);
                //finish();
            }
        });
        meetings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // capture picture
                Intent intent = new Intent(MainActivity.this, Schedule.class);
                startActivity(intent);
                //finish();
            }
        });
        paywaterbills.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // capture picture
                Intent intent = new Intent(MainActivity.this, WaterBills.class);
                startActivity(intent);
                //finish();
            }
        });
    }

    private void showCustomDialog() {
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(MainActivity.this);
        alertdialog.setMessage("Your Not connected to internet, Please Connect to Proceed").setCancelable(false).setPositiveButton("Connect", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_DATA_USAGE_SETTINGS));
                finish();
            }
        });

    }

    private boolean isConnected(MainActivity mainActivity) {
        ConnectivityManager connectivitymanager = (ConnectivityManager) mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wificon = connectivitymanager.getNetworkInfo(connectivitymanager.TYPE_WIFI);
        NetworkInfo mobilecon = connectivitymanager.getNetworkInfo(connectivitymanager.TYPE_MOBILE);
        if ((wificon != null && wificon.isConnected()) || (mobilecon != null && mobilecon.isConnected())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logsout) {
            try {
                db.deleteUsers();
                SharedPreferences settings = getApplicationContext().getSharedPreferences("AndroidHiveLogin", getApplicationContext().MODE_PRIVATE);
                settings.edit().clear().commit();

                SharedPreferences setting = getApplicationContext().getSharedPreferences("androidhive-welcome", getApplicationContext().MODE_PRIVATE);
                setting.edit().clear().commit();
                Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                startActivity(intent);
                finish();
            } catch (Exception EX) {
                Toast.makeText(MainActivity.this, EX.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
            return true;
        } else if (id == R.id.action_notifications) {
            Intent intent = new Intent(MainActivity.this, Notification.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.advise) {
            try {
                Intent intent = new Intent(MainActivity.this, Advise.class);
                startActivity(intent);
            } catch (Exception EX) {
                Toast.makeText(MainActivity.this, EX.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.payments) {
            try {
                Intent intent = new Intent(MainActivity.this, WaterBillPayments.class);
                startActivity(intent);
            } catch (Exception EX) {
                Toast.makeText(MainActivity.this, EX.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.biocontrol) {
            try {
                Intent intent = new Intent(MainActivity.this, BiocontrolAgents.class);
                startActivity(intent);
            } catch (Exception EX) {
                Toast.makeText(MainActivity.this, EX.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.diseases) {
            try {
                Intent intent = new Intent(MainActivity.this, CropDiseases.class);
                startActivity(intent);
            } catch (Exception EX) {
                Toast.makeText(MainActivity.this, EX.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.stats) {
            try {
                Intent intent = new Intent(MainActivity.this, Statistics.class);
                startActivity(intent);
            } catch (Exception EX) {
                Toast.makeText(MainActivity.this, EX.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            Intent intent = new Intent(MainActivity.this, ChatMain.class);
            startActivity(intent);
        } else if (id == R.id.logout) {
            try {
                db.deleteUsers();
                SharedPreferences settings = getApplicationContext().getSharedPreferences("AndroidHiveLogin", getApplicationContext().MODE_PRIVATE);
                settings.edit().clear().commit();

                SharedPreferences setting = getApplicationContext().getSharedPreferences("androidhive-welcome", getApplicationContext().MODE_PRIVATE);
                setting.edit().clear().commit();
                Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                startActivity(intent);
                finish();
            } catch (Exception EX) {
                Toast.makeText(MainActivity.this, EX.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
