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
import android.hardware.Camera;
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
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.utils.FileUtils;
import com.insurance.insuranceapp.Datamodel.DynamicFileNameInfo;
import com.insurance.insuranceapp.Datamodel.PendingCaseListInfo;
import com.insurance.insuranceapp.Datamodel.PendingInfo;
import com.insurance.insuranceapp.Datamodel.Triggers;
import com.insurance.insuranceapp.Datamodel.TriggersInfo;
import com.insurance.insuranceapp.Datamodel.UserAccountInfo;
import com.insurance.insuranceapp.R;
import com.insurance.insuranceapp.RestAPI.InsuranceAPI;
import com.insurance.insuranceapp.Utilities.InsApp;





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
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import okhttp3.MultipartBody;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import static com.insurance.insuranceapp.Datamodel.UserAccountInfo.getAll;


public class DynamicActivity extends AppCompatActivity implements SurfaceHolder.Callback, View.OnClickListener {

    private static final String TAG = DynamicActivity.class.getSimpleName();
    public static SurfaceView mSurfaceView;
    public static SurfaceHolder mSurfaceHolder;
    public static Camera mCamera;
    public static boolean mPreviewRunning;

    private EditText[] ed;
    private int count = 0;
    private TextView filename;
    private List<File> triggerlist;
    private List<String> triggerListId;

    private TextView[] filenameholder;
    private List<TriggersInfo> triggersInfoList;
    private List<String> docNameList;
    private List<DynamicFileNameInfo> dynamicFileNameInfoList;
    private static final int MY_PERMISSION_CAMERA = 10;
    private static final int MY_PERMISSION_EXTERNAL_STORAGE = 11;
    private int REQUEST_CAMERA = 2, SELECT_FILE = 1;
    private String userChoosenTask;
    public static final int PERMISSIONS_REQUEST_CODE = 0;
    public static final int FILE_PICKER_REQUEST_CODE = 1;
    MediaRecorder mediaRecorder;

    private String format;
    ProgressDialog progressDialog;
    InsuranceAPI insuranceAPI;
    private List<UserAccountInfo> userAccountInfoList;
    private PendingCaseListInfo pendingInfo;
    public static final int RequestPermissionCode = 1;
    private TextView title1, file1, filename1;
    private TextView title2, file2, filename2;
    private TextView title3, file3, filename3;
    private TextView title4, file4, filename4;
    private TextView title8, file8, filename8;

    private TextView title31, file31, filename31;
    InsApp api;
    private String triggerID = "";
    private Hashtable<Integer, String> triggerhash;

    private String string1 = "<font color='#000000'>Company Authorization Letter towards the hospital </font>" + "<font color='#FF0000'>*</font>";
    private String string2 = "<font color='#000000'>Investigation report form  </font>" + "<font color='#FF0000'>*</font>";
    private String string3 = "<font color='#000000'>Insured Questionnaire </font>" + "<font color='#FF0000'>*</font>";
    private String string4 = "<font color='#000000'>Treating doctor Questionnaire  </font>" + "<font color='#FF0000'>*</font>";
    private String assignmentID = "";
    private String string8 = "Others";

    private String string31 = "Conveyance File(s)";
    private String triggerreply = "<font color='#000000'>Trigger Reply </font>" + "<font color='#FF0000'>*</font>";
    private Button submit;
    private String  comments = "", Convanceamt = "", temp = "";

    private List<PendingInfo> pendingInfoList;
    private RelativeLayout relativeLayout;

    private EditText ed_comments;
    private EditText  ed_convance;


    private Button backbutton;
    List<EditText> allEds = new ArrayList<EditText>();
    private String mode = "";
    retrofit.Retrofit retrofit;
    private List<String> triggeranswer;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Others");

        mSurfaceView = findViewById(R.id.surfaceView1);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

//        Intent intent = new Intent(DynamicActivity.this, RecorderService.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       // startService(intent);

        userAccountInfoList = getAll();
        triggerlist = new ArrayList<>();

        triggeranswer = new ArrayList<>();
        pendingInfo = (PendingCaseListInfo) getIntent().getSerializableExtra("data");
        getdetails(pendingInfo);
        textdata();
        triggerhash = new Hashtable<Integer, String>();
        filename1 = (TextView) findViewById(R.id.filename1);
        filename2 = (TextView) findViewById(R.id.filename2);
        filename3 = (TextView) findViewById(R.id.filename3);
        filename4 = (TextView) findViewById(R.id.filename4);
        filename8 = (TextView) findViewById(R.id.filename8);

        filename31 = (TextView) findViewById(R.id.filename31);

        file1 = (TextView) findViewById(R.id.file1);
        file1.setOnClickListener((View.OnClickListener) this);
        file2 = (TextView) findViewById(R.id.file2);
        file2.setOnClickListener((View.OnClickListener) this);
        file3 = (TextView) findViewById(R.id.file3);
        file3.setOnClickListener((View.OnClickListener) this);
        file4 = (TextView) findViewById(R.id.file4);
        file4.setOnClickListener((View.OnClickListener) this);
        file8 = (TextView) findViewById(R.id.file8);
        file8.setOnClickListener((View.OnClickListener) this);


        file31 = (TextView) findViewById(R.id.file31);
        file31.setOnClickListener((View.OnClickListener) this);


        ed_comments = (EditText) findViewById(R.id.ed_comments);

        ed_convance = (EditText) findViewById(R.id.ed_convence);

        submit = (Button) findViewById(R.id.bt_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // stopService(new Intent(DynamicActivity.this, RecorderService.class));
              /*  for (int i = 0; i<ed.length;i++){
                   String ans =  ed[i].getText().toString();
                    if(ans!=null){
                        triggeranswer.add(ed[i].getText().toString());
                    }
                }*/
                mode = "1";
                gettriggerslist(pendingInfo);
                // submitted_date =  ed_date.getText().toString();
               /* if(submitted_date!=null && !submitted_date.isEmpty())
                {
                    comments = ed_comments.getText().toString();
                    triggerfinding = ed_triggerfinding.getText().toString();
                    Convanceamt = ed_convance.getText().toString();
                }else{
                    textInputLayout.setError("Cannot be empty");
                }*/
                // mediaRecorder.stop();
                // sendAudio();
            }
        });

        backbutton = (Button) findViewById(R.id.bt_back);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void textdata() {
        title1 = (TextView) findViewById(R.id.title1);
        title1.setText(Html.fromHtml(string1));
        title2 = (TextView) findViewById(R.id.title2);
        title2.setText(Html.fromHtml(string2));
        title3 = (TextView) findViewById(R.id.title3);
        title3.setText(Html.fromHtml(string3));
        title4 = (TextView) findViewById(R.id.title4);
        title4.setText(Html.fromHtml(string4));


        title8 = (TextView) findViewById(R.id.title8);
        title8.setText(string8);

        title31 = (TextView) findViewById(R.id.title31);
        title31.setText(string31);
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
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
            Intent in = new Intent(DynamicActivity.this, MainActivity.class);
            startActivity(in);
            return true;
        }
        onBackPressed();
        return true;
    }


    private void getdetails(final PendingCaseListInfo pendingInfo) {
        String consultantid = "";
        progressDialog = new ProgressDialog(DynamicActivity.this, R.style.AppTheme_Dark_Dialog);
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

        for (UserAccountInfo user : userAccountInfoList) {
            consultantid = user.getConsultant_id();
        }

        String status = pendingInfo.getAssign_status();
        assignmentID = pendingInfo.getCase_assignment_id();
        String flag = "0";


        Call<List<DynamicFileNameInfo>> call = insuranceAPI.getdetails(consultantid, status, assignmentID, flag);
        call.enqueue(new retrofit.Callback<List<DynamicFileNameInfo>>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(retrofit.Response<List<DynamicFileNameInfo>> response, retrofit.Retrofit retrofit) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                dynamicFileNameInfoList = response.body();
                if (dynamicFileNameInfoList != null) {
                    filelayout();
                    mode = "0";
                    gettriggerslist(pendingInfo);
                }


            }


            @Override
            public void onFailure(Throwable t) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }

                Toast.makeText(DynamicActivity.this, "Network Issue" + t, Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void gettriggerslist(PendingCaseListInfo pendingInfo) {

        String consultantid = "";
        progressDialog = new ProgressDialog(DynamicActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        if (mode == "0") {
            com.squareup.okhttp.OkHttpClient okHttpClient = new com.squareup.okhttp.OkHttpClient();
            okHttpClient.setConnectTimeout(120000, TimeUnit.MILLISECONDS);
            okHttpClient.setReadTimeout(120000, TimeUnit.MILLISECONDS);
            retrofit = new retrofit.Retrofit.Builder()
                    .baseUrl(getBaseContext().getString(R.string.DomainURL))
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
            insuranceAPI = retrofit.create(InsuranceAPI.class);
        }

        for (UserAccountInfo user : userAccountInfoList) {
            consultantid = user.getConsultant_id();
        }

        if (mode == "0") {
            assignmentID = pendingInfo.getCase_assignment_id();
            mode = "0";
            triggerListId = Collections.singletonList("");
            Call<List<TriggersInfo>> call = insuranceAPI.gettriggersdetails(assignmentID, mode, triggerListId, triggeranswer, triggerlist);
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

                    Toast.makeText(DynamicActivity.this, "Network Issue" + t, Toast.LENGTH_SHORT).show();

                }
            });


        } else if (mode == "1") {
            mode = "1";
            assignmentID = pendingInfo.getCase_assignment_id();
            File file;
            com.squareup.okhttp.OkHttpClient okHttpClient = new com.squareup.okhttp.OkHttpClient();
            okHttpClient.setConnectTimeout(120000, TimeUnit.MILLISECONDS);
            okHttpClient.setReadTimeout(120000, TimeUnit.MILLISECONDS);

            retrofit.Retrofit retrofit = new retrofit.Retrofit.Builder()
                    .baseUrl(getBaseContext().getString(R.string.DomainURL))
                    .client(okHttpClient)
                    .build();


            insuranceAPI = retrofit.create(InsuranceAPI.class);

            MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);

             builder.addFormDataPart("case_assignment_id", assignmentID);
           builder.addFormDataPart("flag", mode);

           for (int i = 0; i < triggerListId.size(); i++) {
                String triggID = triggerListId.get(i);

               builder.addFormDataPart("case_trigger_id", triggID);
            }

            for (int i = 0; i < triggeranswer.size(); i++) {
                String trigans = triggeranswer.get(i);
              builder.addFormDataPart("trigger_answer", trigans);
            }


            for (int i = 0; i < triggerlist.size(); i++) {
               // file = new File(triggerlist.get(i));
                RequestBody fbody = RequestBody.create(MediaType.parse("multipart/form-data"),triggerlist.get(i));
                builder.addFormDataPart("file", i+"", fbody);
                            }
            RequestBody pa = builder.build();
            Call<com.squareup.okhttp.ResponseBody> call =  insuranceAPI.uploadMultiFile(pa);
           call.enqueue(new retrofit.Callback<com.squareup.okhttp.ResponseBody>() {
               @Override
               public void onResponse(retrofit.Response<com.squareup.okhttp.ResponseBody> response, retrofit.Retrofit retrofit) {
                   if (progressDialog != null) {
                       progressDialog.dismiss();
                   }
                   com.squareup.okhttp.ResponseBody res = response.body();
               }

               @Override
               public void onFailure(Throwable t) {
                   if (progressDialog != null) {
                       progressDialog.dismiss();
                   }
                   String err = t.getMessage() == null ? "" : t.getMessage();
                   Log.e("RETROFIT", err);
               }
           });


        }


    }


    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void filelayout() {
        List<String> Document_name = new ArrayList<>();
        List<String> fileNeameList = new ArrayList<>();
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.lineare);

        for (int i = 1; i <= dynamicFileNameInfoList.size(); i++) {
            for (DynamicFileNameInfo dyno : dynamicFileNameInfoList) {
                Document_name.add(dyno.getDocument_name());
                fileNeameList.add(dyno.getAttach_file_name());
            }
            TextView title = new TextView(this);
            TextView button = new TextView(this);
            TextView filename = new TextView(this);
            title.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            title.setText(Document_name.get(i));

            button.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            filename.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            params.setMargins(10, 10, 10, 10);
            Typeface face = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                face = getResources().getFont(R.font.verdana);
            }

            title.setTypeface(face);
            button.setTypeface(face);
            filename.setTypeface(face);
            title.setLayoutParams(params);
            button.setLayoutParams(params);
            button.setBackground(getResources().getDrawable(R.drawable.rounded_border_edittext));

            title.setTextColor(getResources().getColor(R.color.Black));

            button.setTextColor(getResources().getColor(R.color.Black));
            filename.setTextColor(getResources().getColor(R.color.Black));

            button.setPadding(5, 5, 5, 5);
            filename.setPadding(5, 5, 5, 5);

            button.setText("Choose File");

            filename.setTextSize(15f);

            filename.setText(fileNeameList.get(i));
            button.setTextSize(17f);
            title.setTextSize(15f);
            linearLayout.addView(title);
            linearLayout.addView(button);
            linearLayout.addView(filename);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "asf", Toast.LENGTH_SHORT).show();
                }
            });
        }
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
                        selectImage();


                    }
                }
            });


        }


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.file1:
                count = 1;
                selectImage();

                break;

            case R.id.file2:
                count = 2;
                selectImage();

                break;

            case R.id.file3:
                count = 3;
                selectImage();

                break;
            case R.id.file4:
                count = 4;
                selectImage();
                break;

            case R.id.file8:
                count = 8;
                selectImage();
                break;

            case R.id.file9:
                count = 9;
                selectImage();
                break;

            case R.id.file31:
                count = 31;
                selectImage();
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
                pendingInfo.getClaim_no() + "_" + pendingInfo.getCase_assignment_id() + "_" + System.currentTimeMillis() + ".jpg");
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

        String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), thumbnail, "image", null);
        String upload = destination.getPath().substring(destination.getPath().lastIndexOf('/') + 1);
        if (count == 1) {

            filename1.setText(upload);
        } else if (count == 2) {
            filename2.setText(upload);
        } else if (count == 3) {
            filename3.setText(upload);
        } else if (count == 4) {
            filename4.setText(upload);
        } else if (count == 8) {
            filename8.setText(upload);
        } else if (count == 31) {
            filename31.setText(upload);
        } else if (count == count) {
            triggerListId.add(triggerID);
            triggerlist.add(destination);
            filenameholder[count - 100].setText(destination.getPath());
        }
        /*Picasso.with(this).load(path).resize(350, 350).transform(new CircleTransform())
                .centerCrop().memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(ivImage);*/
        //ivImage.setImageBitmap(thumbnail);
    }





    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }
}