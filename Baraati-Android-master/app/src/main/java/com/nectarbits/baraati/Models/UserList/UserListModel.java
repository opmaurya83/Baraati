package com.nectarbits.baraati.Models.UserList;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserListModel{

	@SerializedName("status")
	@Expose
	private String status;

	@SerializedName("Result")
	@Expose
	private List<ResultItem> result;

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setResult(List<ResultItem> result){
		this.result = result;
	}

	public List<ResultItem> getResult(){
		return result;
	}
}