package com.cocay.sicecd.controller;
import java.util.ArrayList;
import java.util.List;

import com.cocay.sicecd.service.Logging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cocay.sicecd.model.Usuario_sys;
import com.cocay.sicecd.repo.Estatus_usuario_sysRep;
import com.cocay.sicecd.repo.Perfil_sysRep;
import com.cocay.sicecd.repo.Usuario_sysRep;
import com.cocay.sicecd.service.SendMailService;


@Controller
public class AltaUsuarios {
	
	@Autowired 
	Usuario_sysRep _usuarioSys;
	@Autowired
	Estatus_usuario_sysRep estatusSys;
	@Autowired
	Perfil_sysRep perfilSys;
	@Autowired
	SendMailService _email;
	@Autowired
	Logging log;
	
	
	//Alta usuario
	@RequestMapping(value = "/AdministracionCursos/formAltaUsuario", method = RequestMethod.GET)
	public String formularioAltaUsuario() {
		return "altaUsuario/agregaUsuario";
	}
	
		
	@PostMapping("/AdministracionCursos/altaUsuario")
	public ResponseEntity<String> darAltaUsuario(@RequestBody Usuario_sys consulta) 
	{

		consulta.setFk_id_estatus_usuario_sys(estatusSys.findByNombre("Inactivo").get(0));
		consulta.setConfirmacion("true");
		consulta.setConfirmacioncorreo("false");
		consulta.setConfirmarecupera("false");
		String codigo=String.valueOf((int) (Math.random() * 1000) + 1);
		consulta.setCodigo(codigo);
		_usuarioSys.save(consulta);
		Usuario_sys guardado= _usuarioSys.findByRfc(consulta.getRfc()).get(0);
		String link="http://localhost:8080/configuracionPass?codigo="+codigo+"&usuario="+guardado.getPk_id_usuario_sys();
		String from="cocayprueba@gmail.com";
		String to=consulta.getCorreo();
		String subject="Activación de cuenta";
		String body="Hola da clic al siguiente  link \n" + 
				link+ "\npara activar tu cuenta y configurar una nueva contraseña.";
		_email.sendMail(from, to, subject, body);
		return ResponseEntity.ok("Usuario Agregado con exito");
	}
	

	@GetMapping("/configuracionPass")
	public String configuracionPass(@RequestParam(name = "usuario") int id,
									@RequestParam(name = "codigo") String codigo,
									Model model) 
	{
		model.addAttribute("id", id);
		model.addAttribute("codigo", codigo);
		return "altaUsuario/configuracionpass";
	}

	@PostMapping("/activacion")
	public String ActivacionAdministrador(
			@RequestParam(name = "id") int id,
			@RequestParam(name = "codigo") String codigo,
			@RequestParam(name = "contrasena") String contrasena) 
	{
		if (_usuarioSys.existsById(id)) {
			Usuario_sys candidato= (_usuarioSys.findById(id)).get();
			if (codigo.equals(candidato.getCodigo()) ) {
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				candidato.setPassword(passwordEncoder.encode(contrasena));
				candidato.setFk_id_estatus_usuario_sys(estatusSys.findByNombre("Activo").get(0));
				candidato.setConfirmacion("false");
				_usuarioSys.save(candidato);
			}
			if ( codigo.equals(candidato.getCodigorecupera()) ) {
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				candidato.setPassword(passwordEncoder.encode(contrasena));
				candidato.setConfirmarecupera("false");
				_usuarioSys.save(candidato);
			}
			
		}
		
		return "redirect:/login?mensaje=Ya puedes realizar login";
	}
	
	@GetMapping("/AdministracionCursos/prueba")
	@ResponseBody
	public String prueba() {

		log.setTrace("prueba");
		log.setTrace("prueba", "hola");
		return "hola";
	}
}



