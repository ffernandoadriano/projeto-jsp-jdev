package model;

import java.io.Serializable;
import java.util.Objects;

import model.enums.TipoContato;

public class Telefone implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String numero;
	private Usuario usuario;
	private Usuario usuarioInclusao;
	private TipoContato tipoContato;
	private String infoAdicional;

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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioInclusao() {
		return usuarioInclusao;
	}

	public void setUsuarioInclusao(Usuario usuarioInclusao) {
		this.usuarioInclusao = usuarioInclusao;
	}

	public TipoContato getTipoContato() {
		return tipoContato;
	}

	public void setTipoContato(TipoContato tipoContato) {
		this.tipoContato = tipoContato;
	}

	public String getInfoAdicional() {
		return infoAdicional;
	}

	public void setInfoAdicional(String infoAdicional) {
		this.infoAdicional = infoAdicional;
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
		return "Telefone [id=" + id + ", numero=" + numero + ", usuario=" + usuario + ", usuarioInclusao="
				+ usuarioInclusao + ", tipoContato=" + tipoContato + ", infoAdicional=" + infoAdicional + "]";
	}

}
