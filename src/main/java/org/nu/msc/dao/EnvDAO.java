package org.nu.msc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.nu.msc.model.CompanyDTO;
import org.nu.msc.model.EnvDTO;
import org.nu.msc.model.GroupDTO;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.skife.jdbi.v2.util.IntegerMapper;

public class EnvDAO {

	
	interface EnviromentPersistable{
		@SqlBatch("INSERT INTO  cmxprofile (companydid,groupdid,id,description) values( :companydid,:groupdid,:Key,:value ) ")
		public void insertAll(@Bind("companydid") int companydid,@Bind("groupdid") int groupdid,@BindBean Iterator<Entry<String, String>> keyvalue);
	}
	
	public int create(CompanyDTO company, GroupDTO groupDTO, String envID) {
		DBI dbi = JDBIUtil.getInstance();
		StringBuilder sql = new StringBuilder();
		sql.append(" INSERT INTO  cmxprofile (groupdid,companydid,id,description) " )
		.append(" values( :profiledid,:groupdid,:companydid,:id,:description ) ");
		Integer companyDid = dbi.open().createStatement(sql.toString())
				.bind("groupdid", groupDTO.getGroupDid())
				.bind("companydid", company.getCompanyDid())
				.bind("id", envID)
				.bind("description", envID)
				.executeAndReturnGeneratedKeys(IntegerMapper.FIRST).first();

		return companyDid;

	}

	public void createAll(CompanyDTO company, GroupDTO groupDTO, Map<String,String> envID) {
		DBI dbi = JDBIUtil.getInstance(); 
		Handle h = dbi.open();
		EnviromentPersistable eventPersister = h.attach(EnviromentPersistable.class);
		eventPersister.insertAll(company.getCompanyDid(), groupDTO.getGroupDid(), envID.entrySet().iterator());
		h.close();
	}
	
	public EnvDTO load(CompanyDTO company, GroupDTO groupDTO, String envID) {
		DBI dbi = JDBIUtil.getInstance();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT profiledid,groupdid,companydid ,");
		sql.append(" id");
		sql.append(" FROM cmxprofile ");
		sql.append("WHERE groupdid=:groupdid AND");
		sql.append(" companydid=:companydid AND  ");
		sql.append(" id=:envID");
		
		return dbi.open().createQuery(sql.toString())
				.bind("groupdid", groupDTO.getGroupDid())
				.bind("companydid", company.getCompanyDid())
				.bind("envID", envID)
				.map(new ResultSetMapper<EnvDTO>() {

					@Override
					public EnvDTO map(int arg0, ResultSet arg1, StatementContext arg2) throws SQLException {
						return new EnvDTO(arg1.getInt(1), arg1.getInt(2), arg1.getInt(3),arg1.getString(4));
					}

				}).first();
	}

	public void delete(CompanyDTO company, GroupDTO groupDTO, EnvDTO envDTO) {
		DBI dbi = JDBIUtil.getInstance();
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE cmxprofile ")
			.append(" SET isvalid=:isvalid")
			.append(" WHERE groupdid=:groupdid AND ")
			.append( " companydid=:companydid AND  ")
			.append(" profiledid=:profiledid");
		
		dbi.open().createStatement(sql.toString())
				.bind("isvalid", Boolean.FALSE)
				.bind("groupdid", groupDTO.getGroupDid())
				.bind("companydid", company.getCompanyDid())
				.bind("profiledid", envDTO.getProfileDid())
				.execute();
		
	}

}
