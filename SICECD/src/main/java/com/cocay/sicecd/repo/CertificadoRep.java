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
	
	@Query(value = "SELECT * FROM Certificado WHERE fk_id_profesor = :id_profesor AND fk_id_curso = :id_curso AND fk_id_grupo = :id_grupo", nativeQuery = true)
	Certificado findCertificado(@Param("id_profesor") Integer id_profesor, @Param("id_curso") Integer id_curso, @Param("id_grupo") Integer id_grupo);
}
