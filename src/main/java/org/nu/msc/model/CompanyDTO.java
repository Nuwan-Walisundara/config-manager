package org.nu.msc.model;

import java.io.Serializable;

import org.skife.jdbi.v2.tweak.Argument;

public class CompanyDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3629189250146111031L;
	private GroupDTO context;
	private int did;
	private String name;
	private int compDid;
	
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


	public int getCompanyDid() {
		// TODO Auto-generated method stub
		return compDid;
	}

	
}
