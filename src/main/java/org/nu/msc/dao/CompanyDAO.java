package org.nu.msc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Assert;
import org.nu.msc.model.CompanyDTO;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.skife.jdbi.v2.util.IntegerMapper;

public class CompanyDAO {

	public CompanyDTO load(String id) {
		DBI dbi = JDBIUtil.getInstance();
		return dbi.open().createQuery("SELECT did,name FROM cmxcompany WHERE name=:id").bind("id", id)
				.map(new ResultSetMapper<CompanyDTO>() {

					@Override
					public CompanyDTO map(int arg0, ResultSet arg1, StatementContext arg2) throws SQLException {
						return new CompanyDTO(arg1.getInt(1), arg1.getString(2));
					}

				}).first();
	}

	public int create(String id) {
		DBI dbi = JDBIUtil.getInstance();
		Integer companyDid = dbi.open().createStatement(" INSERT INTO cmxcompany( name )  values( :name ) ")
				.bind("name", id).executeAndReturnGeneratedKeys(IntegerMapper.FIRST).first();

		Assert.assertNotNull(companyDid);
		return companyDid;

	}


}
