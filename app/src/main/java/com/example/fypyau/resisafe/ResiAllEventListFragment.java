package com.example.fypyau.resisafe;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.support.v4.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import android.net.Uri;

import android.view.Menu;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

;
import com.google.android.gms.appindexing.Action;

import com.google.android.gms.appindexing.Thing;


/**
 * Created by Nick Yau on 1/30/2017.
 */

public class ResiAllEventListFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = ResiAllEventListFragment.class.getSimpleName();
    View MyView;
    private static final String urlAll = "https://resisafe.000webhostapp.com/EventShowAllResiSafe.php?";
    private ProgressDialog pDialog;
    private List<Event> eventList = new ArrayList<Event>();
    private ListView listView;
    private TextView tvNoresult;
    private CustomListAdapter adapter;
    private Spinner sortSpinner, filterSpinner;
    int check = 0;
    Date currentdate;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy, HH:mm");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MyView = inflater.inflate(R.layout.resi_event_layout, container, false);
        getActivity().setTitle("Event List");
        listView = (ListView) MyView.findViewById(R.id.list);
        sortSpinner = (Spinner) MyView.findViewById(R.id.sortSpinner);
        filterSpinner = (Spinner) MyView.findViewById(R.id.filterSpinner);
        tvNoresult = (TextView) MyView.findViewById(R.id.tvNoresult);

        Calendar calendar = Calendar.getInstance();
        String transfer = formatter.format(calendar.getTime());
        try {
            currentdate = formatter.parse(transfer);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Log.v("TAG", "CLICKED row number: " + arg2);
                getData(eventList.get(arg2).getEventID());
            }

        });
        setHasOptionsMenu(true);
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tvNoresult.setVisibility(View.GONE);
                String selectedSort = sortSpinner.getSelectedItem().toString();
                    showLoading();
                    resetList();
                    getListview("");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tvNoresult.setVisibility(View.GONE);
                if(++check > 1) {
                    showLoading();
                    resetList();
                    getListview("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return MyView;
    }



        public void getListview(final String searchText) {




        // Creating volley request obj
        JsonArrayRequest movieReq = new JsonArrayRequest(urlAll,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();

                        // Parsing json
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
                                String sortselected = sortSpinner.getSelectedItem().toString();
                                String filterselected = filterSpinner.getSelectedItem().toString();
                                Date startingdate = formatter.parse(event.getEventstartdate() + ", " + event.getEventstarttime());
                                Date endingdate = formatter.parse(event.getEventenddate() + ", " + event.getEventendtime());
                                if(filterselected.equals("All events"))
                                    eventList.add(event);
                                  else if (filterselected.equals("Upcoming events")){
                                    if (currentdate.compareTo(startingdate) < 0){
                                        eventList.add(event);
                                    }
                                } else if (filterselected.equals("Past events")){
                                    if (currentdate.compareTo(endingdate) > 0){
                                        eventList.add(event);
                                    }
                                } else if (filterselected.equals("On going events")){
                                    if (currentdate.compareTo(startingdate) >= 0 && currentdate.compareTo(endingdate) <= 0){
                                        eventList.add(event);
                                    }
                                }
                                if (sortselected.equals("Latest Date")) {
                                    Collections.sort(eventList, new Comparator<Event>() {
                                        @Override
                                        public int compare(Event o1, Event o2) {
                                            if (o1.getEventID() >= o2.getEventID()) {
                                                return -1;
                                            } else {
                                                return 1;
                                            }
                                        }
                                    });
                                } else if (sortselected.equals("Alphabetical")){
                                    Collections.sort(eventList, new Comparator<Event>() {
                                        @Override
                                        public int compare(Event o1, Event o2) {
                                            return o1.getEventName().toLowerCase().compareTo(o2.getEventName().toLowerCase());
                                        }
                                    });
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                        if (eventList.isEmpty()){
                            tvNoresult.setVisibility(View.VISIBLE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage().toString());
                hidePDialog();

            }
        });

        // Adding request to request queue
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

            //Adding request to the queue
            requestQueue.add(movieReq);


    }

    public void showLoading(){
        pDialog = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    public void resetList(){
        eventList.clear();
        listView.setAdapter(null);
        adapter = new CustomListAdapter(getActivity(), eventList);
        listView.setAdapter(adapter);
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
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
            public boolean onQueryTextSubmit(String query)
            {
                String searchText = query.toString();
                Intent i = new Intent (getActivity(), ResiEventSearchResultActivity.class);
                i.putExtra("searchText",searchText);
                getActivity().startActivity(i);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText.toString());
                return false;
            }
        });

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("ResiEventList Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

    }


    @Override
    public void onClick(View v) {

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
                        Toast.makeText(getActivity(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
    private void showJSON(String response){
        Event event = new Event();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");
            JSONObject object = result.getJSONObject(0);
            event.setEventName(object.getString("eventName"));
            event.setEventLocation(object.getString("eventLocation"));
            event.setEventDescription(object.getString("eventDescription"));
            event.setEventstartdate(object.getString("eventStartDate"));
            event.setEventenddate(object.getString("eventEndDate"));
            event.setEventstarttime(object.getString("eventStartTime"));
            event.setEventendtime(object.getString("eventEndTime"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent i = new Intent(getActivity(), ResiEventDetailsActivity.class);
        i.putExtra("eventdata", event);
        startActivity(i);
    }
}
