package com.example.fypyau.resisafe;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Nick Yau on 3/4/2017.
 */

public class ResiVisitingDataMenuFragment extends Fragment implements View.OnClickListener{
    View MyView;
    ImageView btSearchVisitingData, btShowAllVisitingData, btShowCheckIn, btShowCheckOut;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MyView = inflater.inflate(R.layout.resi_visitingdata2_layout, container, false);
        btSearchVisitingData = (ImageView) MyView.findViewById(R.id.btSearchVisitingData);
        btShowAllVisitingData = (ImageView) MyView.findViewById(R.id.btShowAllVisitingData);
        btShowCheckIn = (ImageView) MyView.findViewById(R.id.btShowCheckIn);
        btShowCheckOut = (ImageView) MyView.findViewById(R.id.btShowCheckOut);
        btShowAllVisitingData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),ResiVisitingDataActivity.class);
                i.putExtra("selection", "1");
                getActivity().startActivity(i);
            }
        });
        btSearchVisitingData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),ResiSearchVisitingDataActivity.class);
                getActivity().startActivity(i);
            }
        });
        btShowCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),ResiVisitingDataActivity.class);
                i.putExtra("selection", "2");
                getActivity().startActivity(i);
            }
        });
        btShowCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),ResiVisitingDataActivity.class);
                i.putExtra("selection", "3");
                getActivity().startActivity(i);
            }
        });
        return MyView;
    }

    @Override
    public void onClick(View v) {

    }


}
