package org.nu.msc.dao;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

interface DistributionPersistable {

	 @SqlUpdate("UPDATE cmxattributevalue atrib SET atrib.isvalid=:invalid WHERE   atrib.profiledid in(select profiledid from cmxprofile where companydid =:companydid)")
	 public void delete(@Bind("invalid") Boolean invalid,@Bind("companydid") int companydid) ;
	 
	 @SqlUpdate("INSERT INTO cmxattributedistribution(companydid,groupdid,attributedid )  values( :companydid,:groupdid,:attributedid)")
	 @GetGeneratedKeys
	 public List<Integer> create(@Bind("companydid") Integer companydid ,@Bind("groupdid") Integer groupdid,
			 					@Bind("attributedid") Integer  attributedid );
	 

}
