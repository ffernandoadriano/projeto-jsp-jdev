package model.enums;

/**
 * Enumeração que representa os tipos de imagem utilizados no sistema.
 * 
 * <p>
 * Exemplos comuns incluem:
 * <ul>
 * <li>{@link #PERFIL} – Imagem de perfil do usuário</li>
 * <li>{@link #GALERIA} – Imagens diversas enviadas para uma galeria</li>
 * </ul>
 * 
 * Cada tipo possui uma descrição textual associada.
 * 
 * @author Fernando Adriano
 */
public enum TipoImagem {

	PERFIL("perfil"), GALERIA("galeria");

	/**
	 * Descrição textual associada ao tipo da imagem.
	 */
	private final String descricao;

	/**
	 * Construtor da enumeração.
	 * 
	 * @param descricao a descrição legível do tipo da imagem
	 */
	TipoImagem(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * Retorna a descrição textual do tipo da imagem.
	 * 
	 * @return a descrição legível
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * Retorna a descrição textual do tipo da imagem.
	 * 
	 * @return a descrição legível
	 */
	@Override
	public String toString() {
		return descricao;
	}
}
