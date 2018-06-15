package com.trafico.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trafico.mapper.TraficoMapper;
import com.trafico.model.Convertibilidad;
import com.trafico.model.ResumenGeneral;
import com.trafico.model.ResumenHistorico;
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
	public List<ResumenHistorico> resumenHistorico(){
		List<ResumenHistorico> retorno = null;
		try {
			retorno = traficoMapper.resumenHistorico();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return retorno;
	}
	
	@Override
	public List<ResumenGeneral> resumenGeneral(){
		List<ResumenGeneral> retorno = null;
		try {
			retorno = traficoMapper.resumenGeneral();
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
