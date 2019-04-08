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
@Table(name = "Curso")
public class Curso {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pk_id_curso")
	int pk_id_curso;
	@Column(name = "clave")
	String clave;
	@Column(name = "nombre")
	String nombre;

	@ManyToOne(targetEntity=Tipo_curso.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "fk_id_tipo_curso", referencedColumnName="pk_id_tipo_curso")
	int fk_id_tipo_curso;
	
	@Column(name = "horas")

	int horas;

	@OneToMany(mappedBy = "fk_id_curso", targetEntity=Grupo.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Grupo> grupos = new ArrayList<>();

	public int getPk_id_curso() {
		return pk_id_curso;
	}

	public void setPk_id_curso(int pk_id_curso) {
		this.pk_id_curso = pk_id_curso;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getFk_id_tipo_curso() {
		return fk_id_tipo_curso;
	}

	public void setFk_id_tipo_curso(int fk_id_tipo_curso) {
		this.fk_id_tipo_curso = fk_id_tipo_curso;
	}

	public int getHoras() {
		return horas;
	}

	public void setHoras(int horas) {
		this.horas = horas;
	}

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}



}
