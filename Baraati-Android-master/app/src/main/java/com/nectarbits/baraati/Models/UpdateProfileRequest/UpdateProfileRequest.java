package com.nectarbits.baraati.Models.UpdateProfileRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 19/9/16.
 */
public class UpdateProfileRequest {


    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("firstName")
    @Expose
    private String firstName;


    @SerializedName("lastName")
    @Expose
    private String lastName="";


    @SerializedName("city")
    @Expose
    private String city="";


    @SerializedName("state")
    @Expose
    private String state="";


    @SerializedName("country")
    @Expose
    private String country="";


    @SerializedName("addressLine1")
    @Expose
    private String addressLine1;


    @SerializedName("addressline2")
    @Expose
    private String addressline2 ="";


    @SerializedName("user_profile_image")
    @Expose
    private String user_profile_image="";

    @SerializedName("gendar")
    @Expose
    private String gendar="";

    @SerializedName("zipcode")
    @Expose
    private String zipcode="";

    @SerializedName("weddingdate")
    @Expose
    private String weddingdate="";

    @SerializedName("groom")
    @Expose
    private String groom="";

    @SerializedName("bridal")
    @Expose
    private String bridal="";


    @SerializedName("bridephone")
    @Expose
    private String bridephone="";
    @SerializedName("brideemail")
    @Expose
    private String brideemail="";
    @SerializedName("groomphone")
    @Expose
    private String groomphone="";
    @SerializedName("groomemail")
    @Expose
    private String groomemail="";

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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressline2() {
        return addressline2;
    }

    public void setAddressline2(String addressline2) {
        this.addressline2 = addressline2;
    }

    public String getUser_profile_image() {
        return user_profile_image;
    }

    public void setUser_profile_image(String user_profile_image) {
        this.user_profile_image = user_profile_image;
    }

    public String getGendar() {
        return gendar;
    }

    public void setGendar(String gendar) {
        this.gendar = gendar;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getWeddingdate() {
        return weddingdate;
    }

    public void setWeddingdate(String weddingdate) {
        this.weddingdate = weddingdate;
    }

    public String getGroom() {
        return groom;
    }

    public void setGroom(String groom) {
        this.groom = groom;
    }

    public String getBridal() {
        return bridal;
    }

    public void setBridal(String bridal) {
        this.bridal = bridal;
    }
}
