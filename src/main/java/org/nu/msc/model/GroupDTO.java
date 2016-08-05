package org.nu.msc.model;

public class GroupDTO {

	private int groupDid;
	private int companydid;
	private String id;
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

