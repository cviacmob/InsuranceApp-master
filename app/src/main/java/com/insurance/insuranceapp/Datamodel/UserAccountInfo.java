package com.insurance.insuranceapp.Datamodel;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.Date;
import java.util.List;

/**
 * Created by Balaji on 4/23/2018.
 */
@Table(name = "UserAccountInfo")
public class UserAccountInfo extends Model {
    @Column(name = "consultant_id")
    private String consultant_id;
    @Column(name = "consultant_Name")
    private String consultant_Name;
    @Column(name = "agreementNumber")
    private String agreementNumber;
    @Column(name = "consultant_email")
    private String consultant_email;
    @Column(name = "primary_phone_no")
    private String primary_phone_no;
    @Column(name = "secondary_phone_no")
    private String secondary_phone_no;
    @Column(name = "father_name")
    private String father_name;
    @Column(name = "date_of_birth")
    private String date_of_birth;
    @Column(name = "aadhar_card_number")
    private String aadhar_card_number;
    @Column(name = "driving_license_number")
    private String driving_license_number;
    @Column(name = "permanent_address")
    private String permanent_address;
    @Column(name = "present_address")
    private String present_address;
    @Column(name = "state_id")
    private String state_id;
    @Column(name = "city_id")
    private String city_id;
    @Column(name = "pincode")
    private String pincode;
    @Column(name = "qualification")
    private String qualification;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "account_name")
    private String account_name;
    @Column(name = "account_number")
    private String account_number;
    @Column(name = "bank_name")
    private String bank_name;
    @Column(name = "branch_location")
    private String branch_location;
    @Column(name = "ifsc_code")
    private String ifsc_code;
    @Column(name = "status")
    private String status;
    @Column(name = "Pending")
    private String Pending;
    @Column(name = "Saved")
    private String Saved;
    @Column(name = "Submitted")
    private String Submitted;
    @Column(name = "raise_query")
    private String Raise_query;
    @Column(name = "approve_raise_query")
    private String Approve_raise_query;

    public UserAccountInfo(){
        super();
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

    public void setConsultant_id(String consultant_id) {
        this.consultant_id = consultant_id;
    }

    public String getConsultant_Name() {
        return consultant_Name;
    }

    public void setConsultant_Name(String consultant_Name) {
        this.consultant_Name = consultant_Name;
    }

    public String getAgreementNumber() {
        return agreementNumber;
    }

    public void setAgreementNumber(String agreementNumber) {
        this.agreementNumber = agreementNumber;
    }

    public String getConsultant_id() {
        return consultant_id;
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

    public static List<UserAccountInfo> getAll(){

        return new Select()
                .all()
                .from(UserAccountInfo.class)
                .execute();

    }
    public static void getdeletecareprovider( ){

        new Delete().from(UserAccountInfo.class).execute();
    }
}
