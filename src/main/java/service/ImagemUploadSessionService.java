package service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import model.Imagem;

/**
 * Serviço responsável por gerenciar dados de imagem na sessão HTTP.
 * 
 * <p>
 * Armazena imagens temporariamente durante o processo de upload para permitir
 * sua exibição imediata e posterior persistência.
 * </p>
 * 
 * <p>
 * Utiliza atributos da sessão para guardar dados como bytes da imagem, content
 * type, extensão e base64.
 * </p>
 */
public class ImagemUploadSessionService {

	private static final String IMAGEM_BASE64 = "imagemBase64";
	private static final String IMAGEM_TEMP_BYTES = "imagemTempBytes";
	private static final String IMAGEM_TEMP_CONTENT_TYPE = "imagemTempContentType";
	private static final String IMAGEM_TEMP_EXTENSAO = "imagemTempExtensao";
	private static final String IMAGEM_TEMP_BASE64 = "imagemTempBase64";

	private final HttpServletRequest request;

	/**
	 * Cria uma instância do serviço com base na requisição atual.
	 *
	 * @param request a requisição HTTP que fornece acesso à sessão
	 */
	public ImagemUploadSessionService(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * Armazena a imagem convertida em base64 na sessão para exibição imediata.
	 *
	 * @param obj o objeto {@link Imagem} contendo os dados da imagem
	 */
	public void create(Imagem obj) {
		HttpSession session = request.getSession(); // cria uma nova session automaticamente.
		session.setAttribute(IMAGEM_BASE64, obj);
	}

	/**
	 * Armazena temporariamente os dados da imagem na sessão para uso posterior,
	 * como no momento do salvamento em banco de dados.
	 *
	 * @param imagem o objeto {@link Imagem} com os dados da imagem
	 */
	public void createTemp(Imagem imagem) {
		HttpSession session = request.getSession(); // cria uma nova session automaticamente.
		session.setAttribute(IMAGEM_TEMP_BYTES, imagem.getImage());
		session.setAttribute(IMAGEM_TEMP_CONTENT_TYPE, imagem.getContentType());
		session.setAttribute(IMAGEM_TEMP_EXTENSAO, imagem.getExtensao());
		session.setAttribute(IMAGEM_TEMP_BASE64, imagem.getImageBase64());
	}

	/**
	 * Remove os dados temporários da imagem da sessão, normalmente após o
	 * salvamento.
	 */
	public void removerTemp() {
		// Retorna a sessão existente, ou null se não existir (⚠️ não cria)
		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute(IMAGEM_TEMP_BASE64) != null) {
			session.removeAttribute(IMAGEM_TEMP_BYTES);
			session.removeAttribute(IMAGEM_TEMP_CONTENT_TYPE);
			session.removeAttribute(IMAGEM_TEMP_EXTENSAO);
			session.removeAttribute(IMAGEM_TEMP_BASE64);
		}
	}

	/**
	 * Recupera os dados temporários da imagem armazenados na sessão.
	 *
	 * @return um objeto {@link Imagem} com os dados, ou {@code null} se os dados
	 *         não estiverem presentes na sessão
	 */
	public Imagem getTemp() {
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
