package com.example.fypyau.resisafe;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ResiWlcmActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    public static String profileimage;
    public static int ruserid;
    public static String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resi_wlcm);

        final TextView tvName = (TextView) findViewById(R.id.tvName);
        final TextView tvWelcomeMsg = (TextView) findViewById(R.id.tvWelcomeMsg);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        ruserid = intent.getIntExtra("ruserid",-1);
        profileimage = intent.getStringExtra("profileimage");

        String message = "Welcome to ResiSafe!";
        tvWelcomeMsg.setText(message);
        tvName.setText(name);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {

                Intent i = new Intent(ResiWlcmActivity.this, ResiNavigatorMainActivity.class);
                startActivity(i);


                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
