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

@WebServlet("/EditarTelefoneServlet")
public class EditarTelefoneServlet extends HttpServlet {
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

		if (id != null) {

			try {
				UsuarioDao usuarioDao = new UsuarioDao();
				TelefoneDao telefoneDao = new TelefoneDao();

				Telefone telefone = telefoneDao.buscarPorId(Long.parseLong(id));
				Usuario usuario = usuarioDao.buscarPorIdComEndereco(telefone.getUsuario().getId());

				definirValores(request, usuario, telefone);

				List<Telefone> telefones = telefoneDao.listarTodosPorUsuarioId(telefone.getUsuario().getId());
				request.setAttribute("telefones", telefones);

			} catch (DaoException e) {
				throw new ServletException(e);
			}
		}

		request.getRequestDispatcher("/principal/telefone.jsp").forward(request, response);

	}

	private void definirValores(HttpServletRequest request, Usuario usuario, Telefone telefone) {
		request.setAttribute("id", usuario.getId());
		request.setAttribute("nome", usuario.getNome());
		request.setAttribute("idTel", telefone.getId());
		request.setAttribute("tipo", telefone.getTipoContato().getId());
		request.setAttribute("contato", telefone.getNumero());
		request.setAttribute("info", telefone.getInfoAdicional());
	}
}
