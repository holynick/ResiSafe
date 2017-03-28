package com.example.fypyau.resisafe;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nick Yau on 2/24/2017.
 */

public class VisiQRcodeRegisterRequest extends StringRequest {
    private static final String QRCODEREGISTER_REQUEST_URL = "https://resisafe.000webhostapp.com/QRcodeRegisterResiSafe.php";
    private Map<String,String> params;

    public VisiQRcodeRegisterRequest(String qrcode, String validdate, Response.Listener<String> Listener){
        super(Method.POST, QRCODEREGISTER_REQUEST_URL, Listener, null);
        params = new HashMap<>();
        params.put("qrcode",qrcode);
        params.put("validdate",validdate);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
