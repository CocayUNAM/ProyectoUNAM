package com.cocay.sicecd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ModificarUsuario {
	
	//Alta usuario
	@RequestMapping(value = "/listaProfesores", method = RequestMethod.GET)
	public String formularioAltaUsuario() {
		return "ModificarUsuario/listaProfesores";
	}
}
