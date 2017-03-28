package com.example.fypyau.resisafe;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nick Yau on 1/24/2017.
 */

public class EventCreatorRequest extends StringRequest{
    private static final String EVENT_REQUEST_URL = "https://resisafe.000webhostapp.com/EventCreatorResiSafe.php";
    private Map<String,String> params;

    public EventCreatorRequest(String eventname, String eventlocation, String eventdescription, String startdate, String enddate, String starttime, String endtime, String eventmode, String eventcode, int ruserid, Response.Listener<String> Listener){
        super(Request.Method.POST, EVENT_REQUEST_URL, Listener, null);
        params = new HashMap<>();
        params.put("eventname",eventname);
        params.put("eventlocation",eventlocation);
        params.put("eventdescription",eventdescription);
        params.put("startdate",startdate);
        params.put("enddate",enddate);
        params.put("starttime",starttime);
        params.put("endtime",endtime);
        params.put("eventmode",eventmode);
        params.put("eventcode",eventcode);
        params.put("ruserid",ruserid + "");

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
