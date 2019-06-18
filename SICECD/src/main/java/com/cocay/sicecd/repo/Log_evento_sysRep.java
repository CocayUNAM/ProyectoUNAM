package com.cocay.sicecd.repo;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cocay.sicecd.model.Log_evento_sys;
import com.cocay.sicecd.model.TestClass;

@Repository
public interface Log_evento_sysRep extends PagingAndSortingRepository<Log_evento_sys, String>{
	List<Log_evento_sys> findByNombre(String name);

}
