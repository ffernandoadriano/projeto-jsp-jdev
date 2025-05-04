package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dao.DaoException;
import dao.TelefoneDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Telefone;
import model.Usuario;
import model.enums.TipoContato;
import session.NotificationSession;
import session.UsuarioLogadoSession;
import util.StringUtils;

@WebServlet("/SalvarTelefoneServlet")
public class SalvarTelefoneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private HttpServletRequest request;

	private TelefoneDao telefoneDao = new TelefoneDao();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doIt(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doIt(request, response);
	}

	private void doIt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.request = request;

		String idTel = request.getParameter("idTel");
		String idUser = request.getParameter("id");
		String nome = request.getParameter("nome");
		String tipo = request.getParameter("tipo").trim();
		String contato = request.getParameter("contato").trim();
		String info = request.getParameter("info").trim();

		validarContato(contato);
		validarTipoContato(tipo);
		validarInfo(info);

		// Caso tenha ocorrido algum erro de validação, coloca as informações novamente
		// na request (para permitir que o formulário
		// exiba os campos preenchidos) e volta para a mesma tela.
		if (existemErros()) {
			definirValores(idUser, nome, tipo, contato, info);
			request.getRequestDispatcher("/principal/telefone.jsp").forward(request, response);
			return;
		}

		Telefone telefone = new Telefone();
		telefone.setNumero(contato);
		telefone.setUsuario(new Usuario(Long.parseLong(idUser)));
		telefone.setUsuarioInclusao(new Usuario(UsuarioLogadoSession.getUsuarioLogado(request).getId()));
		telefone.setTipoContato(TipoContato.fromId(Integer.parseInt(tipo)));
		telefone.setInfoAdicional(info);

		try {
			String acao;

			if (idTel != null) {
				telefone.setId(Long.parseLong(idTel));
				telefoneDao.atualizar(telefone);
				acao = "Atualizar";
			} else {
				telefoneDao.inserir(telefone);
				acao = "Salvar";
			}

			NotificationSession.set(request, acao, "ok");
			response.sendRedirect(request.getContextPath() + "/TelefoneServlet?id=" + idUser);
		} catch (DaoException e) {
			throw new ServletException(e);
		}
	}

	private void definirValores(String id, String nome, String tipo, String contato, String info) {
		request.setAttribute("id", id);
		request.setAttribute("nome", nome);
		request.setAttribute("tipo", tipo);
		request.setAttribute("contato", contato);
		request.setAttribute("info", info);
	}

	/**
	 * Valida o info
	 * 
	 * @param info
	 */
	private void validarInfo(String info) {

		if (info.length() > 255) {
			adicionarErro("A informação adicional digitada é muito grande");
		}
	}

	/**
	 * Valida o contato
	 * 
	 * @param contato
	 */
	private void validarContato(String contato) {
		if (StringUtils.isEmpty(contato)) {
			adicionarErro("O contato é obrigatório");
		}

		if (contato.length() > 30) {
			adicionarErro("O nome digitado é muito grande");
		}
	}

	/**
	 * Valida o tipo
	 * 
	 * @param tipo
	 */
	private void validarTipoContato(String tipo) {
		if (tipo == null || tipo.equals("0")) {
			adicionarErro("Selecione um tipo de contato válido.");
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
