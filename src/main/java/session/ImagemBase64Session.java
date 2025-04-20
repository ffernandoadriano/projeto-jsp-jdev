package session;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import model.Imagem;

public class ImagemBase64Session {

	private static final String IMAGEM_BASE64 = "imagemBase64";
	private static final String IMAGEM_TEMP_BYTES = "imagemTempBytes";
	private static final String IMAGEM_TEMP_CONTENT_TYPE = "imagemTempContentType";
	private static final String IMAGEM_TEMP_EXTENSAO = "imagemTempExtensao";
	private static final String IMAGEM_TEMP_BASE64 = "imagemTempBase64";

	private ImagemBase64Session() {
	}

	/**
	 * Armazena a imagem convertida em base64 na sessão (para exibição imediata).
	 */
	public static void create(HttpServletRequest request, Imagem obj) {
		HttpSession session = request.getSession(); // cria uma nova session automaticamente.
		session.setAttribute(IMAGEM_BASE64, obj);
	}

	/**
	 * Armazena temporariamente os dados brutos da imagem na sessão para
	 * reutilização posterior (como salvar no banco).
	 */
	public static void createTemp(HttpServletRequest request, Imagem imagem) {
		HttpSession session = request.getSession(); // cria uma nova session automaticamente.
		session.setAttribute(IMAGEM_TEMP_BYTES, imagem.getImage());
		session.setAttribute(IMAGEM_TEMP_CONTENT_TYPE, imagem.getContentType());
		session.setAttribute(IMAGEM_TEMP_EXTENSAO, imagem.getExtensao());
		session.setAttribute(IMAGEM_TEMP_BASE64, imagem.getImageBase64());
	}

	/**
	 * Remove os dados temporários da sessão, normalmente após salvar a imagem.
	 */
	public static void removerTemp(HttpServletRequest request) {
		// Retorna a sessão existente, ou null se não existir (⚠️ não cria)
		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute(IMAGEM_TEMP_BASE64) != null) {
			session.removeAttribute(IMAGEM_TEMP_BYTES);
			session.removeAttribute(IMAGEM_TEMP_CONTENT_TYPE);
			session.removeAttribute(IMAGEM_TEMP_EXTENSAO);
			session.removeAttribute(IMAGEM_TEMP_BASE64);
		}
	}

	public static Imagem getTemp(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return null;
		}

		byte[] imagemBytes = (byte[]) session.getAttribute(IMAGEM_TEMP_BYTES);
		String contentType = (String) session.getAttribute(IMAGEM_TEMP_CONTENT_TYPE);
		String extensao = (String) session.getAttribute(IMAGEM_TEMP_EXTENSAO);
		String base64 = (String) session.getAttribute(IMAGEM_TEMP_BASE64);

		if (imagemBytes == null || base64 == null) {
			return null;
		}

		Imagem imagem = new Imagem();
		imagem.setImage(imagemBytes);
		imagem.setExtensao(extensao);
		imagem.setImageBase64(base64);
		imagem.setContentType(contentType);

		return imagem;
	}
}
