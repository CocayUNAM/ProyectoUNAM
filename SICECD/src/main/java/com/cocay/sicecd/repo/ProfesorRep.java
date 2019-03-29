package com.cocay.sicecd.repo;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cocay.sicecd.model.Profesor;
import com.cocay.sicecd.model.Test;

@Repository
public interface ProfesorRep extends PagingAndSortingRepository<Profesor, Integer>{
	List<Profesor> findByCurp(String name);
	List<Profesor> findByRfc(String name);

}
