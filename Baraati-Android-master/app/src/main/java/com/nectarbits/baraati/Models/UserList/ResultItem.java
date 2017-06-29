package com.nectarbits.baraati.Models.UserList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultItem{

	@SerializedName("phone")
	@Expose
	private String phone;

	@SerializedName("userDetail")
	@Expose
	private UserDetail userDetail;

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setUserDetail(UserDetail userDetail){
		this.userDetail = userDetail;
	}

	public UserDetail getUserDetail(){
		return userDetail;
	}
}