package com.cocay.sicecd.webService;

import org.springframework.beans.factory.annotation.Autowired;

import com.cocay.sicecd.dao.CargaBatchServiceDAO;
import com.cocay.sicecd.dto.ResponseGeneric;
import com.cocay.sicecd.dto.ResponseGenericPagination;

public class CargaBatchServiceImpl implements CargaBatchService{

	@Autowired
	private CargaBatchServiceDAO cargaBatchServiceDAO;

	@Override
	public ResponseGeneric lstProfesores(Integer limit, Integer offset, String search, String name, String order) {
		ResponseGeneric response = new ResponseGeneric();
		ResponseGenericPagination pagination = new ResponseGenericPagination();
		try{
			pagination.setRows(cargaBatchServiceDAO.lstProfesores(limit, offset, search, name, order));
			pagination.setTotal(cargaBatchServiceDAO.totalLstProfesores(search));
			response.setResponse(pagination);
			response.setCdMensaje("1");
			response.setNbMensaje("OK");
		} catch (Exception e) {
			response.setCdMensaje("0");
			response.setNbMensaje("ERROR");
		}
		return response;
	}
	
	

}
