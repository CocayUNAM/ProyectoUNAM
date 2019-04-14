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
public class ConsultaCursoController {
	@Autowired
	CursoRep curso;
	@Autowired
	GrupoRep grupo;
	
	@RequestMapping(value = "/consultaCurso", method = RequestMethod.GET)
	public String consultaCurso(Model model) {
		return "ConsultarCurso/consultarCurso";
	}
	
	@RequestMapping(value = "/consultaCurso", method = RequestMethod.POST)
	public ModelAndView consultarCurso(ModelMap model,HttpServletRequest request) throws ParseException {
		String fecha_curso = request.getParameter("fecha_curso");
		String nombre_curso = request.getParameter("nombre_curso");		
		Curso cursos=curso.findByNombre(nombre_curso);
		int fk_id_curso=cursos.getPk_id_curso();
		List<Grupo> grupos= grupo.findByID(fk_id_curso);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date parsed = format.parse(fecha_curso);
		//System.out.println(parsed);
		System.out.println(nombre_curso);
		
		
		if(cursos!=null) {
		model.addAttribute("nombre", cursos.getNombre());
		for(Grupo g: grupos) {
			model.addAttribute("fecha_inicio",g.getFecha_inicio());
			model.addAttribute("fecha_fin",g.getFecha_fin());
			//System.out.println(g.getFecha_inicio());
			//System.out.println(g.getFecha_fin());
		}

		
		return new ModelAndView("ConsultarCurso/mostrarCurso",model);
		}else {
			return  new ModelAndView("ConsultarCurso/mostrarCurso");
		}
	}

}
