package com.cocay.sicecd.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cocay.sicecd.model.Grupo;
import com.cocay.sicecd.model.Inscripcion;
import com.cocay.sicecd.model.Profesor;
import com.cocay.sicecd.repo.GrupoRep;
import com.cocay.sicecd.repo.InscripcionRep;
import com.cocay.sicecd.repo.ProfesorRep;

@Controller
public class InscripcionesController {
	
	@Autowired
	InscripcionRep insRep;
	
	@Autowired
	GrupoRep grupoRep;
	
	@Autowired
	ProfesorRep profRep;
	
	//Mapeo del html para registrar cursos
			@RequestMapping(value = "/registrarInscripcion", method = {RequestMethod.POST, RequestMethod.GET})
			public String registrarInscripcion(Model model, HttpServletRequest request){
				
				if(request.getParameterNames().hasMoreElements()) {
					String grupo = request.getParameter("grupo");
					
					String par = request.getParameter("par");
				
					Inscripcion ins = new Inscripcion();
					
					List<Grupo> grupop = grupoRep.findByClave(grupo);
					if(!grupop.isEmpty()) {
						ins.setFk_id_grupo(grupop.get(0));
					}
					
					Profesor profe = profRep.findByRfc(par);
					if(profe != null) {
						ins.setFk_id_profesor(profe);
					}

					insRep.save(ins);
				}
				
				return "InscripcionesController/registrarInscripcion";
			}



}
