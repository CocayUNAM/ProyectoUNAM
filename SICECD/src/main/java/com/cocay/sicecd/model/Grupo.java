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
	@ManyToOne(targetEntity=Curso.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "fk_id_curso")
	int fk_id_curso;
	@Column(name = "clave")
	String clave;
	@Column(name = "fecha_inicio")
	Date fecha_inicio;
	@Column(name = "fecha_fin")
	Date fecha_fin;

	@OneToMany(mappedBy = "fk_id_grupo", targetEntity=Inscripcion.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Inscripcion> inscripciones = new ArrayList<>();

	public int getPk_id_grupo() {
		return pk_id_grupo;
	}

	public void setPk_id_grupo(int pk_id_grupo) {
		this.pk_id_grupo = pk_id_grupo;
	}

	public int getFk_id_curso() {
		return fk_id_curso;
	}

	public void setFk_id_curso(int fk_id_curso) {
		this.fk_id_curso = fk_id_curso;
	}

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


}
