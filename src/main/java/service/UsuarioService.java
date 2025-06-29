package service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import dao.DaoException;
import dao.UsuarioDao;
import dto.MediaSalarialDTO;
import model.Usuario;
import util.StringUtils;

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
	 * Salva um novo usuário ou atualiza um usuário existente, dependendo se o ID
	 * está presente.
	 * <p>
	 * Se o {@code usuario.getId()} for {@code null}, o método realiza uma inserção
	 * no banco de dados. Caso contrário, realiza uma atualização, incluindo dados
	 * de endereço e, opcionalmente, a senha.
	 * </p>
	 *
	 * @param usuario            o usuário a ser salvo ou atualizado.
	 * @param usuarioSS          o serviço de sessão do usuário, utilizado para
	 *                           obter o usuário logado.
	 * @param deveAtualizarSenha indica se a senha do usuário deve ser atualizada
	 *                           durante a operação.
	 * 
	 * @return uma {@code String} indicando a ação realizada: "Salvar" ou
	 *         "Atualizar".
	 * 
	 * @throws ServiceException se ocorrer uma falha ao acessar a camada DAO durante
	 *                          a operação.
	 */
	public String salvarOuAtualizarUsuario(Usuario usuario, UsuarioSessionService usuarioSS, boolean deveAtualizarSenha)
			throws ServiceException {
		try {
			String acao;

			if (usuario.getId() == null) {
				usuarioDao.inserir(usuario, usuarioSS.getUsuarioLogado().getId());
				acao = "Salvar";

			} else {
				usuarioDao.atualizarComEndereco(usuario, deveAtualizarSenha);
				acao = "Atualizar";
			}

			return acao;

		} catch (DaoException e) {
			throw new ServiceException(e);
		}
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

	/**
	 * Verifica se existe um usuário com o email informado.
	 *
	 * @param email o email a verificar
	 * @return true se existir, false caso contrário
	 * @throws ServiceException em caso de erro
	 */
	public boolean existeEmail(String email) throws ServiceException {
		try {
			return usuarioDao.existeEmail(email);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * Verifica se existe um usuário com o login informado.
	 *
	 * @param login o login a verificar
	 * @return true se existir, false caso contrário
	 * @throws ServiceException em caso de erro
	 */
	public boolean existeLogin(String login) throws ServiceException {
		try {
			return usuarioDao.existeLogin(login);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * Verifica se o e-mail informado já está em uso, considerando se é uma operação
	 * de insert ou update.
	 *
	 * @param id        o ID do usuário (null para insert, não-nulo para update)
	 * @param email     o e-mail a ser validado
	 * @param acao      a ação (usada para diferenciar tipos de update)
	 * @param usuarioSS o serviço de sessão do usuário
	 * @return true se o e-mail já estiver em uso, false caso contrário
	 * @throws ServiceException em caso de erro no serviço
	 */
	public boolean isEmailEmUso(Long id, String email, String acao, UsuarioSessionService usuarioSS)
			throws ServiceException {

		if (id != null) {
			Usuario usuarioBanco;

			if (!StringUtils.isEmpty(acao)) {

				usuarioBanco = buscarPorIdComEndereco(id);

			} else {

				Optional<Usuario> usuarioOptional = buscarPorIdComEndereco(id, usuarioSS.getUsuarioLogado().getId());

				if (usuarioOptional.isEmpty()) {
					return false; // ou lançar exceção
				}

				usuarioBanco = usuarioOptional.get();
			}

			String emailBanco = usuarioBanco.getEmail();
			if (!emailBanco.equalsIgnoreCase(email)) {
				return existeEmail(email);
			}

			return false;
		}

		return existeEmail(email);
	}

	/**
	 * Verifica se o login informado já está em uso, considerando se é uma operação
	 * de insert ou update.
	 *
	 * @param id        o ID do usuário (null para insert, não-nulo para update)
	 * @param login     o login a ser validado
	 * @param acao      a ação (usada para diferenciar tipos de update)
	 * @param usuarioSS o serviço de sessão do usuário
	 * @return true se o login já estiver em uso, false caso contrário
	 * @throws ServiceException em caso de erro no serviço
	 */
	public boolean isLoginEmUso(Long id, String login, String acao, UsuarioSessionService usuarioSS)
			throws ServiceException {

		if (id != null) {
			Usuario usuarioBanco;

			if (!StringUtils.isEmpty(acao)) {

				usuarioBanco = buscarPorIdComEndereco(id);

			} else {

				Optional<Usuario> usuarioOptional = buscarPorIdComEndereco(id, usuarioSS.getUsuarioLogado().getId());

				if (usuarioOptional.isEmpty()) {
					return false; // ou lançar exceção se fizer mais sentido no seu domínio
				}

				usuarioBanco = usuarioOptional.get();
			}

			String loginBanco = usuarioBanco.getLogin();

			if (!loginBanco.equalsIgnoreCase(login)) {
				return existeLogin(login);
			}

			return false;
		}

		return existeLogin(login);
	}

	/**
	 * Busca um usuário por ID, sem restrição de usuário logado, incluindo endereço.
	 *
	 * @param id ID do usuário
	 * @return o usuário encontrado ou null
	 * @throws ServiceException em caso de falha na busca
	 */
	public Usuario buscarPorIdComEndereco(Long id) throws ServiceException {
		try {
			return usuarioDao.buscarPorIdComEndereco(id);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * Busca um usuário pelo seu ID e pelo ID do usuário logado, garantindo que o
	 * usuário encontrado não seja um administrador (admin = false) e que pertença
	 * ao usuário logado. Também carrega os dados do endereço associado, se houver.
	 *
	 * @param id              o ID do usuário a ser buscado.
	 * @param idUsuarioLogado o ID do usuário atualmente logado (proprietário do
	 *                        usuário buscado).
	 * @return um {@link Optional} contendo o {@link Usuario} encontrado com seu
	 *         endereço carregado, ou {@link Optional#empty()} se nenhum usuário for
	 *         encontrado.
	 * @throws ServiceException se ocorrer um erro ao acessar o banco de dados.
	 */
	public Optional<Usuario> buscarPorIdComEndereco(Long id, Long idUsuarioLogado) throws ServiceException {
		try {
			return usuarioDao.buscarPorIdComEndereco(id, idUsuarioLogado);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * Retorna uma lista de usuários filtrados com base no usuário logado e em um
	 * intervalo de datas de nascimento.
	 *
	 * <p>
	 * Este método consulta a base de dados por meio da camada DAO, retornando os
	 * usuários associados ao usuário logado e que tenham data de nascimento dentro
	 * do intervalo especificado.
	 * </p>
	 *
	 * @param idUsuarioLogado ID do usuário logado que está realizando a consulta.
	 * @param dataNascInicial Data de nascimento inicial no formato {@code String}
	 *                        (ex: "1990-01-01").
	 * @param dataNascFinal   Data de nascimento final no formato {@code String}
	 *                        (ex: "2000-12-31").
	 * @return Lista de objetos {@link Usuario} que atendem aos critérios
	 *         informados.
	 * @throws ServiceException Se ocorrer um erro ao acessar a camada de
	 *                          persistência.
	 */
	public List<Usuario> listarPorUsuarioLogado(Long idUsuarioLogado, String dataNascInicial, String dataNascFinal)
			throws ServiceException {
		try {
			return usuarioDao.listarPorUsuarioLogado(idUsuarioLogado, dataNascInicial, dataNascFinal);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * Lista todos os usuários vinculados ao usuário logado com paginação.
	 *
	 * @param idUsuarioLogado ID do usuário logado
	 * @param offset          posição inicial para paginação
	 * @return lista de usuários
	 * @throws ServiceException em caso de falha
	 */
	public List<Usuario> listarPorUsuarioLogado(Long idUsuarioLogado, int offset) throws ServiceException {
		try {
			return usuarioDao.listarPorUsuarioLogado(idUsuarioLogado, offset);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * Lista todos os usuários não administradores associados ao usuário logado
	 * informado.
	 * <p>
	 * A consulta retorna usuários cujo campo {@code admin} é {@code false} e que
	 * possuem o {@code usuario_id} igual ao ID do usuário logado fornecido. Os
	 * dados retornados incluem informações básicas do usuário e seus telefones
	 * associados.
	 * </p>
	 *
	 * @param idUsuarioLogado o ID do usuário logado (usado para filtrar os usuários
	 *                        associados)
	 * @return uma lista de {@link Usuario} contendo os usuários encontrados
	 * @throws ServiceException se ocorrer um erro ao acessar o banco de dados
	 */
	public List<Usuario> listarPorUsuarioLogado(Long idUsuarioLogado) throws ServiceException {
		try {
			return usuarioDao.listarPorUsuarioLogado(idUsuarioLogado);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * Lista os usuários por nome com paginação, vinculados ao usuário logado.
	 *
	 * @param nome            nome parcial a ser buscado
	 * @param idUsuarioLogado ID do usuário logado
	 * @param offset          posição inicial para paginação
	 * @return lista de usuários encontrados
	 * @throws ServiceException em caso de erro
	 */
	public List<Usuario> listarTodosPorNome(String nome, Long idUsuarioLogado, int offset) throws ServiceException {
		try {
			return usuarioDao.listarTodosPorNome(nome, idUsuarioLogado, offset);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * Remove um usuário pelo ID, desde que não seja um administrador.
	 *
	 * @param id o ID do usuário
	 * @throws ServiceException em caso de erro ao deletar
	 */
	public void deletarPorId(Long id) throws ServiceException {
		try {
			usuarioDao.deletarPorId(id);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * Calcula o número total de páginas de usuários cadastrados pelo usuário
	 * logado.
	 *
	 * @param idUsuarioLogado ID do usuário logado
	 * @return total de páginas
	 * @throws ServiceException em caso de erro na contagem
	 */
	public int calcularTotalPaginas(Long idUsuarioLogado) throws ServiceException {
		try {
			return usuarioDao.calcularTotalPaginas(idUsuarioLogado);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * Calcula o total de páginas filtrando pelo nome, considerando o usuário
	 * logado.
	 *
	 * @param nome            nome parcial
	 * @param idUsuarioLogado ID do usuário logado
	 * @return total de páginas
	 * @throws ServiceException em caso de erro
	 */
	public int calcularTotalPaginasPorNome(String nome, Long idUsuarioLogado) throws ServiceException {
		try {
			return usuarioDao.calcularTotalPaginasPorNome(nome, idUsuarioLogado);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * Retorna o limite fixo de registros por página.
	 *
	 * @return o limite de paginação
	 */
	public int getLimitePagina() {
		return usuarioDao.getLimitePagina();
	}

	/**
	 * Retorna uma lista com a média salarial agrupada por perfil, considerando
	 * apenas os usuários subordinados ao usuário logado (usuário pai) e cuja data
	 * de nascimento esteja dentro do intervalo especificado.
	 * <p>
	 * A média é calculada com duas casas decimais ({@code ROUND(AVG(...), 2)}) e
	 * agrupada por {@code perfil_id}.
	 * <p>
	 * Os resultados são ordenados pela média salarial.
	 *
	 * @param idUsuarioLogado ID do usuário logado (usuário pai), utilizado como
	 *                        filtro.
	 * @param dataInicial     Data inicial do intervalo de nascimento dos usuários a
	 *                        serem considerados.
	 * @param dataFinal       Data final do intervalo de nascimento dos usuários a
	 *                        serem considerados.
	 * @return Lista de {@link MediaSalarialDTO} contendo a média salarial por
	 *         perfil dos usuários no intervalo informado.
	 * @throws ServiceException Caso ocorra erro de SQL, falha de conexão ou durante
	 *                          o rollback em caso de exceção.
	 */
	public List<MediaSalarialDTO> listarMediaSalarialPorPerfil(Long idUsuarioLogado, LocalDate dataInicial,
			LocalDate dataFinal) throws ServiceException {
		try {
			return usuarioDao.listarMediaSalarialPorPerfil(idUsuarioLogado, dataInicial, dataFinal);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * Retorna uma lista com a média salarial agrupada por perfil, considerando
	 * apenas os usuários subordinados ao usuário logado (usuário pai).
	 * <p>
	 * A média é calculada com duas casas decimais usando a função
	 * {@code ROUND(AVG(...), 2)} no banco de dados. O agrupamento é feito com base
	 * no {@code perfil_id} de cada usuário.
	 *
	 * @param idUsuarioLogado ID do usuário logado (usuário pai), utilizado para
	 *                        filtrar os registros.
	 * @return Lista de {@link MediaSalarialDTO} contendo a média salarial por
	 *         perfil dos usuários vinculados.
	 * @throws ServiceException Caso ocorra erro de SQL, falha de conexão ou durante
	 *                          o rollback em caso de exceção.
	 */
	public List<MediaSalarialDTO> listarMediaSalarialPorPerfil(Long idUsuarioLogado) throws ServiceException {
		try {
			return usuarioDao.listarMediaSalarialPorPerfil(idUsuarioLogado);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

}
