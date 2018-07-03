package com.insurance.insuranceapp.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.insurance.insuranceapp.Adapters.PendingCasesAdapter;
import com.insurance.insuranceapp.Adapters.paymentsListAdapter;
import com.insurance.insuranceapp.Datamodel.GetPaymentsInfo;
import com.insurance.insuranceapp.Datamodel.PendingCaseListInfo;
import com.insurance.insuranceapp.Datamodel.UserAccountInfo;
import com.insurance.insuranceapp.R;
import com.insurance.insuranceapp.RestAPI.InsuranceAPI;
import com.insurance.insuranceapp.Utilities.AlertDialogNoData;
import com.insurance.insuranceapp.Utilities.InsApp;
import com.insurance.insuranceapp.Utilities.Prefs;

import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.Call;
import retrofit.GsonConverterFactory;

import static com.insurance.insuranceapp.Datamodel.UserAccountInfo.getAll;

public class ReservedPaymentsActivity extends AppCompatActivity {
    private String mode;
    private TableLayout tableLayout;
    private TableRow row1,row2,row3,row4,row5,row6,row7,row9;
    private TextView claim_number,patientname,blockname,convanceypay,TA,MRD,incentive,totalamt;
    ProgressDialog progressDialog;
    InsApp api;
    InsuranceAPI insuranceAPI;
    private List<UserAccountInfo> userAccountInfoList;
    private String domainurl;

    private PendingCaseListInfo pendingCaseListInfo;
    private String temp= "";
    private ListView listview;
    private paymentsListAdapter paymentsListAdapter;
    private List<GetPaymentsInfo> getPaymentsInfoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_case);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         mode =  getIntent().getStringExtra("payments");
        userAccountInfoList  = getAll();
        domainurl = Prefs.getString("domainurl", "");
        pendingCaseListInfo = (PendingCaseListInfo) getIntent().getSerializableExtra("object");
        tableLayout = (TableLayout)findViewById(R.id.tableLayout);
        listview = (ListView)findViewById(R.id.lab_list);




       if(mode.equalsIgnoreCase("Confirmed")){
           setTitle("Confirmed Payments");
           temp = "Confirmed";
           getpayments();
       }else if(mode.equalsIgnoreCase("Reserved")){
           setTitle("Reserved Payments");
           temp = "Reserved";
           getpayments();


       }



    }

private  void getdate(List<GetPaymentsInfo> getPaymentsInfoList){
    paymentsListAdapter = new paymentsListAdapter(getPaymentsInfoList,this.getApplication());
    listview.setDivider(null);
    listview.setAdapter(paymentsListAdapter);
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_home) {
            Intent in = new Intent(ReservedPaymentsActivity.this,MainActivity.class);
            startActivity(in);
            return true;
        }
        onBackPressed();
        return true;
    }


    private void getpayments() {

        String consultantid = "";
        progressDialog = new ProgressDialog(ReservedPaymentsActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        com.squareup.okhttp.OkHttpClient okHttpClient = new com.squareup.okhttp.OkHttpClient();
        okHttpClient.setConnectTimeout(120000, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(120000, TimeUnit.MILLISECONDS);

        retrofit.Retrofit retrofit = new retrofit.Retrofit.Builder()
                .baseUrl(getBaseContext().getString(R.string.DomainURL))
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
                getPaymentsInfoList = response.body();
              if(getPaymentsInfoList!=null){
                  getdate(getPaymentsInfoList);
              }




            }
            @Override
            public void onFailure(Throwable t) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }

                Toast.makeText(ReservedPaymentsActivity.this, "Network Issue" + t, Toast.LENGTH_SHORT).show();

            }
        });


    }
}
