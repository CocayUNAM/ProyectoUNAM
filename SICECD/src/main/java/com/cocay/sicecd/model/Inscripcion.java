package com.cocay.sicecd.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "Inscripcion")
public class Inscripcion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pk_id_inscripcion")
	int pk_id_inscripcion;
	@ManyToOne(targetEntity=Grupo.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "fk_id_grupo",referencedColumnName="pk_id_grupo",insertable = false, updatable = false)
	Grupo grupo;
	int fk_id_grupo;
	@ManyToOne(targetEntity=Profesor.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "fk_id_profesor",referencedColumnName="pk_id_profesor",insertable = false, updatable = false)
	Profesor profesor;
	int fk_id_profesor;
	public int getPk_id_inscripcion() {
		return pk_id_inscripcion;
	}
	public void setPk_id_inscripcion(int pk_id_inscripcion) {
		this.pk_id_inscripcion = pk_id_inscripcion;
	}
	public int getFk_id_grupo() {
		return fk_id_grupo;
	}
	public void setFk_id_grupo(int fk_id_grupo) {
		this.fk_id_grupo = fk_id_grupo;
	}
	public int getFk_id_profesor() {
		return fk_id_profesor;
	}
	public void setFk_id_profesor(int fk_id_profesor) {
		this.fk_id_profesor = fk_id_profesor;
	}


}
