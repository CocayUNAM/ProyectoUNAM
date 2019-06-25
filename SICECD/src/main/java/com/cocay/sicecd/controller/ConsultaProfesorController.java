package com.cocay.sicecd.controller;

import java.text.Normalizer;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cocay.sicecd.model.Profesor;
import com.cocay.sicecd.repo.ProfesorRep;

@Controller
public class ConsultaProfesorController {

	@Autowired
	ProfesorRep profesor;

	@RequestMapping(value = "/consultaProfesor", method = RequestMethod.GET)
	public String consultaProfesor(Model model) {
		return "ConsultarProfesor/consultaProfesor";
	}

	@RequestMapping(value = "/consultarProfesorRFC", method = RequestMethod.POST)
	public ModelAndView consultarProfesor(ModelMap model, HttpServletRequest request) {
		String rfcs = request.getParameter("rfc");
		Profesor p = profesor.findByRfc(rfcs);
		if (p != null) {
			System.out.println(p.getCorreo());
			System.out.println(p.getRfc());
			model.addAttribute("nombre", p.getNombre());
			model.addAttribute("apellido_paterno", p.getApellido_paterno());
			model.addAttribute("apellido_materno", p.getApellido_materno());
			model.addAttribute("correo", p.getCorreo());
			model.addAttribute("rfc", p.getRfc());
			model.addAttribute("estado", p.getFk_id_estado().getNombre());
			model.addAttribute("grado", p.getFk_id_grado_profesor().getNombre());
			model.addAttribute("clave-plantel", p.getClave_plantel());
			model.addAttribute("ocupacion", p.getOcupacion());
			model.addAttribute("inscripcion", p.getInscripciones());
			return new ModelAndView("/ConsultarProfesor/muestraProfesor", model);			
		} else {
			return new ModelAndView("/Avisos/ErrorBusqueda");
		}
	}
	
	/*
	 * @author Derian Estrada
	 */
	@RequestMapping(value = "/consultarProfesorPersonalizado", method = RequestMethod.POST)
	public ModelAndView consultarProfesorPersonalizado(ModelMap model, HttpServletRequest request) {
		String curps = request.getParameter("curp").toUpperCase();
		String rfcs = request.getParameter("rfc").toUpperCase();
		String nombre = normalizar(request.getParameter("nombre")).toUpperCase();
		String apellido_paterno = normalizar(request.getParameter("apellido_paterno")).toUpperCase();
		String apellido_materno = normalizar(request.getParameter("apellido_materno")).toUpperCase();
		
		Integer id_grado = Integer.parseInt(request.getParameter("grado_estudios"));
		Integer id_genero = Integer.parseInt(request.getParameter("genero"));
		Integer id_estado = Integer.parseInt(request.getParameter("estados"));
		Integer id_turno = Integer.parseInt(request.getParameter("turno"));
		
		List<Profesor>	list_p1 = profesor.findAll();
		List<Profesor>	list_p2 = profesor.findAll();
		
		//Filtrando por CURP
		if (curps != "") {
			for (Profesor p : list_p1) {
				String pcurp = p.getCurp().toUpperCase(); 
				if( !pcurp.contains(curps) ) {
					list_p2.remove(p);
				}
			}
		}
		
		//Filtrando por RFC
		if (rfcs != "") {
			for (Profesor p : list_p1) {
				String prfc = p.getRfc().toUpperCase(); 
				if( !prfc.contains(rfcs) ) {
					list_p2.remove(p);
				}
			}
		}
		
		//Filtrando por Apellido Paterno
		if (nombre != "") {
			for (Profesor p : list_p1) {
				String ap = normalizar(p.getApellido_paterno()).toUpperCase(); 
				if( !ap.contains(apellido_paterno) ) {
					list_p2.remove(p);
				}
			}
		}
		
		//Filtrando por Apellido Materno
		if (nombre != "") {
			for (Profesor p : list_p1) {
				String am = normalizar(p.getApellido_materno()).toUpperCase(); 
				if( !am.contains(apellido_materno) ) {
					list_p2.remove(p);
				}
			}
		}
		
		//Filtrando por Nombre
		if (nombre != "") {
			for (Profesor p : list_p1) {
				String nom = normalizar(p.getNombre()).toUpperCase();
				if( !nom.contains(nombre) ) {
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
			
		//Filtrando por gÃ©nero
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
		
		if(!list_p1.isEmpty()) {
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
}
