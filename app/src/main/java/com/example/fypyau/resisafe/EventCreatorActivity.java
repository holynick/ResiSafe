package com.example.fypyau.resisafe;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class EventCreatorActivity extends AppCompatActivity {
    TextView btnStartTime, btnEndTime, tvTitle, tvShowHouseNum;
    Button btnEventRegister;
    EditText etEventName, etEventDescription;
    String houseNum;
    ProgressDialog progressDialog;
    private int monthX, dayX, yearX, hourX, minuteX;
    static final int DILOG3_ID = 2, DILOG4_ID = 3;
    private String date, StartTimeX = "", EndTimeX = "";
    private String eventmode;
    private Spinner locationspinner;
    private String UPDATEURL = "https://resisafe.000webhostapp.com/UpdateEventDateResiSafe.php?id=";
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
    Date cstarttime, cendtime;
    RadioGroup rg;
    RadioButton rb;
    boolean valid = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creator);


        btnStartTime = (TextView) findViewById(R.id.btnStartTime);
        btnEndTime = (TextView) findViewById(R.id.btnEndTime);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvShowHouseNum = (TextView) findViewById(R.id.tvShowHouseNum);
        btnEventRegister = (Button) findViewById(R.id.btnEventRegister);
        etEventName = (EditText) findViewById(R.id.etEventName);
        locationspinner = (Spinner) findViewById(R.id.locationspinner);
        etEventDescription = (EditText) findViewById(R.id.etEventDescription);
        progressDialog = new ProgressDialog(this);



        locationspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selecteditem = locationspinner.getSelectedItem().toString();

                if(selecteditem.equals("House")){
                    final EditText etRoom = new EditText(EventCreatorActivity.this);
                    etRoom.setInputType(InputType.TYPE_CLASS_TEXT);
                    AlertDialog.Builder builder = new AlertDialog.Builder(EventCreatorActivity.this);
                    builder.setTitle("Please enter house number");
                    builder.setView(etRoom);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            houseNum = etRoom.getText().toString();
                            if (houseNum.equals(""))
                                Toast.makeText(getBaseContext(), "Please enter a valid house number", Toast.LENGTH_SHORT).show();
                            else {
                                dialog.dismiss();
                                tvShowHouseNum.setText("You have selected house number " + houseNum + ".");
                                tvShowHouseNum.setVisibility(View.VISIBLE);
                            }

                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            etRoom.setText("");
                            locationspinner.setSelection(0);
                            tvShowHouseNum.setVisibility(View.GONE);
                        }
                    });
                    builder.show();
                } else
                    tvShowHouseNum.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Typeface custom_font = Typeface.createFromAsset(tvTitle.getContext().getAssets(),  "fonts/Bariol.ttf");
        tvTitle.setTypeface(custom_font);
        btnStartTime.setTypeface(custom_font);
        btnEndTime.setTypeface(custom_font);
        etEventDescription.setTypeface(custom_font);

        rg = (RadioGroup) findViewById(R.id.eventmodeRadio);
        String dayString, monthString;
        final Calendar cal = Calendar.getInstance();
        dayX = cal.get(Calendar.DAY_OF_MONTH);
        monthX = cal.get(Calendar.MONTH) + 1;
        yearX = cal.get(Calendar.YEAR);
        if (dayX < 10)
            dayString = "0" + dayX;
        else
            dayString = Integer.toString(dayX);
        if (monthX < 10)
            monthString = "0" + monthX;
        else
            monthString = Integer.toString(monthX);
        date = dayString + "/" + monthString + "/" + yearX;
        showDialogButtonClick();

        btnEventRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selecteditem = locationspinner.getSelectedItem().toString();
                final String eventName = etEventName.getText().toString();
                final String eventLocation;
                if (selecteditem.equals("House")) {
                    eventLocation = selecteditem + " " + houseNum;
                } else {
                    eventLocation = selecteditem;
                }
                final String eventDescription = etEventDescription.getText().toString();
                final String StartDate = date;
                final String EndDate = date;
                final String StartTime = StartTimeX;
                final String EndTime = EndTimeX;
                int radiobuttonid = rg.getCheckedRadioButtonId();
                rb = (RadioButton) findViewById(radiobuttonid);
                eventmode = rb.getText().toString();
                Random rnd = new Random();
                final int EventCodeINT = 100000 + rnd.nextInt(900000);
                final int ruserid = ResiWlcmActivity.ruserid;
                final String eventcode = Integer.toString(EventCodeINT);
                try {
                    cstarttime = formatter.parse(StartTimeX);
                    cendtime = formatter.parse(EndTimeX);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                valid = true;

                if (eventName.equals("")) {
                    etEventName.setError("Please enter the event name");
                    valid = false;
                }
                if (eventDescription.equals("")) {
                    etEventDescription.setError("Please enter the event description");
                    valid = false;
                }
                if (StartTimeX.isEmpty() || EndTimeX.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EventCreatorActivity.this);
                    builder.setTitle("Date Error");
                    builder.setMessage("Please specify the starting and ending time");
                    builder.setNegativeButton("OK", null);
                    builder.show();
                    valid = false;
                }
                else if (cendtime.compareTo(cstarttime) < 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EventCreatorActivity.this);
                    builder.setTitle("Date Error");
                    builder.setMessage("An error has occured in the specified time period.");
                    builder.setNegativeButton("OK", null);
                    builder.show();
                    valid = false;
                }
                if (valid) {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject JSONresponse = new JSONObject(response);
                                boolean success = JSONresponse.getBoolean("success");
                                progressDialog.dismiss();

                                if (success) {
                                    Intent registersuccessintent = new Intent(EventCreatorActivity.this, EventLoadingSplashActivity.class);
                                    registersuccessintent.putExtra("eventname", eventName);
                                    registersuccessintent.putExtra("eventlocation", eventLocation);
                                    registersuccessintent.putExtra("startdate", StartDate);
                                    registersuccessintent.putExtra("enddate", EndDate);
                                    registersuccessintent.putExtra("starttime", StartTime);
                                    registersuccessintent.putExtra("endtime", EndTime);
                                    registersuccessintent.putExtra("eventmode", eventmode);
                                    registersuccessintent.putExtra("eventcode", eventcode);
                                    registersuccessintent.putExtra("ruserid", ruserid);
                                    updatelasteventdate(Integer.toString(ruserid), date);
                                    EventCreatorActivity.this.startActivity(registersuccessintent);
                                    finish();


                                } else {
                                    AlertDialog.Builder alertmsg = new AlertDialog.Builder(EventCreatorActivity.this);
                                    alertmsg.setMessage("Event creation failed, please re-check all the entered information.")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    progressDialog.setTitle("Creating Event");
                    progressDialog.setMessage("Please wait...");
                    progressDialog.setCancelable(false);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    EventCreatorRequest EventCreatorRequest = new EventCreatorRequest(eventName, eventLocation, eventDescription, StartDate, EndDate, StartTime, EndTime, eventmode, eventcode, ruserid, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(EventCreatorActivity.this);
                    queue.add(EventCreatorRequest);
                }
            }
        });

    }

    public void showDialogButtonClick(){

        btnStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DILOG3_ID);
            }
        });

        btnEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DILOG4_ID);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id){
            if (id == DILOG3_ID)
                return new TimePickerDialog(EventCreatorActivity.this, tpickerListener, hourX, minuteX, false);
            else if (id == DILOG4_ID)
                return new TimePickerDialog(EventCreatorActivity.this, tpickerListener2, hourX, minuteX, false);
        return null;
    }

    private TimePickerDialog.OnTimeSetListener tpickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String minuteString = "";
            hourX = hourOfDay;
            minuteX = minute;
            if (minute < 10){
                minuteString = "0" + minute;
            }else
                minuteString = Integer.toString(minute);
            btnStartTime.setText(hourOfDay+" : "+minuteString);
            StartTimeX = hourOfDay+":"+minuteString;
        }
    };

    private TimePickerDialog.OnTimeSetListener tpickerListener2 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String minuteString = "";
            hourX = hourOfDay;
            minuteX = minute;
            if (minute < 10){
                minuteString = "0" + minute;
            }else
                minuteString = Integer.toString(minute);
            btnEndTime.setText(hourOfDay+" : "+minuteString);
            EndTimeX = hourOfDay+":"+minuteString;
        }
    };

    public void rbclick (View v){
        int radiobuttonid = rg.getCheckedRadioButtonId();
        rb = (RadioButton) findViewById(radiobuttonid);

        eventmode = rb.getText().toString();
        if(eventmode.equals("Public"))
            Toast.makeText(getBaseContext(),"You have selected Public Mode, Your event information are allowed to be known by other residents.",Toast.LENGTH_LONG).show();
        else if (eventmode.equals("Private"))
            Toast.makeText(getBaseContext(),"You have selected Private Mode, Your event information are restricted to be known by other residents.",Toast.LENGTH_LONG).show();


    }

    private void updatelasteventdate(final String ruserid, final String lasteventdate){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPDATEURL + ruserid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    boolean success = object.getBoolean("success");

                    if (success) {
                        Toast.makeText(getBaseContext(), "Last Event date has been updated.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getBaseContext(), "An error has occured while updating the event date into the database.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("lasteventdate", lasteventdate);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }
}
