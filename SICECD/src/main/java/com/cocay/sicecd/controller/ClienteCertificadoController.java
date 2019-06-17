package com.cocay.sicecd.controller;

import com.cocay.sicecd.model.Curso;
import com.cocay.sicecd.model.Certificado;
import com.cocay.sicecd.model.Profesor;
import com.cocay.sicecd.repo.CertificadoRep;
import com.cocay.sicecd.repo.CursoRep;
import com.cocay.sicecd.repo.ProfesorRep;
import com.cocay.sicecd.security.pdf.SeguridadPDF;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.json.*;

@Controller
@PropertySource(ignoreResourceNotFound = true, value = "classpath:application-cert.properties")
public class ClienteCertificadoController {
	@Value("${ws.ruta_local}")
	private String RUTA_LOCAL;
	@Value("${ws.url_rs}")
	private String URL_RS;
	
	@Autowired
	CertificadoRep bd_certificado;
	@Autowired
	ProfesorRep bd_profesor;
	@Autowired
	CursoRep bd_curso;

	/**
	 * Metodo que obtiene un certificado dado un correo y nombre de curso, el cual
	 * es depositado en alguna carpeta y guardada su direccion en una base de datos.
	 * 
	 * @param correo       Correo de usuario.
	 * @param nombre_curso Nombre del curso en el cual esta inscrito el usuario.
	 * @return Devuelve la ruta en el sistema de archivos si el certificado se
	 *         obtuvo y se ha guardado en Disco y en la BD.
	 */
	public String obtenCertificado(Profesor profesor, Curso curso) throws Exception {

		String correo = profesor.getCorreo();
		String nombre_curso = curso.getNombre();
		Certificado cert = new Certificado();
		cert.setFk_id_curso(curso);
		cert.setFk_id_profesor(profesor);
		correo = correo.replaceFirst("@", "%40");
		System.out.println(correo);
		nombre_curso = nombre_curso.replaceAll(" ", "%20");
		System.out.println(nombre_curso);
		String url = URL_RS + "email=" + correo + "&nc=" + nombre_curso;
		HttpClient client = HttpClients.createDefault();
		HttpGet request = new HttpGet(url);
		HttpResponse response = client.execute(request);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		String jsonText = "";
		String linea = null;
		while ((linea = rd.readLine()) != null) {
			// System.out.println(linea);
			jsonText += linea;
		}
		JSONObject json = new JSONObject(jsonText);
		String mensaje = (String) json.get("mensaje");
		if (!mensaje.equals("NULL")) {
			return null;
		}
		String string_pdf = (String) json.get("bytespdf");
		byte[] bytearray = java.util.Base64.getDecoder().decode(string_pdf);
		//String aux = (String) json.get("nombre_archivo");
		//String na = new String(new String(java.util.Base64.getDecoder().decode(aux),"ISO-8859-1").getBytes("UTF-8"),"UTF-8");
		String path = RUTA_LOCAL + curso.getNombre() + "/" + curso.getNombre() + "_" + profesor.getPk_id_profesor() + ".pdf";
		File out = new File(path);
		new File(out.getParent()).mkdirs();
		try (FileOutputStream os = new FileOutputStream(out)) {
			os.write(bytearray);
			// System.out.println("Archivo escrito!");
		} catch (Exception e) {
			System.out.println(e);
		}
		SeguridadPDF spdf = new SeguridadPDF();
		String nombrec = profesor.getNombre() + " " + profesor.getApellido_paterno() + " " + profesor.getApellido_materno();
		spdf.cifraPdf(path, nombrec, curso.getNombre());
		cert.setTiempo_creado(Long.parseLong((String) json.get("tiempo")));
		cert.setRuta(path);
		bd_certificado.save(cert);
		return path;
		// return true;
	}

	@RequestMapping(value = "/certificado", method = RequestMethod.GET)
	public String muestra(Model model) {
		return "Certificado/certificado";
	}

	@RequestMapping(value = "/certificadoRes", method = RequestMethod.GET)
	public String procesaCertificado(Model model, HttpServletRequest request) throws Exception {
		String correo = request.getParameter("correo");
		String nombre_c = request.getParameter("nc");
		Profesor profr = bd_profesor.findByCorreo(correo);// obtener usuario de base de datos
		Curso curso = bd_curso.findByNombre(nombre_c);// obtener curso de bd
		String path = obtenCertificado(profr, curso);
		if (path != null) {
			model.addAttribute("mensaje", path);
		} else {
			model.addAttribute("mensaje", "No se encontro el certificado!");
		}
		return "Certificado/resultado";
	}

}
