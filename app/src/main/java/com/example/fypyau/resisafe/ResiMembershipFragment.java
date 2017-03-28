package com.example.fypyau.resisafe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by Nick Yau on 3/10/2017.
 */

public class ResiMembershipFragment extends Fragment {

    private static final String TAG = ResiMembershipFragment.class.getSimpleName();
    View MyView;
    TextView tvMembershipstatus, tvLastpaiddate, tvAmount, tvNextpayment, tvValiddays, tvValidtype;
    private static final String url = "https://resisafe.000webhostapp.com/GetMembershipResiSafe.php?id=";
    ImageView imageView;
    ResiMembership memberdata = new ResiMembership();
    ProgressDialog pDialog;
    String date;
    Date currentdate, nextdate;
    int ruserid;
    Calendar cdate =  Calendar.getInstance(),ndate = Calendar.getInstance();
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    Button btpay;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MyView = inflater.inflate(R.layout.activity_resi_membership, container, false);

        tvMembershipstatus = (TextView) MyView.findViewById(R.id.tvMembershipstatus);
        tvLastpaiddate = (TextView) MyView.findViewById(R.id.tvLastpaiddate);
        tvAmount = (TextView) MyView.findViewById(R.id.tvAmount);
        tvNextpayment = (TextView) MyView.findViewById(R.id.tvNextpayment);
        tvValiddays = (TextView) MyView.findViewById(R.id.tvValiddays);
        tvValidtype = (TextView) MyView.findViewById(R.id.tvValidtype);
        imageView = (ImageView) MyView.findViewById(R.id.imageView);
        btpay = (Button) MyView.findViewById(R.id.btpay);
        ruserid = ResiWlcmActivity.ruserid;
        date = getCurrentDate();
        showLoading();
        //test();
        getMembershipstatus();

        btpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),ResiPaymentActivity.class);
                i.putExtra("memberdata", memberdata);
                getActivity().startActivity(i);
            }
        });
        return MyView;
    }

    private void getMembershipstatus(){
        JsonArrayRequest memberReq = new JsonArrayRequest(url + ruserid,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());


                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                memberdata.setRuserid(obj.getString("ruser_id"));
                                memberdata.setMembershipstatus(obj.getString("membershipstatus"));
                                memberdata.setAmount(obj.getString("amount"));
                                memberdata.setLastpayment(obj.getString("lastpayment"));
                                memberdata.setNextpayment(obj.getString("nextpayment"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        String membership = memberdata.getMembershipstatus();
                        try {
                            nextdate = formatter.parse(memberdata.getNextpayment());
                            currentdate = formatter.parse(date);
                            ndate.setTime(nextdate);
                            cdate.setTime(currentdate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        tvNextpayment.setText(memberdata.getNextpayment());
                        if (membership.equals("Valid")){
                            imageView.setImageResource(R.drawable.tick);
                            tvMembershipstatus.setText("Your membership is valid.");
                            tvMembershipstatus.setTextColor(ContextCompat.getColor(getActivity(), R.color.checkingreen));
                            long diff = ndate.getTimeInMillis() - cdate.getTimeInMillis();
                            long days = diff / (24 * 60 * 60 * 1000);
                            tvValidtype.setText("Validity period:");
                            tvValiddays.setText(days + " days");
                            btpay.setVisibility(View.GONE);
                            tvAmount.setVisibility(View.GONE);
                        } else {
                            imageView.setImageResource(R.drawable.exclamationmark);
                            tvMembershipstatus.setText("Your membership fee is overdue!");
                            tvMembershipstatus.setTextColor(ContextCompat.getColor(getActivity(), R.color.checkoutred));
                            long diff = cdate.getTimeInMillis() - ndate.getTimeInMillis();
                            long days = diff / (24 * 60 * 60 * 1000);
                            tvValidtype.setText("Overdue period:");
                            tvValiddays.setText(days + " days");
                            btpay.setVisibility(View.VISIBLE);
                            tvAmount.setVisibility(View.VISIBLE);
                        }
                        tvAmount.setText(memberdata.getAmount());
                        tvLastpaiddate.setText(memberdata.getLastpayment());
                        hidePDialog();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage().toString());
                hidePDialog();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(memberReq);
    }
    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    public void showLoading(){
        pDialog = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        pDialog.setTitle("Retrieving Data...");
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
    }

    private String getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        String dateX = day + "/" + month + "/" + year;
        return dateX;
    }
}
