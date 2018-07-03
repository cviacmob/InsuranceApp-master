package com.insurance.insuranceapp.RestAPI;

import com.insurance.insuranceapp.Datamodel.DynamicFileNameInfo;
import com.insurance.insuranceapp.Datamodel.GetPaymentsInfo;
import com.insurance.insuranceapp.Datamodel.PendingCaseListInfo;
import com.insurance.insuranceapp.Datamodel.ProfileInfo;
import com.insurance.insuranceapp.Datamodel.QueryInfo;
import com.insurance.insuranceapp.Datamodel.RegistrationInfo;
import com.insurance.insuranceapp.Datamodel.TriggersInfo;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.io.File;
import java.util.List;


import okhttp3.MultipartBody;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit2.http.Part;

/**
 * Created by Balaji on 4/16/2018.
 */

public interface InsuranceAPI {

    @POST("/insapi/index.php/login")
    Call<ProfileInfo> getlogin(@Body RegistrationInfo registrationInfo);
    @FormUrlEncoded
    @POST("/insapi/index.php/cases")
    Call<List<PendingCaseListInfo>> getpendinglist(@Field("consultant_id") String consultant_id,
                                                   @Field("status") String status);
    @FormUrlEncoded
    @POST("/insapi/index.php/savestatus")
    Call<List<String>> getsavedata( @Field("consultant_id") String consultant_id,
                                    @Field("assign_status") String status,
                                    @Field("case_id") String caseid,
                                    @Field("case_assignment_id") String assignment_id,
                                    @Field("other_case_type_id") String othercasetypeid,
                                    @Field("case_type") String casetype);
    @FormUrlEncoded
    @POST("/insapi/index.php/fees")
    Call<List<GetPaymentsInfo>> getgetpayments(@Field("consultant_id") String consultant_id,
                                               @Field("fee_is_paid") String status);


    @FormUrlEncoded
    @POST("/insapi/index.php/dynamiccase")
    Call<List<DynamicFileNameInfo>> getdetails(@Field("consultant_id") String consultant_id,
                                               @Field("assign_status") String status,
                                               @Field("case_assignment_id") String assignment_id,
                                               @Field("flag") String flag);

    @FormUrlEncoded
    @POST("/insapi/index.php/triggers")
    Call<List<TriggersInfo>> gettriggersdetails(@Field("case_assignment_id") String assignment_id,
                                                @Field("flag") String flag,
                                                @Field("case_trigger_id") List<String> case_trigger_id,
                                                @Field("trigger_answer") List<String> trigger_answer,
                                                @Field("file") List<File> file);



    @POST("/insapi/index.php/triggers")
    Call<ResponseBody> uploadMultiFile(@Body RequestBody file);

    @FormUrlEncoded
    @POST("/insapi/index.php/cases")
    Call<List<QueryInfo>> getquerycases(@Field("consultant_id") String assignment_id,
                                        @Field("status") String flag);

    @Multipart
    @POST("/insapi/index.php/triggers")
    Call<ResponseBody> uploadMultipleFilesDynamic(

            @Part("case_assignment_id") RequestBody assignment_id,
            @Part("flag") RequestBody flag,
            @Part("case_trigger_id") RequestBody case_trigger_id,
            @Part("trigger_answer") RequestBody trigger_answer,
            @Part MultipartBody.Part[] image);

    @POST("insapi/audioupload.php/")
    Call<ResponseBody> sendAudio(@Body RequestBody body);

           /* (@Query("consultant_id") String consultant_id,
                                 @Query("case_type") String case_type,
                                 @Query("assign_status") String assign_status,
                                 @Query("case_id") String case_id,
                                 @Query("case_assignment_id") String case_assignment_id,
                                 @Query("claim_no") String claim_no,
                                 @Query("case_type_id") String case_type_id,
                                 @Part("fileToUpload\"; filename=\"audio.3gp\" ") RequestBody file,
                                 @Query("submit") String submit);*/
}