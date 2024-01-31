package br.com.cotiinformatica.entities;

import java.util.List;
import java.util.UUID;

public class Categoria {

	private UUID id;
	private String nome;
	private List<Contato> contatos;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Contato> getContatos() {
		return contatos;
	}

	public void setContatos(List<Contato> contatos) {
		this.contatos = contatos;
	}
}
