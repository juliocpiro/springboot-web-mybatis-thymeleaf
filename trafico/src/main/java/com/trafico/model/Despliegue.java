package com.trafico.model;

public class Despliegue {
	private String canalVentaAgrup;
	private String vendApp;
	private String vendWeb;
	private String otros;
	private String total;
	private String despliegue;
	public String getCanalVentaAgrup() {
		return canalVentaAgrup;
	}
	public void setCanalVentaAgrup(String canalVentaAgrup) {
		this.canalVentaAgrup = canalVentaAgrup;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getVendApp() {
		return vendApp;
	}
	public void setVendApp(String vendApp) {
		this.vendApp = vendApp;
	}
	public String getVendWeb() {
		return vendWeb;
	}
	public void setVendWeb(String vendWeb) {
		this.vendWeb = vendWeb;
	}
	public String getDespliegue() {
		return despliegue;
	}
	public void setDespliegue(String despliegue) {
		this.despliegue = despliegue;
	}
	public String getOtros() {
		return otros;
	}
	public void setOtros(String otros) {
		this.otros = otros;
	}
	
	
}
