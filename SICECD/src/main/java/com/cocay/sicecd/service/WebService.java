package com.cocay.sicecd.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.cocay.sicecd.LogTypes;
import com.cocay.sicecd.model.Curso;
import com.cocay.sicecd.model.Grupo;
import com.cocay.sicecd.model.Inscripcion;
import com.cocay.sicecd.model.Profesor;
import com.cocay.sicecd.model.Url_ws_curso;
import com.cocay.sicecd.model.Url_ws_inscripcion;
import com.cocay.sicecd.model.Url_ws_profesor;
import com.cocay.sicecd.repo.CursoRep;
import com.cocay.sicecd.repo.GrupoRep;
import com.cocay.sicecd.repo.InscripcionRep;
import com.cocay.sicecd.repo.ProfesorRep;
import com.cocay.sicecd.repo.Url_ws_cursoRep;
import com.cocay.sicecd.repo.Url_ws_inscripcionRep;
import com.cocay.sicecd.repo.Url_ws_profesorRep;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service

@PropertySource(ignoreResourceNotFound = true, value = "classpath:application.properties")
public class WebService {
	@Autowired
	ProfesorRep profesor;
	@Autowired
	GrupoRep grupo_rep;
	@Autowired
	CursoRep curso_rep;
	@Autowired
	InscripcionRep inscripcionRep;
	@Autowired
	Url_ws_profesorRep urls;
	@Autowired
	Url_ws_cursoRep urls_curso;
	@Autowired
	Url_ws_inscripcionRep urls_inscripcion;
	@Autowired
	Logging log;
	@Value("${ws.url_key}")
	private String key;
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	private static int FIRST_ELEMENT = 0;
	private static int SECOND_ELEMENT = 1;

	public interface ReturnTypeOne {
	}

	public interface ReturnTypeTwo {
	}

	public interface ReturnTypeThree {
	}

	/*
	 * Se compone de dos secciones. Primero en el que se envían dos tareas al grupo
	 * de hilos. Cada llamada ExecutorService.submit () devuelve inmediatamente un
	 * valor futuro del cálculo de la tarea. Las tareas se envían inmediatamente en
	 * la llamada a submit () y se ejecutan en segundo plano. Por supuesto que
	 * pueden hacer mas de 3 tareas En la segunda sección se obtienen los valores de
	 * futuros. Lo que sucede es que la llamada a Future.get () bloquea el
	 * subproceso actual hasta que se calcula el valor. No significa que cualquier
	 * tarea esté bloqueada, todas se estén ejecutando, el hilo solo espera hasta
	 * que una tarea determinada se complete y devuelva un valor. Una vez que se
	 * devuelve el primer valor, se realiza la segunda llamada Future.get (). En
	 * este caso, puede o no bloquearse. Si la segunda tarea ya ha finalizado
	 * (posiblemente antes de la primera tarea), el valor se devuelve
	 * inmediatamente. Si la segunda tarea aún se está ejecutando, la llamada
	 * bloquea el subproceso actual hasta que se calcula el valor.
	 */
	
	public void run() {
		final CountDownLatch cdl1 = new CountDownLatch(1);
	    final CountDownLatch cdl2 = new CountDownLatch(1);
		ExecutorService executor = Executors.newFixedThreadPool(3);

		// Tenemos listas las tareas()
		Future<ReturnTypeOne> first = executor.submit(new Callable<ReturnTypeOne>() {

			@Override
			public ReturnTypeOne call() throws Exception {
				LOGGER.info("Start Thread WS One");
				get_Profesores();
				cdl1.countDown();
				LOGGER.info("Finish Thread WS One");
				return null;
			}
		});
		Future<ReturnTypeTwo> second = executor.submit(new Callable<ReturnTypeTwo>() {

			@Override
			public ReturnTypeTwo call() throws Exception {
				LOGGER.info("Start Thread WS Second");
				get_Curso();
				cdl2.countDown();
				LOGGER.info("Finish Thread WS Second");
				return null;
			}
		});
		Future<ReturnTypeThree> third = executor.submit(new Callable<ReturnTypeThree>() {

			@Override
			public ReturnTypeThree call() throws Exception {
				try {
	                cdl1.await();
	                cdl2.await();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
				LOGGER.info("Start Thread WS Three");
				get_Calificaciones();
				LOGGER.info("Finish Thread WS Three");
				return null;
			}
		});

		// Obtenemos resultados
		try {
			ReturnTypeOne firstValue = first.get();
			ReturnTypeTwo secondValue = second.get();
			ReturnTypeThree thirdValue = third.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	public void get_Profesores() {

		LinkedList<Url_ws_profesor> links = new LinkedList<>(urls.findVarios());

		if (links.size() == 0) {
			LOGGER.debug("No hay urls para el proceso obtener Profesores");
		}

		for (Url_ws_profesor url : links) {
			System.out.println("Se conecto" + url.getUrl());
			String json = jsonGetRequest(url.getUrl() + "?clave=" + key);
			System.out.println(json);
			insert_update_Profesor(json);
			// log.setTrace(LogTypes.ACTUALIZAR_PROFESOR,"Se agrego un profesor");
			// log.setTrace(LogTypes.AGREGAR_PROFESOR,"Se actualizo un profesor");

		}

	}

	public void get_Curso() {

		LinkedList<Url_ws_curso> links = new LinkedList<>(urls_curso.findVarios());

		if (links.size() == 0) {
			LOGGER.debug("No hay urls para el proceso obtener Cursos");
		}

		for (Url_ws_curso url : links) {
			System.out.println("Se conecto" + url.getUrl());
			String json = jsonGetRequest(url.getUrl() + "?clave=" + key);
			System.out.println(json);
			insert_Course(json);
			// log.setTrace(LogTypes.ACTUALIZAR_PROFESOR,"Se agrego un profesor");
			// log.setTrace(LogTypes.AGREGAR_PROFESOR,"Se actualizo un profesor");

		}

	}
	@Scheduled(cron = "${ws.scheduleImportData}")
	public void get_Calificaciones() {

		LinkedList<Url_ws_inscripcion> links = new LinkedList<>(urls_inscripcion.findVarios());
		if (links.size() == 0) {
			LOGGER.debug("No hay urls para el proceso ObtenerCalificaciones");
			return;
		}
		//for (Url_ws_inscripcion url : links) {
			//System.out.println("Se conecto" + url.getUrl());
			String json = jsonGetRequest(/*url.getUrl() */"http://unamedu.serveftp.com:8585/WB/api/users/grades.php"+ "?clave=" + key);
			System.out.println(json);
			insert_Grade(json);
			// log.setTrace(LogTypes.ACTUALIZAR_PROFESOR);
			// log.setTrace(LogTypes.AGREGAR_PROFESOR);
		//}
	}
/*	public void  inserta_calificaciones(String jSonResultString) {
		JSONArray arr = new JSONArray(jSonResultString);
		for (int i = FIRST_ELEMENT; i < SECOND_ELEMENT; i++) {
			JSONObject jsonProductObject = arr.getJSONObject(i);
			String nombre_curso = jsonProductObject.getString("shortname");
			String id_grupo = jsonProductObject.getString("idnumber");
			String[] claves = separaNombreCurso(id_grupo);
			if(claves.length!=2) {
				String error = "Error guardando Curso, formato IdNumber incorrecto:"+id_grupo+" - "+nombre_curso; 
				log.logtrace(LogTypes.CARGA_WS_BATCH_ERROR_CURSO, error);
				LOGGER.error(error);
				continue;
			}
			String clave_curso = claves[0];
			String clave_grupo = claves[1];
			System.out.println(claves);
			
		}
		//System.out.println("First: " + arr.getJSONObject(FIRST_ELEMENT).toString());
		//String nombre_curso = jsonProductObject.getString("grade");
		for (int i = SECOND_ELEMENT; i < arr.length(); i++) {
			JSONObject jsonProductObject = arr.getJSONObject(i);
			String calificacion = jsonProductObject.getString("grade");
			String profesor = jsonProductObject.getString("username");
			System.out.println(calificacion);
			System.out.println(profesor);
			
		}
		
		
	}*/
	public void insert_Course(String jSonResultString) {
		JSONArray arr = new JSONArray(jSonResultString);
		for (int i = 0; i < arr.length(); i++) {
			JSONObject jsonProductObject = arr.getJSONObject(i);
			String nombre_curso = jsonProductObject.getString("shortname");
			String id_grupo = jsonProductObject.getString("idnumber");
			String[] claves = separaNombreCurso(id_grupo);
			if(claves.length!=2) {
				String error = "Error guardando Curso, formato IdNumber incorrecto:"+id_grupo+" - "+nombre_curso; 
				log.logtrace(LogTypes.CARGA_WS_BATCH_ERROR_CURSO, error);
				LOGGER.error(error);
				continue;
			}
			String clave_curso = claves[0];
			String clave_grupo = claves[1];
			Curso exits = curso_rep.findByUniqueClaveCurso(clave_curso);

			if (exits == null) {
				try {
					curso_rep.saveC(clave_curso, nombre_curso);
				} catch (Exception ex) {
					String error = "Error: "+ex.toString()+"	|	";
					error=error+"Clave Curso:"+clave_curso+"	|	";
					error=error+"Nombre Curso:"+nombre_curso;
					log.logtrace(LogTypes.CARGA_WS_BATCH_ERROR_CURSO, error);
					LOGGER.error("Error guardando Curso en carga Batch WS", ex);
					ex.printStackTrace();
				}
				exits = curso_rep.findByUniqueClaveCurso(clave_curso);

			} else {

				LOGGER.debug("Ya existe el curso");

			}


			Grupo exits_group = grupo_rep.findByClaveGrupoIdCurso(clave_grupo, exits);
			if (exits_group == null) {
				try {
					grupo_rep.saveC(clave_grupo, exits.getPk_id_curso());
				} catch (Exception ex) {
					String error = "Error: "+ex.toString()+"	|	";
					error=error+"Clave Curso:"+clave_curso+"	|	";
					error=error+"ID Curso:"+exits.getPk_id_curso();
					log.logtrace(LogTypes.CARGA_WS_BATCH_ERROR_GRUPO, error);
					LOGGER.error("Error guardando Grupo en carga Batch WS", ex);
					ex.printStackTrace();
				}

			} else {
				LOGGER.debug("Ya existe el grupo");
			}

		}
	}

	public void insert_Grade(String jSonResultString) {
		JSONArray arr = new JSONArray(jSonResultString);
		String clave_curso=null;
		String clave_grupo=null;
		for (int i = 0; i < arr.length(); i++) {
			JSONObject jsonProductObject = arr.getJSONObject(i);
			String nombre_curso=jsonProductObject.getJSONObject("materia").getJSONObject("datos").getString("shortname");
			String id_curso=jsonProductObject.getJSONObject("materia").getJSONObject("datos").getString("shortname");
			JSONArray arrObj = jsonProductObject.getJSONObject("materia").getJSONArray("inscripcion");


			
			for (int j = 0; j < arrObj.length(); j++) {
			    JSONObject row = arrObj.getJSONObject(j);
			    String profesor = row.getJSONObject("profesor").getString("username");
			    String grade = row.getJSONObject("profesor").getString("grade");
			    System.out.println("Profesor"+profesor);
			    System.out.println("Calificacion"+grade);
			    System.out.println("______________________");
			}

			//String nombre_curso = jsonProductObject.getString("materia");
			//String id_grupo = jsonProductObject.getString("datos");
			System.out.println("***********************");
			System.out.println("______________________");
			System.out.println(nombre_curso);
			System.out.println(id_curso);
			System.out.println("______________________");
			System.out.println("***********************");
			//String[] claves = separaNombreCurso(id_grupo);
			/*if(claves.length!=2) {
				String error = "Error guardando Curso, formato IdNumber incorrecto:"+id_grupo+" - "+nombre_curso; 
				log.logtrace(LogTypes.CARGA_WS_BATCH_ERROR_CURSO, error);
				LOGGER.error(error);
				continue;
			}
			clave_curso = claves[0];
			clave_grupo = claves[1];
			System.out.println(claves);*/
			
		}
		/*
		for (int i = SECOND_ELEMENT; i < arr.length(); i++) {
			JSONObject jsonProductObject = arr.getJSONObject(i);
			String calificacion = jsonProductObject.getString("grade");
			String curp = jsonProductObject.getString("username");
			double result = Double.parseDouble(calificacion);
			boolean aprobado = aprobadoCalificacion(result);
			Profesor exits = profesor.findByCurp(curp);


			Curso curso = curso_rep.findByUniqueClaveCurso(clave_curso);
			if(curso == null) {
				LOGGER.info("No existe el curso: "+clave_grupo+" - "+curp);
			}else {
				Grupo grupo = grupo_rep.findByClaveGrupoIdCurso(clave_grupo, curso);
				if (grupo == null) {
					LOGGER.info("No existe el grupo: "+clave_grupo+" - "+curp);
				} else {
					if(exits==null) {
						LOGGER.info("No existe el profesor: "+clave_grupo+" - "+curp);
					}else {
						if(calificacion.length()>3) {
							calificacion = calificacion.substring(0, 3);
							calificacion = calificacion.replace(".", "");
						}
						try {
							//inscripcionRep.saveI(grupo.getPk_id_grupo(), exits.getPk_id_profesor(), calificacion, aprobado);
						} catch (Exception ex) {
							String error = "Error: "+ex.toString()+"	|	";
							error=error+"ID Grupo:"+grupo.getPk_id_grupo()+"	|	";
							error=error+"ID Profesor:"+exits.getPk_id_profesor()+"	|	";
							error=error+"Calificacion:"+calificacion+"	|	";
							error=error+"Aprobado:"+aprobado+"	|	";
							log.logtrace(LogTypes.CARGA_WS_BATCH_ERROR_INSCRIPCION, error);
							LOGGER.error("Error guardando Incripción en carga Batch WS", ex);
							ex.printStackTrace();
							
						}
					}
				}
			}

		}*/
	}

	public void insert_update_Profesor(String jSonResultString) {
		JSONArray arr = new JSONArray(jSonResultString);
		String apellido_paterno = "";
		String apellido_materno = "";
		// para cada objeto del json
		for (int i = 0; i < arr.length(); i++) {

			JSONObject jsonProductObject = arr.getJSONObject(i);
			String curp = jsonProductObject.getString("username").toUpperCase();
			String nombre = jsonProductObject.getString("firstname");
			String apellidos = jsonProductObject.getString("lastname");
			String email = jsonProductObject.getString("email");
			String institucion = jsonProductObject.getString("institution");
			String ciudad = jsonProductObject.getString("city");
			String rfc;
			if(curp.length()>10) {
				rfc = curp.substring(0, 10);
			}else {
				rfc = curp;
			}
			ArrayList<String> completos = separaApellidos(apellidos);
			if (!completos.isEmpty()) {
				apellido_paterno = completos.get(0);
				apellido_materno = completos.get(1);
			}

			//Profesor exits = profesor.findByCurp(curp);
			Profesor exits_rfc = profesor.findByRFC(rfc);
			// Considerando que nuestra base de datos ya esta poblada se hace esta condicion
			// en caso de que no mandara un null
			
			if(exits_rfc==null) {
				try {
					profesor.saveT(nombre, apellido_paterno, apellido_materno, curp, rfc, email, institucion, ciudad, 33, 5, 4, 3);
				} catch (Exception ex) {
					String error = "Error: "+ex.toString()+"	|	";
					error=error+"Nombre:"+nombre+"	|	";
					error=error+"Apellido Paterno:"+apellido_paterno+"	|	";
					error=error+"Apellido Materno:"+apellido_materno+"	|	";
					error=error+"CURP:"+curp+"	|	";
					error=error+"RFC:"+rfc+"	|	";
					error=error+"Email:"+email+"	|	";
					error=error+"Institucion:"+institucion+"	|	";
					error=error+"Ciudad:"+ciudad;
					log.logtrace(LogTypes.CARGA_WS_BATCH_ERROR_PROFESOR, error);
					LOGGER.error("Error guardando Profesor en carga Batch WS", ex);
					ex.printStackTrace();
				}
			}else if(rfc.equals(exits_rfc.getRfc())) {
				profesor.updateProfesorByRFC(nombre, apellido_paterno, apellido_materno, email, institucion, ciudad,
						exits_rfc.getRfc());
			}
			
			/*if (exits == null) {
				profesor.saveT(nombre, apellido_paterno, apellido_materno, curp, rfc, email, institucion, ciudad, 1, 1, 1,
						1);

			} else if (curp.equals(exits.getCurp())) {
				// Se guardan profesores
				// Algunos valores se guardan por default ya que no se tienen todos los datos
				// disponibles en Moodle
				profesor.updateProfesor(nombre, apellido_paterno, apellido_materno, email, institucion, ciudad,
						exits.getCurp());

			} */
			else {
				LOGGER.debug("No se pudo agregar al profesor");
			}
		}
	}

	private static String streamToString(InputStream inputStream) {
		String text = new Scanner(inputStream, "UTF-8").useDelimiter("\\Z").next();
		return text;
	}

	public static String jsonGetRequest(String urlQueryString) {
		String json = null;
		try {
			URL url = new URL(urlQueryString);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("charset", "utf-8");
			connection.connect();
			InputStream inStream = connection.getInputStream();
			json = streamToString(inStream);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return json;
	}

	/*
	 * Método privado para la clase WebService Separa los apellidos recibidos de la
	 * base de datos de Moodle en apellido paterno y materno
	 * 
	 * @return ArrayList de apellido paterno, apellido materno
	 */
	private static ArrayList<String> separaApellidos(String s) {
		ArrayList<String> apellidos = new ArrayList();
		String espacio = " ";
		String apellido_paterno = "";
		String apellido_materno = "";
		int count = contarEspacios(s);
		if (count == 0) {
			apellido_paterno = s;
			apellido_materno = null;
			apellidos.add(apellido_paterno);
			apellidos.add(apellido_materno);
		}

		if (count >= 1) {
			apellido_paterno = s.substring(0, s.indexOf(espacio));
			apellido_materno = s.substring(s.indexOf(espacio) + 1, s.length());
			apellidos.add(apellido_paterno);
			apellidos.add(apellido_materno);

		}
		return apellidos;
	}

	private static String[] separaNombreCurso(String s) {
		String[] parts = s.split("_");
		return parts;
	}

	/*
	 * Método privado para la clase WebService Cuenta los espacios
	 * 
	 * @return int numero de espacios
	 */

	private static int contarEspacios(String s) {
		int n = s.length() - s.replaceAll(" ", "").length();
		return n;
	}

	/*
	 * Método privado para la clase WebService nos dice si un profesor aprueba o no
	 * 
	 * @return boolean
	 */
	private boolean aprobadoCalificacion(double n) {
		return n >= 60 ? true : false;
	}

}
