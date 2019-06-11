package com.cocay.sicecd.service.step;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.cocay.sicecd.model.Curso;
import com.cocay.sicecd.model.Tipo_curso;

 
public class Processor implements ItemProcessor<Curso, Curso> {
	 
	  private static final Logger log = LoggerFactory.getLogger(Processor.class);
	 
//	  @Override
//	  public Customer process(Customer customer) throws Exception {
//	    Random r = new Random();
//	    
//	    final String firstName = customer.getFirstName().toUpperCase();
//	    final String lastName = customer.getLastName().toUpperCase();
//	 
//	    final Customer fixedCustomer = new Customer(r.nextLong(), firstName, lastName);
//	 
//	    log.info("Converting (" + customer + ") into (" + fixedCustomer + ")");
//	 
//	    return fixedCustomer;
//	  }
	  
	  @Override
	  public Curso process(Curso curso) throws Exception {
	    Random r = new Random();
	    final String clave = curso.getClave();
	    final String nombre = curso.getNombre();
	    final int horas = curso.getHoras();
	 
	    final Curso fixedCurso = new Curso(clave, nombre, horas);
	 
	    log.info("Converting (" + curso + ") into (" + fixedCurso + ")");
	 
	    return fixedCurso;
	  }
}