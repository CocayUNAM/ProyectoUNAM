package com.cocay.sicecd.controller;

import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.cocay.sicecd.model.Curso;
import com.cocay.sicecd.model.Grupo;
import com.cocay.sicecd.repo.CursoRep;


@Controller
public class ConsultaCursoController {
	@Autowired
	CursoRep curso;
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value = "/consultaCurso", method = RequestMethod.GET)
	public String consultaCurso(Model model) {
		return "ConsultarCurso/consultarCurso";
	}
	
	/*
	 * Consulta de Cursos.
	 * @author Derian Estrada
	 */
	@RequestMapping(value = "/consultaCurso", method = RequestMethod.POST)
	public ModelAndView consultaSimpleCurso(ModelMap model,HttpServletRequest request) throws ParseException {
		
		try {
			String nombre_curso = normalizar(request.getParameter("nombre_curso")).toUpperCase().trim();
			String clave_curso = request.getParameter("clave_curso").toUpperCase().trim();
			Integer id_tipo = Integer.parseInt(request.getParameter("tipos_cursos"));
		
			List<Curso> cursos1, cursos2;
			cursos1 = curso.findAll();
			cursos2 = curso.findAll();
		
			//Filtrando por tipo de curso
			if (id_tipo != 0) {
				for(Curso c : cursos1) {
					if(c.getFk_id_tipo_curso().getPk_id_tipo_curso() != id_tipo ) {
						cursos2.remove(c);
					}
				}
			}
		
			//Filtrando por clave de curso
			if (clave_curso != "") {
				for(Curso c : cursos1) {
					String cclave = normalizar(c.getClave()).toUpperCase().trim();
					if(!cclave.contains(clave_curso)){
						cursos2.remove(c);
					}
				}
			}
		
			//Filtrando por nombre de curso
			if (nombre_curso != "") {
				for(Curso c : cursos1) {
					String cnom = normalizar(c.getNombre()).toUpperCase().trim();
					if(!cnom.contains(nombre_curso)){
						cursos2.remove(c);
					}
				}
			}
		
			if(!cursos2.isEmpty() || cursos2.size() > 0 ) {
				model.put("cursos", cursos2);
				return new ModelAndView("ConsultarCurso/muestraListaCurso",model);
			} else {
				return new ModelAndView("/Avisos/ErrorBusqueda");
			}
		} catch (NullPointerException e) {
			LOGGER.error("En la Tabla Curso se encuentra una columna con todos sus datos con valor " + e.getMessage() + " que provoca el error.");
			model.addAttribute("mensaje", "¡Lo sentimos!\nEn nuestra base de datos no tenemos datos con el cual comparar la información que ingresaste");
			return new ModelAndView("/Avisos/ErrorBusqueda");
		}
	}
	
	public String normalizar(String src) {
        return Normalizer
                .normalize(src , Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]" , "");
    }
}
