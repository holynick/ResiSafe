package com.example.fypyau.resisafe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ResiUserProfileActivity extends AppCompatActivity {
    private static final String TAG = ResiUserProfileActivity.class.getSimpleName();
    private String url = "https://resisafe.000webhostapp.com/EventShowUserResiSafe.php?id=";
    TextView tvUsername, tvNationality, tvCarplatenum, tvNoresult;
    ImageView userimage;
    ListView usereventlist;
    CustomListAdapter adapter;
    List<Event> event_data;
    ProgressDialog pDialog;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resi_user_profile);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading....");
        pDialog.show();
        tvUsername = (TextView) findViewById(R.id.tvUsername);
        tvNationality = (TextView) findViewById(R.id.tvNationality);
        tvCarplatenum = (TextView) findViewById(R.id.tvCarplatenum);
        tvNoresult = (TextView) findViewById(R.id.tvNoresult);
        usereventlist = (ListView) findViewById(R.id.usereventlist);
        userimage = (ImageView) findViewById(R.id.user_profile_photo);
        event_data = new ArrayList<>();
        adapter = new CustomListAdapter(this, event_data);
        usereventlist.setAdapter(adapter);
        Intent i = getIntent();
        ResidentUser userdata = (ResidentUser) i.getSerializableExtra("userdata");
        tvUsername.setText(userdata.getName());
        tvNationality.setText(userdata.getAddress());
        tvCarplatenum.setText("");
        String imagelink = userdata.getProfileimage();
        if(imagelink.isEmpty())
            userimage.setImageResource(R.drawable.unknownprofile);
        else
            Glide.with(this).load(imagelink).into(userimage);
        id = Integer.toString(userdata.getId());
        geteventdata();

    }

    private void geteventdata(){
        final JsonArrayRequest eventReq = new JsonArrayRequest(url + id,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidepDialog();

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Event event = new Event();
                                event.setEventID(obj.getInt("event_id"));
                                event.setEventName(obj.getString("eventName"));
                                event.setEventLocation(obj.getString("eventLocation"));
                                event.setEventstartdate(obj.getString("eventStartDate"));
                                event.setEventendtime(obj.getString("eventStarttime"));
                                event.setEventenddate(obj.getString("eventEndDate"));
                                event.setEventendtime(obj.getString("eventEndTime"));
                                event.setEventDescription(obj.getString("eventDescription"));
                                event.setRuserID(obj.getInt("ruser_id"));
                                event_data.add(event);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        if (event_data.isEmpty())
                            tvNoresult.setVisibility(View.VISIBLE);
                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidepDialog();

            }
        });

        // Adding request to request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(eventReq);
    }

    private void hidepDialog(){
        if (pDialog != null ){
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
