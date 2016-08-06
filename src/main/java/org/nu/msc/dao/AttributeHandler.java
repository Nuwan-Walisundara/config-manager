package org.nu.msc.dao;

import java.util.ArrayList;
import java.util.List;

import org.nu.msc.model.CompanyDTO;
import org.nu.msc.model.GroupDTO;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;
import org.skife.jdbi.v2.sqlobject.mixins.GetHandle;

abstract class AttributeHandler implements GetHandle {

	@CreateSqlObject
	abstract AttributePersistable attribPersistable();
	
	@CreateSqlObject
	abstract AttribValuePersistable valuePersistable();
	
	@CreateSqlObject
	abstract DistributionPersistable dstributionPersistable();
	
	
	public void deleteAttributes(final CompanyDTO companyDTO){
		valuePersistable().delete(Boolean.FALSE, companyDTO.getCompanyDid()); //disable existing values fo the company
		 dstributionPersistable().delete(Boolean.FALSE, companyDTO.getCompanyDid()); //disable existing distributions for the company
		 attribPersistable().delete(Boolean.FALSE, companyDTO.getCompanyDid());//disable existing attributes for the company
	}
	
	public void createAttributes(final CompanyDTO companyDTO,final GroupDTO groupDTO,final List<String>newAttributes){
		List<Integer> attributeDids = new ArrayList<Integer>();
		newAttributes.forEach(x->{
							final Integer did =	attribPersistable().create(companyDTO.getCompanyDid(), x);
							attributeDids.add(did);
							dstributionPersistable().create(companyDTO.getCompanyDid(), groupDTO.getGroupDid(),did);
							
							});
		
		
		
	}
}
