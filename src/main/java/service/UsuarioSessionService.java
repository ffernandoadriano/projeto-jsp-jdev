package service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import model.Imagem;
import model.Usuario;

public class UsuarioSessionService {

	private static final String USUARIO_LOGADO = "usuarioLogado";
	private static final String FOTO_PERFIL = "imagemPerfil";

	private final HttpServletRequest request;

	public UsuarioSessionService(HttpServletRequest request) {
		this.request = request;
	}

	public void logar(Usuario usuario) {
		HttpSession session = request.getSession(); // cria uma nova session automaticamente.
		session.setAttribute(USUARIO_LOGADO, usuario);
	}

	public Usuario getUsuarioLogado() {
		HttpSession session = request.getSession(false); // não cria uma sessão automaticamente, apenas consulta.
		return (session != null) ? (Usuario) session.getAttribute(USUARIO_LOGADO) : null;
	}

	public void deslogar() {
		HttpSession session = request.getSession(false); // Retorna a sessão existente, ou null se não existir (⚠️ não
															// cria)
		if (session != null) {
			session.invalidate(); // invalida a sessão - Remove todos os atributos armazenados na sessão.
		}
	}

	public boolean isLogado() {
		return getUsuarioLogado() != null;
	}

	public void createFotoPerfil(Imagem fotoPerfil) {
		if (fotoPerfil != null) {
			HttpSession session = request.getSession(); // cria uma nova session automaticamente.
			session.setAttribute(FOTO_PERFIL, fotoPerfil.getImageBase64());
		}
	}

	public String getFotoPerfil() {
		HttpSession session = request.getSession(false); // não cria uma sessão automaticamente, apenas consulta.
		return (session != null) ? (String) session.getAttribute(FOTO_PERFIL) : null;
	}

}
