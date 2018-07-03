package com.insurance.insuranceapp.Datamodel;

import java.io.Serializable;

/**
 * Created by Balaji on 4/23/2018.
 */

public class PendingCaseListInfo implements Serializable {
    private String case_id;
    private String case_type;
    private String case_type_id;
    private String other_case_type_id;
    private String case_assignment_id;
    private String case_assigned_on;
    private String assign_status;
    private String patient_name;
    private String claim_no;
    private String policy_number;
    private String company_name;
    private String hospital_name;

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
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

    public String getCase_type_id() {
        return case_type_id;
    }

    public void setCase_type_id(String case_type_id) {
        this.case_type_id = case_type_id;
    }

    public String getOther_case_type_id() {
        return other_case_type_id;
    }

    public void setOther_case_type_id(String other_case_type_id) {
        this.other_case_type_id = other_case_type_id;
    }

    public String getCase_assignment_id() {
        return case_assignment_id;
    }

    public void setCase_assignment_id(String case_assignment_id) {
        this.case_assignment_id = case_assignment_id;
    }

    public String getCase_assigned_on() {
        return case_assigned_on;
    }

    public void setCase_assigned_on(String case_assigned_on) {
        this.case_assigned_on = case_assigned_on;
    }

    public String getAssign_status() {
        return assign_status;
    }

    public void setAssign_status(String assign_status) {
        this.assign_status = assign_status;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getClaim_no() {
        return claim_no;
    }

    public void setClaim_no(String claim_no) {
        this.claim_no = claim_no;
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

    public PendingCaseListInfo(){

    }



}
