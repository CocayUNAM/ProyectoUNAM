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
	
	@Query("SELECT c FROM Curso c")
	List<Curso> findAll();
	
	@Query("SELECT c FROM Curso c "
			+ "WHERE c.nombre LIKE CONCAT('%',:nombre,'%') "
			+ "AND c.clave LIKE CONCAT('%',:clave,'%')")
	List<Curso> findByParams(@Param("nombre") String nombre, @Param("clave") String clave);
	
	@Query("SELECT c FROM Curso c where c.pk_id_curso = :fk_id_curso ")
	Curso findByID(@Param("fk_id_curso") int fk_id_curso);
	
	@Query("SELECT c FROM Curso c where c.nombre = :nombre")
	Curso findByNombre(@Param("nombre") String nombre);
	
	/*
	 * @author Derian Estrada
	 * Consultas por Clave
	 */
	@Query("SELECT c FROM Curso c WHERE upper(c.clave) LIKE CONCAT('%',:clave,'%')")
	List<Curso> findByClave(@Param("clave") String clave);
	
	@Query(value="SELECT * FROM Curso", nativeQuery = true)
	List<Curso> loadAllCursos();
	
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
