package servlet;

import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

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

			String paginaParam = request.getParameter("page");
			String paginaAtual = PaginationSession.get(request, "paginacao");

			if (paginaAtual == null) {
				paginaAtual = "1";
			} else if (paginaParam != null) {
				paginaAtual = paginaParam;
			}

			String action = request.getParameter("action");

			if (action != null && (action.equalsIgnoreCase("edit") || action.equalsIgnoreCase("save"))) {
				String userId = request.getParameter("userID");

				Usuario usuario = usuarioDao.buscarPorIdComEndereco(Long.parseLong(userId),
						UsuarioLogadoSession.getUsuarioLogado(request).getId());

				ImagemDao imagemDao = new ImagemDao();
				Imagem imagem = imagemDao.buscarPorUsuarioIdETipo(usuario.getId(), "perfil");

				request.setAttribute("PerfilFoto", imagem);
				definirValores(request, usuario);
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

	/**
	 * Coloca os valores submetidos pelo formulário novamente na request, para que
	 * possam ser exibidos novamente após o carregamento da página
	 * 
	 */
	private void definirValores(HttpServletRequest request, Usuario usuario) {
		/* Usuario */
		request.setAttribute("id", usuario.getId());
		request.setAttribute("nome", usuario.getNome());
		if (usuario.getDataNascimento() != null) {
			request.setAttribute("dataNascimento", formatarData(usuario.getDataNascimento()));
		}
		request.setAttribute("sexo", usuario.getSexo().getSigla());
		request.setAttribute("email", usuario.getEmail());
		request.setAttribute("perfil", usuario.getPerfil().getId());
		request.setAttribute("rendaMensal", formatarParaMoeda(usuario.getRendaMensal()));
		request.setAttribute("login", usuario.getLogin());
		request.setAttribute("senha", usuario.getSenha());

		/* Endereço */
		if (usuario.getEndereco() != null) {
			request.setAttribute("enderecoId", usuario.getEndereco().getId()); // Campo hidden
			request.setAttribute("cep", usuario.getEndereco().getCep());
			request.setAttribute("rua", usuario.getEndereco().getRua());
			request.setAttribute("numero", usuario.getEndereco().getNumero());
			request.setAttribute("bairro", usuario.getEndereco().getBairro());
			request.setAttribute("cidade", usuario.getEndereco().getCidade());
			request.setAttribute("estado", usuario.getEndereco().getEstado());
			request.setAttribute("uf", usuario.getEndereco().getUf());
		}
	}

	private String formatarParaMoeda(double valor) {
		Locale brasil = Locale.of("pt", "BR");
		NumberFormat formatador = NumberFormat.getCurrencyInstance(brasil);
		return formatador.format(valor);
	}

	private String formatarData(LocalDate data) {
		return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(data);
	}
}
