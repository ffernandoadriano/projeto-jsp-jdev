package servlet;

import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Imagem;
import model.Usuario;
import model.enums.TipoImagem;
import service.ImagemService;
import service.ServiceException;
import service.UsuarioService;
import service.UsuarioSessionService;
import session.PaginationSession;
import util.PaginacaoUtil;

@WebServlet("/CadastrarUsuarioServlet")
public class CadastrarUsuarioServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger.getLogger(CadastrarUsuarioServlet.class.getName());

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doIt(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doIt(request, response);
	}

	private void doIt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		UsuarioSessionService usuarioSessionService = new UsuarioSessionService(request);
		UsuarioService usuarioService = new UsuarioService();

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

				Optional<Usuario> usuarioOptional = usuarioService.buscarPorIdComEndereco(Long.valueOf(userId),
						usuarioSessionService.getUsuarioLogado().getId());

				usuarioOptional.ifPresent(usuario -> {

					ImagemService imagemService = new ImagemService();

					try {

						Imagem imagem = imagemService.buscarPorUsuarioIdETipo(usuario.getId(),
								TipoImagem.PERFIL.getDescricao());

						request.setAttribute("PerfilFoto", imagem);
						definirValores(request, usuario);

					} catch (ServiceException e) {
						LOGGER.log(Level.SEVERE, "Error", e);
					}

				});
			}

			int totalPaginas = usuarioService.calcularTotalPaginas(usuarioSessionService.getUsuarioLogado().getId());
			int offset = PaginacaoUtil.calcularOffset(Integer.parseInt(paginaAtual), usuarioService.getLimitePagina());

			List<Usuario> usuarios = usuarioService
					.listarPorUsuarioLogado(usuarioSessionService.getUsuarioLogado().getId(), offset);

			PaginationSession.set(request, "paginacao", paginaAtual);
			PaginationSession.set(request, "totalPaginas", totalPaginas);

			request.setAttribute("usuarios", usuarios);
			request.getRequestDispatcher("/principal/cadastrar_usuario.jsp").forward(request, response);

		} catch (ServiceException e) {
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
		request.setAttribute("senha", "********"); // Caracteres padrão 8: ********
		// obs: a senha é alterada quando for diferente dos caracteres

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
