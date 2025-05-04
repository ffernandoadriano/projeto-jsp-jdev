package model.enums;

import java.util.Objects;

public enum TipoContato {

	CELULAR(1, "Celular"), TELEFONE(2, "Telefone");

	private Integer id;
	private String descricao;

	private TipoContato(Integer id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public Integer getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoContato fromId(Integer id) {
		for (TipoContato contato : TipoContato.values()) {
			if (Objects.equals(contato.getId(), id)) {
				return contato;
			}
		}

		throw new IllegalArgumentException("Id do Tipo do Contato inválido: " + id);
	}

	public static TipoContato fromDescricao(String descricao) {
		for (TipoContato contato : TipoContato.values()) {
			if (Objects.equals(contato.descricao, descricao)) {
				return contato;
			}
		}

		throw new IllegalArgumentException("Descricao do Tipo do Contato inválido: " + descricao);
	}
}
