package com.cocay.sicecd.controller;

import com.cocay.sicecd.model.Curso;
import com.cocay.sicecd.model.Inscripcion;
import com.cocay.sicecd.model.Certificado;
import com.cocay.sicecd.model.Profesor;
import com.cocay.sicecd.repo.CertificadoRep;
import com.cocay.sicecd.repo.CursoRep;
import com.cocay.sicecd.repo.ProfesorRep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.json.*;

@Controller
public class ClienteCertificadoController {

	private final String RUTA_LOCAL = "/home/jrivera/";
	private final String URL_RS = "http://localhost/moodle3.5/mod/simplecertificate/wscertificado.php?";
	private final String URL_RSM = "http://localhost/moodle3.5/mod/simplecertificate/wscertificados.php";
	private final String TEMP_ZIP = "/home/jrivera/tmp/";
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
		String aux = (String) json.get("nombre_archivo");
		String na = String.valueOf(java.util.Base64.getDecoder().decode(aux));
		String path = RUTA_LOCAL + profesor.getPk_id_profesor() + "_" + na + ".pdf";
		File out = new File(path);
		try (FileOutputStream os = new FileOutputStream(out)) {
			os.write(bytearray);
			// System.out.println("Archivo escrito!");
		} catch (Exception e) {
			System.out.println(e);
		}
		cert.setTiempo_creado((long) json.get("tiempo"));
		cert.setRuta(path);
		bd_certificado.save(cert);
		return path;
		// return true;
	}

	@RequestMapping(value = "/certificado", method = RequestMethod.GET)
	public String muestra(Model model) {
		try {
			System.out.println("CERTIFICADO MASIVO");
			certificadosMasivo();
			System.out.println("FIN");
		} catch (Exception e) {
				System.out.println(e);
		}

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

	/**
	 * Metodo que obtiene certificados masivamente para actualizarlos o traer nuevos archivos.
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public void certificadosMasivo() throws ClientProtocolException, IOException {
		HttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(URL_RSM);
		JSONObject json = new JSONObject();
		LinkedList<Profesor> profesores = new LinkedList<>(bd_profesor.findAll());
		System.out.println("A punto de buscar profesores.");
		for (Profesor p : profesores) {
			LinkedList<Certificado> cert = new LinkedList<>(p.getCertificados());
			if (cert.size() == 0) {
				LinkedList<Inscripcion> ins = new LinkedList<>(p.getInscripciones());
				if (ins.size() == 0) {
					System.out.println("No hay inscripciones asociadas!");
					continue;
				}
				int k = 0;
				for (Inscripcion i : ins) {
					int fkc = i.getFk_id_grupo().getFk_id_curso();
					Curso caux = bd_curso.findByID(fkc);
					json.put("correo" + k, p.getCorreo());
					json.put("curso" + k, caux.getNombre());
					json.put("tiempo" + k, 0);
					k++;
				}
				json.put("cuenta", k);
				System.out.println("Se insertaron elementos en el JSON (certificados no presentes)");
				continue;
			}
			int k = 0;
			for (Certificado c : cert) {
				json.put("correo" + k, p.getCorreo());
				json.put("curso" + k, c.getFk_id_curso().getNombre());
				json.put("tiempo" + k, c.getTiempo_creado());
			}
			json.put("cuenta", k);
			System.out.println("Se insertaron elementos en el JSON (certificados presentes)");
		}
		//StringEntity se = new StringEntity(json.toString());
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("json", json.toString()));
		System.out.println(json.toString());
		System.out.println(params);
		post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		// esperar respuesta
		HttpResponse response = client.execute(post);
		
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		String jsonText = "";
		String linea = null;
		while ((linea = rd.readLine()) != null) {
			System.out.println(linea);
			jsonText += linea;
		}
		JSONObject json_r = new JSONObject(jsonText);
		String mns = (String) json_r.get("zip");
		byte[] bytearray = java.util.Base64.getDecoder().decode(mns);
		String path = RUTA_LOCAL + "certificados.zip";
		File tempdir = new File(RUTA_LOCAL);
		if (!tempdir.exists()) {
			tempdir.mkdirs();
		}
		File out = new File(path);
		try (FileOutputStream os = new FileOutputStream(out)) {
			os.write(bytearray);
			// System.out.println("Archivo escrito!");
		} catch (Exception e) {
			System.out.println(e);
		}
		FileInputStream fis;
		byte[] buffer = new byte[1024];
		try {
			fis = new FileInputStream(TEMP_ZIP);
			ZipInputStream zis = new ZipInputStream(fis);
			ZipEntry ze = zis.getNextEntry();
			while (ze != null) {
				String fileName = ze.getName();
				File newFile = new File(TEMP_ZIP + fileName);
				new File(newFile.getParent()).mkdirs();
				FileOutputStream fos = new FileOutputStream(newFile);
				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fos.close();
				zis.closeEntry();
				ze = zis.getNextEntry();
			}
			zis.closeEntry();
			zis.close();
			fis.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		//comienza a mover los pdfs a la ruta elegida
		for (File f : tempdir.listFiles()) {
			Curso c = bd_curso.findByNombre(f.getName());
			for (File f2 : f.listFiles()) {
				Profesor p = bd_profesor.findByCorreo(f2.getName());
				for (File f3 : f2.listFiles()) {
					String pt = RUTA_LOCAL + p.getPk_id_profesor() + "_" + f3.getName() + ".pdf";
					FileInputStream fs = new FileInputStream(f3);
					File aux = new File(pt);
					if(aux.exists()) {
						aux.delete();
					}
					try (FileOutputStream os = new FileOutputStream(aux)) {
						os.write(fs.readAllBytes());
					} catch (Exception e) {
						System.out.println(e);
					}
					f3.delete();//elimina archivo
					Certificado cert = new Certificado();
					cert.setRuta(pt);
					//cert.setTiempo_creado(0);
					cert.setFk_id_curso(c);
					cert.setFk_id_profesor(p);
					bd_certificado.save(cert);
				}
				f2.delete();//elimina directorio (usuario)
			}
			f.delete();//elimina directorio padre (curso)
		}
		out.delete();//elimina zip
		//tarea completada*/
	}

}
