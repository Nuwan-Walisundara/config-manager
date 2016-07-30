/*
 * Copyright (c) 2016, WSO2 Inc. (http://wso2.com) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.nu.msc.dao;

import org.nu.msc.conf.ConfigReader;
import org.skife.jdbi.v2.DBI;

import io.dropwizard.jdbi.DBIFactory;

class JDBIUtil {
	private static DBI  jdbi; 
	static {
		final ConfigReader configReader = ConfigReader.getInstance();
		final DBIFactory factory = new DBIFactory();
		jdbi = factory.build(configReader.getEnvironment(), 
										configReader.getConfigDTO().getDataSourceFactory(), 
										"mysql");
	}
	
	public static DBI getInstance(){
		return jdbi;
	}
}
