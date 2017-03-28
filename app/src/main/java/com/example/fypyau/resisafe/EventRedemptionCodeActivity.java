package com.example.fypyau.resisafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EventRedemptionCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redemption_code);

        TextView tvRedCode = (TextView) findViewById(R.id.tvRedCode);
        TextView eventnameX = (TextView) findViewById(R.id.eventname);
        TextView startdateX = (TextView) findViewById(R.id.startdate);
        TextView enddateX = (TextView) findViewById(R.id.enddate);

        Button btnOK = (Button) findViewById(R.id.btnOK);

        Intent intent = getIntent();
        String eventname = intent.getStringExtra("eventname");
        String eventlocation = intent.getStringExtra("eventlocation");
        String startdate = intent.getStringExtra("startdate");
        String enddate = intent.getStringExtra("enddate");
        String starttime= intent.getStringExtra("starttime");
        String endtime = intent.getStringExtra("endtime");
        String eventcode = intent.getStringExtra("eventcode");
        String eventmode = intent.getStringExtra("eventmode");
        final int ruserid = intent.getIntExtra("ruserid", -1);

        tvRedCode.setText(eventcode);
        eventnameX.setText(eventname);
        startdateX.setText(startdate + ", " +  starttime);
        enddateX.setText(enddate+ ", " +  endtime);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backintent = new Intent(EventRedemptionCodeActivity.this,ResiNavigatorMainActivity.class);
                backintent.putExtra("ruserid",ruserid);
                EventRedemptionCodeActivity.this.startActivity(backintent);
                finish();
            }
        });
    }
}
