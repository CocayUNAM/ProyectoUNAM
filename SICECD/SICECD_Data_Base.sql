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