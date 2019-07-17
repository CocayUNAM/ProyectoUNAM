package com.cocay.sicecd.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
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
import com.cocay.sicecd.model.Grado_profesor;
import com.cocay.sicecd.model.Grupo;
import com.cocay.sicecd.model.Inscripcion;
import com.cocay.sicecd.model.Profesor;
import com.cocay.sicecd.model.Turno;
import com.cocay.sicecd.repo.CursoRep;
import com.cocay.sicecd.repo.EstadoRep;
import com.cocay.sicecd.repo.Grado_profesorRep;
import com.cocay.sicecd.repo.GrupoRep;
import com.cocay.sicecd.repo.InscripcionRep;
import com.cocay.sicecd.repo.ProfesorRep;
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
	EstadoRep stRep;
	
	@Autowired
	Grado_profesorRep gpRep;
	
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
		
		if(cambio.getCurp() != null) {
			prof.setCurp(cambio.getCurp());
		}
		
		if(cambio.getCurp_doc() != null) {
			prof.setCurp_doc(cambio.getCurp_doc());
		}
		
		prof.setEstado(Integer.toString(cambio.getFk_id_estado().getPk_id_estado()));
		
		prof.setNombreEstado(cambio.getFk_id_estado().getNombre());
		
		if(cambio.getFechaNac() != null) {
			prof.setfNacimiento(cambio.getFechaNac().toString());
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
		
		if(constancia != null) {
            String originalName = constancia.getOriginalFilename();
            if(mod.getCertificado_doc() == null) {
                mod.setCertificado_doc(originalName);
            }else {
                if(!mod.getCertificado_doc().equals(originalName)) {
                	File here = new File(".");
                	String rutaActual = here.getAbsolutePath().replace(".", ""); 
                	String rutaR = rutaActual.replace("\\", "/") + ruta.replace("./", "") + Integer.toString(mod.getPk_id_profesor()) + "/" + mod.getCertificado_doc();
                    File archivo_anterior = new File(rutaR);
                    System.out.println("----------La ruta actual es: " + rutaR);
                    if(archivo_anterior.delete()) {
                        System.out.println(rutaR +" File deleted");
                    }else System.out.println("archivo: " + rutaR + " doesn't exist");
                }
            }
            saveConstancia(constancia, mod);
            mod.setCertificado_doc(originalName);
        }
		
		if(comprobante != null) {
            String originalName2 = comprobante.getOriginalFilename();
            if(mod.getComprobante_doc() == null) {
                mod.setComprobante_doc(originalName2);
            }else {
                if(!mod.getComprobante_doc().equals(originalName2)) {
                    File archivo_anterior = new File(ruta + Integer.toString(mod.getPk_id_profesor()) + "/" + mod.getComprobante_doc());
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
                    File archivo_anterior = new File(ruta + Integer.toString(mod.getPk_id_profesor()) + "/" + mod.getRfc_doc());
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
                    File archivo_anterior = new File(ruta + Integer.toString(mod.getPk_id_profesor()) + "/" + mod.getCurp_doc());
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
		InscripcionDto insi = new InscripcionDto();
		
		insi.setIdentificador(Integer.toString(cambio.getPk_id_inscripcion()));
		
		insi.setCalificacion(cambio.getCalif());
		
		insi.setAprobado(cambio.isAprobado());
		
		insi.setIdGrupo(cambio.getFk_id_grupo().getClave());
		
		insi.setIdProfesor(cambio.getFk_id_profesor().getRfc());
		
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
		
		if (!mod.getFk_id_grupo().getClave().equals(ins.getIdGrupo())) {
			mod.setFk_id_grupo(gpo);
		}
		
		Profesor pro = proRep.findByRfc(ins.getIdProfesor());
		
		if (!mod.getFk_id_profesor().getNombre().equals(ins.getIdProfesor())) {
			mod.setFk_id_profesor(pro);
		}
		
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
		
		GrupoDto gdto = new GrupoDto();
		
		gdto.setIdentificador(Integer.toString(gr.getPk_id_grupo()));
		
		gdto.setClave(gr.getClave());
		
		gdto.setAsesor(gr.getFk_id_profesor().getRfc());
		
		gdto.setCurso(gr.getFk_id_curso().getClave());
		
		if(gr.getFecha_inicio() != null) {
			gdto.setInicio(gr.getFecha_inicio().toString());
		}
		
		if(gr.getFecha_fin() != null) {
			gdto.setTermino(gr.getFecha_fin().toString());
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
			if(grp.getInicio() != null) {
				Date fecha = null;
				try {
					fecha = new SimpleDateFormat("yyyy-MM-dd").parse(grp.getInicio());
				} catch (ParseException e) {
					e.printStackTrace();
				}  
				mod.setFecha_inicio(fecha);
			} else {
				Date fecha = null;
				try {
					fecha = new SimpleDateFormat("yyyy-MM-dd").parse(grp.getInicio());
				} catch (ParseException e) {
					e.printStackTrace();
				}  
				mod.setFecha_inicio(fecha);
			}
		} else {
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
		
		if(mod.getFecha_fin() == null) {
			if(grp.getTermino() != null) {
				Date fecha = null;
				try {
					fecha = new SimpleDateFormat("yyyy-MM-dd").parse(grp.getTermino());
				} catch (ParseException e) {
					e.printStackTrace();
				}  
				mod.setFecha_fin(fecha);
			} else {
				Date fecha = null;
				try {
					fecha = new SimpleDateFormat("yyyy-MM-dd").parse(grp.getTermino());
				} catch (ParseException e) {
					e.printStackTrace();
				}  
				mod.setFecha_fin(fecha);
			}
		} else {
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
		
		Profesor pro = proRep.findByRfc(grp.getAsesor());
		
		if (!mod.getFk_id_profesor().getRfc().equals(grp.getAsesor())) {
			mod.setFk_id_profesor(pro);
		}
		
		Curso cur = crRep.findByClave(grp.getCurso()).get(0);
		
		if (!mod.getFk_id_curso().getClave().equals(grp.getCurso())) {
			mod.setFk_id_curso(cur);
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
		
		if(mod.getfInicio() == null) {
			if(cr.getfInicio() != null) {
				Calendar calendario = Calendar.getInstance();
				calendario.setTime(cr.getfInicio());
				calendario.add(Calendar.DAY_OF_YEAR, 1);
				mod.setfInicio(calendario.getTime());
			} else {
				mod.setfInicio(cr.getfInicio());
			}
		} else {
			if (!mod.getfInicio().equals(cr.getfInicio())) {
				cambios += "Fecha de inicio de " + mod.getfInicio() + " a " + cr.getfInicio()
				+ "\n";
				Calendar calendario = Calendar.getInstance();
				calendario.setTime(cr.getfInicio());
				calendario.add(Calendar.DAY_OF_YEAR, 1);
				mod.setfInicio(calendario.getTime());
			}
		} 
		
		if(mod.getfTermino() == null) {
			if(cr.getfTermino() != null) {
				Calendar calendario = Calendar.getInstance();
				calendario.setTime(cr.getfTermino());
				calendario.add(Calendar.DAY_OF_YEAR, 1);
				mod.setfTermino(calendario.getTime());
			} else {
				mod.setfTermino(cr.getfTermino());
			}
		} else {
			if (!mod.getfTermino().equals(cr.getfTermino())) {
				cambios += "Fecha de termino de " + mod.getfTermino() + " a " + cr.getfTermino()
				+ "\n";
				Calendar calendario = Calendar.getInstance();
				calendario.setTime(cr.getfTermino());
				calendario.add(Calendar.DAY_OF_YEAR, 1);
				mod.setfTermino(calendario.getTime());
			}
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
		
		//Se filtran todos los profesores que no tengan fecha de nacimiento
		list_p1 = list_p1.stream().filter(x -> x.getFk_id_estado().getPk_id_estado() == 33).collect(Collectors.toList());
		
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
		
		if(mod.getFechaNac() == null) {
			mod.setFechaNac(profesor.getFechaNac());
		} else {
			if (!mod.getFechaNac().equals(profesor.getFechaNac())) {
				cambios += "Fecha de termino de " + mod.getFechaNac() + " a " + profesor.getFechaNac()
				+ "\n";
				Calendar calendario = Calendar.getInstance();
				calendario.setTime(profesor.getFechaNac());
				calendario.add(Calendar.DAY_OF_YEAR, 1);
				mod.setFechaNac(calendario.getTime());
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
