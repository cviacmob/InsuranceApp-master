package com.insurance.insuranceapp.Datamodel;

import com.activeandroid.query.Select;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Balaji on 4/13/2018.
 */

public class ProfileInfo  {
    private String consultant_id;
    private String consultant_Name;
    private String agreementNumber;
    private String consultant_email;
    private String primary_phone_no;
    private String secondary_phone_no;
    private String father_name;
    private String date_of_birth;
    private String aadhar_card_number;
    private String driving_license_number;
    private String permanent_address;
    private String present_address;
    private String state_id;
    private String city_id;
    private String pincode;
    private String qualification;
    private String nickname;
    private String account_name;
    private String account_number;
    private String bank_name;
    private String branch_location;
    private String ifsc_code;
    private String status;
    private String Pending;
    private String Saved;
    private String Submitted;
    private String Raise_query;
    private String Approve_raise_query;

    public ProfileInfo(){

    }

    public String getApprove_raise_query() {
        return Approve_raise_query;
    }

    public void setApprove_raise_query(String approve_raise_query) {
        Approve_raise_query = approve_raise_query;
    }

    public String getRaise_query() {
        return Raise_query;
    }

    public void setRaise_query(String raise_query) {
        this.Raise_query = raise_query;
    }

    public String getConsultant_Name() {
        return consultant_Name;
    }

    public void setConsultant_Name(String consultant_Name) {
        this.consultant_Name = consultant_Name;
    }

    public String getPending() {
        return Pending;
    }

    public void setPending(String pending) {
        Pending = pending;
    }

    public String getSaved() {
        return Saved;
    }

    public void setSaved(String saved) {
        Saved = saved;
    }

    public String getSubmitted() {
        return Submitted;
    }

    public void setSubmitted(String submitted) {
        Submitted = submitted;
    }

    public String getAgreementNumber() {
        return agreementNumber;
    }

    public void setAgreementNumber(String agreementNumber) {
        this.agreementNumber = agreementNumber;
    }

    public String getConsultant_email() {
        return consultant_email;
    }

    public void setConsultant_email(String consultant_email) {
        this.consultant_email = consultant_email;
    }

    public String getPrimary_phone_no() {
        return primary_phone_no;
    }

    public void setPrimary_phone_no(String primary_phone_no) {
        this.primary_phone_no = primary_phone_no;
    }

    public String getSecondary_phone_no() {
        return secondary_phone_no;
    }

    public void setSecondary_phone_no(String secondary_phone_no) {
        this.secondary_phone_no = secondary_phone_no;
    }

    public String getFather_name() {
        return father_name;
    }

    public void setFather_name(String father_name) {
        this.father_name = father_name;
    }


    public String getAadhar_card_number() {
        return aadhar_card_number;
    }

    public void setAadhar_card_number(String aadhar_card_number) {
        this.aadhar_card_number = aadhar_card_number;
    }

    public String getDriving_license_number() {
        return driving_license_number;
    }

    public void setDriving_license_number(String driving_license_number) {
        this.driving_license_number = driving_license_number;
    }

    public String getPermanent_address() {
        return permanent_address;
    }

    public void setPermanent_address(String permanent_address) {
        this.permanent_address = permanent_address;
    }

    public String getPresent_address() {
        return present_address;
    }

    public void setPresent_address(String present_address) {
        this.present_address = present_address;
    }

    public String getConsultant_id() {
        return consultant_id;
    }

    public void setConsultant_id(String consultant_id) {
        this.consultant_id = consultant_id;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBranch_location() {
        return branch_location;
    }

    public void setBranch_location(String branch_location) {
        this.branch_location = branch_location;
    }

    public String getIfsc_code() {
        return ifsc_code;
    }

    public void setIfsc_code(String ifsc_code) {
        this.ifsc_code = ifsc_code;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




}
