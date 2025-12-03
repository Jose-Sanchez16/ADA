package com.example.conada.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "Proyectos")
public class Proyecto {

    @Id
    private int idProyecto;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    public Proyecto() {
    }

    public Proyecto(int idProyecto, String nombre ){
        this.idProyecto = idProyecto;
        this.nombre = nombre;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Proyecto [idProyecto=" + idProyecto + ", nombre=" + nombre + "]";
    }
    
 
}
