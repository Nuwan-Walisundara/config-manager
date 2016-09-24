package org.nu.msc.service;

import java.io.StringReader;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.nu.msc.exception.ConfigException;
import org.nu.msc.model.CompanyDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
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
				Gson gson = new GsonBuilder()
		                .excludeFieldsWithoutExposeAnnotation()
		                .create();
				JsonElement je = gson.toJsonTree(cmpDTO);
				 JsonObject jo = new JsonObject();
				 jo.add("Company", je);
				return Response.status(Response.Status.OK).entity(jo.toString()).build();
			} catch (ConfigException e) {
				return Response.status(Response.Status.BAD_REQUEST)
						.entity(e.getError()).build();
			}

	}
	
/*	@POST
	@Path("/{ownerID}/{contextID}/{envID}")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	@ApiOperation(value = "create new values for configurations", notes = "API for return the contact by given the id", response = Response.class)
	public Response create(	@ApiParam(value = "owner Id of token to fetch", required = true) @PathParam("ownerID") String id,
			@ApiParam(value = "Context to fetch", required = true) @PathParam("contextID") String contextID,
			@ApiParam(value = "enviormtn to fetch", required = true) @PathParam("envID") String envID,
			@ApiParam(value = "owner Id of token to fetch", required = true)  MultivaluedMap<String, String> paramMap){
		Map<String,String> valueMap =new HashMap<String,String>();
		paramMap.putAll(paramMap);
		try {
			new ConfigManager().create ( id, contextID,envID,valueMap);
			return Response.status(Response.Status.OK).entity("Successfuly created").build();
			
		} catch (ConfigException e) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(e.getError()).build();
		}
	}*/
	
	
	@POST
	@Path("/{ownerID}/{contextID}")
//	@Consumes({MediaType.APPLICATION_FORM_URLENCODED})
	@Consumes({MediaType.APPLICATION_JSON})
	@ApiOperation(value = "create new values for configurations", notes = "API for return the contact by given the id", response = Response.class)
	public Response create(	@ApiParam(value = "owner Id of token to fetch", required = true) @PathParam("ownerID") String id,
			@ApiParam(value = "Context to fetch", required = true) @PathParam("contextID") String contextID,
			@ApiParam(value = "List of Attributes to fesh", required = true)  String jsonBody){
		try {
			 Gson gs = new Gson();
//			 paramMap.containsKey("callbackUrl");
			 CreateWrapperDTO paramMap = gs.fromJson(jsonBody, CreateWrapperDTO.class);
			new ConfigManager().create ( id, contextID, paramMap.getAttributes(),paramMap.getEnviorments());
			return Response.status(Response.Status.OK).entity("Successfuly created").build();
			
		} catch (ConfigException e) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(e.getError()).build();
		}
	}
	
/*	@PUT
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
	}*/
	
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
	
	class CreateWrapperDTO{
		private AttributeDTO[] attributes;

		private   AttributeDTO[] enviorments;

		public Map<String,String> getAttributes() {
			return Arrays .asList(attributes ).stream()
							.collect(Collectors.toMap(AttributeDTO::getName, AttributeDTO::getDescription)) ;
		}

		public void setAttributes(AttributeDTO[] attributes) {
			this.attributes = attributes;
		}

		public  Map<String,String> getEnviorments() {
			return Arrays .asList(enviorments ).stream().collect(Collectors.toMap(AttributeDTO::getName, AttributeDTO::getDescription));
		}

		public void setEnviorments(AttributeDTO[] enviorments) {
			this.enviorments = enviorments;
		}
		
		 
		}
	
	class AttributeDTO{
		private String name;
		private String description;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		
		 
		
	}
}
