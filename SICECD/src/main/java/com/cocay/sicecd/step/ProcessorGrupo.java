package com.cocay.sicecd.step;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.cocay.sicecd.model.Grupo;

public class ProcessorGrupo implements ItemProcessor<Grupo, Grupo> {

	private static final Logger log = LoggerFactory.getLogger(ProcessorGrupo.class);

	@Override
    public Grupo process(Grupo grupo) throws Exception {
        String clave = grupo.getClave();
//        Date fecha_in = grupo.getFecha_inicio();
//        Date fecha_fin = grupo.getFecha_fin();
        
        System.out.println("Objeto convertido a grupo ");
        
        return grupo;
    }
}
