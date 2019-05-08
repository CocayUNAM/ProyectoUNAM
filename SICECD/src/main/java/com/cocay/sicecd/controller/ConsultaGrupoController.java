package com.cocay.sicecd.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
		
		List<Grupo> grupos = obtenerGruposFecha(fecha_inicio_grupo, fecha_fin_grupo);
		List<Grupo> grupos_lista = obtenerGruposFecha(fecha_inicio_grupo, fecha_fin_grupo);
		
		for(Grupo g : grupos) {
			if(g.getClave() != clave_grupo) {
				grupos_lista.remove(g);
			}
		}
		
		if(!grupos.isEmpty()) {
			model.put("grupos", grupos);
			return new ModelAndView("ConsultarGrupo/muestraListaGrupo",model);
		}else {
			return new ModelAndView("/Avisos/ErrorBusqueda");
		}
	}
	
	/**
	 * @author Derian Estrada
	 * @param model
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/consultaGrupoCurso", method = RequestMethod.POST)
	public ModelAndView consultarGrupoCurso(ModelMap model,HttpServletRequest request) throws ParseException {
		String fecha_inicio_grupo = request.getParameter("fecha_inicio_grupo");
		String fecha_fin_grupo = request.getParameter("fecha_fin_grupo");
		String curso_grupo = request.getParameter("curso_grupo");
		
		List<Curso> cursos = curso.findByClave(curso_grupo);
		List<Grupo> grupos = obtenerGruposFecha(fecha_inicio_grupo, fecha_fin_grupo);
		List<Grupo> grupos_lista = obtenerGruposFecha(fecha_inicio_grupo, fecha_fin_grupo);
		
		if(!cursos.isEmpty()) {
			for(Curso c : cursos) {
				for(Grupo g : grupos) {
					if(g.getFk_id_curso() != c.getPk_id_curso()) {
						grupos_lista.remove(g);
					}
				}
			}
		}
		
		if(!grupos_lista.isEmpty()) {
			model.put("grupos", grupos_lista);
			return new ModelAndView("ConsultarGrupo/muestraListaGrupo",model);
		}else {
			return new ModelAndView("/Avisos/ErrorBusqueda");
		}
	}
	
	/**
	 * @author Derian Estrada
	 * @param model
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/consultaGrupoFecha", method = RequestMethod.POST)
	public ModelAndView consultarGrupoFecha(ModelMap model,HttpServletRequest request) throws ParseException {
		String fecha_inicio_grupo = request.getParameter("fecha_inicio_grupo");
		String fecha_fin_grupo = request.getParameter("fecha_fin_grupo");
		List<Grupo> grupos = obtenerGruposFecha(fecha_inicio_grupo, fecha_fin_grupo);
		
		if(!grupos.isEmpty()) {
			model.put("grupos", grupos);
			return new ModelAndView("ConsultarGrupo/muestraListaGrupo",model);
		}else {
			return new ModelAndView("/Avisos/ErrorBusqueda");
		}
	}
	
	/**
	 * @author Derian Estrada
	 * @param fecha_inicio_grupo
	 * @param fecha_fin_grupo
	 * @return
	 * @throws ParseException
	 */
	private List<Grupo> obtenerGruposFecha(String fecha_inicio_grupo, String fecha_fin_grupo) throws ParseException{
		List<Grupo> grupos;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date fecha_ini;
		Date fecha_fin;
		
		if(fecha_inicio_grupo == "" && fecha_fin_grupo == "") {
			grupos = grupo.findAll();
		}else if(fecha_fin_grupo == ""){
			fecha_ini = format.parse(fecha_inicio_grupo);
			grupos = grupo.findByFechaInicio(fecha_ini);
		}else if(fecha_inicio_grupo == ""){
			fecha_fin = format.parse(fecha_fin_grupo);
			grupos = grupo.findByFechaFin(fecha_fin);
		}else{
			fecha_ini = format.parse(fecha_inicio_grupo);
			fecha_fin = format.parse(fecha_fin_grupo);
			grupos = grupo.findByFecha(fecha_ini, fecha_fin);
		}
		
		return grupos;
	}
}
