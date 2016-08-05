package org.nu.msc.model;

public class AttributeDTO {
	private String id;
	private int attributedid;

	public AttributeDTO(String id, int did) {
		this.id = id;
		this.attributedid = did;

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getAttributedid() {
		return attributedid;
	}

	public void setAttributedid(int attributedid) {
		this.attributedid = attributedid;
	}
	

}
