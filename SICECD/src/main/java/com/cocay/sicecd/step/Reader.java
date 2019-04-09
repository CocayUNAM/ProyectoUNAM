package com.cocay.sicecd.step;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;

import com.cocay.sicecd.model.Customer;

 
public class Reader {
  public static FlatFileItemReader<Customer> reader(String path) {
    FlatFileItemReader<Customer> reader = new FlatFileItemReader<Customer>();
 
    reader.setResource(new ClassPathResource(path));
    reader.setLineMapper(new DefaultLineMapper<Customer>() {
      {
        setLineTokenizer(new DelimitedLineTokenizer() {
          {
            setNames(new String[] { "id", "firstName", "lastName" });
          }
        });
        setFieldSetMapper(new BeanWrapperFieldSetMapper<Customer>() {
          {
            setTargetType(Customer.class);
          }
        });
      }
    });
    return reader;
  }
}
