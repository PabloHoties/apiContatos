package br.com.cotiinformatica.factories;

import java.sql.Connection;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConnectionFactory {

    @Autowired
    private DataSource dataSource;

    public Connection getConnection() throws Exception {
        return dataSource.getConnection();
    }
}
