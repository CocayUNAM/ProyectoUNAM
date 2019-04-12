package com.cocay.sicecd.repo;





import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cocay.sicecd.model.Profesor;


@Repository
public interface ProfesorRep extends PagingAndSortingRepository<Profesor, Integer>{
//List<Profesor> findByRfc(String name);

@Query("select new com.cocay.sicecd.model.Profesor(profesor.nombre, profesor.apellido_paterno,profesor.apellido_materno,profesor.correo,profesor.rfc) from Profesor profesor where profesor.rfc = :rfc ")
Profesor findByRfc(@Param("rfc")String rfc);

@Query("select p from Profesor p where p.nombre = :nombre and p.apellido_paterno = :apellido_paterno and p.apellido_materno = :apellido_materno")
Profesor findByCompleteName(@Param("nombre") String nombre,
                             @Param("apellido_paterno") String apellido_paterno,
                             @Param("apellido_materno") String apellido_materno);
}
	
	
