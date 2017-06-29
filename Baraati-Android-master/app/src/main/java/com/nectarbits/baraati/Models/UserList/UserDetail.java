package com.nectarbits.baraati.Models.UserList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDetail{

	@SerializedName("gendar")
	@Expose
	private String gendar;

	@SerializedName("quickBlockuserId")
	@Expose
	private String quickBlockuserId;

	@SerializedName("lastName")
	@Expose
	private String lastName;

	@SerializedName("country")
	@Expose
	private String country;

	@SerializedName("groom")
	@Expose
	private String groom;

	@SerializedName("bridal")
	@Expose
	private String bridal;

	@SerializedName("city")
	@Expose
	private String city;

	@SerializedName("zipcode")
	@Expose
	private String zipcode;

	@SerializedName("firstName")
	@Expose
	private String firstName;

	@SerializedName("loginusing")
	@Expose
	private String loginusing;

	@SerializedName("profilePicture")
	@Expose
	private String profilePicture;

	@SerializedName("user_id")
	@Expose
	private String userId;

	@SerializedName("weddingdate")
	@Expose
	private String weddingdate;

	@SerializedName("contact")
	@Expose
	private String contact;

	@SerializedName("addressLine1")
	@Expose
	private String addressLine1;

	@SerializedName("addressline2")
	@Expose
	private String addressline2;

	@SerializedName("state")
	@Expose
	private String state;

	@SerializedName("email")
	@Expose
	private String email;

	@SerializedName("registerDate")
	@Expose
	private String registerDate;

	public void setGendar(String gendar){
		this.gendar = gendar;
	}

	public String getGendar(){
		return gendar;
	}

	public void setQuickBlockuserId(String quickBlockuserId){
		this.quickBlockuserId = quickBlockuserId;
	}

	public String getQuickBlockuserId(){
		return quickBlockuserId;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setGroom(String groom){
		this.groom = groom;
	}

	public String getGroom(){
		return groom;
	}

	public void setBridal(String bridal){
		this.bridal = bridal;
	}

	public String getBridal(){
		return bridal;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setZipcode(String zipcode){
		this.zipcode = zipcode;
	}

	public String getZipcode(){
		return zipcode;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setLoginusing(String loginusing){
		this.loginusing = loginusing;
	}

	public String getLoginusing(){
		return loginusing;
	}

	public void setProfilePicture(String profilePicture){
		this.profilePicture = profilePicture;
	}

	public String getProfilePicture(){
		return profilePicture;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setWeddingdate(String weddingdate){
		this.weddingdate = weddingdate;
	}

	public String getWeddingdate(){
		return weddingdate;
	}

	public void setContact(String contact){
		this.contact = contact;
	}

	public String getContact(){
		return contact;
	}

	public void setAddressLine1(String addressLine1){
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine1(){
		return addressLine1;
	}

	public void setAddressline2(String addressline2){
		this.addressline2 = addressline2;
	}

	public String getAddressline2(){
		return addressline2;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return state;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setRegisterDate(String registerDate){
		this.registerDate = registerDate;
	}

	public String getRegisterDate(){
		return registerDate;
	}
}