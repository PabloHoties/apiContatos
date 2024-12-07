package br.com.cotiinformatica.dtos;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CategoriaResponse {

	@Schema(description = "ID da categoria", example = "281019ce-edf8-4bb6-9cfc-9030b4f6485b")
	private UUID id;
	
	@Schema(description = "Nome da categoria", example = "Categoria pessoa física")
	private String nome;
}
