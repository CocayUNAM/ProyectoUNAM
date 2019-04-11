package com.cocay.sicecd.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cocay.sicecd.model.Profesor;
import com.cocay.sicecd.repo.ProfesorRep;
import com.cocay.sicecd.repo.TestRepository;

@Controller
public class WebController {

	@Autowired
	TestRepository testRepository;
	@Autowired
	ProfesorRep test;
	
	/*
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loggea(HttpServletRequest request, Principal principal) {
        if (request.isUserInRole("ROLE_USUARIO")) {
            return "example/user/inicio";
        }
        if (request.isUserInRole("ROLE_ADMIN")) {
            return "example/admin/inicio";
        }
        return "example/error";
    }*/
	
	@RequestMapping(value= {"/login","/"}, method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("errorMsg", "Your username and password are invalid.");
        	System.out.println("\nusuairio erroneo----");
        	System.out.println(error);
        	System.out.println("\nusuairio erroneo-hjk-");

        }

        if (logout != null)
            model.addAttribute("msg", "You have been logged out successfully.");

        return "login";
    }

	
	@RequestMapping(value = "/logout", method = { RequestMethod.GET, RequestMethod.POST })
	public String logout(HttpServletRequest request, HttpServletResponse response){
		HttpSession session= request.getSession(false);
	    SecurityContextHolder.clearContext();
	         session= request.getSession(false);
	        if(session != null) {
	            session.invalidate();
	        }
	        for(Cookie cookie : request.getCookies()) {
	            cookie.setMaxAge(0);
	        }
		return "login";
	}
    
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
	public String exampleTableBasic(Model model){
		return "example/table-basic";
	}
	
	@RequestMapping(value = "/table-export", method = RequestMethod.GET)
	public String exampleExport(){
		return "example/table-export";
	}
	
	@RequestMapping(value = "/consultarProfesor", method = RequestMethod.GET)
	public String consultaProfesor(Model model) {
		return "example/consultaProfesor";
	}

	@RequestMapping(value = "/consultarProfesorOK", method = RequestMethod.POST)
	public String consultarProfesor(Model model, HttpServletRequest request) {
		String rfcs = request.getParameter("rfc");
		List<Profesor> rfc = test.findByRfc(rfcs);

		if (rfc.isEmpty()) {
			return "example/consultaProfesor";

		} else {

			model.addAttribute("test", test.findAll());
			return "/example/muestraProfesor";
		}

		/*
		 * if(rfcs.equals(rfc)) { model.addAttribute("test", test.findAll()); return
		 * "/example/muestraProfesor";
		 * 
		 * 
		 * }else { return "example/consultaProfesor";
		 * 
		 * }
		 */
	}
	
	@RequestMapping(value = "/consultarProfesornoMBRE", method = RequestMethod.POST)
	public String consultarProfesornoNombre(Model model, HttpServletRequest request) {
		String nombre = request.getParameter("nombre");
		String apellido_paterno = request.getParameter("apellido_paterno");
		String apellido_materno = request.getParameter("apellido_materno");
		
		return "example/consultaProfesor";
		/*
		 * if(rfcs.equals(rfc)) { model.addAttribute("test", test.findAll()); return
		 * "/example/muestraProfesor";
		 * 
		 * 
		 * }else { return "example/consultaProfesor";
		 * 
		 * }
		 */
	}
	@RequestMapping(value = "/consultaCurso", method = RequestMethod.GET)
	public String consultaCurso(Model model){
		return "example/consultarCurso";
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
