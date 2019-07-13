package com.cocay.sicecd.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cocay.sicecd.model.Grupo;
import com.cocay.sicecd.model.Inscripcion;

@Repository
public interface InscripcionRep extends PagingAndSortingRepository<Inscripcion, Integer>{
	
	@Query(value = "SELECT * FROM inscripcion", nativeQuery = true)
	List<Inscripcion> findAll();	
}