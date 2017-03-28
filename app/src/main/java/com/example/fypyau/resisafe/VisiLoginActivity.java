package com.example.fypyau.resisafe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class VisiLoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visi_login);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);

        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final TextView registerLink = (TextView) findViewById(R.id.registerLink);

        Typeface custom_font = Typeface.createFromAsset(etUsername.getContext().getAssets(),  "fonts/Bariol.ttf");
        etUsername.setTypeface(custom_font);
        etPassword.setTypeface(custom_font);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(VisiLoginActivity.this,VisiRegisterActivity.class);
                VisiLoginActivity.this.startActivity(registerIntent);
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
                                    int vuserid = jsonResponse.getInt("vuserid");
                                    String profileimage = jsonResponse.getString("profileimage");

                                    Intent intent = new Intent(VisiLoginActivity.this, VisiWlcmActivity.class);
                                    intent.putExtra("name", name);
                                    intent.putExtra("username", username);
                                    intent.putExtra("vuserid",vuserid);
                                    intent.putExtra("profileimage",profileimage);
                                    VisiLoginActivity.this.startActivity(intent);
                                } else {
                                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(VisiLoginActivity.this);
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
                    VisiLoginRequest loginRequest = new VisiLoginRequest(username, password, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(VisiLoginActivity.this);
                    queue.add(loginRequest);
                }
            }
        });


    }


}
