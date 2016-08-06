package org.nu.msc.model;

import java.util.List;

import org.skife.jdbi.v2.tweak.Argument;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EnvDTO {
	private int profiledid;
	private int groupdid;
	private int companydid;
	@Expose
	private String id;
	@Expose
	@SerializedName("attributes") 
	private List<AttribValueDTO> attributes;
	public EnvDTO(int profiledid, int groupdid, int companydid, String id) {
		this.profiledid= profiledid;
		this.groupdid = groupdid;
		this.companydid = companydid;
		this.id = id;
	}

	public void setAttribValues(List<AttribValueDTO> attribValueDTOs) {
		this.attributes =attribValueDTOs;
		
	}

	public int getProfileDid() {
		// TODO Auto-generated method stub
		return profiledid;
	}

}
