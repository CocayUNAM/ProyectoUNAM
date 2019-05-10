package com.cocay.sicecd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;
@SpringBootApplication //same as @Configuration @EnableAutoConfiguration @ComponentScan
@EnableWebMvc
@EnableScheduling
@ComponentScan
@EntityScan(basePackages= {
		"com.cocay.sicecd.model"
})
public class SicecdApplication implements WebMvcConfigurer  {
    
	@Autowired
	ApplicationContext applicationContext;
	
	@Bean
	public SpringResourceTemplateResolver templateResolver() {
	    SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
	    templateResolver.setApplicationContext(this.applicationContext);
	    templateResolver.setPrefix("classpath:/templates/");
	    templateResolver.setSuffix(".html");
	    templateResolver.setTemplateMode(TemplateMode.HTML);
	    //We need change to true when we put the system into production
	    templateResolver.setCacheable(false);
	    return templateResolver;
	}
	
	
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
        		"favicon.ico",
                "/css/**",
                "/fonts/**",
                "/img/**",
                "/js/**")
                .addResourceLocations(
                		"classpath:/static/favicon.ico",
                        "classpath:/static/css/",
                        "classpath:/static/fonts/",
                        "classpath:/static/img/",
                        "classpath:/static/js/");
    }
	
	public static void main(String[] args) {
		SpringApplication.run(SicecdApplication.class, args);
	}

}