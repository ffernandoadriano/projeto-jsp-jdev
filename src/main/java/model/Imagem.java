package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

public class Imagem implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Usuario usuario;
	/*
	 * A imagem será armazenada em bytes, porque em base64 ocupa 33% a mais do que o
	 * tamanho real.
	 * 
	 */
	private byte[] image;
	private String extensao;
	private String tipo;
	private LocalDateTime dataUpload;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public byte[] getImagem() {
		return image;
	}

	public void setImagem(byte[] imagem) {
		this.image = imagem;
	}

	public String getExtensao() {
		return extensao;
	}

	public void setExtensao(String extensao) {
		this.extensao = extensao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public LocalDateTime getDataUpload() {
		return dataUpload;
	}

	public void setDataUpload(LocalDateTime dataUpload) {
		this.dataUpload = dataUpload;
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
		Imagem other = (Imagem) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Imagem [id=" + id + ", usuario=" + usuario + ", imagem=" + Arrays.toString(image) + ", extensao="
				+ extensao + ", tipo=" + tipo + ", dataUpload=" + dataUpload + "]";
	}

}
