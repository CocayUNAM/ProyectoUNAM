package com.cocay.sicecd.controller;

import java.security.Principal;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WebController {
	
	@RequestMapping(value = { "/login", "/" }, method = RequestMethod.GET)
	public String login(Model model, String error, String logout, HttpServletRequest request, Principal principal) {

		
		if (error != null) {
			model.addAttribute("errorMsg", "Your username or password are invalid.");
			System.out.println(error);
			System.out.println("\nUsuario erroneo-");
		}

		if (logout != null) {
			model.addAttribute("msg", "You have been logged out successfully.");
		}
		
		String mensaje=request.getParameter("mensaje") ;
		if (mensaje!=null) {
			model.addAttribute("mensaje", mensaje);
			
		}else {
			model.addAttribute("mensaje", "nohay");
		}
		
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
	
	@RequestMapping(value = "/start", method = { RequestMethod.GET, RequestMethod.POST })
	public String start() {
		return "start";
	}
}
