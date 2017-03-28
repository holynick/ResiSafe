package com.example.fypyau.resisafe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ResiEventSearchResultActivity extends AppCompatActivity {

    private static final String TAG = ResiEventSearchResultActivity.class.getSimpleName();
    private ListView eventresult;
    private TextView noresult;
    private static final String url = "https://resisafe.000webhostapp.com/EventShowAllResiSafe.php?";
    ProgressDialog pDialog;
    private CustomListAdapter adapter;
    List<Event> searchevent_list = new ArrayList<Event>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resi_event_search_result);
        this.setTitle("Search Result");
        showLoading();

        eventresult = (ListView) findViewById(R.id.eventresult);
        noresult = (TextView) findViewById(R.id.tvNoresult);
        adapter = new CustomListAdapter(this, searchevent_list);
        eventresult.setAdapter(adapter);

        Intent i = getIntent();
        String searchText = i.getStringExtra("searchText");

        eventresult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getData(searchevent_list.get(position).getEventID());
            }
        });

        getSearchResult(searchText);

    }

    private void getSearchResult(final String searchText){
        JsonArrayRequest searchReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Event event = new Event();
                                event.setEventID(obj.getInt("event_id"));
                                event.setEventName(obj.getString("eventName"));
                                event.setEventLocation(obj.getString("eventLocation"));
                                event.setEventstartdate(obj.getString("eventStartDate"));
                                event.setEventstarttime(obj.getString("eventStartTime"));
                                event.setEventenddate(obj.getString("eventEndDate"));
                                event.setEventendtime(obj.getString("eventEndTime"));
                                event.setEventDescription(obj.getString("eventDescription"));
                                event.setRuserID(obj.getInt("ruser_id"));
                                    if (event.getEventName().toLowerCase().contains(searchText.toLowerCase())) {
                                        searchevent_list.add(event);
                                    } else { }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                        if (searchevent_list.isEmpty()){
                            eventresult.setVisibility(View.GONE);
                            noresult.setVisibility(View.VISIBLE);

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(searchReq);
    }
    public void showLoading(){
        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }


    public void getData(int id) {
        showLoading();
        String eventid = Integer.toString(id);
        String url = "https://resisafe.000webhostapp.com/ShowEventDetailsResiSafe.php?id=" + eventid;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hidePDialog();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hidePDialog();
                        Toast.makeText(getBaseContext(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void showJSON(String response){
        String eventname="";
        String eventlocation="";
        String eventdescription = "";
        String eventstartdate = "";
        String eventenddate = "";
        String eventstarttime = "";
        String eventendtime = "";
        String eventmode = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");
            JSONObject collegeData = result.getJSONObject(0);
            eventname = collegeData.getString("eventName");
            eventlocation = collegeData.getString("eventLocation");
            eventdescription = collegeData.getString("eventDescription");
            eventstartdate = collegeData.getString("eventStartDate");
            eventenddate = collegeData.getString("eventEndDate");
            eventstarttime = collegeData.getString("eventStartTime");
            eventendtime = collegeData.getString("eventEndTime");
            eventmode = collegeData.getString("eventMode");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent i = new Intent(this, ResiEventDetailsActivity.class);
        i.putExtra("eventname",eventname);
        i.putExtra("eventlocation",eventlocation);
        i.putExtra("eventdescription",eventdescription);
        i.putExtra("eventstartdate",eventstartdate);
        i.putExtra("eventenddate",eventenddate);
        i.putExtra("eventstarttime",eventstarttime);
        i.putExtra("eventendtime",eventendtime);
        i.putExtra("eventmode",eventmode);
        startActivity(i);
    }
}

