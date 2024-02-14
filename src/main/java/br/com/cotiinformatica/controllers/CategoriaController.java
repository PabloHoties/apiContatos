package br.com.cotiinformatica.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.entities.Categoria;
import br.com.cotiinformatica.repositories.CategoriaRepository;

@RestController
@RequestMapping(value = "/api/categorias")
public class CategoriaController {

	@GetMapping
	public ResponseEntity<List<Categoria>> getAll() throws Exception {

		try {
			CategoriaRepository categoriaRepository = new CategoriaRepository();
			List<Categoria> categorias = categoriaRepository.findAll();

			if (categorias.size() == 0)
				return ResponseEntity.status(204).body(null);

			return ResponseEntity.status(200).body(categorias);
		} catch (Exception e) {
			return ResponseEntity.status(500).body(null);
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<Categoria> getById(@PathVariable("id") UUID id) throws Exception {

		try {
			CategoriaRepository categoriaRepository = new CategoriaRepository();
			Categoria categoria = categoriaRepository.findById(id);

			if (categoria == null)
				return ResponseEntity.status(204).body(null);

			return ResponseEntity.status(200).body(categoria);
		} catch (Exception e) {
			return ResponseEntity.status(500).body(null);
		}
	}
}
