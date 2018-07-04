package com.insurance.insuranceapp.Activites;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.media.MediaPlayer;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.insurance.insuranceapp.Datamodel.ImagesSaveInfo;
import com.insurance.insuranceapp.Datamodel.PendingCaseListInfo;
import com.insurance.insuranceapp.Datamodel.PendingInfo;
import com.insurance.insuranceapp.Datamodel.ProfileInfo;
import com.insurance.insuranceapp.Datamodel.RegistrationInfo;
import com.insurance.insuranceapp.Datamodel.UserAccountInfo;
import com.insurance.insuranceapp.R;
import com.insurance.insuranceapp.RestAPI.InsuranceAPI;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.itextpdf.text.DocumentException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import retrofit.Call;
import retrofit.GsonConverterFactory;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.activeandroid.Cache.getContext;
import static com.insurance.insuranceapp.Datamodel.ImagesSaveInfo.getdeleteimages;
import static com.insurance.insuranceapp.Datamodel.ImagesSaveInfo.getimages;
import static com.insurance.insuranceapp.Datamodel.UserAccountInfo.getAll;
import static com.insurance.insuranceapp.Datamodel.UserAccountInfo.getdeletecareprovider;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfCopyFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.PngImage;



public class HospitalBlockActivity extends AppCompatActivity implements
        View.OnClickListener {

    private static final int MY_PERMISSION_CAMERA = 10;
    private static final int MY_PERMISSION_EXTERNAL_STORAGE = 11;
    private int REQUEST_CAMERA = 2, SELECT_FILE = 1;
    private String userChoosenTask;
    public static final int PERMISSIONS_REQUEST_CODE = 0;

    private String AudioSavePathInDevice = null;
    MediaRecorder mediaRecorder ;
    Random random ;
    String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    ProgressDialog progressDialog;
    InsuranceAPI insuranceAPI;
    private List<String> imagepathList;
    private Boolean mode;
    public static final int RequestPermissionCode = 1;
    MediaPlayer mediaPlayer ;
    private ImagesSaveInfo imagesSaveInfo;
    private TextView title1,file1,filename1;
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
    private TextView title14,file14,filename14;
    private TextView title19,file19,filename19;
    private String string1= "<font color='#000000'>Company Authorization Letter towards the hospital </font>" + "<font color='#FF0000'>*</font>";
    private String string2= "<font color='#000000'>Investigation report form </font>" + "<font color='#FF0000'>*</font>";
    private String string3= "<font color='#000000'>Treating doctor Questionnaire </font>" + "<font color='#FF0000'>*</font>";
    private String string4= "<font color='#000000'>Case sheet </font>" + "<font color='#FF0000'>*</font>";
    private String string5= "<font color='#000000'>Doctor Questionnaire </font>" + "<font color='#FF0000'>*</font>";
    private String string6 = "IP register";
    private String string7 = "Lab Register";
    private String string8 = "Final Bill";
    private String string9 = "Previous OP and IP records";
    private String string10 = "<font color='#000000'>Field Investigation report </font>" + "<font color='#FF0000'>*</font>";

    private String string11= "Hospital Snaps";
    private String string12= "Others";
    private String string13= "Medical Records Bill (if any)";

    private String string19= "Conveyance File(s)";
    private String triggerreply = "<font color='#000000'>Trigger Reply </font>" + "<font color='#FF0000'>*</font>";
    private ListView triggerlist;
    private Button bt_next;
    private Button save,submit,back;
    private TextView filechoose1;
    private Button backbutton;
    private List<PendingInfo> pendingInfoList;
    private RelativeLayout relativeLayout;
    private EditText ed_comments,ed_date,ed_convoy,ed_mrd;
    private String Comments = "",Submitted_date = "",temp = "",conveyance = "",MRD = "";
    private ArrayList pdfFileList;
    private DatePickerDialog datePickerDialog;

    private TextInputLayout textInputLayout;
    private Button button;
    private PendingCaseListInfo pendingInfo;
    private String AudioSavePath = null;
    private String format;
    private List<UserAccountInfo> userAccountInfoList;
    private String domainurl;
    final String MyPREFERENCES = "MyPrefs";
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_block);
        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        domainurl = prefs.getString("domainurl","");
        pendingInfo = (PendingCaseListInfo) getIntent().getSerializableExtra("data");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getLogin();

        if(pendingInfo!=null){
            setTitle(pendingInfo.getClaim_no() +" "+"Hospital Part Block");
        }

        userAccountInfoList = getAll();
        textInputLayout = (TextInputLayout)findViewById(R.id.input_edit_consult);

        ed_convoy = (EditText)findViewById(R.id.famildoc);
        ed_mrd = (EditText)findViewById(R.id.famildoc1);
        save = (Button)findViewById(R.id.care_save);
        submit = (Button)findViewById(R.id.bt_submit);
        backbutton = (Button)findViewById(R.id.bt_back);
        random = new Random();
        back = (Button)findViewById(R.id.bt_back);
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

        title19 = (TextView)findViewById(R.id.title19);
        title19.setText(Html.fromHtml(string19));

        filename1 = (TextView)findViewById(R.id.filename1);
        ed_comments = (EditText)findViewById(R.id.ed_family);

        file1 = (TextView)findViewById(R.id.file_1);
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

        file19 = (TextView)findViewById(R.id.file19);
        file19.setOnClickListener((View.OnClickListener) this);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Submitted_date = ed_date.getText().toString();
                if(Submitted_date!=null && !Submitted_date.isEmpty()){
                    Comments = ed_comments.getText().toString();
                MRD = ed_mrd.getText().toString();
                conveyance = ed_convoy.getText().toString();

                }else{
                    textInputLayout.setError("Cannot be empty");
                }*/

            }
        });
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        createEditTextView();
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(HospitalBlockActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }






    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.file_1:
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
                mode = true;
                if(mode){
                    pfdconvert();
                }else {
                    selectImage();
                }


                break;

            case R.id.file3:
                selectImage();
                break;
            case R.id.file4:
                selectImage();
                break;

            case R.id.file5:
                selectImage();
                break;
            case R.id.file6:
                selectImage();
                break;

            case R.id.file7:
                selectImage();
                break;
            case R.id.file8:
                selectImage();
                break;

            case R.id.file9:
                selectImage();
                break;
            case R.id.file10:
                selectImage();
                break;

            case R.id.file11:
                selectImage();
                break;
            case R.id.file12:
                selectImage();
                break;

            case R.id.file13:
                selectImage();
                break;
            case R.id.file14:
                selectImage();
                break;
            case R.id.file19:
                selectImage();
                break;

            default:
                break;
        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(HospitalBlockActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(HospitalBlockActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(HospitalBlockActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_CODE);
        }
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
            filename.setText("adfd");
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

        pendingInfoList.add(pendingInfo);


        return pendingInfoList;
    }

    private void getLogin() {
        progressDialog = new ProgressDialog(HospitalBlockActivity.this, R.style.AppTheme_Dark_Dialog);
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

        final retrofit.Call call = insuranceAPI.sample(case_assign);
        call.enqueue(new retrofit.Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit.Response<ResponseBody> response, retrofit.Retrofit retrofit) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }

                // profileInfoList = response.body();

                if (response.code() == 200) {

                    ResponseBody  pr = response.body();

                }else{
                    Toast.makeText(HospitalBlockActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }

            }


            @Override
            public void onFailure(Throwable t) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }

                 Toast.makeText(HospitalBlockActivity.this, "Network Issue" + t, Toast.LENGTH_SHORT).show();

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
            Intent in = new Intent(HospitalBlockActivity.this,MainActivity.class);
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
                galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
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

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Random ran =new Random();
        int  n = ran.nextInt(500) + 1;
       String pa =  destination.getPath();
        imagesSaveInfo = new ImagesSaveInfo();
        imagesSaveInfo.setBlockName("HospitalBlock");
        imagesSaveInfo.setCliamNumber(pendingInfo.getClaim_no());
        imagesSaveInfo.setImageID(String.valueOf(n));
        imagesSaveInfo.setImagesPath(pa);
        imagesSaveInfo.save();

        String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), thumbnail, "", null);




    }

    private void pfdconvert(){
        List<ImagesSaveInfo> path =  getimages();
        ImagesSaveInfo img = new ImagesSaveInfo();
        FileOutputStream fileOutputStream;
        String pa = "";
        Document convertToPdf = new Document(PageSize.LETTER, 400, 400, 400, 400);

            try {
                for(int i = 0; i <path.size();i++) {
                    pa = path.get(i).getImagesPath();
                    String FileToPdf = pa.replace("." + "jpg", ".pdf");
                    fileOutputStream = new FileOutputStream(FileToPdf);
                    PdfWriter.getInstance(convertToPdf,fileOutputStream);
                    convertToPdf.open();
                    Image convertBmp = Image.getInstance(pa);

                    convertToPdf.add(convertBmp);


                }
                convertToPdf.close();
            } catch (Exception e) {
                System.out.println(e.toString());
            }

        mode = false;
        getdeleteimages();
    }








}