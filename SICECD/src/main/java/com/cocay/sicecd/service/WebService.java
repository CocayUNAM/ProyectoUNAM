package com.cocay.sicecd.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WebService {
	
	@RequestMapping(value = "/webservice", method = RequestMethod.GET)
	public String call_me() {
		return "consultas/consultaWebService";
	}
	

}
