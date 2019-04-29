package com.cocay.sicecd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cocay.sicecd.model.Usuario_sys;
import com.cocay.sicecd.repo.Usuario_sysRep;
import com.cocay.sicecd.service.SendMailService;

@Controller
public class RecuperaContrasena {
	@Autowired
	Usuario_sysRep _usuarioSys;
	@Autowired
	SendMailService _email;

	@PostMapping("/enviarecupera")
	private ResponseEntity<String> enviarecupera(@RequestBody Usuario_sys consulta) {
		String mensaje="";
		if ( _usuarioSys.findByRfc(consulta.getRfc()).isEmpty()) {
			System.out.println("[USUARIO--------]"+_usuarioSys.findByRfc("FrankConsultaas").get(0).getNombre()); 
			mensaje="rfc no valido";
		}else {
			Usuario_sys guardado=_usuarioSys.findByRfc(consulta.getRfc()).get(0);
			String codigo = String.valueOf((int) (Math.random() * 1000) + 1);
			String link = "http://localhost:8080/configuracionPass?codigo=" + codigo + "&usuario="
					+ guardado.getPk_id_usuario_sys();
			String from = "cocayprueba@gmail.com";
			String to = guardado.getCorreo();
			String subject = "Recuperacion de contraseña";
			String body = "Hola da clic al siguiente  link \n" + link + "\npara confirmar tu correo.";
			_email.sendMail(from, to, subject, body);
			guardado.setCodigorecupera(codigo);
			guardado.setConfirmarecupera("true");
			guardado.setCorreocambio(consulta.getCorreo());
			_usuarioSys.save(guardado);
			String correoo=guardado.getCorreo();
			mensaje="Se ha enviado un link a "+correoo.substring(0,3)+"*****"+correoo.substring(correoo.length()-3,correoo.length());
		}
		
		return ResponseEntity.ok(mensaje);
		
	}
	
	
}
