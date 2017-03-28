package com.example.fypyau.resisafe;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

public class VisiRegisterActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private RegisterVisitor registerVisitor = new RegisterVisitor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visi_register);


        final TextView registertile = (TextView) findViewById(R.id.registertitle);
        final TextView tvNationality = (TextView) findViewById(R.id.tvNationality);
        final EditText etFullname = (EditText) findViewById(R.id.etFullName);
        final EditText etUsername = (EditText) findViewById(R.id.tvName);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etICnum = (EditText) findViewById(R.id.etICnum);
        final EditText etCarplatenum = (EditText) findViewById(R.id.etCarplatenum);
        final EditText etContact = (EditText) findViewById(R.id.etContact);
        final Spinner nationalityspinner = (Spinner) findViewById(R.id.nationalityspinner);
        final Spinner ictypesspinner = (Spinner) findViewById(R.id.ictypesspinner);
        final Button bRegister = (Button) findViewById(R.id.bRegister);
        final EditText etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        bRegister.getBackground().setAlpha(45);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item,R.id.nationalityspinner);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_item,R.id.ictypesspinner);

        Typeface custom_font = Typeface.createFromAsset(etUsername.getContext().getAssets(),  "fonts/Bariol.ttf");
        registertile.setTypeface(custom_font);
        tvNationality.setTypeface(custom_font);
        etFullname.setTypeface(custom_font);
        etUsername.setTypeface(custom_font);
        etPassword.setTypeface(custom_font);
        etConfirmPassword.setTypeface(custom_font);
        etICnum.setTypeface(custom_font);
        etContact.setTypeface(custom_font);
        etCarplatenum.setTypeface(custom_font);

        etConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = etPassword.getText().toString();
                String confirmpassword = etConfirmPassword.getText().toString();
                if (!confirmpassword.equals(password))
                    etConfirmPassword.setError("Both of the password are not match.");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = etFullname.getText().toString();
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                final String icnum = etICnum.getText().toString();
                final String nationality = nationalityspinner.getSelectedItem().toString();
                final String ictypes = ictypesspinner.getSelectedItem().toString();
                final String carplatenum = etCarplatenum.getText().toString();
                final String contactnum = etContact.getText().toString();
                boolean valid = true;

                if (name.isEmpty() || name.length() > 32) {
                    etFullname.setError("Please enter a valid name as per identity card");
                    valid = false;
                }
                if (username.isEmpty()) {
                    etUsername.setError("Please enter a valid username");
                    valid = false;
                }
                if (password.isEmpty() || password.length() < 8) {
                    etPassword.setError("Please enter a valid password with a minimum length of 8 characters");
                    valid = false;
                }
                if (!password.equals(etConfirmPassword.getText().toString())){
                    etConfirmPassword.setError("Both of the password are not match");
                    valid = false;
                }
                if (ictypes.equals("NRIC") && icnum.isEmpty() || ictypes.equals("NRIC") && icnum.length() != 12) {
                    etICnum.setError("Please enter a valid identity card number");
                    valid = false;
                }
                if (ictypes.equals("Passport Number") && icnum.isEmpty() || ictypes.equals("Passport Number") && icnum.length() != 9) {
                    etICnum.setError("Please enter a valid passport number");
                    valid = false;
                }
                if (carplatenum.isEmpty()) {
                    etCarplatenum.setError("Please enter a valid car plate number");
                    valid = false;
                }
                if (contactnum.isEmpty() || contactnum.length() > 20) {
                    etContact.setError("Please enter a valid contact number");
                    valid = false;
                }else if (valid == true){
                    registerVisitor.setFullname(name);
                    registerVisitor.setUsername(username);
                    registerVisitor.setPassword(password);
                    registerVisitor.setIcnum(icnum);
                    registerVisitor.setNationality(nationality);
                    registerVisitor.setCarplatenum(carplatenum);
                    registerVisitor.setContactnum(contactnum);

                    Intent i = new Intent(VisiRegisterActivity.this, UploadImageActivity.class);
                    i.putExtra("registervisitor", registerVisitor);
                    VisiRegisterActivity.this.startActivity(i);
            }
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "VisiRegister Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.fypyau.resisafe/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "VisiRegister Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.fypyau.resisafe/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
