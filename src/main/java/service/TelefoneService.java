package service;

import java.util.List;

import dao.DaoException;
import dao.TelefoneDao;
import model.Telefone;

/**
 * A classe {@code TelefoneService} fornece serviços relacionados ao
 * gerenciamento de telefones. Ela atua como uma camada intermediária entre os
 * controladores e o {@code TelefoneDao}, facilitando operações de negócio
 * relacionadas a telefones.
 */
public class TelefoneService {

	/**
	 * Instância do {@code TelefoneDao} responsável pelas operações de acesso a
	 * dados.
	 */
	private TelefoneDao telefoneDao;

	/**
	 * Construtor padrão que inicializa a {@code TelefoneService} criando uma nova
	 * instância de {@code TelefoneDao}.
	 */
	public TelefoneService() {
		this.telefoneDao = new TelefoneDao();
	}

	/**
	 * Busca telefone por id
	 */
	public Telefone buscarPorId(Long id) throws ServiceException {
		try {
			return telefoneDao.buscarPorId(id);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * Listar todos os telefones de um usuário
	 * 
	 */
	public List<Telefone> listarTodosPorUsuarioId(Long usuarioId) throws ServiceException {
		try {
			return telefoneDao.listarTodosPorUsuarioId(usuarioId);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * Deletar telefone por ID
	 */
	public void deletarPorId(Long id) throws ServiceException {
		try {
			telefoneDao.deletarPorId(id);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

}
