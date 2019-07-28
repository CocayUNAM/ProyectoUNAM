package com.cocay.sicecd.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cocay.sicecd.LogTypes;
import com.cocay.sicecd.dto.GrupoDto;
import com.cocay.sicecd.model.Curso;
import com.cocay.sicecd.model.Grupo;
import com.cocay.sicecd.model.Profesor;
import com.cocay.sicecd.repo.CursoRep;
import com.cocay.sicecd.repo.GrupoRep;
import com.cocay.sicecd.repo.ProfesorRep;
import com.cocay.sicecd.service.Logging;

@Controller
@RequestMapping("AdministracionRegistroManual")
public class GrupoController {
	
	@Autowired
	GrupoRep grupoRep;
	
	@Autowired
	CursoRep cursoRep;
	
	@Autowired
	ProfesorRep profRep;
	
	@Autowired
	Logging log;
	
	// Mapeo del html para registrar cursos
	@RequestMapping(value = "/registrarGrupo2")
	public String registrarInscripcion(Model model, HttpServletRequest request) {
		return "GrupoController/registrarGrupo";
	}
	
	@RequestMapping(value = "/registrarGrupo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> agregarGrupo(@RequestBody GrupoDto gr) {
		
		Grupo grupo = new Grupo();
		
		String clave = gr.getClave();
		
		String curso = gr.getCurso();
		
		String asesor = gr.getAsesor();
		
		String fInicio = gr.getInicio();

		String fTermino = gr.getTermino();
		
		Date fechaI = null;
		try {
			fechaI = new SimpleDateFormat("yyyy-MM-dd").parse(fInicio);
			grupo.setFecha_inicio(fechaI);
		} catch (ParseException e1) {
			e1.printStackTrace();
			grupo.setFecha_inicio(null);
		}
		
		Date fechaT = null;
		try {
			fechaT = new SimpleDateFormat("yyyy-MM-dd").parse(fTermino);
			grupo.setFecha_fin(fechaT);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			grupo.setFecha_fin(null);
		}
		
		grupo.setClave(clave);
		
		List<Curso> cursop = cursoRep.findByClave(curso);
		if(!cursop.isEmpty()) {
			grupo.setFk_id_curso(cursop.get(0));
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
		            .body("¡Curso no encontrado!");
		}
		
		Profesor profe = profRep.findByRfc(asesor);
		if(profe != null) {
			grupo.setFk_id_profesor(profe);
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
		            .body("¡Asesor no encontrado!");
		}
		
		log.setTrace(LogTypes.REGISTRAR_GRUPO);

		grupoRep.save(grupo);
		return ResponseEntity.ok("{\"message\":\"¡Grupo agregado con exito!\"}");
	}
	
}
