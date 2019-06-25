package com.cocay.sicecd.controller;

import java.text.Normalizer;
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


@Controller
public class ConsultaCursoController {
	@Autowired
	CursoRep curso;
	
	@RequestMapping(value = "/consultaCurso", method = RequestMethod.GET)
	public String consultaCurso(Model model) {
		return "ConsultarCurso/consultarCurso";
	}
	
	/*
	 * Consulta Simple de los Cursos.
	 * @author Derian Estrada
	 */
	@RequestMapping(value = "/consultaSimpleCurso", method = RequestMethod.POST)
	public ModelAndView consultaSimpleCurso(ModelMap model,HttpServletRequest request) throws ParseException {
		String fecha_inicio_curso = request.getParameter("fecha_inicio_curso");
		String fecha_fin_curso = request.getParameter("fecha_fin_curso");
		String nombre_curso = normalizar(request.getParameter("nombre_curso")).toUpperCase();
		String clave_curso = request.getParameter("clave_curso");
		Integer id_tipo = Integer.parseInt(request.getParameter("tipos_cursos"));
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date fecha_ini, fecha_fin;
		List<Curso> cursos1, cursos2;
		
		if(fecha_inicio_curso != "") {
			fecha_ini = format.parse(fecha_inicio_curso);
			cursos1 = curso.findByFechaInicio(fecha_ini);
			cursos2 = curso.findByFechaInicio(fecha_ini);
		} else if (fecha_inicio_curso == "" && fecha_fin_curso != ""){
			fecha_fin = format.parse(fecha_fin_curso);
			cursos1 = curso.findByFechaFin(fecha_fin);
			cursos2 = curso.findByFechaFin(fecha_fin);
		} else if (fecha_inicio_curso != "" && fecha_fin_curso != ""){
			fecha_ini = format.parse(fecha_inicio_curso);
			fecha_fin = format.parse(fecha_fin_curso);
			cursos1 = curso.findByFecha(fecha_ini, fecha_fin);
			cursos2 = curso.findByFecha(fecha_ini, fecha_fin);
		} else {
			cursos1 = curso.findAll();
			cursos2 = curso.findAll();
		}
		
		//Filtrando por tipo de curso
		if (id_tipo != 4) {
			for(Curso c : cursos1) {
				if(c.getFk_id_tipo_curso().getPk_id_tipo_curso() != id_tipo ) {
					cursos2.remove(c);
				}
			}
		}
		
		//Filtrando por clave de curso
		if (clave_curso != "") {
			for(Curso c : cursos1) {
				if(!c.getClave().contains(clave_curso)){
					cursos2.remove(c);
				}
			}
		}
		
		//Filtrando por nombre de curso
		if (nombre_curso != "") {
			for(Curso c : cursos1) {
				String cnom = normalizar(c.getNombre()).toUpperCase();
				if(!cnom.contains(nombre_curso)){
					cursos2.remove(c);
				}
			}
		}
		
		if(!cursos2.isEmpty()) {
			model.put("cursos", cursos2);
			return new ModelAndView("ConsultarCurso/muestraListaCurso",model);
		} else {
			return new ModelAndView("/Avisos/ErrorBusqueda");
		}
	}
	
	/*
	 * Consulta Avanzada de los Cursos.
	 * @author Derian Estrada
	 */
	@RequestMapping(value = "/consultaAvanzadaCurso", method = RequestMethod.POST)
	public ModelAndView consultaAvanzadaCurso(ModelMap model,HttpServletRequest request) throws ParseException {
		String fecha_inicio_curso_1 = request.getParameter("fecha_inicio_curso_1");
		String fecha_inicio_curso_2 = request.getParameter("fecha_inicio_curso_2");
		String fecha_fin_curso_1 = request.getParameter("fecha_fin_curso_1");
		String fecha_fin_curso_2 = request.getParameter("fecha_fin_curso_2");
		String nombre_curso = normalizar(request.getParameter("nombre_curso")).toUpperCase();
		String clave_curso = request.getParameter("clave_curso");
		Integer id_tipo = Integer.parseInt(request.getParameter("tipos"));
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date fecha_ini_1, fecha_ini_2, fecha_fin_1, fecha_fin_2;
		List<Curso> cursos1, cursos2;
		
		if(fecha_inicio_curso_1 != "" && fecha_fin_curso_1 == "") {
			fecha_ini_1 = format.parse(fecha_inicio_curso_1);
			fecha_ini_2 = format.parse(fecha_inicio_curso_2);
			cursos1 = curso.findByFechaInicio(fecha_ini_1, fecha_ini_2);
			cursos2 = curso.findByFechaInicio(fecha_ini_1, fecha_ini_2);
		}else if (fecha_inicio_curso_1 == "" && fecha_fin_curso_1 != ""){
			fecha_fin_1 = format.parse(fecha_fin_curso_1);
			fecha_fin_2 = format.parse(fecha_fin_curso_2);
			cursos1 = curso.findByFechaFin(fecha_fin_1, fecha_fin_2);
			cursos2 = curso.findByFechaFin(fecha_fin_1, fecha_fin_2);
		}else if (fecha_inicio_curso_1 != "" && fecha_fin_curso_1 != ""){
			fecha_ini_1 = format.parse(fecha_inicio_curso_1);
			fecha_ini_2 = format.parse(fecha_inicio_curso_2);
			fecha_fin_1 = format.parse(fecha_fin_curso_1);
			fecha_fin_2 = format.parse(fecha_fin_curso_2);
			cursos1 = curso.findByFecha(fecha_ini_1, fecha_ini_2, fecha_fin_1, fecha_fin_2);
			cursos2 = curso.findByFecha(fecha_ini_1, fecha_ini_2, fecha_fin_1, fecha_fin_2);
		}else {
			cursos1 = curso.findAll();
			cursos2 = curso.findAll();
		}
		
		//Filtrando por tipo de curso
		if (id_tipo != 4) {
			for(Curso c : cursos1) {
				if(c.getFk_id_tipo_curso().getPk_id_tipo_curso() != id_tipo ) {
					cursos2.remove(c);
				}
			}
		}
		
		//Filtrando por clave de curso
		if (clave_curso != "") {
			for(Curso c : cursos1) {
				if(!c.getClave().contains(clave_curso)){
					cursos2.remove(c);
				}
			}
		}
		
		//Filtrando por nombre de curso
		if (nombre_curso != "") {
			for(Curso c : cursos1) {
				String cnom = normalizar(c.getNombre()).toUpperCase();
				if(!cnom.contains(nombre_curso)){
					cursos2.remove(c);
				}
			}
		}
		
		if(!cursos2.isEmpty()) {
			model.put("cursos", cursos2);
			return new ModelAndView("ConsultarCurso/muestraListaCurso",model);
		} else {
			return new ModelAndView("/Avisos/ErrorBusqueda");
		}
	}
	
	public String normalizar(String src) {
        return Normalizer
                .normalize(src , Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]" , "");
    }
}
