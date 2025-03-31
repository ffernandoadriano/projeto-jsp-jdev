package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Login;

/*As Servlets são chamado de controller*/
@WebServlet(urlPatterns = { "/principal/ServletLogin", "/ServletLogin" }) /* Mapeamento de URL que vem da tela */
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/* Recebe os dados vindo pelo método get */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doIt(request, response);
	}

	/* Recebe os dados vindo pelo método post */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doIt(request, response);
	}

	/* Atendimento de requisição GET ou POST */
	private void doIt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		String url = request.getServletPath();

		if (login != null && senha != null) {

			Login loginTela = new Login();
			loginTela.setLogin(login);
			loginTela.setSenha(senha);

			/* Simulando um login */
			if (loginTela.getLogin().equalsIgnoreCase("admin") && loginTela.getSenha().equalsIgnoreCase("admin")) {

				if (url.equals("/ServletLogin")) {
					url = "principal/principal.jsp";
				} else {
					url = "principal.jsp";
				}

				request.getSession().setAttribute("loginSession", loginTela);
				request.getRequestDispatcher(url).forward(request, response);

			} else {

				request.setAttribute("messageErro", "Informe o login e senha corretamente!");
				forwardIndex(request, response);

			}

		} else {
			forwardIndex(request, response);
		}
	}

	private void forwardIndex(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/index.jsp").forward(request, response); // redirecionamento interno
	}

}
