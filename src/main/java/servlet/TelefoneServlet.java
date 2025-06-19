package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Telefone;
import model.Usuario;
import service.ServiceException;
import service.TelefoneService;
import service.UsuarioService;

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

		UsuarioService usuarioService = new UsuarioService();
		TelefoneService telefoneService = new TelefoneService();

		String id = request.getParameter("id");
		String action = request.getParameter("action");

		try {

			Usuario usuario = null;

			if ("edit".equalsIgnoreCase(action)) {
				String foneId = request.getParameter("foneID");

				Telefone telefone = telefoneService.buscarPorId(Long.parseLong(foneId));
				usuario = usuarioService.buscarPorIdComEndereco(telefone.getUsuario().getId());

				definirValores(request, usuario, telefone);

			} else if (id != null) {
				usuario = usuarioService.buscarPorIdComEndereco(Long.parseLong(id));

				definirValores(request, usuario);

			} else {
				response.sendRedirect(request.getContextPath() + "/CadastrarUsuarioServlet");
				return;
			}

			List<Telefone> telefones = telefoneService.listarTodosPorUsuarioId(usuario.getId());

			request.setAttribute("telefones", telefones);
			request.getRequestDispatcher("/principal/telefone.jsp").forward(request, response);

		} catch (ServiceException e) {
			throw new ServletException(e);
		}

	}

	private void definirValores(HttpServletRequest request, Usuario usuario) {
		request.setAttribute("id", usuario.getId());
		request.setAttribute("nome", usuario.getNome());
	}

	private void definirValores(HttpServletRequest request, Usuario usuario, Telefone telefone) {
		request.setAttribute("id", usuario.getId());
		request.setAttribute("nome", usuario.getNome());
		request.setAttribute("idTel", telefone.getId()); // hidden
		request.setAttribute("tipo", telefone.getTipoContato().getId());
		request.setAttribute("contato", telefone.getNumero());
		request.setAttribute("info", telefone.getInfoAdicional());
	}
}
