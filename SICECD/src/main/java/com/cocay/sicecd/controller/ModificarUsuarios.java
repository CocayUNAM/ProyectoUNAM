package com.cocay.sicecd.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cocay.sicecd.LogTypes;
import com.cocay.sicecd.dto.GrupoDto;
import com.cocay.sicecd.dto.InscripcionDto;
import com.cocay.sicecd.dto.ProfesorDto;
import com.cocay.sicecd.model.Curso;
import com.cocay.sicecd.model.Estado;
import com.cocay.sicecd.model.Genero;
import com.cocay.sicecd.model.Grado_profesor;
import com.cocay.sicecd.model.Grupo;
import com.cocay.sicecd.model.Inscripcion;
import com.cocay.sicecd.model.Profesor;
import com.cocay.sicecd.model.Tipo_curso;
import com.cocay.sicecd.model.Turno;
import com.cocay.sicecd.repo.CursoRep;
import com.cocay.sicecd.repo.EstadoRep;
import com.cocay.sicecd.repo.GeneroRep;
import com.cocay.sicecd.repo.Grado_profesorRep;
import com.cocay.sicecd.repo.GrupoRep;
import com.cocay.sicecd.repo.InscripcionRep;
import com.cocay.sicecd.repo.ProfesorRep;
import com.cocay.sicecd.repo.Tipo_cursoRep;
import com.cocay.sicecd.repo.TurnoRep;
import com.cocay.sicecd.service.Logging;

@Controller
@RequestMapping("AdministracionModificaciones")
@PropertySource("classpath:application.properties")
public class ModificarUsuarios {
	
	@Autowired
	ProfesorRep proRep;
	
	@Autowired
	InscripcionRep insRep;
	
	@Autowired
	GrupoRep grRep;
	
	@Autowired
	CursoRep crRep;
	
	@Autowired
	GeneroRep genRep;
	
	@Autowired
	EstadoRep stRep;
	
	@Autowired
	Grado_profesorRep gpRep;
	
	@Autowired
	Tipo_cursoRep tcRep;
	
	@Autowired
	TurnoRep tnRep;
	
	@Autowired
	Logging log;
	
	@Value("${path_constancia}")
    private String ruta;
		
	/*
	 * Modificacion de Participantes.
	 * */
	@RequestMapping(value = "/listaProfesores", method = RequestMethod.GET)
	public ModelAndView consultarProfesorEstado(ModelMap model) {
		List<Profesor> list_p1 = proRep.findAll();
		
		//Se filtran a los participantes de los asesores
		for(Profesor p : list_p1) {
			if(p.getFk_id_estado() == null) {
				Optional<Estado> estado = stRep.findById(34);
				Estado defa = estado.get(); 
				p.setFk_id_estado(defa);
				proRep.save(p);
			}
			
			if(p.getFk_id_grado_profesor() == null) {
				Optional<Grado_profesor> gd = gpRep.findById(5);
				Grado_profesor gdp = gd.get(); 
				p.setFk_id_grado_profesor(gdp);
				proRep.save(p);
			}
			
			if(p.getFk_id_turno() == null) {
				Optional<Turno> tr = tnRep.findById(4);
				Turno tur = tr.get(); 
				p.setFk_id_turno(tur);
				proRep.save(p);
			}
			
			if(p.getGenero() == null) {
				Optional<Genero> g = genRep.findById(3);
				Genero gen = g.get(); 
				p.setGenero(gen);
				proRep.save(p);
			}
		}
		list_p1 = list_p1.stream().filter(x -> x.getFk_id_estado().getPk_id_estado() != 34).collect(Collectors.toList());
		
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
		
		ProfesorDto prof = new ProfesorDto();
		
		prof.setIdProfesor(cambio.getPk_id_profesor());
		
		prof.setaMaterno(cambio.getApellido_materno());
		
		prof.setaPaterno(cambio.getApellido_paterno());
		
		if(cambio.getCertificado_doc() != null) {
			prof.setCertificado_doc(cambio.getCertificado_doc());
		}
		
		if(cambio.getCiudad_localidad() != null) {
			prof.setCilo(cambio.getCiudad_localidad());
		}
		
		if(cambio.getComprobante_doc() != null) {
			prof.setComprobante_doc(cambio.getComprobante_doc());
		}
		
		prof.setCorreo(cambio.getCorreo());
		
		if(cambio.getClave_plantel() != null) {
			prof.setcPlantel(cambio.getClave_plantel());
		}
		
		if(cambio.getFechaNac() != null) {
			prof.setfNacimiento(cambio.getFechaNac().toString().substring(0, 10));
		} 
		
		if(cambio.getCurp() != null) {
			prof.setCurp(cambio.getCurp());
		}
		
		if(cambio.getCurp_doc() != null) {
			prof.setCurp_doc(cambio.getCurp_doc());
		}
		prof.setEstado(Integer.toString(cambio.getFk_id_estado().getPk_id_estado()));
		
		prof.setNombreEstado(cambio.getFk_id_estado().getNombre());
		
		if(cambio.getFechaNac() != null) {
			prof.setfNacimiento(cambio.getFechaNac().toString().substring(0, 10));
		}
		
		prof.setGenero(Integer.toString(cambio.getGenero().getPk_id_genero()));
		
		prof.setNombreGenero(cambio.getGenero().getGenero());
		
		prof.setGrado(Integer.toString(cambio.getFk_id_grado_profesor().getPk_id_grado_profesor()));
		
		prof.setNombreGrado(cambio.getFk_id_grado_profesor().getNombre());
		
		prof.setNombres(cambio.getNombre());
		
		if(cambio.getOcupacion() != null) {
			prof.setOcupacion(cambio.getOcupacion());
		}
		
		if(cambio.getPlantel() != null) {
			prof.setPlantel(cambio.getPlantel());
		}
		
		prof.setRfc(cambio.getRfc());
		
		if(cambio.getRfc_doc() != null) {
			prof.setRfc_doc(cambio.getRfc_doc());
		}
		
		if(cambio.getTelefono() != null) {
			prof.setTelefono(cambio.getTelefono());
		}
		
		prof.setTurno(Integer.toString(cambio.getFk_id_turno().getPk_id_turno()));
		prof.setNombreTurno(cambio.getFk_id_turno().getNombre());
		
		ModelAndView model = new ModelAndView("ModificarUsuario/pantallaModificacion");
		model.addObject("profesor", prof);
		return model;
	}
	
	@PostMapping(value = "/editarprofesor")
	private ResponseEntity<String> editarProfesor(
			Locale locale, 
		    @Valid ProfesorDto profesor,
		    @RequestParam(value = "constancia", required = false) MultipartFile constancia,
		    @RequestParam(value = "comprobante", required = false) MultipartFile comprobante,
            @RequestParam(value = "rfc_docu", required = false) MultipartFile rfc_pdf,
            @RequestParam(value = "curp_docu", required = false) MultipartFile curp_pdf
	) {
		int id = profesor.getIdProfesor();
		
		Profesor mod = proRep.findById(id).get();
		
		String cambios = "";
		
		if (!mod.getRfc().equals(profesor.getRfc())) {
			cambios += "Rfc de " + mod.getRfc() + " a " + profesor.getRfc() + "\n";
			mod.setRfc(profesor.getRfc());
		}
		
		if (!mod.getNombre().equals(profesor.getNombres())) {
			cambios += "Nombre de " + mod.getNombre() + " a " + profesor.getNombres() + "\n";
			mod.setNombre(profesor.getNombres());
		}
		if (!mod.getApellido_paterno().equals(profesor.getaPaterno())) {
			cambios += "Apellido Paterno de " + mod.getApellido_paterno() + " a " + profesor.getaPaterno()
					+ "\n";
			mod.setApellido_paterno(profesor.getaPaterno());
		}
		if (!mod.getApellido_materno().equals(profesor.getaMaterno())) {
			cambios += "Apellido Materno de " + mod.getApellido_materno() + " a " + profesor.getaMaterno()
					+ "\n";
			mod.setApellido_materno(profesor.getaMaterno());
		}
		
		if(mod.getCurp() == null) {
			cambios += "Curp de " + mod.getCurp() + " a " + profesor.getCurp()
			+ "\n";
			mod.setCurp(profesor.getCurp());
		} else {
			Profesor repetido = proRep.findByCurp(profesor.getCurp());
			if(repetido != null && mod.getRfc() != repetido.getRfc()) {
				return ResponseEntity.ok("Error: curp ya registrada");
			}
			
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
			cambios += "Ciudad o localidad de " + mod.getCiudad_localidad() + " a " + profesor.getCilo()
			+ "\n";
			mod.setCiudad_localidad(profesor.getCilo());
		} else {
			if (!mod.getCiudad_localidad().equals(profesor.getCilo())) {
				cambios += "Ciudad o localidad de " + mod.getCiudad_localidad() + " a " + profesor.getCilo()
				+ "\n";
				mod.setCiudad_localidad(profesor.getCilo());
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
			cambios += "clave de Plantel de " + mod.getClave_plantel() + " a " + profesor.getcPlantel()
			+ "\n";
			mod.setClave_plantel(profesor.getcPlantel());
		} else {
			if (!mod.getClave_plantel().equals(profesor.getcPlantel())) {
				cambios += "clave de Plantel de " + mod.getClave_plantel() + " a " + profesor.getcPlantel()
				+ "\n";
				mod.setClave_plantel(profesor.getcPlantel());
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
		
		Estado st = stRep.findById(Integer.parseInt(profesor.getEstado())).get();
		
		if (!Integer.toString(mod.getFk_id_estado().getPk_id_estado()).equals(profesor.getEstado())) {
			mod.setFk_id_estado(st);
		}
		
		Grado_profesor gp = gpRep.findById(Integer.parseInt(profesor.getGrado())).get();
		
		if (!Integer.toString(mod.getFk_id_grado_profesor().getPk_id_grado_profesor()).equals(profesor.getGrado())) {
			mod.setFk_id_grado_profesor(gp);
		}
		
		Turno turn = tnRep.findById(Integer.parseInt(profesor.getTurno())).get();
		
		if (!Integer.toString(mod.getFk_id_turno().getPk_id_turno()).equals(profesor.getTurno())) {
			mod.setFk_id_turno(turn);
		}
		
		if(mod.getFechaNac() == null) {
			String fechaSt = profesor.getfNacimiento();
			Date fecha = null;
			try {
				fecha = new SimpleDateFormat("yyyy-MM-dd").parse(fechaSt);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			mod.setFechaNac(fecha);
		} else {
			if(profesor.getfNacimiento() != null) {
				String fechaSt = profesor.getfNacimiento();
				Date fecha = null;
				try {
					fecha = new SimpleDateFormat("yyyy-MM-dd").parse(fechaSt);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if (!mod.getFechaNac().equals(fecha)) {
					Calendar calendario = Calendar.getInstance();
					calendario.setTime(fecha);
					calendario.add(Calendar.DAY_OF_YEAR, 1);
					mod.setFechaNac(calendario.getTime());
				}
			}
		}
		
		if(constancia != null) {
            String originalName2 = constancia.getOriginalFilename();
            if(mod.getCertificado_doc() == null) {
                mod.setCertificado_doc(originalName2);
            }else {
                if(!mod.getCertificado_doc().equals(originalName2)) {
                	File here = new File(".");
                	String rutaActual = here.getAbsolutePath().replace(".", ""); 
                	String rutaR = rutaActual.replace("\\", "/") + ruta.replace("./", "") + Integer.toString(mod.getPk_id_profesor()) + "/" + mod.getCertificado_doc();
                    File archivo_anterior = new File(rutaR);
                    archivo_anterior.delete();
                }
            }
            saveConstancia(constancia, mod);
            mod.setCertificado_doc(originalName2);
        }
		
		if(comprobante != null) {
            String originalName2 = comprobante.getOriginalFilename();
            if(mod.getComprobante_doc() == null) {
                mod.setComprobante_doc(originalName2);
            }else {
                if(!mod.getComprobante_doc().equals(originalName2)) {
                	File here = new File(".");
                	String rutaActual = here.getAbsolutePath().replace(".", ""); 
                	String rutaR = rutaActual.replace("\\", "/") + ruta.replace("./", "") + Integer.toString(mod.getPk_id_profesor()) + "/" + mod.getComprobante_doc();
                    File archivo_anterior = new File(rutaR);
                    archivo_anterior.delete();
                }
            }
            saveConstancia(comprobante, mod);
            mod.setComprobante_doc(originalName2);
        }
        
        if(rfc_pdf != null) {
            String originalName2 = rfc_pdf.getOriginalFilename();
            System.out.println("---------El nombre del rfc es:" + originalName2);
            if(mod.getRfc_doc() == null) {
                mod.setRfc_doc(originalName2);
            }else {
                if(!mod.getRfc_doc().equals(originalName2)) {
                	File here = new File(".");
                	String rutaActual = here.getAbsolutePath().replace(".", ""); 
                	String rutaR = rutaActual.replace("\\", "/") + ruta.replace("./", "") + Integer.toString(mod.getPk_id_profesor()) + "/" + mod.getRfc_doc();
                    File archivo_anterior = new File(rutaR);
                    archivo_anterior.delete();
                }
            }
            saveConstancia(rfc_pdf, mod);
            mod.setRfc_doc(originalName2);
        }
        
        if(curp_pdf != null) {
            String originalName2 = curp_pdf.getOriginalFilename();
            if(mod.getCurp_doc() == null) {
                mod.setCurp_doc(originalName2);
            }else {
                if(!mod.getCurp_doc().equals(originalName2)) {
                	File here = new File(".");
                	String rutaActual = here.getAbsolutePath().replace(".", ""); 
                	String rutaR = rutaActual.replace("\\", "/") + ruta.replace("./", "") + Integer.toString(mod.getPk_id_profesor()) + "/" + mod.getCurp_doc();
                    File archivo_anterior = new File(rutaR);
                    archivo_anterior.delete();
                }
            }
            saveConstancia(curp_pdf, mod);
            mod.setCurp_doc(originalName2);
        }
		
		System.out.println(cambios);
		
		log.setTrace(LogTypes.MODIFICAR_PARTICIPANTE);
		
		if (!cambios.equals("")) {
			proRep.save(mod);
		}

		return ResponseEntity.ok("¡Participante editado con exito!");
	}
	

    private String saveConstancia(MultipartFile constancia, Profesor pr) {
        
        String path = Integer.toString(pr.getPk_id_profesor());
        System.out.println("path: " + path);

        String originalName = constancia.getOriginalFilename();
        System.out.println("FileName: " + originalName);
        
        String folder = ruta+path+"/";
        try {
            
            FileUtils.forceMkdir(new File(folder));
            try (FileOutputStream fos = new FileOutputStream(new File(folder + originalName))){
                IOUtils.copy(constancia.getInputStream(), fos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
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
		Inscripcion cambio = insRep.findById(id).get();
		
		List<Grupo> list_p1 = grRep.findAll();
		List<Profesor> list_p2 = proRep.findAll();
		
		List<String> grupos = new ArrayList<String>();
		List<String> rfcs = new ArrayList<String>();
		
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		
		StringBuilder nc = new StringBuilder();
		
		for(Grupo g : list_p1) {
			grupos.add(g.getClave());
			sb1.append(g.getClave() + ",");
		}
		
		for(Profesor p : list_p2) {
			rfcs.add(p.getRfc());
			sb2.append(p.getRfc() + ",");
		}
		
		for(Profesor p : list_p2) {
			nc.append(p.getApellido_paterno() + " " +  p.getApellido_materno() + " " + p.getNombre() + ",");
			rfcs.add(nc.toString());
		}
		
		String re = sb1.toString();
		sb1.setLength(re.length() - 1);
		
		String rep = sb2.toString();
		sb2.setLength(rep.length() - 1);
		
		String nombresc = nc.toString();
		nc.setLength(nombresc.length() - 1);
		
		InscripcionDto insi = new InscripcionDto();
		
		insi.setIdentificador(Integer.toString(cambio.getPk_id_inscripcion()));
		
		insi.setCalificacion(cambio.getCalif());
		
		insi.setAprobado(cambio.isAprobado());
		
		insi.setIdGrupo(cambio.getFk_id_grupo().getClave());
		
		insi.setIdProfesor(cambio.getFk_id_profesor().getRfc());
		
		insi.setJsonG(sb1.toString());
		insi.setJsonP(sb2.toString());
		insi.setJsonNombres(nc.toString());
		
		ModelAndView model = new ModelAndView("ModificarUsuario/pantallaModificacionI");
		model.addObject("inscripcion", insi);
		return model;
	}
	
	@PostMapping(value = "/editarinscripcion")
	private ResponseEntity<String> editarInsc(@RequestBody InscripcionDto ins) {
		int id = Integer.parseInt(ins.getIdentificador());
		
		Inscripcion mod = insRep.findById(id).get();
		
		String cambios = "";
		
		if(mod.getCalif() == null) {
			mod.setCalif(ins.getCalificacion());
		} else {
			if (!mod.getCalif().equals(ins.getCalificacion())) {
				mod.setCalif(ins.getCalificacion());
			}
		}
		
		Grupo gpo = grRep.findByClave(ins.getIdGrupo()).get(0); 
		
		if(gpo != null) {
			if (!mod.getFk_id_grupo().getClave().equals(ins.getIdGrupo())) {
				mod.setFk_id_grupo(gpo);
			}
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
		            .body("¡Grupo no encontrado!");
		}
		
		String grupo = ins.getIdGrupo();
		
		List<Grupo> grupop = grRep.findByClave(grupo);
		
		ArrayList<String> rfcs = new ArrayList<String>();
		
		for(Grupo g : grupop) {
			rfcs.add(g.getFk_id_profesor().getRfc());
		}
		
		Profesor pro = proRep.findByRfc(ins.getIdProfesor());
		
		if(rfcs.contains(pro.getRfc())) {
			System.out.println("Sí lo contengo! es:");
			System.out.println(pro.getRfc());
			return ResponseEntity.ok("Error : El participante ya habia sido registrado con este grupo");
		}
		
		if(pro != null) {
			if (!mod.getFk_id_profesor().getNombre().equals(ins.getIdProfesor())) {
				mod.setFk_id_profesor(pro);
			}
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
		            .body("¡Asesor no encontrado!");
		}
		
		mod.setAprobado(ins.isAprobado());
		
		System.out.println(cambios);
		
		log.setTrace(LogTypes.MODIFICAR_INSCRIPCION);
		
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
		
		List<GrupoDto> lista = new ArrayList<>();
		
		for(Grupo g : list_p1) {
			Integer id = g.getPk_id_grupo();
			
			String clave = g.getClave();
			
			String fi = null;
			if(g.getFecha_fin() != null) {
				fi = g.getFecha_fin().toString().substring(0, 10);
			}
			
			String ft = null;
			if(g.getFecha_fin() != null) {
				ft = g.getFecha_fin().toString().substring(0, 10);
			}
			
			String cc = g.getFk_id_curso().getClave();
			
			String cm = g.getFk_id_curso().getNombre();
			
			String rfc = "Sin definir";
			String nombre = null;
			String apaterno = null;
			if(g.getFk_id_profesor() != null) {
				rfc= g.getFk_id_profesor().getRfc();
				nombre = g.getFk_id_profesor().getNombre();
				apaterno = g.getFk_id_profesor().getApellido_paterno();
			}

			GrupoDto nm = new GrupoDto();
			
			nm.setIdGrupo(id.toString());
			
			nm.setClave(clave);
			
			nm.setInicio(fi);
			
			nm.setTermino(ft);
			
			nm.setCurso(cm);
			
			nm.setClaveCurso(cc);
			
			nm.setAsesor(rfc);
			
			nm.setAnombre(nombre);
			
			nm.setApaterno(apaterno);
			
			lista.add(nm);
		}
		
		if(!lista.isEmpty()) {
			
			model.put("grupos", lista);
			return new ModelAndView("ModificarUsuario/listaGrupos", model);
		} else {
			return new ModelAndView("/Avisos/ErrorBusqueda");
		}
	}
	
	@GetMapping(value = "/pantallaModificacionG")
	public ModelAndView formEditarGrupo(@RequestParam(name = "id") String id) {
		Grupo gr = grRep.findById(Integer.parseInt(id)).get();
		
		List<Curso> list_p1 = crRep.findAll();
		List<Profesor> list_p2 = proRep.findAll();
		
		List<String> claves = new ArrayList<String>();
		List<String> rfcs = new ArrayList<String>();
		
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		
		StringBuilder nc = new StringBuilder();
		
		for(Curso c : list_p1) {
			claves.add(c.getClave());
			sb1.append(c.getClave() + ",");
		}
		
		for(Profesor p : list_p2) {
			rfcs.add(p.getRfc());
			sb2.append(p.getRfc() + ",");
		}
		
		for(Profesor p : list_p2) {
			nc.append(p.getApellido_paterno() + " " +  p.getApellido_materno() + " " + p.getNombre() + ",");
			rfcs.add(nc.toString());
		}
		
		String re = sb1.toString();
		sb1.setLength(re.length() - 1);
		
		String rep = sb2.toString();
		sb2.setLength(rep.length() - 1);
		
		String nombresc = nc.toString();
		nc.setLength(nombresc.length() - 1);
		
		GrupoDto gdto = new GrupoDto();
		
		gdto.setIdentificador(Integer.toString(gr.getPk_id_grupo()));
		
		gdto.setClave(gr.getClave());
			
		if(gr.getFk_id_profesor() != null) {
			gdto.setAsesor(gr.getFk_id_profesor().getRfc());
		} else {
			gdto.setAsesor("Sin definir");
		} 
		
		gdto.setCurso(gr.getFk_id_curso().getClave());

		gdto.setJsonC(sb1.toString());
		gdto.setJsonP(sb2.toString());
		gdto.setJsonNombres(nc.toString());
		
		if(gr.getFecha_inicio() != null) {
			gdto.setInicio(gr.getFecha_inicio().toString().substring(0, 10));
		}
		
		if(gr.getFecha_fin() != null) {
			gdto.setTermino(gr.getFecha_fin().toString().substring(0, 10));
		}
		
		ModelAndView model = new ModelAndView("ModificarUsuario/pantallaModificacionG");
		model.addObject("grupo", gdto);
		return model;
	}
	
	@PostMapping(value = "/editargrupo")
	private ResponseEntity<String> editarGrupo(@RequestBody GrupoDto grp) 
	{
		String id = grp.getIdentificador();
		Integer idP =Integer.parseInt(id);
		
		Grupo mod = grRep.findById(idP).get();
		
		String cambios = "";
		
		if (!mod.getClave().equals(grp.getClave())) {
			cambios += "Clave de " + mod.getClave() + " a " + grp.getClave() + "\n";
			mod.setClave(grp.getClave());
		}
		
		if(mod.getFecha_inicio() == null) {
			if(grp.getInicio() != "") {
				Date fecha = null;
				try {
					fecha = new SimpleDateFormat("yyyy-MM-dd").parse(grp.getInicio());
				} catch (ParseException e) {
					e.printStackTrace();
				}  
				mod.setFecha_inicio(fecha);
			} 
		} else {
			if(grp.getInicio() != "") {
				if (!mod.getFecha_inicio().toString().equals(grp.getInicio())) {
					cambios += "Fecha de inicio de " + mod.getFecha_inicio().toString() + " a " + grp.getInicio()
					+ "\n";
					Date fecha = null;
					try {
						fecha = new SimpleDateFormat("yyyy-MM-dd").parse(grp.getInicio());
					} catch (ParseException e) {
						e.printStackTrace();
					}  
					mod.setFecha_inicio(fecha);
				}
			}
		} 
		
		if(mod.getFecha_fin() == null) {
			if(grp.getTermino() != "") {
				Date fecha = null;
				try {
					fecha = new SimpleDateFormat("yyyy-MM-dd").parse(grp.getTermino());
				} catch (ParseException e) {
					e.printStackTrace();
				}  
				mod.setFecha_fin(fecha);
			} 
		} else {
			if(grp.getTermino() != "") {
				if (!mod.getFecha_fin().toString().equals(grp.getTermino())) {
					cambios += "Fecha de termino de " + mod.getFecha_fin().toString() + " a " + grp.getTermino()
					+ "\n";
					Date fecha = null;
					try {
						fecha = new SimpleDateFormat("yyyy-MM-dd").parse(grp.getTermino());
					} catch (ParseException e) {
						e.printStackTrace();
					} 
					mod.setFecha_fin(fecha);
				}
			}
		}
		
		Profesor pro = proRep.findByRfc(grp.getAsesor());
				
		if(pro!=null) {
			if(mod.getFk_id_profesor() != null) {
				if (!mod.getFk_id_profesor().getRfc().equals(grp.getAsesor())) {
					mod.setFk_id_profesor(pro);
				}
			} else {
				mod.setFk_id_profesor(pro);
			}
		} 
		
		if(grp.getAsesor().contains("Sin definir")) {
			mod.setFk_id_profesor(null);
		}
		
		Curso cur = crRep.findByClave(grp.getCurso()).get(0);
		
		if(cur != null) {
			if (!mod.getFk_id_curso().getClave().equals(grp.getCurso())) {
				mod.setFk_id_curso(cur);
			}  
		}

		System.out.println(cambios);
		
		log.setTrace(LogTypes.MODIFICAR_GRUPO);
		
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
		
		for(Curso c : list_p1) {
			if(c.getFk_id_tipo_curso() == null) {
				Optional<Tipo_curso> tp = tcRep.findById(6);
				Tipo_curso tpc = tp.get(); 
				c.setFk_id_tipo_curso(tpc);
				crRep.save(c);
			}
		}
		
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
		
		if(mod.getHoras() == 0) {
			mod.setHoras(cr.getHoras());
		} else {
			if (mod.getHoras() != cr.getHoras()) {
				cambios += "Horas de " + mod.getHoras() + " a " + cr.getHoras() + "\n";
				mod.setHoras(cr.getHoras());
			}
		}
		
		if (!mod.getNombre().equals(cr.getNombre())) {
			cambios += "Nombre de " + mod.getNombre() + " a " + cr.getNombre() + "\n";
			mod.setNombre(cr.getNombre());
		}
		
		System.out.println(cambios);
		
		log.setTrace(LogTypes.MODIFICAR_CURSO);
		
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
		
		//Se filtran a los participantes de los asesores
		for(Profesor p : list_p1) {
			if(p.getFk_id_estado() == null) {
				Optional<Estado> estado = stRep.findById(34);
				Estado defa = estado.get(); 
				p.setFk_id_estado(defa);
				proRep.save(p);
			}
			if(p.getFk_id_grado_profesor() == null) {
				Optional<Grado_profesor> gd = gpRep.findById(5);
				Grado_profesor gdp = gd.get(); 
				p.setFk_id_grado_profesor(gdp);
				proRep.save(p);
			}
			
			if(p.getFk_id_turno() == null) {
				Optional<Turno> tr = tnRep.findById(4);
				Turno tur = tr.get(); 
				p.setFk_id_turno(tur);
				proRep.save(p);
			}
			
			if(p.getGenero() == null) {
				Optional<Genero> g = genRep.findById(3);
				Genero gen = g.get(); 
				p.setGenero(gen);
				proRep.save(p);
			}
		}
		
		//Se filtran todos los profesores que no tengan fecha de nacimiento
		list_p1 = list_p1.stream().filter(x -> x.getFk_id_estado().getPk_id_estado() == 34).collect(Collectors.toList());
		
		
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
		
		ProfesorDto pdt = new ProfesorDto();
		
		pdt.setaMaterno(cambio.getApellido_materno());
		
		pdt.setaPaterno(cambio.getApellido_paterno());
		
		pdt.setNombres(cambio.getNombre());
		
		pdt.setCorreo(cambio.getCorreo());
		
		if(cambio.getFechaNac() != null) {
			pdt.setfNacimiento(cambio.getFechaNac().toString().substring(0, 10));
		} 
		
		pdt.setGenero(cambio.getGenero().toString());
		
		pdt.setIdProfesor(cambio.getPk_id_profesor());
		
		pdt.setRfc(cambio.getRfc());
		
		ModelAndView model = new ModelAndView("ModificarUsuario/pantallaModificacionA");
		model.addObject("asesor", pdt);
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
		
		if(mod.getFechaNac() == null) {
			mod.setFechaNac(profesor.getFechaNac());
		} else {
			if(profesor.getFechaNac() != null) {
				if (!mod.getFechaNac().equals(profesor.getFechaNac())) {
					cambios += "Fecha de termino de " + mod.getFechaNac() + " a " + profesor.getFechaNac()
					+ "\n";
					Calendar calendario = Calendar.getInstance();
					calendario.setTime(profesor.getFechaNac());
					calendario.add(Calendar.DAY_OF_YEAR, 1);
					mod.setFechaNac(calendario.getTime());
				}
			}
		}
		
		System.out.println(cambios);
		log.setTrace(LogTypes.MODIFICAR_ASESOR);
		
		if (!cambios.equals("")) {
			proRep.save(mod);
		}

		return ResponseEntity.ok("¡Asesor editado con exito!");
	}
}