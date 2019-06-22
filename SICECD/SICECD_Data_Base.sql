/*
Autor: Juan Carlos Hernández de Anda
Fecha: 25/03/2019
Accion: Creacion del usuario principal de la base de datos
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

/*
Autor: Juan Carlos Hernández de Anda
Fecha: 25/03/2019
Accion: Creacion de la Base de Datos
*/
CREATE DATABASE "SICECD"
    WITH 
    OWNER = "SICECD"
    ENCODING = 'UTF8'
    LC_COLLATE = 'Spanish_Spain.1252'
    LC_CTYPE = 'Spanish_Spain.1252'
    CONNECTION LIMIT = -1;

/*
Autor: Juan Carlos Hernández de Anda
Fecha: 25/03/2019
Accion: Creacion de tabla de prueba
*/
CREATE TABLE public.test
(
    id integer NOT NULL DEFAULT nextval('test_id_seq'::regclass),
    name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT test_pk PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.test
    OWNER to "SICECD";

/*
Autor: Juan Carlos Hernández de Anda
Fecha: 25/03/2019
Accion: Llenado de tabla de prueba
*/
INSERT INTO public.test(name) VALUES ('Ciencias');
INSERT INTO public.test(name) VALUES ('Ingenieria');
INSERT INTO public.test(name) VALUES ('Contaduria');


/*
Autor: Héctos Santaella Marin Ciencias
Fecha: 27/03/2019
Accion: Creación de tablas: Log_sys, Log_evento_sys, Usuario_sys, Estatus_usuario_sys,
Perfil_sys, Grado_profesor, Turno, Genero, Estado, Inscripcion, Grupo, Curso, Profesor
*/

CREATE TABLE Usuario_sys(
  pk_id_usuario_sys  SERIAL PRIMARY KEY,
  rfc CHAR(13) UNIQUE,
  password VARCHAR(250) NOT NULL,
  correo  VARCHAR(150) NOT NULL,
  nombre VARCHAR(150) ,
  apellido_paterno VARCHAR(250),
  apellido_materno VARCHAR(250),
  fk_id_estatus_usuario_sys INTEGER NOT NULL,
  fk_id_perfil_sys INTEGER NOT NULL);
CREATE TABLE Perfil_sys(
  pk_id_perfil_sys SERIAL PRIMARY KEY,
  nombre VARCHAR(150) NOT NULL
);
CREATE TABLE Estatus_usuario_sys(
  pk_estatus_usuario_sys SERIAL PRIMARY KEY,
  nombre VARCHAR(50) NOT NULL
);
CREATE TABLE Log_sys(
  pk_id_log_sys SERIAL PRIMARY KEY,
  ip CHAR(12) NOT NULL,
  hora TIMESTAMP NOT NULL,
  fk_id_usuario_sys INTEGER NOT NULL,
  fk_id_log_evento_sys INTEGER NOT NULL
);
CREATE TABLE Log_evento_sys(
  pk_id_log_evento_sys SERIAL PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL
);

--tablas

CREATE TABLE Profesor(
  pk_id_profesor SERIAL PRIMARY KEY,
  nombre VARCHAR(250) NOT NULL,
  apellido_paterno VARCHAR(250) NOT NULL,
  apellido_materno VARCHAR(250),
  rfc CHAR(13) UNIQUE,
  curp CHAR(18) UNIQUE,
  correo VARCHAR(100) NOT NULL,
  telefono VARCHAR(50),
  fk_id_estado INTEGER,
  ciudad_localidad VARCHAR(250),
  id_genero INTEGER,
  plantel VARCHAR(250),
  clave_plantel VARCHAR(7),
  fk_id_turno INTEGER,
  fk_id_grado_profesor INTEGER,
  ocupacion VARCHAR(250),
  curriculum VARCHAR(250)
);
CREATE TABLE Curso(
  pk_id_curso SERIAL PRIMARY KEY,
  clave CHAR(7) UNIQUE NOT NULL,
  nombre VARCHAR(250) NOT NULL,
  fk_id_tipo_curso INTEGER,
  horas INTEGER
);
CREATE TABLE Grupo(
  pk_id_grupo SERIAL PRIMARY KEY,
  fk_id_curso INTEGER NOT NULL,
  clave VARCHAR(7) NOT NULL,
  fecha_inicio TIMESTAMP NOT NULL,
  fecha_fin TIMESTAMP NOT NULL
);

CREATE TABLE Inscripcion(
  pk_id_inscripcion SERIAL PRIMARY KEY,
  fk_id_grupo INTEGER NOT NULL,
  fk_id_profesor INTEGER NOT NULL
);

CREATE TABLE Estado(
  pk_id_estado SERIAL PRIMARY KEY,
  nombre VARCHAR(50) NOT NULL
);
CREATE TABLE Genero(
  pk_id_genero SERIAL PRIMARY KEY,
  genero VARCHAR(30)
);
CREATE TABLE Turno(
  pk_id_turno SERIAL PRIMARY KEY,
  nombre VARCHAR(30)
);
CREATE TABLE Grado_profesor(
  pk_id_grado_profesor SERIAL PRIMARY KEY,
  nombre VARCHAR(30)
);
/*
Autor: Juan Carlos Hernández de Anda
Fecha: 25/03/2019
Accion: Creacion de la tabla Tipo_curso
*/

CREATE TABLE Tipo_curso(
  pk_id_tipo_curso SERIAL PRIMARY KEY,
  nombre VARCHAR(70)
);

/*
Autor: Juan Carlos Hernández de Anda
Fecha: 01/04/2019
Accion: Corrección de llave foranea
*/
--ALTER TABLE Curso DROP CONSTRAINT curso_fk_id_tipo_curso_fkey;
ALTER TABLE Curso ADD FOREIGN KEY(fk_id_tipo_curso) REFERENCES Tipo_curso (pk_id_tipo_curso) ON DELETE CASCADE;

ALTER TABLE Usuario_sys ADD FOREIGN KEY(fk_id_estatus_usuario_sys) REFERENCES Estatus_usuario_sys (pk_estatus_usuario_sys ) ON DELETE CASCADE;
ALTER TABLE Usuario_sys ADD FOREIGN KEY(fk_id_perfil_sys) REFERENCES Perfil_sys (pk_id_perfil_sys ) ON DELETE CASCADE;
ALTER TABLE Log_sys ADD FOREIGN KEY(fk_id_usuario_sys) REFERENCES Usuario_sys (pk_id_usuario_sys ) ON DELETE CASCADE;
ALTER TABLE Log_sys ADD FOREIGN KEY(fk_id_log_evento_sys) REFERENCES Log_evento_sys (pk_id_log_evento_sys ) ON DELETE CASCADE;
ALTER TABLE Profesor ADD FOREIGN KEY(fk_id_estado) REFERENCES Estado (pk_id_estado ) ON DELETE CASCADE;
ALTER TABLE Profesor ADD FOREIGN KEY(fk_id_turno) REFERENCES Turno (pk_id_turno ) ON DELETE CASCADE;
ALTER TABLE Profesor ADD FOREIGN KEY(fk_id_grado_profesor) REFERENCES Grado_profesor (pk_id_grado_profesor) ON DELETE CASCADE;
ALTER TABLE Grupo ADD FOREIGN KEY(fk_id_curso) REFERENCES Curso (pk_id_curso ) ON DELETE CASCADE;
ALTER TABLE Inscripcion ADD FOREIGN KEY(fk_id_grupo) REFERENCES Grupo (pk_id_grupo ) ON DELETE CASCADE;
ALTER TABLE Inscripcion ADD FOREIGN KEY(fk_id_profesor) REFERENCES Profesor (pk_id_profesor ) ON DELETE CASCADE;
ALTER TABLE Inscripcion ADD calificacion VARCHAR(5);

--DROP TABLE Log_sys;
--DROP TABLE Log_evento_sys;
--DROP TABLE Usuario_sys;
--DROP TABLE Estatus_usuario_sys;
--DROP TABLE Perfil_sys;
--DROP TABLE Grado_profesor;
--DROP TABLE Turno;
--DROP TABLE Genero;
--DROP TABLE Estado;
--DROP TABLE Inscripcion;
--DROP TABLE Grupo;
--DROP TABLE Curso;
--DROP TABLE Profesor;
--DROP TABLE Tipo_curso;




/*
Autor: Juan Carlos Hernández de Anda
Fecha: 25/03/2019
Accion: Creacion de la tabla Tipo_curso
*/
ALTER TABLE Curso DROP CONSTRAINT curso_fk_id_tipo_curso_fkey;
ALTER TABLE Curso ADD FOREIGN KEY(fk_id_tipo_curso) REFERENCES Tipo_curso (pk_id_tipo_curso) ON DELETE CASCADE;

CREATE TABLE Tipo_curso(
  pk_id_tipo_curso SERIAL PRIMARY KEY,
  nombre VARCHAR(70)
);
ALTER TABLE Curso ADD FOREIGN KEY(fk_id_tipo_curso) REFERENCES Estado (pk_id_estado ) ON DELETE CASCADE;

--DROP TABLE Tipo_curso;

/*
Autor: Juan Carlos Hernández de Anda
Fecha: 10/04/2019
Accion: Creacion de datos de los catalogos y datos de prueba
*/
TRUNCATE TABLE public.inscripcion, public.grupo, public.curso, public.profesor, public.estado, 
public.genero, public.grado_profesor, public.turno, public.tipo_curso, public.estado_profesores,
public.estatus_usuario_sys_usuarios, public.test_class, public.log_sys, public.usuario_sys, public.estatus_usuario_sys, public.perfil_sys
RESTART IDENTITY;
COMMIT;

INSERT INTO public.perfil_sys(nombre) VALUES ('Administrador');
INSERT INTO public.perfil_sys(nombre) VALUES ('Consultas');

INSERT INTO public.estatus_usuario_sys(nombre) VALUES ('Activo');
INSERT INTO public.estatus_usuario_sys(nombre) VALUES ('Inactivo');

INSERT INTO public.usuario_sys(rfc, password, correo, nombre,  apellido_paterno, apellido_materno, confirmacion, codigo, confirmacioncorreo,codigo_correo,correocambio,codigorecupera, confirmarecupera, fk_id_estatus_usuario_sys, fk_id_perfil_sys) 
VALUES ('AAAA801201SN9', '$2a$10$.PYYPU6zW9cN/lLRbiM3VePaDcKNjfp4tNMcCPJ3/G51dlg9N8jhG', 'francisco3122151@gmail.com','franki', 'mcs', 'panki', 'false',123,'false',789,'',101,'false',1, 1);--123456789



INSERT INTO public.tipo_curso(nombre) VALUES ('Curso');
INSERT INTO public.tipo_curso(nombre) VALUES ('Diplomado');
INSERT INTO public.tipo_curso(nombre) VALUES ('Especialidad');

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
COMMIT;

INSERT INTO public.usuario_sys(rfc, password, correo, nombre,  apellido_paterno, apellido_materno, confirmacion, codigo, confirmacioncorreo,codigo_correo,correocambio,codigorecupera, confirmarecupera, fk_id_estatus_usuario_sys, fk_id_perfil_sys) 
VALUES ('AAAA801201SN9', '$2a$10$.PYYPU6zW9cN/lLRbiM3VePaDcKNjfp4tNMcCPJ3/G51dlg9N8jhG', 'francisco3122151@gmail.com','franki', 'mcs', 'panki', 'false',123,'false',789,'',101,'false',1, 1);--123456789

INSERT INTO public.log_evento_sys(Log_evento_sys, nombre) VALUES ('prueba', 'algunNombre');




INSERT INTO public.usuario_sys(rfc, password, correo, fk_id_estatus_usuario_sys, fk_id_perfil_sys) VALUES ('BBBB801201SN9', '$2a$10$8n2o/aSS96.kisZBBMzdM.BwOryAWdFwFlsjIWFvIkObYJ8Na/2O2', 'benitez@unam.mx', 1, 2);--1234567890

INSERT INTO public.profesor(nombre, apellido_paterno, apellido_materno, rfc, correo, fk_id_estado, id_genero, fk_id_turno, fk_id_grado_profesor) VALUES ('Raul', 'Lopez', 'Diaz', 'LODR800505MMM', 'raul@unam.mx', 1, 1, 1, 1);
INSERT INTO public.profesor(nombre, apellido_paterno, apellido_materno, rfc, correo, fk_id_estado, id_genero, fk_id_turno, fk_id_grado_profesor) VALUES ('Maria', 'Martinez', 'Ordaz', 'MAOM800505MMM', 'maria@unam.mx', 1, 1, 1, 1);
INSERT INTO public.profesor(nombre, apellido_paterno, apellido_materno, rfc, correo, fk_id_estado, id_genero, fk_id_turno, fk_id_grado_profesor) VALUES ('Irma', 'Villa', 'Salinas', 'VISI800505MMM', 'irma@unam.mx', 1, 1, 1, 1);
INSERT INTO public.profesor(nombre, apellido_paterno, apellido_materno, rfc, correo, fk_id_estado, id_genero, fk_id_turno, fk_id_grado_profesor) VALUES ('Gerardo', 'Gutierrez', 'Pliego', 'GUPG800505MMM', 'gerardo@unam.mx', 1, 1, 1, 1);

INSERT INTO public.curso(clave, nombre, fk_id_tipo_curso, horas) VALUES ('A001', 'Biologia 1', 1, 40);
INSERT INTO public.curso(clave, nombre, fk_id_tipo_curso, horas) VALUES ('A002', 'Biologia 2', 1, 40);
INSERT INTO public.curso(clave, nombre, fk_id_tipo_curso, horas) VALUES ('A003', 'Biologia 3', 1, 40);
INSERT INTO public.curso(clave, nombre, fk_id_tipo_curso, horas) VALUES ('A004', 'Biologia 4', 1, 40);
INSERT INTO public.curso(clave, nombre, fk_id_tipo_curso, horas) VALUES ('B001', 'Matematicas 1', 1, 40);
INSERT INTO public.curso(clave, nombre, fk_id_tipo_curso, horas) VALUES ('B002', 'Matematicas 2', 1, 40);
INSERT INTO public.curso(clave, nombre, fk_id_tipo_curso, horas) VALUES ('B003', 'Matematicas 3', 1, 40);

INSERT INTO public.grupo(fk_id_curso, clave, fecha_inicio, fecha_fin) VALUES (1, '001', TIMESTAMP '2019-01-05 00:00:00', TIMESTAMP '2019-05-05 00:00:00');
INSERT INTO public.grupo(fk_id_curso, clave, fecha_inicio, fecha_fin) VALUES (1, '002', TIMESTAMP '2019-01-05 00:00:00', TIMESTAMP '2019-05-05 00:00:00');
INSERT INTO public.grupo(fk_id_curso, clave, fecha_inicio, fecha_fin) VALUES (5, '001', TIMESTAMP '2019-01-05 00:00:00', TIMESTAMP '2019-05-05 00:00:00');
COMMIT;

INSERT INTO public.inscripcion(fk_id_grupo, fk_id_profesor) VALUES (1, 1);
INSERT INTO public.inscripcion(fk_id_grupo, fk_id_profesor) VALUES (2, 2);
INSERT INTO public.inscripcion(fk_id_grupo, fk_id_profesor) VALUES (3, 3);
INSERT INTO public.inscripcion(fk_id_grupo, fk_id_profesor) VALUES (1, 4);
COMMIT;

/*
Autor: Juan Carlos Hernández de Anda
Fecha: 11/04/2019
Accion: Creacion de datos de los catalogos y datos de prueba
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

/*
Autor: Jorge Erick Rivera Lopez
Fecha: 24/04/2019
Accion Creacion de tabla para certificados
*/
CREATE TABLE Certificado(
  pk_id_certificado SERIAL PRIMARY KEY,
  fk_id_profesor INTEGER NOT NULL,
  ruta VARCHAR(200) NOT NULL,
  fk_id_curso INTEGER NOT NULL
);
ALTER TABLE Certificado ADD FOREIGN KEY (fk_id_curso) REFERENCES Curso(pk_id_curso);
ALTER TABLE Certificado ADD FOREIGN KEY (fk_id_profesor) REFERENCES Profesor(pk_id_profesor);
INSERT INTO curso (clave,nombre,fk_id_tipo_curso,horas) VALUES ('A005','COSDAC 2018',1,40);
INSERT INTO profesor (nombre, apellido_paterno, apellido_materno, rfc, correo, fk_id_estado, id_genero, fk_id_turno, fk_id_grado_profesor) VALUES ('Abraham', 'Diaz', 'Diaz', 'ABDI800505MMM', 'asmaharba@gmail.com', 1, 1, 1, 1);
ALTER TABLE public.certificado OWNER to "SICECD";

/*
Autor: Jorge Erick Rivera Lopez
Fecha: 04/05/2019
Accion: Agrega campo sobre tiempo de creacion de certificado
*/
ALTER TABLE Certificado ADD tiempo_creado bigint default 0;
/*
Autor: Jorge Erick Rivera Lopez
Fecha: 12/05/2019
Accion: Agrega informacion para prueba de extraccion de certificados.
*/

INSERT INTO public.grupo(fk_id_curso, clave, fecha_inicio, fecha_fin) VALUES (8, '003', TIMESTAMP '2019-01-05 00:00:00', TIMESTAMP '2019-05-05 00:00:00');
INSERT INTO public.profesor (nombre, apellido_paterno, apellido_materno, rfc, correo, fk_id_estado, id_genero, fk_id_turno, fk_id_grado_profesor) VALUES ('Lourdes', 'Diaz', 'Diaz', 'LBDI800505MMM', 'matyap59@hotmail.com', 1, 1, 1, 1);
INSERT INTO public.profesor (nombre, apellido_paterno, apellido_materno, rfc, correo, fk_id_estado, id_genero, fk_id_turno, fk_id_grado_profesor) VALUES ('Maria', 'Diaz', 'Diaz', 'MBDI800505MMM', 'mahalymf@hotmail.com', 1, 1, 1, 1);
INSERT INTO public.profesor (nombre, apellido_paterno, apellido_materno, rfc, correo, fk_id_estado, id_genero, fk_id_turno, fk_id_grado_profesor) VALUES ('Ramiro', 'Diaz', 'Diaz', 'RBDI800505MMM', 'murcielagoblue@yahoo.com.mx', 1, 1, 1, 1);
INSERT INTO public.inscripcion(fk_id_grupo, fk_id_profesor) VALUES (4, 6);
INSERT INTO public.inscripcion(fk_id_grupo, fk_id_profesor) VALUES (4, 7);
INSERT INTO public.inscripcion(fk_id_grupo, fk_id_profesor) VALUES (4, 8);
/*
Autor: Jorge Erick Rivera Lopez
Fecha:  20/06/2019
Accion: Agrega tablas para resguardo de urls de ws de constancias.
*/

CREATE TABLE Url_ws (
 pk_id_url_ws SERIAL PRIMARY KEY,
 url VARCHAR(200) UNIQUE NOT NULL,
 varios BOOLEAN DEFAULT false,
 activa BOOLEAN DEFAULT false
);

ALTER TABLE Url_ws OWNER to "SICECD";
