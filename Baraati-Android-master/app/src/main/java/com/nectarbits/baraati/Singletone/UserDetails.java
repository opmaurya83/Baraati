package com.nectarbits.baraati.Singletone;

import android.content.Context;

import com.nectarbits.baraati.generalHelper.SP;

/**
 * Created by root on 19/8/16.
 */
public class UserDetails {
    private static UserDetails ourInstance = new UserDetails();
    private static Context mContext;
    public static String user_name;
    public static String user_emailid;
    public static String user_id;
    public static String user_phone="";
    public static String user_address1 ="";
    public static String user_gender="";
    public static String user_wedding_date="";
    public static String user_picture="";
    public static String user_country="";
    public static String user_state="";
    public static String user_city="";
    public static String user_address2="";
    public static String user_zip="";
    public static String user_groom="";
    public static String user_groom_phone="";
    public static String user_groom_email="";
    public static String user_bridal="";
    public static String user_bridal_phone="";
    public static String user_bridal_email="";
    public static String user_cover="";
    public static UserDetails getInstance(Context context) {
        mContext=context;
        user_name= SP.getPreferences(mContext,SP.USER_NAME);
        user_emailid= SP.getPreferences(mContext,SP.USER_EMAIL);
        user_id= SP.getPreferences(mContext,SP.USER_USERID);
        user_phone= SP.getPreferences(mContext,SP.USER_PHONE);
        user_address1 = SP.getPreferences(mContext,SP.USER_ADDRESS_1);
        user_gender= SP.getPreferences(mContext,SP.USER_GENDER);
        user_wedding_date= SP.getPreferences(mContext,SP.USER_WEDDING_DATE);
        user_picture= SP.getPreferences(mContext,SP.USER_AVTAR);
        user_country= SP.getPreferences(mContext,SP.USER_COUNTRY);
        user_state= SP.getPreferences(mContext,SP.USER_STATE);
        user_city= SP.getPreferences(mContext,SP.USER_CITY);
        user_address2= SP.getPreferences(mContext,SP.USER_ADDRESS_2);
        user_zip= SP.getPreferences(mContext,SP.USER_ZIP);
        user_groom= SP.getPreferences(mContext,SP.USER_GROOM);
        user_bridal= SP.getPreferences(mContext,SP.USER_BRIDAL);

        user_bridal_email= SP.getPreferences(mContext,SP.USER_BRIDAL_EMAIL);
        user_bridal_phone= SP.getPreferences(mContext,SP.USER_BRIDAL_PHONE);
        user_groom_email= SP.getPreferences(mContext,SP.USER_GROOM_EMAIL);
        user_groom_phone= SP.getPreferences(mContext,SP.USER_GROOM_PHONE);
        user_cover= SP.getPreferences(mContext,SP.USER_BG);
        return ourInstance;
    }

    private UserDetails() {
    }

    public  String getUser_cover() {
        return user_cover;
    }

    public  void setUser_cover(String user_cover) {
        UserDetails.user_cover = user_cover;
    }

    public  String getUser_groom_phone() {
        return user_groom_phone;
    }

    public  void setUser_groom_phone(String user_groom_phone) {
        UserDetails.user_groom_phone = user_groom_phone;
    }

    public  String getUser_groom_email() {
        return user_groom_email;
    }

    public  void setUser_groom_email(String user_groom_email) {
        UserDetails.user_groom_email = user_groom_email;
    }

    public  String getUser_bridal_phone() {
        return user_bridal_phone;
    }

    public  void setUser_bridal_phone(String user_bridal_phone) {
        UserDetails.user_bridal_phone = user_bridal_phone;
    }

    public  String getUser_bridal_email() {
        return user_bridal_email;
    }

    public  void setUser_bridal_email(String user_bridal_email) {
        UserDetails.user_bridal_email = user_bridal_email;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_emailid() {
        return user_emailid;
    }

    public void setUser_emailid(String user_emailid) {
        this.user_emailid = user_emailid;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public  String getUser_phone() {
        return user_phone;
    }

    public  void setUser_phone(String user_phone) {
        UserDetails.user_phone = user_phone;
    }



    public  String getUser_gender() {
        return user_gender;
    }

    public  void setUser_gender(String user_gender) {
        UserDetails.user_gender = user_gender;
    }

    public  String getUser_wedding_date() {
        return user_wedding_date;
    }

    public  void setUser_wedding_date(String user_wedding_date) {
        UserDetails.user_wedding_date = user_wedding_date;
    }

    public  String getUser_picture() {
        return user_picture;
    }

    public  void setUser_picture(String user_picture) {
        UserDetails.user_picture = user_picture;
    }

    public  String getUser_address1() {
        return user_address1;
    }

    public  void setUser_address1(String user_address1) {
        UserDetails.user_address1 = user_address1;
    }

    public  String getUser_country() {
        return user_country;
    }

    public  void setUser_country(String user_country) {
        UserDetails.user_country = user_country;
    }

    public  String getUser_state() {
        return user_state;
    }

    public  void setUser_state(String user_state) {
        UserDetails.user_state = user_state;
    }

    public  String getUser_city() {
        return user_city;
    }

    public  void setUser_city(String user_city) {
        UserDetails.user_city = user_city;
    }

    public  String getUser_address2() {
        return user_address2;
    }

    public  void setUser_address2(String user_address2) {
        UserDetails.user_address2 = user_address2;
    }

    public  String getUser_zip() {
        return user_zip;
    }

    public  void setUser_zip(String user_zip) {
        UserDetails.user_zip = user_zip;
    }

    public  String getUser_groom() {
        return user_groom;
    }

    public  void setUser_groom(String user_groom) {
        UserDetails.user_groom = user_groom;
    }

    public  String getUser_bridal() {
        return user_bridal;
    }

    public  void setUser_bridal(String user_bridal) {
        UserDetails.user_bridal = user_bridal;
    }
}
