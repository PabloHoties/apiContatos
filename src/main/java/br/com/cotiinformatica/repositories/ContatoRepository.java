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
import br.com.cotiinformatica.entities.Contato;
import br.com.cotiinformatica.factories.ConnectionFactory;

@Repository
public class ContatoRepository {
	
	@Autowired
	private ConnectionFactory connectionFactory;

	public void insert(Contato contato) throws Exception {
		Connection connection = connectionFactory.getConnection();

		PreparedStatement statement = connection
				.prepareStatement("INSERT INTO contato(id, nome, telefone, categoria_id) VALUES (?,?,?,?)");

		statement.setObject(1, contato.getId());
		statement.setString(2, contato.getNome());
		statement.setString(3, contato.getTelefone());
		statement.setObject(4, contato.getCategoria().getId());
		statement.execute();

		connection.close();
	}

	public void update(Contato contato) throws Exception {
		Connection connection = connectionFactory.getConnection();

		PreparedStatement statement = connection
				.prepareStatement("UPDATE contato SET nome=?, telefone=?, categoria_id=? WHERE id=?");

		statement.setString(1, contato.getNome());
		statement.setString(2, contato.getTelefone());
		statement.setObject(3, contato.getCategoria().getId());
		statement.setObject(4, contato.getId());
		statement.execute();

		connection.close();
	}

	public void delete(Contato contato) throws Exception {
		Connection connection = connectionFactory.getConnection();

		PreparedStatement statement = connection.prepareStatement("DELETE FROM contato WHERE id=?");

		statement.setObject(1, contato.getId());
		statement.execute();

		connection.close();
	}

	public List<Contato> findAll() throws Exception {
		Connection connection = connectionFactory.getConnection();

		PreparedStatement statement = connection
				.prepareStatement("SELECT co.id, co.nome, co.telefone, ca.id as idcategoria, ca.nome AS nomecategoria "
						+ "FROM contato co INNER JOIN categoria ca ON ca.id = co.categoria_id " + "ORDER BY co.nome");

		ResultSet resultSet = statement.executeQuery();

		List<Contato> lista = new ArrayList<Contato>();

		while (resultSet.next()) {
			Contato contato = new Contato();
			contato.setCategoria(new Categoria());
			
			contato.setId(UUID.fromString(resultSet.getString("id")));
			contato.setNome(resultSet.getString("nome"));
			contato.setTelefone(resultSet.getString("telefone"));
			contato.getCategoria().setId(UUID.fromString(resultSet.getString("idcategoria")));
			contato.getCategoria().setNome(resultSet.getString("nomecategoria"));

			lista.add(contato);
		}

		connection.close();
		return lista;
	}

	public Optional<Contato> findById(UUID id) throws Exception {
		Connection connection = connectionFactory.getConnection();

		PreparedStatement statement = connection
				.prepareStatement("SELECT co.id, co.nome, co.telefone, ca.id AS idcategoria, ca.nome AS nomecategoria "
						+ "FROM contato co INNER JOIN categoria ca on ca.id = co.categoria_id " + "WHERE co.id = ?");

		statement.setObject(1, id);
		ResultSet resultSet = statement.executeQuery();

		Contato contato = null;

		if (resultSet.next()) {
			contato = new Contato();
			contato.setCategoria(new Categoria());
			contato.setId(UUID.fromString(resultSet.getString("id")));
			contato.setNome(resultSet.getString("nome"));
			contato.setTelefone(resultSet.getString("telefone"));
			contato.getCategoria().setId(UUID.fromString(resultSet.getString("idcategoria")));
			contato.getCategoria().setNome(resultSet.getString("nomecategoria"));
		}
		connection.close();
		return Optional.ofNullable(contato);
	}
}
