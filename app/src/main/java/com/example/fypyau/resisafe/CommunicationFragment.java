package com.example.fypyau.resisafe;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Nick Yau on 3/11/2017.
 */

public class CommunicationFragment extends Fragment {

    View MyView;
    Button btnCall;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MyView = inflater.inflate(R.layout.communication_layout, container, false);
        btnCall = (Button) MyView.findViewById(R.id.btnCall);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent (Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:0391445663"));
                startActivity(callIntent);
            }
        });
        return MyView;
    }
}
