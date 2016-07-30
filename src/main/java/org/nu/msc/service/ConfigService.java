package org.nu.msc.service;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.nu.msc.exception.ConfigException;
import org.nu.msc.model.CompanyDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Api(value = "/configservice", description = "This service provide a rest service for config service")
@Path("/configservice")
public class ConfigService {
	Logger log = LoggerFactory.getLogger(ConfigService.class);

	
	@GET
	@Path("/{ownerID}/{contextID}/{envID}")
	@ApiOperation(value = "Get the valid token", notes = "API for return the contact by given the id", response = Response.class)
	public Response get(
			@ApiParam(value = "owner Id of token to fetch", required = true) @PathParam("ownerID") String id,
			@ApiParam(value = "Context to fetch", required = true) @PathParam("contextID") String contextID,
			@ApiParam(value = "enviormtn to fetch", required = true) @PathParam("envID") String envID)  {
			try {
				CompanyDTO cmpDTO=	new ConfigManager().loadConfig ( id, contextID,envID);
				return Response.status(Response.Status.OK).entity(cmpDTO).build();
			} catch (ConfigException e) {
				return Response.status(Response.Status.BAD_REQUEST)
						.entity(e.getError()).build();
			}

	}
	
	@POST
	@Path("/{ownerID}/{contextID}/{envID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "create new values for configurations", notes = "API for return the contact by given the id", response = Response.class)
	public Response create(	@ApiParam(value = "owner Id of token to fetch", required = true) @PathParam("ownerID") String id,
			@ApiParam(value = "Context to fetch", required = true) @PathParam("contextID") String contextID,
			@ApiParam(value = "enviormtn to fetch", required = true) @PathParam("envID") String envID,
			@ApiParam(value = "owner Id of token to fetch", required = true) MultivaluedMap<String, String> paramMap){
		Map<String,String> valueMap =new HashMap<String,String>();
		paramMap.putAll(paramMap);
		try {
			new ConfigManager().create ( id, contextID,envID,valueMap);
			return Response.status(Response.Status.OK).entity("Successfuly created").build();
			
		} catch (ConfigException e) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(e.getError()).build();
		}
	}
	
	@PUT
	@Path("/{ownerID}/{contextID}/{envID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update existing configurations for given parameters", notes = "API for return the contact by given the id", response = Response.class)
	public Response update(@ApiParam(value = "owner Id of token to fetch", required = true) @PathParam("ownerID") String id,
			@ApiParam(value = "Context to fetch", required = true) @PathParam("contextID") String contextID,
			@ApiParam(value = "enviormtn to fetch", required = true) @PathParam("envID") String envID,
			@ApiParam(value = "owner Id of token to fetch", required = true) MultivaluedMap<String, String> paramMap){
		try {
			new ConfigManager().update ( id, contextID,envID,paramMap);
			return Response.status(Response.Status.OK).entity("Successfuly updated").build();
		} catch (ConfigException e) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(e.getError()).build();
		}
	}
	
	@DELETE
	@Path("/{ownerID}/{contextID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Delete the  Context from given owner", notes = "API for return the contact by given the id", response = Response.class)
	public Response delete(
			@ApiParam(value = "owner Id of token to fetch", required = true) @PathParam("ownerID") String id,
			@ApiParam(value = "Context to fetch", required = true) @PathParam("contextID") String contextID ){
		try {
			new ConfigManager().delete ( id, contextID );
			return Response.status(Response.Status.OK).entity("Successfuly deleted").build();
		} catch (ConfigException e) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(e.getError()).build();
		}
	}
	@DELETE
	@Path("/{ownerID}/{contextID}/{envID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Delete the enviroment from given owner,contextID ", notes = "API for return the contact by given the id", response = Response.class)
	public Response delete(
			@ApiParam(value = "owner Id of token to fetch", required = true) @PathParam("ownerID") String id,
			@ApiParam(value = "Context to fetch", required = true) @PathParam("contextID") String contextID,
			@ApiParam(value = "enviormtn to fetch", required = true) @PathParam("envID") String envID){
		try {
			new ConfigManager().delete ( id, contextID,envID);
			return Response.status(Response.Status.OK).entity("Successfuly deleted").build();
		} catch (ConfigException e) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(e.getError()).build();
		}
	}
}
