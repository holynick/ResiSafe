package com.example.fypyau.resisafe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ResiVisitingDataDetailsActivity extends AppCompatActivity {

    private static final String TAG = ResiVisitingDataDetailsActivity.class.getSimpleName();
    private final String url = "https://resisafe.000webhostapp.com/ShowRVisitingDataResiSafe.php?qrcode=";
    private final String eventdetailsurl = "https://resisafe.000webhostapp.com/CodeShowEventDetailsResiSafe.php?eventcode=";
    private final String locationurl = "https://resisafe.000webhostapp.com/ShowEventLocationResiSafe.php?eventcode=";
    TextView tvVisitingtype, tvName, tvDate, tvCarplatenum, tvRVisitingtype, tvRDate, tvEmptyResult, tvEventlocation;
    Event event = new Event();
    CardView eventcardview;
    JSONObject obj = null;
    String vtype, Rvisitingtype = "", Rdate, Rtime;
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resi_visiting_data_details);

        Intent i = getIntent();
        VisitingData visit_data = (VisitingData) i.getSerializableExtra("visitdata");
        String qrcode = visit_data.getQrcode();
        final String eventCode = visit_data.getEventcode();
        if (qrcode.equals(""))
            qrcode = "Nothing";
        tvVisitingtype = (TextView) findViewById(R.id.tvVisitingtype);
        eventcardview = (CardView) findViewById(R.id.eventcard_view);
        tvName = (TextView) findViewById(R.id.tvName);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvCarplatenum = (TextView) findViewById(R.id.tvCarplatenum);
        tvRVisitingtype = (TextView) findViewById(R.id.tvRVisitingtype);
        tvRDate = (TextView) findViewById(R.id.tvRDate);
        tvEmptyResult = (TextView) findViewById(R.id.tvEmptyResult);
        tvEventlocation = (TextView) findViewById(R.id.tvEventlocation);

        vtype = visit_data.getVisitingtype();
        tvVisitingtype.setText(vtype);
        if (vtype.equals("Check In")){
            tvVisitingtype.setTextColor(ContextCompat.getColor(this, R.color.checkingreen));
        } else
            tvVisitingtype.setTextColor(ContextCompat.getColor(this, R.color.checkoutred));
        tvDate.setText("Date: " + visit_data.getVisitingdate() + ", " + visit_data.getVisitingtime());
        tvName.setText(visit_data.getVusername());
        tvCarplatenum.setText(visit_data.getVusercarplatenum());
        showLoading();
        getRdata(qrcode);
        getEventLocation(eventCode);

        eventcardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
                getEventLocationData(eventCode);
            }
        });

    }

    private void getRdata(String qrcode){
        JsonArrayRequest userReq = new JsonArrayRequest(url + qrcode,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            // Parsing json
                            try {
                                obj = response.getJSONObject(i);
                                String visitingtype = obj.getString("visitingtype");
                                if (vtype.equals("Check In")) {
                                    if (visitingtype.equals("Check Out")) {
                                        Rvisitingtype = visitingtype;
                                        Rtime = obj.getString("visitingtime");
                                        Rdate = obj.getString("visitingdate");
                                    }
                                } else if  (vtype.equals("Check Out")){
                                    if (visitingtype.equals("Check In")) {
                                        Rvisitingtype = visitingtype;
                                        Rtime = obj.getString("visitingtime");
                                        Rdate = obj.getString("visitingdate");
                                    }
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (Rvisitingtype.isEmpty()){
                            tvEmptyResult.setVisibility(View.VISIBLE);
                            tvRVisitingtype.setVisibility(View.GONE);
                            tvRDate.setVisibility(View.GONE);
                            if (vtype.equals("Check In")){
                                tvEmptyResult.setText("The person has not check out yet.");
                            } else if (vtype.equals("Check Out")){
                                tvEmptyResult.setText("The person has not check in yet.");
                            }
                            hidepDialog();
                        } else {
                            tvRVisitingtype.setText("The person had " + Rvisitingtype + " at: ");
                            tvRDate.setText("Date: " + Rdate + ", " + Rtime);
                            hidepDialog();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidepDialog();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(userReq);
    }

    private void getEventLocation(String eventCode){

        JsonArrayRequest userReq = new JsonArrayRequest(eventdetailsurl + eventCode,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        // Parsing json
                        try {
                            obj = response.getJSONObject(0);
                            tvEventlocation.setText(obj.getString("eventLocation"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidepDialog();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(userReq);

    }
    private void getEventLocationData(String eventCode){

        JsonArrayRequest userReq = new JsonArrayRequest(eventdetailsurl + eventCode,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                            // Parsing json
                            try {
                                obj = response.getJSONObject(0);
                                event.setEventName(obj.getString("eventName"));
                                event.setEventLocation(obj.getString("eventLocation"));
                                event.setEventDescription(obj.getString("eventDescription"));
                                event.setEventstartdate(obj.getString("eventStartDate"));
                                event.setEventenddate(obj.getString("eventEndDate"));
                                event.setEventstarttime(obj.getString("eventStartTime"));
                                event.setEventendtime(obj.getString("eventEndTime"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                                hidepDialog();
                            }
                        hidepDialog();
                        Intent i = new Intent(ResiVisitingDataDetailsActivity.this, ResiEventDetailsActivity.class);
                        i.putExtra("eventdata", event);
                        ResiVisitingDataDetailsActivity.this.startActivity(i);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidepDialog();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(userReq);
    }

    private void hidepDialog(){
        if (pDialog != null ){
            pDialog.dismiss();
            pDialog = null;
        }
    }

    private void showLoading(){
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
    }
}
