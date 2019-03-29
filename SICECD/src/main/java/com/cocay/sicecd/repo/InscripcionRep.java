package com.cocay.sicecd.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cocay.sicecd.model.Inscripcion;

@Repository
public interface InscripcionRep extends PagingAndSortingRepository<Inscripcion, Integer>{

}
