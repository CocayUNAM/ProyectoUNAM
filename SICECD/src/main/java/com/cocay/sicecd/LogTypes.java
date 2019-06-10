package com.cocay.sicecd;

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
	
	/*Accion de hacer login*/
	public static final String WEB_LOGIN = "WLIN00";
	/*Accion de hacer logout*/
	public static final String WEB_LOGOUT = "WLOT00";
	
	
}