package com.cocay.sicecd.repo;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cocay.sicecd.model.Curso;

@Repository
public interface CursoRep extends PagingAndSortingRepository<Curso, Integer>{
	List<Curso> findByNombre(String name);

}
