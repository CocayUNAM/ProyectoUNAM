package com.cocay.sicecd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cocay.sicecd.model.Profesor;
import com.cocay.sicecd.model.Usuario_sys;
import com.cocay.sicecd.repo.ProfesorRep;

@Controller
public class ModificarUsuarios {
	
	@Autowired
	ProfesorRep proRep;
		
	@RequestMapping(value = "/listaProfesores", method = RequestMethod.GET)
	public ModelAndView consultarProfesorEstado(ModelMap model) {
		List<Profesor> list_p1 = proRep.findAll();
		
		if(!list_p1.isEmpty()) {
			
			model.put("profesores", list_p1);
			return new ModelAndView("ModificarUsuario/listaProfesores", model);
		} else {
			return new ModelAndView("/Avisos/ErrorBusqueda");
		}
	}
	
	@GetMapping(value = "/pantallaModificacion")
	public ModelAndView formEditarPerfilUsuario(@RequestParam(name = "rfc") String rfc) {
		Profesor cambio = (proRep.findByRfc(rfc.trim()));
		ModelAndView model = new ModelAndView("ModificarUsuario/pantallaModificacion");
		model.addObject("profesor", cambio);
		return model;
	}
}
