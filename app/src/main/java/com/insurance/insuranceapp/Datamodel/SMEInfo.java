package com.insurance.insuranceapp.Datamodel;

import java.io.File;

public class SMEInfo {

    private File sme_form;
    private File authorization_letter;
    private File company_snap;
    private File company_visiting_card;
    private File sme_verification_report;
    private File hr_letter;
    private File company_registration_certificate;
    private File other_file;

    public File getSme_form() {
        return sme_form;
    }

    public void setSme_form(File sme_form) {
        this.sme_form = sme_form;
    }

    public File getAuthorization_letter() {
        return authorization_letter;
    }

    public void setAuthorization_letter(File authorization_letter) {
        this.authorization_letter = authorization_letter;
    }

    public File getCompany_snap() {
        return company_snap;
    }

    public void setCompany_snap(File company_snap) {
        this.company_snap = company_snap;
    }

    public File getCompany_visiting_card() {
        return company_visiting_card;
    }

    public void setCompany_visiting_card(File company_visiting_card) {
        this.company_visiting_card = company_visiting_card;
    }

    public File getSme_verification_report() {
        return sme_verification_report;
    }

    public void setSme_verification_report(File sme_verification_report) {
        this.sme_verification_report = sme_verification_report;
    }

    public File getHr_letter() {
        return hr_letter;
    }

    public void setHr_letter(File hr_letter) {
        this.hr_letter = hr_letter;
    }

    public File getCompany_registration_certificate() {
        return company_registration_certificate;
    }

    public void setCompany_registration_certificate(File company_registration_certificate) {
        this.company_registration_certificate = company_registration_certificate;
    }

    public File getOther_file() {
        return other_file;
    }

    public void setOther_file(File other_file) {
        this.other_file = other_file;
    }
}
