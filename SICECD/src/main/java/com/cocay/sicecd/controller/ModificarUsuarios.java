package com.cocay.sicecd.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cocay.sicecd.model.Profesor;
import com.cocay.sicecd.repo.ProfesorRep;

@Controller
public class ModificarUsuarios {
	
	ProfesorRep proRep;
		
	@RequestMapping(value = "/listaProfesores", method = RequestMethod.POST)
	public ModelAndView consultarProfesorEstado(ModelMap model) {
		List<Profesor> list_p1 = proRep.findAll();
		
		if(!list_p1.isEmpty()) {
			
			model.put("profesores", list_p1);
			return new ModelAndView("/ModificarUsuario/listaProfesores", model);
		} else {
			return new ModelAndView("/Avisos/ErrorBusqueda");
		}
	}
}
