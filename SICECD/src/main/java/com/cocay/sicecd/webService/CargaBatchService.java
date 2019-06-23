package com.cocay.sicecd.webService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.cocay.sicecd.dto.ResponseGeneric;

@Path("/CargaBatchService")
public interface CargaBatchService {
	
	@GET
	@Path("/lstProfesores/")
	@Produces( MediaType.APPLICATION_JSON )
	public ResponseGeneric lstProfesores(
			@QueryParam("limit") Integer limit,
			@QueryParam("offset") Integer offset,
			@QueryParam("search") String search,
			@QueryParam("name") String name,
			@QueryParam("order") String order
	); 
}
