package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RelatorioUsuarioServlet")
public class RelatorioUsuarioServlet extends HttpServlet {
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

		if (dataInicial != null & dataFinal != null) {
			definirValores(request, dataInicial, dataFinal);
		}

		request.getRequestDispatcher("/principal/relatorio_usuario.jsp").forward(request, response);
	}

	private void definirValores(HttpServletRequest request, String dataInicial, String dataFinal) {
		request.setAttribute("dataInicial", dataInicial);
		request.setAttribute("dataFinal", dataFinal);
	}
	


}
