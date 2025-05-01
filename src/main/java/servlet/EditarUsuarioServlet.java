package servlet;

import java.io.IOException;

import dao.DaoException;
import dao.ImagemDao;
import dao.UsuarioDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Imagem;
import model.Usuario;
import session.UsuarioLogadoSession;

@WebServlet("/EditarUsuarioServlet")
public class EditarUsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UsuarioDao usuarioDao = new UsuarioDao();
	private ImagemDao imagemDao = new ImagemDao();

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

		if (id != null) {

			try {
				Usuario usuario = usuarioDao.buscarPorIdSePertencerComEndereco(Long.parseLong(id),
						UsuarioLogadoSession.getUsuarioLogado(request).getId());

				if (usuario != null) {

					Imagem imagem = imagemDao.buscarPorUsuarioIdETipo(usuario.getId(), "perfil");

					request.setAttribute("PerfilFoto", imagem);
					request.setAttribute("usuarioSalvo", usuario);
				}
			} catch (DaoException | NumberFormatException e) {
				throw new ServletException(e);
			}

		}

		request.getRequestDispatcher("/principal/cadastrar_usuario.jsp").forward(request, response);
	}

}
