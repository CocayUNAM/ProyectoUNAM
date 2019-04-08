package com.cocay.sicecd.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TestClass")
public class TestClass implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2898229381395977855L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "pass")
	private String pass;

	@Column(name = "rol")
	private String rol;
	 
	public TestClass() {
	}
	 
	public TestClass(String name) {
	  this.name = name;
	}
	 
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	@Override
	public String toString() {
	  return String.format("Test[id=%d, name='%s']", id, name);
	}
}
