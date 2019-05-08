package com.cocay.sicecd.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cocay.sicecd.model.Grupo;
import com.cocay.sicecd.model.Inscripcion;
import com.cocay.sicecd.repo.InscripcionRep;


@Controller
public class ConsultaInscripcionController {
	@Autowired
	InscripcionRep ins;
	
	@RequestMapping(value = "/consultaInscripcion", method = RequestMethod.GET)
	public String consultaCurso(Model model) {
		return "ConsultarInscripcion/consultaInscripcion";
	}
	
	@RequestMapping(value = "/consultaInsGrupo", method = RequestMethod.POST)
	public ModelAndView consultarInsGrupo(ModelMap model,HttpServletRequest request) throws ParseException {
		String clave_grupo = request.getParameter("ins_grupo");
		
		List<Inscripcion> ins_lista1 = ins.findAll();
		List<Inscripcion> ins_lista2 = ins.findAll();
		
		for(Inscripcion i : ins_lista1) {
			if(i.getFk_id_grupo().getClave() != clave_grupo) {
				ins_lista2.remove(i);
			}
		}
		
		if(!ins_lista2.isEmpty()) {
			model.put("ins", ins_lista2);
			return new ModelAndView("ConsultarGrupo/muestraListaIns",model);
		}else {
			return new ModelAndView("/Avisos/ErrorBusqueda");
		}
	}
}
