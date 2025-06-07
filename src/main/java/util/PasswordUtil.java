package util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Classe utilitária para hashing e verificação de senhas utilizando o algoritmo
 * BCrypt.
 * <p>
 * Esta classe oferece métodos para gerar o hash seguro de uma senha em texto
 * puro e verificar se uma senha fornecida corresponde a um hash armazenado.
 * </p>
 * 
 * <p>
 * <b>Observação:</b> O BCrypt já aplica automaticamente um "salt" (valor
 * aleatório) em cada senha, aumentando a segurança contra ataques de dicionário
 * e força bruta.
 * </p>
 * 
 * <p>
 * Exemplo de uso:
 * </p>
 * 
 * <pre>{@code
 * String hash = PasswordUtil.hashSenha("minhaSenha123");
 * boolean valida = PasswordUtil.verificarSenha("minhaSenha123", hash);
 * }</pre>
 */
public class PasswordUtil {

	/**
	 * Construtor privado para impedir a instanciação da classe utilitária.
	 */
	private PasswordUtil() {
		// Construtor privado para não permitir instanciar essa classe
	}

	/**
	 * Gera o hash de uma senha em texto puro utilizando o algoritmo BCrypt.
	 *
	 * @param senha a senha original (em texto puro)
	 * @return o hash seguro da senha, incluindo o salt
	 */
	public static String hashPassword(String senha) {
		return BCrypt.hashpw(senha, BCrypt.gensalt());
	}

	/**
	 * Verifica se uma senha fornecida corresponde ao hash armazenado.
	 *
	 * @param senha     a senha digitada pelo usuário (em texto puro)
	 * @param senhaHash o hash da senha previamente armazenado
	 * @return {@code true} se a senha corresponder ao hash, {@code false} caso
	 *         contrário
	 */
	public static boolean verifyPassword(String senha, String senhaHash) {
		return BCrypt.checkpw(senha, senhaHash);
	}
}
