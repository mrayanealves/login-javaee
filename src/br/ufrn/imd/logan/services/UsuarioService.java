package br.ufrn.imd.logan.services;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.ufrn.imd.logan.dto.Login;
import br.ufrn.imd.logan.exception.LoginException;
import br.ufrn.imd.logan.model.Usuario;
import br.ufrn.imd.logan.repository.UsuarioRepository;
import br.ufrn.imd.logan.security.JWTUtil;

@Stateless
public class UsuarioService {
	
	@Inject
	private UsuarioRepository repository;
	
	public String login(Login login) throws Exception, LoginException {
		Usuario usuarioCadastrado = repository.buscarUsuarioPorEmailSenha(login.getEmail(), 
																		  login.getSenha());
		
		if (usuarioCadastrado == null) {
			throw new LoginException();
		}
		
		return JWTUtil.create(usuarioCadastrado.getEmail());
	}
}
