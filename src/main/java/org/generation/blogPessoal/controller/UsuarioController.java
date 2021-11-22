package org.generation.blogPessoal.controller;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.generation.blogPessoal.model.UsuarioLoginModel;
import org.generation.blogPessoal.model.UsuarioModel;
import org.generation.blogPessoal.repository.UsuarioRepository;
import org.generation.blogPessoal.servico.UsuarioServicos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {

	@Autowired
	private UsuarioServicos usuarioServicos;

	@Autowired
	private UsuarioRepository repository;

	@GetMapping("/all")
	public ResponseEntity<List<UsuarioModel>> getAll() {
		return ResponseEntity.ok(repository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<UsuarioModel> getById(@PathVariable Long id) {
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/logar")
	public ResponseEntity<UsuarioModel> Autentication(@ Valid @RequestBody Optional<UsuarioLoginModel> usuario) {
		return usuarioServicos.Logar(usuario).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());

	}

	@PostMapping("/cadastrar")
	public ResponseEntity<Optional<UsuarioModel>> Post(@Valid @RequestBody UsuarioModel usuarioModel) {
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioServicos.cadastrarUsuario(usuarioModel));

	}

}
