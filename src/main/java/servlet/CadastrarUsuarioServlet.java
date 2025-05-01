package servlet;

import java.io.IOException;
import java.util.List;

import dao.DaoException;
import dao.UsuarioDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Usuario;
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
			HttpSession session = request.getSession();

			final String paginaParam = request.getParameter("pagina");
			String paginaAtual = (String) session.getAttribute("paginacao");

			if (paginaAtual == null) {
				paginaAtual = "1";
			} else if (paginaParam != null) {
				paginaAtual = paginaParam;
			}

			int totalPaginas = usuarioDao.calcularTotalPaginas(UsuarioLogadoSession.getUsuarioLogado(request).getId());
			int offset = PaginacaoUtil.calcularOffset(Integer.parseInt(paginaAtual), usuarioDao.getLimitePagina());

			List<Usuario> usuarios = usuarioDao.listarPorUsuarioLogado(UsuarioLogadoSession.getUsuarioLogado(request).getId(),
					offset);

			session.setAttribute("paginacao", paginaAtual);
			request.getSession().setAttribute("totalPaginas", totalPaginas);
			session.setAttribute("listarUsuariosSession", usuarios);
			response.sendRedirect(request.getContextPath() + "/principal/cadastrar_usuario.jsp");

		} catch (DaoException | IOException e) {
			throw new ServletException(e);
		}
	}
}
