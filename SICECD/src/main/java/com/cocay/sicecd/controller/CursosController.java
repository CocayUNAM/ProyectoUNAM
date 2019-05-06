package com.cocay.sicecd.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cocay.sicecd.model.Curso;
import com.cocay.sicecd.model.Tipo_curso;
import com.cocay.sicecd.repo.CursoRep;
import com.cocay.sicecd.repo.Tipo_cursoRep;

@Controller
@RequestMapping("AdministracionCursos")
public class CursosController {
	
	@Autowired
	CursoRep cursoRep;
	
	@Autowired
	private Tipo_cursoRep tpRep;

	
	//Mapeo del html para registrar cursos
	@RequestMapping(value = {"/registrarCursos" , "/pruebas1"}, method = {RequestMethod.POST, RequestMethod.GET})
	public String registrarCursos(Model model, HttpServletRequest request){
		if(request.getParameterNames().hasMoreElements()) {
			String clave = request.getParameter("clave");
			
			String tipo = request.getParameter("tipo");
			
			if(tipo.equals("curso")) {
				tipo = "Curso";
			} else if(tipo.equals("diplomado")) {
				tipo = "Diplomado";
			} else {
				tipo = "Especialidad";
			}
			
			String horas = request.getParameter("horas");
			
			String nombre = request.getParameter("nombre");
			
			/* Curso como tal, no requiere fechas de inicio o fin
			String fInicio = request.getParameter("f_inicio");
			Date fechaI = null;
			try {
				fechaI = new SimpleDateFormat("dd/MM/yyyy").parse(fInicio);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			String fTermino = request.getParameter("f_termino");
			Date fechaT = null;
			try {
				fechaT = new SimpleDateFormat("dd/MM/yyyy").parse(fTermino);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			Curso curso = new Curso();
			
			curso.setClave(clave);
			
			List<Tipo_curso> cursos = tpRep.findByNombre(tipo);
			if(!cursos.isEmpty()) {
				curso.setFk_id_tipo_curso(cursos.get(0));
			}
			
			curso.setHoras(Integer.valueOf(horas));
			curso.setNombre(nombre);
			cursoRep.save(curso);
		}
		
		return "CursosController/registrarCursos";
	}

}
