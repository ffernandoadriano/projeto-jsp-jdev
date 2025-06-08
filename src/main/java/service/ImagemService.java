package service;

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

}
