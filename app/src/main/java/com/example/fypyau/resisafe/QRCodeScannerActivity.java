package com.example.fypyau.resisafe;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.SimpleFormatter;

public class QRCodeScannerActivity extends AppCompatActivity {
    private static final String TAG = QRCodeScannerActivity.class.getSimpleName();
    private final String REGISTERURL = "https://resisafe.000webhostapp.com/RegisterVisitingDataResiSafe.php";
    private final String url = "https://resisafe.000webhostapp.com/FetchVuserDetailsForVDResiSafe.php?id=";
    private final String UPDATEURL = "https://resisafe.000webhostapp.com/UpdateVisiCheckInDateResiSafe.php?id=";
    private final String QRCODEURL = "https://resisafe.000webhostapp.com/CheckQRcodeValidityResiSafe.php";
    private final String TOKENURL = "https://resisafe.000webhostapp.com/UpdateTokenResiSafe.php?tokentype=";
    private ProgressDialog PDialog;
    String vusername = "" , vusercarplatenum = "";
    String eventcode, removecode, vuserid, date, time, qrcode, scantype, tokentype;
    List<VisitorUser> visit_data;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private JSONObject obj = null;
    ProgressDialog pDialog;
    Date currentdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_scanner);
        Button btCheckin = (Button) findViewById(R.id.btCheckin);
        Button btCheckout = (Button) findViewById(R.id.btCheckout);
        visit_data = new ArrayList<>();
        final Activity activity = this;
        Calendar calendar = Calendar.getInstance();
        String currentdateString = formatter.format(calendar.getTime());
        try {
            currentdate = formatter.parse(currentdateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        btCheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scantype = "Check In";
                tokentype = "1";
                IntentIntegrator intentIntegrator = new IntentIntegrator(activity);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                intentIntegrator.setPrompt("Check In Scan");
                intentIntegrator.setCameraId(0);
                intentIntegrator.setBeepEnabled(false);
                intentIntegrator.setBarcodeImageEnabled(false);
                intentIntegrator.initiateScan();
            }
        });

        btCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scantype = "Check Out";
                tokentype = "2";
                IntentIntegrator intentIntegrator = new IntentIntegrator(activity);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                intentIntegrator.setPrompt("Check Out Scan");
                intentIntegrator.setCameraId(0);
                intentIntegrator.setBeepEnabled(false);
                intentIntegrator.setBarcodeImageEnabled(false);
                intentIntegrator.initiateScan();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        showLoading();
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result != null){
            if (result.getContents() == null) {
                Toast.makeText(this, "You have cancelled the scan.", Toast.LENGTH_SHORT).show();
                hidePDialog();
            }
            else {
                qrcode = result.getContents().toString();
                qrcode = qrcode.replaceAll("\\s+","");
                Toast.makeText(this,qrcode,Toast.LENGTH_SHORT).show();
                checkvalidityqrcode();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void checkvalidityqrcode() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, QRCODEURL , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    boolean success = object.getBoolean("success");

                    if (success) {
                        String validdateString = object.getString("validdate");
                        Date validdate = formatter.parse(validdateString);
                        if (currentdate.compareTo(validdate) > 0) {
                            hidePDialog();
                            AlertDialog.Builder builder = new AlertDialog.Builder(QRCodeScannerActivity.this);
                            builder.setTitle("Access Denied.");
                            builder.setMessage("This QR code has expired.");
                            builder.setNegativeButton("OK", null);
                            builder.show();
                        } else {
                            int checkintoken = object.getInt("checkintoken");
                            int checkouttoken = object.getInt("checkouttoken");
                            if (scantype.equals("Check In")) {
                                if (checkintoken == 0) {
                                    hidePDialog();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(QRCodeScannerActivity.this);
                                    builder.setTitle("Access Denied.");
                                    builder.setMessage("You dont have any " + scantype + " token.");
                                    builder.setNegativeButton("OK", null);
                                    builder.show();
                                } else {
                                    checkintoken--;
                                    updatetoken(checkintoken);
                                }
                            } else if (scantype.equals("Check Out")) {
                                if (checkouttoken == 0) {
                                    hidePDialog();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(QRCodeScannerActivity.this);
                                    builder.setTitle("Access Denied.");
                                    builder.setMessage("You dont have any " + scantype + " token.");
                                    builder.setNegativeButton("OK", null);
                                    builder.show();
                                } else {
                                    checkouttoken--;
                                    updatetoken(checkouttoken);
                                }
                            }
                        }
                        }else{
                            hidePDialog();
                            AlertDialog.Builder builder = new AlertDialog.Builder(QRCodeScannerActivity.this);
                            builder.setTitle("Access Denied.");
                            builder.setMessage("Invalid QR code");
                            builder.setNegativeButton("OK", null);
                            builder.show();
                        }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("qrcode", qrcode);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void updatetoken(final int token){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, TOKENURL + tokentype, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    boolean success = object.getBoolean("success");

                    if (success) {
                        if (scantype.equals("Check In")){
                            Toast.makeText(getBaseContext(), "Removed 1 Check In Token.", Toast.LENGTH_SHORT).show();
                        } else if (scantype.equals("Check Out")){
                            Toast.makeText(getBaseContext(), "Removed 1 Check Out Token.", Toast.LENGTH_SHORT).show();
                        }
                        eventcode = qrcode.substring(0,6);
                        removecode = qrcode.substring(6,12);
                        vuserid = qrcode.substring(12);
                        date = getCurrentDate();
                        time = getCurrentTime();
                        getvuserdata(vuserid);
                    } else {
                        hidePDialog();
                        Toast.makeText(getBaseContext(), "An error has occured while updating the token.", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", token + "");
                params.put("qrcode", qrcode);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void showEventdata(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        registerVisitingdata(scantype, eventcode,Integer.parseInt(vuserid),date,time,vusername,vusercarplatenum, qrcode);

                        String eventname = jsonResponse.getString("eventname");
                        String eventlocation = jsonResponse.getString("eventlocation");
                        String startdate = jsonResponse.getString("startdate");
                        String enddate = jsonResponse.getString("enddate");
                        String starttime = jsonResponse.getString("starttime");
                        String endtime = jsonResponse.getString("endtime");

                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(QRCodeScannerActivity.this);
                        builder.setMessage("Access Granted \n \nEventname: " + eventname + "\nEvent Location: " + eventlocation + "\nEvent Starting Data: " + startdate +
                                "\nEvent Ending Date: " + enddate + "\nEvent Starting Time: " + starttime + "\nEvent Ending Time: " + endtime )
                                .setNegativeButton("OK", null)
                                .create()
                                .show();


                    } else {
                        hidePDialog();
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(QRCodeScannerActivity.this);
                        builder.setMessage("Access Denied \n \nInvalid Information.")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        VisiRedeemRequest RedeemRequest = new VisiRedeemRequest(eventcode, responseListener);
        RequestQueue queue = Volley.newRequestQueue(QRCodeScannerActivity.this);
        queue.add(RedeemRequest);
    }

    private void registerVisitingdata(final String visitingtype, final String eventcode, final int vuserid, final String date, final String time, final String vusername, final String vusercarplatenum, final String qrcode){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTERURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               try{
                   JSONObject object = new JSONObject(response);
                   boolean success = object.getBoolean("success");

                   if(success){
                       Toast.makeText(getBaseContext(), "Visiting details has been registered into the database", Toast.LENGTH_SHORT).show();
                       updateLastCheckInDate(date);

                   }else{
                       Toast.makeText(getBaseContext(), "An error has occured while registering the data into the database.", Toast.LENGTH_SHORT).show();
                       hidePDialog();
                   }
               }catch (JSONException e){
                   e.printStackTrace();
               }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("visitingtype", visitingtype);
                params.put("visitingdate", date);
                params.put("visitingtime", time);
                params.put("eventcode", eventcode);
                params.put("vuserid", vuserid + "");
                params.put("vusername", vusername);
                params.put("vusercarplatenum", vusercarplatenum);
                params.put("qrcode", qrcode);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);

    }
    private void getvuserdata(String vuserid){
        JsonArrayRequest userReq = new JsonArrayRequest(url + vuserid ,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        // Parsing json

                            try {
                                obj = response.getJSONObject(0);
                                vusername = obj.getString("name");
                                vusercarplatenum = obj.getString("carplatenum");


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        showEventdata();
                        Toast.makeText(getBaseContext(),vusername + vusercarplatenum,Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

            }

        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(userReq);

    }


    private void updateLastCheckInDate(final String lastcheckindate) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPDATEURL + vuserid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    boolean success = object.getBoolean("success");

                    if (success) {
                        hidePDialog();
                        Toast.makeText(getBaseContext(), "Last Check in date has been updated.", Toast.LENGTH_SHORT).show();
                    } else {
                        hidePDialog();
                        Toast.makeText(getBaseContext(), "An error has occured while updating the check in date into the database.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("lastcheckindate", lastcheckindate);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private String getCurrentDate(){
        final Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        String date = day + "/" + month + "/" + year;
        return date;
    }

    private String getCurrentTime(){
        final Calendar cal = Calendar.getInstance();
        String minuteString = "";
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        if (minute < 10){
            minuteString = "0" + minute;
        }else
            minuteString = Integer.toString(minute);
        String time = hour + ":" + minuteString;
        return time;
    }

    public void showLoading(){
        pDialog = new ProgressDialog(this);
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
