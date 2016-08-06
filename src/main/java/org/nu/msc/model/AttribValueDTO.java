package org.nu.msc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttribValueDTO {
	@Expose
	@SerializedName("ID") 
	private  String attribID ;
	@Expose
	@SerializedName("Value") 
	private String attribVal ;
	public AttribValueDTO(String attribID , String attribVal ) {
		this.attribID=attribID;
		this.attribVal =attribVal;
	}
	
	

}
