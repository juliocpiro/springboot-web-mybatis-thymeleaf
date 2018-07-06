package com.trafico.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trafico.mapper.TraficoMapper;
import com.trafico.model.Adopcion;
import com.trafico.model.Convertibilidad;
import com.trafico.model.ConvertibilidadRegistro;
import com.trafico.model.Despliegue;
import com.trafico.model.EvolutivoApp;
import com.trafico.model.EvolutivoTotal;
import com.trafico.model.EvolutivoWeb;
import com.trafico.model.Trafico;
import com.trafico.model.Usabilidad;
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
	public List<Adopcion> adopcion(Integer mes){
		List<Adopcion> retorno = null;
		try {
			retorno = traficoMapper.adopcion(mes);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return retorno;
	}
	
	@Override
	public List<Despliegue> despliegue(Integer mes){
		List<Despliegue> retorno = null;
		try {
			retorno = traficoMapper.despliegue(mes);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return retorno;
	}
	@Override
	public List<Convertibilidad> convertibilidad(Integer mes){
		List<Convertibilidad> retorno = null;
		try {
			retorno = traficoMapper.convertibilidad(mes);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return retorno;
	}	
	@Override
	public List<ConvertibilidadRegistro> convertibilidadRegistro(Integer mes){
		List<ConvertibilidadRegistro> retorno = null;
		try {
			retorno = traficoMapper.convertibilidadRegistro(mes);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return retorno;
	}	
	@Override
	public List<EvolutivoTotal> evolutivoTotal(){
		List<EvolutivoTotal> retorno = null;
		try {
			retorno = traficoMapper.evolutivoTotal();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return retorno;
	}
	@Override
	public List<EvolutivoApp> evolutivoApp(){
		List<EvolutivoApp> retorno = null;
		try {
			retorno = traficoMapper.evolutivoApp();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return retorno;
	}
	@Override
	public List<EvolutivoWeb> evolutivoWeb(){
		List<EvolutivoWeb> retorno = null;
		try {
			retorno = traficoMapper.evolutivoWeb();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return retorno;
	}
}
