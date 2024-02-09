package br.com.cotiinformatica.dtos;

import java.util.UUID;

import lombok.Data;

@Data
public class ContatoPostRequestDto {

	private String nome, telefone;
	private UUID categoriaId;
}
