package util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Map;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

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

		try (InputStream inputStream = ReportUtil.class.getResourceAsStream(caminhoRelatorio)) {

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
		} catch (IOException e) {
			throw new JRException("Erro ao ler o arquivo do relatório", e);
		}
	}

	/**
	 * Gera e envia um relatório JasperReports em formato PDF para o navegador,
	 * exibindo-o diretamente na tela do usuário (sem forçar o download).
	 * 
	 * @param jasperPrint Objeto JasperPrint já preenchido com os dados do
	 *                    relatório.
	 * @param nomeArquivo Nome sugerido para o arquivo PDF exibido no navegador (sem
	 *                    extensão).
	 * @param response    Objeto HttpServletResponse usado para escrever a resposta
	 *                    HTTP.
	 * 
	 * @throws JRException Se ocorrer um erro ao gerar o PDF.
	 * @throws IOException Se ocorrer um erro ao escrever na resposta HTTP.
	 */
	public static void exportarParaPdf(JasperPrint jasperPrint, String nomeArquivo, HttpServletResponse response)
			throws JRException, IOException {
		// Gera o PDF como array de bytes
		byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);

		// Prepara resposta HTTP
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline; filename=\"" + nomeArquivo + ".pdf\"");
		response.setContentLength(pdfBytes.length);

		// Evita cache do navegador
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);

		// Envia o conteúdo
		response.getOutputStream().write(pdfBytes);
		response.getOutputStream().flush();
	}

	/**
	 * Exporta um relatório JasperPrint para o formato Excel (.xlsx) e envia o
	 * arquivo diretamente na resposta HTTP.
	 *
	 * <p>
	 * Este método configura os cabeçalhos HTTP adequadamente para que o navegador
	 * interprete o conteúdo como um arquivo Excel e exiba ou baixe o arquivo com o
	 * nome informado. Utiliza o {@link JRXlsxExporter} para a exportação.
	 * </p>
	 *
	 * @param jasperPrint O objeto {@link JasperPrint} que representa o relatório
	 *                    preenchido.
	 * @param nomeArquivo O nome do arquivo a ser enviado na resposta (sem
	 *                    extensão).
	 * @param response    O {@link HttpServletResponse} usado para enviar o conteúdo
	 *                    ao cliente.
	 * @throws JRException Se ocorrer um erro durante a exportação do relatório.
	 * @throws IOException Se ocorrer um erro de I/O ao escrever a resposta.
	 */
	public static void exportarParaExcel(JasperPrint jasperPrint, String nomeArquivo, HttpServletResponse response)
			throws JRException, IOException {

		// Configura a resposta HTTP para Excel
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "inline; filename=\"" + nomeArquivo + ".xlsx\"");
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);

		// Exportador XLSX
		JRXlsxExporter exporter = new JRXlsxExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
		exporter.exportReport();
	}

}
