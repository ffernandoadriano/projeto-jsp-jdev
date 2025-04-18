package model.enums;

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
			if (sexo.sigla.equals(sigla)) {
				return sexo;
			}
		}
		throw new IllegalArgumentException("Sigla inválida: " + sigla);
	}
}
