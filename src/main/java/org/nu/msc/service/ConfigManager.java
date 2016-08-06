package org.nu.msc.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.ws.rs.core.MultivaluedMap;

import org.nu.msc.dao.AtrribDAO;
import org.nu.msc.dao.CompanyDAO;
import org.nu.msc.dao.EnvDAO;
import org.nu.msc.dao.GroupDAO;
import org.nu.msc.exception.ConfigException;
import org.nu.msc.exception.ConfigException.Error;
import org.nu.msc.model.AttribValueDTO;
import org.nu.msc.model.AttributeDTO;
import org.nu.msc.model.CompanyDTO;
import org.nu.msc.model.EnvDTO;
import org.nu.msc.model.GroupDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.cj.api.x.Collection;

public class ConfigManager {
	Logger log = LoggerFactory.getLogger(ConfigManager.class);
	
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
		
		
		
	/*	CompanyDTO company = dao.createIfAbsent(id);
		 
		GroupDTO groupDTO = groupDAO.createIfAbsent(company,contextID);
		
		EnvDTO envDTO = envDAO.createIfAbsent(company,groupDTO,envID);
		attribDAO.createIfAbsent(company,groupDTO,envDTO,paramMap);*/
		
		
	}
	
	/*public void createIfAbsent(String id, String contextID, String envID,  Map<String, String> paramMap)throws ConfigException {

		CompanyDTO company =dao. load(id);
		if (company == null) {
			Integer did = dao.create(id);
			company = new CompanyDTO(did, id);
		}

		GroupDTO groupDTO = groupDAO.load(company, contextID);
		if (groupDTO == null) {
			int groupDid = groupDAO.create(company, contextID);
			groupDTO = new GroupDTO(company.getCompanyDid(), contextID, groupDid);
		}
		

		EnvDTO eventDTO= envDAO.load(company,groupDTO,envID);
		if(eventDTO==null){
			
			int profiledid = envDAO.create(company,groupDTO,envID);
			eventDTO = new EnvDTO(profiledid, groupDTO.getGroupDid(), company.getCompanyDid(), envID);
		}
		
		List<AttributeDTO> attributeList = attribDAO.loadAttribute(company);
		Map<String,AttributeDTO > result2=attributeList.stream().collect(Collectors.toMap(x->x.getId(),x->x));
		List<String> newAttribs =new ArrayList<String>();
		paramMap.forEach((k,v)->{
								if(!result2.containsKey(k)){
									newAttribs.add(k);
								}
								} );
		attribDAO.createAttributes(company, newAttribs);
		
		
	}*/

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

	public void create(String id, String contextID, List<String> paramMap) throws ConfigException{

		CompanyDTO company =dao. load(id);
		if (company == null) {
			Integer did = dao.create(id);
			company = new CompanyDTO(did, id);
		}
 
		GroupDTO groupDTO = groupDAO.load(company, contextID);		//load Group
		if (groupDTO == null) { //create if does not exists
			int groupDid = groupDAO.create(company, contextID);
			groupDTO = new GroupDTO(company.getCompanyDid(), contextID, groupDid);
		}
		

		
		try {
			attribDAO.deleteAttributes(company);
		} catch (SQLException e) {
			log.error("",e);
			throw new ConfigException(Error.INTERNAL_SERVER_ERROR);
		}//delete attributes if exists
		attribDAO.createAttributes(company,groupDTO, paramMap); //Create attributes given
		
		
	}

}


