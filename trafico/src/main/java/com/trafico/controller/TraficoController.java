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

import com.trafico.model.Adopcion;
import com.trafico.model.Convertibilidad;
import com.trafico.model.ConvertibilidadRegistro;
import com.trafico.model.Despliegue;
import com.trafico.model.EvolutivoApp;
import com.trafico.model.EvolutivoTotal;
import com.trafico.model.EvolutivoWeb;
import com.trafico.model.Usabilidad;
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
	
	@PostMapping("/usabilidad")
	public @ResponseBody Map<String, Object> usabilidad(HttpSession session, HttpServletRequest request) {
		System.out.println("Inicio usabilidad");
		Map<String, Object> retorno = new HashMap<String, Object>();
		try {
			List<Usabilidad> data = traficoService.usabilidad();
			retorno.put("data", data);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return retorno;
	}
	
	@PostMapping("/adopcion")
	public @ResponseBody Map<String, Object> adopcion(Integer mes,HttpSession session, HttpServletRequest request) {
		System.out.println("Inicio adopcion - mes:"+mes);
		Map<String, Object> retorno = new HashMap<String, Object>();
		try {
			List<Adopcion> data = traficoService.adopcion(mes);
			retorno.put("data", data);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return retorno;
	}
	
	@PostMapping("/despliegue")
	public @ResponseBody Map<String, Object> despliegue(Integer mes,HttpSession session, HttpServletRequest request) {
		System.out.println("Inicio despliegue - mes:"+mes);
		Map<String, Object> retorno = new HashMap<String, Object>();
		try {
			List<Despliegue> data = traficoService.despliegue(mes);
			retorno.put("data", data);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return retorno;
	}
	@PostMapping("/convertibilidad")
	public @ResponseBody Map<String, Object> convertibilidad(Integer mes,HttpSession session, HttpServletRequest request) {
		System.out.println("Inicio convertibilidad - mes:"+mes);
		Map<String, Object> retorno = new HashMap<String, Object>();
		try {
			List<Convertibilidad> dataC = traficoService.convertibilidad(mes);
			List<ConvertibilidadRegistro> dataCR = traficoService.convertibilidadRegistro(mes);
			retorno.put("dataC", dataC);
			retorno.put("dataCR", dataCR);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return retorno;
	}
	@PostMapping("/evolutivo")
	public @ResponseBody Map<String, Object> evolutivo(HttpSession session, HttpServletRequest request) {
		System.out.println("Inicio evolutivo");
		Map<String, Object> retorno = new HashMap<String, Object>();
		try {
			List<EvolutivoTotal> dataT = traficoService.evolutivoTotal();
			List<EvolutivoApp> dataA = traficoService.evolutivoApp();
			List<EvolutivoWeb> dataW = traficoService.evolutivoWeb();
			
			retorno.put("dataT", dataT);
			retorno.put("dataA", dataA);
			retorno.put("dataW", dataW);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return retorno;
	}
}
