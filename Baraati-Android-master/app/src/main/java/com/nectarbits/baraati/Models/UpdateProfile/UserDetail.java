
package com.nectarbits.baraati.Models.UpdateProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDetail {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("registerDate")
    @Expose
    private String registerDate;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("addressLine1")
    @Expose
    private String addressLine1;
    @SerializedName("addressline2")
    @Expose
    private String addressline2;
    @SerializedName("zipcode")
    @Expose
    private String zipcode;
    @SerializedName("weddingdate")
    @Expose
    private String weddingdate;
    @SerializedName("gendar")
    @Expose
    private String gendar;
    @SerializedName("profilePicture")
    @Expose
    private String profilePicture;


    @SerializedName("groom")
    @Expose
    private String groom;
    @SerializedName("bridal")
    @Expose
    private String bridal;


    @SerializedName("bridephone")
    @Expose
    private String bridephone;

    @SerializedName("brideemail")
    @Expose
    private String brideemail;


    @SerializedName("groomphone")
    @Expose
    private String groomphone;


    @SerializedName("groomemail")
    @Expose
    private String groomemail;


    @SerializedName("cover")
    @Expose
    private String cover;

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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
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
     *     The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * 
     * @param firstName
     *     The firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * 
     * @return
     *     The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * 
     * @param lastName
     *     The lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
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
     *     The registerDate
     */
    public String getRegisterDate() {
        return registerDate;
    }

    /**
     * 
     * @param registerDate
     *     The registerDate
     */
    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    /**
     * 
     * @return
     *     The contact
     */
    public String getContact() {
        return contact;
    }

    /**
     * 
     * @param contact
     *     The contact
     */
    public void setContact(String contact) {
        this.contact = contact;
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
     *     The addressLine1
     */
    public String getAddressLine1() {
        return addressLine1;
    }

    /**
     * 
     * @param addressLine1
     *     The addressLine1
     */
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
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
     *     The weddingdate
     */
    public String getWeddingdate() {
        return weddingdate;
    }

    /**
     * 
     * @param weddingdate
     *     The weddingdate
     */
    public void setWeddingdate(String weddingdate) {
        this.weddingdate = weddingdate;
    }

    /**
     * 
     * @return
     *     The gendar
     */
    public String getGendar() {
        return gendar;
    }

    /**
     * 
     * @param gendar
     *     The gendar
     */
    public void setGendar(String gendar) {
        this.gendar = gendar;
    }

    /**
     * 
     * @return
     *     The profilePicture
     */
    public String getProfilePicture() {
        return profilePicture;
    }

    /**
     * 
     * @param profilePicture
     *     The profilePicture
     */
    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
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
