package br.ufrn.imd.repository;

import java.util.Optional;

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
	
	public Optional<Usuario> buscarUsuarioPorEmailSenha(String email, String senha) {
		try {
			Query query = em.createQuery("select u from Usuario u "
					+ "where email = :email and senha = :senha");
			query.setParameter("email", email);
			query.setParameter("senha", senha);
			
			return Optional.of((Usuario) query.getSingleResult());
		} catch (NoResultException e) {
			return Optional.empty();
		}
	}
}
