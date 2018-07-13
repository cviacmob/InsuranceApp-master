package com.insurance.insuranceapp.Datamodel;

import java.io.File;

public class PatientBlockInfo {

    private File patient_doctor_questionarie;
    private File patient_id_proof;
    private File patient_questionarie;
    private File previous_op_ip_records;
    private File patient_narration_letter;
    private File patient_authorization;
    private File query_reply;
    private File others;

    public File getPatient_doctor_questionarie() {
        return patient_doctor_questionarie;
    }

    public void setPatient_doctor_questionarie(File patient_doctor_questionarie) {
        this.patient_doctor_questionarie = patient_doctor_questionarie;
    }

    public File getPatient_id_proof() {
        return patient_id_proof;
    }

    public void setPatient_id_proof(File patient_id_proof) {
        this.patient_id_proof = patient_id_proof;
    }

    public File getPatient_questionarie() {
        return patient_questionarie;
    }

    public void setPatient_questionarie(File patient_questionarie) {
        this.patient_questionarie = patient_questionarie;
    }

    public File getPrevious_op_ip_records() {
        return previous_op_ip_records;
    }

    public void setPrevious_op_ip_records(File previous_op_ip_records) {
        this.previous_op_ip_records = previous_op_ip_records;
    }

    public File getPatient_narration_letter() {
        return patient_narration_letter;
    }

    public void setPatient_narration_letter(File patient_narration_letter) {
        this.patient_narration_letter = patient_narration_letter;
    }

    public File getPatient_authorization() {
        return patient_authorization;
    }

    public void setPatient_authorization(File patient_authorization) {
        this.patient_authorization = patient_authorization;
    }

    public File getQuery_reply() {
        return query_reply;
    }

    public void setQuery_reply(File query_reply) {
        this.query_reply = query_reply;
    }

    public File getOthers() {
        return others;
    }

    public void setOthers(File others) {
        this.others = others;
    }
}
