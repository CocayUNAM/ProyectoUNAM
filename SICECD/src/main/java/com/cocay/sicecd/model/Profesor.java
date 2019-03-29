package com.cocay.sicecd.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "Profesor")
public class Profesor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pk_id_profesor")
	int pk_id_profesor;
	@Column(name = "nombre")
	String nombre;
	@Column(name = "apellido_paterno")
	String apellido_paterno;
	@Column(name = "apellido_materno")
	String apellido_materno;
	@Column(name = "rfc")
	String rfc;
	@Column(name = "curp")
	String curp;
	@Column(name = "correo")
	String correo;
	@Column(name = "telefono")
	String telefono;
	@ManyToOne(targetEntity=Estado.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "fk_id_estado")
	int fk_id_estado;
	String ciudad_localidad;
	int id_genero;
	String plantel;
	String clave_plantel;
	@ManyToOne(targetEntity=Turno.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "fk_id_turno")
	int fk_id_turno;
	@ManyToOne(targetEntity=Grado_profesor.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "fk_id_grado_profesor")
	int fk_id_grado_profesor;
	String ocupacion;
	String curriculum;

	@OneToMany(mappedBy = "fk_id_profesor", targetEntity=Inscripcion.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Inscripcion> inscripciones = new ArrayList<>();

	public int getPk_id_profesor() {
		return pk_id_profesor;
	}

	public void setPk_id_profesor(int pk_id_profesor) {
		this.pk_id_profesor = pk_id_profesor;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido_paterno() {
		return apellido_paterno;
	}

	public void setApellido_paterno(String apellido_paterno) {
		this.apellido_paterno = apellido_paterno;
	}

	public String getApellido_materno() {
		return apellido_materno;
	}

	public void setApellido_materno(String apellido_materno) {
		this.apellido_materno = apellido_materno;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public int getFk_id_estado() {
		return fk_id_estado;
	}

	public void setFk_id_estado(int fk_id_estado) {
		this.fk_id_estado = fk_id_estado;
	}

	public String getCiudad_localidad() {
		return ciudad_localidad;
	}

	public void setCiudad_localidad(String ciudad_localidad) {
		this.ciudad_localidad = ciudad_localidad;
	}

	public int getId_genero() {
		return id_genero;
	}

	public void setId_genero(int id_genero) {
		this.id_genero = id_genero;
	}

	public String getPlantel() {
		return plantel;
	}

	public void setPlantel(String plantel) {
		this.plantel = plantel;
	}

	public String getClave_plantel() {
		return clave_plantel;
	}

	public void setClave_plantel(String clave_plantel) {
		this.clave_plantel = clave_plantel;
	}

	public int getFk_id_turno() {
		return fk_id_turno;
	}

	public void setFk_id_turno(int fk_id_turno) {
		this.fk_id_turno = fk_id_turno;
	}

	public int getFk_id_grado_profesor() {
		return fk_id_grado_profesor;
	}

	public void setFk_id_grado_profesor(int fk_id_grado_profesor) {
		this.fk_id_grado_profesor = fk_id_grado_profesor;
	}

	public String getOcupacion() {
		return ocupacion;
	}

	public void setOcupacion(String ocupacion) {
		this.ocupacion = ocupacion;
	}

	public String getCurriculum() {
		return curriculum;
	}

	public void setCurriculum(String curriculum) {
		this.curriculum = curriculum;
	}

	public List<Inscripcion> getInscripciones() {
		return inscripciones;
	}

	public void setInscripciones(List<Inscripcion> inscripciones) {
		this.inscripciones = inscripciones;
	}
	
	
}
