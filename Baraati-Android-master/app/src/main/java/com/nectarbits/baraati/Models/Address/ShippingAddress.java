
package com.nectarbits.baraati.Models.Address;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShippingAddress {

    @SerializedName("user_shippingDetail_id")
    @Expose
    private String userShippingDetailId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("addressline1")
    @Expose
    private String addressline1;
    @SerializedName("addressline2")
    @Expose
    private String addressline2;
    @SerializedName("zipcode")
    @Expose
    private String zipcode;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("name")
    @Expose
    private String name;

    Boolean isChangeable=false;
    Boolean isSelected=false;

    /**
     * 
     * @return
     *     The userShippingDetailId
     */
    public String getUserShippingDetailId() {
        return userShippingDetailId;
    }

    /**
     * 
     * @param userShippingDetailId
     *     The user_shippingDetail_id
     */
    public void setUserShippingDetailId(String userShippingDetailId) {
        this.userShippingDetailId = userShippingDetailId;
    }

    /**
     * 
     * @return
     *     The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 
     * @param userId
     *     The user_id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 
     * @return
     *     The country
     */
    public String getCountry() {
        return country;
    }

    /**
     * 
     * @param country
     *     The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * 
     * @return
     *     The state
     */
    public String getState() {
        return state;
    }

    /**
     * 
     * @param state
     *     The state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 
     * @return
     *     The city
     */
    public String getCity() {
        return city;
    }

    /**
     * 
     * @param city
     *     The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 
     * @return
     *     The addressline1
     */
    public String getAddressline1() {
        return addressline1;
    }

    /**
     * 
     * @param addressline1
     *     The addressline1
     */
    public void setAddressline1(String addressline1) {
        this.addressline1 = addressline1;
    }

    /**
     * 
     * @return
     *     The addressline2
     */
    public String getAddressline2() {
        return addressline2;
    }

    /**
     * 
     * @param addressline2
     *     The addressline2
     */
    public void setAddressline2(String addressline2) {
        this.addressline2 = addressline2;
    }

    /**
     * 
     * @return
     *     The zipcode
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     * 
     * @param zipcode
     *     The zipcode
     */
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    /**
     * 
     * @return
     *     The phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 
     * @param phone
     *     The phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public Boolean getChangeable() {
        return isChangeable;
    }

    public void setChangeable(Boolean changeable) {
        isChangeable = changeable;
    }
}
