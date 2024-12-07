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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/contatos")
@Tag(name = "Contatos", description = "Operações de gerenciamento de contatos")
public class ContatoController {

	@Autowired
	private ContatoService contatoService;

	@Operation(summary = "Cadastro de contato")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Sucesso no cadastro do contato"),
			@ApiResponse(responseCode = "400", description = "Dados inválidos"),
			@ApiResponse(responseCode = "404", description = "Categoria não encontrada") })
	@PostMapping
	public ResponseEntity<ContatoResponse> post(@RequestBody @Valid ContatoRequest request) throws Exception {
		return ResponseEntity.status(201).body(contatoService.criar(request));
	}

	@Operation(summary = "Edição de contato")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Sucesso na edição do contato"),
			@ApiResponse(responseCode = "400", description = "Dados inválidos"),
			@ApiResponse(responseCode = "404", description = "Contato ou categoria não encontrados") })
	@PutMapping("{id}")
	public ResponseEntity<ContatoResponse> put(@PathVariable UUID id, @RequestBody @Valid ContatoRequest request)
			throws Exception {
		return ResponseEntity.status(200).body(contatoService.atualizar(id, request));
	}

	@Operation(summary = "Exclusão de contato")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Sucesso na exclusão do contato"),
			@ApiResponse(responseCode = "404", description = "Contato não encontrado") })
	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") UUID id) throws Exception {
		contatoService.excluir(id);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Consulta contatos", description = "Retorna os dados dos contatos e de suas categorias associadas. É ordenado pelo nome do contato.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Sucesso na obtenção da lista de contatos"),
			@ApiResponse(responseCode = "404", description = "Contatos não encontrados") })
	@GetMapping
	public ResponseEntity<List<ContatoResponse>> getAll() throws Exception {
		return contatoService.consultar();
	}

	@Operation(summary = "Consulta contato pelo UUID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Contato encontrado com sucesso"),
			@ApiResponse(responseCode = "404", description = "Contato não encontrado") })
	@GetMapping("{id}")
	public ResponseEntity<ContatoResponse> getById(@PathVariable("id") UUID id) throws Exception {
		return ResponseEntity.status(200).body(contatoService.obterPorId(id));
	}
}
