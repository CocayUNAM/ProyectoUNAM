package com.cocay.sicecd.step;

import java.util.Date;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cocay.sicecd.model.Curso;
import com.cocay.sicecd.model.Grupo;
import com.cocay.sicecd.model.Profesor;
import com.cocay.sicecd.repo.CursoRep;
import com.cocay.sicecd.repo.ProfesorRep;

public class ProcessorGrupo implements ItemProcessor<Grupo, Grupo> {

	private static final Logger log = LoggerFactory.getLogger(ProcessorGrupo.class);
	
	//Curso curso = new Curso();
	
	//Profesor profesor = new Profesor();
	
	@Autowired
	CursoRep cursoRep;
	
	@Autowired
	ProfesorRep profesorRep;
	
	@Override
    public Grupo process(Grupo grupo) throws Exception {
		String cdCurso = grupo.getTempCurso();
		String cdProfesor = grupo.getTempProfesor();
		System.out.println(cdCurso);
		System.out.println(cdProfesor);
		Curso curso = cursoRep.findByUniqueClave(cdCurso);
		Profesor profesor = profesorRep.findByRfc(cdProfesor);
//		curso.setPk_id_curso(cdCurso);
//		profesor.setPk_id_profesor(cdProfesor);
		grupo.setFk_id_curso(curso);
		grupo.setFk_id_profesor(profesor);
		grupo.setStTabla(1);
        
        System.out.println("Objeto convertido a grupo ");
        
        return grupo;
    }
}
