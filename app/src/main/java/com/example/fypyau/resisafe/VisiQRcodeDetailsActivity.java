package com.example.fypyau.resisafe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.IOException;

public class VisiQRcodeDetailsActivity extends AppCompatActivity {

    private static final String TAG = VisiQRcodeDetailsActivity.class.getSimpleName();
    TextView tvEventname, tvEventLocation, tvStartdate, tvEnddate;
    ImageView qrcodeimage;
    Qrcode qrcodedata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visi_qrcode_details);

        tvEventname = (TextView) findViewById(R.id.tvEventname);
        tvEventLocation = (TextView) findViewById(R.id.tvEventlocation);
        tvStartdate = (TextView) findViewById(R.id.tvStartdate);
        tvEnddate = (TextView) findViewById(R.id.tvEnddate);

        qrcodeimage = (ImageView) findViewById(R.id.qrcodeimage);

        Intent i = getIntent();
        qrcodedata = (Qrcode) i .getSerializableExtra("qrcodedata");
        tvEventname.setText(qrcodedata.getEventname());
        tvEventLocation.setText(qrcodedata.getEventlocation());
        tvStartdate.setText(qrcodedata.getEventstartdate() + ", " + qrcodedata.getEventstarttime());
        tvEnddate.setText(qrcodedata.getEventenddate() + ", " + qrcodedata.getEventendtime());
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(qrcodedata.getQrcode(), BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qrcodeimage.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
