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
import com.cocay.sicecd.model.Grupo;
import com.cocay.sicecd.model.Profesor;
import com.cocay.sicecd.repo.CursoRep;
import com.cocay.sicecd.repo.GrupoRep;
import com.cocay.sicecd.repo.ProfesorRep;

@Controller
public class GrupoController {
	
	@Autowired
	GrupoRep grupoRep;
	
	@Autowired
	CursoRep cursoRep;
	
	@Autowired
	ProfesorRep profRep;
	
	//Mapeo del html para registrar cursos
		@RequestMapping(value = "/registrarGrupo", method = {RequestMethod.POST, RequestMethod.GET})
		public String registrarGrupo(Model model, HttpServletRequest request){
			if(request.getParameterNames().hasMoreElements()) {
				String clave = request.getParameter("clave");
				
				String curso = request.getParameter("curso");
				
				String asesor = request.getParameter("asesor");
				
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
				}
			
				Grupo grupo = new Grupo();
				
				grupo.setClave(clave);
				
				grupo.setFecha_inicio(fechaI);
				
				grupo.setFecha_fin(fechaT);
				
				List<Curso> cursop = cursoRep.findByClave(curso);
				if(!cursop.isEmpty()) {
					grupo.setFk_id_curso(cursop.get(0).getPk_id_curso());
				}
				
				Profesor profe = profRep.findByRfc(asesor);
				if(profe != null) {
					grupo.setFk_id_profesor(profe);
				}
				
				grupoRep.save(grupo);
			}
			
			return "GrupoController/registrarGrupo";
		}


}
