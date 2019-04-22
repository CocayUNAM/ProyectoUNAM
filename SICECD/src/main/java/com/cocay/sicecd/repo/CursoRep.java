package com.cocay.sicecd.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cocay.sicecd.model.Curso;
import com.cocay.sicecd.model.Tipo_curso;

@Repository
public interface CursoRep extends PagingAndSortingRepository<Curso, Integer>{
	Curso findByNombre(String name);
	
	@Query("SELECT c FROM Curso c "
			+ "WHERE upper(c.clave) LIKE CONCAT('%',:clave,'%')")
	Curso findByClave(@Param("clave") String clave);
	
	@Query("SELECT c FROM Curso c where c.pk_id_curso = :fk_id_curso ")
	Curso findByID(@Param("fk_id_curso") int fk_id_curso);

}
