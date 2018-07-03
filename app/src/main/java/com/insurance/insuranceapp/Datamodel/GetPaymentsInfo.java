package com.insurance.insuranceapp.Datamodel;

/**
 * Created by Balaji on 4/27/2018.
 */

public class GetPaymentsInfo {
    private String mrd_amount;
    private String pay_conveyance;
    private String consultant_fee;
    private String consult_insentivies;
    private String case_type;
    private String claim_no;
    private String patient_name;

    public String getMrd_amount() {
        return mrd_amount;
    }

    public void setMrd_amount(String mrd_amount) {
        this.mrd_amount = mrd_amount;
    }

    public String getPay_conveyance() {
        return pay_conveyance;
    }

    public void setPay_conveyance(String pay_conveyance) {
        this.pay_conveyance = pay_conveyance;
    }

    public String getConsultant_fee() {
        return consultant_fee;
    }

    public void setConsultant_fee(String consultant_fee) {
        this.consultant_fee = consultant_fee;
    }

    public String getConsult_insentivies() {
        return consult_insentivies;
    }

    public void setConsult_insentivies(String consult_insentivies) {
        this.consult_insentivies = consult_insentivies;
    }

    public String getCase_type() {
        return case_type;
    }

    public void setCase_type(String case_type) {
        this.case_type = case_type;
    }

    public String getClaim_no() {
        return claim_no;
    }

    public void setClaim_no(String claim_no) {
        this.claim_no = claim_no;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }
}
