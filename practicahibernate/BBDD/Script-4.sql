drop database if exists Biblioteca;
create database Biblioteca;
use Biblioteca;

create table libro (
    id int primary key,
    autor varchar(20) unique,
    titulo varchar(30), 		
    estado varchar(20) not null,
    n_paginas int not null
);

create table usuario (
    id int primary key,
    nombre varchar(30),
    apellido1 varchar(20),
    apellido2 varchar(20),
    ciudad varchar(20),
    categoría decimal(10,2),
    fecha_ingreso date
);

create table prestamo (
    id_libro int,
    id_usuario int,
    fecha_inicio datetime not null unique,
    fecha_fin datetime,
    primary key (id_libro, id_usuario)
);