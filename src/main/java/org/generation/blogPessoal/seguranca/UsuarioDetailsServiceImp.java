package org.generation.blogPessoal.seguranca;

import java.util.Optional;

import org.generation.blogPessoal.model.UsuarioModel;
import org.generation.blogPessoal.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service

public class UsuarioDetailsServiceImp implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String usuarioModel) throws UsernameNotFoundException {
		Optional<UsuarioModel> user = usuarioRepository.findAllByUsuarioModelContainingIgnoreCase(usuarioModel);
		user.orElseThrow(()-> new UsernameNotFoundException(usuarioModel + "not found."));
		
		return user.map(UsuarioDetailsImp:: new).get();
	}

}
