package com.cocay.sicecd.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cocay.sicecd.model.Usuario_sys;
import com.cocay.sicecd.repo.TestRepository;
import com.cocay.sicecd.repo.Usuario_sysRep;

@Controller
@RequestMapping("example")
public class ExampleContoller {

	@Autowired
	TestRepository testRepository;
	@Autowired
	Usuario_sysRep _usuarioSys;
	
	@RequestMapping(value = "/find-test", method = RequestMethod.GET)
	public String findTest(Model model){
		//http://localhost:8080/find-test
        model.addAttribute("testCollection", testRepository.findAll());
		return "example/test";
	}
	
	@RequestMapping(value = "/table-row-select", method = RequestMethod.GET)
	public String exampleTableRowSelect(Model model){
		return "example/table-row-select";
	}
	
	@RequestMapping(value = "/table-basic", method = RequestMethod.GET)
	public String exampleTableBasic(Model model, Principal principal){
		Usuario_sys user= _usuarioSys.findByRfc(principal.getName()).get(0);
		if (user.getConfirmarecupera().equals("true")) {
			user.setConfirmarecupera("false");
			_usuarioSys.save(user);
		}
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
	}
	
	@RequestMapping(value = "/blank", method = RequestMethod.GET)
	public String exampleBlank(Model model){
		return "example/blank";
	}

}
