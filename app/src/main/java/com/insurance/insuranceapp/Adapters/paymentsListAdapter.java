package com.insurance.insuranceapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.insurance.insuranceapp.Activites.ReservedPaymentsActivity;
import com.insurance.insuranceapp.Datamodel.GetPaymentsInfo;
import com.insurance.insuranceapp.Datamodel.PendingCaseListInfo;
import com.insurance.insuranceapp.R;

import java.util.List;

/**
 * Created by Balaji on 4/27/2018.
 */

public class paymentsListAdapter extends ArrayAdapter<GetPaymentsInfo> {

private List<GetPaymentsInfo> pendingInfoList;


private int lastPostion = -1;

        Context mContext;

public paymentsListAdapter(List<GetPaymentsInfo> objects, Context context) {
        super(context, R.layout.activity_reserved_payments, objects);
        pendingInfoList = objects;
        mContext = context;
        }

public static class ViewHolder {
    public TableLayout tableLayout;
    public TableRow row1,row2,row3,row4,row5,row6,row7,row8,row9,row10,row11;
    public TextView claim_number,patientname,blockname,convanceypay,incentive,TA,MRD,totalamt;


}


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GetPaymentsInfo pay = getItem(position);
        View vw = convertView;
       ViewHolder holder;

        if (convertView == null) {

            LayoutInflater inf = LayoutInflater.from(getContext());
            vw = inf.inflate(R.layout.activity_reserved_payments, parent, false);
            holder = new ViewHolder();
            holder.tableLayout = (TableLayout)vw.findViewById(R.id.tableLayout);

            holder.row1 = (TableRow) holder.tableLayout.getChildAt(0);
            holder.row2 = (TableRow)  holder.tableLayout.getChildAt(1);
            holder.row3 = (TableRow)  holder.tableLayout.getChildAt(2);
            holder.row4 = (TableRow)  holder.tableLayout.getChildAt(3);
            holder.row5 = (TableRow)  holder.tableLayout.getChildAt(4);
            holder.row6 = (TableRow)  holder.tableLayout.getChildAt(5);
            holder.row7 = (TableRow)  holder.tableLayout.getChildAt(6);
            holder.row9 = (TableRow)  holder.tableLayout.getChildAt(7);


            holder.claim_number =(TextView) holder.row1.getChildAt(1);
            holder.patientname =(TextView) holder.row2.getChildAt(1);
            holder.blockname =(TextView) holder.row3.getChildAt(1);
            holder.convanceypay=(TextView) holder.row4.getChildAt(1);
            holder.incentive =(TextView) holder.row5.getChildAt(1);
            holder.TA =(TextView) holder.row6.getChildAt(1);
            holder.MRD =(TextView) holder.row7.getChildAt(1);
            holder.totalamt =(TextView) holder.row9.getChildAt(1);


            vw.setTag(holder);
        } else {
            holder = (ViewHolder) vw.getTag();
        }

            holder.claim_number.setText(pay.getClaim_no());
                holder.patientname.setText(pay.getPatient_name());
                holder.blockname.setText(pay.getCase_type());
                try {
                    int consultant_fee = Integer.parseInt(pay.getConsultant_fee()!=null?pay.getConsultant_fee():"0");
                    holder.convanceypay.setText(""+consultant_fee);
                    int insentive = Integer.parseInt(pay.getConsult_insentivies()!=null?pay.getConsult_insentivies():"0");
                    holder.incentive.setText(""+insentive);
                    int ta = Integer.parseInt(pay.getPay_conveyance()!=null?pay.getPay_conveyance():"0");
                    holder.TA.setText(""+ta);
                    int mrd = Integer.parseInt(pay.getMrd_amount()!=null?pay.getMrd_amount():"0");
                    holder.MRD.setText(""+mrd);
                    int total = consultant_fee+insentive+ta+mrd;
                    holder.totalamt.setText("Rs. " + total);

                }
                catch (Exception e) {
                    Toast.makeText(mContext, "reservedtext_values", Toast.LENGTH_LONG).show();
                }


        return vw;


    }


}

