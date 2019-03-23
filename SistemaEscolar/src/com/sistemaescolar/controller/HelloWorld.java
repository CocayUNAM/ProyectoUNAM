package com.sistemaescolar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/*
 * author: Juan
 */

@Controller
public class HelloWorld {
	
	@RequestMapping("/welcome")
	public ModelAndView helloWorld() {
		String mensaje = "Hello World";
		
		return new ModelAndView("welcome","mensaje", mensaje);
	}

}
