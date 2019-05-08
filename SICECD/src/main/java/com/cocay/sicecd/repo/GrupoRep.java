package com.cocay.sicecd.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cocay.sicecd.model.Curso;
import com.cocay.sicecd.model.Grupo;

@Repository
public interface GrupoRep extends PagingAndSortingRepository<Grupo, Integer>{
	//Grupo findByClave(String name);
	
	@Query("SELECT g FROM Grupo g")
	List<Grupo> findAll();
	
	@Query("SELECT g FROM Grupo g where g.clave = :clave")
	List<Grupo> findByClave(@Param("clave") String clave);
	
	@Query(value = "SELECT * FROM Grupo WHERE fecha_inicio <= :fecha_ini", nativeQuery = true)
	List<Grupo> findByFechaInicio(@Param("fecha_ini") Date fecha_ini);
	
	@Query(value = "SELECT * FROM Grupo WHERE fecha_fin = :fecha_fin", nativeQuery = true)
	List<Grupo> findByFechaFin(@Param("fecha_fin") Date fecha_fin);
	
	@Query(value = "SELECT * FROM Grupo WHERE fecha_inicio <= :fecha_ini AND fecha_fin = :fecha_fin", nativeQuery = true)
	List<Grupo> findByFecha(@Param("fecha_ini") Date fecha_ini, @Param("fecha_fin") Date fecha_fin);
	
	@Query(value="SELECT * FROM Grupo", nativeQuery = true)
	List<Grupo> loadAllGrupo();
}
