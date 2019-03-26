package com.cocay.sicecd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@ComponentScan
public class SicecdApplication {
    
	public static void main(String[] args) {
		SpringApplication.run(SicecdApplication.class, args);
	}

}
