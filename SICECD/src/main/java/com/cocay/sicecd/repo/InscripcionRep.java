package com.cocay.sicecd.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cocay.sicecd.model.Grupo;
import com.cocay.sicecd.model.Inscripcion;

@Repository
public interface InscripcionRep extends PagingAndSortingRepository<Inscripcion, Integer>{
	
	@Query(value = "SELECT * FROM inscripcion", nativeQuery = true)
	List<Inscripcion> findAll();	
	
	/*
	 * @author Héctor Santaella Marín
	 * 
	 */
	@Modifying
    @Query(value = "insert into Inscripcion (fk_id_profesor,calif) VALUES (:fk_id_profesor,:calif)", nativeQuery = true)
    @Transactional
    void saveI(@Param("fk_id_profesor") int fk_id_curso, @Param("calif") String calif);
}