package com.cocay.sicecd.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cocay.sicecd.LogTypes;
import com.cocay.sicecd.dto.InscripcionDto;
import com.cocay.sicecd.model.Grupo;
import com.cocay.sicecd.model.Inscripcion;
import com.cocay.sicecd.model.Profesor;
import com.cocay.sicecd.repo.GrupoRep;
import com.cocay.sicecd.repo.InscripcionRep;
import com.cocay.sicecd.repo.ProfesorRep;
import com.cocay.sicecd.service.Logging;

@Controller
@RequestMapping("AdministracionRegistroManual")
public class InscripcionesController {

	@Autowired
	InscripcionRep insRep;

	@Autowired
	GrupoRep grupoRep;

	@Autowired
	ProfesorRep profRep;
	
	@Autowired
	Logging log;

	// Mapeo del html para registrar cursos
	@RequestMapping(value = "/registrarInscripcion2")
	public String registrarInscripcion(Model model, HttpServletRequest request) {
		return "InscripcionesController/registrarInscripcion";
	}

	@RequestMapping(value = "/registrarInscripcion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> agregarIns(@RequestBody InscripcionDto ins) {
		Inscripcion inst = new Inscripcion();

		String grupo = ins.getIdGrupo();

		String par = ins.getIdProfesor();
		
		String cal = ins.getCalificacion();
		
		boolean ap = ins.isAprobado();
		
		List<Grupo> grupop = grupoRep.findByClave(grupo);
		if (!grupop.isEmpty()) {
			inst.setFk_id_grupo(grupop.get(0));
		}

		Profesor profe = profRep.findByRfc(par);
		if (profe != null) {
			inst.setFk_id_profesor(profe);
		}
		
		inst.setCalif(cal);
		
		inst.setAprobado(ap);
		
		log.setTrace(LogTypes.REGISTRAR_INSCRIPCION);

		insRep.save(inst);
		return ResponseEntity.ok("{\"message\":\"¡Inscripcion agregada con exito!\"}");
	}

}
