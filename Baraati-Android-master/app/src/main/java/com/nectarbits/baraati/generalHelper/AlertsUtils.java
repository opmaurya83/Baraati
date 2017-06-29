package com.nectarbits.baraati.generalHelper;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.nectarbits.baraati.Interface.OnAlertDialogClicked;
import com.nectarbits.baraati.R;


/**
 * Created by root on 17/6/16.
 */
public class AlertsUtils {
    private static AlertsUtils ourInstance = new AlertsUtils();

    public static AlertsUtils getInstance() {
        return ourInstance;
    }

    private AlertsUtils() {
    }


    /*public void showYesNoCancelAlert(Context context, int messageResource, final OnAlertDialogClicked onAlertDialogClicked){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(messageResource);
        builder.setPositiveButton(context.getString(R.string.str_YES), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onAlertDialogClicked.onPositiveClicked();
            }
        });
        builder.setNegativeButton(context.getString(R.string.str_NO), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onAlertDialogClicked.onNagativeClicked();
            }
        });
        builder.setNeutralButton(context.getString(R.string.str_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onAlertDialogClicked.onNeutralClicked();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    public void showYesNoCancelAlert(Context context, int messageResource,int yes,int no,int cancel, final OnAlertDialogClicked onAlertDialogClicked){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(messageResource);
        builder.setPositiveButton(yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onAlertDialogClicked.onPositiveClicked();
            }
        });
        builder.setNegativeButton(no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onAlertDialogClicked.onNagativeClicked();
            }
        });
        builder.setNeutralButton(cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onAlertDialogClicked.onNeutralClicked();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }



*/

    public void showYesNoAlert(Context context,String message,int positiveResource,int nagativeResouce,final OnAlertDialogClicked onAlertDialogClicked){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton(positiveResource, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onAlertDialogClicked.onPositiveClicked();
            }
        });
        builder.setNegativeButton(nagativeResouce, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onAlertDialogClicked.onNagativeClicked();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }



    public void showOKAlert(Context context,String messageResource){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(messageResource);
        builder.setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
