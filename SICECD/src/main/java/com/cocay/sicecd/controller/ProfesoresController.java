package com.cocay.sicecd.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cocay.sicecd.dto.ProfesorDto;
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
	@RequestMapping(value = "/registrarAsesor2", method = RequestMethod.GET)
	public String RegistrarAsesores(Model model, HttpServletRequest request) throws ParseException{
		return "ProfesoresController/registrarAsesor";
	}
	
	@RequestMapping(value = "/registrarAsesor", method = RequestMethod.POST)
	public ResponseEntity<String> agregarAs(@RequestBody ProfesorDto prof) {
		Profesor pro = new Profesor();
		
		String apaterno = prof.getaPaterno();
		
		String amaterno = prof.getaMaterno();
		
		String nombres = prof.getNombres();
		
		String rfc = prof.getRfc();
		
		String telefono = prof.getTelefono();
		
		String correo = prof.getCorreo();
		
		String fechaSt = prof.getfNacimiento();
		Date fecha = null;
		try {
			fecha = new SimpleDateFormat("yyyy-MM-dd").parse(fechaSt);
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		
		pro.setApellido_paterno(apaterno);
		
		pro.setApellido_materno(amaterno);
		
		pro.setNombre(nombres);
		
		pro.setRfc(rfc);
		
		pro.setTelefono(telefono);
		
		pro.setCorreo(correo);
		
		pro.setFechaNac(fecha);
		
		/*------------------------------------------------------------------------------*/
		
		/*------------------------------------------------------------------------------*/
		/*Datos por default para la tabla*/
		
		List<Estado> est = stRep.findByNombre("Sin definir");
		
		Optional<Turno> trn = tRep.findById(4);
		
		Optional<Genero> gen = gRep.findById(3);
		
		Optional<Grado_profesor> gr = gpRep.findById(5);
		
		if(!est.isEmpty()) {
			pro.setFk_id_estado(est.get(0));
		}
		
		pro.setFk_id_turno(trn.get());
		pro.setGenero(gen.get());
		pro.setFk_id_grado_profesor(gr.get());
		
		/*------------------------------------------------------------------------------*/
		
		profRep.save(pro);
		return ResponseEntity.ok("¡Profesor agregado con exito!");
	}
	
	//Mapeo del html para registrar cursos
		@RequestMapping(value = "/registrarParticipantes", method = RequestMethod.GET)
		public String RegistrarParti(Model model, HttpServletRequest request) throws ParseException{
			return "ProfesoresController/registrarParticipante";
		}

	
	//Mapeo del html para registrar cursos
		@RequestMapping(value = "/registrarParticipante", method = RequestMethod.POST)
		public ResponseEntity<String> RegistrarParticipantes(@RequestBody ProfesorDto prof){
			
				Profesor profe = new Profesor();
				
				String apaterno = prof.getaPaterno();
				
				String amaterno = prof.getaMaterno();
				
				String nombres = prof.getNombres();
				
				String curp = prof.getCurp();
				
				String rfc = prof.getRfc();
				
				String correo = prof.getCorreo();
				
				String telefono = prof.getTelefono();
				
//				/*base*/
				String estado = prof.getEstado();
				List<Estado> est = stRep.findByNombre(estado);
				
				String cilo = prof.getCilo();
				
				/*base*/
				Integer genero = Integer.parseInt(prof.getGenero());
				Optional<Genero> gen = gRep.findById(genero);
				
				String plantel = prof.getPlantel();
				
				/*base*/
				Integer turno = Integer.parseInt(prof.getTurno());
				Optional<Turno> trn = tRep.findById(turno);
				
				String cplantel = prof.getcPlantel();
				
				/*base*/
				Integer grado = Integer.parseInt(prof.getGrado());
				Optional<Grado_profesor> gr = gpRep.findById(grado);
				
				String ocupacion = prof.getOcupacion();
				
				profe.setApellido_paterno(apaterno);
				
				profe.setApellido_materno(amaterno);
				
				profe.setNombre(nombres);
				
				profe.setCurp(curp);
				
				profe.setRfc(rfc);
				
				profe.setCorreo(correo);
				
				profe.setTelefono(telefono);
				
				if(!est.isEmpty()) {
					profe.setFk_id_estado(est.get(0));
				}
				
				profe.setCiudad_localidad(cilo);
				
				profe.setGenero(gen.get());
				
				profe.setPlantel(plantel);
				
				profe.setFk_id_turno(trn.get());
				
				profe.setClave_plantel(cplantel);
				
				profe.setFk_id_grado_profesor(gr.get());
				
				profe.setOcupacion(ocupacion);
				
				profRep.save(profe);
			
			return ResponseEntity.ok("¡Participante agregado con exito!");
		}
}
