package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Login;

import java.io.IOException;

/*As Servlets são chamado de controller*/
@WebServlet("/ServletLogin") /* Mapeamento de URL que vem da tela */
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/* Recebe os dados vindo pelo método get */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	/* Recebe os dados vindo pelo método post */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String login = request.getParameter("login");
		String senha = request.getParameter("senha");

		if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {

			Login loginTela = new Login();
			loginTela.setLogin(login);
			loginTela.setSenha(senha);

		} else {

			request.setAttribute("messageErro", "Informe o login e senha corretamente!");
			request.getRequestDispatcher("index.jsp").forward(request, response); // redirecionamento interno
		}

	}

}
