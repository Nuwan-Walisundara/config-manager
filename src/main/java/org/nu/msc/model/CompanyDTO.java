package org.nu.msc.model;

import java.io.Serializable;

public class CompanyDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3629189250146111031L;
	private GroupDTO context;
	private int did;
	private String name;
	
	public CompanyDTO(int did,   String name) {
		this.did =did;
		this.name = name;
	}

	public GroupDTO getContext() {
		return context;
	}

	public void setContext(GroupDTO context) {
		this.context = context;
	}

	
}
