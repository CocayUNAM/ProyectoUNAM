package com.cocay.sicecd.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cocay.sicecd.model.Estado;
import com.cocay.sicecd.model.Profesor;
import com.cocay.sicecd.repo.EstadoRep;
import com.cocay.sicecd.repo.GeneroRep;
import com.cocay.sicecd.repo.Grado_profesorRep;
import com.cocay.sicecd.repo.ProfesorRep;
import com.cocay.sicecd.repo.TurnoRep;

@Controller
public class ProfesoresController {
	
	@Autowired
	ProfesorRep profRep;
	
	@Autowired
	private EstadoRep stRep;
	
	@Autowired
	private Grado_profesorRep gpRep;
	
	@Autowired
	private TurnoRep tRep;
	
	@Autowired
	private GeneroRep gRep;
		
	//Mapeo del html para registrar cursos
	@RequestMapping(value = "/registrarAsesor", method = RequestMethod.GET)
	public String RegistrarProfesores(Model model, HttpServletRequest request){
		
		if(request.getParameterNames().hasMoreElements()) {
			String apaterno = request.getParameter("apaterno");
			
			String amaterno = request.getParameter("amaterno");
			
			String nombres = request.getParameter("nombres");
			
			String rfc = request.getParameter("rfc");
			
			String telefono = request.getParameter("telefono");
			
			String correo = request.getParameter("correo");
			
			Profesor profe = new Profesor();
			
			profe.setApellido_paterno(apaterno);
			
			profe.setApellido_materno(amaterno);
			
			profe.setNombre(nombres);
			
			profe.setRfc(rfc);
			
			profe.setTelefono(telefono);
			
			profe.setCorreo(correo);
			
			Optional<Estado> estado = stRep.findById(10);
			if(!estado.isEmpty()) {
				profe.setFk_id_estado(estado.get());
			}
			
			profRep.save(profe);
		}
		
		return "ProfesoresController/registrarAsesor";
	}

}
