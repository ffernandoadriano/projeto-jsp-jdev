package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dao.DaoException;
import dao.UsuarioDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Usuario;
import util.StringUtils;

@WebServlet("/SalvarUsuarioServlet")
public class SalvarUsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private HttpServletRequest request;

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

		String idParam = request.getParameter("id");
		// null não pode ser passado diretamente para Long.valueOf()
		Long id = (idParam == null || idParam.isEmpty()) ? null : Long.valueOf(idParam.trim());
		String nome = request.getParameter("nome").trim();
		String email = request.getParameter("email").trim();
		String login = request.getParameter("login").trim();
		String senha = request.getParameter("senha").trim();

		// Faz a validação dos dados digitados
		validarNome(nome);
		validarEmail(email);
		validarLogin(login);
		validarSenha(senha);

		// Caso tenha ocorrido algum erro de validação, coloca as informações novamente
		// na request (para permitir que o formulário
		// exiba os campos preenchidos) e volta para a mesma tela.
		if (existemErros()) {
			definirValores(idParam, nome, email, login, senha);
			request.getRequestDispatcher("/principal/cadastrar_usuario.jsp").forward(request, response);
			return;
		}

		Usuario usuario = new Usuario();
		usuario.setId(id);
		usuario.setNome(nome);
		usuario.setEmail(email);
		usuario.setLogin(login);
		usuario.setSenha(senha);

		UsuarioDao usuarioDao = new UsuarioDao();

		try {
			usuarioDao.salvar(usuario);
		} catch (DaoException e) {
			throw new ServletException(e);
		}

		response.sendRedirect(request.getContextPath() + "/principal/cadastrar_usuario.jsp?acao=salvo");
	}

	/**
	 * Coloca os valores submetidos pelo formulário novamente na request, para que
	 * possam ser exibidos novamente após o carregamento da página
	 */
	private void definirValores(String id, String nome, String email, String login, String senha) {
		request.setAttribute("id", id);
		request.setAttribute("nome", nome);
		request.setAttribute("email", email);
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
	 */
	private void validarEmail(String email) {
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
	}

	/**
	 * Valida o login
	 * 
	 * @param login
	 */
	private void validarLogin(String login) {
		if (StringUtils.isEmpty(login)) {
			adicionarErro("O login é obrigatório");
		}

		if (login.length() > 50) {
			adicionarErro("O login digitado é muito grande");
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
