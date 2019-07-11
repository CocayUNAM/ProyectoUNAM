package com.cocay.sicecd.model;

import java.util.ArrayList;
import java.util.Date;
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
	
	@Column(name = "nombre", nullable = false)
	String nombre;
	
	@Column(name = "apellido_paterno", nullable = false)
	String apellido_paterno;
	
	@Column(name = "apellido_materno")
	String apellido_materno;
	
	@Column(name = "rfc", nullable = false)
	String rfc;
	
	@Column(name = "curp")
	String curp;
	
	@Column(name = "correo", nullable = false)
	String correo;
	
	@Column(name = "telefono")
	String telefono;
	
	@Column(name = "fecha_nac")
	Date fechaNac;
	
	@ManyToOne(targetEntity=Estado.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "fk_id_estado", referencedColumnName="pk_id_estado",insertable = true, updatable = true)
	Estado fk_id_estado;
	
	@Column(name = "ciudad_localidad")
	String ciudad_localidad;
	
	@ManyToOne(targetEntity=Genero.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "id_genero", referencedColumnName="pk_id_genero",insertable = true, updatable = true)
	Genero id_genero;
	
	@Column(name = "plantel")
	String plantel;
	
	@Column(name = "clave_plantel")
	String clave_plantel;
	
	@ManyToOne(targetEntity=Turno.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "fk_id_turno",referencedColumnName="pk_id_turno",insertable = true, updatable = true)
	Turno fk_id_turno;
	
	@ManyToOne(targetEntity=Grado_profesor.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "fk_id_grado_profesor",referencedColumnName="pk_id_grado_profesor",insertable = true, updatable = true)
	Grado_profesor fk_id_grado_profesor;
	
	@Column(name = "ocupacion")
	String ocupacion;

	@OneToMany(mappedBy = "fk_id_profesor", targetEntity=Inscripcion.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Inscripcion> inscripciones = new ArrayList<>();

	@OneToMany(mappedBy = "fk_id_profesor",targetEntity=Certificado.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Certificado> certificados = new ArrayList<>();
	
	@Column(name = "comprobante_dom")
	String comprobante_dom;
	
	@Column(name = "curp_doc")
	String curp_doc;
	
	@Column(name = "rfc_doc")
	String rfc_doc;
	
	@Column(name = "certificado_doc")
	String certificado_doc;
	
	public Profesor(){}
	
	public Profesor(String nombre, String apellido_paterno,String apellido_materno,String correo, String rfc) {
		this.nombre=nombre;
		this.apellido_paterno=apellido_paterno;
		this.apellido_materno=apellido_materno;
		this.correo=correo;
		this.rfc=rfc;
		
	}
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
	
	public Genero getGenero() {
		return id_genero;
	}
	
	public void setGenero(Genero id_genero) {
		this.id_genero = id_genero;
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

	public Estado getFk_id_estado() {
		return fk_id_estado;
	}

	/*Llave foranea del estado*/
	public void setFk_id_estado(Estado fk_id_estado) {
		this.fk_id_estado = fk_id_estado;
	}

	public String getCiudad_localidad() {
		return ciudad_localidad;
	}

	public void setCiudad_localidad(String ciudad_localidad) {
		this.ciudad_localidad = ciudad_localidad;
	}

	public Genero getId_genero() {
		return id_genero;
	}

	public void setId_genero(Genero id_genero) {
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

	public Turno getFk_id_turno() {
		return fk_id_turno;
	}

	public void setFk_id_turno(Turno fk_id_turno) {
		this.fk_id_turno = fk_id_turno;
	}

	public Grado_profesor getFk_id_grado_profesor() {
		return fk_id_grado_profesor;
	}

	public void setFk_id_grado_profesor(Grado_profesor fk_id_grado_profesor) {
		this.fk_id_grado_profesor = fk_id_grado_profesor;
	}

	public String getOcupacion() {
		return ocupacion;
	}

	public void setOcupacion(String ocupacion) {
		this.ocupacion = ocupacion;
	}

	public List<Inscripcion> getInscripciones() {
		return inscripciones;
	}

	public void setInscripciones(List<Inscripcion> inscripciones) {
		this.inscripciones = inscripciones;
	}
	
	public Date getFechaNac() {
		return fechaNac;
	}
	
	public void setFechaNac(Date fechaNac) {
		this.fechaNac = fechaNac;
	}
	
	public List<Certificado> getCertificados() {
		return certificados;
	}

	public void setCertificados(List<Certificado> certificados) {
		this.certificados = certificados;
	}
	
}