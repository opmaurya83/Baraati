package com.nectarbits.baraati.Models.UpdateProfileRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 19/9/16.
 */
public class UpdateProfileRequestOverlay {


    @SerializedName("bridename")
    @Expose
    private String bridename;
    @SerializedName("bridephone")
    @Expose
    private String bridephone;

    @SerializedName("brideemail")
    @Expose
    private String brideemail;


    @SerializedName("groomname")
    @Expose
    private String groomname="";


    @SerializedName("groomphone")
    @Expose
    private String groomphone="";


    @SerializedName("groomemail")
    @Expose
    private String groomemail="";


    @SerializedName("weddingdate")
    @Expose
    private String weddingdate="";


    @SerializedName("userid")
    @Expose
    private String userid;

    public String getBridename() {
        return bridename;
    }

    public void setBridename(String bridename) {
        this.bridename = bridename;
    }

    public String getBridephone() {
        return bridephone;
    }

    public void setBridephone(String bridephone) {
        this.bridephone = bridephone;
    }

    public String getBrideemail() {
        return brideemail;
    }

    public void setBrideemail(String brideemail) {
        this.brideemail = brideemail;
    }

    public String getGroomname() {
        return groomname;
    }

    public void setGroomname(String groomname) {
        this.groomname = groomname;
    }

    public String getGroomphone() {
        return groomphone;
    }

    public void setGroomphone(String groomphone) {
        this.groomphone = groomphone;
    }

    public String getGroomemail() {
        return groomemail;
    }

    public void setGroomemail(String groomemail) {
        this.groomemail = groomemail;
    }

    public String getWeddingdate() {
        return weddingdate;
    }

    public void setWeddingdate(String weddingdate) {
        this.weddingdate = weddingdate;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
