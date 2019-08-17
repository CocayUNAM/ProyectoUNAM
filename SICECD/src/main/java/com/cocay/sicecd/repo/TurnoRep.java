package com.cocay.sicecd.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cocay.sicecd.model.Turno;

@Repository
public interface TurnoRep extends PagingAndSortingRepository<Turno, Integer>{
	List<Turno> findByNombre(String name);
	
	@Query("SELECT c FROM Turno c where c.nombre = :nombre")
	Turno findByNombreTurno(@Param("nombre") String nombre);

}
