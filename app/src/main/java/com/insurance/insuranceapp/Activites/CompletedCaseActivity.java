package com.insurance.insuranceapp.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.insurance.insuranceapp.Adapters.PendingCasesAdapter;
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

public class CompletedCaseActivity extends AppCompatActivity {
    private PendingCasesAdapter pendingcaseAdapter;
    private List<PendingCaseListInfo> pendingInfoList;
    private ListView listView;
    ProgressDialog progressDialog;
    InsApp api;
    InsuranceAPI insuranceAPI;
    private List<UserAccountInfo> userAccountInfoList;
    private String  domainurl;
    private int mode;
    private String temp ="";
    private String temp1="saved";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_case);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mode =  getIntent().getIntExtra("Payments",0);
        domainurl = Prefs.getString("domainurl", "");
        listView = findViewById(R.id.lab_list);
        userAccountInfoList  = getAll();

        temp = "submitted";
            setTitle("Completed Cases");
            getcompleted();




    }

    private void getList(List<PendingCaseListInfo> pendingCasesActivityList) {

        pendingcaseAdapter = new PendingCasesAdapter(pendingCasesActivityList,this.getApplication());
        listView.setDivider(null);
        listView.setAdapter(pendingcaseAdapter);

    }
    private void getcompleted() {

        String consultantid = "";
        progressDialog = new ProgressDialog(CompletedCaseActivity.this, R.style.AppTheme_Dark_Dialog);
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



        Call<List<PendingCaseListInfo>> call = insuranceAPI.getpendinglist(consultantid , temp);
        call.enqueue(new retrofit.Callback<List<PendingCaseListInfo>>() {
            @Override
            public void onResponse(retrofit.Response<List<PendingCaseListInfo>> response, retrofit.Retrofit retrofit) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                pendingInfoList =  response.body();
                if (response.code() == 200) {
                    if(pendingInfoList!=null){
                        getList(pendingInfoList);
                    }else if(pendingInfoList==null){
                        AlertDialogNoData.alertdialog(CompletedCaseActivity.this);
                    }

                }

            }


            @Override
            public void onFailure(Throwable t) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }

                Toast.makeText(CompletedCaseActivity.this, "Network Issue" + t, Toast.LENGTH_SHORT).show();

            }
        });


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
            Intent in = new Intent(CompletedCaseActivity.this,MainActivity.class);
            startActivity(in);
            return true;
        }
        onBackPressed();
        return true;
    }
}
