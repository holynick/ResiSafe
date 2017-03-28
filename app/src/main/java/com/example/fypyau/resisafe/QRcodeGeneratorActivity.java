package com.example.fypyau.resisafe;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.encoder.QRCode;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QRcodeGeneratorActivity extends AppCompatActivity {
    private static final String TAG = QRcodeGeneratorActivity.class.getSimpleName();
    private TextView tveventname, tveventlocation, tvstarttime, tvendtime, tvstartdate, tvenddate;
    private Button btnOK;
    String text2qr;
    ImageView qrcodeimage;
    List<Qrcode> qrcodedata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_generator);

        tveventname = (TextView) findViewById(R.id.eventname);
        tveventlocation = (TextView) findViewById(R.id.eventlocation);
        tvstarttime = (TextView) findViewById(R.id.starttime);
        tvendtime= (TextView) findViewById(R.id.endtime);
        tvstartdate = (TextView) findViewById(R.id.startdate);
        tvenddate = (TextView) findViewById(R.id.enddate);

        btnOK = (Button) findViewById(R.id.btnOK);
        qrcodeimage = (ImageView) findViewById(R.id.qrcodeimageX);

        Intent intent = getIntent();
        final String eventname = intent.getStringExtra("eventname");
        final String eventlocation = intent.getStringExtra("eventlocation");
        final String startdate = intent.getStringExtra("startdate");
        final String enddate = intent.getStringExtra("enddate");
        final String starttime= intent.getStringExtra("starttime");
        final String endtime = intent.getStringExtra("endtime");
        final String eventcode = intent.getStringExtra("redemptioncode");
        final int vuserid = intent.getIntExtra("vuserid", -1);
        Random rnd =  new Random();
        String randomcode = Integer.toString(100000 + rnd.nextInt(900000));
        text2qr = eventcode + randomcode + Integer.toString(vuserid);
        try{
            qrcodedata = (List<Qrcode>) InternalStorage.readObject(QRcodeGeneratorActivity.this, "qrcodedata");
        }catch (IOException e) {
            qrcodedata = new ArrayList<>();
            Log.e(TAG, e.getMessage());
        }catch (ClassNotFoundException e) {
            qrcodedata = new ArrayList<>();
            Log.e(TAG, e.getMessage());
        }

        if (eventlocation.equals("Swimming Pool")){
           tveventlocation.setBackgroundResource(R.drawable.eventbackgroundpool);
        } else if (eventlocation.equals("Gym")){
            tveventlocation.setBackgroundResource(R.drawable.eventbackgroundgym);
        } else if (eventlocation.equals("Badminton Court")){
            tveventlocation.setBackgroundResource(R.drawable.eventbackgroundbadcourt);
        } else if (eventlocation.equals("Public Park")){
            tveventlocation.setBackgroundResource(R.drawable.eventbackgroundpark);
        }
        else
            tveventlocation.setBackgroundResource(R.drawable.eventbackgroundhouse);

        tveventname.setText(eventname);
        tveventlocation.setText(eventlocation);
        tvstartdate.setText(startdate);
        tvenddate.setText(enddate);
        tvstarttime.setText(starttime);
        tvendtime.setText(endtime);

        Toast.makeText(getBaseContext(),"The QR code has been automatically saved in to your device. You can retrieve from the \"QR code List.\"",Toast.LENGTH_LONG ).show();

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(QRcodeGeneratorActivity.this,VisiNavigatorMainActivity.class);
                QRcodeGeneratorActivity.this.startActivity(i);
                finish();
            }
        });
        Response.Listener<String> responselistener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject JSONresponse = new JSONObject(response);
                    boolean success = JSONresponse.getBoolean("success");
                    if (success) {
                        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                        try{
                            BitMatrix bitMatrix = multiFormatWriter.encode(text2qr, BarcodeFormat.QR_CODE,200,200);
                            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                            qrcodeimage.setImageBitmap(bitmap);
                            Qrcode qrcode = new Qrcode();
                            qrcode.setEventname(eventname);
                            qrcode.setEventstartdate(startdate);
                            qrcode.setEventenddate(enddate);
                            qrcode.setEventstarttime(starttime);
                            qrcode.setEventendtime(endtime);
                            qrcode.setQrcode(text2qr);
                            qrcode.setEventcode(eventcode);
                            qrcode.setEventlocation(eventlocation);
                            qrcodedata.add(qrcode);
                            try {
                                InternalStorage.writeObject(QRcodeGeneratorActivity.this, "qrcodedata", qrcodedata);
                            }catch (IOException e) {
                                Log.e(TAG, e.getMessage());
                            }
                        } catch (WriterException e) {
                            e.printStackTrace();
                        }

                    } else {
                        AlertDialog.Builder alertmsg = new AlertDialog.Builder(QRcodeGeneratorActivity.this);
                        alertmsg.setMessage("An error has occured.")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        VisiQRcodeRegisterRequest RegisterRequest = new VisiQRcodeRegisterRequest(text2qr, enddate, responselistener);
        RequestQueue queue = Volley.newRequestQueue(QRcodeGeneratorActivity.this);
        queue.add(RegisterRequest);


    }
}
