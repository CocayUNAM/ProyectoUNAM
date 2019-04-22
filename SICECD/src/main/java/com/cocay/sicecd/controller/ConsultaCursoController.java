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
import com.cocay.sicecd.model.Tipo_curso;
import com.cocay.sicecd.repo.CursoRep;
import com.cocay.sicecd.repo.GrupoRep;
import com.cocay.sicecd.repo.Tipo_cursoRep;


@Controller
public class ConsultaCursoController {
	@Autowired
	CursoRep curso;
	@Autowired
	GrupoRep grupo;
	@Autowired
	Tipo_cursoRep tipo;
	
	@RequestMapping(value = "/consultaCurso", method = RequestMethod.GET)
	public String consultaCurso(Model model) {
		return "ConsultarCurso/consultarCurso";
	}
	
	@RequestMapping(value = "/consultaCursoNombre", method = RequestMethod.POST)
	public ModelAndView consultarCursoNombre(ModelMap model,HttpServletRequest request) throws ParseException {
		String fecha_inicio_curso = request.getParameter("fecha_inicio_curso");
		String fecha_fin_curso = request.getParameter("fecha_fin_curso");
		String nombre_curso = request.getParameter("nombre_curso");		
		
		Curso cursos = curso.findByNombre(nombre_curso);
		int fk_id_curso = cursos.getPk_id_curso();
		
		List<Grupo> grupos= grupo.findByID(fk_id_curso);
		List<Tipo_curso> tipos = tipo.findByID(fk_id_curso);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date_ini = format.parse(fecha_inicio_curso);
		Date date_fin = format.parse(fecha_fin_curso);
		
		if(cursos != null) {
			model.addAttribute("clave", cursos.getClave());
			model.addAttribute("nombre", cursos.getNombre());
			
			for(Tipo_curso t: tipos) {
				model.addAttribute("tipo_curso", t.getNombre());
			}
			
			for(Grupo g: grupos) {
				model.addAttribute("fecha_inicio",g.getFecha_inicio());
				model.addAttribute("fecha_fin",g.getFecha_fin());
			}
		
			return new ModelAndView("ConsultarCurso/muestraListaCurso",model);
		}else {
			return new ModelAndView("ConsultarCurso/mostrarCurso");
		}
	}
	
	@RequestMapping(value = "/consultaCursoClave", method = RequestMethod.POST)
	public ModelAndView consultarCursoClave(ModelMap model,HttpServletRequest request) throws ParseException {
		String fecha_inicio_curso = request.getParameter("fecha_inicio_curso");
		String fecha_fin_curso = request.getParameter("fecha_fin_curso");
		String clave_curso = request.getParameter("clave_curso");
		
		Curso cursos = curso.findByClave(clave_curso);
		int fk_id_curso=cursos.getPk_id_curso();
		
		List<Grupo> grupos= grupo.findByID(fk_id_curso);
		List<Tipo_curso> tipos = tipo.findByID(fk_id_curso);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date_ini = format.parse(fecha_inicio_curso);
		Date date_fin = format.parse(fecha_fin_curso);
		
		if(cursos!=null) {
			model.addAttribute("clave", cursos.getClave());
			model.addAttribute("nombre", cursos.getNombre());
		
			for(Tipo_curso t: tipos) {
				model.addAttribute("tipo_curso", t.getNombre());
			}
		
			for(Grupo g: grupos) {
				model.addAttribute("fecha_inicio",g.getFecha_inicio());
				model.addAttribute("fecha_fin",g.getFecha_fin());
			}
		
			return new ModelAndView("ConsultarCurso/muestraListaCurso",model);
		}else {
			return new ModelAndView("ConsultarCurso/mostrarCurso");
		}
	}
}
