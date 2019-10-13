package com.cocay.sicecd.step;

import java.util.Date;
import java.util.LinkedList;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.cocay.sicecd.model.Curso;
import com.cocay.sicecd.model.Grupo;
import com.cocay.sicecd.model.Profesor;
import com.cocay.sicecd.repo.CursoRep;
import com.cocay.sicecd.repo.GrupoRep;
import com.cocay.sicecd.repo.ProfesorRep;

@Service
public class ProcessorGrupo implements ItemProcessor<Grupo, Grupo> {

	private static final Logger log = LoggerFactory.getLogger(ProcessorGrupo.class);
	
	
	@Autowired
	CursoRep cursoRep;
	
	@Autowired
	ProfesorRep profesorRep;
	
	@Autowired
	GrupoRep grupoRep;
	
	@Autowired
	private EntityManager em;
	
	@Override
    public Grupo process(Grupo grupo) throws Exception {
		String cdCurso = grupo.getTempCurso();
		String cdProfesor = grupo.getTempProfesor();
		System.out.println(cdCurso);
		System.out.println(cdProfesor);
		
		/*
		 * Marca error Null
		 */
		Profesor profesor = profesorRep.findByRfc(cdProfesor);
		
		Curso curso = cursoRep.findByUniqueClave(cdCurso);
		
		String clave = grupo.getClave();
		Grupo grp = grupoRep.findByClaveGrupo(clave);
		
		if(grp == null) {
			grupo.setFk_id_curso(curso);
			grupo.setFk_id_profesor(profesor);
			grupo.setStTabla(1);
	        
	        System.out.println("Objeto convertido a grupo ");
	        
	        return grupo;
		}else {
			String mensaje = "Error en la tabla Grupo, campo clave: "+clave+" ya existente";
			String consulta = "INSERT INTO errores (mensaje, estado) VALUES ('"+mensaje+"', 1)";
			Query query = em.createNativeQuery(consulta);
			query.executeUpdate();
			return null;
		}
		
    }
}
