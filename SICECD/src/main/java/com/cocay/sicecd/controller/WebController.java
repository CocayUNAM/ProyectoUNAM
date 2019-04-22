package com.cocay.sicecd.controller;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;



import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;


//@RestController
@Controller
public class WebController {


	@Autowired
	TestRepository testRepository;

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job job;
	/*
	@RequestMapping("/runjob")
	public String handle() throws Exception {
		Logger logger = LoggerFactory.getLogger(this.getClass());
		try {
			JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
					.toJobParameters();
			jobLauncher.run(job, jobParameters);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return "Done! Check Console Window for more details";
	}*/

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String login(Model model) {
		return "login";
	}

	@RequestMapping(value = "/find-test", method = RequestMethod.GET)
	public String findTest(Model model) {
		// http://localhost:8080/find-test
		model.addAttribute("testCollection", testRepository.findAll());
		return "example/test";
	}

	@RequestMapping(value = "/table-row-select", method = RequestMethod.GET)
	public String exampleTableRowSelect(Model model) {
		return "example/table-row-select";
	}
	
	@RequestMapping(value = "/table-basic", method = RequestMethod.GET)
	public String exampleTableBasic(Model model){
		return "example/table-basic";
	}
	
	@RequestMapping(value = "/table-export", method = RequestMethod.GET)
	public String exampleExport(){
		return "example/table-export";
	}
	
	@RequestMapping(value = "/table-jsgrid", method = RequestMethod.GET)
	public String exampleJsgrid(Model model){
		return "example/table-jsgrid";
	}
	
	@RequestMapping(value = "/form-basic", method = RequestMethod.GET)
	public String exampleFormBasic(Model model){
		return "example/form-basic";
	}
	
	@RequestMapping(value = "/form-validation", method = RequestMethod.GET)
	public String exampleFormValidation(Model model){
		return "example/form-validation";

	@RequestMapping(value = { "/login", "/" }, method = RequestMethod.GET)
	public String login(Model model, String error, String logout) {
		if (error != null) {
			model.addAttribute("errorMsg", "Your username or password are invalid.");
			System.out.println(error);
			System.out.println("\nUsuario erroneo-");
		}

		if (logout != null)
			model.addAttribute("msg", "You have been logged out successfully.");

		return "login";
	}

	@RequestMapping(value = "/logout", method = { RequestMethod.GET, RequestMethod.POST })
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		SecurityContextHolder.clearContext();
		session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		for (Cookie cookie : request.getCookies()) {
			cookie.setMaxAge(0);
		}
		return "login";

	}
	
	//Mapeo del html para registrar cursos
	@RequestMapping(value = "/registrarProfesores", method = RequestMethod.GET)
	public String RegistrarProfesores(Model model){
		return "ProfesoresController/registrarProfesores";
	}

}
