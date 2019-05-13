package com.cocay.sicecd.controller;

import com.cocay.sicecd.model.Curso;
import com.cocay.sicecd.model.Inscripcion;
import com.cocay.sicecd.model.Certificado;
import com.cocay.sicecd.model.Profesor;
import com.cocay.sicecd.repo.CertificadoRep;
import com.cocay.sicecd.repo.CursoRep;
import com.cocay.sicecd.repo.ProfesorRep;

import java.util.concurrent.TimeUnit;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.*;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.ScheduledTask;
import org.json.*;

@Component
public class CertificadoMasivoController {
	private static final Logger logger = LoggerFactory.getLogger(ScheduledTask.class);
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	private final String RUTA_LOCAL = "/your/path/";
	private final String URL_RSM = "http://localhost/moodle3.5/mod/simplecertificate/wscertificados.php";
	private final String TEMP_ZIP = "/your/path/tmp/";
	@Autowired
	CertificadoRep bd_certificado;
	@Autowired
	ProfesorRep bd_profesor;
	@Autowired
	CursoRep bd_curso;

	/*
	 * public void scheduleTaskWithFixedRate() {}
	 * 
	 * public void scheduleTaskWithFixedDelay() {}
	 * 
	 * public void scheduleTaskWithInitialDelay() {}
	 */
	/**
	 * Metodo que obtiene certificados masivamente para actualizarlos o traer nuevos
	 * archivos. (Cada 2 horas realiza la tarea)
	 * 
	 * @throws Exception
	 */
	@Scheduled(cron = "0 5 21 * * ?")
	public void scheduleTaskWithCronExpression() throws Exception {
		HttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(URL_RSM);
		JSONObject json = new JSONObject();
		LinkedList<Profesor> profesores = new LinkedList<>(bd_profesor.findAll());
		if(profesores.size() == 0){
			return;
		}
		System.out.println("A punto de buscar profesores.");
		int k = 0;
		for (Profesor p : profesores) {
			LinkedList<Certificado> cert = new LinkedList<>(p.getCertificados());
			if (cert.size() == 0) {
				LinkedList<Inscripcion> ins = new LinkedList<>(p.getInscripciones());
				if (ins.size() == 0) {
					System.out.println("No hay inscripciones asociadas!");
					continue;
				}
				
				for (Inscripcion i : ins) {
					//int fkc = i.getFk_id_grupo().getFk_id_curso();
					//Curso caux = bd_curso.findByID(fkc);
					Curso caux = i.getFk_id_grupo().getFk_id_curso();
					//if(!caux.getNombre().equals("COSDAC 2018")) {
						//continue;
					//}
					System.out.println("**\n" + p.getCorreo()+ "\n" + caux.getNombre() + "\n**");
					json.put("correo" + k, p.getCorreo());
					json.put("curso" + k, caux.getNombre());
					json.put("tiempo" + k, 0);
					System.out.println("Se insertaron elementos en el JSON (certificados no presentes)");
					k++;
				}
				continue;
			}/*
			for (Certificado c : cert) {
				System.out.println("**\n" + p.getCorreo()+ "\n" + c.getFk_id_curso().getNombre() + "\n**");
				json.put("correo" + k, p.getCorreo());
				json.put("curso" + k, c.getFk_id_curso().getNombre());
				json.put("tiempo" + k, c.getTiempo_creado());
				System.out.println("Se insertaron elementos en el JSON (certificadospresentes)");
				k++;
			}*/
			
			
		}
		json.put("cuenta", k);
		//System.out.println(json.toString());

		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("json", json.toString()));
		post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		// esperar respuesta
		HttpResponse response = client.execute(post);

		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "ISO_8859_1"));
		String jsonText = "";
		String linea = null;
		while ((linea = rd.readLine()) != null) {
			// System.out.println(linea);
			jsonText += linea;
		}
		JSONObject json_r = null;
		try {
			json_r = new JSONObject(jsonText);
		} catch(Exception e) {
			System.out.println(jsonText);
			return;
		}
		//JSONObject json_r = new JSONObject(jsonText);
		String msg = (String) json_r.get("mensaje");
		System.out.println(msg);
		//msg = new String(java.util.Base64.getDecoder().decode(msg),Charset.forName("UTF-8"));
		if(!msg.equals("NULL")) {
			System.out.println("No hay certificados nuevos!");
			return;
		}

		String mns = (String) json_r.get("zip");
		// System.out.println(mns);

		byte[] bytearray = java.util.Base64.getDecoder().decode(mns);
		String path = RUTA_LOCAL + "certificados.zip";
		File tempdir = new File(RUTA_LOCAL);
		if (!tempdir.exists()) {
			tempdir.mkdirs();
		}
		File out = new File(path);
		try (FileOutputStream os = new FileOutputStream(out)) {
			os.write(bytearray);
			System.out.println("Archivo escrito!");
		} catch (Exception e) {
			System.out.println(e);
		}
		byte[] buffer = new byte[1024];
		File tmp = new File(TEMP_ZIP);
		if (!tmp.exists()) {
			tmp.mkdirs();
		}
		try {
			ZipFile zpf = new ZipFile(out, Charset.forName("Cp437"));
			Enumeration e = zpf.entries();
			ZipEntry ze;
			// System.out.println("PASE");
			while (e.hasMoreElements()) {
				ze = (ZipEntry) e.nextElement();
				String fileName = ze.getName();
				File newFile = new File(TEMP_ZIP + fileName);
				new File(newFile.getParent()).mkdirs();

				BufferedInputStream bis = new BufferedInputStream(zpf.getInputStream(ze));
				FileOutputStream fos = new FileOutputStream(newFile);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				int len;
				while ((len = bis.read(buffer, 0, 1024)) != -1) {
					fos.write(buffer, 0, len);
					// System.out.println("Escribiendo");
				}
				bos.flush();
				bos.close();
				bis.close();
			}
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		// comienza a mover los pdfs a la ruta elegida
		for (File f : tmp.listFiles()) {
			Curso c = bd_curso.findByNombre(f.getName());
			JSONObject ar = new JSONObject(json_r.get(f.getName()).toString());
			for (File f2 : f.listFiles()) {
				Profesor p = bd_profesor.findByCorreo(f2.getName());
				for (File f3 : f2.listFiles()) {
					String pt = RUTA_LOCAL + p.getPk_id_profesor() + "_" + f3.getName() + ".pdf";
					FileInputStream fs = new FileInputStream(f3);
					File aux = new File(pt);
					if (aux.exists()) {
						aux.delete();
					}
					try (FileOutputStream os = new FileOutputStream(aux)) {
						os.write(fs.readAllBytes());
					} catch (Exception e) {
						System.out.println(e);
					}
					f3.delete();// elimina archivo
					Certificado cert = new Certificado();
					cert.setRuta(pt);
					long tt = Long.parseLong((String)ar.get(p.getCorreo()));
					cert.setTiempo_creado(tt);
					cert.setFk_id_curso(c);
					cert.setFk_id_profesor(p);
					bd_certificado.save(cert);
				}
				f2.delete();// elimina directorio (usuario)
			}
			f.delete();// elimina directorio padre (curso)
		}
		out.delete();// elimina zip
		// tarea completada*/
	}
}