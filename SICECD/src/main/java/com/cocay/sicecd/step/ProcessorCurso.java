package com.cocay.sicecd.step;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cocay.sicecd.model.Curso;
import com.cocay.sicecd.model.Grupo;
import com.cocay.sicecd.model.Tipo_curso;
import com.cocay.sicecd.repo.CursoRep;
import com.cocay.sicecd.repo.GrupoRep;
import com.cocay.sicecd.repo.Tipo_cursoRep;

@Service
public class ProcessorCurso implements ItemProcessor<Curso, Curso> {

	private static final Logger log = LoggerFactory.getLogger(ProcessorCurso.class);
	
	Tipo_curso tp_curso = new Tipo_curso();	
	
	@Autowired
	CursoRep cursoRep;
	
	@Autowired
	Tipo_cursoRep tipoCursoRep;
	
	@Autowired
	GrupoRep grupoRep;
	
	@Override
    public Curso process(Curso curso) throws Exception {
		String name = curso.getTempTipoCurso();
		String grupoNombre = curso.getTempGrupo();
		Tipo_curso tpC = tipoCursoRep.findByNombreTipo(name);
		Grupo grupo = grupoRep.findByClaveGrupo(grupoNombre);
		curso.setFk_id_tipo_curso(tpC);
		curso.setFk_id_grupo(grupo);
		curso.setStTabla(1);
        
        System.out.println("Objeto convertido a curso ");
        
        return curso;
    }
}
