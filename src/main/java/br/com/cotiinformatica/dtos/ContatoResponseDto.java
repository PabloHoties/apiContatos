package br.com.cotiinformatica.dtos;

import java.util.UUID;

import lombok.Data;

@Data
public class ContatoResponseDto {

	private UUID id;
	private String nome;
	private String telefone;
	private CategoriaResponseDto categoria;
}
