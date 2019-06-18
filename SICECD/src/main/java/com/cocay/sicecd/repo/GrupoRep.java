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
	
	@Query("SELECT g FROM Grupo g")
	List<Grupo> findAll();
	
	@Query(value="SELECT * FROM Grupo", nativeQuery = true)
	List<Grupo> loadAllGrupo();
	
	/*
	 * @author Derian Estrada
	 * Consultas por Clave
	 */
	@Query("SELECT g FROM Grupo g WHERE upper(g.clave) LIKE CONCAT('%',:clave,'%')")
	List<Grupo> findByClave(@Param("clave") String clave);
	
	@Query(value = "SELECT * FROM Grupo WHERE clave = :clave AND fecha_inicio = :fecha_ini", nativeQuery = true)
	List<Grupo> findByClaveAndFechaIni(@Param("clave") String clave, @Param("fecha_ini") Date fecha_ini);
	
	@Query(value = "SELECT * FROM Grupo WHERE clave = :clave AND fecha_fin = :fecha_fin", nativeQuery = true)
	List<Grupo> findByClaveAndFechaFin(@Param("clave") String clave, @Param("fecha_fin") Date fecha_fin);
	
	@Query(value = "SELECT * FROM Grupo WHERE clave = :clave AND fecha_inicio = :fecha_ini AND fecha_fin = :fecha_fin", nativeQuery = true)
	List<Grupo> findByClaveAndFecha(@Param("clave") String clave, @Param("fecha_ini") Date fecha_ini, @Param("fecha_fin") Date fecha_fin);
	
	/*
	 * @author Derian Estrada
	 * Consultas Simples por Fecha
	 */
	@Query(value = "SELECT * FROM Grupo WHERE fecha_inicio = :fecha_ini", nativeQuery = true)
	List<Grupo> findByFechaInicio(@Param("fecha_ini") Date fecha_ini);
	
	@Query(value = "SELECT * FROM Grupo WHERE fecha_fin = :fecha_fin", nativeQuery = true)
	List<Grupo> findByFechaFin(@Param("fecha_fin") Date fecha_fin);
	
	@Query(value = "SELECT * FROM Grupo WHERE fecha_inicio = :fecha_ini AND fecha_fin = :fecha_fin", nativeQuery = true)
	List<Grupo> findByFecha(@Param("fecha_ini") Date fecha_ini, @Param("fecha_fin") Date fecha_fin);
	
	/*
	 * @author Derian Estrada
	 * Consultas Avanzadas por Fecha
	 */
	@Query(value = "SELECT * FROM Grupo WHERE fecha_inicio >= :fecha_ini_1 AND fecha_inicio <= :fecha_ini_2", nativeQuery = true)
	List<Grupo> findByFechaInicio(@Param("fecha_ini_1") Date fecha_ini_1, @Param("fecha_ini_2") Date fecha_ini_2);
	
	@Query(value = "SELECT * FROM Grupo WHERE fecha_fin >= :fecha_fin_1 AND fecha_fin <= :fecha_fin_2", nativeQuery = true)
	List<Grupo> findByFechaFin(@Param("fecha_fin_1") Date fecha_fin_1, @Param("fecha_fin_2") Date fecha_fin_2);
	
	@Query(value = "SELECT * FROM Grupo WHERE fecha_inicio >= :fecha_ini_1 AND fecha_inicio <= :fecha_ini_2 AND fecha_fin >= :fecha_fin_1 AND fecha_fin <= :fecha_fin_2", nativeQuery = true)
	List<Grupo> findByFecha(@Param("fecha_ini_1") Date fecha_ini_1, @Param("fecha_ini_2") Date fecha_ini_2,
							@Param("fecha_fin_1") Date fecha_fin_1, @Param("fecha_fin_2") Date fecha_fin_2);
}
