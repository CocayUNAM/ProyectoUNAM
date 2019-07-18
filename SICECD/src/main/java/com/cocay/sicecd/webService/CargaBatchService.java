package com.cocay.sicecd.webService;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
	
	@GET
	@Path("/lstCursos/")
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseGeneric lstCursos(
			@QueryParam("limit") Integer limit,
			@QueryParam("offset") Integer offset,
			@QueryParam("search") String search,
			@QueryParam("name") String name,
			@QueryParam("order") String order
	);
	
	@GET
	@Path("/lstGrupo/")
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseGeneric lstGrupo(
			@QueryParam("limit") Integer limit,
			@QueryParam("offset") Integer offset,
			@QueryParam("search") String search,
			@QueryParam("name") String name,
			@QueryParam("order") String order
	);
	
	@GET
	@Path("/lstInscripciones/")
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseGeneric lstInscripciones(
			@QueryParam("limit") Integer limit,
			@QueryParam("offset") Integer offset,
			@QueryParam("search") String search,
			@QueryParam("name") String name,
			@QueryParam("order") String order
	);
	
	@GET
	@Path("/lstErrores/")
	@Produces( MediaType.APPLICATION_JSON )
	public ResponseGeneric lstErrores(
			@QueryParam("limit") Integer limit,
			@QueryParam("offset") Integer offset,
			@QueryParam("search") String search,
			@QueryParam("name") String name,
			@QueryParam("order") String order
	);
	
	@POST
	@Path("/lstLimpiar/")
	@Produces( MediaType.APPLICATION_JSON )
	@Consumes( MediaType.APPLICATION_JSON)
	public ResponseGeneric lstLimpiar(
	);
	
	@GET
	@Path("/ultimoValor/")
	@Produces( MediaType.APPLICATION_JSON )
	public ResponseGeneric ultimoValor(
	);
	
	@GET
	@Path("/limpiarTabla/{tabla}")
	@Produces( MediaType.APPLICATION_JSON )
	public ResponseGeneric limpiarTabla(
			@PathParam("tabla") String tabla
	);
}
