package com.cocay.sicecd.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cocay.sicecd.model.Curso;
import com.cocay.sicecd.model.Grupo;
import com.cocay.sicecd.model.Profesor;
import com.cocay.sicecd.repo.CursoRep;
import com.cocay.sicecd.repo.GrupoRep;
import com.cocay.sicecd.repo.ProfesorRep;


@Controller
public class BatchController {
	
	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job job;
	
	@Autowired
	CursoRep curso;
	
	@Autowired
	GrupoRep grupo;
	
	@Autowired
	ProfesorRep profesor;
	
	@RequestMapping(value = "/runjob", method = RequestMethod.GET)
	public ModelAndView handle(ModelMap model, HttpServletRequest request) throws Exception {
		Logger logger = LoggerFactory.getLogger(this.getClass());
		try {
			JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
					.toJobParameters();
			jobLauncher.run(job, jobParameters);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return new ModelAndView("/BatchTemplate/consultarProfesor");
	}
	
	/*
	@RequestMapping(value = "/vistaBatch", method = RequestMethod.GET)
	public ModelAndView consultaCustomer(ModelMap model, HttpServletRequest request) {
		
		return new ModelAndView("/BatchTemplate/CargaBatch");
		
	}*/
	
	@RequestMapping(value = "/consultarCursos", method = RequestMethod.GET)
	public ModelAndView consultarCursos(ModelMap model, HttpServletRequest request) {
		
		List<Curso> list_p = curso.loadAllCursos();
		if(!list_p.isEmpty()) {
			model.put("cursos", list_p);
			return new ModelAndView("/BatchTemplate/consultarCursos", model);
			
		} else {
			return new ModelAndView("/BatchTemplate/consultarCursos");
		}
	}
	
	@RequestMapping(value = "/consultarGrupo", method = RequestMethod.GET)
	public ModelAndView consultarGrupo(ModelMap model, HttpServletRequest request) {
		
		List<Grupo> list_p = grupo.loadAllGrupo();
		if(!list_p.isEmpty()) {
			model.put("grupos", list_p);
			return new ModelAndView("/BatchTemplate/consultarGrupo", model);
			
		} else {
			return new ModelAndView("/BatchTemplate/consultarGrupo");
		}
	}
	
	@RequestMapping(value = "/consultarProfesor", method = RequestMethod.GET)
	public ModelAndView consultarProfesor(ModelMap model, HttpServletRequest request) {
		
		List<Profesor> list_p = profesor.loadAllProfesor();
		if(!list_p.isEmpty()) {
			model.put("profesores", list_p);
			return new ModelAndView("/BatchTemplate/consultarProfesor", model);
			
		} else {
			return new ModelAndView("/BatchTemplate/consultarProfesor");
		}
	}
	

}
