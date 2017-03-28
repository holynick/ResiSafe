package com.example.fypyau.resisafe;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class VisiWlcmActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;

    public static String profileimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visi_wlcm);

        final TextView tvName = (TextView) findViewById(R.id.tvName);
        final TextView tvWelcomeMsg = (TextView) findViewById(R.id.tvWelcomeMsg);

        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        final int vuserid = intent.getIntExtra("vuserid",-1);
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
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(VisiWlcmActivity.this, VisiNavigatorMainActivity.class);
                i.putExtra("name",name);
                i.putExtra("vuserid",vuserid);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);


    }
}
