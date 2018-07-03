package com.insurance.insuranceapp.Activites;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.insurance.insuranceapp.Datamodel.GetPaymentsInfo;
import com.insurance.insuranceapp.Datamodel.QueryInfo;
import com.insurance.insuranceapp.Datamodel.UserAccountInfo;
import com.insurance.insuranceapp.R;
import com.insurance.insuranceapp.RestAPI.InsuranceAPI;
import com.insurance.insuranceapp.Utilities.InsApp;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.Call;
import retrofit.GsonConverterFactory;

import static com.insurance.insuranceapp.Activites.DeathCliamActivity.FILE_PICKER_REQUEST_CODE;
import static com.insurance.insuranceapp.Activites.DeathCliamActivity.PERMISSIONS_REQUEST_CODE;
import static com.insurance.insuranceapp.Datamodel.UserAccountInfo.getAll;

public class QueryCasesActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_CAMERA = 10;
    private static final int MY_PERMISSION_EXTERNAL_STORAGE = 11;
    private TextView qc_query,qc_queryfile,qc_queryon;
    private EditText QueryRly;
    private TextView file,filename,title;
    ProgressDialog progressDialog;
    InsApp api;
    InsuranceAPI insuranceAPI;
    private  List<QueryInfo> queryInfo;
    private List<UserAccountInfo> userAccountInfoList;
    private Button bt_back,bt_submit;
    private String queryReply = "";
    private String userChoosenTask;
    private int REQUEST_CAMERA = 2, SELECT_FILE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_cases);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Query Cases");
        userAccountInfoList = getAll();
        qc_query = (TextView)findViewById(R.id.pending);
        qc_queryfile = (TextView)findViewById(R.id.query);
        qc_queryon = (TextView)findViewById(R.id.save);
        QueryRly = (EditText)findViewById(R.id.ed_comments);
        title = (TextView)findViewById(R.id.title31);
        file = (TextView)findViewById(R.id.file31);
        filename = (TextView)findViewById(R.id.filename31);
        bt_submit = (Button)findViewById(R.id.bt_submit);
        bt_back = (Button)findViewById(R.id.bt_back);
        file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getQueryCases();
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryReply = QueryRly.getText().toString();

            }
        });

    }

    private void textviewValues(){


        for(QueryInfo qry:queryInfo) {
            qc_query.setText(qry.getQc_case_query());
            title.setText(qry.getQc_case_query());
            qc_queryfile.setText(qry.getQc_query_file());
            String srt = qry.getQc_query_on();
            try {
                String sr=    modifyDateLayout(srt);
                qc_queryon.setText(sr);
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
    }
    private String modifyDateLayout(String inputDate) throws ParseException{
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(inputDate);
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
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
            Intent in = new Intent(QueryCasesActivity.this,MainActivity.class);
            startActivity(in);
            return true;
        }
        onBackPressed();
        return true;
    }

    private void selectImage() {
        cameraIntent();

    }

    private void cameraIntent() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CAMERA);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSION_CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                }
            }
            break;
            case MY_PERMISSION_EXTERNAL_STORAGE: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                if (grantResults.length > 1 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, SELECT_FILE);
            }
            break;


        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

           if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }


    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
            // uploadProfileImage(destination.getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), thumbnail, "", null);
        /*Picasso.with(this).load(path).resize(350, 350).transform(new CircleTransform())
                .centerCrop().memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(ivImage);*/
        //ivImage.setImageBitmap(thumbnail);
        filename.setText(path);

    }


    private void checkPermissionsAndOpenFilePicker() {
        String permission = Manifest.permission.READ_EXTERNAL_STORAGE;

        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                showError();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{permission}, PERMISSIONS_REQUEST_CODE);
            }
        }
    }
    private void showError() {
        Toast.makeText(this, "Allow external storage reading", Toast.LENGTH_SHORT).show();
    }




    private void getQueryCases() {

        String consultantid = "";
        progressDialog = new ProgressDialog(QueryCasesActivity.this, R.style.AppTheme_Dark_Dialog);
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
        String status  = "aprove_raise_query";
        Call<List<QueryInfo>> call = insuranceAPI.getquerycases(consultantid , status);
        call.enqueue(new retrofit.Callback<List<QueryInfo>>() {
            @Override
            public void onResponse(retrofit.Response<List<QueryInfo>> response, retrofit.Retrofit retrofit) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                queryInfo = response.body();
                if(queryInfo!=null) {
                    textviewValues();

                }



            }
            @Override
            public void onFailure(Throwable t) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }

                Toast.makeText(QueryCasesActivity.this, "Network Issue" + t, Toast.LENGTH_SHORT).show();

            }
        });


    }
}
