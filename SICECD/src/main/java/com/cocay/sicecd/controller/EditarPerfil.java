package com.cocay.sicecd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cocay.sicecd.model.Usuario_sys;
import com.cocay.sicecd.repo.Usuario_sysRep;
import com.cocay.sicecd.service.SendMailService;

@Controller
public class EditarPerfil {

	@Autowired
	Usuario_sysRep _usuarioSys;
	@Autowired
	SendMailService _email;
	
	
	
	private void editarCorreo(Usuario_sys guardado, Usuario_sys consulta) {
		String codigo = String.valueOf((int) (Math.random() * 1000) + 1);
		String link = "http://localhost:8080/confirmacorreo?codigo=" + codigo + "&usuario="
				+ guardado.getPk_id_usuario_sys();
		String from = "cocayprueba@gmail.com";
		String to = consulta.getCorreo();
		String subject = "Cambio de correo";
		String body = "Hola da clic al siguiente  link \n" + link + "\npara confirmar tu correo.";
		_email.sendMail(from, to, subject, body);
		guardado.setCodigoCorreo(codigo);
		guardado.setConfirmacion("true");
		guardado.setCorreocambio(consulta.getCorreo());
		_usuarioSys.save(guardado);
	}
	
	@GetMapping("/AdministracionCursos/listaUsuarios")
	private ModelAndView listaUsuarios() {
		ModelAndView model=new ModelAndView("editarPerfil/listausuarios");
		model.addObject("usuarios",(List<Usuario_sys>)_usuarioSys.findAll());
		
		return model;
	}

	@GetMapping(value = "/AdministracionCursos/formEditarPerfilUsuario")
	public ModelAndView formEditarPerfilUsuario(@RequestParam(name = "rfc") String rfc) {
		Usuario_sys cambio = (_usuarioSys.findByRfc(rfc.trim())).get(0);
		ModelAndView model = new ModelAndView("editarPerfil/formEditarPerfilUsuario");
		model.addObject("usuario", cambio);
		return model;
	}

	@PostMapping(value = "/AdministracionCursos/editarperfilusuario")
	private ResponseEntity<String> editarPerfilUsuario(@RequestBody Usuario_sys consulta) {
		Usuario_sys guardado = _usuarioSys.findById(consulta.getPk_id_usuario_sys()).get();
		String cambios = "";
		if (!guardado.getRfc().equals(consulta.getRfc())) {
			cambios += "Rfc de " + guardado.getRfc() + " a " + consulta.getRfc() + "\n";
			guardado.setRfc(consulta.getRfc());
		}
		if (!guardado.getNombre().equals(consulta.getNombre())) {
			cambios += "Nombre de " + guardado.getNombre() + " a " + consulta.getNombre() + "\n";
			guardado.setNombre(consulta.getNombre());
		}
		if (!guardado.getApellido_paterno().equals(consulta.getApellido_paterno())) {
			cambios += "Apellido Paterno de " + guardado.getApellido_paterno() + " a " + consulta.getApellido_paterno()
					+ "\n";
			guardado.setApellido_paterno(consulta.getApellido_paterno());
		}
		if (!guardado.getApellido_materno().equals(consulta.getApellido_materno())) {
			cambios += "Apellido Materno de " + guardado.getApellido_materno() + " a " + consulta.getApellido_materno()
					+ "\n";
			guardado.setApellido_materno(consulta.getApellido_materno());
		}
		if (!guardado.getFk_id_perfil_sys().getNombre().equals(consulta.getFk_id_perfil_sys().getNombre())) {
			cambios += "Perfil " + guardado.getFk_id_perfil_sys().getNombre() + " a "
					+ consulta.getFk_id_perfil_sys().getNombre() + "\n";
			guardado.setFk_id_perfil_sys(consulta.getFk_id_perfil_sys());
		}

		if (!guardado.getFk_id_estatus_usuario_sys().getNombre()
				.equals(consulta.getFk_id_estatus_usuario_sys().getNombre())) {
			cambios += "Estatus " + guardado.getFk_id_estatus_usuario_sys().getNombre() + " a "
					+ consulta.getFk_id_estatus_usuario_sys().getNombre() + "\n";
			guardado.setFk_id_estatus_usuario_sys(consulta.getFk_id_estatus_usuario_sys());
		}

		if (!consulta.getCorreo().equals(guardado.getCorreo())) {
			editarCorreo(guardado, consulta);
		}
		String frome = "cocayprueba@gmail.com";
		String toe = guardado.getCorreo();
		String subjecte = "Edicion de Perfil";
		String bodye = "Hola se han echo una serie de cambios en tu perfil\n" + cambios;

		if (!cambios.equals("")) {
			_email.sendMail(frome, toe, subjecte, bodye);
			_usuarioSys.save(guardado);
		}

		return ResponseEntity.ok("Usuario Editado con exito");

	}
	
	
	@PostMapping("/AdministracionCursos/renviarcambiocorreo")
	private ResponseEntity<String> renviarCambioCorreo(@RequestBody Usuario_sys consulta) {
		Usuario_sys guardado = _usuarioSys.findByRfc(consulta.getRfc()).get(0);

		editarCorreo(guardado, consulta);
		return ResponseEntity.ok("Correo renviado");
	}

	@GetMapping(value = "/confirmacorreo")
	private String confirmaCorreo(
			@RequestParam(name = "usuario") int id,
			@RequestParam(name = "codigo") String codigo)
	{
		if (_usuarioSys.existsById(id)) {
			Usuario_sys u=_usuarioSys.findById(id).get();
			if (u.getCodigoCorreo().equals(codigo)) {
				u.setCorreo(u.getCorreocambio());
				u.setCorreocambio("");
				u.setConfirmacion("false");
				_usuarioSys.save(u);
			}
		}
		return "redirect:/login?mensaje=CorreoActualizado";	}

}
