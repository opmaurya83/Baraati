package com.nectarbits.baraati.Models.UpdateQuickBloxid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class UpdateQuickBloxModel{

	@SerializedName("quickBlockuserId")
	@Expose
	private String quickBlockuserId;

	@SerializedName("msg")
	@Expose
	private String msg;

	@SerializedName("status")
	@Expose
	private String status;

	public void setQuickBlockuserId(String quickBlockuserId){
		this.quickBlockuserId = quickBlockuserId;
	}

	public String getQuickBlockuserId(){
		return quickBlockuserId;
	}

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}