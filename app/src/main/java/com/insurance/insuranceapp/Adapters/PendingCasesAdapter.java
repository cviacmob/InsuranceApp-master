package com.insurance.insuranceapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.insurance.insuranceapp.Activites.ReservedPaymentsActivity;
import com.insurance.insuranceapp.Datamodel.PendingCaseListInfo;
import com.insurance.insuranceapp.R;

import java.util.List;

/**
 * Created by Balaji on 4/14/2018.
 */

public class PendingCasesAdapter extends ArrayAdapter<PendingCaseListInfo> {

    private List<PendingCaseListInfo> pendingInfoList;

    private String temp = "";
    private int lastPostion = -1;

    Context mContext;

    public PendingCasesAdapter(List<PendingCaseListInfo> objects, Context context) {
        super(context, R.layout.layout_pending, objects);
        pendingInfoList = objects;
        mContext = context;


    }

    public static class ViewHolder {
        public TableLayout tableLayout;
        public TableRow row1,row2,row3,row4,row5,row6,row7,row8,row9,row10,row11;
        public TextView claim_number,patientname,hospitalName,TAT,part;


    }


    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        final PendingCaseListInfo all = getItem(position);
        View vw = convertView;
        ViewHolder holder;

        if (convertView == null) {

            LayoutInflater inf = LayoutInflater.from(getContext());
            vw = inf.inflate(R.layout.layout_pending, parent, false);
            holder = new ViewHolder();
            holder.tableLayout = (TableLayout)vw.findViewById(R.id.tableLayout);



            holder.row1 = (TableRow) holder.tableLayout.getChildAt(0);
            holder.row2 = (TableRow) holder.tableLayout.getChildAt(1);
            holder.row3 = (TableRow) holder.tableLayout.getChildAt(2);
            holder.row4 = (TableRow) holder.tableLayout.getChildAt(3);
            holder.row5 = (TableRow) holder.tableLayout.getChildAt(4);




            holder.claim_number =(TextView)holder.row1.getChildAt(1);
            holder.patientname =(TextView)holder.row2.getChildAt(1);
            holder.hospitalName =(TextView)holder.row3.getChildAt(1);
            holder.TAT =(TextView)holder.row4.getChildAt(1);
            holder.part =(TextView)holder.row5.getChildAt(1);



            vw.setTag(holder);
        } else {
            holder = (ViewHolder) vw.getTag();
        }


        holder.claim_number.setText(all.getClaim_no());
        holder.patientname.setText(all.getPatient_name());
        holder.hospitalName.setText(all.getHospital_name());
        holder.TAT.setText(all.getCase_type_id());
        temp = all.getCase_type();
        if(temp!=null && temp.equalsIgnoreCase("default")){
            holder.part.setText(getPartName(all.getCase_type_id()));
        }else {
            holder.part.setText(getPartName("Others"));
        }





        return vw;


    }

    private String getPartName(String in){
        String partName = in;
        if(in.equalsIgnoreCase("1")){
            partName= "Hospital Part";
        }else if(in.equalsIgnoreCase("2")){
            partName= "Patient Part";
        }else if(in.equalsIgnoreCase("3")){
            partName= "SME";
        }else if(in.equalsIgnoreCase("4")){
            partName= "DeathCliam";
        }else if(in.equalsIgnoreCase("5")){
            partName= "Disability";
        }else if(in.equalsIgnoreCase("6")){
            partName= "PersonalAccident";
        }else if(in.equalsIgnoreCase("7")){
            partName= "BillVerificationHospital";
        }else if(in.equalsIgnoreCase("8")){
            partName= "BillVerificationPharmacy";
        }else if(in.equalsIgnoreCase("9")){
            partName= "DocumentsVerification";
        }else if(in.equalsIgnoreCase("10")){
            partName= "Cashless";
        }else if(in.equalsIgnoreCase("11")){
            partName= "IntimationCase";
        }

        return partName;
    }
}

