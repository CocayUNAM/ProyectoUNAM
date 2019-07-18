package com.cocay.sicecd.config;

import javax.ws.rs.ApplicationPath;

//import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import com.cocay.sicecd.webService.CargaBatchServiceImpl;
import com.cocay.sicecd.webService.CorreoServiceImpl;



@Configuration
@ApplicationPath("/rest")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
    	//register(ejemploServiceImpl.class);
    	 register(CargaBatchServiceImpl.class);
    	 register(CorreoServiceImpl.class);
    	 //register(MultiPartFeature.class);
    }
}