package servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.StringUtils;

@WebServlet("/ImprimirRelatorioUsuarioServlet")
public class ImprimirRelatorioUsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private HttpServletRequest request;

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

		String dataInicial = request.getParameter("dataInicial");
		String dataFinal = request.getParameter("dataFinal");

		validarDataInicial(dataInicial);
		validarDataFinal(dataFinal);
		validarPeriodo(dataInicial, dataFinal);

		if (existemErros()) {
			definirValores(request, dataInicial, dataFinal);
			request.getRequestDispatcher("principal/relatorio_usuario.jsp").forward(request, response);
			return;
		}

		// É necessário usar quando houver caracteres na URL, para evitar comportamentos
		// inesperados.
		String dataInicialEncoded = URLEncoder.encode(dataInicial, StandardCharsets.UTF_8);
		String dataFinalEncoded = URLEncoder.encode(dataFinal, StandardCharsets.UTF_8);

		response.sendRedirect(request.getContextPath() + String
				.format("/RelatorioUsuarioServlet?dataInicial=%s&dataFinal=%s", dataInicialEncoded, dataFinalEncoded));

	}

	private void validarDataInicial(String dataInicial) {
		if (StringUtils.isEmpty(dataInicial)) {
			adicionarErro("O Período incial é obrigatório.");
		}
	}

	private void validarDataFinal(String dataFinal) {
		if (StringUtils.isEmpty(dataFinal)) {
			adicionarErro("O Período final é obrigatório.");
		}
	}

	private void validarPeriodo(String dataInicial, String dataFinal) {
		if (!StringUtils.isEmpty(dataInicial) && !StringUtils.isEmpty(dataFinal)) {

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			try {
				LocalDate dataIni = LocalDate.parse(dataInicial, formatter);
				LocalDate dataFin = LocalDate.parse(dataFinal, formatter);

				if (dataIni.isAfter(dataFin)) {
					adicionarErro("A data inicial não pode ser maior que a data final.");
				}

			} catch (DateTimeParseException e) {
				adicionarErro("Formato de data inválido. Use o padrão DD/MM/AAAA.");
			}
		}

	}

	private void definirValores(HttpServletRequest request, String dataInicial, String dataFinal) {
		request.setAttribute("dataInicial", dataInicial);
		request.setAttribute("dataFinal", dataFinal);
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
