package com.trafico.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trafico.mapper.TraficoMapper;
import com.trafico.model.Despliegue;
import com.trafico.model.Adopcion;
import com.trafico.model.Convertibilidad;
import com.trafico.model.Usabilidad;
import com.trafico.model.Trafico;
import com.trafico.service.TraficoService;

@Service()
public class TraficoServiceImpl implements TraficoService	{
	
	@Autowired
	private TraficoMapper traficoMapper;
	
	@Override
	public List<Trafico> findAll(){
		Trafico trafico = new Trafico();
		trafico.setNombreAsesor("Adriana");
		trafico.setCodAsesorBack("Adri"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		//traficoMapper.insert(trafico);
		//return traficoMapper.findAll();
		return null;
	}
	
	@Override
	public List<Usabilidad> usabilidad(){
		List<Usabilidad> retorno = null;
		try {
			retorno = traficoMapper.usabilidad();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return retorno;
	}
	
	@Override
	public List<Adopcion> adopcion(){
		List<Adopcion> retorno = null;
		try {
			retorno = traficoMapper.adopcion();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return retorno;
	}
	
	@Override
	public List<Despliegue> despliegue(){
		List<Despliegue> retorno = null;
		try {
			retorno = traficoMapper.despliegue();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return retorno;
	}
	@Override
	public List<Convertibilidad> convertibilidad(){
		List<Convertibilidad> retorno = null;
		try {
			retorno = traficoMapper.convertibilidad();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return retorno;
	}	
}
