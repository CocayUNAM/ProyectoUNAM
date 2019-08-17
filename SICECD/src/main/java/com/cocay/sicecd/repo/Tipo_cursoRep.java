package com.cocay.sicecd.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cocay.sicecd.model.Curso;
import com.cocay.sicecd.model.Tipo_curso;

@Repository
public interface Tipo_cursoRep extends PagingAndSortingRepository<Tipo_curso, Integer>{
	List<Tipo_curso> findByNombre(String name);
	
	@Query("SELECT t FROM Tipo_curso t where t.pk_id_tipo_curso = :fk_id_tipo_curso ")
	List<Tipo_curso> findByID(@Param("fk_id_tipo_curso") int fk_id_tipo_curso);
	
	@Query("SELECT t FROM Tipo_curso t where t.nombre = :nombre")
	Tipo_curso findByNombreTipo(@Param("nombre") String nombre);

}
