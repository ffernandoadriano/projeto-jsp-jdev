package servlet;

import java.io.IOException;
import java.util.List;

import dao.DaoException;
import dao.TelefoneDao;
import dao.UsuarioDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Telefone;
import model.Usuario;

@WebServlet("/TelefoneServlet")
public class TelefoneServlet extends HttpServlet {
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

		String id = request.getParameter("id");

		try {
			if (id != null) {

				UsuarioDao usuarioDao = new UsuarioDao();
				TelefoneDao telefoneDao = new TelefoneDao();

				Usuario usuario = usuarioDao.buscarPorIdComEndereco(Long.parseLong(id));
				definirValores(request, usuario);

				List<Telefone> telefones = telefoneDao.listarTodosPorUsuarioId(usuario.getId());
				request.setAttribute("telefones", telefones);

				request.getRequestDispatcher("/principal/telefone.jsp").forward(request, response);

			} else {
				response.sendRedirect(request.getContextPath() + "/CadastrarUsuarioServlet");
			}
		} catch (DaoException e) {
			throw new ServletException(e);
		}

	}

	private void definirValores(HttpServletRequest request, Usuario usuario) {
		request.setAttribute("id", usuario.getId());
		request.setAttribute("nome", usuario.getNome());
	}
}
