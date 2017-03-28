package com.example.fypyau.resisafe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

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

/**
 * Created by Nick Yau on 2/19/2017.
 */

public class ResiResiUserListFragment extends Fragment {
    private static final String TAG = ResiResiUserListFragment.class.getSimpleName();
    private String url = "https://resisafe.000webhostapp.com/ShowUserResiSafe.php?id=2";
    View MyView;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private ResiUserListCustomAdapter adapter;
    private List<ResidentUser> user_list;
    private ProgressDialog pDialog;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MyView = inflater.inflate(R.layout.resi_userlist_layout, container, false);
        recyclerView = (RecyclerView) MyView.findViewById(R.id.recycler_view);
        getActivity().setTitle("User List");
        user_list  = new ArrayList<>();

        gridLayoutManager = new GridLayoutManager(getActivity(),1);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new ResiUserListCustomAdapter(getActivity(),user_list);
        recyclerView.setAdapter(adapter);


        setHasOptionsMenu(true);

        pDialog = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
        user_list.clear();
        load_data_from_server();
        return MyView;
    }

    private void load_data_from_server() {

        JsonArrayRequest userReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidepDialog();
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
                                user_list.add(user);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidepDialog();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //Adding request to the queue
        requestQueue.add(userReq);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.clear();
        inflater.inflate(R.menu.resi_main_menu,menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String searchText = query.toString();
                Intent i = new Intent(getActivity(),ResiUserSearchResultActivity.class);
                i.putExtra("searchText", searchText);
                i.putExtra("searchType", "2");
                getActivity().startActivity(i);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    private void hidepDialog(){
        if (pDialog != null ){
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
