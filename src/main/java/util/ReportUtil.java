package util;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

public class ReportUtil {

	private ReportUtil() {
		// Construtor privado para não permitir instanciar essa classe
	}

	/**
	 * Gera um JasperPrint a partir de um .jasper ou .jrxml
	 * 
	 * @param caminhoRelatorio Caminho no classpath (ex:
	 *                         /relatorio/relatorioUsuarios.jasper)
	 * @param parametros       Mapa de parâmetros
	 * @param conexao          JDBC Connection
	 * @return JasperPrint preenchido
	 * @throws JRException Em caso de erro de compilação/preenchimento
	 */
	public static JasperPrint gerarRelatorio(String caminhoRelatorio, Map<String, Object> parametros,
			Connection conexao) throws JRException {
		InputStream inputStream = ReportUtil.class.getResourceAsStream(caminhoRelatorio);

		if (inputStream == null) {
			throw new JRException("Relatório não encontrado: " + caminhoRelatorio);
		}

		// Detecta extensão
		boolean isJrxml = caminhoRelatorio.toLowerCase().endsWith(".jrxml");

		JasperReport jasperReport;
		if (isJrxml) {
			// ⚠️ Essa abordagem é prática, mas mais lenta, já que compila o relatório toda
			// vez que ele é usado.
			jasperReport = JasperCompileManager.compileReport(inputStream);
		} else {
			// Rodar o relatório com performance, pois já está compilado.
			jasperReport = (JasperReport) JRLoader.loadObject(inputStream);
		}

		return JasperFillManager.fillReport(jasperReport, parametros, conexao);
	}

	/**
	 * Exporta um relatório para PDF
	 * 
	 * @param jasperPrint  Objeto preenchido
	 * @param caminhoSaida Caminho completo do arquivo PDF
	 * @throws JRException Em caso de erro na exportação
	 */
	public static void exportarParaPdf(JasperPrint jasperPrint, String caminhoSaida) throws JRException {
		JasperExportManager.exportReportToPdfFile(jasperPrint, caminhoSaida);
	}

}
