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

import com.cocay.sicecd.model.Curso;
import com.cocay.sicecd.model.Grupo;
import com.cocay.sicecd.repo.CursoRep;
import com.cocay.sicecd.repo.GrupoRep;

@Controller
public class ConsultaGrupoController {
	@Autowired
	GrupoRep grupo;
	@Autowired
	CursoRep curso;
	
	@RequestMapping(value = "/consultaGrupo", method = RequestMethod.GET)
	public String consultaCurso(Model model) {
		return "ConsultarGrupo/consultaGrupo";
	}
	
	@RequestMapping(value = "/consultaGrupoClave", method = RequestMethod.POST)
	public ModelAndView consultarGrupoClave(ModelMap model,HttpServletRequest request) throws ParseException {
		String fecha_inicio_grupo = request.getParameter("fecha_inicio_grupo");
		String fecha_fin_grupo = request.getParameter("fecha_fin_grupo");
		String clave_grupo = request.getParameter("clave_grupo");
		
		List<Grupo> grupos = grupo.findByClave(clave_grupo);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date_ini = format.parse(fecha_inicio_grupo);
		Date date_fin = format.parse(fecha_fin_grupo);
		
		if(!grupos.isEmpty()) {
			model.put("grupos", grupos);
			return new ModelAndView("ConsultarGrupo/muestraListaGrupo",model);
		}else {
			return new ModelAndView("/Avisos/ErrorBusqueda");
		}
	}
}
