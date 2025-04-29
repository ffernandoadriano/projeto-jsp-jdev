package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Base64;

import connection.ConnectionFactory;
import model.Imagem;
import model.Usuario;

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

	public void atualizar(Imagem obj) throws DaoException, SQLException {

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
			connection.rollback();
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

	public Imagem encontrarPorId(Long usuarioId, String tipo) throws DaoException {

		String sql = "SELECT id, usuario_id, imagem, extensao, tipo, data_upload FROM imagem WHERE usuario_id = ? AND tipo = ?";

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setLong(1, usuarioId);
			pstmt.setString(2, tipo);

			try (ResultSet rs = pstmt.executeQuery()) {

				if (rs.next()) {
					Imagem imagem = new Imagem();

					byte[] imagemBytes = rs.getBytes("imagem");
					String extensao = rs.getString("extensao");

					imagem.setId(rs.getLong("id"));
					imagem.setUsuario(new Usuario(rs.getLong("usuario_id")));
					imagem.setImage(imagemBytes);
					imagem.setExtensao(extensao);
					imagem.setTipo(rs.getString("tipo"));
					imagem.setDataUpload(rs.getObject("data_upload", LocalDateTime.class));
					imagem.setImageBase64(convertImageByteToBase64(imagemBytes, extensao));

					return imagem;
				}

			}
		} catch (SQLException e) {
			throw new DaoException(e);
		}

		return null;
	}

	private String convertImageByteToBase64(byte[] image, String extensao) {
		if (image != null) {
			/* Formato para visulizar a imagem no jsp */
			return String.format("data:image/%s;base64,%s", extensao, Base64.getEncoder().encodeToString(image));
		} else {
			return null;
		}
	}

}
