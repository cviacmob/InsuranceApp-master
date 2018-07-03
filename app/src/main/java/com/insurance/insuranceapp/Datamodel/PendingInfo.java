package com.insurance.insuranceapp.Datamodel;

import java.io.Serializable;

/**
 * Created by Balaji on 4/14/2018.
 */

public class PendingInfo implements Serializable {

    private String case_id;
    private String case_type;
    private String case_assigned_on;
    private String patient_name;
    private String cliam_no;
    private String status;
    private String policy_number;
    private String company_name;



    public PendingInfo(){

    }

    public String getCase_id() {
        return case_id;
    }

    public void setCase_id(String case_id) {
        this.case_id = case_id;
    }

    public String getCase_type() {
        return case_type;
    }

    public void setCase_type(String case_type) {
        this.case_type = case_type;
    }

    public String getCase_assigned_on() {
        return case_assigned_on;
    }

    public void setCase_assigned_on(String case_assigned_on) {
        this.case_assigned_on = case_assigned_on;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getCliam_no() {
        return cliam_no;
    }

    public void setCliam_no(String cliam_no) {
        this.cliam_no = cliam_no;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPolicy_number() {
        return policy_number;
    }

    public void setPolicy_number(String policy_number) {
        this.policy_number = policy_number;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }
}
