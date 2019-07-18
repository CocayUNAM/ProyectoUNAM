package com.cocay.sicecd.step;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.cocay.sicecd.model.Grupo;
import com.cocay.sicecd.model.Inscripcion;
import com.cocay.sicecd.model.Profesor;


public class ProcessorInscripcion implements ItemProcessor<Inscripcion, Inscripcion> {

	private static final Logger log = LoggerFactory.getLogger(ProcessorInscripcion.class);

	Grupo grupo = new Grupo();
	Profesor profesor = new Profesor();
	
	@Override
    public Inscripcion process(Inscripcion inscripcion) throws Exception {
		System.out.println("grupo: "+inscripcion.getTempGrupo());
		System.out.println("profesor: "+inscripcion.getTempProfesor());
        int cdGrupo = inscripcion.getTempGrupo();
        int cdProfesor = inscripcion.getTempProfesor();
       
        grupo.setPk_id_grupo(cdGrupo);
        profesor.setPk_id_profesor(cdProfesor);
        
        inscripcion.setFk_id_grupo(grupo);
        inscripcion.setFk_id_profesor(profesor);
        inscripcion.setStTabla(1);
        
        System.out.println("Objeto convertido a inscripcion ");
        
        return inscripcion;
    }
}
