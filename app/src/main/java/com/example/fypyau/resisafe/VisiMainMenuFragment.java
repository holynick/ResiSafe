package com.example.fypyau.resisafe;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by Nick Yau on 1/31/2017.
 */

public class VisiMainMenuFragment extends Fragment implements View.OnClickListener{
    View MyView;
    ImageView btRedeem, btQrcodelist, btContact;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MyView = inflater.inflate(R.layout.visi_main_layout,container,false);
        btRedeem = (ImageView) MyView.findViewById(R.id.btRedeem);
        btQrcodelist = (ImageView) MyView.findViewById(R.id.btQrcodelist);
        btContact = (ImageView) MyView.findViewById(R.id.btContact);
        Bundle bundle = this.getArguments();
        final int vuserid = bundle.getInt("vuserid", -1);

        btRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),VisiRedeemQRcodeActivity.class);
                i.putExtra("vuserid",vuserid);
                startActivity(i);
            }
        });

        btQrcodelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.visicontent_frame,
                                new VisiQRListFragment())
                        .commit();
            }
        });

        btContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.visicontent_frame,
                                new CommunicationFragment())
                        .commit();
            }
        });
        return MyView;
    }

    @Override
    public void onClick(View v) {

    }
}
