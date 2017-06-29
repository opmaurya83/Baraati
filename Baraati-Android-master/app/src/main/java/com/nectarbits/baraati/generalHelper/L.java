package com.nectarbits.baraati.generalHelper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by nectarbits on 16/02/16.
 */
public class L {
    private static boolean isDebug=true;
    private static final String MY_TAG = "@@@@@:::::@@@:::::@@@@@";

    public static void showLog(String getLog) {
        if (isDebug) {
            Log.d(MY_TAG, getLog);
        }
    }

    public static void showError(String getError) {
        if (isDebug) {
            Log.e(MY_TAG, getError);
        }
    }

    public static void showException(Exception e){
        if (isDebug){
            e.printStackTrace();
        }
    }

    public static void showToast(Context context, String getToast) {
        Toast.makeText(context, getToast, Toast.LENGTH_SHORT).show();
    }
}
