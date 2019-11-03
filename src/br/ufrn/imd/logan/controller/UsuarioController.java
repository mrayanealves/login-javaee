package br.ufrn.imd.logan.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.Consumes;

import br.ufrn.imd.logan.dto.UsuarioDTO;
import br.ufrn.imd.logan.exception.LoginException;
import br.ufrn.imd.logan.services.UsuarioService;

@Path("/login")
@Stateless
public class UsuarioController {
	@EJB
	private UsuarioService service;
	
	@POST
	@Consumes("application/json; charset=UTF-8")
	@Path("/")
	public Response login(UsuarioDTO login) {
		try {
			return Response.ok(service.login(login)).build();
		} catch (LoginException e) {
			return Response.status(Status.UNAUTHORIZED).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
