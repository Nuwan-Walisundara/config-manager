package org.nu.msc.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

interface AttributePersistable {

	 @SqlUpdate("INSERT INTO cmxattribute(companydid, id )  values( :companydid,:id )")
	 @GetGeneratedKeys
	 public Integer create(@Bind("companydid") Integer companydid ,@Bind("id")  String  id );
	 
	 @SqlUpdate("UPDATE cmxattribute atrib SET atrib.isvalid=:invalid WHERE   atrib.companydid=:companydid")
	 public void delete(@Bind("invalid") Boolean invalid,@Bind("companydid") int companydid) ;
	  

}
