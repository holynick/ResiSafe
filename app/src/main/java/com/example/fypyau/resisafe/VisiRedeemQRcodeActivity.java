package com.example.fypyau.resisafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class VisiRedeemQRcodeActivity extends AppCompatActivity {

    Button btnRedeem;
    EditText etRedCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visi_redeem_qrcode);

        btnRedeem = (Button) findViewById(R.id.btnRedeem);
        etRedCode = (EditText) findViewById(R.id.etRedCode);

        Intent i = getIntent();
        final int vuserid = i.getIntExtra("vuserid", -1);

        Toast.makeText(this, Integer.toString(vuserid), Toast.LENGTH_SHORT);

        btnRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String redemptioncode = etRedCode.getText().toString();
                boolean valid = true;

                if (redemptioncode.isEmpty() || redemptioncode.length()!=6) {
                    etRedCode.setError("Please enter a valid 6 characters redemption code");
                    valid = false;
                }

                else if(valid == true){

                    // Response received from the server
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {
                                    String eventname = jsonResponse.getString("eventname");
                                    String eventlocation = jsonResponse.getString("eventlocation");
                                    String startdate = jsonResponse.getString("startdate");
                                    String enddate = jsonResponse.getString("enddate");
                                    String starttime = jsonResponse.getString("starttime");
                                    String endtime = jsonResponse.getString("endtime");

                                    Intent intent = new Intent(VisiRedeemQRcodeActivity.this, QRcodeGeneratorActivity.class);
                                    intent.putExtra("eventname", eventname);
                                    intent.putExtra("eventlocation", eventlocation);
                                    intent.putExtra("startdate", startdate);
                                    intent.putExtra("enddate", enddate);
                                    intent.putExtra("starttime", starttime);
                                    intent.putExtra("endtime", endtime);
                                    intent.putExtra("redemptioncode", redemptioncode);
                                    intent.putExtra("vuserid", vuserid);
                                    VisiRedeemQRcodeActivity.this.startActivity(intent);
                                } else {
                                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(VisiRedeemQRcodeActivity.this);
                                    builder.setMessage("Redeem Failed")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    VisiRedeemRequest RedeemRequest = new VisiRedeemRequest(redemptioncode, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(VisiRedeemQRcodeActivity.this);
                    queue.add(RedeemRequest);
                }
            }
        });


    }
}
