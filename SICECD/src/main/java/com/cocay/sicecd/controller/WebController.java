package com.cocay.sicecd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cocay.sicecd.repo.TestRepository;

@Controller
public class WebController {

	@Autowired
	TestRepository testRepository;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String login(Model model){
		return "login";
	}
	
	@RequestMapping(value = "/find-test", method = RequestMethod.GET)
	public String findTest(Model model){
		//http://localhost:8080/find-test
        model.addAttribute("testCollection", testRepository.findAll());
		return "test";
	}
	
}
