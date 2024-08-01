package br.com.cotiinformatica.controllers;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.CategoriaResponseDto;
import br.com.cotiinformatica.entities.Categoria;
import br.com.cotiinformatica.repositories.CategoriaRepository;

@RestController
@RequestMapping(value = "/api/categorias")
public class CategoriaController {

	@Autowired
	ModelMapper modelMapper;

	@GetMapping
	public ResponseEntity<List<CategoriaResponseDto>> getAll() throws Exception {
		try {
			CategoriaRepository categoriaRepository = new CategoriaRepository();
			List<Categoria> categorias = categoriaRepository.findAll();

			if (categorias.size() == 0)
				return ResponseEntity.status(204).body(null);

			List<CategoriaResponseDto> response = categorias.stream()
					.map(categoria -> modelMapper.map(categoria, CategoriaResponseDto.class))
					.collect(Collectors.toList());

			return ResponseEntity.status(200).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(500).body(null);
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<CategoriaResponseDto> getById(@PathVariable("id") UUID id) throws Exception {
		try {
			CategoriaRepository categoriaRepository = new CategoriaRepository();
			Categoria categoria = categoriaRepository.findById(id);

			if (categoria == null)
				return ResponseEntity.status(204).body(null);

			CategoriaResponseDto response = modelMapper.map(categoria, CategoriaResponseDto.class);

			return ResponseEntity.status(200).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(500).body(null);
		}
	}
}
