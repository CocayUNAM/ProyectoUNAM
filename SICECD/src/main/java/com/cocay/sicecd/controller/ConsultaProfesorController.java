package com.cocay.sicecd.controller;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cocay.sicecd.model.Grupo;
import com.cocay.sicecd.model.Profesor;
import com.cocay.sicecd.repo.GrupoRep;
import com.cocay.sicecd.repo.ProfesorRep;

@Controller
public class ConsultaProfesorController {

	@Autowired
	ProfesorRep profesorRep;
	
	@Autowired
	GrupoRep grupoRep;

	@RequestMapping(value = "/consultaProfesor", method = RequestMethod.GET)
	public String consultaProfesor(Model model) {
		return "ConsultarProfesor/consultaProfesor";
	}
	
	/*
	 * @author Derian Estrada
	 */
	@RequestMapping(value = "/consultaProfesor", method = RequestMethod.POST)
	public ModelAndView consultaProfesor(ModelMap model, HttpServletRequest request) {
		String curps = request.getParameter("curp").toUpperCase().trim();
		String rfcs = request.getParameter("rfc").toUpperCase().trim();
		String nombre, apellido_paterno, apellido_materno;
		
		if (request.getParameter("nombre") != null) {
			nombre = normalizar(request.getParameter("nombre")).toUpperCase().trim();
		} else {
			nombre = "";
		}
		
		if (request.getParameter("apellido_paterno") != null) {
			apellido_paterno = normalizar(request.getParameter("apellido_materno")).toUpperCase().trim();
		} else {
			apellido_paterno = "";
		}
		
		if (request.getParameter("apellido_materno") != null) {
			apellido_materno = normalizar(request.getParameter("apellido_materno")).toUpperCase().trim();
		} else {
			apellido_materno = "";
		}
		
		Integer id_grado = Integer.parseInt(request.getParameter("grado_estudios"));
		Integer id_genero = Integer.parseInt(request.getParameter("genero"));
		Integer id_estado = Integer.parseInt(request.getParameter("estados"));
		Integer id_turno = Integer.parseInt(request.getParameter("turno"));
		
		List<Profesor>	list_p1 = profesorRep.findAll();
		List<Profesor>	list_p2 = profesorRep.findAll();
		
		//Filtrando por CURP
		if (curps != "") {
			for (Profesor p : list_p1) {
				String pcurp = p.getCurp().toUpperCase().trim(); 
				if( !pcurp.contains(curps) ) {
					list_p2.remove(p);
				}
			}
		}
		
		//Filtrando por RFC
		if (rfcs != "") {
			for (Profesor p : list_p1) {
				String prfc = p.getRfc().toUpperCase().trim(); 
				if( !prfc.contains(rfcs) ) {
					list_p2.remove(p);
				}
			}
		}
		
		//Filtrando por Nombre
		if (nombre != "") {
			for (Profesor p : list_p1) {
				String nom = normalizar(p.getNombre()).toUpperCase().trim();
				if( !nom.contains(nombre) ) {
					list_p2.remove(p);
				}
			}
		}
		
		//Filtrando por Apellido Paterno
		if (apellido_paterno != "") {
			for (Profesor p : list_p1) {
				String ap = normalizar(p.getApellido_paterno()).toUpperCase().trim();
				if( !ap.contains(apellido_paterno) ) {
					list_p2.remove(p);
				}
			}
		}
		
		//Filtrando por Apellido Materno
		if (apellido_materno != "") {
			for (Profesor p : list_p1) {
				String am = normalizar(p.getApellido_materno()).toUpperCase().trim();
				if( !am.contains(apellido_materno) ) {
					list_p2.remove(p);
				}
			}
		}
						
		//Filtrando por grado de estudios
		if (id_grado != 5) {
			for(Profesor p : list_p1) {
				if(p.getFk_id_grado_profesor().getPk_id_grado_profesor() != id_grado) {
					list_p2.remove(p);
				}
			}
		}
			
		//Filtrando por género
		if ( id_genero != 3) {
			for(Profesor p : list_p1) {
				if(p.getId_genero().getPk_id_genero() != id_genero) {
					list_p2.remove(p);
				}
			}
		}
			
		//Filtrando por estado
		if(id_estado != 33 ) {
			for(Profesor p : list_p1) {
				if(p.getFk_id_estado().getPk_id_estado() != id_estado) {
					list_p2.remove(p);
				}
			}
		}
			
		//Filtrando por turno
		if( id_turno != 4) {
			for(Profesor p : list_p1) {
				if(p.getFk_id_turno().getPk_id_turno() != id_turno) {
					list_p2.remove(p);
				}
			}
		}
		
		if(!list_p2.isEmpty() || list_p2.size() > 0) {
			model.put("profesores", list_p2);
			return new ModelAndView("/ConsultarProfesor/muestraListaProfesor", model);
		} else {
			return new ModelAndView("/Avisos/ErrorBusqueda");
		}
	}
	
	public String normalizar(String src) {
        return Normalizer
                .normalize(src , Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]" , "");
    }
	
	@RequestMapping(value = "/ficha_profesor", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView fichaProfesor(@RequestParam("id") int id_profesor, ModelMap model) {
		Profesor p = profesorRep.findByID(id_profesor);
		List<Grupo> grupos = grupoRep.findByIdAsesor(p.getPk_id_profesor());
		
		model.addAttribute("nombre", p.getNombre());
		model.addAttribute("apellido_paterno", p.getApellido_paterno());
		model.addAttribute("apellido_materno", p.getApellido_materno());
		
		model.addAttribute("genero", p.getGenero().getGenero());
		
		if (p.getFechaNac() == null) {
			model.addAttribute("fecha_nac", "");
		} else {
			SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
			model.addAttribute("fecha_nac", formatter.format(p.getFechaNac()));
		}
		
		//model.addAttribute("fecha_nac", p.getFechaNac());
		model.addAttribute("planel", p.getPlantel());
		model.addAttribute("clave_plantel", p.getClave_plantel());
		
		model.addAttribute("curp", p.getCurp());
		model.addAttribute("correo", p.getCorreo());
		model.addAttribute("telefono", p.getTelefono());
		
		String localidad = "";
		
		if (p.getCiudad_localidad() == null) {
			localidad =  p.getFk_id_estado().getNombre();
		} else {
			localidad = p.getCiudad_localidad() + ", " + p.getFk_id_estado().getNombre();
		}
		
		model.addAttribute("localidad", localidad);
		model.addAttribute("rfc", p.getRfc());
		model.addAttribute("escolaridad", p.getFk_id_grado_profesor().getNombre());
		model.addAttribute("turno", p.getFk_id_turno().getNombre());
		
		model.put("grupo", grupos);
		model.put("ins", p.getInscripciones());
		return new ModelAndView("/ConsultarProfesor/ficha_profesor");
	}
}
