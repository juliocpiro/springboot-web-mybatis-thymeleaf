package com.trafico.service;

import java.util.List;

import com.trafico.model.Adopcion;
import com.trafico.model.Convertibilidad;
import com.trafico.model.ConvertibilidadRegistro;
import com.trafico.model.Despliegue;
import com.trafico.model.Trafico;
import com.trafico.model.Usabilidad;

public interface TraficoService {
	public List<Trafico> findAll();  
	public List<Usabilidad> usabilidad();
	public List<Adopcion> adopcion();
	public List<Despliegue> despliegue();
	public List<Convertibilidad> convertibilidad();
	public List<ConvertibilidadRegistro> convertibilidadRegistro();
}
