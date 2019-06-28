package com.cocay.sicecd.controller;

import java.text.Normalizer;
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
import com.cocay.sicecd.model.Inscripcion;
import com.cocay.sicecd.model.Profesor;
import com.cocay.sicecd.repo.CursoRep;
import com.cocay.sicecd.repo.GrupoRep;
import com.cocay.sicecd.repo.InscripcionRep;
import com.cocay.sicecd.repo.ProfesorRep;


@Controller
public class ConsultaInscripcionController {
	@Autowired
	InscripcionRep ins_rep;
	
	@Autowired
	ProfesorRep profesor_rep;
	
	@Autowired
	CursoRep curso_rep;
	
	@Autowired
	GrupoRep grupo_rep;
	
	@RequestMapping(value = "/consultaInscripcion", method = RequestMethod.GET)
	public String consultaCurso(Model model) {
		return "ConsultarInscripcion/consultaInscripcion";
	}
	
	@RequestMapping(value = "/consultaInscripciones", method = RequestMethod.POST)
	public ModelAndView consultaInscripciones(ModelMap model,HttpServletRequest request) throws ParseException {
		
		/* Datos del profesor */
		String curp = request.getParameter("curp").toUpperCase();
		String rfc = request.getParameter("rfc").toUpperCase();
		String nombre = normalizar(request.getParameter("nombre")).toUpperCase();
		String apellido_paterno = normalizar(request.getParameter("apellido_paterno")).toUpperCase();
		Integer id_grado = Integer.parseInt(request.getParameter("grado_estudios"));
		Integer id_genero = Integer.parseInt(request.getParameter("genero"));
		Integer id_turno = Integer.parseInt(request.getParameter("turno"));
		
		/* Datos del curso */
		String clave_curso = request.getParameter("clave_curso");
		Integer id_tipo = Integer.parseInt(request.getParameter("tipos"));
		
		/* Datos del grupo */
		String clave_grupo = request.getParameter("clave_grupo");
		System.out.println("Clave Grupo: " + clave_grupo);
		
		/* Intervalo de tiempo */
		String fecha_inicio_1 = request.getParameter("fecha_1");
		String fecha_inicio_2 = request.getParameter("fecha_2");
		
		List<Inscripcion> ins1 = ins_rep.findAll();		
		List<Inscripcion> ins2 = null;
		Profesor p = new Profesor();
		
		if (rfc != "" ) {
			p = profesor_rep.findByRfc(rfc);
		} else if (curp != "") {
			p = profesor_rep.findByCurp(curp);
		}
		
		if( p != null ) {
			ins2 = p.getInscripciones();
		}
		
		if ( ins2 != null ) {
			System.out.println(ins2.size());
			model.put("ins", ins2);
			return new ModelAndView("ConsultarInscripcion/muestraListaIns",model);
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
