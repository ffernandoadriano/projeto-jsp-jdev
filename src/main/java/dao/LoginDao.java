package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.ConnectionFactory;
import model.Login;

public class LoginDao implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * transient - impede que o atributo seja serializado (salvo em arquivos,
	 * sessão, etc.).
	 */
	private transient Connection connection;

	/**
	 * Construtor que inicializa a conexão com o banco de dados.
	 */
	public LoginDao() {
		this.connection = ConnectionFactory.getConnection();
	}

	public boolean validarAutenticacao(Login login) throws DaoException {
		String sql = "SELECT login, senha FROM login WHERE login = ? AND senha = ?";

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, login.getLogin());
			pstmt.setString(2, login.getSenha());

			try (ResultSet rs = pstmt.executeQuery()) {

				// caso seja encontrado
				if (rs.next()) {
					return true; /* Autenticado */
				}
			}

		} catch (SQLException e) {
			throw new DaoException(e);
		}

		return false; /* Não Autenticado */
	}
}
