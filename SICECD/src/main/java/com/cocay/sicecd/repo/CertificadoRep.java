package com.cocay.sicecd.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cocay.sicecd.model.Certificado;
import com.cocay.sicecd.model.Curso;

public interface CertificadoRep extends PagingAndSortingRepository<Certificado, Integer>{
	@Query("SELECT c FROM Certificado c where c.ruta = :ruta")
	Certificado findByRuta(@Param("ruta") String ruta);
}
