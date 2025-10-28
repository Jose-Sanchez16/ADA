package com.example;


public class Tema2 {
    public static void main(String[] args) throws Exception{
        Conexion conexion = new Conexion();
        conexion.insertar("insert into clientes values ('333', 'Juan', 'Paseo de Roma 34', '333444555');");
        conexion.insertar("insert into clientes values ('222', 'lola', 'Av. Alemania', '222333444');");
        conexion.insertar("insert into clientes values ('555', 'Maria', 'Avda. Paris, 7', '555666777');");
        conexion.insertar("insert into clientes values ('444', 'Pedro', 'Plaza de la Constitucion', '444555666');");
        conexion.insertar("insert into clientes values ('111', 'pepe', 'Av. Paris', '111222333');");

        conexion.cerrar();
    }
}