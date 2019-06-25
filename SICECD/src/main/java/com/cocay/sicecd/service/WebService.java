package com.cocay.sicecd.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

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

import com.cocay.sicecd.model.Profesor;
import com.cocay.sicecd.repo.ProfesorRep;

@Component

@PropertySource(ignoreResourceNotFound = true, value = "classpath:application.properties")
public class WebService {
	@Autowired
	ProfesorRep profesor;
	@Value("${ws.url_profesor}")
	private String url;

	// @RequestMapping(value = "/webservice", method = RequestMethod.GET)
//	@Scheduled(cron = "30 * * * * *")
//	public String call_me() {
//
//		String json = jsonGetRequest(url);
//		System.out.println(json);
//		insert_update_Profesor(json);
//		System.out.println("----->enTRO");
//		return "consultas/consultaWebService";
//	}

	public void insert_update_Profesor(String jSonResultString) {
		JSONArray arr = new JSONArray(jSonResultString);
		String apellido_paterno = "";
		String apellido_materno = "";
		// para cada objeto del json

		// Se indica por el momento solo algunos profesores guardados el el array
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
				System.out.println("Se actualizara el dato");
				profesor.updateProfesor(nombre, apellido_paterno, apellido_materno, email, institucion, ciudad,
						exits.getCurp());
				System.out.println("Se actualizo");

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
	 * Método privado para la clase WebService
	 * Separa los apellidos recibidos de la base de datos de Moodle
	 * en apellido paterno y materno
	 * @return ArrayList de apellido paterno, apellido materno*/
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
	 * Método privado para la clase WebService
	 * Cuenta los espacios 
	 * @return  int numero de espacios*/

	private static int contarEspacios(String s) {
		int n = s.length() - s.replaceAll(" ", "").length();
		return n;
	}

}
