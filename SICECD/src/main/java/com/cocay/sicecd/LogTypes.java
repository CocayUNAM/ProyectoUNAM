package com.cocay.sicecd;

public final class LogTypes {
	
	private LogTypes() {
		// restrict instantiation
	}
	
	/*
	 *	Codigo: WLIN00
	 * 	Elemetos: WL IN 00
	 * 	Primer elemento: WL es un prefijo general para todas las acciones que impliquen login/logout
	 * 	Segundo elemento: IN descrive una subcategoria dentro de las acciones de logearse, en este caso iniciar sesión
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
	/*Error en Carga Batch de Profesor*/
	public static final String CARGA_WS_BATCH_ERROR_PROFESOR = "CWBE01";
	/*Error en Carga Batch de Curso*/
	public static final String CARGA_WS_BATCH_ERROR_CURSO = "CWBE02";
	/*Error en Carga Batch de Grupo*/
	public static final String CARGA_WS_BATCH_ERROR_GRUPO = "CWBE03";
	/*Error en Carga Batch de Inscripcion*/
	public static final String CARGA_WS_BATCH_ERROR_INSCRIPCION = "CWBE04";
	/*Accion de hacer consulta a WS de constancias (nuevas, nunca antes traidas)*/
	public static final String EXTRACCION_CONSTANCIAS_NUEVAS = "ECNU00";
	/*Accion de hacer consulta a WS de constancias (actualiza)*/
	public static final String EXTRACCION_CONSTANCIAS_ACTUALIZACION = "ECAC00";
	/*Accion de registrar un participante*/
	public static final String REGISTRAR_PARTICIPANTE = "REPR00";
	/*Accion de registrar un asesor*/
	public static final String REGISTRAR_ASESOR = "REPR01";
	/*Accion de registrar un curso*/
	public static final String REGISTRAR_CURSO = "RECU00";
	/*Accion de registrar un grupo*/
	public static final String REGISTRAR_GRUPO = "REGR00";
	/*Accion de registrar un inscripcion*/
	public static final String REGISTRAR_INSCRIPCION = "REIN00";
	/*Accion de modificar un participante*/
	public static final String MODIFICAR_PARTICIPANTE = "MOPR00";
	/*Accion de modificar un asesor*/
	public static final String MODIFICAR_ASESOR = "MOPR01";
	/*Accion de modificar un curso*/
	public static final String MODIFICAR_CURSO = "MOCU00";
	/*Accion de modificar un grupo*/
	public static final String MODIFICAR_GRUPO = "MOGR00";
	/*Accion de modificar una inscripcion*/
	public static final String MODIFICAR_INSCRIPCION = "MOIN00";
	/* Acción de consultar profesores*/
	public static final String CONSULTAR_PARTICIPANTE = "CNPR00";
	/* Acción de consultar inscripciones*/
	public static final String CONSULTAR_INSCRIPCION = "CNIN00";
	/* Acción de consultar cursos*/
	public static final String CONSULTAR_CURSO = "CNCU00";
	/* Acción de consultar grupos*/
	public static final String CONSULTAR_GRUPO = "CNGR00";
	/* Acción de exportar una consulta de profesores*/
	public static final String EXPORTAR_REPORTE_PARTICIPANTE = "EXPR00";
	/* Acción de exportar una consulta de inscripciones*/
	public static final String EXPORTAR_REPORTE_INSCRIPCION = "EXIN00";
	/* Acción de exportar una consulta de cursos*/
	public static final String EXPORTAR_REPORTE_CURSO = "EXCU00";
	/* Acción de exportar una consulta de grupos*/
	public static final String EXPORTAR_REPORTE_GRUPO = "EXGR00";
	/* Acción de exportar una consulta de grupos*/
	public static final String AGREGAR_PROFESOR = "AGPR00";
	/* Acción de exportar una consulta de grupos*/
	public static final String ALTA_USUARIO = "ALUS00";
	/* Acción de exportar una consulta de grupos*/
	public static final String ACTIVA_USUARIO = "ACUS00";
	/* Acción de exportar una consulta de grupos*/
	public static final String RECUPERA_PASSWORD = "RECO00";
	/* Acción de exportar una consulta de grupos*/
	public static final String EDITA_USUARIO= "EDUS00";
	/* Acción de exportar una consulta de grupos*/
	public static final String RENVIA_CAMBIO_CORREO= "RECC00";
	/* Acción de exportar una consulta de grupos*/
	public static final String RENVIA_CORREO_ACTIVACION= "RECA00";
	
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