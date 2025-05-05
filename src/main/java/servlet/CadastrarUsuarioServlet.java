package servlet;

import java.io.IOException;
import java.util.List;

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
import session.PaginationSession;
import session.UsuarioLogadoSession;
import util.PaginacaoUtil;

@WebServlet("/CadastrarUsuarioServlet")
public class CadastrarUsuarioServlet extends HttpServlet {

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

		try {

			String paginaParam = request.getParameter("pagina");
			String paginaAtual = PaginationSession.get(request, "paginacao");

			if (paginaAtual == null) {
				paginaAtual = "1";
			} else if (paginaParam != null) {
				paginaAtual = paginaParam;
			}

			String action = request.getParameter("action");

			if (action != null && action.equalsIgnoreCase("edit")) {
				String userId = request.getParameter("userID");

				Usuario usuario = usuarioDao.buscarPorIdSePertencerComEndereco(Long.parseLong(userId),
						UsuarioLogadoSession.getUsuarioLogado(request).getId());

				ImagemDao imagemDao = new ImagemDao();
				Imagem imagem = imagemDao.buscarPorUsuarioIdETipo(usuario.getId(), "perfil");

				request.setAttribute("PerfilFoto", imagem);
				request.setAttribute("usuarioSalvo", usuario);
			}

			int totalPaginas = usuarioDao.calcularTotalPaginas(UsuarioLogadoSession.getUsuarioLogado(request).getId());
			int offset = PaginacaoUtil.calcularOffset(Integer.parseInt(paginaAtual), usuarioDao.getLimitePagina());

			List<Usuario> usuarios = usuarioDao
					.listarPorUsuarioLogado(UsuarioLogadoSession.getUsuarioLogado(request).getId(), offset);

			PaginationSession.set(request, "paginacao", paginaAtual);
			PaginationSession.set(request, "totalPaginas", totalPaginas);
			request.setAttribute("usuarios", usuarios);
			request.getRequestDispatcher("/principal/cadastrar_usuario.jsp").forward(request, response);

		} catch (DaoException e) {
			throw new ServletException(e);
		}
	}
}
