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
    @Query(value = "insert into Inscripcion (fk_id_grupo,fk_id_profesor,calificacion,aprobado) VALUES (:fk_id_grupo,:fk_id_profesor,:calificacion,:aprobado)", nativeQuery = true)
    @Transactional
    void saveI(@Param("fk_id_grupo") int fk_id_grupo,@Param("fk_id_profesor") int fk_id_profesor, @Param("calificacion") String calificacion,@Param("aprobado")boolean aprobado);
	
	
	@Query("select  u.fk_id_grupo from Inscripcion u inner join u.fk_id_grupo ar where ar.clave =:clave")
	Inscripcion findIDGroup(@Param("clave")String clave);
}