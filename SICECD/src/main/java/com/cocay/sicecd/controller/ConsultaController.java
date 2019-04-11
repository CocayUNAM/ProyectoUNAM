package com.cocay.sicecd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cocay.sicecd.repo.ProfesorRep;

@Controller
public class ConsultaController {
	
	@Autowired
	ProfesorRep profesor;
	
	@RequestMapping(value = "/consultas", method = RequestMethod.GET)
	public String show(Model model){
        model.addAttribute("profesores", profesor.findAll());
		
		return "consultas/consultas";
	}

}
