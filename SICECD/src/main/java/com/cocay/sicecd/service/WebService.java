package com.cocay.sicecd.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cocay.sicecd.model.Profesor;
import com.cocay.sicecd.repo.ProfesorRep;

@Controller
public class WebService {
	@Autowired
	ProfesorRep profesor;

	@RequestMapping(value = "/webservice", method = RequestMethod.GET)
	public String call_me() {

		String json = jsonGetRequest("http://localhost:8888/WB/api/users/read.php");
		System.out.println(json);
		resultJson(json);
		return "consultas/consultaWebService";
	}

	public void resultJson(String jSonResultString) {
		JSONArray arr = new JSONArray(jSonResultString);
		// para cada objeto del json
		
		//Se indica por el momento solo algunos profesores guardados el el array
		for (int i = 15; i < 20; i++) {

			JSONObject jsonProductObject = arr.getJSONObject(i);
			String nombre = jsonProductObject.getString("firstname");
			String apellidos = jsonProductObject.getString("lastname");
			String curp = jsonProductObject.getString("username");
			String email = jsonProductObject.getString("email");
			String institucion = jsonProductObject.getString("institution");
			String ciudad = jsonProductObject.getString("city");
			//Se guardan profesores
			profesor.saveT(nombre, apellidos, curp, email, institucion, ciudad, 1, 1, 1, 1);

			System.out.println("Nombre");
			System.out.println(nombre);
			System.out.println("Apellidos");
			System.out.println(apellidos);
			System.out.println("CURP");
			System.out.println(curp);
			System.out.println("Institucion");
			System.out.println(institucion);
			System.out.println("Ciudad");
			System.out.println(ciudad);
			System.out.println("-----------------------");

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

}
