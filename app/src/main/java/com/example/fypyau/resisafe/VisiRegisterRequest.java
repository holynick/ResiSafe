package com.example.fypyau.resisafe;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nick Yau on 1/20/2017.
 */
public class VisiRegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "https://resisafe.000webhostapp.com/RegisterResiSafe.php";
    private Map<String,String> params;

    public VisiRegisterRequest(String name, String username, String password, String nationality, String icnum, String carplatenum, String contactnum, String image, Response.Listener<String> Listener){
        super(Method.POST, REGISTER_REQUEST_URL, Listener, null);
        params = new HashMap<>();
        params.put("name",name);
        params.put("username",username);
        params.put("password",password);
        params.put("nationality",nationality);
        params.put("icnum",icnum);
        params.put("carplatenum",carplatenum);
        params.put("contactnum",contactnum);
        params.put("image", image);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
