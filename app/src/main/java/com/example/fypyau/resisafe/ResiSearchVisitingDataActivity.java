package com.example.fypyau.resisafe;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.Inflater;

public class ResiSearchVisitingDataActivity extends AppCompatActivity {
    EditText etSearchQuery;
    Button btnSearch;
    TextView tvStartdate, tvStarttime, tvEndtime, tvEnddate, tvChosendate;
    private int dayX, monthX, yearX, hourX, minuteX;
    String startdate, starttime, enddate, endtime;
    boolean timeconfigured = false;
    Spinner searchspinner;
    CheckBox checkBox;
    String selection;
    View dialoglayout;
    AlertDialog.Builder builder;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy, HH:mm");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resi_search_visiting_data);

        etSearchQuery = (EditText) findViewById(R.id.etSearchQuery);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        searchspinner = (Spinner) findViewById(R.id.searchspinner);
        checkBox = (CheckBox) findViewById(R.id.checkBox);

        tvChosendate = (TextView) findViewById(R.id.tvChosendate);


        searchspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = searchspinner.getSelectedItem().toString();
                if (selectedItem.equals("By Name"))
                    selection = "searchName";
                else if (selectedItem.equals("By Car Plate Number"))
                    selection = "searchCar";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true)
                    datetimedialog();
                else
                    tvChosendate.setVisibility(View.GONE);

            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = etSearchQuery.getText().toString();
                Intent i = new Intent(ResiSearchVisitingDataActivity.this, ResiVisitingDataActivity.class);
                i.putExtra("selection", selection);
                i.putExtra("query", query);
                if (checkBox.isChecked()){
                    i.putExtra("startdate", startdate);
                    i.putExtra("enddate", enddate);
                    i.putExtra("starttime", starttime);
                    i.putExtra("endtime", endtime);
                }
                ResiSearchVisitingDataActivity.this.startActivity(i);
            }
        });
    }

    private void timedialogbuilder(){
        dialoglayout = getLayoutInflater().inflate(R.layout.datetime_custom_dialog_layout, null);

                startdate = "";
                enddate = "";
                starttime = "";
                endtime = "";

        tvStartdate = (TextView) dialoglayout.findViewById(R.id.tvStartdate);
        tvEnddate = (TextView) dialoglayout.findViewById(R.id.tvEnddate);
        tvStarttime = (TextView) dialoglayout.findViewById(R.id.tvStarttime);
        tvEndtime = (TextView) dialoglayout.findViewById(R.id.tvEndtime);

        builder = new AlertDialog.Builder(ResiSearchVisitingDataActivity.this);
        builder.setView(dialoglayout);
        builder.setTitle("Choose Date and Time");
        builder.setPositiveButton("OK", null);

        Calendar calendar = Calendar.getInstance();
        dayX = calendar.get(Calendar.DAY_OF_MONTH);
        monthX = calendar.get(Calendar.MONTH);
        yearX = calendar.get(Calendar.YEAR);
        hourX = calendar.get(Calendar.HOUR_OF_DAY);
        minuteX = calendar.get(Calendar.MINUTE);

        tvStartdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(1);
            }
        });

        tvEnddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(2);
            }
        });

        tvStarttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(3);
            }
        });

        tvEndtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(4);
            }
        });



    }
    protected Dialog onCreateDialog(int id){
        if (id == 1)
            return new DatePickerDialog(ResiSearchVisitingDataActivity.this, dpickerListener, yearX, monthX,dayX);
        else if (id == 2)
            return new DatePickerDialog(ResiSearchVisitingDataActivity.this, dpickerListener2, yearX, monthX,dayX);
        else if (id == 3)
            return new TimePickerDialog(ResiSearchVisitingDataActivity.this, tpickerListener, hourX, minuteX, false);
        else if (id == 4)
            return new TimePickerDialog(ResiSearchVisitingDataActivity.this, tpickerListener2, hourX, minuteX, false);
        return null;
    }

    private TimePickerDialog.OnTimeSetListener tpickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String minuteString = "";
            String hourString = "";
            if (hourOfDay < 10){
                hourString = "0" + hourOfDay;
            } else
                hourString = Integer.toString(hourOfDay);
            if (minute < 10){
                minuteString = "0" + minute;
            }else
                minuteString = Integer.toString(minute);
            tvStarttime.setText(hourOfDay+" : "+minuteString);
            starttime = hourString+":"+minuteString;
        }
    };

    private TimePickerDialog.OnTimeSetListener tpickerListener2 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String minuteString = "";
            String hourString = "";
            if (hourOfDay < 10){
                hourString = "0" + hourOfDay;
            } else
                hourString = Integer.toString(hourOfDay);
            if (minute < 10){
                minuteString = "0" + minute;
            }else
                minuteString = Integer.toString(minute);
            tvEndtime.setText(hourOfDay+" : "+minuteString);
            endtime = hourString+":"+minuteString;
        }
    };

    private DatePickerDialog.OnDateSetListener dpickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String dayString = "";
            String monthString = "";
            month++;
            yearX = year;
            if (dayOfMonth < 10)
                dayString = "0" + dayOfMonth;
            else
                dayString = Integer.toString(dayOfMonth);
            if (month < 10)
                monthString = "0" + month;
            else
                monthString = Integer.toString(month);
            startdate = dayString + "/" + monthString + "/" + yearX;
            tvStartdate.setText(startdate);

        }
    };

    private DatePickerDialog.OnDateSetListener dpickerListener2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String dayString = "";
            String monthString = "";
            month++;
            yearX = year;
            if (dayOfMonth < 10)
                dayString = "0" + dayOfMonth;
            else
                dayString = Integer.toString(dayOfMonth);
            if (month < 10)
                monthString = "0" + month;
            else
                monthString = Integer.toString(month);
            enddate = dayString + "/" + monthString + "/" + yearX;
            tvEnddate.setText(enddate);

        }
    };



    private void datetimedialog(){
        timedialogbuilder();
        AlertDialog showdialog = builder.create();
        showdialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if ( startdate.equals("") ||  enddate.equals("")  ||  starttime.equals("") ||  endtime.equals("")  ) {
                            AlertDialog.Builder error = new AlertDialog.Builder(ResiSearchVisitingDataActivity.this);
                            error.setTitle("Error");
                            error.setMessage("Invalid Date Configuration");
                            error.setNegativeButton("OK", null);
                            error.show();
                        }
                        else {
                            Date start = new Date(), end = new Date();
                            try {
                                start = formatter.parse(startdate + ", " + starttime);
                                end = formatter.parse(enddate + ", " + endtime);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if (start.compareTo(end) > 0 ){
                                AlertDialog.Builder error = new AlertDialog.Builder(ResiSearchVisitingDataActivity.this);
                                error.setTitle("Error");
                                error.setMessage("Invalid Date Configuration");
                                error.setNegativeButton("OK", null);
                                error.show();
                            } else {
                                tvChosendate.setText("You have chosen to search from " + startdate + ", " + starttime + " until " + enddate + ", " + endtime + ". " );
                                tvChosendate.setVisibility(View.VISIBLE);
                                dialog.dismiss();}
                        }
                    }
                });
            }
        });

        showdialog.show();
    }


}
