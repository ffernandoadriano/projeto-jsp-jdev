package dto;

import model.enums.Perfil;

public class MediaSalarialDTO {

	private Perfil perfil;
	private Double mediaSalarial;

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Double getMediaSalarial() {
		return mediaSalarial;
	}

	public void setMediaSalarial(Double mediaSalarial) {
		this.mediaSalarial = mediaSalarial;
	}

}
