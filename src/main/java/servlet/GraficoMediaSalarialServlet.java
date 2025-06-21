package servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import dto.MediaSalarialDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ServiceException;
import service.UsuarioService;
import service.UsuarioSessionService;
import util.StringUtils;

@WebServlet("/GraficoMediaSalarialServlet")
public class GraficoMediaSalarialServlet extends HttpServlet {
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
		String acao = request.getParameter("acao");

		if (acao != null && acao.equalsIgnoreCase("gerarGrafico")) {

			UsuarioService usuarioService = new UsuarioService();
			UsuarioSessionService usuarioSS = new UsuarioSessionService(request);

			try {
				List<MediaSalarialDTO> mediaSalarialPorPerfil;

				if (!StringUtils.isEmpty(dataInicial) && !StringUtils.isEmpty(dataFinal)) {

					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

					dataInicial = URLDecoder.decode(dataInicial, StandardCharsets.UTF_8);
					dataFinal = URLDecoder.decode(dataFinal, StandardCharsets.UTF_8);

					LocalDate dataIni = LocalDate.parse(dataInicial, formatter);
					LocalDate dataFin = LocalDate.parse(dataFinal, formatter);

					mediaSalarialPorPerfil = usuarioService
							.listarMediaSalarialPorPerfil(usuarioSS.getUsuarioLogado().getId(), dataIni, dataFin);

				} else {

					mediaSalarialPorPerfil = usuarioService
							.listarMediaSalarialPorPerfil(usuarioSS.getUsuarioLogado().getId());

				}

				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mediaSalarialPorPerfil);

				// Define o tipo de conteúdo e a codificação de caracteres
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");

				// Escreve o conteúdo JSON na resposta
				response.getWriter().write(json);
				response.getWriter().flush();

			} catch (ServiceException e) {
				throw new ServletException(e);
			}

		} else {
			request.getRequestDispatcher("/principal/grafico_mediaSalarial.jsp").forward(request, response);
		}

	}

}
