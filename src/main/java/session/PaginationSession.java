package session;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class PaginationSession {

	private PaginationSession() {

	}

	public static void set(HttpServletRequest request, String key, String value) {
		HttpSession session = request.getSession(); // cria uma nova session automaticamente.
		session.setAttribute(key, value);
	}

	public static void set(HttpServletRequest request, String key, int value) {
		HttpSession session = request.getSession(); // cria uma nova session automaticamente.
		session.setAttribute(key, value);
	}

	public static void remove(HttpServletRequest request, String key) {
		// Retorna a sessão existente, ou null se não existir (⚠️ não cria)
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(key);
		}
	}

	public static String get(HttpServletRequest request, String key) {
		HttpSession session = request.getSession(false);
		return session != null ? (String) session.getAttribute(key) : null;
	}

}
