package com.nectarbits.baraati.Singletone;

import com.nectarbits.baraati.Models.Login.LoginModel;

/**
 * Created by root on 10/6/16.
 */
public class LoginResponseSingletone {
    LoginModel loginResponse=new LoginModel();
    String logintype="";
    String phonenumber;
    String otp;

    private static LoginResponseSingletone ourInstance = new LoginResponseSingletone();

    public static LoginResponseSingletone getInstance() {
        return ourInstance;
    }

    private LoginResponseSingletone() {
    }

    public void  reset(){
        loginResponse=new LoginModel();
        logintype="";
        phonenumber="";
        otp="";
    }
    public LoginModel getLoginResponse() {
        return loginResponse;
    }

    public void setLoginResponse(LoginModel loginResponse) {
        this.loginResponse = loginResponse;
    }

    public String getLogintype() {
        return logintype;
    }

    public void setLogintype(String logintype) {
        this.logintype = logintype;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
