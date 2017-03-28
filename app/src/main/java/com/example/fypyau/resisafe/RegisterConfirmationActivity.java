package com.example.fypyau.resisafe;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class RegisterConfirmationActivity extends AppCompatActivity {

    TextView tvName, tvCarplatenum, tvIcnum, tvContactnum, tvlinkYES, tvlinkNO;
    ImageView imageView;
    ProgressDialog progressDialog;
    RegisterVisitor registerVisitor = new RegisterVisitor();
    Bitmap bitmap;
    Uri filePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_confirmation);

        tvName = (TextView) findViewById(R.id.tvName);
        tvCarplatenum = (TextView) findViewById(R.id.tvCarplatenum);
        tvIcnum = (TextView) findViewById(R.id.tvIcnum);
        tvContactnum = (TextView) findViewById(R.id.tvContactnum);
        tvlinkYES = (TextView) findViewById(R.id.tvlinkYES);
        tvlinkNO = (TextView) findViewById(R.id.tvlinkNO);
        imageView = (ImageView) findViewById(R.id.imageView);

        Intent i = getIntent();
        registerVisitor = (RegisterVisitor) i.getSerializableExtra("registervisitor");
        filePath = (Uri) i.getParcelableExtra("filePath");
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }


        imageView.setImageBitmap(bitmap);
        tvName.setText(registerVisitor.getFullname());
        tvCarplatenum.setText(registerVisitor.getCarplatenum());
        tvIcnum.setText(registerVisitor.getIcnum());
        tvContactnum.setText(registerVisitor.getContactnum());

        tvlinkYES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerData();
            }
        });

        tvlinkNO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterConfirmationActivity.this);
                builder.setTitle("Quit the registration?");
                builder.setMessage("Do you want to quit the registration? Everything you entered previously will be lost!");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(RegisterConfirmationActivity.this, VisiLoginActivity.class);
                        RegisterConfirmationActivity.this.startActivity(i);
                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();
            }
        });

    }

    private void registerData(){

        String name = registerVisitor.getFullname();
        String username = registerVisitor.getUsername();
        String password = registerVisitor.getPassword();
        String nationality = registerVisitor.getNationality();
        String icnum = registerVisitor.getIcnum();
        String carplatenum = registerVisitor.getCarplatenum();
        String contactnum = registerVisitor.getContactnum();
        String image = getStringImage(bitmap);

        progressDialog = new ProgressDialog(RegisterConfirmationActivity.this);
        progressDialog.setTitle("Registering....");
        progressDialog.setMessage("This might take a moment for the photo to be uploaded...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        Response.Listener<String> responselistener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject JSONresponse = new JSONObject(response);
                    boolean success = JSONresponse.getBoolean("success");
                    progressDialog.dismiss();

                    if (success) {
                        AlertDialog.Builder successmsg = new AlertDialog.Builder(RegisterConfirmationActivity.this);
                        successmsg.setMessage("Register Successful!");
                        successmsg.setPositiveButton("Back to Login Page",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent registersuccessintent = new Intent(RegisterConfirmationActivity.this, VisiLoginActivity.class);
                                        RegisterConfirmationActivity.this.startActivity(registersuccessintent);
                                    }
                                });
                        successmsg.create()
                                .show();
                    } else {
                        AlertDialog.Builder alertmsg = new AlertDialog.Builder(RegisterConfirmationActivity.this);
                        alertmsg.setMessage("Register Failed")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }

            }
        };
        VisiRegisterRequest RegisterRequest = new VisiRegisterRequest(name, username, password, nationality, icnum, carplatenum, contactnum, image, responselistener);
        RequestQueue queue = Volley.newRequestQueue(RegisterConfirmationActivity.this);
        queue.add(RegisterRequest);
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}
