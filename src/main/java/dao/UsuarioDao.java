package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

		String sql = "SELECT login, senha FROM usuario WHERE UPPER(login) = UPPER(?) AND senha = ?";

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

		try (PreparedStatement pstmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, obj.getNome());
			pstmt.setString(2, obj.getEmail());
			pstmt.setString(3, obj.getLogin());
			pstmt.setString(4, obj.getSenha());

			// Executa a query
			int linhasAfetas = pstmt.executeUpdate();

			// Verifica se inseriu alguma linha
			if (linhasAfetas > 0) {

				// Recupera as chaves geradas (o ID gerado)
				try (ResultSet rs = pstmt.getGeneratedKeys()) {

					if (rs.next()) {
						Long idGerado = rs.getLong("id");
						// Insere o ID na referência do objeto.
						obj.setId(idGerado);
					}
				}
			}

			// Confirma a transação no banco
			connection.commit();

		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	public Usuario encontrarPorId(Long id) throws DaoException {

		String sql = "SELECT id, nome, email, login, senha FROM usuario WHERE id = ?";

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setLong(1, id);

			try (ResultSet rs = pstmt.executeQuery()) {

				if (rs.next()) {
					Usuario usuario = new Usuario();

					usuario.setId(rs.getLong("id"));
					usuario.setNome(rs.getString("nome"));
					usuario.setEmail(rs.getString("email"));
					usuario.setLogin(rs.getString("login"));
					usuario.setSenha(rs.getString("senha"));

					return usuario;
				}

			}
		} catch (SQLException e) {
			throw new DaoException(e);
		}

		return null;
	}

	public boolean existeEmail(String email) throws DaoException {

		// Mais leve e rápido ⚡ Só quer saber se existe ou não
		String sql = "SELECT 1 FROM usuario WHERE UPPER(email) = UPPER(?)";

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, email);

			try (ResultSet rs = pstmt.executeQuery()) {

				return rs.next(); // true se encontrou, false se não

			}
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	public boolean existeLogin(String login) throws DaoException {

		// Mais leve e rápido ⚡ Só quer saber se existe ou não
		String sql = "SELECT 1 FROM usuario WHERE UPPER(login) = UPPER(?)";

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, login);

			try (ResultSet rs = pstmt.executeQuery()) {

				return rs.next(); // true se encontrou, false se não

			}
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}
}
