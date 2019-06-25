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
public interface CursoRep extends PagingAndSortingRepository<Curso, Integer>{
	
	@Query("SELECT c FROM Curso c")
	List<Curso> findAll();
	
	@Query("SELECT c FROM Curso c where c.pk_id_curso = :fk_id_curso ")
	Curso findByID(@Param("fk_id_curso") int fk_id_curso);
	
	@Query("SELECT c FROM Curso c where c.nombre = :nombre")
	Curso findByNombre(@Param("nombre") String nombre);
	
	/*
	 * @author Derian Estrada
	 * Consultas por Nombre
	 */
	@Query(value = "SELECT * FROM Curso WHERE nombre = :nombre", nativeQuery = true)
	List<Curso> findByName(@Param("nombre") String nombre);
	
	@Query(value = "SELECT * FROM Curso WHERE nombre = :nombre AND f_inicio = :fecha_ini", nativeQuery = true)
	List<Curso> findByNombreAndFechaIni(@Param("nombre") String nombre, @Param("fecha_ini") Date fecha_ini);
	
	@Query(value = "SELECT * FROM Curso WHERE nombre = :nombre AND f_termino = :fecha_fin", nativeQuery = true)
	List<Curso> findByNombreAndFechaFin(@Param("nombre") String clave, @Param("fecha_fin") Date fecha_fin);
	
	@Query(value = "SELECT * FROM Curso WHERE nombre = :nombre AND f_inicio = :fecha_ini AND f_termino = :fecha_fin", nativeQuery = true)
	List<Curso> findByNombreAndFecha(@Param("nombre") String nombre, @Param("fecha_ini") Date fecha_ini, @Param("fecha_fin") Date fecha_fin);
	
	/*
	 * @author Derian Estrada
	 * Consultas por Clave
	 */
	@Query("SELECT c FROM Curso c WHERE upper(c.clave) LIKE CONCAT('%',:clave,'%')")
	List<Curso> findByClave(@Param("clave") String clave);
	
	@Query(value = "SELECT * FROM Curso WHERE clave = :clave AND f_inicio = :fecha_ini", nativeQuery = true)
	List<Curso> findByClaveAndFechaIni(@Param("clave") String clave, @Param("fecha_ini") Date fecha_ini);
	
	@Query(value = "SELECT * FROM Curso WHERE clave = :clave AND f_termino = :fecha_fin", nativeQuery = true)
	List<Curso> findByClaveAndFechaFin(@Param("clave") String clave, @Param("fecha_fin") Date fecha_fin);
	
	@Query(value = "SELECT * FROM Curso WHERE clave = :clave AND f_inicio = :fecha_ini AND f_termino = :fecha_fin", nativeQuery = true)
	List<Curso> findByClaveAndFecha(@Param("clave") String clave, @Param("fecha_ini") Date fecha_ini, @Param("fecha_fin") Date fecha_fin);
	
	/*
	 * @author Derian Estrada
	 * Consultas por Tipo de curso
	 */
	@Query(value = "SELECT * FROM Curso WHERE fk_id_tipo_curso = :id_tipo", nativeQuery = true)
	List<Curso> findByTipo(@Param("id_tipo") int id_tipo);
	
	@Query(value = "SELECT * FROM Curso WHERE fk_id_tipo_curso = :id_tipo AND f_inicio = :fecha_ini", nativeQuery = true)
	List<Curso> findByTipoAndFechaIni(@Param("id_tipo") int id_tipo, @Param("fecha_ini") Date fecha_fin);
	
	@Query(value = "SELECT * FROM Curso WHERE fk_id_tipo_curso = :id_tipo AND f_termino = :fecha_fin", nativeQuery = true)
	List<Curso> findByTipoAndFechaFin(@Param("id_tipo") int id_tipo, @Param("fecha_fin") Date fecha_fin);
	
	@Query(value = "SELECT * FROM Curso WHERE fk_id_tipo_curso = :id_tipo AND f_inicio = :fecha_ini AND f_termino = :fecha_fin", nativeQuery = true)
	List<Curso> findByTipoAndFecha(@Param("id_tipo") int id_tipo, @Param("fecha_ini") Date fecha_ini, @Param("fecha_fin") Date fecha_fin);
	
	/*
	 * @author Derian Estrada
	 * Consultas Simples por Fecha
	 */
	@Query(value = "SELECT * FROM Curso WHERE f_inicio = :fecha_ini", nativeQuery = true)
	List<Curso> findByFechaInicio(@Param("fecha_ini") Date fecha_ini);
	
	@Query(value = "SELECT * FROM Curso WHERE f_termino = :fecha_fin", nativeQuery = true)
	List<Curso> findByFechaFin(@Param("fecha_fin") Date fecha_fin_1);
	
	@Query(value = "SELECT * FROM Curso WHERE f_inicio = :fecha_ini AND f_termino = :fecha_fin", nativeQuery = true)
	List<Curso> findByFecha(@Param("fecha_ini") Date fecha_ini, @Param("fecha_fin") Date fecha_fin);
	
	/*
	 * @author Derian Estrada
	 * Consultas Avanzadas por Fecha
	 */
	@Query(value = "SELECT * FROM Curso WHERE f_inicio >= :fecha_ini_1 AND f_inicio <= :fecha_ini_2", nativeQuery = true)
	List<Curso> findByFechaInicio(@Param("fecha_ini_1") Date fecha_ini_1, @Param("fecha_ini_2") Date fecha_ini_2);
	
	@Query(value = "SELECT * FROM Curso WHERE f_termino >= :fecha_fin_1 AND f_termino <= :fecha_fin_2", nativeQuery = true)
	List<Curso> findByFechaFin(@Param("fecha_fin_1") Date fecha_fin_1, @Param("fecha_fin_2") Date fecha_fin_2);
	
	@Query(value = "SELECT * FROM Curso WHERE f_inicio >= :fecha_ini_1 AND f_inicio <= :fecha_ini_2 AND f_termino >= :fecha_fin_1 AND f_termino <= :fecha_fin_2", nativeQuery = true)
	List<Curso> findByFecha(@Param("fecha_ini_1") Date fecha_ini_1, @Param("fecha_ini_2") Date fecha_ini_2,
							@Param("fecha_fin_1") Date fecha_fin_1, @Param("fecha_fin_2") Date fecha_fin_2);
	
	@Query(value="SELECT * FROM Curso", nativeQuery = true)
	List<Curso> loadAllCursos();

}
