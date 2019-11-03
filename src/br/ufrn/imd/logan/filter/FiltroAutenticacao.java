package br.ufrn.imd.logan.filter;

import java.io.IOException;
import java.security.Principal;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import br.ufrn.imd.logan.security.JWTUtil;
import br.ufrn.imd.logan.security.Security;
import io.jsonwebtoken.Claims;

@Security
@Provider
@Priority(Priorities.AUTHENTICATION)
public class FiltroAutenticacao implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

		if ((authorizationHeader == null)) {
			System.out.println("Aqui");
			throw new NotAuthorizedException("Authorization header required.");	
		}
		
		if (!authorizationHeader.startsWith("Bearer ")) {
			throw new NotAuthorizedException("Authorization header required.");
		}

		String token = authorizationHeader.substring("Bearer ".length()).trim();

		try {
			Claims claims = JWTUtil.decode(token);

			if (claims == null) {
				throw new Exception("Token invalid.");
			}
			
			this.modificarRequestContext(requestContext, claims.getId());
		} catch (Exception e) {
			e.printStackTrace();
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
		}
	}

	private void modificarRequestContext(ContainerRequestContext requestContext, String login) {
		final SecurityContext currentSecurityContext = requestContext.getSecurityContext();
		requestContext.setSecurityContext(new SecurityContext() {

			@Override
			public Principal getUserPrincipal() {
				return new Principal() {
					@Override
					public String getName() {
						return login;
					}
				};
			}

			@Override
			public boolean isUserInRole(String role) {
				return true;
			}

			@Override
			public boolean isSecure() {
				return currentSecurityContext.isSecure();
			}

			@Override
			public String getAuthenticationScheme() {
				return "Bearer";
			}
		});
	}
}
