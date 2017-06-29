
package com.nectarbits.baraati.Models.InquiryModel.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyInqiry {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("budget")
    @Expose
    private String budget;
    @SerializedName("vendorId")
    @Expose
    private String vendorId;
    @SerializedName("vendorName")
    @Expose
    private String vendorName;
    @SerializedName("longDescription")
    @Expose
    private String longDescription;
    @SerializedName("twolinedescription")
    @Expose
    private String twolinedescription;
    @SerializedName("eventname")
    @Expose
    private String eventname;

    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("inquiryby")
    @Expose
    private String inquiryby;

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 
     * @param email
     *     The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 
     * @return
     *     The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * 
     * @param message
     *     The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 
     * @return
     *     The budget
     */
    public String getBudget() {
        return budget;
    }

    /**
     * 
     * @param budget
     *     The budget
     */
    public void setBudget(String budget) {
        this.budget = budget;
    }

    /**
     * 
     * @return
     *     The vendorId
     */
    public String getVendorId() {
        return vendorId;
    }

    /**
     * 
     * @param vendorId
     *     The vendorId
     */
    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    /**
     * 
     * @return
     *     The vendorName
     */
    public String getVendorName() {
        return vendorName;
    }

    /**
     * 
     * @param vendorName
     *     The vendorName
     */
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    /**
     * 
     * @return
     *     The longDescription
     */
    public String getLongDescription() {
        return longDescription;
    }

    /**
     * 
     * @param longDescription
     *     The longDescription
     */
    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    /**
     * 
     * @return
     *     The twolinedescription
     */
    public String getTwolinedescription() {
        return twolinedescription;
    }

    /**
     * 
     * @param twolinedescription
     *     The twolinedescription
     */
    public void setTwolinedescription(String twolinedescription) {
        this.twolinedescription = twolinedescription;
    }

    /**
     * 
     * @return
     *     The eventname
     */
    public String getEventname() {
        return eventname;
    }

    /**
     * 
     * @param eventname
     *     The eventname
     */
    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getInquiryby() {
        return inquiryby;
    }

    public void setInquiryby(String inquiryby) {
        this.inquiryby = inquiryby;
    }
}
