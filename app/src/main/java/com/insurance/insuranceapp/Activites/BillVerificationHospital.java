package com.insurance.insuranceapp.Activites;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.insurance.insuranceapp.Datamodel.BillingVerifiHospInfo;
import com.insurance.insuranceapp.Datamodel.ImagesSaveInfo;
import com.insurance.insuranceapp.Datamodel.PendingCaseListInfo;
import com.insurance.insuranceapp.Datamodel.PendingInfo;
import com.insurance.insuranceapp.Datamodel.SMEInfo;
import com.insurance.insuranceapp.Datamodel.TriggersInfo;
import com.insurance.insuranceapp.Datamodel.UserAccountInfo;
import com.insurance.insuranceapp.R;
import com.insurance.insuranceapp.RestAPI.InsuranceAPI;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import static com.insurance.insuranceapp.Activites.PatientBlockActivity.PERMISSIONS_REQUEST_CODE;
import static com.insurance.insuranceapp.Datamodel.ImagesSaveInfo.getimages;

public class BillVerificationHospital extends AppCompatActivity implements
        View.OnClickListener{
    private TextView[] filenameholder;
    private EditText[] ed;
    private List<TriggersInfo> triggersInfoList;
    private List<String> triggerListId;
    List<EditText> allEds = new ArrayList<EditText>();
    private TextView filename;
    private String triggerID = "";
    private int count = 0;
    private String temp1 = "";
    private List<String> triggeranswer;
    private List<File> triggerlistitem;
    private File file;
    private RequestBody fbody1,fbody2,fbody3,fbody4,fbody5,fbody6,fbody7,fbody8,fbody9,fbody10,fbody11,fbody12,fbody13;
    private List<File> triggerfiles = new ArrayList<>();
    private static final int MY_PERMISSION_CAMERA = 10;
    private static final int MY_PERMISSION_EXTERNAL_STORAGE = 11;
    private int REQUEST_CAMERA = 2, SELECT_FILE = 1;
    private String userChoosenTask;
    public static final int PERMISSIONS_REQUEST_CODE = 0;
    public static final int FILE_PICKER_REQUEST_CODE = 1;
    MediaRecorder mediaRecorder ;
    private String AudioSavePath = null;
    private String format;

    ProgressDialog progressDialog;
    InsuranceAPI insuranceAPI;
    private List<UserAccountInfo> userAccountInfoList;
    private PendingCaseListInfo pendingInfo;
    public static final int RequestPermissionCode = 1;

    private TextView title2,file2,filename2;
    private TextView title3,file3,filename3;
    private TextView title4,file4,filename4;
    private TextView title5,file5,filename5;
    private TextView title6,file6,filename6;
    private TextView title7,file7,filename7;
    private TextView title8,file8,filename8;
    private TextView title9,file9,filename9;
    private TextView title10,file10,filename10;
    private TextView title11,file11,filename11;
    private TextView title12,file12,filename12;
    private TextView title13,file13,filename13;

    private TextView title31,file31,filename31;

    private EditText ed_totalamt;
    private EditText ed_ifany;
    private EditText any_Reason;
    private EditText mrd_amt;

    private EditText ed_comments;
    private EditText ed_convance;


    private String triggerreply = "<font color='#000000'>Trigger Reply </font>" + "<font color='#FF0000'>*</font>";
    private Button submit;
    private String comments ="",Convanceamt = "",temp ="";

    private List<PendingInfo> pendingInfoList;
    private RelativeLayout relativeLayout;



    private String string2= "<font color='#000000'>Bill Verification report form </font>" + "<font color='#FF0000'>*</font>";
    private String string3= "<font color='#000000'>Bill Confirmation form  </font>" + "<font color='#FF0000'>*</font>";
    private String string4= "<font color='#000000'>Bill Copies </font>" + "<font color='#FF0000'>*</font>";
    private String string5= "Medical Records Bill";
    private String string6 = "<font color='#000000'>Bill Verification report </font>" + "<font color='#FF0000'>*</font>";
    private String string7 = "Cash Receipts";
    private String string8 = "Break Up Bill";
    private String string9 = "<font color='#000000'>IP register  </font>" + "<font color='#FF0000'>*</font>";
    private String string10 = "<font color='#000000'>Tariff List  </font>" + "<font color='#FF0000'>*</font>";
    private String string11= "<font color='#000000'>Case Sheet </font>" + "<font color='#FF0000'>*</font>";
    private String string12= "<font color='#000000'>Hospital Snaps </font>" + "<font color='#FF0000'>*</font>";
    private String string13= "<font color='#000000'>Others  </font>" + "<font color='#FF0000'>*</font>";
    private String string31 = "Conveyance File(s)";
    private String gender = "";
    private Button backbutton;

    private RadioGroup radiogrp;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_verification_hospital);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pendingInfo = (PendingCaseListInfo) getIntent().getSerializableExtra("data");
        if(pendingInfo!=null){
            setTitle(pendingInfo.getClaim_no() +" "+"Bill Verification Hospital");
        }
        gettriggerslist(pendingInfo);
        radiogrp = (RadioGroup) findViewById(R.id.radiogroup);
        ed_totalamt = (EditText)findViewById(R.id.ed_totalamt);

        ed_comments = (EditText)findViewById(R.id.ed_comments);

        ed_convance = (EditText)findViewById(R.id.ed_convence);
        mrd_amt = (EditText)findViewById(R.id.edit_mrdamt);
        submit = (Button)findViewById(R.id.bt_submit);
        ed_ifany = (EditText) findViewById(R.id.ed_ifany);
        any_Reason = (EditText) findViewById(R.id.ed_ifany_Reason);

        title2 = (TextView)findViewById(R.id.title2);
        title2.setText(Html.fromHtml(string2));
        title3 = (TextView)findViewById(R.id.title3);
        title3.setText(Html.fromHtml(string3));
        title4 = (TextView)findViewById(R.id.title4);
        title4.setText(Html.fromHtml(string4));
        title5 = (TextView)findViewById(R.id.title5);
        title5.setText(Html.fromHtml(string5));
        title6 = (TextView)findViewById(R.id.title6);
        title6.setText(Html.fromHtml(string6));
        title7 = (TextView)findViewById(R.id.title7);
        title7.setText(string7);
        title8 = (TextView)findViewById(R.id.title8);
        title8.setText(string8);
        title9 = (TextView)findViewById(R.id.title9);
        title9.setText(Html.fromHtml(string9));
        title10 = (TextView)findViewById(R.id.title10);
        title10.setText(Html.fromHtml(string10));
        title11 = (TextView)findViewById(R.id.title11);
        title11.setText(Html.fromHtml(string11));
        title12 = (TextView)findViewById(R.id.title12);
        title12.setText(Html.fromHtml(string12));
        title13 = (TextView)findViewById(R.id.title13);
        title13.setText(Html.fromHtml(string13));

        title31 = (TextView)findViewById(R.id.title31);
        title31.setText(string31);

        filename2 = (TextView) findViewById(R.id.filename2);
        filename3 = (TextView) findViewById(R.id.filename3);
        filename4 = (TextView) findViewById(R.id.filename4);
        filename5 = (TextView) findViewById(R.id.filename5);
        filename6 = (TextView) findViewById(R.id.filename6);
        filename7 = (TextView) findViewById(R.id.filename7);
        filename8 = (TextView) findViewById(R.id.filename8);
        filename9 = (TextView) findViewById(R.id.filename9);
        filename10 = (TextView) findViewById(R.id.filename10);
        filename11 = (TextView) findViewById(R.id.filename11);
        filename12 = (TextView) findViewById(R.id.filename12);
        filename13 = (TextView) findViewById(R.id.filename13);


        file2 = (TextView)findViewById(R.id.file2);
        file2.setOnClickListener((View.OnClickListener) this);
        file3 = (TextView)findViewById(R.id.file3);
        file3.setOnClickListener((View.OnClickListener) this);
        file4 = (TextView)findViewById(R.id.file4);
        file4.setOnClickListener((View.OnClickListener) this);
        file5 = (TextView)findViewById(R.id.file5);
        file5.setOnClickListener((View.OnClickListener) this);
        file6 = (TextView)findViewById(R.id.file6);
        file6.setOnClickListener((View.OnClickListener) this);
        file7 = (TextView)findViewById(R.id.file7);
        file7.setOnClickListener((View.OnClickListener) this);
        file8 = (TextView)findViewById(R.id.file8);
        file8.setOnClickListener((View.OnClickListener) this);
        file9 = (TextView)findViewById(R.id.file9);
        file9.setOnClickListener((View.OnClickListener) this);
        file10 = (TextView)findViewById(R.id.file10);
        file10.setOnClickListener((View.OnClickListener) this);
        file11 = (TextView)findViewById(R.id.file11);
        file11.setOnClickListener((View.OnClickListener) this);
        file12 = (TextView)findViewById(R.id.file12);
        file12.setOnClickListener((View.OnClickListener) this);
        file13 = (TextView)findViewById(R.id.file13);
        file13.setOnClickListener((View.OnClickListener) this);

        file31 = (TextView)findViewById(R.id.file31);
        file31.setOnClickListener((View.OnClickListener) this);

        radiogrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) findViewById(checkedId);
                gender = rb.getText().toString();

            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                    comments = ed_comments.getText().toString();
//                    Convanceamt = ed_convance.getText().toString();

                getLogin();
                //sendAudio();
            }
        });

        backbutton = (Button)findViewById(R.id.bt_back);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }



    private void gettriggerslist(PendingCaseListInfo pendingInfo) {

        String consultantid = "";
        progressDialog = new ProgressDialog(BillVerificationHospital.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        com.squareup.okhttp.OkHttpClient okHttpClient = new com.squareup.okhttp.OkHttpClient();
        okHttpClient.setConnectTimeout(120000, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(120000, TimeUnit.MILLISECONDS);
        retrofit.Retrofit retrofit = new retrofit.Retrofit.Builder()
                .baseUrl("http://ayuhas.co.in")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        insuranceAPI = retrofit.create(InsuranceAPI.class);
        String mode = "";
        String case_assign = pendingInfo.getCase_assignment_id();

        triggerListId = Collections.singletonList("");
        Call<List<TriggersInfo>> call = insuranceAPI.gettriggersdetails(case_assign, mode, triggerListId, triggeranswer, triggerlistitem);
        call.enqueue(new retrofit.Callback<List<TriggersInfo>>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(retrofit.Response<List<TriggersInfo>> response, retrofit.Retrofit retrofit) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                triggersInfoList = response.body();
                if (triggersInfoList != null) {

                    createEditTextView();
                }

            }


            @Override
            public void onFailure(Throwable t) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }

                Toast.makeText(BillVerificationHospital.this, "Network Issue" + t, Toast.LENGTH_SHORT).show();

            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    protected void createEditTextView() {


        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear);      //find the linear layout
        linearLayout.removeAllViews();
        relativeLayout = (RelativeLayout) findViewById(R.id.realdynmo);
        final TextView[] buttonholder = new TextView[10];
        filenameholder = new TextView[10];
        ed = new EditText[10];
        //TextView button = new TextView(this);

        int in = triggersInfoList.size();

        List<String> titleList = new ArrayList<>();
        List<String> fileNameList = new ArrayList<>();
        triggerListId = new ArrayList<>();

        for (TriggersInfo tri : triggersInfoList) {

            titleList.add(tri.getTrigger_name());

            // fileNameList.add(tri.getTrigger_file());
        }
        for (int i = 1; i <= in; i++) {

            EditText edittext = new EditText(this);
            TextView title = new TextView(this);
            final TextView button = new TextView(this);
            allEds.add(edittext);
            edittext.setId(i);
            filename = new TextView(this);
            filename.setId(i - 1);
            // filename.setText(""+(i-1));
            button.setId(i - 1);
            fileNameList.add("" + (i - 1));
            title.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            View child = linearLayout.getChildAt(i);
            title.setText(titleList.get(i - 1));

            edittext.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 160));
            button.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            filename.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            params.setMargins(10, 10, 10, 10);
            Typeface face = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                face = getResources().getFont(R.font.verdana);
            }
            edittext.setTypeface(face);
            title.setTypeface(face);
            button.setTypeface(face);
            filename.setTypeface(face);
            title.setLayoutParams(params);
            button.setLayoutParams(params);
            button.setBackground(getResources().getDrawable(R.drawable.rounded_border_edittext));
            edittext.setInputType(10);
            title.setTextColor(getResources().getColor(R.color.Black));
            edittext.setTextColor(getResources().getColor(R.color.Black));
            button.setTextColor(getResources().getColor(R.color.Black));
            filename.setTextColor(getResources().getColor(R.color.Black));
            edittext.setGravity(Gravity.BOTTOM | Gravity.LEFT);
            edittext.setPadding(5, 5, 5, 5);
            button.setPadding(5, 5, 5, 5);
            filename.setPadding(5, 5, 5, 5);
            edittext.setBackground(getResources().getDrawable(R.drawable.rounded_border_edittext));
            button.setText("Choose File");
            edittext.setTextSize(15f);
            filename.setTextSize(15f);
            edittext.setHint(Html.fromHtml(triggerreply));

            button.setTextSize(17f);

            title.setTextSize(15f);
            button.setOnClickListener(this);
            buttonholder[i] = button;
            filenameholder[i] = filename;
            ed[i] = edittext;
            linearLayout.addView(title);
            linearLayout.addView(edittext);
            linearLayout.addView(button);
            linearLayout.addView(filename);

            final int finalI = i;


            buttonholder[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int in = v.getId();
                    int is = filenameholder[finalI].getId();
                    TriggersInfo triggerId = triggersInfoList.get(is);
                    triggerID = triggerId.getCase_trigger_id();

                    if (is == in) {
                        count = finalI + 100;
                        temp1 = "0";
                        selectImage();


                    }
                }
            });


        }

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
            Intent in = new Intent(BillVerificationHospital.this,MainActivity.class);
            startActivity(in);
            return true;
        }
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.file1:
                temp1 = "1";
                if (Build.VERSION.SDK_INT >= 23)
                {
                    if (checkPermission())
                    {

                        selectImage();
                    } else {
                        requestPermission(); // Code for permission
                    }
                }
                else
                {
                    selectImage();
                    // Code for Below 23 API Oriented Device
                    // Do next code
                }
                break;

            case R.id.file2:
                temp1 = "2";
                selectImage();
                break;

            case R.id.file3:
                temp1 = "3";
                selectImage();
                break;
            case R.id.file4:
                temp1 = "4";
                selectImage();
                break;

            case R.id.file5:
                temp1 = "5";
                selectImage();
                break;
            case R.id.file6:
                temp1 = "6";
                selectImage();
                break;

            case R.id.file7:
                temp1 = "7";
                selectImage();
                break;
            case R.id.file8:
                temp1 = "8";
                selectImage();
                break;

            case R.id.file9:
                temp1 = "9";
                selectImage();
                break;


            case R.id.file10:
                temp1 = "10";
                selectImage();
                break;
            case R.id.file11:
                temp1 = "11";
                selectImage();
                break;


            default:
                break;
        }
    }
    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(BillVerificationHospital.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(BillVerificationHospital.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(BillVerificationHospital.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_CODE);
        }
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
        file = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            file.createNewFile();
            fo = new FileOutputStream(file);
            fo.write(bytes.toByteArray());
            fo.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Random ran =new Random();
        int  n = ran.nextInt(500) + 1;
        AudioSavePath =  file.getPath();


        String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), thumbnail, "", null);
        pfdconvert();


    }
    private void pfdconvert(){
        BillingVerifiHospInfo hos = new BillingVerifiHospInfo();
        List<ImagesSaveInfo> path =  getimages();
        ImagesSaveInfo img = new ImagesSaveInfo();
        FileOutputStream fileOutputStream;
        String pa = "";
        Document convertToPdf = new Document(PageSize.LETTER, 400, 400, 400, 400);

        try {

            // pa = path.get(i).getImagesPath();
            String FileToPdf = AudioSavePath.replace("." + "jpg", ".pdf");

            fileOutputStream = new FileOutputStream(FileToPdf);

            PdfWriter.getInstance(convertToPdf,fileOutputStream);
            convertToPdf.open();
            Image convertBmp = Image.getInstance(AudioSavePath);
            convertToPdf.add(convertBmp);
            convertToPdf.close();
            File fileupload = new File(FileToPdf);
            if(temp1.equalsIgnoreCase("1")){
                hos.setBill_verific_report(fileupload);
                filename2.setText(fileupload.getPath());
                fbody1 = RequestBody.create(MediaType.parse("pdf/*"),fileupload);

            }else if(temp1.equalsIgnoreCase("2"))
            {
                hos.setBill_confirm_report(fileupload);
                filename3.setText(fileupload.getPath());
                fbody2= RequestBody.create(MediaType.parse("pdf/*"),fileupload);
            }else if(temp1.equalsIgnoreCase("3"))
            {

                hos.setBill_copies(fileupload);
                filename4.setText(fileupload.getPath());
                fbody3 = RequestBody.create(MediaType.parse("pdf/*"),fileupload);
            }else if(temp1.equalsIgnoreCase("4"))
            {
                hos.setMedical_record_bill(fileupload);
                filename5.setText(fileupload.getPath());
                fbody4 = RequestBody.create(MediaType.parse("pdf/*"),fileupload);
            }else if(temp1.equalsIgnoreCase("5"))
            {
                hos.setCash_receipt(fileupload);
                filename6.setText(fileupload.getPath());
                fbody5 = RequestBody.create(MediaType.parse("pdf/*"),fileupload);
            }else if(temp1.equalsIgnoreCase("6"))
            {
                hos.setBreak_up_bill(fileupload);
                filename7.setText(fileupload.getPath());
                fbody6 = RequestBody.create(MediaType.parse("pdf/*"),fileupload);
            }else if(temp1.equalsIgnoreCase("7"))
            {
                hos.setIp_register(fileupload);
                filename8.setText(fileupload.getPath());
                fbody7 = RequestBody.create(MediaType.parse("pdf/*"),fileupload);
            }else if(temp1.equalsIgnoreCase("8"))
            {
                hos.setTariff_list(fileupload);
                filename9.setText(fileupload.getPath());
                fbody8 = RequestBody.create(MediaType.parse("pdf/*"),fileupload);
            }if(temp1.equalsIgnoreCase("9")){
                hos.setCase_sheet(fileupload);
                filename10.setText(fileupload.getPath());
                fbody9 = RequestBody.create(MediaType.parse("pdf/*"),fileupload);

            }else if(temp1.equalsIgnoreCase("10"))
            {
                hos.setHospital_snap(fileupload);
                filename11.setText(fileupload.getPath());
                fbody10= RequestBody.create(MediaType.parse("pdf/*"),fileupload);
            }else if(temp1.equalsIgnoreCase("11"))
            {

                hos.setOther_files(fileupload);
                filename12.setText(fileupload.getPath());
                fbody11 = RequestBody.create(MediaType.parse("pdf/*"),fileupload);
            }else  if (count == count) {
                triggerfiles.add(fileupload);
                filenameholder[count - 100].setText(fileupload.getPath());


            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }


    }

    private void getLogin() {
        progressDialog = new ProgressDialog(BillVerificationHospital.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        com.squareup.okhttp.OkHttpClient okHttpClient = new com.squareup.okhttp.OkHttpClient();
        okHttpClient.setConnectTimeout(120000, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(120000, TimeUnit.MILLISECONDS);
        retrofit.Retrofit retrofit = new retrofit.Retrofit.Builder()
                .baseUrl("http://ayuhas.co.in")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        insuranceAPI = retrofit.create(InsuranceAPI.class);

        String case_assign = pendingInfo.getCase_assignment_id();
        String case_reg = pendingInfo.getClaim_no();

        // File file = new File(AudioSavePath);

       /* for(int j = 0; j < ed.length; j++){
            Log.d("Value ","Val " + ed[j].getText());

        }*/
        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);

        builder.addFormDataPart("case_assignment_id", case_assign);
        builder.addFormDataPart("sme_form", case_reg+"_"+"sme_form.pdf", fbody1);
        builder.addFormDataPart("authorization_letter", case_reg+"_"+"authorization_letter.pdf", fbody2);
        builder.addFormDataPart("company_snap", case_reg+"_"+"company_snap.pdf", fbody3);
        builder.addFormDataPart("company_visiting_card", case_reg+"_"+"company_visiting_card.pdf", fbody4);
        builder.addFormDataPart("sme_verification_report", case_reg+"_"+"sme_verification_report.pdf", fbody5);
        builder.addFormDataPart("hr_letter", case_reg+"_"+"hr_letter.pdf", fbody6);
        builder.addFormDataPart("company_registration_certificate", case_reg+"_"+"company_registration_certificate.pdf", fbody7);
        builder.addFormDataPart("other_file", case_reg+"_"+"other_file.pdf", fbody8);

        builder.addFormDataPart("any_comments", "adfda");

        for(int i = 0; i<triggerfiles.size();i++){
            File file = triggerfiles.get(i);
            builder.addFormDataPart("trigger_file_"+(i+1), "trigger_file"+"_"+(i+1)+".pdf", RequestBody.create(MediaType.parse("pdf/*"),file));
        }
        builder.addFormDataPart("doc_store_status", "save");
        final Call call = insuranceAPI.sample(builder.build());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit.Response<ResponseBody> response, Retrofit retrofit) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                if (response.code() == 200) {

                    ResponseBody  pr = response.body();

                }else{
                    Toast.makeText(BillVerificationHospital.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }

                Toast.makeText(BillVerificationHospital.this, "Network Issue" + t, Toast.LENGTH_SHORT).show();

            }
        });


    }





}
