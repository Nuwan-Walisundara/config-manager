package org.nu.msc.exception;

public class ConfigException extends Exception {

	private Error error;
	
	public ConfigException(Error errorDef) {
		this.error = errorDef;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8689398294794961476L;

	public Error getError(){
		return this.error;
	}
	
	public enum Error{
		INTERNAL_SERVER_ERROR("CF00001","Unexpected server error"),
		COMPANY_NOT_DEFINED("CF00002","Company not found in the system"),
		CONTEXT_NOT_DEFINED("CF00003","Context not found in the system"),
		ENV_NOT_DEFINED("CF00004","Environment not found in the system"), 
		ATTRIB_NOT_DEFINED("CF00005","Attributes not found in the system");
		private String key;
		private String val;
		
		Error(final String key,final String desc){
			this.key = key;
			this.val = desc;
		}
		
		public String toString(){
			return "Code : "+this.key +" ,Description :"+this.val;
		}
		
	}
	
	
}
