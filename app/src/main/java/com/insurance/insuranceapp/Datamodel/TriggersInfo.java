package com.insurance.insuranceapp.Datamodel;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by BALA on 08-05-2018.
 */

public class TriggersInfo {

    private String case_trigger_id;

    private String trigger_name;

    private String trigger_file;

    private String trigger_answer;


    public String getCase_trigger_id() {
        return case_trigger_id;
    }

    public void setCase_trigger_id(String case_trigger_id) {
        this.case_trigger_id = case_trigger_id;
    }

    public String getTrigger_name() {
        return trigger_name;
    }

    public void setTrigger_name(String trigger_name) {
        this.trigger_name = trigger_name;
    }

    public String getTrigger_file() {
        return trigger_file;
    }

    public void setTrigger_file(String trigger_file) {
        this.trigger_file = trigger_file;
    }

    public String getTrigger_answer() {
        return trigger_answer;
    }

    public void setTrigger_answer(String trigger_answer) {
        this.trigger_answer = trigger_answer;
    }


}
