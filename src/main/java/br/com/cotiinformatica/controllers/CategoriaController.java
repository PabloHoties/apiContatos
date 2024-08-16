package br.com.cotiinformatica.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.CategoriaResponse;
import br.com.cotiinformatica.services.CategoriaService;

@RestController
@RequestMapping(value = "/api/categorias")
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;

	@GetMapping
	public ResponseEntity<List<CategoriaResponse>> getAll() throws Exception {
		return ResponseEntity.status(200).body(categoriaService.consultar());
	}

	@GetMapping("{id}")
	public ResponseEntity<CategoriaResponse> getById(@PathVariable("id") UUID id) throws Exception {
		return ResponseEntity.status(200).body(categoriaService.obterPorId(id));
	}
}
