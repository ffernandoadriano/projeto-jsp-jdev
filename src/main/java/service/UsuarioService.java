package service;

import java.util.Optional;

import dao.DaoException;
import dao.UsuarioDao;
import model.Usuario;

/**
 * Classe responsável por fornecer serviços relacionados ao modelo
 * {@link Usuario}.
 */

public class UsuarioService {

	private UsuarioDao usuarioDao;

	/**
	 * Construtor que inicializa o serviço de jogador com uma instância de
	 * {@link UsuarioDao}.
	 */
	public UsuarioService() {
		this.usuarioDao = new UsuarioDao();
	}

	/**
	 * Busca um usuário pelo login (sem restrições de administrador).
	 *
	 * @param login o login do usuário
	 * @return um Optional contendo o usuário, se encontrado
	 * @throws ServiceException em caso de erro
	 */
	public Optional<Usuario> buscarPorLogin(String login) throws ServiceException {
		try {
			return usuarioDao.buscarPorLogin(login);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

}
