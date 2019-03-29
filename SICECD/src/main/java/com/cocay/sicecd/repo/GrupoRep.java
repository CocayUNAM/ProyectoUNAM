package com.cocay.sicecd.repo;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cocay.sicecd.model.Grupo;
import com.cocay.sicecd.model.Test;

@Repository
public interface GrupoRep extends PagingAndSortingRepository<Grupo, Integer>{
	List<Grupo> findByClave(String name);

}
