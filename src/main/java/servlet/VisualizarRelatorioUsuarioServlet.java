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

@WebServlet("/VisualizarRelatorioUsuarioServlet")
public class VisualizarRelatorioUsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		String dataInicial = request.getParameter("dataInicial");
		String dataFinal = request.getParameter("dataFinal");

		validarPeriodo(dataInicial, dataFinal, request);

		if (existemErros(request)) {
			definirValores(request, dataInicial, dataFinal);
			request.getRequestDispatcher("principal/relatorio_usuario.jsp").forward(request, response);
			return;
		}

		// É necessário usar quando houver caracteres na URL, para evitar comportamentos
		// inesperados.
		String dataInicialEncoded = URLEncoder.encode(dataInicial, StandardCharsets.UTF_8);
		String dataFinalEncoded = URLEncoder.encode(dataFinal, StandardCharsets.UTF_8);

		response.sendRedirect(request.getContextPath()
				+ String.format("/RelatorioUsuarioServlet?dataInicial=%s&dataFinal=%s&action=print", dataInicialEncoded,
						dataFinalEncoded));

	}

	private void validarPeriodo(String dataInicial, String dataFinal, HttpServletRequest request) {
		if (!StringUtils.isEmpty(dataInicial) && !StringUtils.isEmpty(dataFinal)) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			try {
				LocalDate dataIni = LocalDate.parse(dataInicial, formatter);
				LocalDate dataFin = LocalDate.parse(dataFinal, formatter);

				if (dataIni.isAfter(dataFin)) {
					adicionarErro("A data inicial não pode ser maior que a data final.", request);
				}

			} catch (DateTimeParseException e) {
				adicionarErro("Formato de data inválido. Use o padrão DD/MM/AAAA.", request);
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
	private void adicionarErro(String erro, HttpServletRequest request) {
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
	private boolean existemErros(HttpServletRequest request) {
		List<String> erros = (List<String>) request.getAttribute("erros");
		return erros != null && !erros.isEmpty();
	}

}
