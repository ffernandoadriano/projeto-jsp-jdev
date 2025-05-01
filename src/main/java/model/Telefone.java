package model;

import java.io.Serializable;
import java.util.Objects;

public class Telefone implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String numero;
	private Usuario usuario_id;
	private Usuario usuario_inclusao_id;

	public Telefone() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Usuario getUsuario_id() {
		return usuario_id;
	}

	public void setUsuario_id(Usuario usuario_id) {
		this.usuario_id = usuario_id;
	}

	public Usuario getUsuario_inclusao_id() {
		return usuario_inclusao_id;
	}

	public void setUsuario_inclusao_id(Usuario usuario_inclusao_id) {
		this.usuario_inclusao_id = usuario_inclusao_id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Telefone other = (Telefone) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Telefone [id=" + id + ", numero=" + numero + ", usuario_id=" + usuario_id + ", usuario_inclusao_id="
				+ usuario_inclusao_id + "]";
	}

}
