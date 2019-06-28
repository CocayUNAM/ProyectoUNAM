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
		Profesor cambio = (proRep.findByRfc(rfc));
		ModelAndView model = new ModelAndView("ModificarUsuario/pantallaModificacion");
		model.addObject("profesor", cambio);
		return model;
	}
	
	@PostMapping(value = "/editarprofesor")
	private ResponseEntity<String> editarProfesor(@RequestBody Profesor profesor) {
		int id = profesor.getPk_id_profesor();
		
		Profesor mod = proRep.findById(id).get();
		
		String cambios = "";
		
		if (!mod.getRfc().equals(profesor.getRfc())) {
			cambios += "Rfc de " + mod.getRfc() + " a " + profesor.getRfc() + "\n";
			mod.setRfc(profesor.getRfc());
		}
		if (!mod.getNombre().equals(profesor.getNombre())) {
			cambios += "Nombre de " + mod.getNombre() + " a " + profesor.getNombre() + "\n";
			mod.setNombre(profesor.getNombre());
		}
		if (!mod.getApellido_paterno().equals(profesor.getApellido_paterno())) {
			cambios += "Apellido Paterno de " + mod.getApellido_paterno() + " a " + profesor.getApellido_paterno()
					+ "\n";
			mod.setApellido_paterno(profesor.getApellido_paterno());
		}
		if (!mod.getApellido_materno().equals(profesor.getApellido_materno())) {
			cambios += "Apellido Materno de " + mod.getApellido_materno() + " a " + profesor.getApellido_materno()
					+ "\n";
			mod.setApellido_materno(profesor.getApellido_materno());
		}
		
		if(mod.getCurp() == null) {
			cambios += "Curp de " + mod.getCurp() + " a " + profesor.getCurp()
			+ "\n";
			mod.setCurp(profesor.getCurp());
		} else {
			if (!mod.getCurp().equals(profesor.getCurp())) {
				cambios += "Curp de " + mod.getCurp() + " a " + profesor.getCurp()
						+ "\n";
				mod.setCurp(profesor.getCurp());
			}
		}
		
		if (!mod.getCorreo().equals(profesor.getCorreo())) {
			cambios += "Correo de " + mod.getCorreo() + " a " + profesor.getCorreo()
					+ "\n";
			mod.setCorreo(profesor.getCorreo());
		}
		
		if(mod.getTelefono() == null) {
			cambios += "Telefono de " + mod.getTelefono() + " a " + profesor.getTelefono()
			+ "\n";
			mod.setTelefono(profesor.getTelefono());
		} else {
			if (!mod.getTelefono().equals(profesor.getTelefono())) {
				cambios += "Telefono de " + mod.getTelefono() + " a " + profesor.getTelefono()
						+ "\n";
				mod.setTelefono(profesor.getTelefono());
			}
		}
		
		if(mod.getCiudad_localidad() == null) {
			cambios += "Ciudad o localidad de " + mod.getCiudad_localidad() + " a " + profesor.getCiudad_localidad()
			+ "\n";
			mod.setCiudad_localidad(profesor.getCiudad_localidad());
		} else {
			if (!mod.getCiudad_localidad().equals(profesor.getCiudad_localidad())) {
				cambios += "Ciudad o localidad de " + mod.getCiudad_localidad() + " a " + profesor.getCiudad_localidad()
				+ "\n";
				mod.setCiudad_localidad(profesor.getCiudad_localidad());
			}
		}
		
		if(mod.getPlantel() == null) {
			cambios += "Plantel de " + mod.getPlantel() + " a " + profesor.getPlantel()
			+ "\n";
			mod.setPlantel(profesor.getPlantel());
		} else {
			if (!mod.getPlantel().equals(profesor.getPlantel())) {
				cambios += "Plantel de " + mod.getPlantel() + " a " + profesor.getPlantel()
				+ "\n";
				mod.setPlantel(profesor.getPlantel());
			}
		}
		
		if(mod.getClave_plantel() == null) {
			cambios += "clave de Plantel de " + mod.getClave_plantel() + " a " + profesor.getClave_plantel()
			+ "\n";
			mod.setClave_plantel(profesor.getClave_plantel());
		} else {
			if (!mod.getClave_plantel().equals(profesor.getClave_plantel())) {
				cambios += "clave de Plantel de " + mod.getClave_plantel() + " a " + profesor.getClave_plantel()
				+ "\n";
				mod.setClave_plantel(profesor.getClave_plantel());
			}
		}
		
		if(mod.getOcupacion() == null) {
			cambios += "Ocupacion de " + mod.getOcupacion() + " a " + profesor.getOcupacion()
			+ "\n";
			mod.setOcupacion(profesor.getOcupacion());
		} else {
			if (!mod.getOcupacion().equals(profesor.getOcupacion())) {
				cambios += "Ocupacion de " + mod.getOcupacion() + " a " + profesor.getOcupacion()
				+ "\n";
				mod.setOcupacion(profesor.getOcupacion());
			}
		}
		
		if (!mod.getFk_id_estado().equals(profesor.getFk_id_estado())) {
			cambios += "Estado de " + mod.getFk_id_estado().getNombre() + " a " + profesor.getFk_id_estado().getNombre()
			+ "\n";
			mod.setFk_id_estado(profesor.getFk_id_estado());
		}
		
		System.out.println(cambios);
		
		if (!cambios.equals("")) {
			proRep.save(mod);
		}

		return ResponseEntity.ok("¡Participante editado con exito!");

	}
}
