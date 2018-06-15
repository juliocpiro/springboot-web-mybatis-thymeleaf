package com.trafico.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trafico.model.Convertibilidad;
import com.trafico.model.ResumenGeneral;
import com.trafico.model.ResumenHistorico;
import com.trafico.service.TraficoService;

@Controller
@RequestMapping("/trafico")
public class TraficoController {
	
	@Autowired
	private TraficoService traficoService;
	
	@GetMapping()
	public String trafico(Model model) {
		return "html/trafico";
	}
	
	@PostMapping("/resumenHistorico")
	public @ResponseBody Map<String, Object> resumenHistorico(HttpSession session, HttpServletRequest request) {
		System.out.println("Inicio resumenHistorico");
		Map<String, Object> retorno = new HashMap<String, Object>();
		try {
			List<ResumenHistorico> data = traficoService.resumenHistorico();
			retorno.put("data", data);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return retorno;
	}
	
	@PostMapping("/resumenGeneral")
	public @ResponseBody Map<String, Object> resumenGeneral(HttpSession session, HttpServletRequest request) {
		System.out.println("Inicio resumenGeneral");
		Map<String, Object> retorno = new HashMap<String, Object>();
		try {
			List<ResumenGeneral> data = traficoService.resumenGeneral();
			retorno.put("data", data);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return retorno;
	}
	
	@PostMapping("/convertibilidad")
	public @ResponseBody Map<String, Object> convertibilidad(HttpSession session, HttpServletRequest request) {
		System.out.println("Inicio convertibilidad");
		Map<String, Object> retorno = new HashMap<String, Object>();
		try {
			List<Convertibilidad> data = traficoService.convertibilidad();
			retorno.put("data", data);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return retorno;
	}
}
