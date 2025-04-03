package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.ConnectionFactory;
import model.Usuario;

public class UsuarioDao implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * transient - impede que o atributo seja serializado (salvo em arquivos,
	 * sessão, etc.).
	 */
	private transient Connection connection;

	/**
	 * Construtor que inicializa a conexão com o banco de dados.
	 */
	public UsuarioDao() {
		this.connection = ConnectionFactory.getConnection();
	}

	public boolean validarAutenticacao(Usuario usuario) throws DaoException {
		String sql = "SELECT login, senha FROM usuario WHERE login = ? AND senha = ?";

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, usuario.getLogin());
			pstmt.setString(2, usuario.getSenha());

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

	public void salvar(Usuario obj) throws DaoException {

		String insertSql = "INSERT INTO usuario (nome, email, login, senha) VALUES ( ?, ?, ?, ?)";

		try (PreparedStatement pstmt = connection.prepareStatement(insertSql)) {
			pstmt.setString(1, obj.getNome());
			pstmt.setString(2, obj.getEmail());
			pstmt.setString(3, obj.getLogin());
			pstmt.setString(4, obj.getSenha());
			
			// Executa a query
			pstmt.executeUpdate();
			
			//Confirma a transação no banco
			connection.commit();

		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}
}
