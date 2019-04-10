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
@Table(name = "Usuario_sys")
public class Usuario_sys {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pk_id_usuario_sys")
	int pk_id_usuario_sys;
	@Column(name = "rfc")
	String rfc;
	@Column(name = "password")
	String password;
	@Column(name = "correo")
	String correo;
	@Column(name = "nombre")
	String nombre;
	@Column(name = "apellido_paterno")
	String apellido_paterno;
	
	@Column(name = "apellido_materno")
	String apellido_materno;
	
	@ManyToOne(targetEntity = Estatus_usuario_sys.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "fk_id_estatus_usuario_sys", referencedColumnName="pk_estatus_usuario_sys")
	Estatus_usuario_sys fk_id_estatus_usuario_sys;
	
	@ManyToOne(targetEntity = Perfil_sys.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "fk_id_perfil_sys", referencedColumnName="pk_id_perfil_sys")
	Perfil_sys fk_id_perfil_sys;

	@OneToMany(mappedBy = "fk_id_usuario_sys", targetEntity=Log_sys.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Log_sys> Log_sys_s = new ArrayList<>();

	public int getPk_id_usuario_sys() {
		return pk_id_usuario_sys;
	}

	public void setPk_id_usuario_sys(int pk_id_usuario_sys) {
		this.pk_id_usuario_sys = pk_id_usuario_sys;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
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



	public Estatus_usuario_sys getFk_id_estatus_usuario_sys() {
		return fk_id_estatus_usuario_sys;
	}

	public void setFk_id_estatus_usuario_sys(Estatus_usuario_sys fk_id_estatus_usuario_sys) {
		this.fk_id_estatus_usuario_sys = fk_id_estatus_usuario_sys;
	}

	public Perfil_sys getFk_id_perfil_sys() {
		return fk_id_perfil_sys;
	}

	public void setFk_id_perfil_sys(Perfil_sys fk_id_perfil_sys) {
		this.fk_id_perfil_sys = fk_id_perfil_sys;
	}



	public List<Log_sys> getLog_sys_s() {
		return Log_sys_s;
	}

	public void setLog_sys_s(List<Log_sys> log_sys_s) {
		Log_sys_s = log_sys_s;
	}
	
	
	
}
