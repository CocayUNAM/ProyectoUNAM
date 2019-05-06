package com.cocay.sicecd.dao;

import java.util.List;

import com.cocay.sicecd.model.Curso;

public interface CursoDao {
	public void insert(List<? extends Curso> cursos);
	  List<Curso> loadAllCursosBatch();
}
