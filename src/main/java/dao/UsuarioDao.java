package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

		String sql = "SELECT 1 FROM usuario WHERE UPPER(login) = UPPER(?) AND senha = ?";

		// Mais leve e rápido ⚡ Só quer saber se existe ou não
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, usuario.getLogin());
			pstmt.setString(2, usuario.getSenha());

			try (ResultSet rs = pstmt.executeQuery()) {

				return rs.next(); // Será autenticado se for encontrado; caso contrário, não será autenticado.
			}

		} catch (SQLException e) {
			throw new DaoException(e);
		}
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

		String sql = "SELECT id, nome, email, login, senha FROM usuario WHERE id = ? AND admin is false";

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

	public List<Usuario> encontrarPorNome(String nome) throws DaoException {

		String sql = "SELECT id, nome, email, login, senha FROM usuario WHERE UPPER(nome) LIKE CONCAT('%',UPPER(?),'%') AND admin is false";

		List<Usuario> usuarios = new ArrayList<>();

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, nome);

			try (ResultSet rs = pstmt.executeQuery()) {

				while (rs.next()) {
					Usuario usuario = new Usuario();

					usuario.setId(rs.getLong("id"));
					usuario.setNome(rs.getString("nome"));
					usuario.setEmail(rs.getString("email"));
					usuario.setLogin(rs.getString("login"));
					// usuario.setSenha(rs.getString("senha"));

					usuarios.add(usuario);
				}
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		}

		return usuarios;
	}
	
	public List<Usuario> encontrarTudo() throws DaoException {

		String sql = "SELECT id, nome, email, login, senha FROM usuario WHERE admin is false ORDER BY id";

		List<Usuario> usuarios = new ArrayList<>();

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

			try (ResultSet rs = pstmt.executeQuery()) {

				while (rs.next()) {
					Usuario usuario = new Usuario();

					usuario.setId(rs.getLong("id"));
					usuario.setNome(rs.getString("nome"));
					usuario.setEmail(rs.getString("email"));
					usuario.setLogin(rs.getString("login"));
					// usuario.setSenha(rs.getString("senha"));

					usuarios.add(usuario);
				}
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		}

		return usuarios;
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

	public void atualizar(Usuario obj) throws DaoException {

		String sql = "UPDATE usuario SET nome = ?, email = ?, login = ?, senha = ? WHERE id = ?";

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, obj.getNome());
			pstmt.setString(2, obj.getEmail());
			pstmt.setString(3, obj.getLogin());
			pstmt.setString(4, obj.getSenha());
			pstmt.setLong(5, obj.getId());

			// Executa a query
			pstmt.executeUpdate();

			// Confirma a transação no banco
			connection.commit();

		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	public void deletarPorId(Long id) throws DaoException {

		String deleteSql = "DELETE FROM usuario WHERE id = ? AND admin is false";

		try (PreparedStatement pstmt = connection.prepareStatement(deleteSql)) {
			pstmt.setLong(1, id);

			// Executa a query
			pstmt.executeUpdate();

			// Confirma a transação no banco
			connection.commit();

		} catch (SQLException e) {
			throw new DaoException("Erro ao remover um registro");
		}
	}
}
