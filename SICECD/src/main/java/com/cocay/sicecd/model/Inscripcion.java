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
	
	@Column(name = "calif", nullable = true, length=3)
	String calif;
	
	@Column(name = "aprobado", nullable = true)
	boolean aprobado;
	
	@ManyToOne(targetEntity=Grupo.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "fk_id_grupo",referencedColumnName="pk_id_grupo",insertable = true, updatable = true)
	Grupo fk_id_grupo;
	
	@ManyToOne(targetEntity=Profesor.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "fk_id_profesor",referencedColumnName="pk_id_profesor",insertable = true, updatable = true)
	Profesor fk_id_profesor;
	
	public Inscripcion(){}	
	
	public int getPk_id_inscripcion() {
		return pk_id_inscripcion;
	}
	public void setPk_id_inscripcion(int pk_id_inscripcion) {
		this.pk_id_inscripcion = pk_id_inscripcion;
	}
	public Grupo getFk_id_grupo() {
		return fk_id_grupo;
	}
	public void setFk_id_grupo(Grupo fk_id_grupo) {
		this.fk_id_grupo = fk_id_grupo;
	}
	public Profesor getFk_id_profesor() {
		return fk_id_profesor;
	}
	public void setFk_id_profesor(Profesor fk_id_profesor) {
		this.fk_id_profesor = fk_id_profesor;
	}

	public String getCalif() {
		return calif;
	}

	public void setCalif(String calif) {
		this.calif = calif;
	}

	public boolean isAprobado() {
		return aprobado;
	}

	public void setAprobado(boolean aprobado) {
		this.aprobado = aprobado;
	}

}
