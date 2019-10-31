package com.cocay.sicecd.controller;

import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cocay.sicecd.model.Grupo;
import com.cocay.sicecd.repo.CursoRep;
import com.cocay.sicecd.repo.GrupoRep;

@Controller
public class ConsultaGrupoController {
	
	@Autowired
	GrupoRep grupo;
	@Autowired
	CursoRep curso;
	@Autowired
	ConsultaGrupoController controller;
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value = "/consultaGrupo", method = RequestMethod.GET)
	public String consultaCurso(Model model) {
		return "ConsultarGrupo/consultaGrupo";
	}
	
	/*
	 * Consulta Simple de los Grupos.
	 * @author Derian Estrada
	 */
	@RequestMapping(value = "/consultaSimpleGrupo", method = RequestMethod.POST)
	public ModelAndView consultaSimpleGrupo(ModelMap model,HttpServletRequest request) throws ParseException {
		try {
			String fecha_inicio_grupo = request.getParameter("fecha_inicio_grupo");
			String fecha_fin_grupo = request.getParameter("fecha_fin_grupo");
			String clave_grupo = request.getParameter("clave_grupo").toUpperCase().trim();
			String curso_grupo = request.getParameter("curso_grupo").toUpperCase().trim();
		
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date fecha_ini, fecha_fin;
			List<Grupo> grupos, grupos2;
			
			if (fecha_inicio_grupo != "" && fecha_fin_grupo != ""){
				fecha_ini = format.parse(fecha_inicio_grupo);
				fecha_fin = format.parse(fecha_fin_grupo);
				grupos = grupo.findByFechaInicio(fecha_ini, fecha_fin, clave_grupo);
				grupos2 = grupo.findByFechaInicio(fecha_ini, fecha_fin, clave_grupo);
			} else {
				grupos = grupo.findByClave(clave_grupo);
				grupos2 = grupo.findByClave(clave_grupo);
			}
		
			//Filtrando por clave de curso
			if (curso_grupo != null) {
				for(Grupo g : grupos2) {
					String gclave = normalizar( g.getFk_id_curso().getClave() ).toUpperCase().trim();
					if( !gclave.contains(curso_grupo) ) {
						grupos.remove(g);
					}
				}
			}
		
			if(!grupos.isEmpty() || grupos.size() > 0) {
				model.put("grupos", grupos);
				model.put("controller", controller);
				return new ModelAndView("ConsultarGrupo/muestraListaGrupo",model);
			} else {
				model.addAttribute("mensaje", "Tu búsqueda no arrojo ningún resultado");
				return new ModelAndView("/Avisos/ErrorBusqueda");
			}
		} catch (NullPointerException e) {
			LOGGER.error("En la Tabla Grupo se encuentra una columna con todos sus datos con valor " + e.getMessage() + " que provoca el error.");
			model.addAttribute("mensaje", "¡Lo sentimos!\nEn nuestra base de datos no tenemos datos con el cual comparar la información que ingresaste");
			return new ModelAndView("/Avisos/ErrorBusqueda");
		}
	}
	
	/*
	 * Consulta Avanzada de los Grupos.
	 * @author Derian Estrada
	 */
	@RequestMapping(value = "/consultaAvanzadaGrupo", method = RequestMethod.POST)
	public ModelAndView consultaAvanzadaGrupo(ModelMap model,HttpServletRequest request) throws ParseException {
		try {
			String fecha_inicio_grupo_1 = request.getParameter("fecha_inicio_grupo_1");
			String fecha_inicio_grupo_2 = request.getParameter("fecha_inicio_grupo_2");
			String fecha_fin_grupo_1 = request.getParameter("fecha_fin_grupo_1");
			String fecha_fin_grupo_2 = request.getParameter("fecha_fin_grupo_2");
			String clave_grupo = request.getParameter("clave_grupo").toUpperCase().trim();
			String curso_grupo = request.getParameter("curso_grupo").toUpperCase().trim();
		
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date fecha_ini_1, fecha_ini_2, fecha_fin_1, fecha_fin_2;
			List<Grupo> grupos, grupos2;
		
			if(fecha_inicio_grupo_1 != "" && fecha_fin_grupo_1 == "") {
				fecha_ini_1 = format.parse(fecha_inicio_grupo_1);
				fecha_ini_2 = format.parse(fecha_inicio_grupo_2);
				grupos = grupo.findByFechaInicio(fecha_ini_1, fecha_ini_2, clave_grupo);
				grupos2 = grupo.findByFechaInicio(fecha_ini_1, fecha_ini_2, clave_grupo);
			}else if (fecha_inicio_grupo_1 == "" && fecha_fin_grupo_1 != ""){
				fecha_fin_1 = format.parse(fecha_fin_grupo_1);
				fecha_fin_2 = format.parse(fecha_fin_grupo_2);
				grupos = grupo.findByFechaFin(fecha_fin_1, fecha_fin_2, clave_grupo);
				grupos2 = grupo.findByFechaFin(fecha_fin_1, fecha_fin_2, clave_grupo);
			}else if (fecha_inicio_grupo_1 != "" && fecha_fin_grupo_1 != ""){
				fecha_ini_1 = format.parse(fecha_inicio_grupo_1);
				fecha_ini_2 = format.parse(fecha_inicio_grupo_2);
				fecha_fin_1 = format.parse(fecha_fin_grupo_1);
				fecha_fin_2 = format.parse(fecha_fin_grupo_2);
				grupos = grupo.findByFecha(fecha_ini_1, fecha_ini_2, fecha_fin_1, fecha_fin_2, clave_grupo);
				grupos2 = grupo.findByFecha(fecha_ini_1, fecha_ini_2, fecha_fin_1, fecha_fin_2, clave_grupo);
			}else {
				grupos = grupo.findAll();
				grupos2 = grupo.findAll();
			}
		
			//Filtrando por clave de curso
			if (curso_grupo != null) {
				for(Grupo g : grupos2) {
					String gclave = normalizar( g.getFk_id_curso().getClave() ).toUpperCase().trim();
					if( !gclave.contains(curso_grupo) ) {
						grupos.remove(g);
					}
				}
			}
		
			if(!grupos.isEmpty() || grupos.size() > 0) {
				model.put("grupos", grupos);
				model.put("controller", controller);
				return new ModelAndView("ConsultarGrupo/muestraListaGrupo",model);
			} else {
				model.addAttribute("mensaje", "Tu búsqueda no arrojo ningún resultado");
				return new ModelAndView("/Avisos/ErrorBusqueda");
			}
		} catch (NullPointerException e) {
			LOGGER.error("En la Tabla Grupo se encuentra una columna con todos sus datos con valor " + e.getMessage() + " que provoca el error.");
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
	 * Le aplica un formato al nombre de un profesor para que cada letra
	 * inicial de su nombre y apellidos sea mayúscula y el resto minúsculas 
	 * @param cadena
	 * @return
	 */
	public String formatoNombre (String profesor) {
		String nombre = "";
		
		if (profesor.isBlank()) {
			return nombre;
		}
		
		String[] cadenas = profesor.split(" ");
		
		for(int i = 0; i < cadenas.length-1; i++) {
			nombre+= cadenas[i].substring(0,1).toUpperCase() + cadenas[i].substring(1).toLowerCase() + " ";
		}
				
		nombre+= cadenas[cadenas.length-1].substring(0,1).toUpperCase() + cadenas[cadenas.length-1].substring(1).toLowerCase();
		
		return nombre;
	}
}