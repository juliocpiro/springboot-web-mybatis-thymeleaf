package com.trafico.service;

import java.util.List;

import com.trafico.model.Adopcion;
import com.trafico.model.Convertibilidad;
import com.trafico.model.ConvertibilidadRegistro;
import com.trafico.model.Despliegue;
import com.trafico.model.EvolutivoApp;
import com.trafico.model.EvolutivoTotal;
import com.trafico.model.EvolutivoWeb;
import com.trafico.model.Trafico;
import com.trafico.model.Usabilidad;

public interface TraficoService {
	public List<Trafico> findAll();  
	public List<Usabilidad> usabilidad();
	public List<Adopcion> adopcion(Integer mes);
	public List<Despliegue> despliegue(Integer mes);
	public List<Convertibilidad> convertibilidad(Integer mes);
	public List<ConvertibilidadRegistro> convertibilidadRegistro(Integer mes);
	public List<EvolutivoTotal> evolutivoTotal();
	public List<EvolutivoApp> evolutivoApp();
	public List<EvolutivoWeb> evolutivoWeb();
}
