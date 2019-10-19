/*
Creación de usuario y base de datos
*/
CREATE USER "SICECD" WITH
	LOGIN
	NOSUPERUSER
	NOCREATEDB
	NOCREATEROLE
	INHERIT
	NOREPLICATION
	CONNECTION LIMIT -1
	PASSWORD '5Fda!vc%fsPZ0@';

CREATE DATABASE "SICECD"
    WITH 
    OWNER = "SICECD"
    ENCODING = 'UTF8'
    LC_COLLATE = 'Spanish_Spain.1252'
    LC_CTYPE = 'Spanish_Spain.1252'
    CONNECTION LIMIT = -1;

/*
Borrado de tablas
*/
DROP TABLE IF EXISTS Log_sys CASCADE;
DROP TABLE IF EXISTS Log_evento_sys CASCADE;
DROP TABLE IF EXISTS Usuario_sys CASCADE;
DROP TABLE IF EXISTS Estatus_usuario_sys CASCADE;
DROP TABLE IF EXISTS Perfil_sys CASCADE;
DROP TABLE IF EXISTS Grado_profesor CASCADE;
DROP TABLE IF EXISTS Turno CASCADE;
DROP TABLE IF EXISTS Genero CASCADE;
DROP TABLE IF EXISTS Estado CASCADE;
DROP TABLE IF EXISTS Inscripcion CASCADE;
DROP TABLE IF EXISTS Grupo CASCADE;
DROP TABLE IF EXISTS Curso CASCADE;
DROP TABLE IF EXISTS Profesor CASCADE;
DROP TABLE IF EXISTS Tipo_curso CASCADE;
DROP TABLE IF EXISTS url_ws CASCADE;
DROP TABLE IF EXISTS url_ws_curso CASCADE;
DROP TABLE IF EXISTS url_ws_inscripcion CASCADE;
DROP TABLE IF EXISTS url_ws_profesor CASCADE;
DROP TABLE IF EXISTS certificado CASCADE;
DROP TABLE IF EXISTS curso_grupos CASCADE;
DROP TABLE IF EXISTS estado_profesores CASCADE;
DROP TABLE IF EXISTS grupo_inscripciones CASCADE;
DROP TABLE IF EXISTS test_class CASCADE;
DROP TABLE IF EXISTS errores CASCADE;
DROP TABLE IF EXISTS batch_job_execution CASCADE;
DROP TABLE IF EXISTS batch_job_execution_context CASCADE;
DROP TABLE IF EXISTS batch_job_execution_params CASCADE;
DROP TABLE IF EXISTS batch_job_instance CASCADE;
DROP TABLE IF EXISTS batch_step_execution CASCADE;
DROP TABLE IF EXISTS batch_step_execution_context CASCADE;



/*
Llenado de tablas catalogo
*/
INSERT INTO public.perfil_sys(nombre) VALUES ('Administrador');
INSERT INTO public.perfil_sys(nombre) VALUES ('Consultas');

INSERT INTO public.estatus_usuario_sys(nombre) VALUES ('Activo');
INSERT INTO public.estatus_usuario_sys(nombre) VALUES ('Inactivo');

INSERT INTO public.tipo_curso(nombre) VALUES ('Curso');
INSERT INTO public.tipo_curso(nombre) VALUES ('Diplomado');
INSERT INTO public.tipo_curso(nombre) VALUES ('Especialidad');
INSERT INTO public.tipo_curso(nombre) values('Maestria');
INSERT INTO public.tipo_curso(nombre) values('Doctorado');
INSERT INTO public.tipo_curso(nombre) VALUES ('Sin definir');

INSERT INTO public.turno(nombre) VALUES ('Matutino');
INSERT INTO public.turno(nombre) VALUES ('Vepertino');
INSERT INTO public.turno(nombre) VALUES ('Completo');
INSERT INTO public.turno(nombre) VALUES('Sin definir');

INSERT INTO public.grado_profesor(nombre) VALUES ('Lic.');
INSERT INTO public.grado_profesor(nombre) VALUES ('Esp.');
INSERT INTO public.grado_profesor(nombre) VALUES ('Mtr.');
INSERT INTO public.grado_profesor(nombre) VALUES ('Doc.');
INSERT INTO public.grado_profesor(nombre) VALUES ('Sin definir');

INSERT INTO public.genero(genero) VALUES ('Masculino');
INSERT INTO public.genero(genero) VALUES ('Femenino');
INSERT INTO public.genero(genero) VALUES('Sin definir');

INSERT INTO public.estado(nombre) VALUES ('Aguascalientes');
INSERT INTO public.estado(nombre) VALUES ('Baja California');
INSERT INTO public.estado(nombre) VALUES ('Baja California Sur');
INSERT INTO public.estado(nombre) VALUES ('Campeche');
INSERT INTO public.estado(nombre) VALUES ('Ciudad de México');
INSERT INTO public.estado(nombre) VALUES ('Coahuila');
INSERT INTO public.estado(nombre) VALUES ('Colima');
INSERT INTO public.estado(nombre) VALUES ('Chiapas');
INSERT INTO public.estado(nombre) VALUES ('Chihuahua');
INSERT INTO public.estado(nombre) VALUES ('Durango');
INSERT INTO public.estado(nombre) VALUES ('Estado de México');
INSERT INTO public.estado(nombre) VALUES ('Guanajuato');
INSERT INTO public.estado(nombre) VALUES ('Guerrero');
INSERT INTO public.estado(nombre) VALUES ('Hidalgo');
INSERT INTO public.estado(nombre) VALUES ('Jalisco');
INSERT INTO public.estado(nombre) VALUES ('Michoacán');
INSERT INTO public.estado(nombre) VALUES ('Morelos');
INSERT INTO public.estado(nombre) VALUES ('Nayarit');
INSERT INTO public.estado(nombre) VALUES ('Nuevo León');
INSERT INTO public.estado(nombre) VALUES ('Oaxaca');
INSERT INTO public.estado(nombre) VALUES ('Puebla');
INSERT INTO public.estado(nombre) VALUES ('Querétaro');
INSERT INTO public.estado(nombre) VALUES ('Quintana Roo');
INSERT INTO public.estado(nombre) VALUES ('San Luis Potosí');
INSERT INTO public.estado(nombre) VALUES ('Sinaloa');
INSERT INTO public.estado(nombre) VALUES ('Sonora');
INSERT INTO public.estado(nombre) VALUES ('Tabasco');
INSERT INTO public.estado(nombre) VALUES ('Tamaulipas');
INSERT INTO public.estado(nombre) VALUES ('Tlaxcala');
INSERT INTO public.estado(nombre) VALUES ('Veracruz');
INSERT INTO public.estado(nombre) VALUES ('Yucatán');
INSERT INTO public.estado(nombre) VALUES ('Zacatecas');
INSERT INTO public.estado(nombre) VALUES ('Sin definir');
INSERT INTO public.estado(nombre) VALUES ('No definido');
COMMIT;

INSERT INTO Log_evento_sys (pk_id_log_evento_sys, nombre) VALUES ('WLIN00', 'Login exitoso');
INSERT INTO Log_evento_sys (pk_id_log_evento_sys, nombre) VALUES ('WLIN01', 'Login fallido');
INSERT INTO Log_evento_sys (pk_id_log_evento_sys, nombre) VALUES ('WLOT00', 'Logout');
INSERT INTO Log_evento_sys (pk_id_log_evento_sys, nombre) VALUES ('ECNU00', 'Consulta a WS de constancias (nuevas, nunca antes traidas)');
INSERT INTO Log_evento_sys (pk_id_log_evento_sys, nombre) VALUES ('ECAC00', 'Consulta a WS de constancias (actualiza)');
INSERT INTO Log_evento_sys (pk_id_log_evento_sys, nombre) VALUES ('REPR00', 'Registrar un participante');
INSERT INTO Log_evento_sys (pk_id_log_evento_sys, nombre) VALUES ('REPR01', 'Registrar un asesor');
INSERT INTO Log_evento_sys (pk_id_log_evento_sys, nombre) VALUES ('RECU00', 'Registrar un curso');
INSERT INTO Log_evento_sys (pk_id_log_evento_sys, nombre) VALUES ('REGR00', 'Registrar un grupo');
INSERT INTO Log_evento_sys (pk_id_log_evento_sys, nombre) VALUES ('REIN00', 'Registrar un inscripción');
INSERT INTO Log_evento_sys (pk_id_log_evento_sys, nombre) VALUES ('MOPR00', 'Modificar un participante');
INSERT INTO Log_evento_sys (pk_id_log_evento_sys, nombre) VALUES ('MOPR01', 'Modificar un asesor');
INSERT INTO Log_evento_sys (pk_id_log_evento_sys, nombre) VALUES ('MOCU00', 'Modificar un curso');
INSERT INTO Log_evento_sys (pk_id_log_evento_sys, nombre) VALUES ('MOGR00', 'Modificar un grupo');
INSERT INTO Log_evento_sys (pk_id_log_evento_sys, nombre) VALUES ('MOIN00', 'Modificar una inscripción');
INSERT INTO Log_evento_sys (pk_id_log_evento_sys, nombre) VALUES ('CNPR00', 'Consultar profesores');
INSERT INTO Log_evento_sys (pk_id_log_evento_sys, nombre) VALUES ('CNIN00', 'Consultar inscripciones');
INSERT INTO Log_evento_sys (pk_id_log_evento_sys, nombre) VALUES ('CNCU00', 'Consultar cursos');
INSERT INTO Log_evento_sys (pk_id_log_evento_sys, nombre) VALUES ('CNGR00', 'Consultar grupos');
INSERT INTO Log_evento_sys (pk_id_log_evento_sys, nombre) VALUES ('EXPR00', 'Exportar una consulta de profesores');
INSERT INTO Log_evento_sys (pk_id_log_evento_sys, nombre) VALUES ('EXIN00', 'Exportar una consulta de inscripciones');
INSERT INTO Log_evento_sys (pk_id_log_evento_sys, nombre) VALUES ('EXCU00', 'Exportar una consulta de cursos');
INSERT INTO Log_evento_sys (pk_id_log_evento_sys, nombre) VALUES ('ALUS00', 'Alta de usuario');
INSERT INTO Log_evento_sys (pk_id_log_evento_sys, nombre) VALUES ('ACUS00', 'Activa usuario');
INSERT INTO Log_evento_sys (pk_id_log_evento_sys, nombre) VALUES ('RECO00', 'Renvia Contrasena');
INSERT INTO Log_evento_sys (pk_id_log_evento_sys, nombre) VALUES ('EDUS00', 'Edita usuario');
INSERT INTO Log_evento_sys (pk_id_log_evento_sys, nombre) VALUES ('RECC00', 'Renvia cambio correo');
INSERT INTO Log_evento_sys (pk_id_log_evento_sys, nombre) VALUES ('RECA00', 'Renvia activacion de cuenta');
INSERT INTO Log_evento_sys (pk_id_log_evento_sys, nombre) VALUES ('CWBE01', 'Error en Carga WS Batch de Profesor');
INSERT INTO Log_evento_sys (pk_id_log_evento_sys, nombre) VALUES ('CWBE02', 'Error en Carga WS Batch de Curso');
INSERT INTO Log_evento_sys (pk_id_log_evento_sys, nombre) VALUES ('CWBE03', 'Error en Carga WS Batch de Grupo');
INSERT INTO Log_evento_sys (pk_id_log_evento_sys, nombre) VALUES ('CWBE04', 'Error en Carga WS Batch de Insripción');
COMMIT;

/*
Datos de prueba
*/
INSERT INTO public.usuario_sys(rfc, password, correo, nombre,  apellido_paterno, apellido_materno, confirmacion, codigo, confirmacioncorreo,codigo_correo,correocambio,codigorecupera, confirmarecupera, fk_id_estatus_usuario_sys, fk_id_perfil_sys) 
VALUES ('AAAA801201SN9', '$2a$10$.PYYPU6zW9cN/lLRbiM3VePaDcKNjfp4tNMcCPJ3/G51dlg9N8jhG', 'yuliana.olvera@cocaytechnologies.com','Yuliana', 'Olvera', 'Osorno', 'false',123,'false',789,'',101,'false',1, 1);--123456789
COMMIT;

INSERT INTO public.usuario_sys(rfc, nombre, apellido_paterno, password, correo, fk_id_estatus_usuario_sys, fk_id_perfil_sys)
VALUES ('BBBB801201SN9', 'Usuario', 'Test', '$2a$10$8n2o/aSS96.kisZBBMzdM.BwOryAWdFwFlsjIWFvIkObYJ8Na/2O2', 'benitez@unam.mx', 1, 2);--1234567890
COMMIT;

INSERT INTO public.profesor(nombre, apellido_paterno, apellido_materno, rfc, correo, fk_id_estado, id_genero, fk_id_turno, fk_id_grado_profesor) VALUES ('Raul', 'Lopez', 'Diaz', 'LODR800505MMM', 'raul@unam.mx', 1, 1, 1, 1);
INSERT INTO public.profesor(nombre, apellido_paterno, apellido_materno, rfc, correo, fk_id_estado, id_genero, fk_id_turno, fk_id_grado_profesor) VALUES ('Maria', 'Martinez', 'Ordaz', 'MAOM800505MMM', 'maria@unam.mx', 1, 1, 1, 1);
INSERT INTO public.profesor(nombre, apellido_paterno, apellido_materno, rfc, correo, fk_id_estado, id_genero, fk_id_turno, fk_id_grado_profesor) VALUES ('Irma', 'Villa', 'Salinas', 'VISI800505MMM', 'irma@unam.mx', 1, 1, 1, 1);
INSERT INTO public.profesor(nombre, apellido_paterno, apellido_materno, rfc, correo, fk_id_estado, id_genero, fk_id_turno, fk_id_grado_profesor) VALUES ('Gerardo', 'Gutierrez', 'Pliego', 'GUPG800505MMM', 'gerardo@unam.mx', 1, 1, 1, 1);
INSERT INTO public.profesor(nombre, apellido_paterno, apellido_materno, rfc, curp, correo) VALUES ('ELENA', 'SOTO', 'RUBIO', 'SORE770909', 'SORE770909MCHTBL07', 'elesoru9@hotmail.com');
INSERT INTO public.profesor(nombre, apellido_paterno, apellido_materno, rfc, curp, correo) VALUES ('JORGE ALBERTO', 'NARVAEZ', 'MENDEZ', 'NAMJ770523', 'NAMJ770523HQTRNR07', 'georgenarvaez@hotmail.com');
INSERT INTO public.profesor(nombre, apellido_paterno, apellido_materno, rfc, curp, correo) VALUES ('JORGE JULIAN', 'REPRIETO', 'RODRIGUEZ', 'RERJ841118', 'RERJ841118HSRPDR03', 'jorgereprietorodriguez@gmail.com');
INSERT INTO public.profesor(nombre, apellido_paterno, apellido_materno, rfc, curp, correo) VALUES ('ALEJANDRO', 'SANCHEZ', 'RODRIGUEZ', 'SARA710731', 'SARA710731HDFNDL06', 'alex_epoem@hotmail.com');
INSERT INTO public.profesor (nombre, apellido_paterno, apellido_materno, rfc, correo, fk_id_estado, id_genero, fk_id_turno, fk_id_grado_profesor) VALUES ('Lourdes', 'Diaz', 'Diaz', 'LBDI800505MMM', 'matyap59@hotmail.com', 1, 1, 1, 1);
INSERT INTO public.profesor (nombre, apellido_paterno, apellido_materno, rfc, correo, fk_id_estado, id_genero, fk_id_turno, fk_id_grado_profesor) VALUES ('Maria', 'Diaz', 'Diaz', 'MBDI800505MMM', 'mahalymf@hotmail.com', 1, 1, 1, 1);
INSERT INTO public.profesor (nombre, apellido_paterno, apellido_materno, rfc, correo, fk_id_estado, id_genero, fk_id_turno, fk_id_grado_profesor) VALUES ('Ramiro', 'Diaz', 'Diaz', 'RBDI800505MMM', 'murcielagoblue@yahoo.com.mx', 1, 1, 1, 1);
COMMIT;

INSERT INTO public.curso(clave, nombre, fk_id_tipo_curso, horas) VALUES ('A001', 'Biologia 1', 1, 40);
INSERT INTO public.curso(clave, nombre, fk_id_tipo_curso, horas) VALUES ('A002', 'Biologia 2', 1, 40);
INSERT INTO public.curso(clave, nombre, fk_id_tipo_curso, horas) VALUES ('A003', 'Biologia 3', 1, 40);
INSERT INTO public.curso(clave, nombre, fk_id_tipo_curso, horas) VALUES ('A004', 'Biologia 4', 1, 40);
INSERT INTO public.curso(clave, nombre, fk_id_tipo_curso, horas) VALUES ('B001', 'Matematicas 1', 1, 40);
INSERT INTO public.curso(clave, nombre, fk_id_tipo_curso, horas) VALUES ('B002', 'Matematicas 2', 1, 40);
INSERT INTO public.curso(clave, nombre, fk_id_tipo_curso, horas) VALUES ('B003', 'Matematicas 3', 1, 40);
COMMIT;

INSERT INTO public.grupo(fk_id_curso, clave, fecha_inicio, fecha_fin, fk_id_profesor) VALUES (1, 'A001', TIMESTAMP '2019-01-05 00:00:00', TIMESTAMP '2019-05-05 00:00:00', 1);
INSERT INTO public.grupo(fk_id_curso, clave, fecha_inicio, fecha_fin, fk_id_profesor) VALUES (1, 'A002', TIMESTAMP '2019-01-05 00:00:00', TIMESTAMP '2019-05-05 00:00:00', 2);
INSERT INTO public.grupo(fk_id_curso, clave, fecha_inicio, fecha_fin, fk_id_profesor) VALUES (5, 'A003', TIMESTAMP '2019-01-05 00:00:00', TIMESTAMP '2019-05-05 00:00:00', 3);
INSERT INTO public.grupo(fk_id_curso, clave, fecha_inicio, fecha_fin, fk_id_profesor) VALUES (7, 'A004', TIMESTAMP '2019-01-05 00:00:00', TIMESTAMP '2019-05-05 00:00:00', 1);
COMMIT;

INSERT INTO public.inscripcion(aprobado, calif, fk_id_grupo, fk_id_profesor) VALUES (true, 6, 1, 1);
INSERT INTO public.inscripcion(aprobado, calif, fk_id_grupo, fk_id_profesor) VALUES (true, 9, 1, 2);
INSERT INTO public.inscripcion(aprobado, calif, fk_id_grupo, fk_id_profesor) VALUES (false, 5, 2, 3);
INSERT INTO public.inscripcion(aprobado, calif, fk_id_grupo, fk_id_profesor) VALUES (true, 9, 1, 4);
INSERT INTO public.inscripcion(aprobado, calif, fk_id_grupo, fk_id_profesor) VALUES (true, 9, 1, 5);
INSERT INTO public.inscripcion(aprobado, calif, fk_id_grupo, fk_id_profesor) VALUES (true, 9, 1, 6);
INSERT INTO public.inscripcion(aprobado, calif, fk_id_grupo, fk_id_profesor) VALUES (true, 9, 1, 7);
INSERT INTO public.inscripcion(aprobado, calif, fk_id_grupo, fk_id_profesor) VALUES (true, 9, 1, 8);
INSERT INTO public.inscripcion(fk_id_grupo, fk_id_profesor) VALUES (4, 6);
INSERT INTO public.inscripcion(fk_id_grupo, fk_id_profesor) VALUES (4, 7);
INSERT INTO public.inscripcion(fk_id_grupo, fk_id_profesor) VALUES (4, 5);
INSERT INTO public.inscripcion(fk_id_grupo, fk_id_profesor) VALUES (2, 8);
INSERT INTO public.inscripcion(fk_id_grupo, fk_id_profesor) VALUES (2, 9);
INSERT INTO public.inscripcion(fk_id_grupo, fk_id_profesor) VALUES (2, 10);
INSERT INTO public.inscripcion(fk_id_grupo, fk_id_profesor) VALUES (2, 11);
COMMIT;

INSERT INTO public.url_ws(activa, url, varios) VALUES(false, 'http://127.0.0.1/mod/simplecertificate/wscertificado.php', false);
INSERT INTO public.url_ws(activa, url, varios) VALUES(false, 'http://127.0.0.1/mod/simplecertificate/wscertificados.php', true);
COMMIT;

INSERT INTO public.url_ws_curso(activa, nombre, url) VALUES(false, 'Moodle Test 1', 'http://127.0.0.1/WB/api/users/courses.php');
INSERT INTO public.url_ws_inscripcion(activa, nombre, url) VALUES(false, 'Moodle Test 1', 'http://127.0.0.1/WB/api/users/grades.php');
INSERT INTO public.url_ws_profesor(activa, nombre, url) VALUES(false, 'Moodle Test 1', 'http://127.0.0.1/WB/api/users/read.php');
COMMIT;

INSERT INTO public.url_ws(activa, url, varios) VALUES(false, 'http://unamedu.serveftp.com:8585/mod/simplecertificate/wscertificado.php', false);
INSERT INTO public.url_ws(activa, url, varios) VALUES(true, 'http://unamedu.serveftp.com:8585/mod/simplecertificate/wscertificados.php', true);
COMMIT;

INSERT INTO public.url_ws_curso(activa, nombre, url) VALUES(true, 'Moodle Test 1', 'http://unamedu.serveftp.com:8585/WB/api/users/courses.php');
INSERT INTO public.url_ws_inscripcion(activa, nombre, url) VALUES(true, 'Moodle Test 1', 'http://unamedu.serveftp.com:8585/WB/api/users/grades.php');
INSERT INTO public.url_ws_profesor(activa, nombre, url) VALUES(true, 'Moodle Test 1', 'http://unamedu.serveftp.com:8585/WB/api/users/read.php');
COMMIT;

/*
Cambio de propietario
*/
ALTER TABLE public.curso OWNER to "SICECD";
ALTER TABLE public.estado OWNER to "SICECD";
ALTER TABLE public.estatus_usuario_sys OWNER to "SICECD";
ALTER TABLE public.genero OWNER to "SICECD";
ALTER TABLE public.grado_profesor OWNER to "SICECD";
ALTER TABLE public.grupo OWNER to "SICECD";
ALTER TABLE public.inscripcion OWNER to "SICECD";
ALTER TABLE public.log_evento_sys OWNER to "SICECD";
ALTER TABLE public.log_sys OWNER to "SICECD";
ALTER TABLE public.perfil_sys OWNER to "SICECD";
ALTER TABLE public.profesor OWNER to "SICECD";
ALTER TABLE public.tipo_curso OWNER to "SICECD";
ALTER TABLE public.turno OWNER to "SICECD";
ALTER TABLE public.usuario_sys OWNER to "SICECD";
ALTER TABLE public.Url_ws OWNER to "SICECD";
ALTER TABLE public.url_ws_curso OWNER to "SICECD";
ALTER TABLE public.url_ws_inscripcion OWNER to "SICECD";
ALTER TABLE public.url_ws_profesor OWNER to "SICECD";
ALTER TABLE public.Log_sys OWNER to "SICECD";
ALTER TABLE public.Log_evento_sys OWNER to "SICECD";
ALTER TABLE public.certificado OWNER to "SICECD";
ALTER TABLE public.Tipo_curso OWNER to "SICECD";
ALTER TABLE public.Estatus_usuario_sys OWNER to "SICECD";
ALTER TABLE public.batch_job_execution OWNER to "SICECD";
ALTER TABLE public.batch_job_execution_context OWNER to "SICECD";
ALTER TABLE public.batch_job_execution_params OWNER to "SICECD";
ALTER TABLE public.batch_job_instance OWNER to "SICECD";
ALTER TABLE public.batch_step_execution OWNER to "SICECD";
ALTER TABLE public.batch_step_execution_context OWNER to "SICECD";


/*
Borrado de los datos de las tablas
*/
TRUNCATE TABLE public.inscripcion, public.grupo, public.curso, public.profesor, public.estado, 
public.genero, public.grado_profesor, public.turno, public.tipo_curso, public.estado_profesores,
public.estatus_usuario_sys_usuarios, public.test_class, public.log_sys, public.usuario_sys, public.estatus_usuario_sys, public.perfil_sys
RESTART IDENTITY;
COMMIT;