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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResiUserSearchResultActivity extends AppCompatActivity {

    private static final String TAG = ResiUserSearchResultActivity.class.getSimpleName();
    private RecyclerView userresult;
    private TextView noresult;
    private static final String url = "https://resisafe.000webhostapp.com/ShowUserResiSafe.php?id=";
    ProgressDialog pDialog;
    GridLayoutManager gridLayoutManager;
    private VisiUserListCustomAdapter visiadapter;
    private ResiUserListCustomAdapter resiadapter;
    List<VisitorUser> searchvisiuser_list = new ArrayList<VisitorUser>();
    List<ResidentUser> searchresiuser_list = new ArrayList<ResidentUser>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resi_user_search_result);
        this.setTitle("Search Result");
        showLoading();

        gridLayoutManager = new GridLayoutManager(this,1);
        userresult = (RecyclerView) findViewById(R.id.userresult);
        noresult = (TextView) findViewById(R.id.tvNoresult);
        userresult.setLayoutManager(gridLayoutManager);


        Intent i = getIntent();
        String searchText = i.getStringExtra("searchText");
        String searchType = i.getStringExtra("searchType");

        if (searchType.equals("1")){
            visiadapter = new VisiUserListCustomAdapter(this, searchvisiuser_list);
            userresult.setAdapter(visiadapter);
            getVisiSearchResult(searchText, searchType);
        } else if (searchType.equals("2")){
            resiadapter = new ResiUserListCustomAdapter(this, searchresiuser_list);
            userresult.setAdapter(resiadapter);
            getResiSearchResult(searchText, searchType);
        }

    }
    private void getVisiSearchResult(final String searchText, final String searchType){
        JsonArrayRequest searchReq = new JsonArrayRequest(url + searchType,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                VisitorUser user = new VisitorUser();
                                user.setName(obj.getString("name"));
                                user.setNationality(obj.getString("nationality"));
                                user.setLastcheckindate(obj.getString("lastcheckindate"));
                                user.setCarplatenum(obj.getString("carplatenum"));
                                user.setId(obj.getInt("vuser_id"));
                                user.setProfileimage(obj.getString("profileimage"));

                                if(user.getName().toLowerCase().contains(searchText.toLowerCase()) ||
                                        user.getCarplatenum().toLowerCase().contains(searchText.toLowerCase()) ) {
                                    searchvisiuser_list.add(user);
                                }
                                else {}

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        visiadapter.notifyDataSetChanged();
                        if (searchvisiuser_list.isEmpty()){
                            userresult.setVisibility(View.GONE);
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

        //Adding request to the queue
        requestQueue.add(searchReq);
    }

    private void getResiSearchResult(final String searchText, final String searchType) {

        JsonArrayRequest userReq = new JsonArrayRequest(url + searchType,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();
                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                ResidentUser user = new ResidentUser();
                                user.setName(obj.getString("name"));
                                user.setAddress(obj.getString("address"));
                                user.setLasteventdate(obj.getString("lasteventdate"));
                                user.setProfileimage(obj.getString("profileimage"));
                                user.setId(obj.getInt("ruser_id"));

                                if(user.getName().toLowerCase().contains(searchText.toLowerCase())){
                                    searchresiuser_list.add(user);
                                } else {}

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        resiadapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(userReq);
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
}
