package com.cocay.sicecd.repo;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cocay.sicecd.model.Genero;
import com.cocay.sicecd.model.TestClass;

@Repository
public interface GeneroRep extends PagingAndSortingRepository<Genero, Integer>{
	//List<Genero> findByName(String name);

}
