package com.cocay.sicecd.repo;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cocay.sicecd.model.Curso;
import com.cocay.sicecd.model.TestClass;

@Repository
public interface CursoRep extends PagingAndSortingRepository<Curso, Integer>{
	Curso findByNombre(String name);

}
