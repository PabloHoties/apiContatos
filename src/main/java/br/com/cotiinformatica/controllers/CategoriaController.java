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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/api/categorias")
@Tag(name = "Categorias", description = "Operações de consultas de categorias")
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;

	@Operation(summary = "Consulta categorias", description = "Retorna uma lista contendo as categorias que um contato pode possuir.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sucesso na obtenção da lista de categorias") })
	@GetMapping
	public ResponseEntity<List<CategoriaResponse>> getAll() throws Exception {
		return ResponseEntity.ok(categoriaService.consultar());
	}

	@Operation(summary = "Consulta contato pelo UUID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Categoria encontrada com sucesso"),
			@ApiResponse(responseCode = "404", description = "Categoria não encontrada") })
	@GetMapping("{id}")
	public ResponseEntity<CategoriaResponse> getById(@PathVariable("id") UUID id) throws Exception {
		return ResponseEntity.ok(categoriaService.obterPorId(id));
	}
}
