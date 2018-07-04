package com.insurance.insuranceapp.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.insurance.insuranceapp.Adapters.PendingCasesAdapter;
import com.insurance.insuranceapp.Datamodel.PendingCaseListInfo;
import com.insurance.insuranceapp.Datamodel.PendingInfo;
import com.insurance.insuranceapp.Datamodel.UserAccountInfo;
import com.insurance.insuranceapp.R;
import com.insurance.insuranceapp.RestAPI.InsuranceAPI;
import com.insurance.insuranceapp.Utilities.AlertDialogNoData;
import com.insurance.insuranceapp.Utilities.InsApp;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import retrofit.Call;
import retrofit.GsonConverterFactory;


import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.insurance.insuranceapp.Datamodel.UserAccountInfo.getAll;

public class PendingCasesActivity extends AppCompatActivity {
    private ListView listView;
    private PendingCasesAdapter pendingcaseAdapter;
    private List<PendingCaseListInfo> pendingInfoList;
    public static final int RequestPermissionCode = 1;
    ProgressDialog progressDialog;
    InsApp api;
    InsuranceAPI insuranceAPI;
    private List<UserAccountInfo> userAccountInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_cases);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Pending Cases");
        userAccountInfoList  = getAll();
        listView = findViewById(R.id.lab_list);

        getpendinglist();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final PendingCaseListInfo pendingInfo= (PendingCaseListInfo) parent.getAdapter().getItem(position);
                String Block_type = pendingInfo.getCase_type();
                String Block_name = pendingInfo.getCase_type_id();
                if(Block_type!=null) {
                    if(Block_type.equalsIgnoreCase("default"))
                    {
                        if(Block_name.equalsIgnoreCase("1"))
                        {
                            Intent in = new Intent(PendingCasesActivity.this, HospitalBlockActivity.class);
                            in.putExtra("data",pendingInfo);
                            startActivity(in);
                        } else if(Block_name.equalsIgnoreCase("2")){
                            Intent in = new Intent(PendingCasesActivity.this, PatientBlockActivity.class);
                            in.putExtra("data",pendingInfo);
                            startActivity(in);
                        } else if(Block_name.equalsIgnoreCase("3")){
                            Intent in = new Intent(PendingCasesActivity.this, SMEActivity.class);
                            in.putExtra("data",pendingInfo);
                            startActivity(in);
                        }
                        else if(Block_name.equalsIgnoreCase("4")){
                            Intent in = new Intent(PendingCasesActivity.this, DeathCliamActivity.class);
                            in.putExtra("data",pendingInfo);
                            startActivity(in);
                        }else if(Block_name.equalsIgnoreCase("5")){
                            Intent in = new Intent(PendingCasesActivity.this, DisabilityActivity.class);
                            in.putExtra("data",pendingInfo);
                            startActivity(in);
                        }else if(Block_name.equalsIgnoreCase("6")){
                            Intent in = new Intent(PendingCasesActivity.this, PersonalAccidentActivity.class);
                            in.putExtra("data",pendingInfo);
                            startActivity(in);
                        }
                        else if(Block_name.equalsIgnoreCase("7")){
                            Intent in = new Intent(PendingCasesActivity.this, BillVerificationHospital.class);
                            in.putExtra("data",pendingInfo);
                            startActivity(in);
                        }
                        else if(Block_name.equalsIgnoreCase("8")){
                            Intent in = new Intent(PendingCasesActivity.this, BillVerificationPharmacy.class);
                            in.putExtra("data",pendingInfo);
                            startActivity(in);
                        }
                        else if(Block_name.equalsIgnoreCase("9")){
                            Intent in = new Intent(PendingCasesActivity.this, DocumentsVerification.class);
                            in.putExtra("data",pendingInfo);
                            startActivity(in);
                        }
                        else if(Block_name.equalsIgnoreCase("10")){
                            Intent in = new Intent(PendingCasesActivity.this, Cashless.class);
                            in.putExtra("data",pendingInfo);
                            startActivity(in);
                        }
                        else if(Block_name.equalsIgnoreCase("11")){
                            Intent in = new Intent(PendingCasesActivity.this, IntimationCase.class);
                            in.putExtra("data",pendingInfo);
                            startActivity(in);
                        }
                    }else if(Block_type.equalsIgnoreCase("dynamic")){
                        Intent in = new Intent(PendingCasesActivity.this, DynamicActivity.class);
                        in.putExtra("data",pendingInfo);
                        startActivity(in); //this for activity starting
                    }


                }
            }
        });
    }

    private void getList(List<PendingCaseListInfo> pendingCasesActivityList) {

        pendingcaseAdapter = new PendingCasesAdapter(pendingCasesActivityList,this.getApplication());
        listView.setDivider(null);
        listView.setAdapter(pendingcaseAdapter);

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
            Intent in = new Intent(PendingCasesActivity.this, MainActivity.class);
            startActivity(in);
            return true;
        }
        onBackPressed();
        return true;
    }

    private void getpendinglist() {

        String consultantid = "";
        progressDialog = new ProgressDialog(PendingCasesActivity.this, R.style.AppTheme_Dark_Dialog);
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

        Call<List<PendingCaseListInfo>> call = insuranceAPI.getpendinglist(consultantid , "pending");
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
                        AlertDialogNoData.alertdialog(PendingCasesActivity.this);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                Toast.makeText(PendingCasesActivity.this, "Network Issue" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }
}