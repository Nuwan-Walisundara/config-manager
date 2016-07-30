package org.nu.msc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.nu.msc.model.CompanyDTO;
import org.nu.msc.model.EnvDTO;
import org.nu.msc.model.GroupDTO;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class EnvDAO {

	public EnvDTO createIfAbsent(CompanyDTO company, GroupDTO groupDTO, String envID) {
		// TODO Auto-generated method stub
		return null;
	}

	public EnvDTO load(CompanyDTO company, GroupDTO groupDTO, String envID) {
		DBI dbi = JDBIUtil.getInstance();
		return dbi.open().createQuery("SELECT did,name FROM cmxcompany WHERE name=:id").bind("id", id)
				.map(new ResultSetMapper<EnvDTO>() {

					@Override
					public EnvDTO map(int arg0, ResultSet arg1, StatementContext arg2) throws SQLException {
						return new EnvDTO(arg1.getInt(1), arg1.getString(2));
					}

				}).first();
	}

	public void delete(CompanyDTO company, GroupDTO groupDTO, EnvDTO envDTO) {
		// TODO Auto-generated method stub
		
	}

}
