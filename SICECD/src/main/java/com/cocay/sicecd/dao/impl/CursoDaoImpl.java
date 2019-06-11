package com.cocay.sicecd.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.cocay.sicecd.dao.CursoDao;
import com.cocay.sicecd.model.Curso;
import com.cocay.sicecd.model.Tipo_curso;
import com.cocay.sicecd.repo.CursoRep;
import com.cocay.sicecd.repo.Tipo_cursoRep;

@Repository
public class CursoDaoImpl extends JdbcDaoSupport implements CursoDao {

	@Autowired
	DataSource dataSource;

	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}

	@Autowired
	CursoRep cursoRep;

	@Autowired
	private Tipo_cursoRep tpRep;

	@Override
	public void insert(List<? extends Curso> cursos) {
		String sql = "INSERT INTO curso " + "(clave, nombre, horas) VALUES (?, ?, ?)";
		getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Curso curso = cursos.get(i);
				ps.setString(1, curso.getClave());
				ps.setString(2, curso.getNombre());
				ps.setInt(3, curso.getHoras());
			}

			public int getBatchSize() {
				return cursos.size();
			}
		});
		// TODO Auto-generated method stub

	}

	@Override
	public List<Curso> loadAllCursosBatch() {
		// TODO Auto-generated method stub
		return null;
	}

}
