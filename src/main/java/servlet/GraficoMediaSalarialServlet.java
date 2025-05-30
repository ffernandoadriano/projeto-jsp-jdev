package servlet;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DaoException;
import dao.UsuarioDao;
import dto.MediaSalarialDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import session.UsuarioLogadoSession;

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

			UsuarioDao usuarioDao = new UsuarioDao();

			try {
				List<MediaSalarialDTO> mediaSalarialPorPerfil = usuarioDao
						.listarMediaSalarialPorPerfil(UsuarioLogadoSession.getUsuarioLogado(request).getId());

				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mediaSalarialPorPerfil);

				// Define o tipo de conteúdo e a codificação de caracteres
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");

				// Escreve o conteúdo JSON na resposta
				response.getWriter().write(json);
				response.getWriter().flush();

			} catch (DaoException e) {
				throw new ServletException(e);
			}

		} else {
			request.getRequestDispatcher("/principal/grafico_mediaSalarial.jsp").forward(request, response);
		}

	}

}
