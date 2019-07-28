package com.cocay.sicecd.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cocay.sicecd.model.Genero;

@Repository
public interface GeneroRep extends PagingAndSortingRepository<Genero, Integer>{
	//List<Genero> findByName(String name);

}
