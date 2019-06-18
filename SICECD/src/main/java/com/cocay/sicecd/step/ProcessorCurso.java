package com.cocay.sicecd.step;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.cocay.sicecd.model.Curso;


public class ProcessorCurso implements ItemProcessor<Curso, Curso> {

	private static final Logger log = LoggerFactory.getLogger(ProcessorCurso.class);

	@Override
    public Curso process(Curso curso) throws Exception {
        String clave = curso.getClave();
        String name = curso.getNombre();
        //Tipo_curso tp_curso = curso.getFk_id_tipo_curso();
        int horas = curso.getHoras();
        
        System.out.println("Objeto convertido a curso ");
        
        return curso;
    }
}
