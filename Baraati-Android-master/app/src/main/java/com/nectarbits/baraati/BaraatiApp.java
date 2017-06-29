package com.nectarbits.baraati;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.nectarbits.baraati.Chat.App;
import com.nectarbits.baraati.Utils.TypefaceUtil;
import com.nectarbits.baraati.generalHelper.DBHelper;
import com.orhanobut.hawk.Hawk;

/**
 * Created by root on 17/8/16.
 */
public class BaraatiApp extends App {
    private static MultiDexApplication instance;
    static String UDID="";
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        DBHelper dbHelper = new DBHelper(this);
        try {
            dbHelper.copyDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }

  //      TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
//        UDID=telephonyManager.getDeviceId();
        FirebaseApp.initializeApp(this);
        UDID= FirebaseInstanceId.getInstance().getToken();
        Log.e("BaraatiApp", "UDID: "+UDID);
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/MyriadPro-Regular.otf"); // font from assets: "assets/fonts/Roboto-Regular.ttf
        Hawk.init(this)
                .build();
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }

    public static String getUDID() {
        Log.e("BaraatiApp", "UDID: "+FirebaseInstanceId.getInstance().getToken());
        return FirebaseInstanceId.getInstance().getToken();

    }

    public static void setUDID(String UDID) {
        BaraatiApp.UDID = UDID;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
