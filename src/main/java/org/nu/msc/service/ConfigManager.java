package org.nu.msc.service;

import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import org.nu.msc.dao.AtrribDAO;
import org.nu.msc.dao.CompanyDAO;
import org.nu.msc.dao.EnvDAO;
import org.nu.msc.dao.GroupDAO;
import org.nu.msc.exception.ConfigException;
import org.nu.msc.exception.ConfigException.Error;
import org.nu.msc.model.AttribValueDTO;
import org.nu.msc.model.CompanyDTO;
import org.nu.msc.model.EnvDTO;
import org.nu.msc.model.GroupDTO;

public class ConfigManager {
	
	private CompanyDAO dao = new CompanyDAO();
	private GroupDAO groupDAO = new GroupDAO();
	private EnvDAO envDAO = new EnvDAO();
	private AtrribDAO attribDAO = new AtrribDAO();
	
	public CompanyDTO loadConfig(String id, String contextID, String envID)throws ConfigException {
		CompanyDTO company =	dao.load(id);
		if(company==null){
			throw new ConfigException(Error.COMPANY_NOT_DEFINED);
		}
		GroupDTO groupDTO = groupDAO.load(company,contextID);
		if(groupDTO==null){
			throw new ConfigException(Error.CONTEXT_NOT_DEFINED);
		}
		
		company.setContext(groupDTO);
		
		EnvDTO envDTO = envDAO.load(company,groupDTO,envID);
		if(envDTO==null){
			throw new ConfigException(Error.ENV_NOT_DEFINED);
		}
		
		groupDTO.setEnv(envDTO);
		
		List<AttribValueDTO> attribValueDTOs= attribDAO.load(company,groupDTO,envDTO);
		
		if(attribValueDTOs==null||attribValueDTOs.isEmpty()){
			throw new ConfigException(Error.ATTRIB_NOT_DEFINED);
		}
		
		envDTO.setAttribValues(attribValueDTOs);
		
		return company;
		
	}

	public void create(String id, String contextID, String envID,  Map<String, String> paramMap)throws ConfigException {
		
		
		
		CompanyDTO company = dao.createIfAbsent(id);
		 
		GroupDTO groupDTO = groupDAO.createIfAbsent(company,contextID);
		
		EnvDTO envDTO = envDAO.createIfAbsent(company,groupDTO,envID);
		attribDAO.createIfAbsent(company,groupDTO,envDTO);
		
		
	}

	public void update(String id, String contextID, String envID, MultivaluedMap<String, String> paramMap)throws ConfigException {
		CompanyDTO company =	dao.load(id);
		if(company==null){
			throw new ConfigException(Error.COMPANY_NOT_DEFINED);
		}
		GroupDTO groupDTO = groupDAO.load(company,contextID);
		if(groupDTO==null){
			throw new ConfigException(Error.CONTEXT_NOT_DEFINED);
		}
		
		EnvDTO envDTO = envDAO.load(company,groupDTO,envID);
		if(envDTO==null){
			throw new ConfigException(Error.ENV_NOT_DEFINED);
		}
		attribDAO.update(company,groupDTO,envDTO,paramMap);
		
		
	}

	public void delete(String id, String contextID) throws ConfigException{
		CompanyDTO company =	dao.load(id);
		if(company==null){
			throw new ConfigException(Error.COMPANY_NOT_DEFINED);
		}
		GroupDTO groupDTO = groupDAO.load(company,contextID);
		if(groupDTO==null){
			throw new ConfigException(Error.CONTEXT_NOT_DEFINED);
		}
		groupDAO.delete(company,groupDTO);
		
		
	}

	public void delete(String id, String contextID, String envID)throws ConfigException {
		CompanyDTO company =	dao.load(id);
		if(company==null){
			throw new ConfigException(Error.COMPANY_NOT_DEFINED);
		}
		GroupDTO groupDTO = groupDAO.load(company,contextID);
		if(groupDTO==null){
			throw new ConfigException(Error.CONTEXT_NOT_DEFINED);
		}
		
		EnvDTO envDTO = envDAO.load(company,groupDTO,envID);
		if(envDTO==null){
			throw new ConfigException(Error.ENV_NOT_DEFINED);
		}
		
		envDAO.delete(company,groupDTO,envDTO);
	}

}


