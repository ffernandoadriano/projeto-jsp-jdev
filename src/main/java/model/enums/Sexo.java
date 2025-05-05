package model.enums;

import java.util.Objects;

public enum Sexo {
	MASCULINO("M", "Masculino"), FEMININO("F", "Feminino");

	private String sigla;
	private String descricao;

	private Sexo(String sigla, String descricao) {
		this.sigla = sigla;
		this.descricao = descricao;
	}

	public String getSigla() {
		return sigla;
	}

	public String getDescricao() {
		return descricao;
	}

	public static Sexo fromSigla(String sigla) {
		for (Sexo sexo : Sexo.values()) {
			if (Objects.equals(sexo.sigla, sigla)) {
				return sexo;
			}
		}
		throw new IllegalArgumentException("Sigla inválida: " + sigla);
	}
}
