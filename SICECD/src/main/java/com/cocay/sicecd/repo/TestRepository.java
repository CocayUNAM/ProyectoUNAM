package com.cocay.sicecd.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cocay.sicecd.model.Test;

public interface TestRepository extends CrudRepository<Test, Long>{
	List<Test> findByName(String name);
}