package com.example.conada.entidades;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Proyectos")
public class Proyecto {

    @Id
    private int idProyecto;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL)
    private List<Inv_pro > investigadores ;

    public Proyecto() {
    }

    public Proyecto(int idProyecto, String nombre, List<Inv_pro> investigadores) {
        this.idProyecto = idProyecto;
        this.nombre = nombre;
        this.investigadores = investigadores;
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

    public List<Inv_pro> getInvestigadores() {
        return investigadores;
    }

    public void setInvestigadores(List<Inv_pro> investigadores) {
        this.investigadores = investigadores;
    }

    @Override
    public String toString() {
        return "Proyecto [idProyecto=" + idProyecto + ", nombre=" + nombre + ", investigadores=" + investigadores + "]";
    }
 
}
