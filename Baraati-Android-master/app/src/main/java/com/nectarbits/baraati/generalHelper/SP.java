package com.nectarbits.baraati.generalHelper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by nectarbits on 15/02/16.
 */
public class SP {

    public static String FLAG_NO = "FLAG_NO";
    public static String FLAG_YES = "FLAG_YES";
    public static String LOGIN_STATUS = "LOGIN_STATUS";
    public static String INTRO_STATUS = "INTRO_STATUS";
    public static String FLAG_INTRO_VIEWED = "FLAG_INTRO_VIEWED";
    public static String FLAG_INTRO_NOT_VIEWED = "FLAG_INTRO_NOT_VIEWED";
    public static String TRUE = "true";
    public static String FALSE = "false";


    public static String USER_NAME="USER_NAME";
    public static String USER_EMAIL="USER_EMAIL";
    public static String USER_PHONE="USER_PHONE";
    public static String USER_USERID="USER_USERID";
    public static String USER_GENDER="USER_GENDER";
    public static String USER_WEDDING_DATE="USER_WEDDING_DATE";
    public static String USER_ADDRESS_1 ="USER_ADDRESS_1";
    public static String USER_AVTAR="USER_AVTAR";
    public static String USER_ADDRESS_2="USER_ADDRESS_2";
    public static String USER_COUNTRY="USER_COUNTRY";
    public static String USER_STATE="USER_STATE";
    public static String USER_CITY="USER_CITY";
    public static String USER_ZIP="USER_ZIP";

    public static String USER_GROOM="USER_GROOM";
    public static String USER_GROOM_PHONE="USER_GROOM_PHONE";
    public static String USER_GROOM_EMAIL="USER_GROOM_EMAIL";
    public static String USER_BG="USER_BG";
    public static String USER_BRIDAL="USER_BRIDAL";
    public static String USER_BRIDAL_PHONE="USER_BRIDAL_PHONE";
    public static String USER_BRIDAL_EMAIL="USER_BRIDAL_EMAIL";
    public static String FIRST_TIME_INSPIRE_SCREEN="FIRST_TIME_INSPIRE_SCREEN";



    /**
     *
     * @param mContext
     * @param key
     * @param value
     */
    public static void savePreferences(Context mContext, String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();

    }

    /**
     *
     * @param context
     * @param keyValue
     * @return
     */
    public static String getPreferences(Context context, String keyValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String name = sharedPreferences.getString(keyValue, "");
        return name;
    }


    public static void clear(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();

    }
}
