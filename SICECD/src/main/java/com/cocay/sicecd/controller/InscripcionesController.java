package com.cocay.sicecd.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
	
	@RequestMapping(value = "/registrarInscripcion2", method = RequestMethod.GET)
	public ModelAndView registrarInscripcion(ModelMap model) {
		
		List<Grupo> list_p1 = grupoRep.findAll();
		List<Profesor> list_p2 = profRep.findAll();
		
		List<String> grupos = new ArrayList<String>();
		List<String> rfcs = new ArrayList<String>();
		
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		
		for(Grupo g : list_p1) {
			grupos.add(g.getClave());
			sb1.append(g.getClave() + ",");
		}
		
		for(Profesor p : list_p2) {
			rfcs.add(p.getRfc());
			sb2.append(p.getRfc() + ",");
		}
		
		String re = sb1.toString();
		sb1.setLength(re.length() - 1);
		
		String rep = sb2.toString();
		sb2.setLength(rep.length() - 1);

		InscripcionDto in = new InscripcionDto();

		in.setJsonG(sb1.toString());
		in.setJsonP(sb2.toString());
		
		System.out.println(sb1.toString());
		System.out.println(sb2.toString());
		
		if(!list_p1.isEmpty()) {
			model.put("datos", in);
			return new ModelAndView("InscripcionesController/registrarInscripcion", model);
		} else {
			return new ModelAndView("/Avisos/ErrorBusqueda");
		}
	}

	@RequestMapping(value = "/registrarInscripcion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> agregarIns(@RequestBody InscripcionDto ins) {
		Inscripcion inst = new Inscripcion();

		String grupo = ins.getIdGrupo();

		String par = ins.getIdProfesor();
		
		String cal = ins.getCalificacion();
		
		Boolean ap = ins.isAprobado();
		
		List<Grupo> grupop = grupoRep.findByClave(grupo);
		if (!grupop.isEmpty()) {
			inst.setFk_id_grupo(grupop.get(0));
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
		            .body("¡Grupo no encontrado!");
		}

		Profesor profe = profRep.findByRfc(par);
		if (profe != null) {
			inst.setFk_id_profesor(profe);
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
		            .body("¡Profesor no encontrado!");
		}
		
		inst.setCalif(cal);
		
		inst.setAprobado(ap);
		
		log.setTrace(LogTypes.REGISTRAR_INSCRIPCION);

		insRep.save(inst);
		return ResponseEntity.ok("{\"message\":\"¡Inscripcion agregada con exito!\"}");
	}

}
