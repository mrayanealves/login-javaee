package br.ufrn.imd.logan.controller;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.ufrn.imd.logan.security.Security;

@Security
@Stateless
@Path("/teste")
public class TesteController {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/")
	public Response teste() {	
		return Response.ok("Testando").build(); 
	}
}
