package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Endereco;

public class EnderecoDao implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * transient - impede que o atributo seja serializado (salvo em arquivos,
	 * sessão, etc.).
	 */
	private transient Connection connection;

	public EnderecoDao(Connection connection) {
		this.connection = connection;
	}

	public void salvar(Endereco endereco) throws DaoException {

		String sql = "INSERT INTO endereco (cep, rua, numero, bairro, cidade, estado, uf) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, endereco.getCep());
			pstmt.setString(2, endereco.getRua());
			pstmt.setString(3, endereco.getNumero());
			pstmt.setString(4, endereco.getBairro());
			pstmt.setString(5, endereco.getCidade());
			pstmt.setString(6, endereco.getEstado());
			pstmt.setString(7, endereco.getUf());

			// Executa a query
			int linhas = pstmt.executeUpdate();

			// Verifica se inseriu alguma linha
			if (linhas > 0) {

				// Recupera as chaves geradas (o ID gerado)
				try (ResultSet rs = pstmt.getGeneratedKeys()) {
					if (rs.next()) {
						// Insere o ID na referência do objeto.
						endereco.setId(rs.getLong(1));
					}
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Ocorreu um erro ao tentar salvar o endereço. " + e.getMessage(), e);
		}
	}

	public void atualizar(Endereco endereco) throws DaoException {

		String updateSql = "UPDATE endereco SET cep=?, rua=?, numero=?, bairro=?, cidade=?, estado=?, uf=? WHERE  id=?";

		try (PreparedStatement pstmt = connection.prepareStatement(updateSql)) {
			pstmt.setString(1, endereco.getCep());
			pstmt.setString(2, endereco.getRua());
			pstmt.setString(3, endereco.getNumero());
			pstmt.setString(4, endereco.getBairro());
			pstmt.setString(5, endereco.getCidade());
			pstmt.setString(6, endereco.getEstado());
			pstmt.setString(7, endereco.getUf());
			pstmt.setLong(8, endereco.getId());

			// Executa a query
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new DaoException("Erro ao atualizar endereço: " + e.getMessage(), e);
		}

	}

	public Endereco encontrarPorId(Long id) throws DaoException {

		String sql = "SELECT * FROM endereco WHERE id = ?";

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setLong(1, id);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					Endereco endereco = new Endereco();
					endereco.setId(rs.getLong("id"));
					endereco.setCep(rs.getString("cep"));
					endereco.setRua(rs.getString("rua"));
					endereco.setNumero(rs.getString("numero"));
					endereco.setBairro(rs.getString("bairro"));
					endereco.setCidade(rs.getString("cidade"));
					endereco.setEstado(rs.getString("estado"));
					endereco.setUf(rs.getString("uf"));

					return endereco;
				}
			}

		} catch (SQLException e) {
			throw new DaoException("Erro ao buscar endereço: " + e.getMessage(), e);
		}

		return null;

	}

}
