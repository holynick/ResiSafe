package com.example.fypyau.resisafe;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

/**
 * Created by Nick Yau on 1/30/2017.
 */

public class ResiMainMenuFragment extends Fragment implements View.OnClickListener{

    View MyView;

    ImageView btShowVisitingData, btShowEvent, btShowVisitor, btShowMembership, btShowContact;
    Button btnScanner;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MyView = inflater.inflate(R.layout.resi_main_layout,container,false);
        getActivity().setTitle("Main Menu");
        ImageButton btnCreateEvent = (ImageButton) MyView.findViewById(R.id.btnCreateEvent);
        btnScanner = (Button) MyView.findViewById(R.id.btScanner);
        btShowVisitingData = (ImageView) MyView.findViewById(R.id.btShowVisitingData);
        btShowEvent = (ImageView) MyView.findViewById(R.id.btShowEvents);
        btShowVisitor = (ImageView) MyView.findViewById(R.id.btShowVisitor);
        btShowMembership = (ImageView) MyView.findViewById(R.id.btShowMembership);
        btShowContact = (ImageView) MyView.findViewById(R.id.btShowContact);


        setHasOptionsMenu(true);


        btnCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),EventCreatorActivity.class);
                startActivity(i);
            }
        });
        btnScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),QRCodeScannerActivity.class);
                startActivity(i);
            }
        });
        btShowEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame,
                                new ResiEventTabFragment()).addToBackStack(null)
                        .commit();
            }
        });
        btShowVisitingData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame,
                                new ResiVisitingDataMenuFragment()).addToBackStack(null)
                        .commit();
            }
        });
        btShowContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame,
                                new CommunicationFragment()).addToBackStack(null)
                        .commit();
            }
        });
        btShowVisitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame,
                                new ResiUserTabFragment()).addToBackStack(null)
                        .commit();
            }
        });
        btShowMembership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame,
                                new ResiMembershipFragment()).addToBackStack(null)
                        .commit();
            }
        });
        return MyView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.clear();

    }







    @Override
    public void onClick(View v) {


        }

    }

