package com.cocay.sicecd.step;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cocay.sicecd.model.Curso;
import com.cocay.sicecd.model.Grupo;
import com.cocay.sicecd.model.Inscripcion;
import com.cocay.sicecd.model.Profesor;
import com.cocay.sicecd.repo.CursoRep;
import com.cocay.sicecd.repo.GrupoRep;
import com.cocay.sicecd.repo.ProfesorRep;

@Service
public class ProcessorInscripcion implements ItemProcessor<Inscripcion, Inscripcion> {

	private static final Logger log = LoggerFactory.getLogger(ProcessorInscripcion.class);
	
	@Autowired
	ProfesorRep profesorRep;
	
	@Autowired
	GrupoRep grupoRep;
	
	@Autowired
	CursoRep cursoRep;
	
	@Override
    public Inscripcion process(Inscripcion inscripcion) throws Exception {
		System.out.println("Grupo: "+inscripcion.getTempGrupo());
		System.out.println("Curso: "+inscripcion.getTempCurso());
		System.out.println("Profesor: "+inscripcion.getTempProfesor());
        String cdGrupo = inscripcion.getTempGrupo();
        String cdProfesor = inscripcion.getTempProfesor();
        
        Curso curso = cursoRep.findByUniqueClave(inscripcion.getTempCurso());
        Grupo grupo = grupoRep.findByClaveGrupoIdCurso(cdGrupo, curso);
    	Profesor profesor = profesorRep.findByRfc(cdProfesor);
        
        inscripcion.setFk_id_grupo(grupo);
        inscripcion.setFk_id_profesor(profesor);
        inscripcion.setStTabla(1);
        
        System.out.println("Objeto convertido a inscripcion ");
        
        return inscripcion;
    }
}
