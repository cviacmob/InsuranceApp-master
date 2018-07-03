package com.insurance.insuranceapp.Utilities;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.insurance.insuranceapp.R;

/**
 * Created by Balaji on 4/25/2018.
 */

public class AlertDialogNoData {

    public static void alertdialog(final Activity activity){
        LayoutInflater inflater = activity.getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_custom_dialog, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle("Info");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finish();
            }
        });


        AlertDialog dialog = alert.create();
        dialog.show();
    }
    public static void alerttest(final Activity activity){
        LayoutInflater inflater = activity.getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_custom_dialog, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle("Info");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finish();
            }
        });


        AlertDialog dialog = alert.create();
        dialog.show();
    }

}
