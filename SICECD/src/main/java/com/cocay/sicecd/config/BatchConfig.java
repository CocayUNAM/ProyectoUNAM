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

import com.cocay.sicecd.dao.CursoDao;
import com.cocay.sicecd.model.Curso;
import com.cocay.sicecd.repo.CursoRep;
import com.cocay.sicecd.service.step.Listener;
import com.cocay.sicecd.service.step.Processor;
import com.cocay.sicecd.service.step.Reader;
import com.cocay.sicecd.service.step.Writer;
 
 
@Configuration
@EnableBatchProcessing
public class BatchConfig {
 
  @Autowired
  public JobBuilderFactory jobBuilderFactory;
 
  @Autowired
  public StepBuilderFactory stepBuilderFactory;
 
  @Autowired
  public CursoDao cursoDao;
 
  @Bean
  public Job job() {
    return jobBuilderFactory.get("job").incrementer(new RunIdIncrementer()).listener(new Listener(cursoDao))
        .flow(step2()).end().build();
  }
 /*
  @Bean
  public Step step1() {
    return stepBuilderFactory.get("step1").<Customer, Customer>chunk(2)
        .reader(Reader.reader("customer-data.csv"))
        .processor(new Processor()).writer(new Writer(customerDao)).build();
  }*/
  
  @Bean
  public Step step2() {
    return stepBuilderFactory.get("step2").<Curso, Curso>chunk(2)
        .reader(Reader.reader("curso-data.csv"))
        .processor(new Processor()).writer(new Writer(cursoDao)).build();
  }
}
