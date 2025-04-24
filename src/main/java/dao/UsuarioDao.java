package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import connection.ConnectionFactory;
import model.Endereco;
import model.Usuario;
import model.enums.Perfil;
import model.enums.Sexo;

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

		// Mais leve e rápido ⚡ Só quer saber se existe ou não
		String sql = "SELECT 1 FROM usuario WHERE UPPER(login) = UPPER(?) AND senha = ?";

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

	/* É necessário apenas saber quem cadastrou identificando pelo usuário logado */
	public void salvar(Usuario obj, Long idUsuarioLogado) throws DaoException {

		String insertSql = "INSERT INTO usuario (nome, email, login, senha, usuario_id, perfil_id, sexo) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement pstmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, obj.getNome());
			pstmt.setString(2, obj.getEmail());
			pstmt.setString(3, obj.getLogin());
			pstmt.setString(4, obj.getSenha());
			pstmt.setLong(5, idUsuarioLogado);
			pstmt.setInt(6, obj.getPerfil().getId());
			pstmt.setString(7, String.valueOf(obj.getSexo().getSigla()));

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

	/* É necessário apenas saber quem cadastrou identificando pelo usuário logado */
	public void salvarComEndereco(Usuario obj, Long idUsuarioLogado) throws DaoException {

		String insertSql = "INSERT INTO usuario (nome, email, login, senha, usuario_id, perfil_id, sexo, endereco_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			// a conexão precisa ser a mesma para o rollback funcionar
			EnderecoDao enderecoDao = new EnderecoDao(connection);
			enderecoDao.salvar(obj.getEndereco());

			try (PreparedStatement pstmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
				pstmt.setString(1, obj.getNome());
				pstmt.setString(2, obj.getEmail());
				pstmt.setString(3, obj.getLogin());
				pstmt.setString(4, obj.getSenha());
				pstmt.setLong(5, idUsuarioLogado);
				pstmt.setInt(6, obj.getPerfil().getId());
				pstmt.setString(7, String.valueOf(obj.getSexo().getSigla()));
				pstmt.setLong(8, obj.getEndereco().getId());

				// Executa a query
				int linhas = pstmt.executeUpdate();

				if (linhas > 0) {
					// Recupera as chaves geradas (o ID gerado)
					try (ResultSet rs = pstmt.getGeneratedKeys()) {
						if (rs.next()) {
							// Insere o ID na referência do objeto.
							obj.setId(rs.getLong(1));
						}
					}
				}
			}

			// Confirma a transação no banco
			connection.commit();

		} catch (SQLException e) {
			rollback();
			throw new DaoException("Erro ao salvar usuário com endereço: " + e.getMessage(), e);
		}
	}

	public Usuario encontrarPorId(Long id, Long idUsuarioLogado) throws DaoException {

		String sql = "SELECT id, nome, email, login, senha, perfil_id, sexo, endereco_id FROM usuario WHERE id = ? AND admin is false AND usuario_id = ?";

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setLong(1, id);
			pstmt.setLong(2, idUsuarioLogado);

			try (ResultSet rs = pstmt.executeQuery()) {

				if (rs.next()) {
					Usuario usuario = new Usuario();

					usuario.setId(rs.getLong("id"));
					usuario.setNome(rs.getString("nome"));
					usuario.setSexo(Sexo.fromSigla(rs.getString("sexo")));
					usuario.setEmail(rs.getString("email"));
					usuario.setLogin(rs.getString("login"));
					usuario.setSenha(rs.getString("senha"));
					usuario.setPerfil(Perfil.fromId(rs.getInt("perfil_id")));

					return usuario;
				}

			}
		} catch (SQLException e) {
			throw new DaoException(e);
		}

		return null;
	}

	public Usuario encontrarPorIdComEndereco(Long id, Long idUsuarioLogado) throws DaoException {

		String sql = "SELECT id, nome, email, login, senha, perfil_id, sexo, endereco_id FROM usuario WHERE id = ? AND admin is false AND usuario_id = ?";

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setLong(1, id);
			pstmt.setLong(2, idUsuarioLogado);

			try (ResultSet rs = pstmt.executeQuery()) {

				if (rs.next()) {
					Usuario usuario = new Usuario();

					usuario.setId(rs.getLong("id"));
					usuario.setNome(rs.getString("nome"));
					usuario.setSexo(Sexo.fromSigla(rs.getString("sexo")));
					usuario.setEmail(rs.getString("email"));
					usuario.setLogin(rs.getString("login"));
					usuario.setSenha(rs.getString("senha"));
					usuario.setPerfil(Perfil.fromId(rs.getInt("perfil_id")));

					Long enderecoId = rs.getLong("endereco_id");

					if (enderecoId != null && enderecoId > 0) {
						EnderecoDao enderecoDao = new EnderecoDao(connection);
						Endereco endereco = enderecoDao.encontrarPorId(enderecoId);
						usuario.setEndereco(endereco);
					}

					return usuario;
				}

			}

			// Confirma a transação no banco
			connection.commit();

		} catch (SQLException e) {
			throw new DaoException("Erro ao buscar usuário com endereço: " + e.getMessage(), e);
		}

		return null;
	}

	/* Query sem restrição por causa dos admins */
	public Optional<Usuario> encontrarPorLogin(String login) throws DaoException {

		String sql = "SELECT id, nome, email, login, senha, admin, perfil_id, sexo FROM usuario WHERE UPPER(login) = UPPER(?)";

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, login);

			try (ResultSet rs = pstmt.executeQuery()) {

				if (rs.next()) {
					Usuario usuario = new Usuario();

					usuario.setId(rs.getLong("id"));
					usuario.setNome(rs.getString("nome"));
					usuario.setSexo(Sexo.fromSigla(rs.getString("sexo")));
					usuario.setEmail(rs.getString("email"));
					usuario.setLogin(rs.getString("login"));
					usuario.setSenha(rs.getString("senha"));
					usuario.setAdmin(rs.getBoolean("admin"));
					usuario.setPerfil(Perfil.fromId(rs.getInt("perfil_id")));

					return Optional.of(usuario);
				}

			}
		} catch (SQLException e) {
			throw new DaoException(e);
		}

		return Optional.empty();
	}

	public Optional<Usuario> encontrarPorLogin(String login, Long idUsuarioLogado) throws DaoException {

		String sql = "SELECT id, nome, email, login, senha FROM usuario, perfil_id, sexo WHERE UPPER(login) = UPPER(?) AND admin is false AND usuario_id = ?";

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, login);
			pstmt.setLong(2, idUsuarioLogado);

			try (ResultSet rs = pstmt.executeQuery()) {

				if (rs.next()) {
					Usuario usuario = new Usuario();

					usuario.setId(rs.getLong("id"));
					usuario.setNome(rs.getString("nome"));
					usuario.setSexo(Sexo.fromSigla(rs.getString("sexo")));
					usuario.setEmail(rs.getString("email"));
					usuario.setLogin(rs.getString("login"));
					usuario.setSenha(rs.getString("senha"));
					usuario.setPerfil(Perfil.fromId(rs.getInt("perfil_id")));

					return Optional.of(usuario);
				}

			}
		} catch (SQLException e) {
			throw new DaoException(e);
		}

		return Optional.empty();
	}

	public List<Usuario> encontrarPorNome(String nome, Long idUsuarioLogado) throws DaoException {

		String sql = "SELECT id, nome, email, login, senha, perfil_id, sexo FROM usuario WHERE UPPER(nome) LIKE CONCAT('%',UPPER(?),'%') AND admin is false AND usuario_id = ?";

		List<Usuario> usuarios = new ArrayList<>();

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, nome);
			pstmt.setLong(2, idUsuarioLogado);

			try (ResultSet rs = pstmt.executeQuery()) {

				while (rs.next()) {
					Usuario usuario = new Usuario();

					usuario.setId(rs.getLong("id"));
					usuario.setNome(rs.getString("nome"));
					usuario.setSexo(Sexo.fromSigla(rs.getString("sexo")));
					usuario.setEmail(rs.getString("email"));
					usuario.setLogin(rs.getString("login"));
					usuario.setPerfil(Perfil.fromId(rs.getInt("perfil_id")));

					usuarios.add(usuario);
				}
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		}

		return usuarios;
	}

	public List<Usuario> encontrarTudo(Long idUsuarioLogado) throws DaoException {

		String sql = "SELECT id, nome, email, login FROM usuario WHERE admin is false AND usuario_id = ? ORDER BY id";

		List<Usuario> usuarios = new ArrayList<>();

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setLong(1, idUsuarioLogado);

			try (ResultSet rs = pstmt.executeQuery()) {

				while (rs.next()) {
					Usuario usuario = new Usuario();

					usuario.setId(rs.getLong("id"));
					usuario.setNome(rs.getString("nome"));
					usuario.setEmail(rs.getString("email"));
					usuario.setLogin(rs.getString("login"));

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

		String sql = "UPDATE usuario SET nome = ?, email = ?, login = ?, senha = ?, perfil_id = ?, sexo = ? WHERE id = ?";

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, obj.getNome());
			pstmt.setString(2, obj.getEmail());
			pstmt.setString(3, obj.getLogin());
			pstmt.setString(4, obj.getSenha());
			pstmt.setInt(5, obj.getPerfil().getId());
			pstmt.setString(6, String.valueOf(obj.getSexo().getSigla()));
			pstmt.setLong(7, obj.getId());

			// Executa a query
			pstmt.executeUpdate();

			// Confirma a transação no banco
			connection.commit();

		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	public void atualizarComEndereco(Usuario obj) throws DaoException {

		String sql = "UPDATE usuario SET nome = ?, email = ?, login = ?, senha = ?, perfil_id = ?, sexo = ? WHERE id = ?";

		try {
			// Atualiza o endereço
			EnderecoDao enderecoDao = new EnderecoDao(connection);
			enderecoDao.atualizar(obj.getEndereco());

			 // Atualiza o usuário
			try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
				pstmt.setString(1, obj.getNome());
				pstmt.setString(2, obj.getEmail());
				pstmt.setString(3, obj.getLogin());
				pstmt.setString(4, obj.getSenha());
				pstmt.setInt(5, obj.getPerfil().getId());
				pstmt.setString(6, String.valueOf(obj.getSexo().getSigla()));
				pstmt.setLong(7, obj.getId());

				// Executa a query
				pstmt.executeUpdate();

				// Confirma a transação no banco
				connection.commit();

			}
		} catch (SQLException e) {
			rollback();
			throw new DaoException("Erro ao atualizar usuário com endereço: " + e.getMessage(), e);
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

	private void rollback() throws DaoException {
		try {
			connection.rollback();
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}
}
