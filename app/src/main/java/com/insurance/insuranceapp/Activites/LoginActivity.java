package com.insurance.insuranceapp.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.insurance.insuranceapp.Datamodel.ProfileInfo;
import com.insurance.insuranceapp.Datamodel.RegistrationInfo;
import com.insurance.insuranceapp.Datamodel.UserAccountInfo;
import com.insurance.insuranceapp.R;
import com.insurance.insuranceapp.RestAPI.InsuranceAPI;
import com.insurance.insuranceapp.RestAPI.ResponseJson;
import com.insurance.insuranceapp.Utilities.InsApp;
import com.insurance.insuranceapp.Utilities.Prefs;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import retrofit.GsonConverterFactory;

import static com.insurance.insuranceapp.Datamodel.UserAccountInfo.getdeletecareprovider;


public class LoginActivity extends AppCompatActivity {
    Button log;
    EditText uname,pass;
    ProgressDialog progressDialog;
    InsApp api;
    InsuranceAPI insuranceAPI;
    private String username;
    private String password;
    private  List<ProfileInfo> profileInfoList;
    private ProfileInfo profileInfo;
    private TextInputLayout textInputLayout,pass_textinput;
    private  String domainurl ="http://ayuhas.co.in";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");
        SharedPreferences.Editor editor = Prefs.edit();
        editor.putString("domainurl",domainurl);
        editor.commit();
        log = findViewById(R.id.bt_sendd);
        uname = findViewById(R.id.edt_user_name);
        pass = findViewById(R.id.edt_pass);
        textInputLayout = (TextInputLayout)findViewById(R.id.user_name);
        pass_textinput = (TextInputLayout)findViewById(R.id.pass);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 username = uname.getText().toString();
                password = pass.getText().toString();
                username = "rateesh.ayuhas@gmail.com";
                password = "04091988";
                LoginActivity dateParser = new LoginActivity();
                try {
                    password = dateParser.getparsedDate(password);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                if (!isValidEmail(username)){
                    uname.setError("Enter Valid Email");
                    return;
                }

                InsApp app = (InsApp) LoginActivity.this.getApplication();

                if(app.isNetworkStatus()){
                    if(username!=null && !username.isEmpty() ){
                        if(password!=null && !password.isEmpty()){
                            getLogin();
                        }
                        else{
                            textInputLayout.setError("Cannot be empty");
                        }
                    }else{
                        pass_textinput.setError("Cannot be empty");

                    }


                }
                else {
                    Toast.makeText(getApplicationContext(),"Check Your Internet Connection",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected boolean isValidEmail(String email) {
        String EMAILPATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAILPATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private String getparsedDate(String date) throws Exception {
        DateFormat sdf = new SimpleDateFormat("ddMMyyyy", Locale.US);
        String s1 = date;
        String s2 = null;
        Date d;
        try {
            d = sdf.parse(s1);
            s2 = (new SimpleDateFormat("yyyy-MM-dd")).format(d);

        } catch (ParseException e) {

            e.printStackTrace();
        }

        return s2;

    }
        private void getLogin() {
            progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
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
            RegistrationInfo reg =new RegistrationInfo();
            reg.setUsername(username);
            reg.setPassword(password);


            final retrofit.Call<ProfileInfo> call = insuranceAPI.getlogin(reg);
            call.enqueue(new retrofit.Callback<ProfileInfo>() {
                @Override
                public void onResponse(retrofit.Response<ProfileInfo> response, retrofit.Retrofit retrofit) {
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }

                   // profileInfoList = response.body();

                    if (response.code() == 200) {

                        profileInfo =  response.body();
                        if(profileInfo!=null){
                            getdeletecareprovider();
                            userdetails(profileInfo);
                            uname.getText().clear();
                            pass.getText().clear();
                            Intent nav = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(nav);
                            finish();

                        }else{
                            textInputLayout.setError("username incorrect");
                            pass_textinput.setError("password incorrect");
                        }

                    }else{
                        Toast.makeText(LoginActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    }

                }


                @Override
                public void onFailure(Throwable t) {
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }

                  //  Toast.makeText(LoginActivity.this, "Network Issue" + t, Toast.LENGTH_SHORT).show();

                }
            });


        }


        private void userdetails(ProfileInfo profileInfo){

            UserAccountInfo userAccountInfo = new UserAccountInfo();
            userAccountInfo.setAadhar_card_number(profileInfo.getAadhar_card_number());
            userAccountInfo.setAccount_name(profileInfo.getAccount_name());
            userAccountInfo.setAccount_number(profileInfo.getAccount_number());
            userAccountInfo.setAgreementNumber(profileInfo.getAgreementNumber());
            userAccountInfo.setBank_name(profileInfo.getBank_name());
            userAccountInfo.setBranch_location(profileInfo.getBranch_location());
            userAccountInfo.setCity_id(profileInfo.getCity_id());
            userAccountInfo.setConsultant_email(profileInfo.getConsultant_email());
            userAccountInfo.setConsultant_id(profileInfo.getConsultant_id());
            userAccountInfo.setConsultant_Name(profileInfo.getConsultant_Name());
            userAccountInfo.setDate_of_birth(profileInfo.getDate_of_birth());
            userAccountInfo.setDriving_license_number(profileInfo.getDriving_license_number());
            userAccountInfo.setFather_name(profileInfo.getFather_name());
            userAccountInfo.setIfsc_code(profileInfo.getIfsc_code());
            userAccountInfo.setNickname(profileInfo.getNickname());
            userAccountInfo.setPermanent_address(profileInfo.getPermanent_address());
            userAccountInfo.setPincode(profileInfo.getPincode());
            userAccountInfo.setPresent_address(profileInfo.getPresent_address());
            userAccountInfo.setPrimary_phone_no(profileInfo.getPrimary_phone_no());
            userAccountInfo.setQualification(profileInfo.getQualification());
            userAccountInfo.setSecondary_phone_no(profileInfo.getSecondary_phone_no());
            userAccountInfo.setState_id(profileInfo.getState_id());
            userAccountInfo.setStatus(profileInfo.getStatus());
            userAccountInfo.setPending(profileInfo.getPending());
            userAccountInfo.setSaved(profileInfo.getSaved());
            userAccountInfo.setSubmitted(profileInfo.getSubmitted());
            userAccountInfo.setRaise_query(profileInfo.getRaise_query());
            userAccountInfo.setApprove_raise_query(profileInfo.getApprove_raise_query());
            userAccountInfo.save();



        }


    }