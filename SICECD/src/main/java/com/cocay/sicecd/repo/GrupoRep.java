package com.cocay.sicecd.repo;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cocay.sicecd.model.Grupo;
<<<<<<< HEAD
import com.cocay.sicecd.model.TestClass;
=======
>>>>>>> 425ced035122cf746cdd9679bee2569b13ebcbad

@Repository
public interface GrupoRep extends PagingAndSortingRepository<Grupo, Integer>{
	List<Grupo> findByClave(String name);

}
