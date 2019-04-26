package com.cocay.sicecd.controller;

import com.cocay.sicecd.model.Curso;
import com.cocay.sicecd.model.Certificado;
import com.cocay.sicecd.model.Profesor;
import com.cocay.sicecd.repo.CertificadoRep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.json.*;



@Controller
public class ClienteCertificadoController {

	private final String RUTA_LOCAL = "/home/jrivera/";
	private final String URL_RS = "http/localhost/moodle35/mod/simplecertificate/test.php?";
	@Autowired
	CertificadoRep bd_certificado;
	/**
	* Metodo que obtiene un certificado dado un correo y nombre de curso, el cual es depositado en alguna carpeta
	* y guardada su direccion en una base de datos.
	* @param correo Correo de usuario.
	* @param nombre_curso Nombre del curso en el cual esta inscrito el usuario.
	* @return Devuelve true si el certificado se obtuvo y se ha guardado en Disco y en la BD.
	*/
	public boolean obtenCertificado(Profesor profesor, Curso curso) throws Exception{
		
		String correo = profesor.getCorreo();
		String nombre_curso = curso.getNombre();
		Certificado cert = new Certificado();
		cert.setFk_id_curso(curso);
		cert.setFk_id_profesor(profesor);
		correo = correo.replaceFirst("@", "%40");
		nombre_curso = nombre_curso.replaceAll(" ", "%20");
		String url = URL_RS + "email=" + correo + "&nc="
				+ nombre_curso;
		@SuppressWarnings("deprecation")
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		HttpResponse response = client.execute(request);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		String jsonText = "";
		String linea = null;
		while ((linea = rd.readLine()) != null) {
			System.out.println(linea);
			jsonText += linea;
		}
		JSONObject json = new JSONObject(jsonText);
		if((String)json.get("mensaje") != "NULL"){
			return false;
		}
		String string_pdf = (String) json.get("bytespdf");
		byte[] bytearray = java.util.Base64.getDecoder().decode(string_pdf);
		String path = RUTA_LOCAL + profesor.getPk_id_profesor() + "_" + (String)json.get("nombre_archivo") + ".pdf";
		File out = new File(path);
		try (FileOutputStream os = new FileOutputStream(out)) {
			os.write(bytearray);
			//System.out.println("Archivo escrito!");
		} catch (Exception e) {
			System.out.println(e);
		}
		cert.setRuta(path);
		bd_certificado.save(cert);
		return true;
	}
}
