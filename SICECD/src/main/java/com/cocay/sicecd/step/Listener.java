package com.cocay.sicecd.step;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

import com.cocay.sicecd.dao.CustomerDao;
import com.cocay.sicecd.model.Customer;
 
 
public class Listener extends JobExecutionListenerSupport {
  private static final Logger log = LoggerFactory.getLogger(Listener.class);
 
  private final CustomerDao customerDao;
 
  public Listener(CustomerDao customerDao) {
    this.customerDao = customerDao;
  }
 
  @Override
  public void afterJob(JobExecution jobExecution) {
    if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
      log.info("Finish Job! Check the results");
 
      List<Customer> customers = customerDao.loadAllCustomers();
 
      for (Customer customer : customers) {
        log.info("Found <" + customer + "> in the database.");
      }
    }
  }
}
