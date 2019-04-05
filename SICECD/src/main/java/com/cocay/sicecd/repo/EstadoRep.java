package com.cocay.sicecd.repo;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cocay.sicecd.model.Estado;
<<<<<<< HEAD
import com.cocay.sicecd.model.TestClass;
=======
>>>>>>> 425ced035122cf746cdd9679bee2569b13ebcbad

@Repository
public interface EstadoRep extends PagingAndSortingRepository<Estado, Integer>{
	List<Estado> findByNombre(String name);

}
