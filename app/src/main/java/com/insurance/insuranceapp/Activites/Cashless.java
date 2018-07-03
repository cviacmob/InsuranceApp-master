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

import com.insurance.insuranceapp.Datamodel.PendingCaseListInfo;
import com.insurance.insuranceapp.Datamodel.PendingInfo;
import com.insurance.insuranceapp.Datamodel.UserAccountInfo;
import com.insurance.insuranceapp.R;
import com.insurance.insuranceapp.RestAPI.InsuranceAPI;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.insurance.insuranceapp.Activites.DeathCliamActivity.FILE_PICKER_REQUEST_CODE;
import static com.insurance.insuranceapp.Activites.DeathCliamActivity.PERMISSIONS_REQUEST_CODE;

public class Cashless extends AppCompatActivity  implements
        View.OnClickListener{

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

    private TextView title31,file31,filename31;

    private EditText ed_comments;
    private EditText ed_convance;
    private ImageView calendar;
    private DatePickerDialog datePickerDialog;
    final Calendar c = Calendar.getInstance();
    int mYear = c.get(Calendar.YEAR); // current year
    int mMonth = c.get(Calendar.MONTH); // current month
    int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
    private Button backbutton;


    private String string1= "<font color='#000000'>Company Authorization Letter towards the hospital </font>" + "<font color='#FF0000'>*</font>";
    private String string2= "<font color='#000000'>Insured Questionnaire </font>" + "<font color='#FF0000'>*</font>";
    private String string3= "<font color='#000000'>Insured Questionnaire Form </font>" + "<font color='#FF0000'>*</font>";
    private String string4= "<font color='#000000'>Casesheet  </font>" + "<font color='#FF0000'>*</font>";
    private String string5= "<font color='#000000'>Insured Questionnaire </font>" + "<font color='#FF0000'>*</font>";
    private String string6 = "<font color='#000000'>Query reply </font>" + "<font color='#FF0000'>*</font>";
    private String string7 = "IP register";
    private String string8 = "<font color='#000000'>Lab Register </font>" + "<font color='#FF0000'>*</font>";
    private String string9 = "ID proof";
    private String string10 = "Previous OP and IP records";
    private String string11= "<font color='#000000'>Field Investigation report </font>" + "<font color='#FF0000'>*</font>";
    private String string12= "Narration letter";
    private String string13= "Others";
    private String string14= "Medical Records Bill";

    private String string31 = "Conveyance File(s)";
    private String triggerreply = "<font color='#000000'>Trigger Reply </font>" + "<font color='#FF0000'>*</font>";
    private Button submit;
    private String submitted_date ="",triggerfinding = "",comments ="",Convanceamt = "",temp ="";
    private TextInputLayout textInputLayout;
    private List<PendingInfo> pendingInfoList;
    private RelativeLayout relativeLayout;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashless);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pendingInfo = (PendingCaseListInfo) getIntent().getSerializableExtra("data");
        if(pendingInfo!=null){
            setTitle(pendingInfo.getClaim_no() +" "+"Cashless Block");
        }


        ed_comments = (EditText)findViewById(R.id.ed_comments);

        ed_convance = (EditText)findViewById(R.id.ed_convence);
        textInputLayout = (TextInputLayout)findViewById(R.id.input_edit_consult);

        submit = (Button)findViewById(R.id.bt_submit);
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
        title7.setText(string7);
        title8 = (TextView)findViewById(R.id.title8);
        title8.setText(Html.fromHtml(string8));
        title9 = (TextView)findViewById(R.id.title9);
        title9.setText((string9));
        title10 = (TextView)findViewById(R.id.title10);
        title10.setText((string10));
        title11 = (TextView)findViewById(R.id.title11);
        title11.setText(Html.fromHtml(string11));
        title12 = (TextView)findViewById(R.id.title12);
        title12.setText((string12));
        title13 = (TextView)findViewById(R.id.title13);
        title13.setText((string13));
        title14 = (TextView)findViewById(R.id.title14);
        title14.setText((string14));

        title31 = (TextView)findViewById(R.id.title31);
        title31.setText((string31));


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
        file14 = (TextView)findViewById(R.id.file14);
        file14.setOnClickListener((View.OnClickListener) this);

        file31 = (TextView)findViewById(R.id.file31);
        file31.setOnClickListener((View.OnClickListener) this);
        backbutton = (Button)findViewById(R.id.bt_back);
        createEditTextView();

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(submitted_date!=null && !submitted_date.isEmpty())
                {
                    comments = ed_comments.getText().toString();

                    Convanceamt = ed_convance.getText().toString();
                }else{
                    textInputLayout.setError("Cannot be empty");
                }

            }
        });

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
            Intent in = new Intent(Cashless.this,MainActivity.class);
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
                selectImage();
                break;

            case R.id.file2:
                selectImage();
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

            case R.id.file15:
                selectImage();
                break;
            case R.id.file31:
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

    }

        }


