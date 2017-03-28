package com.example.fypyau.resisafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ResiEventDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resi_event_details);

        final TextView tvEventName = (TextView) findViewById(R.id.eventname);
        final TextView tvEventLocation = (TextView) findViewById(R.id.eventlocation);
        final TextView tvEventDescription = (TextView) findViewById(R.id.eventdescription);
        final TextView date = (TextView) findViewById(R.id.date);
        final TextView time = (TextView) findViewById(R.id.time);
        final TextView eventcreator = (TextView) findViewById(R.id.eventcreator);
        final TextView viewall = (TextView) findViewById(R.id.viewall);
        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.imgback);




        Intent i = getIntent();
        Event eventdata = (Event) i.getSerializableExtra("eventdata");


        if (eventdata.getEventLocation().equals("Swimming Pool")){
            layout.setBackgroundResource(R.drawable.detailspool);
        } else if (eventdata.getEventLocation().equals("Gym")){
            layout.setBackgroundResource(R.drawable.detailsgym);
        } else if (eventdata.getEventLocation().equals("Badminton Court")){
            layout.setBackgroundResource(R.drawable.detailscourt);
        } else if (eventdata.getEventLocation().equals("Public Park")){
            layout.setBackgroundResource(R.drawable.detailspark);
        }
        else
            layout.setBackgroundResource(R.drawable.detailshouse);

        tvEventName.setText(eventdata.getEventName());
        tvEventLocation.setText(eventdata.getEventLocation());
        tvEventDescription.setText(eventdata.getEventDescription());
        date.setText( eventdata.getEventstartdate() + " to " + eventdata.getEventenddate());
        time.setText( eventdata.getEventstarttime() + " to " + eventdata.getEventendtime());
        eventcreator.setText("Event Creator: " + "Yau Yat Way");

        viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
