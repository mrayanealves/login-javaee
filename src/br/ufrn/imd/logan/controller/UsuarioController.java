package br.ufrn.imd.logan.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;

import br.ufrn.imd.logan.dto.UsuarioDTO;
import br.ufrn.imd.logan.exception.LoginException;
import br.ufrn.imd.logan.model.Usuario;
import br.ufrn.imd.logan.security.AuthenticatedUser;
import br.ufrn.imd.logan.security.Secured;
import br.ufrn.imd.logan.services.UsuarioService;

@Path("/")
@Stateless
public class UsuarioController {
	@EJB
	private UsuarioService service;
	
	@Inject
	@AuthenticatedUser
	Usuario authenticatedUser;
	
	@POST
	@Consumes("application/json; charset=UTF-8")
	@Path("/login")
	public Response login(UsuarioDTO login) {
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
	public Response logado(@Context SecurityContext securityContext) {	
		// String login = securityContext.getUserPrincipal().getName();
		
		System.out.println(this.authenticatedUser);
		
		return Response.ok(this.authenticatedUser.getNome()).build(); 
	}
}
