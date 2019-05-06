package com.cocay.sicecd.service.step;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

import com.cocay.sicecd.dao.CursoDao;
import com.cocay.sicecd.model.Curso;
import com.cocay.sicecd.repo.CursoRep;

 
public class Writer implements ItemWriter<Curso> {
	 
	  private final CursoDao cursoDao;
	  
	  public Writer(CursoDao cursoDao) {
	    this.cursoDao = cursoDao;
	  }
	 
	  @Override
	  public void write(List<? extends Curso> cursos) throws Exception {
		  cursoDao.insert(cursos);
	  }
}