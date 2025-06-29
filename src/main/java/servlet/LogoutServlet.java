package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.UsuarioSessionService;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doIt(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doIt(request, response);
	}

	private void doIt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		UsuarioSessionService usuarioSessionService = new UsuarioSessionService(request);

		String acao = request.getParameter("acao");

		if (acao != null && acao.equals("Logout")) {

			usuarioSessionService.deslogar();
			request.getRequestDispatcher("index.jsp").forward(request, response); // redireciona para página inicial
			return;
		}

		throw new ServletException("erro no mapeamento logout");

	}
}
