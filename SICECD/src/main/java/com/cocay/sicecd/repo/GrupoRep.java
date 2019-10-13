package com.cocay.sicecd.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cocay.sicecd.model.Curso;
import com.cocay.sicecd.model.Grupo;
import com.cocay.sicecd.model.Profesor;

@Repository
public interface GrupoRep extends PagingAndSortingRepository<Grupo, Integer>{
	
	@Query("SELECT g FROM Grupo g")
	List<Grupo> findAll();
	
	@Query("SELECT pk_id_grupo FROM Grupo")
	List<Grupo> findId();
	
	@Query(value="SELECT * FROM Grupo", nativeQuery = true)
	List<Grupo> loadAllGrupo();
	
	/*
	 * @author Derian Estrada
	 * Consultas por Asesor
	 */
	@Query(value = "SELECT * FROM Grupo WHERE fk_id_profesor = :id_asesor", nativeQuery = true)
	List<Grupo> findByIdAsesor(@Param("id_asesor")Integer id_asesor);
	
	/*
	 * @author Derian Estrada
	 * Consultas por Clave
	 */
	@Query("SELECT g FROM Grupo g WHERE upper(g.clave) LIKE CONCAT('%',:clave,'%')")
	List<Grupo> findByClave(@Param("clave") String clave);
	
	/*
	 * @author Derian Estrada
	 * Consultas Simples por Fecha
	 */
	@Query(value = "SELECT * FROM Grupo WHERE fecha_inicio >= :fecha_ini AND fecha_fin <= :fecha_fin", nativeQuery = true)
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
	
	/*
	 * @author Héctor Santaella Marín
	 * 
	 */
	@Modifying
    @Query(value = "insert into Grupo (clave, fk_id_curso) VALUES (:clave, :fk_id_curso)", nativeQuery = true)
    @Transactional
    void saveC(@Param("clave") String clave, @Param("fk_id_curso") Integer fk_id_curso);
	
	@Query("SELECT g FROM Grupo g WHERE upper(g.clave) = :clave AND g.fk_id_curso = :curso")
	Grupo findByClaveGrupoIdCurso(@Param("clave") String clave, @Param("curso") Curso curso);
	
	/*
	 * Grupo Batch
	 */
	@Query("SELECT g FROM Grupo g WHERE upper(g.clave) = :clave")
	Grupo findByClaveGrupo(@Param("clave") String clave);
}

