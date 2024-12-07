package br.com.cotiinformatica.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.cotiinformatica.dtos.ContatoRequest;
import br.com.cotiinformatica.dtos.ContatoResponse;
import br.com.cotiinformatica.entities.Categoria;
import br.com.cotiinformatica.entities.Contato;
import br.com.cotiinformatica.repositories.CategoriaRepository;
import br.com.cotiinformatica.repositories.ContatoRepository;

@Service
public class ContatoService {

	@Autowired
	private ContatoRepository contatoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ModelMapper modelMapper;

	public ContatoResponse criar(ContatoRequest request) throws Exception {

		Contato contato = new Contato();

		contato.setId(UUID.randomUUID());
		contato.setNome(request.getNome());
		contato.setTelefone(request.getTelefone());

		Categoria categoria = categoriaRepository.findById(request.getCategoriaId()).orElseThrow(
				() -> new OpenApiResourceNotFoundException("Categoria não encontrada. Verifique o ID informado."));

		contato.setCategoria(categoria);

		contatoRepository.insert(contato);

		return modelMapper.map(contato, ContatoResponse.class);
	}

	public ContatoResponse atualizar(UUID id, ContatoRequest request) throws Exception {

		Contato contato = contatoRepository.findById(id).orElseThrow(
				() -> new OpenApiResourceNotFoundException("Contato não encontrado. Verifique o ID informado."));

		Categoria categoria = categoriaRepository.findById(request.getCategoriaId()).orElseThrow(
				() -> new OpenApiResourceNotFoundException("Categoria não encontrada. Verifique o ID informado."));

		contato.setNome(request.getNome());
		contato.setTelefone(request.getTelefone());
		contato.setCategoria(categoria);

		contatoRepository.update(contato);

		return modelMapper.map(contato, ContatoResponse.class);
	}

	public void excluir(UUID id) throws Exception {

		Contato contato = contatoRepository.findById(id).orElseThrow(
				() -> new OpenApiResourceNotFoundException("Contato não encontrado. Verifique o ID informado."));

		contatoRepository.delete(contato);
	}

	public ResponseEntity<List<ContatoResponse>> consultar() throws Exception {

		List<Contato> contatos = contatoRepository.findAll();

		if (contatos.isEmpty())
			return ResponseEntity.noContent().build();

		return ResponseEntity.ok(contatos.stream().map(contato -> modelMapper.map(contato, ContatoResponse.class))
				.collect(Collectors.toList()));
	}

	public ContatoResponse obterPorId(UUID id) throws Exception {

		Contato contato = contatoRepository.findById(id).orElseThrow(
				() -> new OpenApiResourceNotFoundException("Contato não encontrado. Verifique o ID informado."));

		return modelMapper.map(contato, ContatoResponse.class);
	}
}
