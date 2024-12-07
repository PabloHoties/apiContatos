package br.com.cotiinformatica.dtos;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ContatoResponse {

	@Schema(description = "ID do contato", example = "281019ce-edf8-4bb6-9cfc-9030b4f6485b")
	private UUID id;
	
	@Schema(description = "Nome completo do contato", example = "João da Silva")
	private String nome;
	
	@Schema(description = "Telefone do contato", example = "(21) 98765-4321")
	private String telefone;
	
	@Schema(description = "ID e nome da categoria associada ao contato")
	private CategoriaResponse categoria;
}
