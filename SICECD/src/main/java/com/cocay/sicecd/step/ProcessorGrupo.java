package com.cocay.sicecd.step;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.cocay.sicecd.model.Curso;
import com.cocay.sicecd.model.Grupo;
import com.cocay.sicecd.model.Profesor;

public class ProcessorGrupo implements ItemProcessor<Grupo, Grupo> {

	private static final Logger log = LoggerFactory.getLogger(ProcessorGrupo.class);
	
	Curso curso = new Curso();
	
	Profesor profesor = new Profesor();
	
	@Override
    public Grupo process(Grupo grupo) throws Exception {
		int cdCurso = grupo.getTempCurso();
		int cdProfesor = grupo.getTempProfesor();
		curso.setPk_id_curso(cdCurso);
		profesor.setPk_id_profesor(cdProfesor);
		grupo.setFk_id_curso(curso);
		grupo.setFk_id_profesor(profesor);
		grupo.setStTabla(1);
        
        System.out.println("Objeto convertido a grupo ");
        
        return grupo;
    }
}
