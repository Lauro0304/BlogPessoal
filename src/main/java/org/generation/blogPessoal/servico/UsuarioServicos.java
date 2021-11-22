package org.generation.blogPessoal.servico;

import java.nio.charset.Charset;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.codec.binary.Base64;
import org.generation.blogPessoal.model.UsuarioLoginModel;
import org.generation.blogPessoal.model.UsuarioModel;
import org.generation.blogPessoal.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioServicos {

	@Autowired

	private UsuarioRepository usuarioRepository;

	public Optional<UsuarioModel> cadastrarUsuario(UsuarioModel usuarioModel) {

		if (usuarioRepository.findAllByUsuarioModelContainingIgnoreCase(usuarioModel.getUsuarioModel()).isPresent())

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);

		usuarioModel.setSenha(criptografarSenha(usuarioModel.getSenha()));

		return Optional.of(usuarioRepository.save(usuarioModel));

	}

	public Optional<UsuarioModel> atualizarUsuario(UsuarioModel usuarioModel) {

		if (usuarioRepository.findById(usuarioModel.getId()).isPresent()) {

			Optional<UsuarioModel> buscaUsuario = usuarioRepository
					.findAllByUsuarioModelContainingIgnoreCase(usuarioModel.getUsuarioModel());

			if (buscaUsuario.isPresent()) {

				if (buscaUsuario.get().getId() != usuarioModel.getId())

					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);

			}

			usuarioModel.setSenha(criptografarSenha(usuarioModel.getSenha()));

			return Optional.of(usuarioRepository.save(usuarioModel));

		}

		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!", null);

	}

	public Optional<UsuarioLoginModel> logarUsuario(Optional<UsuarioLoginModel> usuario) {

		Optional<UsuarioModel> usuarioModel = usuarioRepository
				.findAllByUsuarioModelContainingIgnoreCase(usuario.get().getUsuario());

		if (usuarioModel.isPresent()) {

			if (compararSenhas(usuario.get().getSenha(), usuarioModel.get().getSenha())) {

				usuario.get().setId(usuarioModel.get().getId());
				
				usuario.get().setFoto(usuarioModel.get().getFoto());

				usuario.get().setNome(usuarioModel.get().getNome());

				usuario.get().setSenha(usuarioModel.get().getSenha());

				usuario.get().setToken(generatorBasicToken(usuario.get().getUsuario(), usuario.get().getSenha()));

				return usuario;

			}

		}

		throw new ResponseStatusException(

				HttpStatus.UNAUTHORIZED, "Usuário ou senha inválidos!", null);

	}

	private String criptografarSenha(String senha) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		String senhaEncoder = encoder.encode(senha);

		return senhaEncoder;

	}

	private boolean compararSenhas(String senhaDigitada, String senhaBanco) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		return encoder.matches(senhaDigitada, senhaBanco);

	}

	private String generatorBasicToken(String email, String password) {

		String structure = email + ":" + password;

		byte[] structureBase64 = Base64.encodeBase64(structure.getBytes(Charset.forName("US-ASCII")));

		return "Basic " + new String(structureBase64);

	}

	public Optional<UsuarioModel> Logar(@Valid Optional<UsuarioLoginModel> usuario) {
		// TODO Auto-generated method stub
		return null;
	}

}
