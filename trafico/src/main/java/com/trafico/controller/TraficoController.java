package com.trafico.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.trafico.mapper.TraficoMapper;
import com.trafico.model.Trafico;
import com.trafico.service.TraficoService;

@Controller
public class TraficoController {
	
	@Autowired
	private TraficoService traficoService;
	
	@GetMapping("/trafico")
	public String trafico(Model model) {
		List<Trafico> list = traficoService.findAll();
		model.addAttribute("name", "Julio Cesar");
		System.out.println("-->"+list);
		return "trafico";
	}
}
