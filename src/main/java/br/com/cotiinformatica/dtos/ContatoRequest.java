package br.com.cotiinformatica.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ContatoRequest {

	@Size(min = 8, max = 100, message = "Por favor, informe um nome de 8 a 100 caracteres.")
	@NotBlank(message = "Por favor, informe o nome do contato.")
	private String nome;

	@Pattern(regexp = "\\(\\d{2}\\)\\s\\d{5}-\\d{4}", message = "Por favor, informe o telefone no formato: '(99) 99999-9999'.")
	@NotBlank(message = "Por favor, informe o telefone do contato.")
	private String telefone;

	@NotNull(message = "Por favor, informe o id da categoria.")
	private UUID categoriaId;
}
