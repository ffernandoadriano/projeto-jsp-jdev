package servlet;

import java.io.IOException;

import dao.DaoException;
import dao.UsuarioDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

		try {
			Long id = Long.parseLong(request.getParameter("id"));

			UsuarioDao usuarioDao = new UsuarioDao();
			usuarioDao.deletarPorId(id);
						
		} catch (DaoException | NumberFormatException e) {
			throw new ServletException(e);
		}
		
		response.sendRedirect(request.getContextPath() + "/CadastrarUsuarioServlet");

	}

}
