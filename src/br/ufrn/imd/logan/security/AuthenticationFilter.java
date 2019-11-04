package br.ufrn.imd.logan.security;

import java.io.IOException;
import java.security.Principal;

import javax.annotation.Priority;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import io.jsonwebtoken.Claims;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
	@Inject
	@AuthenticatedUser
	Event<String> userAuthenticatedEvent;
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

		if ((authorizationHeader == null) || (!authorizationHeader.startsWith("Bearer "))) {
			throw new NotAuthorizedException("Authorization header required.");	
		}

		String token = authorizationHeader.substring("Bearer ".length()).trim();

		try {
			Claims claims = JWTUtil.decode(token);

			if (claims == null) {
				throw new Exception("Token invalid.");
			}
			
			this.changeRequestContext(requestContext, claims.getSubject());
			this.userAuthenticatedEvent.fire(claims.getSubject());
		} catch (Exception e) {
			e.printStackTrace();
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
		}
	}

	private void changeRequestContext(ContainerRequestContext requestContext, String login) {
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
