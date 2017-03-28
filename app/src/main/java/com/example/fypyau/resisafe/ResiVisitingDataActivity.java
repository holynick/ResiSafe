package com.example.fypyau.resisafe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResiVisitingDataActivity extends AppCompatActivity {
    private static final String TAG = ResiVisitingDataActivity.class.getSimpleName();
    private RecyclerView visitingdata;
    private TextView noresult;
    private List<VisitingData> visit_data;
    private GridLayoutManager gridLayoutManager;
    private VisitingDataCustomAdapter customAdapter;
    private String url = "https://resisafe.000webhostapp.com/ShowVisitingDataResiSafe.php?id=";
    private String searchnameurl = "https://resisafe.000webhostapp.com/SearchNameVisitingDataResiSafe.php?query=";
    private String searchcarurl = "https://resisafe.000webhostapp.com/SearchCarVisitingDataResiSafe.php?query=";
    private ProgressDialog pDialog;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy, HH:mm");
    Date dstartdate, denddate;
    String starttime = "", endtime = "", startdate = "", enddate = "";
    boolean specificdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resi_visiting_data);
        this.setTitle("Visiting Details");
        visit_data = new ArrayList<>();
        visitingdata = (RecyclerView) findViewById(R.id.visitingdata) ;
        noresult = (TextView) findViewById(R.id.noresult);
        gridLayoutManager = new GridLayoutManager(this, 1);
        visitingdata.setLayoutManager(gridLayoutManager);
        customAdapter = new VisitingDataCustomAdapter(this, visit_data);
        visitingdata.setAdapter(customAdapter);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
        Intent i = getIntent();
        String selection = i.getStringExtra("selection");
        String query = i.getStringExtra("query");
        if (i.hasExtra("startdate")) {
            specificdate = true;
            startdate = i.getStringExtra("startdate");
            enddate = i.getStringExtra("enddate");
            starttime = i.getStringExtra("starttime");
            endtime = i.getStringExtra("endtime");
        }
        if (specificdate) {
            try {
                dstartdate = formatter.parse(startdate + ", " + starttime);
                denddate = formatter.parse(enddate + ", " + endtime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (selection.equals("searchName"))
            searchvisitingdata(searchnameurl, query);
        else if (selection.equals("searchCar"))
            searchvisitingdata(searchcarurl, query);
        else
        loaddata(selection);
    }

    private void loaddata(String selection) {
        JsonArrayRequest userReq = new JsonArrayRequest(url + selection,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidepDialog();
                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                VisitingData data = new VisitingData();
                                data.setVisitingtype(obj.getString("visitingtype"));
                                data.setVisitingtime(obj.getString("visitingtime"));
                                data.setVisitingdate(obj.getString("visitingdate"));
                                data.setEventcode(obj.getString("eventCode"));
                                data.setVuserid(Integer.toString(obj.getInt("vuserid")));
                                data.setVusername(obj.getString("vusername"));
                                data.setVusercarplatenum(obj.getString("vusercarplatenum"));
                                data.setQrcode(obj.getString("qrcode"));
                                visit_data.add(data);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        if(visit_data.isEmpty())
                            noresult.setVisibility(View.VISIBLE);
                        customAdapter.notifyDataSetChanged();
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

    private void hidepDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    private void searchvisitingdata(String searchurl, String searchText) {
        JsonArrayRequest userReq = new JsonArrayRequest(searchurl + searchText,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidepDialog();
                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                VisitingData data = new VisitingData();
                                data.setVisitingtype(obj.getString("visitingtype"));
                                data.setVisitingtime(obj.getString("visitingtime"));
                                data.setVisitingdate(obj.getString("visitingdate"));
                                data.setEventcode(obj.getString("eventCode"));
                                data.setVuserid(Integer.toString(obj.getInt("vuserid")));
                                data.setVusername(obj.getString("vusername"));
                                data.setVusercarplatenum(obj.getString("vusercarplatenum"));
                                data.setQrcode(obj.getString("qrcode"));
                                Date visitdate = formatter.parse(data.getVisitingdate() + ", " + data.getVisitingtime());
                                if(specificdate) {
                                    if (visitdate.compareTo(dstartdate) >= 0 && visitdate.compareTo(denddate) <= 0)
                                        visit_data.add(data);
                                } else
                                    visit_data.add(data);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        }
                        if(visit_data.isEmpty())
                            noresult.setVisibility(View.VISIBLE);
                        customAdapter.notifyDataSetChanged();
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


}
