package com.example.fypyau.resisafe;

import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick Yau on 1/31/2017.
 */

public class VisiQRListFragment extends Fragment {
    private static final String TAG = VisiQRListFragment.class.getSimpleName();
    View MyView;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    VisiQRcodeListCustomAdapter adapter;
    List<Qrcode> qrcodedata;
    ProgressDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MyView = inflater.inflate(R.layout.visi_qrlist_layout,container,false);
        recyclerView = (RecyclerView) MyView.findViewById(R.id.recycler_view);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Retrieving Data...");
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        try{
            qrcodedata = (List<Qrcode>) InternalStorage.readObject(getActivity(), "qrcodedata");
        }catch (IOException e) {
            qrcodedata = new ArrayList<>();
            Log.e(TAG, e.getMessage());
        }catch (ClassNotFoundException e) {
            qrcodedata = new ArrayList<>();
            Log.e(TAG, e.getMessage());
        }
        adapter = new VisiQRcodeListCustomAdapter(getActivity(), qrcodedata);
        gridLayoutManager = new GridLayoutManager(getActivity(),1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        if (qrcodedata.isEmpty()){
            Toast.makeText(getActivity(),"The list is empty", Toast.LENGTH_SHORT).show();
        }
        progressDialog.dismiss();
        return MyView;
    }
}
