package br.com.cotiinformatica.entities;

import java.util.UUID;

import lombok.Data;

@Data
public class Contato {

	private UUID id;
	private String nome;
	private String telefone;
	private Categoria categoria;
}
