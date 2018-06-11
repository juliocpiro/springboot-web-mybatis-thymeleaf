package com.trafico.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trafico.mapper.TraficoMapper;
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
		traficoMapper.insert(trafico);
		
		return traficoMapper.findAll();
	}
}
