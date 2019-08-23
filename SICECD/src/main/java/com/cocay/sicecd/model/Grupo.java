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
@Table(name = "Grupo")
public class Grupo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pk_id_grupo")
	int pk_id_grupo;
	
	@Column(name = "clave", nullable = false, length=8, unique=true)
	String clave;
	
	@Column(name = "fecha_inicio", nullable = true)
	Date fecha_inicio;
	
	@Column(name = "fecha_fin", nullable = true)
	Date fecha_fin;

	@Column(name = "tempCurso", nullable = true)
	private String tempCurso;
	
	@Column(name = "tempProfesor", nullable = true)
	private String tempProfesor;
	
	@Column(name = "stTabla", nullable = true)
	Integer stTabla;

	
	@ManyToOne(targetEntity=Curso.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "fk_id_curso", referencedColumnName="pk_id_curso", insertable = true, updatable = true, nullable = false)
	Curso fk_id_curso;
	
	@ManyToOne(targetEntity=Profesor.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "fk_id_profesor",referencedColumnName="pk_id_profesor",insertable = true, updatable = true)
	Profesor fk_id_profesor;
	
	@OneToMany(mappedBy = "fk_id_grupo", targetEntity=Inscripcion.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Inscripcion> inscripciones = new ArrayList<>();
	
	@OneToMany(mappedBy = "fk_id_grupo",targetEntity=Certificado.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Certificado> certificados = new ArrayList<>();
	
	public List<Certificado> getCertificados() {
		return certificados;
	}

	public void setCertificados(List<Certificado> certificados) {
		this.certificados = certificados;
	}

	public int getPk_id_grupo() {
		return pk_id_grupo;
	}

	public void setPk_id_grupo(int pk_id_grupo) {
		this.pk_id_grupo = pk_id_grupo;
	}
	
	public Curso getFk_id_curso() {
		return fk_id_curso;
	}

	public void setFk_id_curso(Curso curso) {
		this.fk_id_curso = curso;
	}

	/*
	public int getFk_id_curso() {
		return fk_id_curso;
	}

	public void setFk_id_curso(int fk_id_curso) {
		this.fk_id_curso = fk_id_curso;
	}*/

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public Date getFecha_inicio() {
		return fecha_inicio;
	}

	public void setFecha_inicio(Date fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}

	public Date getFecha_fin() {
		return fecha_fin;
	}

	public void setFecha_fin(Date fecha_fin) {
		this.fecha_fin = fecha_fin;
	}

	public List<Inscripcion> getInscripciones() {
		return inscripciones;
	}

	public void setInscripciones(List<Inscripcion> inscripciones) {
		this.inscripciones = inscripciones;
	}

	public Profesor getFk_id_profesor() {
		return fk_id_profesor;
	}

	public void setFk_id_profesor(Profesor fk_id_profesor) {
		this.fk_id_profesor = fk_id_profesor;
	}

	public Integer getStTabla() {
		return stTabla;
	}

	public void setStTabla(Integer stTabla) {
		this.stTabla = stTabla;
	}

	public String getTempCurso() {
		return tempCurso;
	}

	public void setTempCurso(String tempCurso) {
		this.tempCurso = tempCurso;
	}

	public String getTempProfesor() {
		return tempProfesor;
	}

	public void setTempProfesor(String tempProfesor) {
		this.tempProfesor = tempProfesor;
	}
	
	
}
