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
import model.Telefone;
import model.Usuario;

public class TelefoneDao implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * transient - impede que o atributo seja serializado (salvo em arquivos,
	 * sessão, etc.).
	 */
	private transient Connection connection;

	private TelefoneDao() {
		this.connection = ConnectionFactory.getConnection();
	}

	/**
	 * Inserir novo telefone
	 */

	public void inserir(Telefone telefone) throws DaoException {
		String sql = "INSERT INTO telefone (numero, usuario_id, usuario_inclusao_id) VALUES (?, ?, ?)";
		try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, telefone.getNumero());
			pstmt.setLong(2, telefone.getUsuario().getId());
			pstmt.setLong(3, telefone.getUsuario().getId());

			// Executa a query
			int linhas = pstmt.executeUpdate();

			if (linhas > 0) {
				// Recupera as chaves geradas (o ID gerado)
				try (ResultSet rs = pstmt.getGeneratedKeys()) {
					if (rs.next()) {
						// Insere o ID na referência do objeto.
						telefone.setId(rs.getLong(1));
					}
				}
			}

			// Confirma a transação no banco
			connection.commit();

		} catch (SQLException e) {
			throw new DaoException("Erro ao inserir telefone: " + e.getMessage(), e);
		}
	}

	/**
	 * Deletar telefone por ID
	 */
	public void deletarPorId(Long id) throws DaoException {
		String sql = "DELETE FROM telefone WHERE id = ?";

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setLong(1, id);

			// Executa a query
			pstmt.executeUpdate();

			// Confirma a transação no banco
			connection.commit();
		} catch (SQLException e) {
			throw new DaoException("Erro ao deletar telefone por ID: " + e.getMessage(), e);
		}
	}

	/**
	 * Listar todos os telefones de um usuário
	 * 
	 */
	public List<Telefone> listarTodosPorUsuarioId(Long usuarioId) throws DaoException {
		String sql = "SELECT id, numero, usuario_id, usuario_inclusao_id FROM telefone WHERE usuario_id = ? ORDER BY id";

		List<Telefone> telefones = new ArrayList<>();

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setLong(1, usuarioId);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Telefone telefone = new Telefone();

					telefone.setId(rs.getLong("id"));
					telefone.setNumero(rs.getString("numero"));
					telefone.setUsuario(new Usuario(rs.getLong("usuario_id"))); // obs: O objeto Usuario será carregado por completo em outro momento, se necessário.
					telefone.setUsuarioInclusao(new Usuario(rs.getLong("usuario_inclusao_id"))); // obs: O objeto Usuario será carregado por completo em outro momento, se necessário.

					telefones.add(telefone);
				}
			}

		} catch (SQLException e) {
			throw new DaoException("Erro ao listar telefones por usuário: " + e.getMessage(), e);
		}

		return telefones;
	}
}
