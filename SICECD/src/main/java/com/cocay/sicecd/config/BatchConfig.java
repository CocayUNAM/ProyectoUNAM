package com.cocay.sicecd.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cocay.sicecd.dao.CustomerDao;
import com.cocay.sicecd.model.Customer;
import com.cocay.sicecd.step.Listener;
import com.cocay.sicecd.step.Processor;
import com.cocay.sicecd.step.Reader;
import com.cocay.sicecd.step.Writer;
 
 
@Configuration
@EnableBatchProcessing
public class BatchConfig {
 
  @Autowired
  public JobBuilderFactory jobBuilderFactory;
 
  @Autowired
  public StepBuilderFactory stepBuilderFactory;
 
  @Autowired
  public CustomerDao customerDao;
 
  @Bean
  public Job job() {
    return jobBuilderFactory.get("job").incrementer(new RunIdIncrementer()).listener(new Listener(customerDao))
        .flow(step1()).end().build();
  }
 
  @Bean
  public Step step1() {
    return stepBuilderFactory.get("step1").<Customer, Customer>chunk(2)
        .reader(Reader.reader("customer-data.csv"))
        .processor(new Processor()).writer(new Writer(customerDao)).build();
  }
}
