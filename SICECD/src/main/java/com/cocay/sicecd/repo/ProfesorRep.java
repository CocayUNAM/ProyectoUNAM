package com.cocay.sicecd.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cocay.sicecd.model.Curso;
import com.cocay.sicecd.model.Profesor;


@Repository
public interface ProfesorRep extends CrudRepository<Profesor, Integer>{
	
	@Query("SELECT p FROM Profesor p where p.pk_id_profesor = :id ")
	Profesor findByID(@Param("id")Integer id);
	
	@Query("SELECT p FROM Profesor p "
			+ "WHERE upper(p.nombre) LIKE CONCAT('%',:nombre,'%') "
			+ "AND upper(p.apellido_paterno) LIKE CONCAT('%',:apellido_paterno,'%') ")
	List<Profesor> findByName(@Param("nombre") String nombre,
					@Param("apellido_paterno") String apellido_paterno);
	
	@Query("SELECT p FROM Profesor p "
			+ "WHERE p.curp LIKE CONCAT('%',:curp,'%') "
			+ "AND p.rfc LIKE CONCAT('%',:rfc,'%') "
			+ "AND upper(p.nombre) LIKE CONCAT('%',:nombre,'%') "
			+ "AND upper(p.apellido_paterno) LIKE CONCAT('%',:apellido_paterno,'%') "
			+ "AND upper(p.apellido_materno) LIKE CONCAT('%',:apellido_materno,'%') ")
	List<Profesor> findByParams(@Param("curp") String curp,
			@Param("rfc") String rfc,
			@Param("nombre") String nombre,
			@Param("apellido_paterno") String apellido_paterno,
			@Param("apellido_materno") String apellido_materno);
	
	@Query(value="SELECT * FROM Profesor "
			+ "WHERE curp LIKE CONCAT('%',:curp,'%') "
			+ "AND rfc LIKE CONCAT('%',:rfc,'%') "
			+ "AND upper(nombre) LIKE CONCAT('%',:nombre,'%') "
			+ "AND upper(apellido_paterno) LIKE CONCAT('%',:apellido_paterno,'%') "
			+ "AND upper(apellido_materno) LIKE CONCAT('%',:apellido_materno,'%') "
			+ "AND fk_id_estado=:estado", nativeQuery=true)
	List<Profesor> findByParams(@Param("curp") String curp,
			@Param("rfc") String rfc,
			@Param("nombre") String nombre,
			@Param("apellido_paterno") String apellido_paterno,
			@Param("apellido_materno") String apellido_materno,
			@Param("estado") Integer estado);

	@Query("SELECT p FROM Profesor p")
	List<Profesor> findAll();
	
	@Query("SELECT p FROM Profesor p ORDER BY p.pk_id_profesor DESC")
	List<Profesor> findHigherID();
	
	@Query("SELECT p FROM Profesor p where UPPER(p.rfc) = UPPER(:rfc) ")
	Profesor findByRfc(@Param("rfc")String rfc);
	
	@Query("SELECT p FROM Profesor p where UPPER(p.curp) = UPPER(:curp) ")
	Profesor findByCurp(@Param("curp")String curp);
	
	@Query("SELECT p FROM Profesor p where UPPER(p.rfc) = UPPER(:rfc) ")
	Profesor findByRFC(@Param("rfc")String rfc);
	
	@Query("SELECT p FROM Profesor p "
			+ "WHERE upper(p.nombre) LIKE CONCAT('%',:nombre,'%') "
			+ "AND upper(p.apellido_paterno) LIKE CONCAT('%',:apellido_paterno,'%') "
			+ "AND upper(p.apellido_materno) LIKE CONCAT('%',:apellido_materno,'%')")
	List<Profesor> findByCompleteNameList(@Param("nombre") String nombre,
					@Param("apellido_paterno") String apellido_paterno,
					@Param("apellido_materno") String apellido_materno);
	
	@Query("SELECT p FROM Profesor p where p.correo = :correo")
	Profesor findByCorreo(@Param("correo")String correo);
	
	@Query(value="SELECT * FROM Profesor", nativeQuery = true)
	List<Profesor> loadAllProfesor();
	
	@Modifying
    @Query(value = "insert into Profesor (nombre,apellido_paterno,apellido_materno,curp,rfc,correo,clave_plantel,ciudad_localidad,fk_id_estado,fk_id_grado_profesor,fk_id_turno,id_genero) VALUES (:nombre,:apellido_paterno,:apellido_materno,:curp,:rfc,:email,:institution,:city,:id_estado,:id_grado_profesor,:id_turno,:id_genero)", nativeQuery = true)
    @Transactional
    void saveT(@Param("nombre") String firstname, @Param("apellido_paterno") String apellido_paterno, @Param("apellido_materno") String apellido_materno,@Param("curp") String curp,@Param("rfc") String rfc,@Param("email") String email,@Param("institution") String institution,@Param("city") String city,@Param("id_estado") int id_estado,@Param("id_grado_profesor") int id_grado_profesor,@Param("id_turno") int id_turno,@Param("id_genero") int genero);

	@Modifying
    @Query(value = "insert into Profesor (nombre,apellido_paterno,apellido_materno,curp,rfc,correo,clave_plantel,ciudad_localidad,fk_id_estado,fk_id_grado_profesor,fk_id_turno,id_genero) VALUES (:nombre,:apellido_paterno,:apellido_materno,:curp,:rfc,:email,:institution,:city,:id_estado,:id_grado_profesor,:id_turno,:id_genero)", nativeQuery = true)
    @Transactional
    void saveT(@Param("nombre") String firstname, @Param("apellido_paterno") String apellido_paterno, @Param("apellido_materno") String apellido_materno,@Param("curp") String curp,@Param("rfc") String rfc,@Param("email") String email,@Param("institution") String institution,@Param("city") String city);
	
	@Modifying(clearAutomatically = true)
	@Query("update Profesor p set p.nombre =:nombre, p.apellido_paterno=:apellido_paterno, p.apellido_materno=:apellido_materno,p.correo=:correo, p.clave_plantel=:clave_plantel, p.ciudad_localidad=:ciudad_localidad where p.curp =:curp")
	@Transactional
	void updateProfesor(@Param("nombre") String nombre,@Param("apellido_paterno") String apellido_paterno,@Param("apellido_materno") String apellido_materno,@Param("correo") String correo,@Param("clave_plantel") String clave_plantel,@Param("ciudad_localidad") String ciudad_localidad,@Param("curp") String curp);
	
	
	@Modifying(clearAutomatically = true)
	@Query("update Profesor p set p.nombre =:nombre, p.apellido_paterno=:apellido_paterno, p.apellido_materno=:apellido_materno,p.correo=:correo, p.clave_plantel=:clave_plantel, p.ciudad_localidad=:ciudad_localidad where p.rfc =:rfc")
	@Transactional
	void updateProfesorByRFC(@Param("nombre") String nombre,@Param("apellido_paterno") String apellido_paterno,@Param("apellido_materno") String apellido_materno,@Param("correo") String correo,@Param("clave_plantel") String clave_plantel,@Param("ciudad_localidad") String ciudad_localidad,@Param("rfc") String rfc);
}