package com.example.fypyau.resisafe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

public class UploadImageActivity extends AppCompatActivity {
    private Button buttonChoose;
    private Button buttonUpload;

    private ImageView imageView;

    private Bitmap bitmap;

    private int PICK_IMAGE_REQUEST = 1;

    boolean noimage;

    private Uri filePath;

    ByteArrayOutputStream bs = new ByteArrayOutputStream();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);
        noimage = true;

        Intent i = getIntent();
        final RegisterVisitor registerVisitor = (RegisterVisitor) i.getSerializableExtra("registervisitor");


        imageView = (ImageView) findViewById(R.id.imageView);

        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(noimage) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(UploadImageActivity.this);
                    builder.setTitle("Image Error");
                    builder.setMessage("Please upload a image! We need your face for identification!");
                    builder.setNegativeButton("OK", null);
                    builder.show();
                } else if (!noimage) {
                    Intent i = new Intent(UploadImageActivity.this, RegisterConfirmationActivity.class);
                    i.putExtra("registervisitor", registerVisitor);
                    i.putExtra("filePath", filePath);
                    UploadImageActivity.this.startActivity(i);
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                imageView.setImageBitmap(bitmap);
                noimage = false;

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else{
            AlertDialog.Builder builder = new AlertDialog.Builder(UploadImageActivity.this);
            builder.setTitle("Image Error");
            builder.setMessage("You didnt select any image.");
            builder.setNegativeButton("OK", null);
            builder.show();
        }
    }


    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

}
