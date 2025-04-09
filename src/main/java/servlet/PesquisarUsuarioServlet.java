package servlet;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dao.DaoException;
import dao.UsuarioDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Usuario;

@WebServlet("/PesquisarUsuarioServlet")
public class PesquisarUsuarioServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private UsuarioDao usuarioDao = new UsuarioDao();

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
			List<Usuario> usuarios = usuarioDao.encontrarPorNome(nome);

			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(usuarios);

			response.getWriter().append(json).flush();

		} catch (DaoException e) {
			throw new ServletException(e);
		}
	}

}
