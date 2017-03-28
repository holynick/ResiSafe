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
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Spinner;

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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Nick Yau on 2/18/2017.
 */

public class ResiVisiUserListFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = ResiVisiUserListFragment.class.getSimpleName();
    private String url = "https://resisafe.000webhostapp.com/ShowUserResiSafe.php?id=1";
    View MyView;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private VisiUserListCustomAdapter adapter;
    private List<VisitorUser> user_list;
    private ProgressDialog pDialog;
    Spinner sortUserSpinner;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MyView = inflater.inflate(R.layout.resi_userlist_layout, container, false);
        recyclerView = (RecyclerView) MyView.findViewById(R.id.recycler_view);
        sortUserSpinner = (Spinner) MyView.findViewById(R.id.sortUserSpinner);


        getActivity().setTitle("User List");
        user_list  = new ArrayList<>();

        gridLayoutManager = new GridLayoutManager(getActivity(),1);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new VisiUserListCustomAdapter(getActivity(),user_list);
        recyclerView.setAdapter(adapter);
        setHasOptionsMenu(true);
        sortUserSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedSort = sortUserSpinner.getSelectedItem().toString();
                int sort = 1;
                if (selectedSort.equals("Alphabetical")) sort = 1;
                else if (selectedSort.equals("De-Alphabetical"))sort = 2;
                else if (selectedSort.equals("Latest Checked-in"))sort = 3;
                user_list.clear();
                showLoading();
                load_data_from_server(sort);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return MyView;
    }

    private void load_data_from_server(final int sort) {

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
                                VisitorUser user = new VisitorUser();
                                user.setName(obj.getString("name"));
                                user.setNationality(obj.getString("nationality"));
                                user.setCarplatenum(obj.getString("carplatenum"));
                                user.setLastcheckindate(obj.getString("lastcheckindate"));
                                user.setId(obj.getInt("vuser_id"));
                                user.setProfileimage(obj.getString("profileimage"));
                                user_list.add(user);
                                if (sort == 1){
                                    Collections.sort(user_list, new Comparator<VisitorUser>() {
                                        @Override
                                        public int compare(VisitorUser o1, VisitorUser o2) {
                                            return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
                                        }
                                    });
                                } else if (sort == 2){
                                    Collections.sort(user_list, new Comparator<VisitorUser>() {
                                        @Override
                                        public int compare(VisitorUser o1, VisitorUser o2) {
                                           int i = o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
                                            if (i > 0 )
                                                return -1;
                                            else
                                                return 1;
                                        }
                                    });
                                } else if (sort == 3){

                                }

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
                i.putExtra("searchType", "1");
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

    private void showLoading(){
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
    }

    @Override
    public void onClick(View v) {

    }
}

