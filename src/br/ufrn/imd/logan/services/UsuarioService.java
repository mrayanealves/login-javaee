package br.ufrn.imd.logan.services;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.ufrn.imd.logan.dto.UsuarioDTO;
import br.ufrn.imd.logan.exception.LoginException;
import br.ufrn.imd.logan.model.Usuario;
import br.ufrn.imd.logan.repository.UsuarioRepository;
import br.ufrn.imd.logan.security.JWTUtil;

@Stateless
public class UsuarioService {
	
	@Inject
	private UsuarioRepository repository;
	
	public String login(UsuarioDTO usuarioDTO) throws Exception, LoginException {
		Optional<Usuario> usuarioCadastrado = repository.buscarUsuarioPorEmailSenha(usuarioDTO.getEmail(), 
																					usuarioDTO.getSenha());
		
		if (!usuarioCadastrado.isPresent()) {
			throw new LoginException();
		}
		
		return JWTUtil.create(usuarioCadastrado.get().getEmail());
	}
}
