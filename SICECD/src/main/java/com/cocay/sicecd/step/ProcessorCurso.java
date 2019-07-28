package com.cocay.sicecd.step;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.cocay.sicecd.model.Curso;
import com.cocay.sicecd.model.Tipo_curso;
import com.cocay.sicecd.repo.Tipo_cursoRep;


public class ProcessorCurso implements ItemProcessor<Curso, Curso> {

	private static final Logger log = LoggerFactory.getLogger(ProcessorCurso.class);
	
	Tipo_curso tp_curso = new Tipo_curso();	
	
	@Override
    public Curso process(Curso curso) throws Exception {
		int c = curso.getTemp();
		Tipo_curso tpC = new Tipo_curso();
		tpC.setPk_id_tipo_curso(c);
		curso.setFk_id_tipo_curso(tpC);
		curso.setStTabla(1);
        
        System.out.println("Objeto convertido a curso ");
        
        return curso;
    }
}
