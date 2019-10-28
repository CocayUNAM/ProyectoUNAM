package com.cocay.sicecd.dto;

public class InscripcionDto {
	
	String identificador;

	String idGrupo;
	
	String idProfesor;
	
	String calificacion;
	
	Boolean aprobado;
	
	String jsonG;	
	
	String jsonP;

	String calif;
	
	String jsonNombres;
	
	String temp_curso;

	public String getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(String idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getIdProfesor() {
		return idProfesor;
	}

	public void setIdProfesor(String idProfesor) {
		this.idProfesor = idProfesor;
	}

	public String getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(String calificacion) {
		this.calificacion = calificacion;
	}

	public boolean isAprobado() {
		return aprobado;
	}

	public void setAprobado(boolean aprobado) {
		this.aprobado = aprobado;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getCalif() {
		return calif;
	}

	public void setCalif(String calif) {
		this.calif = calif;
	}

	public String getJsonG() {
		return jsonG;
	}

	public void setJsonG(String jsonG) {
		this.jsonG = jsonG;
	}

	public String getJsonP() {
		return jsonP;
	}

	public void setJsonP(String jsonP) {
		this.jsonP = jsonP;
	}

	public Boolean getAprobado() {
		return aprobado;
	}

	public void setAprobado(Boolean aprobado) {
		this.aprobado = aprobado;
	}

	public String getJsonNombres() {
		return jsonNombres;
	}

	public void setJsonNombres(String jsonNombres) {
		this.jsonNombres = jsonNombres;
	}

	public String getTemp_curso() {
		return temp_curso;
	}

	public void setTemp_curso(String temp_curso) {
		this.temp_curso = temp_curso;
	}
	
	
}
