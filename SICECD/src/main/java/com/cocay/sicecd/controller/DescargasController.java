package com.cocay.sicecd.controller;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cocay.sicecd.model.Inscripcion;
import com.cocay.sicecd.model.Profesor;
import com.cocay.sicecd.repo.InscripcionRep;
import com.cocay.sicecd.repo.ProfesorRep;

@Controller
public class DescargasController {

	
	
	@Autowired
	InscripcionRep _incripcion;
	@Autowired
	ProfesorRep _profesor;
	@Value("${path_constancia}")
	String path;
	
	@RequestMapping("descargas")
	@ResponseBody
	public void show(@RequestParam(name = "id") int id,
					 @RequestParam (name = "tipo") String tipo,
					 HttpServletResponse response) {
		
		System.out.println("[ENTRA]----"+ path);
	     
		
		String file= "";
		Profesor profesor;
		
		switch (tipo) {
		case "comprobante":
			  profesor=_profesor.findById(id).get(); 
			  file= profesor.getComprobante_doc();  
		break;
		case "rfc":
			  profesor=_profesor.findById(id).get(); 
			  file= profesor.getRfc_doc(); 
		break;
		case "curp":
			  profesor=_profesor.findById(id).get(); 
			  file= profesor.getCurp_doc(); 
		
		break;
		case "certificado":
			  profesor=_profesor.findById(id).get(); 
			  file= profesor.getCertificado_doc(); 
		break;

		}
		
		
		 


	      response.setContentType("application/pdf");
	      response.setHeader("Content-Disposition", "attachment; filename=" +file);
	      response.setHeader("Content-Transfer-Encoding", "binary");
	      

	      
	      try {
	    	  BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
	    	  
	    	  FileInputStream fis = new FileInputStream(path+file);
	    	  int len;
	    	  byte[] buf = new byte[1024];
	    	  while((len = fis.read(buf)) > 0) {
	    		  bos.write(buf,0,len);
	    	  }
	    	  bos.close();
	    	  response.flushBuffer();
	      }
	      catch(IOException e) {
	    	  e.printStackTrace();
	    	  
	      }
	      
}
}
