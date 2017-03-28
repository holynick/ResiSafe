package com.example.fypyau.resisafe;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nick Yau on 1/22/2017.
 */

public class ResiLoginRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "https://resisafe.000webhostapp.com/RLoginResiSafe.php";
    private Map<String,String> params;

    public ResiLoginRequest(String username, String password, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
