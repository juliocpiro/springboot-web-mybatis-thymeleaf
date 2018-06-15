package com.trafico.service;

import java.util.List;

import com.trafico.model.Convertibilidad;
import com.trafico.model.ResumenGeneral;
import com.trafico.model.ResumenHistorico;
import com.trafico.model.Trafico;

public interface TraficoService {
	public List<Trafico> findAll();  
	public List<ResumenHistorico> resumenHistorico();
	public List<ResumenGeneral> resumenGeneral();
	public List<Convertibilidad> convertibilidad();
}
