package servlet;

import java.io.IOException;

import dao.DaoException;
import dao.UsuarioDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Usuario;

@WebServlet("/TelefoneServlet")
public class TelefoneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UsuarioDao usuarioDao = new UsuarioDao();

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
		String id = request.getParameter("id");

		try {
			if (id != null) {

				Usuario usuario = usuarioDao.buscarPorIdComEndereco(Long.parseLong(id));
				request.setAttribute("usuario", usuario);
				
				request.getRequestDispatcher("/principal/telefone.jsp").forward(request, response);

			} else {
				response.sendRedirect(request.getContextPath() + "/principal/cadastrar_usuario.jsp");
			}
		} catch (DaoException e) {
			throw new ServletException(e);
		}

	}
}
