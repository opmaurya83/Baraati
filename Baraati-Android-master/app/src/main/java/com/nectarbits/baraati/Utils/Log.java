package com.nectarbits.baraati.Utils;

/**
 * Created by nectarbits on 1/6/2017.
 */

public class Log {
    public static Boolean mLoggingEnabled=true;
    public static void v(String tag, String msg) {
        if(mLoggingEnabled) {
            android.util.Log.v(tag, msg);
        }
    }
    public static void w(String tag, String msg) {
        if(mLoggingEnabled) {
            android.util.Log.w(tag, msg);
        }
    }
    public static void e(String tag, String msg) {
        if(mLoggingEnabled) {
            android.util.Log.e(tag, msg);
        }
    }
    public static void d(String tag, String msg) {
        if(mLoggingEnabled) {
            android.util.Log.d(tag, msg);
        }
    }
}
