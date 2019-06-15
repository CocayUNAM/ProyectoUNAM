package com.cocay.sicecd.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import com.cocay.sicecd.controller.ejemploServiceImpl;



@Configuration
@ApplicationPath("/rest")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
    	 register(ejemploServiceImpl.class);
    	 
//    	 packages("com.tarea.spring.app.webservices");
    }
}