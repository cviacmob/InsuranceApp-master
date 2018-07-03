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

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.ArrayList;

import java.util.List;
import java.util.concurrent.TimeUnit;



import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.insurance.insuranceapp.Activites.DisabilityActivity.PERMISSIONS_REQUEST_CODE;
import static com.insurance.insuranceapp.Activites.DynamicActivity.FILE_PICKER_REQUEST_CODE;

public class DeathCliamActivity extends AppCompatActivity implements
        View.OnClickListener {
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
    private TextView title15,file15,filename15;
    private TextView title16,file16,filename16;
    private TextView title17,file17,filename17;
    private TextView title18,file18,filename18;
    private TextView title19,file19,filename19;
    private TextView title20,file20,filename20;
    private TextView title21,file21,filename21;
    private TextView title22,file22,filename22;
    private TextView title23,file23,filename23;
    private TextView title24,file24,filename24;
    private TextView title25,file25,filename25;

    private TextView title31,file31,filename31;

    private EditText ed_comments;
    private EditText ed_convance;

    private Button backbutton;



    private String string1= "<font color='#000000'>Company Authorization Letter towards the hospital </font>" + "<font color='#FF0000'>*</font>";
    private String string2= "<font color='#000000'>Check List </font>" + "<font color='#FF0000'>*</font>";
    private String string3= "<font color='#000000'>Claimant Questionnaire Form </font>" + "<font color='#FF0000'>*</font>";
    private String string4= "<font color='#000000'>Claimant Questionaire </font>" + "<font color='#FF0000'>*</font>";
    private String string5= "<font color='#000000'>Narration Letter </font>" + "<font color='#FF0000'>*</font>";
    private String string6 = "<font color='#000000'>FIR Copy </font>" + "<font color='#FF0000'>*</font>";
    private String string7 = "Inquest report";
    private String string8 = "Spot Photos";
    private String string9 = "<font color='#000000'>Death Certificate  </font>" + "<font color='#FF0000'>*</font>";
    private String string10 = "<font color='#000000'>Family Photo  </font>" + "<font color='#FF0000'>*</font>";
    private String string11= "<font color='#000000'>Death Person Photo </font>" + "<font color='#FF0000'>*</font>";
    private String string12= "<font color='#000000'>Claimant Photo </font>" + "<font color='#FF0000'>*</font>";
    private String string13= "<font color='#000000'>Income Proof of the Insured  </font>" + "<font color='#FF0000'>*</font>";
    private String string14= "<font color='#000000'>Bank details(Cancelled Cheaque or passbook first sheet)  </font>" + "<font color='#FF0000'>*</font>";
    private String string15= "<font color='#000000'>Witness Letter 1 with ID proof </font>" + "<font color='#FF0000'>*</font>";
    private String string16 = "<font color='#000000'>Witness Letter 2 with ID proof </font>" + "<font color='#FF0000'>*</font>";
    private String string17 = "<font color='#000000'>Witness Letter 3 with ID proof </font>" + "<font color='#FF0000'>*</font>";
    private String string18 = "<font color='#000000'>ID proof of Death Person </font>" + "<font color='#FF0000'>*</font>";
    private String string19 = "<font color='#000000'>ID proof of claimant  </font>" + "<font color='#FF0000'>*</font>";
    private String string20 = "Driving License of Death Person";
    private String string21= "Legal Hair certificate";
    private String string22= "<font color='#000000'>Cerimination Ground Certificate </font>" + "<font color='#FF0000'>*</font>";
    private String string23= "Hospital records";
    private String string24= "RC Book of the vehicle";
    private String string25= "Others";

    private String string31 = "Conveyance Amount(₹)";
    private String triggerreply = "<font color='#000000'>Trigger Reply </font>" + "<font color='#FF0000'>*</font>";
    private Button submit;
    private String comments ="",Convanceamt = "",temp ="";

    private List<PendingInfo> pendingInfoList;
    private RelativeLayout relativeLayout;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_death_cliam);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pendingInfo = (PendingCaseListInfo) getIntent().getSerializableExtra("data");
        if(pendingInfo!=null){
            setTitle(pendingInfo.getClaim_no() +" "+"Death Claim Block");
        }


        ed_comments = (EditText)findViewById(R.id.ed_comments);

        ed_convance = (EditText)findViewById(R.id.ed_convence);


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
        title14 = (TextView)findViewById(R.id.title14);
        title14.setText(Html.fromHtml(string14));
        title15 = (TextView)findViewById(R.id.title15);
        title15.setText(Html.fromHtml(string15));
        title16 = (TextView)findViewById(R.id.title16);
        title16.setText(Html.fromHtml(string16));
        title17 = (TextView)findViewById(R.id.title17);
        title17.setText(Html.fromHtml(string17));
        title18 = (TextView)findViewById(R.id.title18);
        title18.setText(Html.fromHtml(string18));
        title19 = (TextView)findViewById(R.id.title19);
        title19.setText(Html.fromHtml(string19));
        title20 = (TextView)findViewById(R.id.title20);
        title20.setText(string20);
        title21 = (TextView)findViewById(R.id.title21);
        title21.setText(string21);
        title22 = (TextView)findViewById(R.id.title22);
        title22.setText(Html.fromHtml(string22));
        title23 = (TextView)findViewById(R.id.title23);
        title23.setText(string23);
        title24 = (TextView)findViewById(R.id.title24);
        title24.setText(string24);
        title25 = (TextView)findViewById(R.id.title25);
        title25.setText(string25);

        title31 = (TextView)findViewById(R.id.title31);
        title31.setText(string31);
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
        file9 = (TextView)findViewById(R.id.file9);
        file9.setOnClickListener((View.OnClickListener) this);
        file10 = (TextView)findViewById(R.id.file10);
        file10.setOnClickListener((View.OnClickListener) this);
        file11 = (TextView)findViewById(R.id.file_11);
        file11.setOnClickListener((View.OnClickListener) this);
        file12 = (TextView)findViewById(R.id.file12);
        file12.setOnClickListener((View.OnClickListener) this);
        file13 = (TextView)findViewById(R.id.file13);
        file13.setOnClickListener((View.OnClickListener) this);
        file14 = (TextView)findViewById(R.id.file14);
        file14.setOnClickListener((View.OnClickListener) this);
        file15 = (TextView)findViewById(R.id.file15);
        file15.setOnClickListener((View.OnClickListener) this);
        file16 = (TextView)findViewById(R.id.file16);
        file16.setOnClickListener((View.OnClickListener) this);
        file17 = (TextView)findViewById(R.id.file17);
        file17.setOnClickListener((View.OnClickListener) this);
        file18 = (TextView)findViewById(R.id.file18);
        file18.setOnClickListener((View.OnClickListener) this);
        file19 = (TextView)findViewById(R.id.file19);
        file19.setOnClickListener((View.OnClickListener) this);
        file20 = (TextView)findViewById(R.id.file20);
        file20.setOnClickListener((View.OnClickListener) this);
        file21 = (TextView)findViewById(R.id.file21);
        file21.setOnClickListener((View.OnClickListener) this);
        file22 = (TextView)findViewById(R.id.file22);
        file22.setOnClickListener((View.OnClickListener) this);
        file23 = (TextView)findViewById(R.id.file23);
        file23.setOnClickListener((View.OnClickListener) this);
        file24 = (TextView)findViewById(R.id.file24);
        file24.setOnClickListener((View.OnClickListener) this);
        file25 = (TextView)findViewById(R.id.file25);
        file25.setOnClickListener((View.OnClickListener) this);

        file31 = (TextView)findViewById(R.id.file31);
        file31.setOnClickListener((View.OnClickListener) this);



        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comments = ed_comments.getText().toString();

                   Convanceamt = ed_convance.getText().toString();


            }
        });


        createEditTextView();



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
            Intent in = new Intent(DeathCliamActivity.this,MainActivity.class);
            startActivity(in);
            return true;
        }
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.file_1:
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
            case R.id.file16:
                selectImage();
                break;

            case R.id.file17:
                selectImage();
                break;
            case R.id.file18:
                selectImage();
                break;

            case R.id.file19:
                selectImage();
                break;

            case R.id.file20:
                selectImage();
                break;
            case R.id.file21:
                selectImage();
                break;

            case R.id.file22:
                selectImage();
                break;

            case R.id.file23:
                selectImage();
                break;
            case R.id.file24:
                selectImage();
                break;

            case R.id.file25:
                selectImage();
                break;
            case R.id.file26:
                selectImage();
                break;
            case R.id.file31:
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
