package servlet;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import connection.ConnectionFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import util.ReportUtil;
import util.StringUtils;

@WebServlet("/ImprimirRelatorioUsuarioPDFServlet")
public class ImprimirRelatorioUsuarioPDFServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String dataInicial = request.getParameter("dataInicial");
		String dataFinal = request.getParameter("dataFinal");

		Map<String, Object> parametros = new HashMap<>();
		// Inserir o caminho do sub-relatório, casohaja.
		parametros.put("PARAM_SUB_RELATORIO", "/relatorio/pdf/sub-relatorio-usuario.jasper");

		try {
			// adicionando os parâmetros para filtro
			if (!StringUtils.isEmpty(dataInicial) && !StringUtils.isEmpty(dataFinal)) {

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

				LocalDate dtIni = LocalDate.parse(dataInicial, formatter);
				LocalDate dtFin = LocalDate.parse(dataFinal, formatter);

				parametros.put("P_DATA_INICIAL", Date.valueOf(dtIni));
				parametros.put("P_DATA_FINAL", Date.valueOf(dtFin));
			}

			String caminho = "/relatorio/pdf/relatorio-usuario.jasper";

			JasperPrint gerarRelatorio = ReportUtil.gerarRelatorio(caminho, parametros,
					ConnectionFactory.getConnection());

			ReportUtil.exportarParaPdf(gerarRelatorio, "relatorio-usuario", response);

		} catch (JRException e) {
			throw new ServletException(e);
		}
	}
}
