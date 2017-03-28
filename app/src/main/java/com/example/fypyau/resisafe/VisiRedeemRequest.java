package com.example.fypyau.resisafe;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nick Yau on 1/30/2017.
 */

public class VisiRedeemRequest extends StringRequest {
    private static final String REDEEM_REQUEST_URL = "https://resisafe.000webhostapp.com/RedeemResiSafe.php";
    private Map<String,String> params;

    public VisiRedeemRequest(String eventcode, Response.Listener<String> listener) {
        super(Request.Method.POST, REDEEM_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("eventcode", eventcode);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
