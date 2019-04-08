package com.cocay.sicecd.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cocay.sicecd.model.TestClass;

public interface TestRepository extends CrudRepository<TestClass, Long>{
	List<TestClass> findByName(String name);
}