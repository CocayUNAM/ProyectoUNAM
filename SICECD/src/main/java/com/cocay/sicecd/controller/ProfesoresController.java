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
import com.cocay.sicecd.model.Genero;
import com.cocay.sicecd.model.Grado_profesor;
import com.cocay.sicecd.model.Profesor;
import com.cocay.sicecd.model.Turno;
import com.cocay.sicecd.repo.EstadoRep;
import com.cocay.sicecd.repo.GeneroRep;
import com.cocay.sicecd.repo.Grado_profesorRep;
import com.cocay.sicecd.repo.ProfesorRep;
import com.cocay.sicecd.repo.TurnoRep;

@Controller
@RequestMapping("AdministracionProfesores")
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
			
			Optional<Estado> estado = stRep.findById((Integer)10);
			if(!estado.isEmpty()) {
				profe.setFk_id_estado(estado.get());
			}
			
			profRep.save(profe);
		}
		
		return "ProfesoresController/registrarAsesor";
	}
	
	//Mapeo del html para registrar cursos
		@RequestMapping(value = "/registrarParticipante", method = RequestMethod.GET)
		public String RegistrarParticipantes(Model model, HttpServletRequest request){
			
			if(request.getParameterNames().hasMoreElements()) {
				
				String apaterno = request.getParameter("apaterno");
				
				String amaterno = request.getParameter("amaterno");
				
				String nombres = request.getParameter("nombres");
				
				String curp = request.getParameter("curp");
				
				String rfc = request.getParameter("rfc");
				
				String correo = request.getParameter("correo");
				
				String telefono = request.getParameter("tel");
				
//				/*base*/
				String estado = request.getParameter("estado");
				List<Estado> est = stRep.findByNombre(estado);
				
				String cilo = request.getParameter("cilo");
				
				/*base*/
				Integer genero = Integer.parseInt(request.getParameter("genero"));
				Optional<Genero> gen = gRep.findById(genero);
				
				String plantel = request.getParameter("plantel");
				
				/*base*/
				Integer turno = Integer.parseInt(request.getParameter("turno"));
				Optional<Turno> trn = tRep.findById(turno);
				
				String cplantel = request.getParameter("cplantel");
				
				/*base*/
				Integer grado = Integer.parseInt(request.getParameter("grado"));
				Optional<Grado_profesor> gr = gpRep.findById(grado);
				
				String ocupacion = request.getParameter("ocupacion");
				
				String calif = request.getParameter("calif");
				
				String consta = request.getParameter("const");
				
				/*Se crea el nuevo profesor a agregar a la base de datos*/
				Profesor profe = new Profesor();
				
				profe.setApellido_paterno(apaterno);
				
				profe.setApellido_materno(amaterno);
				
				profe.setNombre(nombres);
				
				profe.setCurp(curp);
				
				profe.setRfc(rfc);
				
				profe.setCorreo(correo);
				
				profe.setTelefono(telefono);
				
				Estado prueba = est.get(0);
				
				if(!est.isEmpty()) {
					profe.setFk_id_estado(prueba);
				}
				
				profe.setCiudad_localidad(cilo);
				
				profe.setGenero(gen.get());
				
				profe.setPlantel(plantel);
				
				profe.setFk_id_turno(trn.get());
				
				profe.setPlantel(cplantel);
				
				profe.setFk_id_grado_profesor(gr.get());
				
				profe.setOcupacion(ocupacion);
				
				profRep.save(profe);
			}
			
			return "ProfesoresController/registrarParticipante";
		}
	
	

}
