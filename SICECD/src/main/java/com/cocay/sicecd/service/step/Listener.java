package com.cocay.sicecd.service.step;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

import com.cocay.sicecd.dao.CursoDao;
import com.cocay.sicecd.model.Curso;
import com.cocay.sicecd.model.Usuario_sys;
import com.cocay.sicecd.repo.CursoRep;
 
 
public class Listener extends JobExecutionListenerSupport {
  private static final Logger log = LoggerFactory.getLogger(Listener.class);
 
 // private final CustomerDao customerDao;
  //private final CursoRep cursoRep;
  private final CursoDao cursoDao;
  
//  public Listener(CustomerDao customerDao) {
//    this.customerDao = customerDao;
//  }
  
  public Listener(CursoDao cursoDao) {
	    this.cursoDao = cursoDao;
	  }
 /*
  @Override
  public void afterJob(JobExecution jobExecution) {
    if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
      log.info("Finish Job! Check the results");
 
      List<Customer> customers = customerDao.loadAllCustomers();
 
      for (Customer customer : customers) {
        log.info("Found <" + customer + "> in the database.");
      }
    }
  }*/
  
  @Override
  public void afterJob(JobExecution jobExecution) {
    if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
      log.info("Finish Job! Check the results");
 
      List<Curso> cursos = cursoDao.loadAllCursosBatch();
 
      for (Curso curso : cursos) {
        log.info("Found <" + curso + "> in the database.");
      }
    }
  }
}
