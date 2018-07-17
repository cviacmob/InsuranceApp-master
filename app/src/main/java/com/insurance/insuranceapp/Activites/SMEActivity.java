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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.insurance.insuranceapp.Datamodel.HospitalBlockInfo;
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
import static com.insurance.insuranceapp.Datamodel.ImagesSaveInfo.getimages;

public class SMEActivity extends AppCompatActivity implements
        View.OnClickListener {
    private static final int MY_PERMISSION_CAMERA = 10;
    private static final int MY_PERMISSION_EXTERNAL_STORAGE = 11;
    private int REQUEST_CAMERA = 2, SELECT_FILE = 1;
    private String userChoosenTask;
    public static final int PERMISSIONS_REQUEST_CODE = 0;
    public static final int FILE_PICKER_REQUEST_CODE = 1;

    private String AudioSavePath = null;
    private String format;
    ProgressDialog progressDialog;
    InsuranceAPI insuranceAPI;
    private List<UserAccountInfo> userAccountInfoList;
    private PendingCaseListInfo pendingInfo;
    public static final int RequestPermissionCode = 1;
    private List<String> triggerListId;
    private List<String> triggeranswer;
    private List<File> triggerfiles = new ArrayList<>();
    private TextView title1,file1,filename1;
    private TextView title2,file2,filename2;
    private TextView title3,file3,filename3;
    private TextView title4,file4,filename4;
    private TextView title5,file5,filename5;
    private TextView title6,file6,filename6;
    private TextView title7,file7,filename7;
    private TextView title8,file8,filename8;
    private TextView filename;
    private TextView title31,file31,filename31;
    private String temp1 = "";
    private String string1= "<font color='#000000'>SME Form </font>" + "<font color='#FF0000'>*</font>";
    private String string2= "<font color='#000000'>Authorization  </font>" + "<font color='#FF0000'>*</font>";
    private String string3= "<font color='#000000'>Company Snaps  </font>" + "<font color='#FF0000'>*</font>";
    private String string4= "<font color='#000000'>Company Visiting Card  </font>" + "<font color='#FF0000'>*</font>";
    private String string5= "<font color='#000000'>SME Verification report </font>" + "<font color='#FF0000'>*</font>";
    private String string6 = "<font color='#000000'>HR Letter </font>" + "<font color='#FF0000'>*</font>";
    private String string7 = "Company registration Certificate";
    private String string8 = "Others";
    private File file;
    private String string31 = "Conveyance File(s)";
    private List<TriggersInfo> triggersInfoList;
    private Button submit;
    private EditText[] ed;
    private String submitted_date ="",triggerfinding = "",comments ="",Convanceamt = "",temp ="";
    private TextInputLayout textInputLayout;
    private List<PendingInfo> pendingInfoList;
    private RelativeLayout relativeLayout;
    List<EditText> allEds = new ArrayList<EditText>();
    private EditText ed_comments;
    private EditText ed_convance;
    private TextView[] filenameholder;
    private List<File> triggerlistitem;
    private String triggerreply = "<font color='#000000'>Trigger Reply </font>" + "<font color='#FF0000'>*</font>";
    private Button backbutton;
    private String triggerID = "";
    private int count = 0;
    private RequestBody fbody1,fbody2,fbody3,fbody4,fbody5,fbody6,fbody7,fbody8;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sme);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pendingInfo = (PendingCaseListInfo) getIntent().getSerializableExtra("data");
        if(pendingInfo!=null){
            setTitle(pendingInfo.getClaim_no() +" "+"SME");
        }
        gettriggerslist(pendingInfo);


        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear);      //find the linear layout
        linearLayout.removeAllViews();
        relativeLayout = (RelativeLayout) findViewById(R.id.realdynmo);

        title1 = (TextView) findViewById(R.id.title1);
        title1.setText(Html.fromHtml(string1));
        title2 = (TextView) findViewById(R.id.title2);
        title2.setText(Html.fromHtml(string2));
        title3 = (TextView) findViewById(R.id.title3);
        title3.setText(Html.fromHtml(string3));
        title4 = (TextView) findViewById(R.id.title4);
        title4.setText(Html.fromHtml(string4));
        title5 = (TextView) findViewById(R.id.title5);
        title5.setText(Html.fromHtml(string5));
        title6 = (TextView) findViewById(R.id.title6);
        title6.setText(Html.fromHtml(string6));
        title7 = (TextView) findViewById(R.id.title7);
        title7.setText(string7);
        title8 = (TextView) findViewById(R.id.title8);
        title8.setText(string8);

        title31 = (TextView) findViewById(R.id.title31);
        title31.setText(string31);

        ed_comments = (EditText) findViewById(R.id.ed_comments);

        ed_convance = (EditText) findViewById(R.id.ed_convence);

        submit = (Button) findViewById(R.id.bt_submit);
        backbutton = (Button) findViewById(R.id.bt_back);
        file1 = (TextView) findViewById(R.id.file1);
        file1.setOnClickListener((View.OnClickListener) this);
        file2 = (TextView) findViewById(R.id.file2);
        file2.setOnClickListener((View.OnClickListener) this);
        file3 = (TextView) findViewById(R.id.file3);
        file3.setOnClickListener((View.OnClickListener) this);
        file4 = (TextView) findViewById(R.id.file4);
        file4.setOnClickListener((View.OnClickListener) this);
        file5 = (TextView) findViewById(R.id.file5);
        file5.setOnClickListener((View.OnClickListener) this);
        file6 = (TextView) findViewById(R.id.file6);
        file6.setOnClickListener((View.OnClickListener) this);
        file7 = (TextView) findViewById(R.id.file7);
        file7.setOnClickListener((View.OnClickListener) this);
        file8 = (TextView) findViewById(R.id.file8);
        file8.setOnClickListener((View.OnClickListener) this);

        file31 = (TextView) findViewById(R.id.file31);
        file31.setOnClickListener((View.OnClickListener) this);

        filename1 = (TextView) findViewById(R.id.filename1);
        filename2 = (TextView) findViewById(R.id.filename2);
        filename3 = (TextView) findViewById(R.id.filename3);
        filename4 = (TextView) findViewById(R.id.filename4);
        filename5 = (TextView) findViewById(R.id.filename5);
        filename6 = (TextView) findViewById(R.id.filename6);
        filename7 = (TextView) findViewById(R.id.filename7);
        filename8 = (TextView) findViewById(R.id.filename8);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /*  comments = ed_comments.getText().toString();

                Convanceamt = ed_convance.getText().toString();*/
                getLogin();

            }
        });

    }









    private void gettriggerslist(PendingCaseListInfo pendingInfo) {

        String consultantid = "";
        progressDialog = new ProgressDialog(SMEActivity.this, R.style.AppTheme_Dark_Dialog);
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

                Toast.makeText(SMEActivity.this, "Network Issue" + t, Toast.LENGTH_SHORT).show();

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

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(SMEActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
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
            Intent in = new Intent(SMEActivity.this,MainActivity.class);
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


            case R.id.file31:
                temp1 = "31";
                selectImage();
                break;


        }
    }
    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(SMEActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(SMEActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(SMEActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_CODE);
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
        SMEInfo hos = new SMEInfo();
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
                hos.setSme_form(fileupload);
                filename1.setText(fileupload.getPath());
                fbody1 = RequestBody.create(MediaType.parse("pdf/*"),fileupload);

            }else if(temp1.equalsIgnoreCase("2"))
            {
                hos.setAuthorization_letter(fileupload);
                filename2.setText(fileupload.getPath());
                fbody2= RequestBody.create(MediaType.parse("pdf/*"),fileupload);
            }else if(temp1.equalsIgnoreCase("3"))
            {

                hos.setCompany_snap(fileupload);
                filename3.setText(fileupload.getPath());
                fbody3 = RequestBody.create(MediaType.parse("pdf/*"),fileupload);
            }else if(temp1.equalsIgnoreCase("4"))
            {
                hos.setCompany_visiting_card(fileupload);
                filename4.setText(fileupload.getPath());
                fbody4 = RequestBody.create(MediaType.parse("pdf/*"),fileupload);
            }else if(temp1.equalsIgnoreCase("5"))
            {
                hos.setSme_verification_report(fileupload);
                filename5.setText(fileupload.getPath());
                fbody5 = RequestBody.create(MediaType.parse("pdf/*"),fileupload);
            }else if(temp1.equalsIgnoreCase("6"))
            {
                hos.setHr_letter(fileupload);
                filename6.setText(fileupload.getPath());
                fbody6 = RequestBody.create(MediaType.parse("pdf/*"),fileupload);
            }else if(temp1.equalsIgnoreCase("7"))
            {
                hos.setCompany_registration_certificate(fileupload);
                filename7.setText(fileupload.getPath());
                fbody7 = RequestBody.create(MediaType.parse("pdf/*"),fileupload);
            }else if(temp1.equalsIgnoreCase("8"))
            {
                hos.setOther_file(fileupload);
                filename8.setText(fileupload.getPath());
                fbody8 = RequestBody.create(MediaType.parse("pdf/*"),fileupload);
            }else  if (count == count) {
                triggerfiles.add(fileupload);
                filenameholder[count - 100].setText(fileupload.getPath());


            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }


    }


    private void getLogin() {
        progressDialog = new ProgressDialog(SMEActivity.this, R.style.AppTheme_Dark_Dialog);
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
        builder.addFormDataPart("doc_store_status", "submit");
        final Call call = insuranceAPI.sample(builder.build());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit.Response<ResponseBody> response, Retrofit retrofit) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                if (response.code() == 200) {

                    ResponseBody  pr = response.body();
                    String mess = pr.toString();

                }else{
                    Toast.makeText(SMEActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }

                Toast.makeText(SMEActivity.this, "Network Issue" + t, Toast.LENGTH_SHORT).show();

            }
        });


    }

}
