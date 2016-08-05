package org.nu.msc.model;

import java.util.List;

import org.skife.jdbi.v2.tweak.Argument;

public class EnvDTO {
	private int profiledid;
	private int groupdid;
	private int companydid;
	private String id;
	
	public EnvDTO(int profiledid, int groupdid, int companydid, String id) {
		this.profiledid= profiledid;
		this.groupdid = groupdid;
		this.companydid = companydid;
		this.id = id;
	}

	public void setAttribValues(List<AttribValueDTO> attribValueDTOs) {
		// TODO Auto-generated method stub
		
	}

	public int getProfileDid() {
		// TODO Auto-generated method stub
		return profiledid;
	}

}
