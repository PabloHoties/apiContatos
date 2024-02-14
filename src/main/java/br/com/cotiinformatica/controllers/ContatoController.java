package br.com.cotiinformatica.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.ContatoPostRequestDto;
import br.com.cotiinformatica.dtos.ContatoPutRequestDto;
import br.com.cotiinformatica.entities.Categoria;
import br.com.cotiinformatica.entities.Contato;
import br.com.cotiinformatica.repositories.CategoriaRepository;
import br.com.cotiinformatica.repositories.ContatoRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/contatos")
public class ContatoController {

	@PostMapping
	public ResponseEntity<String> post(@RequestBody @Valid ContatoPostRequestDto dto) {

		try {

			Contato contato = new Contato();

			contato.setId(UUID.randomUUID());
			contato.setNome(dto.getNome());
			contato.setTelefone(dto.getTelefone());

			CategoriaRepository categoriaRepository = new CategoriaRepository();
			Categoria categoria = categoriaRepository.findById(dto.getCategoriaId());

			if (categoria == null)
				return ResponseEntity.status(400).body("Categoria não encontrada. Verifique o ID informado.");

			contato.setCategoria(categoria);

			ContatoRepository contatoRepository = new ContatoRepository();
			contatoRepository.insert(contato);

			return ResponseEntity.status(201).body("Contato cadastrado com sucesso.");

		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@PutMapping
	public ResponseEntity<String> put(@RequestBody @Valid ContatoPutRequestDto dto) {

		try {

			ContatoRepository contatoRepository = new ContatoRepository();
			Contato contato = contatoRepository.findById(dto.getId());

			if (contato == null)
				return ResponseEntity.status(400).body("Contato não encontrado. Verifique o ID informado.");

			CategoriaRepository categoriaRepository = new CategoriaRepository();
			Categoria categoria = categoriaRepository.findById(dto.getCategoriaId());

			if (categoria == null)
				return ResponseEntity.status(400).body("Categoria não encontrada. Verifique o ID informado.");

			contato.setNome(dto.getNome());
			contato.setTelefone(dto.getTelefone());
			contato.setCategoria(categoria);

			contatoRepository.update(contato);

			return ResponseEntity.status(200).body("Contato atualizado com sucesso.");

		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<String> delete(@PathVariable("id") UUID id) {

		try {

			ContatoRepository contatoRepository = new ContatoRepository();
			Contato contato = contatoRepository.findById(id);

			if (contato == null)
				return ResponseEntity.status(400).body("Contato não encontrado. Verifique o ID informado.");

			contatoRepository.delete(contato);

			return ResponseEntity.status(200).body("Contato excluído com sucesso.");
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity<List<Contato>> getAll() throws Exception {

		try {
			ContatoRepository contatoRepository = new ContatoRepository();
			List<Contato> contatos = contatoRepository.findAll();

			if (contatos.size() == 0)
				return ResponseEntity.status(204).body(null);

			return ResponseEntity.status(200).body(contatos);
		} catch (Exception e) {
			return ResponseEntity.status(500).body(null);
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<Contato> getById(@PathVariable("id") UUID id) throws Exception {

		try {
			ContatoRepository contatoRepository = new ContatoRepository();
			Contato contato = contatoRepository.findById(id);

			if (contato == null)
				return ResponseEntity.status(204).body(null);

			return ResponseEntity.status(200).body(contato);
		} catch (Exception e) {
			return ResponseEntity.status(500).body(null);
		}
	}
}
