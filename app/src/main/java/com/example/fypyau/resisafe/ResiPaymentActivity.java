package com.example.fypyau.resisafe;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ResiPaymentActivity extends AppCompatActivity {

    EditText etCardnumber, etSRCcode, etExpirydate;
    TextView tvCardtype, tvAmount;
    ImageView cardimage;
    Button btpay;
    ResiMembership memberdata;
    private static final String url = "https://resisafe.000webhostapp.com/UpdateMembershipResiSafe.php";
    String date, ndate;
    Date ndateX;
    ProgressDialog pDialog;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resi_payment);

        etCardnumber = (EditText) findViewById(R.id.etCardNumber);
        etSRCcode = (EditText) findViewById(R.id.etSRCcode);
        etExpirydate = (EditText) findViewById(R.id.etExpirydate);
        tvCardtype = (TextView)findViewById(R.id.tvCardtype);
        tvAmount = (TextView)findViewById(R.id.tvAmount);
        cardimage = (ImageView)findViewById(R.id.cardimage);
        btpay = (Button)findViewById(R.id.btpay);
        date = getCurrentDate();

        Intent i = getIntent();
        memberdata = (ResiMembership) i.getSerializableExtra("memberdata");
        tvAmount.setText("Amount: RM " + memberdata.getAmount());
        try {
            ndateX = formatter.parse(memberdata.getNextpayment());
            Calendar addmonth = Calendar.getInstance();
            addmonth.setTime(ndateX);
            addmonth.add(Calendar.MONTH,1);
            ndate = formatter.format(addmonth.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        etCardnumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                if (text.startsWith("4200")){
                    cardimage.setImageResource(R.drawable.mastercard);
                    tvCardtype.setText("Master card");
                } else if (text.startsWith("4300")){
                    cardimage.setImageResource(R.drawable.visa);
                    tvCardtype.setText("Visa");
                } else {
                    cardimage.setImageResource(R.color.white);
                    tvCardtype.setText("Unknown Card Type");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
         btpay.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 showLoading();
                 updatemembership();
             }
         });
    }

    private void updatemembership(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject object = new JSONObject(response);
                    boolean success = object.getBoolean("success");
                    hidePDialog();
                    if(success){
                        AlertDialog.Builder builder = new AlertDialog.Builder(ResiPaymentActivity.this);
                        builder.setTitle("Payment Complete");
                        builder.setMessage("Your membership status has been updated.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(ResiPaymentActivity.this, ResiNavigatorMainActivity.class);
                                i.putExtra("ruserid",Integer.parseInt(memberdata.getRuserid()));
                                ResiPaymentActivity.this.startActivity(i);
                            }
                        });

                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(ResiPaymentActivity.this);
                        builder.setTitle("Error");
                        builder.setMessage("An error has occurred in the payment process.");
                        builder.setNegativeButton("OK", null);

                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hidePDialog();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ruserid", memberdata.getRuserid());
                params.put("membershipstatus", "Valid");
                params.put("Amount", "0.00");
                params.put("lastpayment", date);
                params.put("nextpayment", ndate);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private String getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        String dateX = day + "/" + month + "/" + year;
        return dateX;
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    public void showLoading(){
        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setTitle("Payment in process...");
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
    }
}
