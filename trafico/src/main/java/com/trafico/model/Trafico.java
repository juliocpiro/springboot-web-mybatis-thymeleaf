package com.trafico.model;

public class Trafico {
	private Integer id;
	private String nombreAsesor;
	private String codAsesorBack;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombreAsesor() {
		return nombreAsesor;
	}
	public void setNombreAsesor(String nombreAsesor) {
		this.nombreAsesor = nombreAsesor;
	}
	public String getCodAsesorBack() {
		return codAsesorBack;
	}
	public void setCodAsesorBack(String codAsesorBack) {
		this.codAsesorBack = codAsesorBack;
	}
	@Override
	public String toString() {
		return "Trafico [id=" + id + ", nombreAsesor=" + nombreAsesor + ", codAsesorBack=" + codAsesorBack + "]";
	}
	
}
