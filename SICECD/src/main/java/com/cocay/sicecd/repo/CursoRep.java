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
import com.cocay.sicecd.model.Profesor;

@Repository
public interface CursoRep extends PagingAndSortingRepository<Curso, Integer>{
	
	@Query(value="SELECT * FROM Curso", nativeQuery = true)
	List<Curso> loadAllCursos();
	
	@Query("SELECT c FROM Curso c where c.nombre = :nombre")
	Curso findByNombre(@Param("nombre") String nombre);
	
	@Query("SELECT c FROM Curso c WHERE upper(c.clave) LIKE CONCAT('%',:clave,'%')")
	List<Curso> findByClave(@Param("clave") String clave);
	
	/*
	 * @author Derian Estrada
	 */
	@Query("SELECT c FROM Curso c")
	List<Curso> findAll();
	
	@Query(value="SELECT * FROM Curso "
			+ "WHERE UPPER(nombre) LIKE CONCAT('%',:nombre,'%') "
			+ "AND UPPER(clave) LIKE CONCAT('%',:clave,'%')", nativeQuery=true)
	List<Curso> findByParams(@Param("nombre") String nombre, @Param("clave") String clave);
	
	@Query(value="SELECT * FROM Curso "
			+ "WHERE UPPER(nombre) LIKE CONCAT('%',:nombre,'%') "
			+ "AND UPPER(clave) LIKE CONCAT('%',:clave,'%') "
			+ "AND fk_id_tipo_curso=:tipo", nativeQuery=true)
	List<Curso> findByParams(@Param("nombre") String nombre, @Param("clave") String clave, @Param("tipo") Integer tipo);
	
	/*
	 * @author Héctor Santaella Marín
	 * 
	 */
	@Modifying
    @Query(value = "insert into Curso (clave, nombre) VALUES (:clave, :nombre)", nativeQuery = true)
    @Transactional
    void saveC(@Param("clave") String clave, @Param("nombre") String nombre);
	
	@Query("SELECT c FROM Curso c WHERE upper(c.clave) = :clave")
	Curso findByUniqueClave(@Param("clave") String clave);
	
	
	@Query("SELECT c FROM Curso c WHERE upper(c.clave) = :clave")
	Curso findByUniqueClaveCurso(@Param("clave") String clave);
}