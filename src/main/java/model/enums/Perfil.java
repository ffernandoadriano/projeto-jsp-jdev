package model.enums;

import java.util.Objects;

public enum Perfil {
	ADMINISTRADOR(1, "Administrador"), SECRETARIA(2, "Secretária"), AUXILIAR(3, "Auxiliar");

	private Integer id;
	private String descricao;

	private Perfil(Integer id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public Integer getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public static Perfil fromId(Integer id) {
		for (Perfil perfil : Perfil.values()) {
			if (Objects.equals(perfil.getId(), id)) {
				return perfil;
			}
		}
		throw new IllegalArgumentException("Id de perfil inválido: " + id);
	}
}
