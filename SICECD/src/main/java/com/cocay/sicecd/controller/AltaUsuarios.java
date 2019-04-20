package com.cocay.sicecd.controller;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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


@Controller
public class AltaUsuarios {
	
	@Autowired 
	Usuario_sysRep _usuarioSys;
	@Autowired
	Estatus_usuario_sysRep estatusSys;
	@Autowired
	Perfil_sysRep perfilSys;
	
	@RequestMapping(value = "/AdministracionCursos/formAltaUsuario", method = RequestMethod.GET)
	public String formularioAltaUsuario() {
		return "altaUsuario/agregaUsuario";
	}
	
		
	@PostMapping("/AdministracionCursos/altaUsuario")
	public ResponseEntity<String> darAltaUsuario(@RequestBody Usuario_sys consulta) 
	{
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		consulta.setPassword(passwordEncoder.encode(consulta.getPassword()));
		consulta.setFk_id_estatus_usuario_sys(estatusSys.findByNombre("Activo").get(0));
		consulta.setFk_id_perfil_sys(perfilSys.findByNombre("Consultas").get(0));
		_usuarioSys.save(consulta);
		System.out.println("--------[ENTRA]------");
		return ResponseEntity.ok("Usuario Agregado con exito");
	}
	
	@RequestMapping(value = "/AdministracionCursos/formAltaAdminstrador", method = RequestMethod.GET)
	public String formularioAltaAdminstrador() {
		return "altaUsuario/agregaAdminstrador";
	}
	
		
	@PostMapping("/AdministracionCursos/altaAdministrador")
	public ResponseEntity<String> darAltaAdministrador(@RequestBody Usuario_sys consulta) 
	{
		consulta.setFk_id_estatus_usuario_sys(estatusSys.findByNombre("Activo").get(0));
		consulta.setFk_id_perfil_sys(perfilSys.findByNombre("Administrador").get(0));
		_usuarioSys.save(consulta);
		System.out.println("--------[ENTRA]------");
		return ResponseEntity.ok("Usuario Agregado con exito");
	}	
}
