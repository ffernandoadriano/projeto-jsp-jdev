package dao;

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

public class ImagemDao {

	private Connection connection;

	/**
	 * Construtor que inicializa a conexão com o banco de dados.
	 */
	public ImagemDao() {
		this.connection = ConnectionFactory.getConnection();
	}

	/**
	 * Insere uma nova imagem no banco de dados e define o ID gerado no objeto.
	 *
	 * @param imagem Objeto {@link Imagem} contendo os dados a serem persistidos.
	 * @throws DaoException Se ocorrer algum erro ao executar a operação de
	 *                      inserção.
	 */
	public void inserir(Imagem imagem) throws DaoException {

		String insertSql = "INSERT INTO imagem (usuario_id, imagem, extensao, tipo, data_upload) VALUES (?, ?, ?, ?, ?)";

		try (PreparedStatement pstmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setLong(1, imagem.getUsuario().getId());
			pstmt.setBytes(2, imagem.getImage());
			pstmt.setString(3, imagem.getExtensao());
			pstmt.setString(4, imagem.getTipo());
			pstmt.setObject(5, imagem.getDataUpload());

			// Executa a query
			int linhas = pstmt.executeUpdate();

			// Verifica se inseriu alguma linha
			if (linhas > 0) {

				// Recupera as chaves geradas (o ID gerado)
				try (ResultSet rs = pstmt.getGeneratedKeys()) {

					if (rs.next()) {
						Long idGerado = rs.getLong("id");
						// Insere o ID na referência do objeto.
						imagem.setId(idGerado);
					}
				}
			}

			// Confirma a transação no banco
			connection.commit();

		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	/**
	 * Atualiza os dados da imagem associada ao usuário no banco de dados.
	 *
	 * @param imagem Objeto {@link Imagem} com os dados atualizados.
	 * @throws DaoException Se ocorrer erro durante a atualização.
	 * @throws SQLException Se houver falha ao aplicar ou desfazer a transação.
	 */
	public void atualizar(Imagem imagem) throws DaoException, SQLException {

		String sql = "UPDATE imagem SET imagem = ?, extensao = ?, tipo = ? , data_upload = ? WHERE usuario_id = ?";

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setBytes(1, imagem.getImage());
			pstmt.setString(2, imagem.getExtensao());
			pstmt.setString(3, imagem.getTipo());
			pstmt.setObject(4, imagem.getDataUpload());
			pstmt.setLong(5, imagem.getUsuario().getId());

			// Executa a query
			pstmt.executeUpdate();

			// Confirma a transação no banco
			connection.commit();

		} catch (SQLException e) {
			connection.rollback();
			throw new DaoException(e);
		}
	}

	/**
	 * Verifica se o usuário possui uma imagem do tipo "perfil" cadastrada.
	 *
	 * @param usuarioId ID do usuário a ser verificado.
	 * @return {@code true} se existir uma imagem do tipo "perfil", {@code false}
	 *         caso contrário.
	 * @throws DaoException Se ocorrer erro ao acessar o banco de dados.
	 */
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

	/**
	 * Busca a imagem de um usuário com base no ID do usuário e no tipo da imagem.
	 *
	 * @param usuarioId ID do usuário.
	 * @param tipo      Tipo da imagem (ex: "perfil", "capa", etc.).
	 * @return Objeto {@link Imagem} correspondente, ou {@code null} se não
	 *         encontrado.
	 * @throws DaoException Se ocorrer erro ao executar a consulta.
	 */
	public Imagem buscarPorUsuarioIdETipo(Long usuarioId, String tipo) throws DaoException {

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

	/**
	 * Converte um array de bytes da imagem para uma string codificada em Base64, no
	 * formato apropriado para exibição em HTML.
	 *
	 * @param image    Array de bytes da imagem.
	 * @param extensao Extensão da imagem (ex: "png", "jpg").
	 * @return String em formato Base64 para uso em <img src="..."> ou {@code null}
	 *         se a imagem for {@code null}.
	 */
	private String convertImageByteToBase64(byte[] image, String extensao) {
		if (image != null) {
			/* Formato para visulizar a imagem no jsp */
			return String.format("data:image/%s;base64,%s", extensao, Base64.getEncoder().encodeToString(image));
		} else {
			return null;
		}
	}

}
