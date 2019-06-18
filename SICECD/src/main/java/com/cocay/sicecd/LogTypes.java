package com.cocay.sicecd;

import org.springframework.beans.factory.annotation.Autowired;

public final class LogTypes {
	
	private LogTypes() {
		// restrict instantiation
	}
	
	/*
	 *	Codigo: WLIN00
	 * 	Elemetos: WL IN 00
	 * 	Primer elemento: WL es un prefijo general para todas las acciones que impliquen login/logout
	 * 	Segundo elemento: IN descrive una subcategoria dentro de las acciones de logearse, en este caso iniciar seción
	 * 	Tercer elemento: 00 si fuere necesario agregar subdividir las acciones para agregar mas detalle 
	 * 						se puede hacer uso de estos consecutivos por ejemplo 00, 01, 02, 03, etc.
	 */
	
	/*Accion de hacer login exitoso*/
	public static final String WEB_LOGIN_SUCCESS = "WLIN00";
	/*Accion de hacer login fallido*/
	public static final String WEB_LOGIN_FAIL = "WLIN01";
	/*Accion de hacer logout*/
	public static final String WEB_LOGOUT = "WLOT00";
	/*Accion de hacer consulta*/
	public static final String CONSULTA_PROFESOR = "COPR00";
	/*Accion de hacer consulta*/
	public static final String CONSULTA_INCRIPCION = "COIN00";
	/*Accion de hacer carga bath incripcion*/
	public static final String CARGA_BATCH_INCRIPCION = "CABA00";
	/*Accion de hacer carga bath profesor*/
	public static final String CARGA_BATCH_PROFESOR = "CABA01";
	/*Accion de hacer carga bath grupo*/
	public static final String CARGA_BATCH_GRUPO = "CABA03";
	/*Accion de hacer consulta a WS de constancias (nuevas, nunca antes traidas)*/
	public static final String EXTRACCION_CONSTANCIAS_NUEVAS = "ECNU00";
	/*Accion de hacer consulta a WS de constancias (actualiza)*/
	public static final String EXTRACCION_CONSTANCIAS_ACTUALIZACION = "ECAC00";
	
	/***
	//Example
	@Autowired
	Logging log;
	
	public void login(){
		log.setTrace(LogTypes.WEB_LOGIN_SUCCESS);
	}
	
	public void consulta_profesor(){
		log.setTrace(LogTypes.CONSULTA_PROFESOR);
	}
	
	public void carga_batch(){
		log.setTrace(LogTypes.CARGA_BATCH_GRUPO, "Se cargaron 50 registros, no se cargaron 2.");
	}
	/***/
}