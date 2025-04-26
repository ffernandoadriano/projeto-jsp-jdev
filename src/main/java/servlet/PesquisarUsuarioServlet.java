package servlet;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DaoException;
import dao.UsuarioDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Usuario;
import session.UsuarioLogadoSession;

@WebServlet("/PesquisarUsuarioServlet")
public class PesquisarUsuarioServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static UsuarioDao usuarioDao = new UsuarioDao();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doIt(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doIt(request, response);
	}

	private void doIt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String nome = request.getParameter("nome");

		try {
			List<Usuario> usuarios = usuarioDao.encontrarPorNome(nome,
					UsuarioLogadoSession.getUsuarioLogado(request).getId(), 0);

			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(usuarios);

			response.getWriter().append(json).flush();

		} catch (DaoException e) {
			throw new ServletException(e);
		}
	}

}
