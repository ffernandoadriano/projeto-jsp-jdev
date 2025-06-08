package servlet;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import util.PasswordUtil;
import util.StringUtils;

/*As Servlets são chamado de controller*/
@WebServlet(urlPatterns = { "/principal/LoginServlet", "/LoginServlet" }) /* Mapeamento de URL que vem da tela */
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final UsuarioDao usuarioDao = new UsuarioDao();

	private final ImagemDao imagemDao = new ImagemDao();

	private static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());

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

		if (UsuarioLogadoSession.isLogado(request)) {
			request.getRequestDispatcher("/principal/principal.jsp").forward(request, response);

		} else {

			String login = request.getParameter("login");
			String senha = request.getParameter("senha");

			if (!StringUtils.isEmpty(login) && !StringUtils.isEmpty(senha)) {

				try {

					Optional<Usuario> usuarioOptinal = usuarioDao.buscarPorLogin(login);

					if (usuarioOptinal.isPresent()
							&& PasswordUtil.verifyPassword(senha, usuarioOptinal.get().getSenha())) {
						
						Usuario usuario = usuarioOptinal.get();

						Imagem fotoPerfil = imagemDao.buscarPorUsuarioIdETipo(usuario.getId(), "perfil");

						// coloca o obj na sessão
						UsuarioLogadoSession.logar(request, usuario);
						UsuarioLogadoSession.createFotoPerfil(request, fotoPerfil);
						response.sendRedirect(request.getContextPath() + "/HomeServlet");

					} else {

						request.setAttribute("messageErro", "Informe o login e senha corretamente!");
						forwardIndex(request, response);

					}
				} catch (DaoException e) {
					LOGGER.log(Level.SEVERE, "Error", e);
				}

			} else {
				forwardIndex(request, response);
			}
		}
	}

	private void forwardIndex(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("LoginFormServlet").forward(request, response); // redirecionamento interno
	}
}
