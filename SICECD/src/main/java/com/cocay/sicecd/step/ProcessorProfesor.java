package com.cocay.sicecd.step;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cocay.sicecd.model.Estado;
import com.cocay.sicecd.model.Genero;
import com.cocay.sicecd.model.Grado_profesor;
import com.cocay.sicecd.model.Profesor;
import com.cocay.sicecd.model.Turno;
import com.cocay.sicecd.repo.EstadoRep;
import com.cocay.sicecd.repo.GeneroRep;
import com.cocay.sicecd.repo.Grado_profesorRep;
import com.cocay.sicecd.repo.ProfesorRep;
import com.cocay.sicecd.repo.TurnoRep;

@Service
public class ProcessorProfesor implements ItemProcessor<Profesor, Profesor> {

	private static final Logger log = LoggerFactory.getLogger(ProcessorProfesor.class);
	
	@Autowired
	EstadoRep estadoRep;
	
	@Autowired
	TurnoRep turnoRep;
	
	@Autowired
	GeneroRep generoRep;
	
	@Autowired
	Grado_profesorRep gradoProfesor;
	
	@Autowired
	ProfesorRep profesorRep;
	
	@Autowired
	private EntityManager em;
	
	@Override
    public Profesor process(Profesor profesor) throws Exception {
        String cdEstado = profesor.getTempEstado();
        String cdTurno = profesor.getTempTurno();
        String cdGenero = profesor.getTempGenero();
        String cdGrado = profesor.getTempGradoP();
        
        Estado estado = estadoRep.findByNombreEstado(cdEstado);
    	Turno turno = turnoRep.findByNombreTurno(cdTurno);
    	Genero genero = generoRep.findByNombreGenero(cdGenero);
    	Grado_profesor gradoP = gradoProfesor.findByNombreGrado(cdGrado);

        
        profesor.setFk_id_estado(estado);
        profesor.setFk_id_turno(turno);
        profesor.setFk_id_grado_profesor(gradoP);
        profesor.setGenero(genero);
        profesor.setStTabla(1);
        String rfc = profesor.getRfc();
        Profesor p = profesorRep.findByRfc(rfc);
        if(p == null) {
        	System.out.println(profesor.toString());
            
            System.out.println("Objeto convertido a profesor ");
            
            return profesor;
        }else {
        	String mensaje = "El profesor: "+rfc+" ya existe actualmente";
			String consulta = "INSERT INTO errores (mensaje, estado) VALUES ('"+mensaje+"', 1)";
			Query query = em.createNativeQuery(consulta);
			query.executeUpdate();
        	return null;
        }
        
    }
}
