package com.cocay.sicecd.controller;

import javax.servlet.http.HttpServletRequest;

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
       // model.addAttribute("profesores", profesor.findAll());
		
		return "consultas/consultas";
	}

	@RequestMapping(value = "/consultasProfesores", method = RequestMethod.GET)
	public String consultasProfesores(Model model){
		
		return "consultas/profesores";
	}
	
	@RequestMapping(value = "/resultadosProfesores", method = {RequestMethod.GET, RequestMethod.POST})
	public String resultadosProfesores(Model model, HttpServletRequest request){
		String nombre = request.getParameter("nombre");
		String apellido_paterno = request.getParameter("apellido_paterno");
		String apellido_materno = request.getParameter("apellido_materno");
		
		if(nombre == null) {nombre="";} else {nombre=nombre.toUpperCase();}
		if(apellido_paterno == null) {apellido_paterno="";} else {apellido_paterno=apellido_paterno.toUpperCase();}
		if(apellido_materno == null) {apellido_materno="";} else {apellido_materno=apellido_materno.toUpperCase();}
	
		System.out.println(nombre +"-___-"+ apellido_paterno +"-___-"+ apellido_materno);
		
		model.addAttribute("profesores", profesor.findByCompleteNameList(nombre, apellido_paterno, apellido_materno));
		return "consultas/resultados-profesores";
	}
	
}
