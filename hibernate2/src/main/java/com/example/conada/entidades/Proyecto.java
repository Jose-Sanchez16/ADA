package com.example.conada.entidades;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Proyectos")
public class Proyecto {

    @Id
    private int idProyecto;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @ManyToMany(mappedBy = "proyectos", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Investigador> investigadores = new HashSet<>();

    public Proyecto() {
    }

    public Proyecto(int idProyecto, String nombre) {
        this.idProyecto = idProyecto;
        this.nombre = nombre;
    }

    // Getters y Setters...
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

    public Set<Investigador> getInvestigadores() {
        return investigadores;
    }

    public void setInvestigadores(Set<Investigador> investigadores) {
        this.investigadores = investigadores;
    }

    @Override
    public String toString() {
        return "Proyecto [idProyecto=" + idProyecto + ", nombre=" + nombre + "]";
    }
}