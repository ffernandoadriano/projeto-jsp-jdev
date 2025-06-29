package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Usuario;
import service.ServiceException;
import service.UsuarioService;
import service.UsuarioSessionService;
import util.StringUtils;

@WebServlet("/RelatorioUsuarioServlet")
public class RelatorioUsuarioServlet extends HttpServlet {
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
		UsuarioService usuarioService = new UsuarioService();
		UsuarioSessionService usuarioSS = new UsuarioSessionService(request);

		String dataInicial = request.getParameter("dataInicial");
		String dataFinal = request.getParameter("dataFinal");
		String action = request.getParameter("action");

		try {
			if ((StringUtils.isEmpty(dataInicial) || StringUtils.isEmpty(dataFinal)) && action != null
					&& action.equalsIgnoreCase("print")) {

				List<Usuario> usuarios = usuarioService.listarPorUsuarioLogado(usuarioSS.getUsuarioLogado().getId());

				request.setAttribute("usuarios", usuarios);

			} else if ((!StringUtils.isEmpty(dataInicial) && !StringUtils.isEmpty(dataFinal)) && action != null
					&& action.equalsIgnoreCase("print")) {

				List<Usuario> usuarios = usuarioService.listarPorUsuarioLogado(usuarioSS.getUsuarioLogado().getId(),
						dataInicial, dataFinal);

				request.setAttribute("usuarios", usuarios);
			}
			
		} catch (ServiceException e) {
			throw new ServletException(e);
		}

		definirValores(request, dataInicial, dataFinal);
		request.getRequestDispatcher("/principal/relatorio_usuario.jsp").forward(request, response);
	}

	private void definirValores(HttpServletRequest request, String dataInicial, String dataFinal) {
		request.setAttribute("dataInicial", dataInicial);
		request.setAttribute("dataFinal", dataFinal);
	}

}
