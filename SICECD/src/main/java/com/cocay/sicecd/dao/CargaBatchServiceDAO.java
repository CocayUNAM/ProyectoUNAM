package com.cocay.sicecd.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cocay.sicecd.dto.ProfesorDto;

@SuppressWarnings("deprecation")
@Component
public class CargaBatchServiceDAO {
	
	@Autowired
	private EntityManager em;
	
	@SuppressWarnings({ "unchecked" })
	public List<ProfesorDto> lstProfesores(Integer limit, Integer offset, String search, String name, String order) throws Exception {
		List<ProfesorDto> lstProfesores = new ArrayList<ProfesorDto>();
		
		String consulta = "SELECT idProfesor, nombres, aPaterno, aMaterno, rfc, curp, correo FROM (SELECT pk_id_profesor idProfesor, \n" + 
				"		nombre nombres, \n" + 
				"		apellido_paterno aPaterno, \n" + 
				"		apellido_materno aMaterno,\n" + 
				"		rfc,\n" + 
				"		curp,\n" + 
				"		correo\n" + 
				"FROM Profesor) foo";
		
		
		if(search != null && !search.equals("")) {
			
		}
		
		consulta += " ORDER BY"+ (name != null && !"".equals(name) ? " " + name + " " : " idProfesor")
				+ (order != null && !order.equals("") ? " " + order : " DESC");
		
		Query query = em.createNativeQuery(consulta);
		query.unwrap(SQLQuery.class)
		.addScalar("idProfesor", IntegerType.INSTANCE)
		.addScalar("nombres", StringType.INSTANCE)
		.addScalar("aPaterno", StringType.INSTANCE)
		.addScalar("aMaterno", StringType.INSTANCE)
		.addScalar("rfc", StringType.INSTANCE)
		.addScalar("curp", StringType.INSTANCE)
		.addScalar("correo", StringType.INSTANCE)
		.setResultTransformer(Transformers.aliasToBean(ProfesorDto.class));
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		lstProfesores = (List<ProfesorDto>) query.getResultList();
		
		
		return lstProfesores;
	}
	
	public Integer totalLstProfesores(String search) throws Exception {
		Query query = em.createNativeQuery("SELECT count(1) FROM Profesor");
		if(search != null && !search.equals("")) {
			
		}
		int total = ((Number) query.getSingleResult()).intValue();
		return total;
	}

}
