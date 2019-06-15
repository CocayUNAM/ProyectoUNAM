package com.cocay.sicecd.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cocay.sicecd.dto.ResponseGeneric;

@RestController
@RequestMapping("/prueba")
public class ejemploServiceImpl {

	@GetMapping(
			value = "/dametucosita", 
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseGeneric lstDameTuCosita() {
		
		
		ResponseGeneric response = new ResponseGeneric();
		
		
		Map<String, String> lstDameTuCosita = new HashMap<String, String>();
		lstDameTuCosita.put("dame", "ah ah");
		lstDameTuCosita.put("tu", "ah ah");
		lstDameTuCosita.put("cosita", "ah ah");
		

		response.setCdMensaje("A huevo");
		response.setNbMensaje("Correct");
		response.setResponse(lstDameTuCosita);
		
		return response;
	}
	
	@PostMapping(
			value = "/leerExcel", 
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseGeneric leerExcel(@RequestParam("file") MultipartFile file ) throws Exception{
		ResponseGeneric response = new ResponseGeneric();
		try{
			
//			if (file == null) {
//				throw new RuntimeException("You must select the a file for uploading");
//			} else {
//				System.out.println("PROBANDO LEER ARCHIVO ");
//				Workbook hssfWorkBook = WorkbookFactory.create(file.getInputStream());
//			}
			
			response.setCdMensaje("1");
			response.setNbMensaje("leyo el pinche excel");
		} catch (Exception e) {
			response.setCdMensaje("0");
			response.setNbMensaje(e.getMessage());
		}
		
		
		
		
		return response;
	}
	
}
