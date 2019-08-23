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
@Table(name="Certificado")
public class Certificado {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pk_id_certificado")
	private int pk_id_certificado;
	
	@Column(name = "ruta", nullable = false, length=250, unique=true)
	private String ruta;
	
	@Column(name = "tiempo_creado", nullable = false)
	private long tiempo_creado;
	
	@ManyToOne(targetEntity=Profesor.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "fk_id_profesor",referencedColumnName="pk_id_profesor", nullable = false)
	private Profesor fk_id_profesor;
	
	@ManyToOne(targetEntity=Curso.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "fk_id_curso", referencedColumnName="pk_id_curso", nullable = false)
	private Curso fk_id_curso;
	
	@ManyToOne(targetEntity=Grupo.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "fk_id_grupo", referencedColumnName="pk_id_grupo", nullable = false)
	private Grupo fk_id_grupo;
    
	public int getPk_id_certificado() {
		return pk_id_certificado;
	}

	public void setPk_id_certificado(int pk_id_certificado) {
		this.pk_id_certificado = pk_id_certificado;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public Profesor getFk_id_profesor() {
		return fk_id_profesor;
	}

	public void setFk_id_profesor(Profesor fk_id_profesor) {
		this.fk_id_profesor = fk_id_profesor;
	}

	public Curso getFk_id_curso() {
		return fk_id_curso;
	}

	public void setFk_id_curso(Curso fk_id_curso) {
		this.fk_id_curso = fk_id_curso;
	}
	
	public Grupo getFk_id_grupo() {
		return fk_id_grupo;
	}

	public void setFk_id_grupo(Grupo fk_id_grupo) {
		this.fk_id_grupo = fk_id_grupo;
	}

        public long getTiempo_creado() {
		return this.tiempo_creado;
	}
	
	public void setTiempo_creado(long tiempo_creado) {
		this.tiempo_creado = tiempo_creado;
	}

}
