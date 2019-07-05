package com.cocay.sicecd.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import com.cocay.sicecd.LogTypes;
import com.cocay.sicecd.model.Curso;
import com.cocay.sicecd.model.Grupo;
import com.cocay.sicecd.model.Inscripcion;
import com.cocay.sicecd.model.Profesor;
import com.cocay.sicecd.repo.CursoRep;
import com.cocay.sicecd.repo.GrupoRep;
import com.cocay.sicecd.repo.InscripcionRep;
import com.cocay.sicecd.repo.ProfesorRep;
import com.cocay.sicecd.service.Logging;

@Controller
public class ModificarUsuarios {
	
	@Autowired
	ProfesorRep proRep;
	
	@Autowired
	InscripcionRep insRep;
	
	@Autowired
	GrupoRep grRep;
	
	@Autowired
	CursoRep crRep;
		
	/*
	 * Modificacion de Participantes.
	 * */
	@RequestMapping(value = "/listaProfesores", method = RequestMethod.GET)
	public ModelAndView consultarProfesorEstado(ModelMap model) {
		List<Profesor> list_p1 = proRep.findAll();
		
		//Se filtran a los participantes de los asesores
		list_p1 = list_p1.stream().filter(x -> x.getFechaNac() == null).collect(Collectors.toList());
		
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
		
		if (!mod.getFk_id_estado().getNombre().equals(profesor.getFk_id_estado().getNombre())) {
			cambios += "Estado de " + mod.getFk_id_estado().getNombre() + " a " + profesor.getFk_id_estado().getNombre()
			+ "\n";
			mod.setFk_id_estado(mod.getFk_id_estado());
		}
		
		if (!mod.getFk_id_grado_profesor().equals(profesor.getFk_id_grado_profesor())) {
			cambios += "Estado de " + mod.getFk_id_grado_profesor().getNombre() + " a " + profesor.getFk_id_grado_profesor().getNombre()
			+ "\n";
			mod.setFk_id_grado_profesor(mod.getFk_id_grado_profesor());
		}
		
		if (!mod.getFk_id_turno().equals(profesor.getFk_id_turno())) {
			cambios += "Estado de " + mod.getFk_id_turno().getNombre() + " a " + profesor.getFk_id_turno().getNombre()
			+ "\n";
			mod.setFk_id_turno(mod.getFk_id_turno());
		}
		
		System.out.println(cambios);
		
		if (!cambios.equals("")) {
			proRep.save(mod);
		}

		return ResponseEntity.ok("¡Participante editado con exito!");
	}
	
	/*
	 * Modificacion de inscripciones
	 * */
	
	@RequestMapping(value = "/listaInscripciones", method = RequestMethod.GET)
	public ModelAndView consultarInscripciones(ModelMap model) {
		List<Inscripcion> list_p1 = insRep.findAll();
		
		if(!list_p1.isEmpty()) {
			
			model.put("inscripciones", list_p1);
			return new ModelAndView("ModificarUsuario/listaInscripciones", model);
		} else {
			return new ModelAndView("/Avisos/ErrorBusqueda");
		}
	}
	
	@GetMapping(value = "/pantallaModificacionI")
	public ModelAndView formEditarInscripcion(@RequestParam(name = "id") int id) {
		Optional<Inscripcion> cambio = insRep.findById(id);
		Inscripcion insi = cambio.get();
		ModelAndView model = new ModelAndView("ModificarUsuario/pantallaModificacionI");
		model.addObject("inscripcion", insi);
		return model;
	}
	
	@PostMapping(value = "/editarinscripcion")
	private ResponseEntity<String> editarInsc(@RequestBody Inscripcion ins) {
		int id = ins.getPk_id_inscripcion();
		
		Inscripcion mod = insRep.findById(id).get();
		
		String cambios = "";
		
		if (!mod.getCalificacion().equals(ins.getCalificacion())) {
			cambios += "Rfc de " + mod.getCalificacion() + " a " + ins.getCalificacion() + "\n";
			mod.setCalificacion(ins.getCalificacion());
		}
		
		if (!mod.getFk_id_grupo().getClave().equals(ins.getFk_id_grupo().getClave())) {
			cambios += "Estado de " + mod.getFk_id_grupo().getClave() + " a " + ins.getFk_id_grupo().getClave()
			+ "\n";
			mod.setFk_id_grupo(ins.getFk_id_grupo());
		}
		
		if (!mod.getFk_id_profesor().getNombre().equals(ins.getFk_id_profesor().getNombre())) {
			cambios += "Estado de " + mod.getFk_id_profesor().getNombre() + " a " + ins.getFk_id_profesor().getNombre()
			+ "\n";
			mod.setFk_id_profesor(ins.getFk_id_profesor());
		}
		
		System.out.println(cambios);
		
		if (!cambios.equals("")) {
			insRep.save(mod);
		}

		return ResponseEntity.ok("¡Inscripcion editada con exito!");
	}
	
	/*
	 * Modificacion de grupos
	 * */
	@RequestMapping(value = "/listaGrupos", method = RequestMethod.GET)
	public ModelAndView consultarGrupos(ModelMap model) {
		List<Grupo> list_p1 = grRep.findAll();
		
		if(!list_p1.isEmpty()) {
			
			model.put("grupos", list_p1);
			return new ModelAndView("ModificarUsuario/listaGrupos", model);
		} else {
			return new ModelAndView("/Avisos/ErrorBusqueda");
		}
	}
	
	@GetMapping(value = "/pantallaModificacionG")
	public ModelAndView formEditarGrupo(@RequestParam(name = "id") int id) {
		Grupo gr = grRep.findById(id).get();
		ModelAndView model = new ModelAndView("ModificarUsuario/pantallaModificacionG");
		model.addObject("grupo", gr);
		return model;
	}
	
	@PostMapping(value = "/editargrupo")
	private ResponseEntity<String> editarGrupo(@RequestBody Grupo grp) {
		int id = grp.getPk_id_grupo();
		
		Grupo mod = grRep.findById(id).get();
		
		String cambios = "";
		
		if (!mod.getClave().equals(grp.getClave())) {
			cambios += "Clave de " + mod.getClave() + " a " + grp.getClave() + "\n";
			mod.setClave(grp.getClave());
		}
		
		if (!mod.getFecha_inicio().equals(grp.getFecha_inicio())) {
			cambios += "Fecha de inicio de " + mod.getFecha_inicio() + " a " + grp.getFecha_inicio() + "\n";
			mod.setFecha_inicio(grp.getFecha_inicio());
		}
		
		if (!mod.getFecha_fin().equals(grp.getFecha_fin())) {
			cambios += "Fecha de fin de " + mod.getFecha_fin() + " a " + grp.getFecha_fin() + "\n";
			mod.setFecha_fin(grp.getFecha_fin());
		}
		
		if (!mod.getFk_id_curso().getClave().equals(grp.getFk_id_curso().getClave())) {
			cambios += "Estado de " + mod.getFk_id_curso().getClave() + " a " + grp.getFk_id_curso().getClave()
			+ "\n";
			mod.setFk_id_curso(grp.getFk_id_curso());
		}
		
		if (!mod.getFk_id_profesor().getNombre().equals(grp.getFk_id_profesor().getNombre())) {
			cambios += "Estado de " + mod.getFk_id_profesor().getNombre() + " a " + grp.getFk_id_profesor().getNombre()
			+ "\n";
			mod.setFk_id_profesor(grp.getFk_id_profesor());
		}
		
		System.out.println(cambios);
		
		if (!cambios.equals("")) {
			grRep.save(mod);
		}

		return ResponseEntity.ok("¡Grupo editado con exito!");
	}
	
	/*
	 * Modificacion de cursos
	 * */
	@RequestMapping(value = "/listaCursos", method = RequestMethod.GET)
	public ModelAndView consultarCursos(ModelMap model) {
		List<Curso> list_p1 = crRep.findAll();
		
		if(!list_p1.isEmpty()) {
			
			model.put("cursos", list_p1);
			return new ModelAndView("ModificarUsuario/listaCursos", model);
		} else {
			return new ModelAndView("/Avisos/ErrorBusqueda");
		}
	}
	
	@GetMapping(value = "/pantallaModificacionC")
	public ModelAndView formEditarCurso(@RequestParam(name = "id") int id) {
		Curso cr = crRep.findById(id).get();
		ModelAndView model = new ModelAndView("ModificarUsuario/pantallaModificacionC");
		model.addObject("curso", cr);
		return model;
	}
	
	@PostMapping(value = "/editarcurso")
	private ResponseEntity<String> editarCurso(@RequestBody Curso cr) {
		int id = cr.getPk_id_curso();
		
		Curso mod = crRep.findById(id).get();
		
		String cambios = "";
		
		if (!mod.getClave().equals(cr.getClave())) {
			cambios += "Clave de " + mod.getClave() + " a " + cr.getClave() + "\n";
			mod.setClave(cr.getClave());
		}
		
		if (!mod.getFk_id_tipo_curso().getNombre().equals(cr.getFk_id_tipo_curso().getNombre())) {
			cambios += "Tipo curso de " + mod.getFk_id_tipo_curso().getNombre() + " a " + cr.getFk_id_tipo_curso().getNombre()
			+ "\n";
			mod.setFk_id_tipo_curso(cr.getFk_id_tipo_curso());
		}
		
		if (mod.getHoras() != cr.getHoras()) {
			cambios += "Horas de " + mod.getHoras() + " a " + cr.getHoras() + "\n";
			mod.setHoras(cr.getHoras());
		}
		
		if (!mod.getNombre().equals(cr.getNombre())) {
			cambios += "Nombre de " + mod.getNombre() + " a " + cr.getNombre() + "\n";
			mod.setNombre(cr.getNombre());
		}
		
		if (!mod.getfInicio().equals(cr.getfInicio())) {
			cambios += "Fecha de inicio de " + mod.getfInicio() + " a " + cr.getfInicio()
			+ "\n";
			mod.setfInicio(cr.getfInicio());
		}
		
		if (!mod.getfTermino().equals(cr.getfTermino())) {
			cambios += "Fecha de termino de " + mod.getfTermino() + " a " + cr.getfTermino()
			+ "\n";
			mod.setfTermino(cr.getfTermino());
		}
		
		System.out.println(cambios);
		
		if (!cambios.equals("")) {
			crRep.save(mod);
		}

		return ResponseEntity.ok("¡Curso editado con exito!");
	}
	
	/*
	 * Modificar Asesores
	 * */
	@RequestMapping(value = "/listaAsesores", method = RequestMethod.GET)
	public ModelAndView consultarAsesores(ModelMap model) {
		List<Profesor> list_p1 = proRep.findAll();
		
		//Se filtran todos los profesores que no tengan fecha de nacimiento
		list_p1 = list_p1.stream().filter(x -> x.getFechaNac() != null).collect(Collectors.toList());
		
		if(!list_p1.isEmpty()) {
			model.put("asesores", list_p1);
			return new ModelAndView("ModificarUsuario/listaAsesores", model);
		} else {
			return new ModelAndView("/Avisos/ErrorBusqueda");
		}
	}
	
	@GetMapping(value = "/pantallaModificacionA")
	public ModelAndView formEditarAsesor(@RequestParam(name = "rfc") String rfc) {
		Profesor cambio = (proRep.findByRfc(rfc));
		ModelAndView model = new ModelAndView("ModificarUsuario/pantallaModificacionA");
		model.addObject("asesor", cambio);
		return model;
	}
	
	@PostMapping(value = "/editarasesor")
	private ResponseEntity<String> editarAsesor(@RequestBody Profesor profesor) {
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
		
		if (!mod.getFechaNac().equals(profesor.getFechaNac())) {
			cambios += "Fecha de termino de " + mod.getFechaNac() + " a " + profesor.getFechaNac()
			+ "\n";
			mod.setFechaNac(profesor.getFechaNac());
		}
		
		System.out.println(cambios);
		
		if (!cambios.equals("")) {
			proRep.save(mod);
		}

		return ResponseEntity.ok("¡Asesor editado con exito!");
	}
}
