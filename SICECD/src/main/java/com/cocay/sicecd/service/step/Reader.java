package com.cocay.sicecd.service.step;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;

import com.cocay.sicecd.model.Curso;

 
public class Reader {

	
	public static FlatFileItemReader<Curso> reader(String path) {
	    FlatFileItemReader<Curso> reader = new FlatFileItemReader<Curso>();
	 
	    reader.setResource(new ClassPathResource(path));
	    reader.setLineMapper(new DefaultLineMapper<Curso>() {
	      {
	        setLineTokenizer(new DelimitedLineTokenizer() {
	          {
	            setNames(new String[] { "clave", "nombre", "horas" });
	          }
	        });
	        setFieldSetMapper(new BeanWrapperFieldSetMapper<Curso>() {
	          {
	            setTargetType(Curso.class);
	          }
	        });
	      }
	    });
	    return reader;
	  }
}
