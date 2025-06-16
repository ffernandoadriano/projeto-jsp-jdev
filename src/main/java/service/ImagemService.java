package service;

import java.sql.SQLException;

import dao.DaoException;
import dao.ImagemDao;
import model.Imagem;

/**
 * Classe responsável por fornecer serviços relacionados ao modelo
 * {@link Imagem}.
 */
public class ImagemService {

	private ImagemDao imagemDao;

	/**
	 * Construtor que inicializa o serviço de jogador com uma instância de
	 * {@link ImagemDao}.
	 */
	public ImagemService() {
		this.imagemDao = new ImagemDao();
	}

	/**
	 * Salva ou atualiza uma imagem de perfil no banco de dados, dependendo da
	 * existência prévia da imagem.
	 * <p>
	 * Caso a imagem de perfil já exista, será realizada a operação de atualização.
	 * Caso contrário, a imagem será inserida como nova.
	 *
	 * @param existeFotoPerfil Indica se a imagem de perfil já existe no banco de
	 *                         dados.
	 * @param imagem           Objeto {@link Imagem} contendo os dados da imagem a
	 *                         serem persistidos ou atualizados.
	 * @throws ServiceException Se ocorrer erro durante a inserção ou atualização da
	 *                          imagem.
	 */
	public void salvarOuAtualizarImagem(boolean existeFotoPerfil, Imagem imagem) throws ServiceException {

		if (existeFotoPerfil) {
			atualizar(imagem);
		} else {
			inserir(imagem);
		}

	}

	/**
	 * Insere uma nova imagem no banco de dados e define o ID gerado no objeto.
	 *
	 * @param imagem Objeto {@link Imagem} contendo os dados a serem persistidos.
	 * @throws ServiceException Se ocorrer algum erro ao executar a operação de
	 *                          inserção.
	 */
	public void inserir(Imagem imagem) throws ServiceException {
		try {
			imagemDao.inserir(imagem);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * Atualiza os dados da imagem associada ao usuário no banco de dados.
	 *
	 * @param imagem Objeto {@link Imagem} com os dados atualizados.
	 * @throws ServiceException Se ocorrer erro durante a atualização.
	 */
	public void atualizar(Imagem imagem) throws ServiceException {
		try {
			imagemDao.atualizar(imagem);
		} catch (DaoException | SQLException e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * Busca a imagem de um usuário com base no ID do usuário e no tipo da imagem.
	 *
	 * @param usuarioId ID do usuário.
	 * @param tipo      Tipo da imagem (ex: "perfil", "capa", etc.).
	 * @return Objeto {@link Imagem} correspondente, ou {@code null} se não
	 *         encontrado.
	 * @throws ServiceException Se ocorrer erro ao executar a consulta.
	 */
	public Imagem buscarPorUsuarioIdETipo(Long usuarioId, String tipo) throws ServiceException {
		try {
			return imagemDao.buscarPorUsuarioIdETipo(usuarioId, tipo);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * Verifica se o usuário possui uma imagem do tipo "perfil" cadastrada.
	 *
	 * @param usuarioId ID do usuário a ser verificado.
	 * @return {@code true} se existir uma imagem do tipo "perfil", {@code false}
	 *         caso contrário.
	 * @throws ServiceException Se ocorrer erro ao acessar o banco de dados.
	 */
	public boolean existeFotoPerfil(Long usuarioId) throws ServiceException {
		try {
			return imagemDao.existeFotoPerfil(usuarioId);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

}
