package org.nu.msc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import org.junit.Assert;
import org.nu.msc.model.AttribValueDTO;
import org.nu.msc.model.AttributeDTO;
import org.nu.msc.model.CompanyDTO;
import org.nu.msc.model.EnvDTO;
import org.nu.msc.model.GroupDTO;
import org.skife.jdbi.v2.Batch;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.customizers.BatchChunkSize;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.skife.jdbi.v2.util.IntegerMapper;


public class AtrribDAO {
	
	
	interface AttributeCreatable {
		 @SqlBatch("INSERT INTO cmxattribute(companydid, id )  values( :companydid,:id )")
		 @BatchChunkSize(10)
		 @GetGeneratedKeys
		 public Integer createAttribute(@Bind("companydid") Integer companydid ,@Bind("id") String id );

	}
	
	public void createIfAbsent(CompanyDTO company, GroupDTO groupDTO, EnvDTO envDTO, Map<String, String> paramMap) {
		// TODO Auto-generated method stub
		
	}
	public List<Integer> createAttributes(CompanyDTO company, List <String> newAttributes) {
		DBI dbi = JDBIUtil.getInstance();
		Handle h = dbi.open();
		AttributeCreatable attribCreator =h.attach(AttributeCreatable.class);
		List<Integer> attributeDids = new ArrayList<Integer>();
		newAttributes.forEach((k)->{
			Integer attributeDid = attribCreator.createAttribute(company.getCompanyDid(), k);
			attributeDids.add(attributeDid);
			
		} );
		
		Assert.assertNotNull(attributeDids);
		return attributeDids;

	}
	public void update(CompanyDTO company, GroupDTO groupDTO, EnvDTO envDTO, MultivaluedMap<String, String> paramMap) {
		// TODO Auto-generated method stub
		
	}

	public List<AttribValueDTO> load(CompanyDTO company, GroupDTO groupDTO, EnvDTO envDTO) {
		DBI dbi = JDBIUtil.getInstance();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT atrib.id ,atv.attribVal");
		sql.append(" FROM  ");
		sql.append(" cmxattributedistribution atribDis,");
		sql.append(" cmxattribute atrib ");
		sql.append(" cmxattributevalue atv,");
		
		sql.append(" WHERE ");
		sql.append(" atribDis.attributedistributiondid = atv.attributedistributiondid");
		sql.append(" AND atrib.attributedid=atribDis.attributedid");
		sql.append(" AND atrib.companydid=atribDis.companydid");
		sql.append(" AND atv.profiledid=:profiledid");
		sql.append(" AND atribDis.groupdid =:groupdid");
		sql.append(" AND atribDis.companydid =:companydid");
		sql.append(" AND atribDis.isvalid=:valid");
		sql.append(" AND atrib.isvalid=:valid");
		sql.append(" AND atv.isvalid=:valid");
		
		return dbi.open()
				.createQuery(sql.toString())
				.bind("profiledid", envDTO.getProfileDid())
				.bind("groupdid", groupDTO.getGroupDid())
				.bind("companydid", company.getCompanyDid())
				.bind("valid", Boolean.TRUE)
				.map(new ResultSetMapper<AttribValueDTO>() {

					@Override
					public AttribValueDTO map(int arg0, ResultSet arg1, StatementContext arg2) throws SQLException {
						return new AttribValueDTO(arg1.getString(1), arg1.getString(2));
					}

				}).list();
	}
	public List<AttributeDTO> loadAttribute(CompanyDTO company) {
		DBI dbi = JDBIUtil.getInstance();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT atrib.id ,atrib.attributedid");
		sql.append(" FROM  ");
		sql.append(" cmxattribute atrib ");
		
		sql.append(" WHERE ");
		sql.append(" atribDis.attributedistributiondid = atv.attributedistributiondid");
		sql.append(" AND atrib.companydid=:companydid");
		sql.append(" AND atrib.isvalid=:valid");
		
		List<AttributeDTO> returnList= dbi.open()
										.createQuery(sql.toString())
										.bind("companydid", company.getCompanyDid())
										.bind("valid", Boolean.TRUE)
										.map(new ResultSetMapper<AttributeDTO>() {
						
											@Override
											public AttributeDTO map(int arg0, ResultSet arg1, StatementContext arg2) throws SQLException {
												return new AttributeDTO(arg1.getString(1), arg1.getInt(2));
											}
						
										}).list();
		returnList= returnList==null||returnList.isEmpty()?	Collections.emptyList():returnList;
		
		return returnList;
	}
	

	 
}
