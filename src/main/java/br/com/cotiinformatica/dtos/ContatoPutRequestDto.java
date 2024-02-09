package br.com.cotiinformatica.dtos;

import java.util.UUID;

import lombok.Data;

@Data
public class ContatoPutRequestDto {

	UUID id;
	String nome, telefone;
	UUID categoriaId;
}
