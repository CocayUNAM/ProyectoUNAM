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
import com.cocay.sicecd.repo.CursoRep;


@Controller
public class ConsultaCursoController {
	@Autowired
	CursoRep curso;
	
	@RequestMapping(value = "/consultaCurso", method = RequestMethod.GET)
	public String consultaCurso(Model model) {
		return "ConsultarCurso/consultarCurso";
	}
	
	@RequestMapping(value = "/consultaCursoNombre", method = RequestMethod.POST)
	public ModelAndView consultarCursoNombre(ModelMap model,HttpServletRequest request) throws ParseException {
		String nombre_curso = request.getParameter("nombre_curso");
		Curso cursos = curso.findByNombre(nombre_curso);
		
		if(cursos != null) {
			model.addAttribute("clave", cursos.getClave());
			model.addAttribute("nombre", cursos.getNombre());
			model.addAttribute("tipo_curso", cursos.getFk_id_tipo_curso().getNombre());
			
			return new ModelAndView("ConsultarCurso/muestraCurso",model);
		}else {
			return new ModelAndView("/Avisos/ErrorBusqueda");
		}
	}
	
	@RequestMapping(value = "/consultaCursoClave", method = RequestMethod.POST)
	public ModelAndView consultarCursoClave(ModelMap model,HttpServletRequest request) throws ParseException {
		//String fecha_inicio_curso = request.getParameter("fecha_inicio_curso");
		//String fecha_fin_curso = request.getParameter("fecha_fin_curso");
		String clave_curso = request.getParameter("clave_curso");
		
		List<Curso> cursos = curso.findByClave(clave_curso);
		//int fk_id_curso=cursos.getPk_id_curso();
		
		//List<Grupo> grupos= grupo.findByID(fk_id_curso);
		//List<Tipo_curso> tipos = tipo.findByID(fk_id_curso);
		
		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		//Date date_ini = format.parse(fecha_inicio_curso);
		//Date date_fin = format.parse(fecha_fin_curso);
		
		if(!cursos.isEmpty()) {
			//model.addAttribute("clave", cursos.getClave());
			//model.addAttribute("nombre", cursos.getNombre());
		
			//for(Tipo_curso t: tipos) {
				//model.addAttribute("tipo_curso", t.getNombre());
			//}
		
			//for(Grupo g: grupos) {
				//model.addAttribute("fecha_inicio",g.getFecha_inicio());
				//model.addAttribute("fecha_fin",g.getFecha_fin());
			//}
			model.put("cursos", cursos);
			return new ModelAndView("ConsultarCurso/muestraListaCurso",model);
		}else {
			return new ModelAndView("/Avisos/ErrorBusqueda");
		}
	}
	
	@RequestMapping(value = "/consultaCursoTipo", method = RequestMethod.POST)
	public ModelAndView consultarCursoTipo(ModelMap model,HttpServletRequest request) throws ParseException {
		Integer id_tipo = Integer.parseInt(request.getParameter("tipos"));
		
		List<Curso> cursos1 = curso.findAll();
		List<Curso> cursos2 = curso.findAll();
		
		if(!cursos1.isEmpty()) {
			for(Curso c : cursos1) {
				if(c.getFk_id_tipo_curso().getPk_id_tipo_curso() != id_tipo) {
					cursos2.remove(c);
				}
			}
			
			model.put("cursos", cursos2);
			return new ModelAndView("ConsultarCurso/muestraListaCurso",model);
		} else {
			return new ModelAndView("/Avisos/ErrorBusqueda");
		}
	}
}
