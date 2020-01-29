package br.ufrn.imd.logan.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;

import br.ufrn.imd.logan.dto.Login;
import br.ufrn.imd.logan.exception.LoginException;
import br.ufrn.imd.logan.security.Secured;
import br.ufrn.imd.logan.services.UsuarioService;

@Path("/")
@Stateless
public class UsuarioController {
	@EJB
	private UsuarioService service;
	
	@POST
	@Consumes("application/json; charset=UTF-8")
	@Path("/login")
	public Response login(Login login) {
		try {
			return Response.ok(service.login(login)).build();
		} catch (LoginException e) {
			return Response.status(Status.UNAUTHORIZED).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/usuario/logado")
	@Secured
	public Response logado() {	
		return Response.ok(service.usuarioLogado()).build(); 
	}
}
