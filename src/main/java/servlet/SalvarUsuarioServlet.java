package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dao.DaoException;
import dao.UsuarioDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Usuario;
import model.enums.Perfil;
import model.enums.Sexo;
import session.UsuarioLogadoSession;
import util.StringUtils;

/**
 * No lado do servidor, o servlet responsável por processar o upload deve ser
 * anotado com @MultipartConfig. Essa anotação informa ao contêiner que o
 * servlet está preparado para lidar com requisições do tipo
 * multipart/form-data.
 **/
@MultipartConfig
@WebServlet("/SalvarUsuarioServlet")
public class SalvarUsuarioServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private HttpServletRequest request;

	private final UsuarioDao usuarioDao = new UsuarioDao();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doIt(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doIt(request, response);
	}

	private void doIt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.request = request;

		// Extrai os dados digitados no formulário
		String idParam = request.getParameter("id");
		// null não pode ser passado diretamente para Long.valueOf()
		Long id = (idParam == null || idParam.isEmpty()) ? null : Long.valueOf(idParam.trim());
		String nome = request.getParameter("nome").trim();
		String sexo = request.getParameter("sexo").trim();
		String email = request.getParameter("email").trim();
		String perfil = request.getParameter("perfil").trim();
		String login = request.getParameter("login").trim();
		String senha = request.getParameter("senha").trim();

		// Faz a validação dos dados digitados
		validarNome(nome);
		validarEmail(email, id);
		validarPerfil(perfil);
		validarLogin(login, id);
		validarSenha(senha);

		// Caso tenha ocorrido algum erro de validação, coloca as informações novamente
		// na request (para permitir que o formulário
		// exiba os campos preenchidos) e volta para a mesma tela.
		if (existemErros()) {
			definirValores(idParam, nome, sexo, email, perfil, login, senha);
			request.getRequestDispatcher("/principal/cadastrar_usuario.jsp").forward(request, response);
			return;
		}

		Usuario usuario = new Usuario();
		usuario.setId(id);
		usuario.setNome(nome);
		usuario.setSexo(Sexo.fromSigla(sexo));
		usuario.setEmail(email);
		usuario.setLogin(login);
		usuario.setSenha(senha);
		usuario.setPerfil(Perfil.fromId(Integer.valueOf(perfil)));

		String acao;

		try {

			if (usuario.getId() == null) {
				usuarioDao.salvar(usuario, UsuarioLogadoSession.getUsuarioLogado(request).getId());
				acao = "salvar";

			} else {
				usuarioDao.atualizar(usuario);
				acao = "atualizar";
			}

			/*
			 * coloco na sessão o objeto criado e removo assim que ele é redirecionado no
			 * jsp. Obs: somente para exibir o objeto criado
			 */
			response.sendRedirect(
					request.getContextPath() + String.format("/principal/cadastrar_usuario.jsp?acao=%s", acao));

			List<Usuario> usuarios = usuarioDao.encontrarTudo(UsuarioLogadoSession.getUsuarioLogado(request).getId());

			request.getSession().setAttribute("listarUsuariosSession", usuarios);
			request.getSession().setAttribute("usuarioSalvo", usuario);

		} catch (DaoException e) {
			throw new ServletException(e);
		}
	}

	/**
	 * Coloca os valores submetidos pelo formulário novamente na request, para que
	 * possam ser exibidos novamente após o carregamento da página
	 */
	private void definirValores(String id, String nome, String sexo, String email, String perfil, String login,
			String senha) {
		request.setAttribute("id", id);
		request.setAttribute("nome", nome);
		request.setAttribute("sexo", sexo);
		request.setAttribute("email", email);
		request.setAttribute("perfil", perfil);
		request.setAttribute("login", login);
		request.setAttribute("senha", senha);
	}

	/**
	 * Valida o nome
	 * 
	 * @param nome
	 */
	private void validarNome(String nome) {
		if (StringUtils.isEmpty(nome)) {
			adicionarErro("O nome é obrigatório");
		}

		if (nome.length() > 255) {
			adicionarErro("O nome digitado é muito grande");
		}
	}

	/**
	 * Valida o e-mail
	 * 
	 * @param email
	 * @throws ServletException
	 */
	private void validarEmail(String email, Long id) throws ServletException {
		if (StringUtils.isEmpty(email)) {
			adicionarErro("O e-mail é obrigatório");
		}

		if (email.length() > 320) {
			adicionarErro("O e-mail digitado é muito grande");
		}

		// Valida com base em uma expressão regular
		if (!email.matches("[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}")) {
			adicionarErro("O e-mail digitado não tem formato válido");
		}

		try {
			boolean emailEmUso = false;

			// Verifica UPDATE de formulário
			if (id != null) {
				Usuario usuarioBanco = usuarioDao.encontrarPorId(id,
						UsuarioLogadoSession.getUsuarioLogado(request).getId());
				String emailBanco = usuarioBanco.getEmail();

				if (!emailBanco.equalsIgnoreCase(email)) {
					emailEmUso = usuarioDao.existeEmail(email);
				}

				// Verifica INSERT formulários
			} else {
				emailEmUso = usuarioDao.existeEmail(email);
			}

			if (emailEmUso) {
				adicionarErro("O e-mail informado já está cadastrado. Por favor, utilize outro.");
			}

		} catch (DaoException e) {
			throw new ServletException(e);
		}
	}

	/**
	 * Valida o perfil
	 * 
	 * @param perfil
	 */
	private void validarPerfil(String perfil) {
		if (perfil == null || perfil.equals("0")) {
			adicionarErro("Selecione um perfil válido.");
		}
	}

	/**
	 * Valida o login
	 * 
	 * @param login
	 * @throws ServletException
	 * 
	 */
	private void validarLogin(String login, Long id) throws ServletException {
		if (StringUtils.isEmpty(login)) {
			adicionarErro("O login é obrigatório");
		}

		if (login.length() > 50) {
			adicionarErro("O login digitado é muito grande");
		}

		try {
			boolean loginEmUso = false;

			// Verifica UPDATE de formulário
			if (id != null) {
				Usuario usuarioBanco = usuarioDao.encontrarPorId(id,
						UsuarioLogadoSession.getUsuarioLogado(request).getId());
				String loginBanco = usuarioBanco.getLogin();

				if (!loginBanco.equalsIgnoreCase(login)) {
					loginEmUso = usuarioDao.existeLogin(login);
				}
				// Verifica INSERT formulários
			} else {
				loginEmUso = usuarioDao.existeLogin(login);
			}

			if (loginEmUso) {
				adicionarErro("O login informado já está em uso. Por favor, escolha outro.");
			}

		} catch (DaoException e) {
			throw new ServletException(e);
		}
	}

	/**
	 * Valida a senha
	 * 
	 * @param senha
	 */
	private void validarSenha(String senha) {
		if (StringUtils.isEmpty(senha)) {
			adicionarErro("O senha é obrigatório");
		}

		if (senha.length() > 50) {
			adicionarErro("O senha digitado é muito grande");
		}
	}

	/**
	 * Adiciona um erro de validação na request. Isto é feito através da colocação
	 * de uma list de erros na request
	 * 
	 * @param erro
	 */
	@SuppressWarnings("unchecked")
	private void adicionarErro(String erro) {
		List<String> erros = (List<String>) request.getAttribute("erros");
		if (erros == null) {
			erros = new ArrayList<>();
			request.setAttribute("erros", erros);
		}

		erros.add(erro);
	}

	/**
	 * Verifica se existem erros de validação
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean existemErros() {
		List<String> erros = (List<String>) request.getAttribute("erros");
		if (erros == null || erros.isEmpty()) {
			return false;
		}
		return true;
	}
}
