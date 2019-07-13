package com.cocay.sicecd.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "Log_evento_sys")
public class Log_evento_sys {
	
	@Id
	@Column(name = "pk_id_log_evento_sys", length=6)
	String pk_id_log_evento_sys;

	@Column(name = "nombre", nullable = false, length=100, unique=true)
	String nombre;

	@OneToMany(mappedBy = "fk_id_log_evento_sys", targetEntity=Log_sys.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Log_sys> Log_sys_s = new ArrayList<>();

	public String getPk_id_log_evento_sys() {
		return pk_id_log_evento_sys;
	}

	public void setPk_id_log_evento_sys(String pk_id_log_evento_sys) {
		this.pk_id_log_evento_sys = pk_id_log_evento_sys;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Log_sys> getLog_sys_s() {
		return Log_sys_s;
	}

	public void setLog_sys_s(List<Log_sys> log_sys_s) {
		Log_sys_s = log_sys_s;
	}
}
