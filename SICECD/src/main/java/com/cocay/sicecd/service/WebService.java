package com.cocay.sicecd.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cocay.sicecd.LogTypes;
import com.cocay.sicecd.model.Grupo;
import com.cocay.sicecd.model.Inscripcion;
import com.cocay.sicecd.model.Profesor;
import com.cocay.sicecd.model.Url_ws;
import com.cocay.sicecd.model.Url_ws_inscripcion;
import com.cocay.sicecd.model.Url_ws_profesor;
import com.cocay.sicecd.repo.GrupoRep;
import com.cocay.sicecd.repo.InscripcionRep;
import com.cocay.sicecd.repo.ProfesorRep;
import com.cocay.sicecd.repo.Url_ws_inscripcionRep;
import com.cocay.sicecd.repo.Url_ws_profesorRep;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component

@PropertySource(ignoreResourceNotFound = true, value = "classpath:application.properties")
public class WebService {
	@Autowired
	ProfesorRep profesor;
	@Autowired
	GrupoRep grupo_rep;
	@Autowired
	InscripcionRep inscripcionRep;
	@Autowired
	Url_ws_profesorRep urls;
	@Autowired
	Url_ws_inscripcionRep urls_inscripcion;
	@Autowired
	Logging log;
	private String key;
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	public interface ReturnTypeOne {
	}

	public interface ReturnTypeTwo {
	}
	public interface ReturnTypeThree {
	}

	
	/*
	 * Se compone de dos secciones. Primero en el que se envían dos tareas al grupo de hilos. 
	 * Cada llamada ExecutorService.submit () devuelve inmediatamente un valor futuro del cálculo de la tarea. 
	 * Las tareas se envían inmediatamente en la llamada a submit () y se ejecutan en segundo plano. 
	 * Por supuesto que pueden hacer mas de 3 tareas
	 * En la segunda sección se obtienen los valores de futuros. 
	 * Lo que sucede es que la llamada a Future.get () bloquea el subproceso actual hasta que se calcula el valor. 
	 * No significa que cualquier tarea esté bloqueada, todas se estén ejecutando, el hilo solo espera hasta que una tarea determinada se complete y devuelva un valor.
	 * Una vez que se devuelve el primer valor, se realiza la segunda llamada Future.get ().
	 * En este caso, puede o no bloquearse. Si la segunda tarea ya ha finalizado (posiblemente antes de la primera tarea), el valor se devuelve inmediatamente. 
	 * Si la segunda tarea aún se está ejecutando, la llamada bloquea el subproceso actual hasta que se calcula el valor.*/
	@Scheduled(cron = "30 * * * * *")
	public void runTwo()  {
		ExecutorService executor = Executors.newFixedThreadPool(3);

		// Tenemos listas las tareas()
		Future<ReturnTypeOne> first = executor.submit(new Callable<ReturnTypeOne>() {
			@Override
			public ReturnTypeOne call() throws Exception {
				get_Profesores();
				return null;
			}
		});
		Future<ReturnTypeTwo> second = executor.submit(new Callable<ReturnTypeTwo>() {
			@Override
			public ReturnTypeTwo call() throws Exception {
				get_Calificaciones();
				return null;
			}
		});
		Future<ReturnTypeThree> third = executor.submit(new Callable<ReturnTypeThree>() {
			@Override
			public ReturnTypeThree call() throws Exception {
				get_Profesores();
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
	
	public void get_Profesores()  {

		LinkedList<Url_ws_profesor> links = new LinkedList<>(urls.findVarios());

		if (links.size() == 0) {
			LOGGER.debug("No hay urls para el proceso obtener Profesores");
			return;
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
	
	public void get_Calificaciones() {

		LinkedList<Url_ws_inscripcion> links = new LinkedList<>(urls_inscripcion.findVarios());
		if (links.size() == 0) {
			LOGGER.debug("No hay urls para el proceso ObtenerCalificaciones");
			return;
		}
		for (Url_ws_inscripcion url : links) {
			System.out.println("Se conecto" + url.getUrl());
			String json = jsonGetRequest(url.getUrl() + "?clave=" + key);
			System.out.println(json);
			insert_Grade(json);
			//log.setTrace(LogTypes.ACTUALIZAR_PROFESOR);
			//log.setTrace(LogTypes.AGREGAR_PROFESOR);
		}
	}
	
	public void insert_Grade(String jSonResultString) {
		JSONArray arr = new JSONArray(jSonResultString);
		for (int i = 0; 0 < 2; i++) {
			JSONObject jsonProductObject = arr.getJSONObject(i);
			String calificacion = jsonProductObject.getString("grade");
			String nombre_curso = jsonProductObject.getString("shortname");
			String curp = jsonProductObject.getString("username");
			int id_grupo = jsonProductObject.getInt("idnumber");
			double result =Double.parseDouble(calificacion);
			boolean aprobado=aprobadoCalificacion(result);
			Profesor exits = profesor.findByCurp(curp);
			System.out.println(calificacion);
			System.out.println(curp);

			inscripcionRep.saveI(id_grupo, exits.getPk_id_profesor(), calificacion,aprobado);
			
			if (curp.equals(exits.getCurp())) {
				
			}else {
				LOGGER.debug("No existe el profesor , por lo que la califcación no puede ser asignada");
				return;
			}
			

		}
	}

	public void insert_update_Profesor(String jSonResultString) {
		JSONArray arr = new JSONArray(jSonResultString);
		String apellido_paterno = "";
		String apellido_materno = "";
		// para cada objeto del json
		for (int i = 0; i < arr.length(); i++) {

			JSONObject jsonProductObject = arr.getJSONObject(i);
			String curp = jsonProductObject.getString("username");
			String nombre = jsonProductObject.getString("firstname");
			String apellidos = jsonProductObject.getString("lastname");
			String email = jsonProductObject.getString("email");
			String institucion = jsonProductObject.getString("institution");
			String ciudad = jsonProductObject.getString("city");
			String calificacion = jsonProductObject.getString("grade");
			ArrayList<String> completos = separaApellidos(apellidos);
			if (!completos.isEmpty()) {
				apellido_paterno = completos.get(0);
				apellido_materno = completos.get(1);
			}

			Profesor exits = profesor.findByCurp(curp);
			// Considerando que nuestra base de datos ya esta poblada se hace esta condicion
			// en caso de que no mandara un null
			if (exits == null) {
				profesor.saveT(nombre, apellido_paterno, apellido_materno, curp, email, institucion, ciudad, 1, 1, 1,
						1);

			} else if (curp.equals(exits.getCurp())) {
				// Se guardan profesores
				// Algunos valores se guardan por default ya que no se tienen todos los datos
				// disponibles en Moodle
				profesor.updateProfesor(nombre, apellido_paterno, apellido_materno, email, institucion, ciudad,
						exits.getCurp());

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
	private boolean aprobadoCalificacion(double n){
		return  n>=60 ? true : false;
	}

}
