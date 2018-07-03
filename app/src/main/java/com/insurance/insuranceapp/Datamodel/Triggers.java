package com.insurance.insuranceapp.Datamodel;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "Triggers")
public class Triggers extends Model {
    @Column(name = "case_assignment_id")
    private String case_assignment_id;
    @Column(name = "case_trigger_id")
    private String case_trigger_id;
    @Column(name = "flag")
    private String flag;
    @Column(name = "trigger_name")
    private String trigger_name;
    @Column(name = "trigger_file")
    private String trigger_file;
    @Column(name = "trigger_answer")
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

    public static List<Triggers> gettriAll(){

        return new Select()
                .all()
                .from(Triggers.class)
                .execute();

    }

    public static void getdeletetriggers( ){

        new Delete().from(Triggers.class).execute();
    }

}
