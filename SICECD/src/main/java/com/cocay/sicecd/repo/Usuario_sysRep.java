package com.cocay.sicecd.repo;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD
import com.cocay.sicecd.model.TestClass;
=======
>>>>>>> 425ced035122cf746cdd9679bee2569b13ebcbad
import com.cocay.sicecd.model.Usuario_sys;

@Repository
public interface Usuario_sysRep extends PagingAndSortingRepository<Usuario_sys, Integer>{
	List<Usuario_sys> findByRfc(String name);
	List<Usuario_sys> findByCorreo(String name);


}
