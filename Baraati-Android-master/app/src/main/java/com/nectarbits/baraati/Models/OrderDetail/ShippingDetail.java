
package com.nectarbits.baraati.Models.OrderDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShippingDetail {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("country")
    @Expose
    private String country;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
