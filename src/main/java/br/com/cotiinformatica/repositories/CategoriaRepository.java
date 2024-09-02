package br.com.cotiinformatica.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.cotiinformatica.entities.Categoria;
import br.com.cotiinformatica.factories.ConnectionFactory;

@Repository
public class CategoriaRepository {
	
	@Autowired
	private ConnectionFactory connectionFactory;

	public List<Categoria> findAll() throws Exception {
		Connection connection = connectionFactory.getConnection();

		PreparedStatement statement = connection.prepareStatement(
				"SELECT id, nome FROM categoria ORDER BY nome");

		ResultSet resultSet = statement.executeQuery();

		List<Categoria> lista = new ArrayList<Categoria>();

		while (resultSet.next()) {

			Categoria categoria = new Categoria();

			categoria.setId(UUID.fromString(resultSet.getString("id")));
			categoria.setNome(resultSet.getString("nome"));

			lista.add(categoria);
		}
		connection.close();
		return lista;
	}

	public Optional<Categoria> findById(UUID id) throws Exception {
		Connection connection = connectionFactory.getConnection();

		PreparedStatement statement = connection.prepareStatement("SELECT id, nome FROM categoria WHERE id=?");

		statement.setObject(1, id);
		ResultSet resultSet = statement.executeQuery();

		Categoria categoria = null;

		if (resultSet.next()) {

			categoria = new Categoria();
			categoria.setId(UUID.fromString(resultSet.getString("id")));
			categoria.setNome(resultSet.getString("nome"));
		}
		connection.close();
		return Optional.ofNullable(categoria);
	}
}
