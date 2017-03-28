package com.example.fypyau.resisafe;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EventLoadingSplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_loading_splash);

        Intent intent = getIntent();
        final String eventname = intent.getStringExtra("eventname");
        final String eventlocation = intent.getStringExtra("eventlocation");
        final String startdate = intent.getStringExtra("startdate");
        final String enddate = intent.getStringExtra("enddate");
        final String starttime= intent.getStringExtra("starttime");
        final String endtime = intent.getStringExtra("endtime");
        final String eventcode = intent.getStringExtra("eventcode");
        final String eventmode = intent.getStringExtra("eventmode");
        final int ruserid = intent.getIntExtra("ruserid", -1);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent registersuccessintent = new Intent(EventLoadingSplashActivity.this, EventRedemptionCodeActivity.class);
                registersuccessintent.putExtra("eventname",eventname);
                registersuccessintent.putExtra("eventlocation",eventlocation);
                registersuccessintent.putExtra("startdate",startdate);
                registersuccessintent.putExtra("enddate",enddate);
                registersuccessintent.putExtra("starttime",starttime);
                registersuccessintent.putExtra("endtime",endtime);
                registersuccessintent.putExtra("eventmode",eventmode);
                registersuccessintent.putExtra("eventcode",eventcode);
                registersuccessintent.putExtra("ruserid",ruserid);

                EventLoadingSplashActivity.this.startActivity(registersuccessintent);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);


    }
}
