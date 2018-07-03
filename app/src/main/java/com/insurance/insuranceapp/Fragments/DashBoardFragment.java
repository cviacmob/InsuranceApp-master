package com.insurance.insuranceapp.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.insurance.insuranceapp.Activites.CompletedCaseActivity;
import com.insurance.insuranceapp.Activites.PendingCasesActivity;
import com.insurance.insuranceapp.Activites.ReservedPaymentsActivity;
import com.insurance.insuranceapp.Activites.SavedCasesActivity;
import com.insurance.insuranceapp.Adapters.PendingCasesAdapter;
import com.insurance.insuranceapp.Datamodel.GetPaymentsInfo;
import com.insurance.insuranceapp.Datamodel.UserAccountInfo;
import com.insurance.insuranceapp.R;
import com.insurance.insuranceapp.RestAPI.InsuranceAPI;
import com.insurance.insuranceapp.Utilities.InsApp;
import com.insurance.insuranceapp.Utilities.Prefs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.Call;
import retrofit.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static com.insurance.insuranceapp.Datamodel.UserAccountInfo.getAll;

/**
 * Created by Balaji on 4/11/2018.
 */

public class DashBoardFragment extends Fragment {
    private TextView save, pending, completed,query, total_reserved, total_confirmed;
    private List<UserAccountInfo> userAccountInfoList;
    private List<GetPaymentsInfo> getPaymentsreserved;
    private List<GetPaymentsInfo> getPaymentsconfirmed;
    ProgressDialog progressDialog;
    InsApp api;
    InsuranceAPI insuranceAPI;
    private String domainurl;
    String temp = "reserved";
    private int reservedtotal;
    private int confirmedtotal;
    private PieChart pieChart;
    private List temp1;
    private List temp2;
    private List temp3;
    private TableLayout tableLayout;
    private TableRow row1,row2,row3,row4;
    private TextView text1,text2,text3,text4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragments, container, false);
        setHasOptionsMenu(true);
        domainurl = Prefs.getString("domainurl", "");

        total_reserved = (TextView) view.findViewById(R.id.tx_totalreserved);
        total_confirmed = (TextView) view.findViewById(R.id.tx_totalconfirmed);
        tableLayout = (TableLayout)view.findViewById(R.id.tableLayout);
        row1 = (TableRow)   tableLayout.getChildAt(0);
        row2 = (TableRow) tableLayout.getChildAt(1);
        row3 = (TableRow) tableLayout.getChildAt(2);
        row4 = (TableRow) tableLayout.getChildAt(3);

        text1 =(TextView)row1.getChildAt(1);
        text2 =(TextView)row2.getChildAt(1);
        text3 =(TextView)row3.getChildAt(1);
        text4 =(TextView)row4.getChildAt(1);
        userAccountInfoList = getAll();
        getpayments();
        for (UserAccountInfo user : userAccountInfoList) {
            text3.setText(user.getSaved()!=null?user.getSaved():"0");
            text4.setText(user.getSubmitted()!=null?user.getSubmitted():"0");
            text1.setText(user.getPending()!=null?user.getPending():"0");
            if(user.getRaise_query()!=null) {
                text2.setText(user.getRaise_query());
            }else if(user.getApprove_raise_query()!=null ) {
                text2.setText(user.getApprove_raise_query());
            }
        }


        return view;
    }

    public DashBoardFragment() {

    }


    private void getpayments() {

        String consultantid = "";
        progressDialog = new ProgressDialog(getActivity(), R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        com.squareup.okhttp.OkHttpClient okHttpClient = new com.squareup.okhttp.OkHttpClient();
        okHttpClient.setConnectTimeout(120000, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(120000, TimeUnit.MILLISECONDS);

        retrofit.Retrofit retrofit = new retrofit.Retrofit.Builder()
                .baseUrl(domainurl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        insuranceAPI = retrofit.create(InsuranceAPI.class);

        for(UserAccountInfo user:userAccountInfoList) {
            consultantid=user.getConsultant_id();
        }


        Call<List<GetPaymentsInfo>> call = insuranceAPI.getgetpayments(consultantid , temp);
        call.enqueue(new retrofit.Callback<List<GetPaymentsInfo>>() {
            @Override
            public void onResponse(retrofit.Response<List<GetPaymentsInfo>> response, retrofit.Retrofit retrofit) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }

                getPaymentsreserved = response.body();
                getPaymentsconfirmed = response.body();
                if(temp.equalsIgnoreCase("reserved") && getPaymentsreserved!=null){

                    for(GetPaymentsInfo getPaymentsInfo :getPaymentsreserved) {

                        reservedtotal = reservedtotal+Integer.parseInt(getPaymentsInfo.getConsultant_fee() != null ? getPaymentsInfo.getConsultant_fee() : "0");

                        reservedtotal =reservedtotal+ Integer.parseInt(getPaymentsInfo.getConsult_insentivies() != null ? getPaymentsInfo.getConsult_insentivies() : "0");

                        reservedtotal = reservedtotal+Integer.parseInt(getPaymentsInfo.getPay_conveyance() != null ? getPaymentsInfo.getPay_conveyance() : "0");

                        reservedtotal = reservedtotal+Integer.parseInt(getPaymentsInfo.getMrd_amount() != null ? getPaymentsInfo.getMrd_amount() : "0");
                    }
                    total_reserved.setText("Rs."+reservedtotal);
                    temp = "confirmed";
                    getpayments();
                }else if(temp.equalsIgnoreCase("confirmed") && getPaymentsconfirmed!=null){
                    for(GetPaymentsInfo getPaymentsInfo :getPaymentsconfirmed) {

                        confirmedtotal = confirmedtotal+Integer.parseInt(getPaymentsInfo.getConsultant_fee() != null ? getPaymentsInfo.getConsultant_fee() : "0");

                        confirmedtotal =confirmedtotal+ Integer.parseInt(getPaymentsInfo.getConsult_insentivies() != null ? getPaymentsInfo.getConsult_insentivies() : "0");

                        confirmedtotal = confirmedtotal+Integer.parseInt(getPaymentsInfo.getPay_conveyance() != null ? getPaymentsInfo.getPay_conveyance() : "0");

                        confirmedtotal = confirmedtotal+Integer.parseInt(getPaymentsInfo.getMrd_amount() != null ? getPaymentsInfo.getMrd_amount() : "0");


                    }
                    total_confirmed.setText("Rs."+confirmedtotal);
                }




            }
            @Override
            public void onFailure(Throwable t) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }

              //  Toast.makeText(getContext(), "Network Issue" + t, Toast.LENGTH_SHORT).show();

            }
        });


    }
}
