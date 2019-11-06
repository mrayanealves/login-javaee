package br.ufrn.imd.logan.repository;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.ufrn.imd.logan.model.Usuario;

@Stateless
public class UsuarioRepository {
	@PersistenceContext
	private EntityManager em;
	
	public Usuario buscarUsuarioPorEmailSenha(String email, String senha) {
		try {
			Query query = em.createQuery("select u from Usuario u "
					+ "where email = :email and senha = :senha");
			query.setParameter("email", email);
			query.setParameter("senha", senha);
			
			return (Usuario) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Usuario buscarUsuarioPorEmail(String email) {
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
