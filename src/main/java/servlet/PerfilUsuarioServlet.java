package servlet;

import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Imagem;
import model.Usuario;
import service.UsuarioSessionService;

@WebServlet("/PerfilUsuarioServlet")
public class PerfilUsuarioServlet extends HttpServlet {
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
		UsuarioSessionService usuarioSS = new UsuarioSessionService(request);

		Usuario usuarioLogado = usuarioSS.getUsuarioLogado();
		String imagem64Perfil = usuarioSS.getFotoPerfil();
		
		definirValores(request, usuarioLogado, imagem64Perfil);

		request.getRequestDispatcher("principal/perfil_usuario.jsp").forward(request, response);
	}

	private void definirValores(HttpServletRequest request, Usuario usuario, String fotoPerfil64) {
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

		/* Foto Perfil */
		if (fotoPerfil64 != null) {
			Imagem imagem = new Imagem();
			imagem.setImageBase64(fotoPerfil64);
			request.setAttribute("PerfilFoto", imagem);
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
