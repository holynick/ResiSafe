package com.example.fypyau.resisafe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ResiLoginActivity extends AppCompatActivity {

    private EditText etUsername,etPassword;
    private TextView tvUsername, tvPassword, tvLogintype;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resi_login);


        etUsername = (EditText) findViewById(R.id.tvName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tvUsername = (TextView) findViewById(R.id.tvUsername);
        tvPassword = (TextView) findViewById(R.id.tvPassword);
        tvLogintype = (TextView) findViewById(R.id.tvLogintype);

        Typeface custom_font = Typeface.createFromAsset(etUsername.getContext().getAssets(),  "fonts/Bariol.ttf");
        etUsername.setTypeface(custom_font);
        etPassword.setTypeface(custom_font);
        tvUsername.setTypeface(custom_font);
        tvPassword.setTypeface(custom_font);
        tvLogintype.setTypeface(custom_font);

        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final TextView registerLink = (TextView) findViewById(R.id.registerLink);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);



        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                boolean valid = true;

                if (username.isEmpty() || username.length()>20) {
                    etUsername.setError("Please enter a valid username");
                    valid = false;
                }
                if (password.isEmpty()) {
                    etPassword.setError("Please enter a valid password");
                    valid = false;

                } else if(valid == true){

                    // Response received from the server
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {

                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                pDialog.dismiss();
                                if (success) {


                                    String name = jsonResponse.getString("name");
                                    int ruserid = jsonResponse.getInt("ruserid");
                                    String profileimage = jsonResponse.getString("profileimage");

                                    Intent intent = new Intent(ResiLoginActivity.this, ResiWlcmActivity.class);
                                    intent.putExtra("name", name);
                                    intent.putExtra("username", username);
                                    intent.putExtra("ruserid",ruserid);
                                    intent.putExtra("profileimage", profileimage);
                                    ResiLoginActivity.this.startActivity(intent);
                                    finish();
                                } else {
                                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ResiLoginActivity.this);
                                    builder.setMessage("Login Failed")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    pDialog.show();
                    ResiLoginRequest loginRequest = new ResiLoginRequest(username, password, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(ResiLoginActivity.this);
                    queue.add(loginRequest);
                }
            }
        });


    }
}
