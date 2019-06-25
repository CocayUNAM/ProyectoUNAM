package com.cocay.sicecd.step;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import com.cocay.sicecd.dto.CursoDto;


public class LoggingCursoWriter implements ItemWriter<CursoDto>{
	
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingCursoWriter.class);

	@Override
	public void write(List<? extends CursoDto> items) throws Exception {
		
		LOGGER.info("Información recibida de {} cursos", items.size());

        items.forEach(i -> LOGGER.debug("Información recibida del curso: {}", i));
	}
}
