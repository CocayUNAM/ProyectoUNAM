package com.cocay.sicecd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cocay.sicecd.model.Inscripcion;
import com.cocay.sicecd.repo.InscripcionRep;

public class ModificarInscripcion {
	
	@Autowired
	InscripcionRep insRep;
		
	@RequestMapping(value = "/listaInscripciones", method = RequestMethod.GET)
	public ModelAndView consultarInscripciones(ModelMap model) {
		List<Inscripcion> list_p1 = insRep.findAll();
		
		if(!list_p1.isEmpty()) {
			
			model.put("inscripciones", list_p1);
			return new ModelAndView("/ModificarInscripcion/listaInscripciones", model);
		} else {
			return new ModelAndView("/Avisos/ErrorBusqueda");
		}
	}

}
