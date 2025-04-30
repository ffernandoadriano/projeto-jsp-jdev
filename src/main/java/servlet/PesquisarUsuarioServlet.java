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
import util.PaginacaoUtil;

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
		String pagina = request.getParameter("pagina");
		int numPagina = pagina != null ? Integer.parseInt(pagina) : 0;

		try {
			int totalPaginasPorNome = usuarioDao.totalPaginasPorNome(nome,
					UsuarioLogadoSession.getUsuarioLogado(request).getId());
			int offset = PaginacaoUtil.calcularOffset(numPagina, usuarioDao.getLimitePagina());

			List<Usuario> usuarios = usuarioDao.encontrarPorNome(nome,
					UsuarioLogadoSession.getUsuarioLogado(request).getId(), offset);

			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(usuarios);

			// Define o tipo de conteúdo e a codificação de caracteres
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

			// Define o cabeçalho personalizado
			// X-: Embora não seja obrigatório, é uma prática comum prefixar nomes de
			// cabeçalhos personalizados com X- para indicar que são específicos da
			// aplicação.
			response.addHeader("X-Total-Paginas", String.valueOf(totalPaginasPorNome));

			// Escreve o conteúdo JSON na resposta
			response.getWriter().write(json);
			response.getWriter().flush();

		} catch (DaoException e) {
			throw new ServletException(e);
		}
	}

}
