package com.cocay.sicecd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cocay.sicecd.model.Profesor;
import com.cocay.sicecd.repo.ProfesorRep;

@Controller
public class ModificarUsuarios {
	
	@Autowired
	ProfesorRep proRep;
		
	@RequestMapping(value = "/listaProfesores", method = RequestMethod.GET)
	public ModelAndView consultarProfesorEstado(ModelMap model) {
		List<Profesor> list_p1 = proRep.findAll();
		
		if(!list_p1.isEmpty()) {
			
			model.put("profesores", list_p1);
			return new ModelAndView("ModificarUsuario/listaProfesores", model);
		} else {
			return new ModelAndView("/Avisos/ErrorBusqueda");
		}
	}
	
	@GetMapping(value = "/pantallaModificacion")
	public ModelAndView formEditarPerfilUsuario(@RequestParam(name = "rfc") String rfc) {
		Profesor cambio = (proRep.findByRfc(rfc.trim()));
		ModelAndView model = new ModelAndView("ModificarUsuario/pantallaModificacion");
		model.addObject("profesor", cambio);
		return model;
	}
	
	@PostMapping(value = "/editarprofesor")
	private ResponseEntity<String> editarProfesor(@RequestBody Profesor profe) {
		int id = profe.getPk_id_profesor();
		
		Profesor mod = proRep.findById(id).get();
		String cambios = "";
		
		if (!mod.getRfc().equals(profe.getRfc())) {
			cambios += "Rfc de " + mod.getRfc() + " a " + profe.getRfc() + "\n";
			mod.setRfc(profe.getRfc());
		}
		if (!mod.getNombre().equals(profe.getNombre())) {
			cambios += "Nombre de " + mod.getNombre() + " a " + profe.getNombre() + "\n";
			mod.setNombre(profe.getNombre());
		}
		if (!mod.getApellido_paterno().equals(profe.getApellido_paterno())) {
			cambios += "Apellido Paterno de " + mod.getApellido_paterno() + " a " + profe.getApellido_paterno()
					+ "\n";
			mod.setApellido_paterno(profe.getApellido_paterno());
		}
		if (!mod.getApellido_materno().equals(profe.getApellido_materno())) {
			cambios += "Apellido Materno de " + mod.getApellido_materno() + " a " + profe.getApellido_materno()
					+ "\n";
			mod.setApellido_materno(profe.getApellido_materno());
		}

		if (!cambios.equals("")) {
			proRep.save(mod);
		}

		return ResponseEntity.ok("Usuario Editado con exito");

	}
}
