package com.cocay.sicecd.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cocay.sicecd.dto.CursoDto;
import com.cocay.sicecd.model.Curso;
import com.cocay.sicecd.model.Tipo_curso;
import com.cocay.sicecd.repo.CursoRep;
import com.cocay.sicecd.repo.Tipo_cursoRep;

@Controller
@RequestMapping("AdministracionCursos")
public class CursosController {
	
	@Autowired
	CursoRep cursoRep;
	
	@Autowired
	private Tipo_cursoRep tpRep;

	// Mapeo del html para registrar cursos
	@RequestMapping(value = "/registrarCursos2")
	public String registrarCur(Model model, HttpServletRequest request) {
		return "CursosController/registrarCursos";
	}
		
	
	@RequestMapping(value = "/registrarCursos", method = RequestMethod.POST)
	public ResponseEntity<String> agregarCurso(@RequestBody CursoDto cr) {
		Curso curso = new Curso();
		
		String clave = cr.getClave();
		
		String tipo = cr.getTipo();

		String horas = cr.getHoras();
		
		String nombre = cr.getNombre();
		
		curso.setClave(clave);
		
		List<Tipo_curso> cursos = tpRep.findByNombre(tipo);
		if(!cursos.isEmpty()) {
			curso.setFk_id_tipo_curso(cursos.get(0));
		}
		
		curso.setHoras(Integer.valueOf(horas));
		curso.setNombre(nombre);
		cursoRep.save(curso);
		
		return ResponseEntity.ok("Curso agregado con exito");
	}

}
