package com.example.fypyau.resisafe;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class LoginTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_type);

        final ImageView bResident = (ImageView) findViewById(R.id.bResident);
        final ImageView bVisitor = (ImageView) findViewById(R.id.bVisitor);

        bResident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent residentintent = new Intent(LoginTypeActivity.this,ResiLoginActivity.class);
                LoginTypeActivity.this.startActivity(residentintent);
            }
        });

        bVisitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent visitorintent = new Intent(LoginTypeActivity.this,VisiLoginActivity.class);
                LoginTypeActivity.this.startActivity(visitorintent);
            }
        });
    }
}
