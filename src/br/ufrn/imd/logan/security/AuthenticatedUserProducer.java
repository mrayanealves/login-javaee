package br.ufrn.imd.logan.security;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.ufrn.imd.logan.model.Usuario;

@RequestScoped
public class AuthenticatedUserProducer {
	@PersistenceContext
	private EntityManager em;
	
	@Produces
    @RequestScoped
    @AuthenticatedUser
    private Usuario authenticatedUser;

    public void handleAuthenticationEvent(@Observes @AuthenticatedUser String email) {
        this.authenticatedUser = buscarUsuario(email);
    }

    private Usuario buscarUsuario(String email) {
    	try {
			Query query = em.createQuery("select u from Usuario u "
					+ "where email = :email");
			query.setParameter("email", email);
			
			return (Usuario) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
    }	
}
