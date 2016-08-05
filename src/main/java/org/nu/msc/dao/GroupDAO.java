package org.nu.msc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Assert;
import org.nu.msc.model.CompanyDTO;
import org.nu.msc.model.GroupDTO;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.skife.jdbi.v2.util.IntegerMapper;

public class GroupDAO {


	public GroupDTO load(CompanyDTO company, String contextID) {
		DBI dbi = JDBIUtil.getInstance();
		return dbi.open()
				.createQuery("SELECT companydid,id,groupdid FROM cmxgroup WHERE companydid=:companydid and name=:id")
				.bind("id", contextID).bind("companydid", company.getCompanyDid()).map(new ResultSetMapper<GroupDTO>() {

					@Override
					public GroupDTO map(int arg0, ResultSet arg1, StatementContext arg2) throws SQLException {
						return new GroupDTO(arg1.getInt(1), arg1.getString(2), arg1.getInt(3));
					}

				}).first();
	}

	public int create(CompanyDTO company, String contextID) {
		DBI dbi = JDBIUtil.getInstance();
		Integer companyDid = dbi.open()
				.createStatement(
						" INSERT INTO cmxgroup( companydid,id,description )  values(:companydid,:id,:description ) ")
				.bind("companydid", company.getCompanyDid()).bind("id", contextID).bind("description", contextID)
				.executeAndReturnGeneratedKeys(IntegerMapper.FIRST).first();

		Assert.assertNotNull(companyDid);
		return companyDid;

	}

	public void delete(CompanyDTO company, GroupDTO groupDTO) {
		DBI dbi = JDBIUtil.getInstance();
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE cmxgroup ")
			.append(" SET isvalid=:isvalid")
			.append(" WHERE groupdid=:groupdid AND ")
			.append(" companydid=:companydid   ");

		dbi.open().createStatement(sql.toString()).bind("isvalid", Boolean.FALSE)
				.bind("groupdid", groupDTO.getGroupDid())
				.bind("companydid", company.getCompanyDid())
				.execute();

	}

}
