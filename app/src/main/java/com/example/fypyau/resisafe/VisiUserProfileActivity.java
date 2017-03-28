package com.example.fypyau.resisafe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import java.util.List;

public class VisiUserProfileActivity extends AppCompatActivity {
    private static final String TAG = VisiUserProfileActivity.class.getSimpleName();
    private String url = "https://resisafe.000webhostapp.com/ShowUserVisitingDataResiSafe.php?id=";
    private GridLayoutManager gridLayoutManager;
    ImageView userimage;
    TextView tvUsername, tvNationality, tvCarplatenum, tvNoresult;
    RecyclerView uservisitingdata;
    UserVisitingListCustomAdapter adapter;
    List<VisitingData> visit_data;
    ProgressDialog pDialog;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visi_user_profile);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading....");
        pDialog.show();
        tvUsername = (TextView) findViewById(R.id.tvUsername);
        tvNationality = (TextView) findViewById(R.id.tvNationality);
        tvCarplatenum = (TextView) findViewById(R.id.tvCarplatenum);
        uservisitingdata = (RecyclerView) findViewById(R.id.uservisitingdata);
        userimage = (ImageView) findViewById(R.id.user_profile_photo);
        tvNoresult = (TextView) findViewById(R.id.tvNoresult);
        visit_data = new ArrayList<>();
        gridLayoutManager = new GridLayoutManager(this, 1);
        uservisitingdata.setLayoutManager(gridLayoutManager);
        adapter = new UserVisitingListCustomAdapter(this, visit_data);
        uservisitingdata.setAdapter(adapter);
        Intent i = getIntent();
        VisitorUser userdata = (VisitorUser) i.getSerializableExtra("userdata");
        tvUsername.setText(userdata.getName());
        tvNationality.setText(userdata.getNationality());
        tvCarplatenum.setText(userdata.getCarplatenum());
        String imagelink = userdata.getProfileimage();
        if(imagelink.isEmpty())
            userimage.setImageResource(R.drawable.unknownprofile);
        else
            Glide.with(this).load(imagelink).into(userimage);
        id = Integer.toString(userdata.getId());
        getvisitingdata();

    }

    private void getvisitingdata(){
        JsonArrayRequest userReq = new JsonArrayRequest(url + id,
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
                        if (visit_data.isEmpty())
                            tvNoresult.setVisibility(View.VISIBLE);
                        adapter.notifyDataSetChanged();
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
}
