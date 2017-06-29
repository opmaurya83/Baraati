package com.nectarbits.baraati.Singletone;

/**
 * Created by root on 19/8/16.
 */
public class RegisterSignletone {

    public String name;
    public String emailid;
    public String password;
    public String phonenumber;
    public String code;
    private static RegisterSignletone ourInstance = new RegisterSignletone();

    public static RegisterSignletone getInstance() {
        return ourInstance;
    }

    private RegisterSignletone() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
