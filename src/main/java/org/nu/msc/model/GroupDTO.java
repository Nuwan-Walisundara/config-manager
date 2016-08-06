package org.nu.msc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GroupDTO {

	private int groupDid;
	private int companydid;

    @Expose
    @SerializedName("ID") 
	private String id;
    @Expose
    @SerializedName("Enviroment") 
	private EnvDTO envDTO;
	
	
	public GroupDTO(int companydid, String id, int groupDid) {
		this.companydid = companydid;
		this.id =id;
		this.groupDid = groupDid;
	}

	public void setEnv(EnvDTO envDTO) {
			this.envDTO = envDTO;
	}

	public int getGroupDid() {
		// TODO Auto-generated method stub
		return groupDid;
	}

}

