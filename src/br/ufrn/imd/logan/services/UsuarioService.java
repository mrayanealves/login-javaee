package br.ufrn.imd.logan.services;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.ufrn.imd.logan.dto.Login;
import br.ufrn.imd.logan.exception.LoginException;
import br.ufrn.imd.logan.model.Usuario;
import br.ufrn.imd.logan.security.JWTUtil;
import br.ufrn.imd.repository.UsuarioRepository;

@Stateless
public class UsuarioService {
	
	@Inject
	private UsuarioRepository repository;
	
	public String login(Login login) throws Exception, LoginException {
		Optional<Usuario> usuarioCadastrado = repository.buscarUsuarioPorEmailSenha(login.getEmail(), login.getSenha());
		
		if (!usuarioCadastrado.isPresent()) {
			throw new LoginException("Usuário não encontrado!");
		}
		
		return JWTUtil.create(usuarioCadastrado.get().getEmail());
	}
}
