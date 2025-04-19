package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import connection.ConnectionFactory;
import model.Imagem;

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

	public void salvar(Imagem obj) throws DaoException {

		String insertSql = "INSERT INTO imagem (usuario_id, imagem, extensao, tipo, data_upload) VALUES (?, ?, ?, ?, ?)";

		try (PreparedStatement pstmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setLong(1, obj.getUsuario().getId());
			pstmt.setBytes(2, obj.getImage());
			pstmt.setString(3, obj.getExtensao());
			pstmt.setString(4, obj.getTipo());
			pstmt.setObject(5, obj.getDataUpload());

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

	public void atualizar(Imagem obj) throws DaoException {

		String sql = "UPDATE imagem SET imagem = ?, extensao = ?, tipo = ? , data_upload = ? WHERE usuario_id = ?";

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setBytes(1, obj.getImage());
			pstmt.setString(2, obj.getExtensao());
			pstmt.setString(3, obj.getTipo());
			pstmt.setObject(4, obj.getDataUpload());
			pstmt.setLong(5, obj.getUsuario().getId());

			// Executa a query
			pstmt.executeUpdate();

			// Confirma a transação no banco
			connection.commit();

		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	public boolean existeFotoPerfil(Long usuarioId) throws DaoException {

		// Mais leve e rápido ⚡ Só quer saber se existe ou não
		String sql = "SELECT 1 FROM imagem WHERE usuario_id = ? AND tipo = 'perfil'";

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setLong(1, usuarioId);

			try (ResultSet rs = pstmt.executeQuery()) {

				return rs.next(); // true se encontrou, false se não
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

}
