package com.cocay.sicecd.repo;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cocay.sicecd.model.Test;
import com.cocay.sicecd.model.Usuario_sys;

@Repository
public interface Usuario_sysRep extends PagingAndSortingRepository<Usuario_sys, Integer>{
	List<Usuario_sys> findByRfc(String name);
	List<Usuario_sys> findByCorreo(String name);


}
