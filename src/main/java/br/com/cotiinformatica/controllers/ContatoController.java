package br.com.cotiinformatica.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.ContatoRequest;
import br.com.cotiinformatica.dtos.ContatoResponse;
import br.com.cotiinformatica.services.ContatoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/contatos")
public class ContatoController {
	
	@Autowired
	private ContatoService contatoService;
	
	@PostMapping
	public ResponseEntity<ContatoResponse> post(@RequestBody @Valid ContatoRequest request) throws Exception {
		return ResponseEntity.status(201).body(contatoService.criar(request));
	}

	@PutMapping("{id}")
	public ResponseEntity<ContatoResponse> put(@PathVariable UUID id, @RequestBody @Valid ContatoRequest request) throws Exception {
		return ResponseEntity.status(200).body(contatoService.atualizar(id, request));
	}

	@DeleteMapping("{id}")
	public ResponseEntity<ContatoResponse> delete(@PathVariable("id") UUID id) throws Exception {
		return ResponseEntity.status(200).body(contatoService.excluir(id));
	}

	@GetMapping
	public ResponseEntity<List<ContatoResponse>> getAll() throws Exception {
		return ResponseEntity.status(200).body(contatoService.consultar());
	}

	@GetMapping("{id}")
	public ResponseEntity<ContatoResponse> getById(@PathVariable("id") UUID id) throws Exception {
		return ResponseEntity.status(200).body(contatoService.obterPorId(id));
	}
}
