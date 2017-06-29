
package com.nectarbits.baraati.Models.Vendor;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VendorDetailInfo {

    @SerializedName("vendorId")
    @Expose
    private String vendorId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone1")
    @Expose
    private String phone1;
    @SerializedName("Phone2")
    @Expose
    private String phone2;
    @SerializedName("Mobile")
    @Expose
    private String mobile;
    @SerializedName("Address1")
    @Expose
    private String address1;
    @SerializedName("Address2")
    @Expose
    private String address2;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("State")
    @Expose
    private String state;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("Business_Details")
    @Expose
    private String businessDetails;
    @SerializedName("Company_Name")
    @Expose
    private String companyName;
    @SerializedName("Company_Address")
    @Expose
    private String companyAddress;
    @SerializedName("Contact_Person")
    @Expose
    private String contactPerson;
    @SerializedName("About_Company")
    @Expose
    private String aboutCompany;

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
     *     The phone1
     */
    public String getPhone1() {
        return phone1;
    }

    /**
     * 
     * @param phone1
     *     The phone1
     */
    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    /**
     * 
     * @return
     *     The phone2
     */
    public String getPhone2() {
        return phone2;
    }

    /**
     * 
     * @param phone2
     *     The Phone2
     */
    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    /**
     * 
     * @return
     *     The mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 
     * @param mobile
     *     The Mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 
     * @return
     *     The address1
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * 
     * @param address1
     *     The Address1
     */
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    /**
     * 
     * @return
     *     The address2
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * 
     * @param address2
     *     The Address2
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
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
     *     The State
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
     *     The City
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 
     * @return
     *     The businessDetails
     */
    public String getBusinessDetails() {
        return businessDetails;
    }

    /**
     * 
     * @param businessDetails
     *     The Business_Details
     */
    public void setBusinessDetails(String businessDetails) {
        this.businessDetails = businessDetails;
    }

    /**
     * 
     * @return
     *     The companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * @param companyName
     *     The Company_Name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 
     * @return
     *     The companyAddress
     */
    public String getCompanyAddress() {
        return companyAddress;
    }

    /**
     * 
     * @param companyAddress
     *     The Company_Address
     */
    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    /**
     * 
     * @return
     *     The contactPerson
     */
    public String getContactPerson() {
        return contactPerson;
    }

    /**
     * 
     * @param contactPerson
     *     The Contact_Person
     */
    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    /**
     * 
     * @return
     *     The aboutCompany
     */
    public String getAboutCompany() {
        return aboutCompany;
    }

    /**
     * 
     * @param aboutCompany
     *     The About_Company
     */
    public void setAboutCompany(String aboutCompany) {
        this.aboutCompany = aboutCompany;
    }

}
