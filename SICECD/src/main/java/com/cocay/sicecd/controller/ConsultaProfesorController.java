package com.cocay.sicecd.controller;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cocay.sicecd.model.Certificado;
import com.cocay.sicecd.model.Grupo;
import com.cocay.sicecd.model.Profesor;
import com.cocay.sicecd.repo.CertificadoRep;
import com.cocay.sicecd.repo.GrupoRep;
import com.cocay.sicecd.repo.ProfesorRep;

@Controller
public class ConsultaProfesorController {
	@Autowired
	ProfesorRep profesorRep;
	
	@Autowired
	GrupoRep grupoRep;
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/consultaProfesor", method = RequestMethod.GET)
	public String consultaProfesor(Model model) {
		return "ConsultarProfesor/consultaProfesor";
	}
	
	/*
	 * @author Derian Estrada
	 */
	@RequestMapping(value = "/consultaProfesor", method = RequestMethod.POST)
	public ModelAndView consultaProfesor(ModelMap model, HttpServletRequest request) {
		try {
			String curps = request.getParameter("curp").toUpperCase().trim();
			String rfcs = request.getParameter("rfc").toUpperCase().trim();
		
			String nombre = normalizar(request.getParameter("nombre")).toUpperCase().trim();
			String apellido_paterno = normalizar(request.getParameter("apellido_paterno")).toUpperCase().trim();
			String apellido_materno = normalizar(request.getParameter("apellido_materno")).toUpperCase().trim();
				
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
		
			//Filtrando por Apellido Materno
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
				model.addAttribute("mensaje", "Tu búsqueda no arrojo ningún resultado");
				return new ModelAndView("/Avisos/ErrorBusqueda");
			}
		}catch (NullPointerException e) {
			LOGGER.error("En la Tabla Profesor se encuentra una columna con todos sus datos con valor " + e.getMessage() + " que provoca el error.");
			model.addAttribute("mensaje", "¡Lo sentimos!\nEn nuestra base de datos no tenemos datos con el cual comparar la información que ingresaste");
			return new ModelAndView("/Avisos/ErrorBusqueda");
		}
	}
	
	public String normalizar(String src) {
		
		if(src == null) {
			return "";
		}
		
        return Normalizer
                .normalize(src , Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]" , "");
    }
	
	/**
	 * Muestra en pantalla la información de un profesor.
	 * @param id_profesor
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ficha_profesor", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView fichaProfesor(@RequestParam("id") int id_profesor, ModelMap model) {
		Profesor p = profesorRep.findByID(id_profesor);
		List<Grupo> grupos = grupoRep.findByIdAsesor(p.getPk_id_profesor());
		
		String nombre = formatoCadena(p.getNombre(), 1);
		String apellido_paterno = formatoCadena(p.getApellido_paterno(), 1);
		String apellido_materno = formatoCadena(p.getApellido_materno(), 1);
		
		model.addAttribute("id_profesor", p.getPk_id_profesor());
		model.addAttribute("nombre", nombre);
		model.addAttribute("apellido_paterno", apellido_paterno);
		model.addAttribute("apellido_materno", apellido_materno);
		
		if (p.getGenero() == null) {
			model.addAttribute("genero", "");
		} else {
			model.addAttribute("genero", p.getGenero().getGenero());
		}
		
		if (p.getFechaNac() == null) {
			model.addAttribute("fecha_nac", "");
		} else {
			SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
			model.addAttribute("fecha_nac", formatter.format(p.getFechaNac()));
		}
		
		String plantel = formatoCadena(p.getPlantel(), 1);
		model.addAttribute("planel", plantel);
		model.addAttribute("clave_plantel", formatoCadena(p.getClave_plantel(), 1));
		
		model.addAttribute("curp", formatoCadena(p.getCurp(), 2));
		model.addAttribute("correo", formatoCadena(p.getCorreo(), 3));
		model.addAttribute("telefono", p.getTelefono());
		
		String localidad = "";
		
		if (p.getCiudad_localidad() == null && p.getFk_id_estado() == null) {
			localidad = "";
		} else if (p.getCiudad_localidad() == null && p.getFk_id_estado() != null) {
			localidad =  p.getFk_id_estado().getNombre();
		} else if (p.getCiudad_localidad() != null && p.getFk_id_estado() == null) {
			localidad = formatoCadena(p.getCiudad_localidad(), 1);
		} else {
			localidad = formatoCadena(p.getCiudad_localidad(), 1) + ", " + p.getFk_id_estado().getNombre();
		}
		
		model.addAttribute("localidad", localidad);
		model.addAttribute("rfc", formatoCadena(p.getRfc(), 2));
		
		if (p.getFk_id_grado_profesor() == null) {
			model.addAttribute("escolaridad", "");
		} else {
			model.addAttribute("escolaridad", p.getFk_id_grado_profesor().getNombre());
		}
		
		if (p.getFk_id_turno() == null) {
			model.addAttribute("turno", "");
		} else {
			model.addAttribute("turno", p.getFk_id_turno().getNombre());
		}
		
		model.put("grupo", grupos);
		model.put("ins", p.getInscripciones());
		return new ModelAndView("/ConsultarProfesor/ficha_profesor");
	}
	
	public String formatoCadena (String cadena, int tipo) {
		String oracion = "";
		
		if (cadena == null) {
			return oracion;
		}
		
		switch (tipo) {
			case 1:
				oracion = cadena.substring(0,1).toUpperCase() + cadena.substring(1).toLowerCase();
				break;
			case 2:
				oracion = cadena.toUpperCase();
				break;
			case 3:
				oracion = cadena.toLowerCase();
				break;
		}
		
		return oracion;
	}
}
