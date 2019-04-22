package com.cocay.sicecd.repo;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cocay.sicecd.model.TestClass;
import com.cocay.sicecd.model.Turno;

@Repository
public interface TurnoRep extends PagingAndSortingRepository<Turno, Integer>{
	List<Turno> findByNombre(String name);

}
