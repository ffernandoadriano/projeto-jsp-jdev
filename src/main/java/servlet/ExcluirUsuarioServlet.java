package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ServiceException;
import service.UsuarioService;

@WebServlet("/ExcluirUsuarioServlet")
public class ExcluirUsuarioServlet extends HttpServlet {

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

		String acao = request.getParameter("acao");
		Long id = Long.parseLong(request.getParameter("id"));

		if (acao != null && acao.equalsIgnoreCase("excluirComAjax")) {
			deletarRegistro(id);
			response.getWriter().append("Operação realizada com sucesso!");

		} else {
			deletarRegistro(id);
			response.sendRedirect(request.getContextPath() + "/CadastrarUsuarioServlet");
		}

	}

	private void deletarRegistro(Long id) throws ServletException {
		try {

			UsuarioService usuarioService = new UsuarioService();
			usuarioService.deletarPorId(id);

		} catch (ServiceException e) {
			throw new ServletException(e);
		}
	}

}
