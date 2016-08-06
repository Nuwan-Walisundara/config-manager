package org.nu.msc.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

interface AttribValuePersistable {
	 @SqlUpdate("UPDATE cmxattributedistribution  SET isvalid=:invalid WHERE    companydid =:companydid ")
	 public void delete(@Bind("invalid") Boolean invalid,@Bind("companydid") int companydid) ;


}
