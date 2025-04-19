package session;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import model.Imagem;

public class ImagemBase64Session {

	private static final String IMAGEM_BASE64 = "imagemBase64";

	private ImagemBase64Session() {
	}

	public static void create(HttpServletRequest request, Imagem obj) {
		HttpSession session = request.getSession(); // cria uma nova session automaticamente.
		session.setAttribute(IMAGEM_BASE64, obj);
	}

	public static Imagem getImagem(HttpServletRequest request) {
		HttpSession session = request.getSession(false); // não cria uma sessão automaticamente, apenas consulta.
		return (session != null) ? (Imagem) session.getAttribute(IMAGEM_BASE64) : null;
	}
}
