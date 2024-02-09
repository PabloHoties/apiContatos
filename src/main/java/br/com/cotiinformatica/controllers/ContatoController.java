package br.com.cotiinformatica.controllers;

import java.util.List;
import java.util.UUID;

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

@RestController
@RequestMapping(value = "/api/contatos")
public class ContatoController {

	@PostMapping
	public String post(@RequestBody ContatoPostRequestDto dto) {

		try {

			Contato contato = new Contato();

			contato.setId(UUID.randomUUID());
			contato.setNome(dto.getNome());
			contato.setTelefone(dto.getTelefone());

			CategoriaRepository categoriaRepository = new CategoriaRepository();
			Categoria categoria = categoriaRepository.findById(dto.getCategoriaId());

			if (categoria == null)
				throw new Exception("Categoria não encontrada. Verifique o ID informado.");

			contato.setCategoria(categoria);

			ContatoRepository contatoRepository = new ContatoRepository();
			contatoRepository.insert(contato);

			return "Contato cadastrado com sucesso.";

		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@PutMapping
	public String put(@RequestBody ContatoPutRequestDto dto) {

		try {

			ContatoRepository contatoRepository = new ContatoRepository();
			Contato contato = contatoRepository.findById(dto.getId());

			if (contato == null)
				throw new Exception("Contato não encontrado. Verifique o ID informado.");

			CategoriaRepository categoriaRepository = new CategoriaRepository();
			Categoria categoria = categoriaRepository.findById(dto.getCategoriaId());

			if (categoria == null)
				throw new Exception("Categoria não encontrada. Verifique o ID informado.");

			contato.setNome(dto.getNome());
			contato.setTelefone(dto.getTelefone());
			contato.setCategoria(categoria);

			contatoRepository.update(contato);

			return "Contato atualizado com sucesso.";

		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@DeleteMapping("{id}")
	public String delete(@PathVariable("id") UUID id) {

		try {

			ContatoRepository contatoRepository = new ContatoRepository();
			Contato contato = contatoRepository.findById(id);

			if (contato == null)
				throw new Exception("Contato não encontrado. Verifique o ID informado.");

			contatoRepository.delete(contato);

			return "Contato excluído com sucesso.";
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@GetMapping
	public List<Contato> getAll() throws Exception {

		ContatoRepository contatoRepository = new ContatoRepository();
		return contatoRepository.findAll();
	}

	@GetMapping("{id}")
	public Contato getById(@PathVariable("id") UUID id) throws Exception {

		ContatoRepository contatoRepository = new ContatoRepository();
		return contatoRepository.findById(id);
	}
}
