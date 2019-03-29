package com.cocay.sicecd.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "Log_evento_sys")
public class Log_evento_sys {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pk_id_log_evento_sys")
	int pk_id_log_evento_sys;
	
	String nombre;

	@OneToMany(mappedBy = "fk_id_log_evento_sys", targetEntity=Log_sys.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Log_sys> Log_sys_s = new ArrayList<>();
}
