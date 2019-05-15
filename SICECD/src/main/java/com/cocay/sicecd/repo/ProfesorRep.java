package com.cocay.sicecd.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cocay.sicecd.model.Profesor;


@Repository
public interface ProfesorRep extends PagingAndSortingRepository<Profesor, Integer>{

	@Query("SELECT p FROM Profesor p")
	List<Profesor> findAll();
	
	@Query("SELECT p FROM Profesor p where p.rfc = :rfc ")
	Profesor findByRfc(@Param("rfc")String rfc);
	
	@Query("SELECT p FROM Profesor p where p.rfc = :rfc ")
	List<Profesor> findByRfcList(@Param("rfc")String rfc);

	@Query("SELECT p FROM Profesor p "
			+ "WHERE upper(p.nombre) LIKE CONCAT('%',:nombre,'%') "
			+ "AND upper(p.apellido_paterno) LIKE CONCAT('%',:apellido_paterno,'%') "
			+ "AND upper(p.apellido_materno) LIKE CONCAT('%',:apellido_materno,'%')")
	List<Profesor> findByCompleteNameList(@Param("nombre") String nombre,
					@Param("apellido_paterno") String apellido_paterno,
					@Param("apellido_materno") String apellido_materno);
	
	@Query("SELECT p FROM Profesor p "
			+ "WHERE upper(p.apellido_paterno) LIKE CONCAT('%',:apellido_paterno,'%') "
			+ "AND upper(p.apellido_materno) LIKE CONCAT('%',:apellido_materno,'%')")
	List<Profesor> findByLastName(@Param("apellido_paterno") String apellido_paterno,
								  @Param("apellido_materno") String apellido_materno);
	
	@Query("SELECT p FROM Profesor p where p.correo = :correo")
	Profesor findByCorreo(@Param("correo")String correo);
	
	@Query(value="SELECT * FROM Profesor", nativeQuery = true)
	List<Profesor> loadAllProfesor();
	
	@Modifying
    @Query(value = "insert into Profesor (nombre,apellido_paterno,curp,correo,plantel,ciudad_localidad,fk_id_estado,fk_id_grado_profesor,fk_id_turno,id_genero) VALUES (:firstname,:lastname,:username,:email,:institution,:city,:id_estado,:id_grado_profesor,:id_turno,:genero)", nativeQuery = true)
    @Transactional
    void saveT(@Param("firstname") String firstname, @Param("lastname") String lastname,@Param("username") String username,@Param("email") String email,@Param("institution") String institution,@Param("city") String city,@Param("id_estado") int id_estado,@Param("id_grado_profesor") int id_grado_profesor,@Param("id_turno") int id_turno,@Param("genero") int genero);
}
	
	
