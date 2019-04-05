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
  fk_id_tipo_curso INTEGER NOT NULL,
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