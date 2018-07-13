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
import android.media.MediaScannerConnection;
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
import com.insurance.insuranceapp.Datamodel.PatientBlockInfo;
import com.insurance.insuranceapp.Datamodel.PendingCaseListInfo;
import com.insurance.insuranceapp.Datamodel.PendingInfo;
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
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


import retrofit.Call;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.insurance.insuranceapp.Datamodel.ImagesSaveInfo.getimages;

public class PatientBlockActivity extends AppCompatActivity implements
        View.OnClickListener {
    private static final int MY_PERMISSION_CAMERA = 10;
    private static final int MY_PERMISSION_EXTERNAL_STORAGE = 11;
    private int REQUEST_CAMERA = 2, SELECT_FILE = 1;
    private String userChoosenTask;
    public static final int PERMISSIONS_REQUEST_CODE = 0;
    public static final int FILE_PICKER_REQUEST_CODE = 1;
    private TextView title1,file1,filename1;
    private TextView title2,file2,filename2;
    private TextView title3,file3,filename3;
    private TextView title4,file4,filename4;
    private TextView title5,file5,filename5;
    private TextView title6,file6,filename6;
    private TextView title7,file7,filename7;
    private TextView title8,file8,filename8;
    private TextView title19,file19,filename19;
    private Button backbutton;
    MediaRecorder mediaRecorder ;
    private String AudioSavePath = null;
    private String format;
    ProgressDialog progressDialog;
    InsuranceAPI insuranceAPI;
    private File file;
    private List<UserAccountInfo> userAccountInfoList;
    private PendingCaseListInfo pendingInfo;
    public static final int RequestPermissionCode = 1;
    private RequestBody fbody1,fbody2,fbody3,fbody4,fbody5,fbody6,fbody7,fbody8,fbody9,fbody10,fbody11,fbody12,fbody13,fbody14;
    private String string1= "<font color='#000000'>Patient Doctor Questionarie  </font>" + "<font color='#FF0000'>*</font>";
    private String string2= "<font color='#000000'>Patient ID Proof </font>" + "<font color='#FF0000'>*</font>";
    private String string3= "<font color='#000000'>Patient Questionnaire </font>" + "<font color='#FF0000'>*</font>";
    private String string4= "<font color='#000000'>Previous OP and IP records</font>" + "<font color='#FF0000'>*</font>";
    private String string5= "<font color='#000000'>Patient Narration letter </font>" + "<font color='#FF0000'>*</font>";
    private String string6 = "Patient Authorization";
    private String string7 = "Query Reply";
    private String string8 = "Others";
    private String string19 = "Conveyance File(s)";
    private String triggerreply = "<font color='#000000'>Trigger Reply </font>" + "<font color='#FF0000'>*</font>";
    private EditText ed_comments,ed_convance;
    private String comments ="",convance = "",temp ="";
    private String count;
    private Button submit;
    private List<PendingInfo> pendingInfoList;
    private RelativeLayout relativeLayout;
    private Button bt_save;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_block);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bt_save = (Button)findViewById(R.id.care_save);
        pendingInfo = (PendingCaseListInfo) getIntent().getSerializableExtra("data");
        if(pendingInfo!=null){
            setTitle(pendingInfo.getClaim_no() +" "+"Patient Part");
        }

        createEditTextView();

        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendAudio();
            }
        });
        ed_comments = (EditText)findViewById(R.id.ed_comments);

        ed_convance = (EditText)findViewById(R.id.ed_convence);

        title1 = (TextView)findViewById(R.id.title1);
        title1.setText(Html.fromHtml(string1));
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
        title7.setText(Html.fromHtml(string7));
        title8 = (TextView)findViewById(R.id.title8);
        title8.setText(Html.fromHtml(string8));

        title19 = (TextView)findViewById(R.id.title19);
        title19.setText((string19));
        submit = (Button)findViewById(R.id.bt_submit);
        backbutton = (Button)findViewById(R.id.bt_back);


        file1 = (TextView)findViewById(R.id.file1);
        file1.setOnClickListener((View.OnClickListener) this);
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

        file19 = (TextView)findViewById(R.id.file19);
        file19.setOnClickListener((View.OnClickListener) this);





        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    convance = ed_convance.getText().toString();
                    comments = ed_comments.getText().toString();

                mediaRecorder.stop();
              //  sendAudio();
            }
        });

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




    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    protected void createEditTextView() {
        LinearLayout linearLayout= (LinearLayout)findViewById(R.id.linear);      //find the linear layout
        linearLayout.removeAllViews();
        relativeLayout = (RelativeLayout)findViewById(R.id.realdynmo);
        pendingInfoList =  getList();

        for(int i=1;i<=pendingInfoList.size();i++) {

            EditText edittext = new EditText(this);
            TextView title = new TextView(this);
            TextView button = new TextView(this);
            TextView filename = new TextView(this);
            title.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            title.setText("sf" + i);
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
            filename.setText("filename");
            button.setTextSize(17f);
            title.setTextSize(15f);
            linearLayout.addView(title);
            linearLayout.addView(edittext);
            linearLayout.addView(button);
            linearLayout.addView(filename);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),"asf",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private List<PendingInfo> getList(){

        pendingInfoList = new ArrayList<>();

        PendingInfo pendingInfo = new PendingInfo();
        pendingInfo.setCliam_no("5461235");
        pendingInfo.setPatient_name("Arun");
        pendingInfo.setCase_type("Hospital Block");
        pendingInfo.setPolicy_number("54322578");
        pendingInfo.setCompany_name("LIC");

        pendingInfo.setCase_assigned_on("Vijila ");

        pendingInfo.setStatus("pending");

        pendingInfoList.add(pendingInfo);

        return pendingInfoList;
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
            Intent in = new Intent(PatientBlockActivity.this,MainActivity.class);
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
                count = "1";
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
                count = "2";
                selectImage();
                break;

            case R.id.file3:
                count = "3";
                selectImage();
                break;
            case R.id.file4:
                count = "4";
                selectImage();
                break;

            case R.id.file5:
                count = "5";
                selectImage();
                break;
            case R.id.file6:
                count = "6";
                selectImage();
                break;

            case R.id.file7:
                count = "7";
                selectImage();
                break;
            case R.id.file8:
                count = "8";
                selectImage();
                break;

            case R.id.file9:
                count = "9";
                selectImage();
                break;

            case R.id.file19:
                count = "10";
                selectImage();
                break;

            default:
                break;
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
            choosePhotoFromGallary();
        }
    }
    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(PatientBlockActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(PatientBlockActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(PatientBlockActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_CODE);
        }
    }
    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, SELECT_FILE);
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
            // uploadProfileImage(destination.getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), thumbnail, "", null);
        AudioSavePath =  file.getPath();

        pfdconvert();
    }
    private void pfdconvert(){
        PatientBlockInfo hos = new PatientBlockInfo();
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

            if(count.equalsIgnoreCase("1")){
                hos.setPatient_doctor_questionarie(fileupload);
                fbody1 = RequestBody.create(MediaType.parse("pdf/*"),fileupload);
            }else if(count.equalsIgnoreCase("2"))
            {
                hos.setPatient_id_proof(fileupload);
                fbody2= RequestBody.create(MediaType.parse("pdf/*"),fileupload);
            }else if(count.equalsIgnoreCase("3"))
            {

                hos.setPatient_questionarie(fileupload);
                fbody3 = RequestBody.create(MediaType.parse("pdf/*"),fileupload);
            }else if(count.equalsIgnoreCase("4"))
                    {
                        hos.setPrevious_op_ip_records(fileupload);
                        fbody4 = RequestBody.create(MediaType.parse("pdf/*"),fileupload);
                    }else if(count.equalsIgnoreCase("5"))
                    {
                        hos.setPatient_narration_letter(fileupload);
                        fbody5 = RequestBody.create(MediaType.parse("pdf/*"),fileupload);
                    }else if(count.equalsIgnoreCase("6"))
                    {
                        hos.setPatient_authorization(fileupload);
                        fbody6 = RequestBody.create(MediaType.parse("pdf/*"),fileupload);
                    }else if(count.equalsIgnoreCase("7"))
                    {
                        hos.setQuery_reply(fileupload);
                        fbody7 = RequestBody.create(MediaType.parse("pdf/*"),fileupload);
                    }else if(count.equalsIgnoreCase("8"))
                    {
                        hos.setOthers(fileupload);

                            fbody8 = RequestBody.create(MediaType.parse("pdf/*"),fileupload);



                    }

        } catch (Exception e) {
            System.out.println(e.toString());
        }


    }
    private void sendAudio(){
        String consultID = "";
        progressDialog = new ProgressDialog(PatientBlockActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Submitting...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        com.squareup.okhttp.OkHttpClient okHttpClient = new com.squareup.okhttp.OkHttpClient();
        okHttpClient.setConnectTimeout(120000, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(120000, TimeUnit.MILLISECONDS);

        retrofit.Retrofit retrofit = new retrofit.Retrofit.Builder()
                .baseUrl(getBaseContext().getString(R.string.DomainURL1))
                .client(okHttpClient)
                .build();

        insuranceAPI = retrofit.create(InsuranceAPI.class);

        String case_assign = pendingInfo.getCase_assignment_id();
        String case_reg = pendingInfo.getClaim_no();
        File file = new File(AudioSavePath);
        RequestBody fbody = RequestBody.create(MediaType.parse("pdf/*"),file);
        String submit = "submit";
        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
        builder.addFormDataPart("case_assignment_id", case_assign);
        builder.addFormDataPart("patient_doctor_questionarie",case_reg+"_"+"patient_doctor_questionarie.pdf", fbody1);
        builder.addFormDataPart("patient_id_proof",case_reg+"_"+"patient_id_proof.pdf", fbody2);
        builder.addFormDataPart("patient_questionarie",case_reg+"_"+"patient_questionarie.pdf",fbody3);
        builder.addFormDataPart("previous_op_ip_records",case_reg+"_"+"previous_op_ip_records.pdf", fbody1);
        builder.addFormDataPart("patient_narration_letter", case_reg+"_"+"patient_narration_letter.pdf",fbody1);
        builder.addFormDataPart("patient_authorization",case_reg+"_"+"patient_authorization.pdf", fbody1);
        builder.addFormDataPart("query_reply", case_reg+"_"+"query_reply.pdf",fbody);
        builder.addFormDataPart("others", case_reg+"_"+"others.pdf",fbody1);
        builder.addFormDataPart("save", "save");
        Call<ResponseBody> call = insuranceAPI.sample(builder.build());
        //  Call<ResponseBody> call = insuranceAPI.sendAudio(consultID,casetype,assignstatus,CaseId,CaseassignmentId,claim_no,case_type_id,fbody,submit);
        call.enqueue(new retrofit.Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit.Response<ResponseBody> response, retrofit.Retrofit retrofit) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                ResponseBody res = response.body();
                try {
                    String autocompleteOptions = res.string();
                    //   Toast.makeText(HospitalBlockActivity.this, autocompleteOptions, Toast.LENGTH_SHORT).show();
                    File file = new File(AudioSavePath);
                    boolean deleted = file.delete();

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
            @Override
            public void onFailure(Throwable t) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                String err = t.getMessage() == null ? "" : t.getMessage();
                Log.e("RETROFIT", err);
                // Toast.makeText(HospitalBlockActivity.this, "Audio_file Failed: " + t, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
