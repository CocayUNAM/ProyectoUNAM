package com.cocay.sicecd.step;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.cocay.sicecd.model.Estado;
import com.cocay.sicecd.model.Genero;
import com.cocay.sicecd.model.Grado_profesor;
import com.cocay.sicecd.model.Profesor;
import com.cocay.sicecd.model.Turno;

public class ProcessorProfesor implements ItemProcessor<Profesor, Profesor> {

	private static final Logger log = LoggerFactory.getLogger(ProcessorProfesor.class);
	
	Estado estado = new Estado();
	Turno turno = new Turno();
	Genero genero = new Genero();
	Grado_profesor gradoP = new Grado_profesor();
	
	@Override
    public Profesor process(Profesor profesor) throws Exception {
        int cdEstado = profesor.getTempEstado();
        int cdTurno = profesor.getTempTurno();
        int cdGenero = profesor.getTempGenero();
        int cdGrado = profesor.getTempGradoP();
        
        estado.setPk_id_estado(cdEstado);
        turno.setPk_id_turno(cdTurno);
        genero.setPk_id_genero(cdGenero);
        gradoP.setPk_id_grado_profesor(cdGrado);
        
        profesor.setFk_id_estado(estado);
        profesor.setFk_id_turno(turno);
        profesor.setFk_id_grado_profesor(gradoP);
        profesor.setGenero(genero);
        profesor.setStTabla(1);
        
        System.out.println(profesor.toString());
        
        System.out.println("Objeto convertido a profesor ");
        
        return profesor;
    }
}
