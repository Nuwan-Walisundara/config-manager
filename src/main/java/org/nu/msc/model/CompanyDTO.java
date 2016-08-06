package org.nu.msc.model;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompanyDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3629189250146111031L;
	@Expose
	@SerializedName("ID") 
	private String name;
	
	@Expose
	@SerializedName("Context") 
	private GroupDTO context;
	private int compDid;
	
	public CompanyDTO(int did,   String name) {
		this.compDid =did;
		this.name = name;
	}

	public GroupDTO getContext() {
		return context;
	}

	public void setContext(GroupDTO context) {
		this.context = context;
	}


	public int getCompanyDid() {
		// TODO Auto-generated method stub
		return compDid;
	}

	
}
