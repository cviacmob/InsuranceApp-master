package com.insurance.insuranceapp.Utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Balaji on 4/11/2018.
 */

public class ExpandableListDataPump {

    public static LinkedHashMap<String, List<String>> getData() {
        LinkedHashMap<String, List<String>> expandableListDetail = new LinkedHashMap<String, List<String>>();

        List<String> dashboard = new ArrayList<String>();

        List<String> payments = new ArrayList<String>();
        payments.add("Reserved Payments");
        payments.add("Confirmed Payments");

        List<String> cases = new ArrayList<String>();
        cases.add("Pending Cases");
        cases.add("Query cases");
        cases.add("Submitted Cases");
        cases.add("Saved Cases");


        Collections.sort(payments);
        Collections.sort(cases);

        expandableListDetail.put("Dashboard",dashboard);
        expandableListDetail.put("Payments", payments);
        expandableListDetail.put("Cases", cases );

        return expandableListDetail;
    }
}