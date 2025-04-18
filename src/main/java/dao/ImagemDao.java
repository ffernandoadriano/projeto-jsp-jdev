package dao;

import java.io.Serializable;
import java.sql.Connection;

import connection.ConnectionFactory;

public class ImagemDao implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * transient - impede que o atributo seja serializado (salvo em arquivos,
	 * sessão, etc.).
	 */
	private transient Connection connection;

	/**
	 * Construtor que inicializa a conexão com o banco de dados.
	 */
	public ImagemDao() {
		this.connection = ConnectionFactory.getConnection();
	}

}
