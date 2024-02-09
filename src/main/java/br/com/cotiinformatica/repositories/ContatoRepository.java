package br.com.cotiinformatica.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.cotiinformatica.entities.Categoria;
import br.com.cotiinformatica.entities.Contato;
import br.com.cotiinformatica.factories.ConnectionFactory;

public class ContatoRepository {

	public void insert(Contato contato) throws Exception {
		Connection connection = ConnectionFactory.getConnection();

		PreparedStatement statement = connection
				.prepareStatement("insert into contato(id, nome, telefone, categoria_id) values (?,?,?,?)");

		statement.setObject(1, contato.getId());
		statement.setString(2, contato.getNome());
		statement.setString(3, contato.getTelefone());
		statement.setObject(4, contato.getCategoria().getId());
		statement.execute();

		connection.close();
	}

	public void update(Contato contato) throws Exception {
		Connection connection = ConnectionFactory.getConnection();

		PreparedStatement statement = connection
				.prepareStatement("update contato set nome=?, telefone=?, categoria_id=? where id=?");

		statement.setString(1, contato.getNome());
		statement.setString(2, contato.getTelefone());
		statement.setObject(3, contato.getCategoria().getId());
		statement.setObject(4, contato.getId());
		statement.execute();

		connection.close();
	}

	public void delete(Contato contato) throws Exception {
		Connection connection = ConnectionFactory.getConnection();

		PreparedStatement statement = connection.prepareStatement("delete from contato where id=?");

		statement.setObject(1, contato.getId());
		statement.execute();

		connection.close();
	}

	public List<Contato> findAll() throws Exception {
		Connection connection = ConnectionFactory.getConnection();

		PreparedStatement statement = connection.prepareStatement("select * from contato order by nome");

		ResultSet resultSet = statement.executeQuery();

		List<Contato> lista = new ArrayList<Contato>();

		while (resultSet.next()) {
			Contato contato = new Contato();
			contato.setCategoria(new Categoria());

			contato.setId(UUID.fromString(resultSet.getString("id")));
			contato.setNome(resultSet.getString("nome"));
			contato.setTelefone(resultSet.getString("telefone"));
			contato.getCategoria().setId(UUID.fromString(resultSet.getString("categoria_id")));

			lista.add(contato);
		}

		connection.close();
		return lista;
	}
	
	public Contato findById(UUID id) throws Exception {
		Connection connection = ConnectionFactory.getConnection();
		
		PreparedStatement statement = connection.prepareStatement("select * from contato where id=?");
		
		statement.setObject(1, id);
		ResultSet resultSet = statement.executeQuery();
		
		Contato contato = null;
		
		if (resultSet.next()) {
			contato = new Contato();
			contato.setCategoria(new Categoria());
			contato.setId(UUID.fromString(resultSet.getString("id")));
			contato.setNome(resultSet.getString("nome"));
			contato.setTelefone(resultSet.getString("telefone"));
			contato.getCategoria().setId(UUID.fromString(resultSet.getString("categoria_id")));
		}
		connection.close();
		return contato;
	}
}
