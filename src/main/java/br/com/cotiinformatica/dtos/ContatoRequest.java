package br.com.cotiinformatica.dtos;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ContatoRequest {

	@Schema(description = "Nome completo do contato, 8 a 100 caracteres", example = "João da Silva")
	@Size(min = 8, max = 100, message = "Por favor, informe um nome de 8 a 100 caracteres.")
	@NotBlank(message = "Por favor, informe o nome do contato.")
	private String nome;

	@Schema(description = "Telefone do contato", example = "(21) 98765-4321")
	@Pattern(regexp = "\\(\\d{2}\\)\\s\\d{5}-\\d{4}", message = "Por favor, informe o telefone no formato: '(99) 99999-9999'.")
	@NotBlank(message = "Por favor, informe o telefone do contato.")
	private String telefone;

	@Schema(description = "ID da categoria, deve ser um UUID", example = "281019ce-edf8-4bb6-9cfc-9030b4f6485b")
	@NotNull(message = "Por favor, informe o id da categoria.")
	private UUID categoriaId;
}
