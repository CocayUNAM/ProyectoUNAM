package com.cocay.sicecd.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Errores")
public class Errores {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pk_id_mns")
	int pk_id_mns;
	
	@Column(name = "mensaje")
	String mensaje;
	
	@Column(name = "estado")
	Integer estado;

	public int getPk_id_mns() {
		return pk_id_mns;
	}

	public void setPk_id_mns(int pk_id_mns) {
		this.pk_id_mns = pk_id_mns;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	
	
}
