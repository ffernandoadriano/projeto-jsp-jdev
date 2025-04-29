package session;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import model.Imagem;
import model.Usuario;

public class UsuarioLogadoSession {

	private static final String USUARIO_LOGADO = "usuarioLogado";
	private static final String FOTO_PERFIL = "fotoPerfil";

	private UsuarioLogadoSession() {
	}

	public static void logar(HttpServletRequest request, Usuario usuario) {
		HttpSession session = request.getSession(); // cria uma nova session automaticamente.
		session.setAttribute(USUARIO_LOGADO, usuario);
	}

	public static Usuario getUsuarioLogado(HttpServletRequest request) {
		HttpSession session = request.getSession(false); // não cria uma sessão automaticamente, apenas consulta.
		return (session != null) ? (Usuario) session.getAttribute(USUARIO_LOGADO) : null;
	}

	public static void deslogar(HttpServletRequest request) {
		HttpSession session = request.getSession(false); // Retorna a sessão existente, ou null se não existir (⚠️ não
															// cria)
		if (session != null) {
			session.invalidate(); // invalida a sessão - Remove todos os atributos armazenados na sessão.
		}
	}

	public static boolean isLogado(HttpServletRequest request) {
		return getUsuarioLogado(request) != null;
	}

	public static void createFotoPerfil(HttpServletRequest request, Imagem fotoPerfil) {
		HttpSession session = request.getSession(); // cria uma nova session automaticamente.
		session.setAttribute(FOTO_PERFIL, fotoPerfil);
	}

}
