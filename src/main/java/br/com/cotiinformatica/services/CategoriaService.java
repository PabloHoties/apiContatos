package br.com.cotiinformatica.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cotiinformatica.dtos.CategoriaResponse;
import br.com.cotiinformatica.entities.Categoria;
import br.com.cotiinformatica.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ModelMapper modelMapper;

	public List<CategoriaResponse> consultar() throws Exception {

		List<Categoria> categorias = categoriaRepository.findAll();

		return categorias.stream()
				.map(categoria -> modelMapper.map(categoria, CategoriaResponse.class)).collect(Collectors.toList());
	}

	public CategoriaResponse obterPorId(UUID id) throws Exception {

		Categoria categoria = categoriaRepository.findById(id).orElseThrow(
				() -> new OpenApiResourceNotFoundException("Categoria não encontrada. Verifique o ID informado."));

		return modelMapper.map(categoria, CategoriaResponse.class);
	}
}
