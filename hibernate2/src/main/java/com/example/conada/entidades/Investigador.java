package com.example.conada.entidades;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Investigadores")
public class Investigador {

    @Id
    private int idInvestigador;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "Investigadores_Proyectos",
        joinColumns = @JoinColumn(name = "id_investigador"),
        inverseJoinColumns = @JoinColumn(name = "id_proyecto")
    )
    private Set<Proyecto> proyectos = new HashSet<>();

    @OneToMany(mappedBy = "investigador", cascade = CascadeType.ALL)
    private Set<asis_confe> conferenciasAsistidas = new HashSet<>();

    public Investigador() {
    }

    public Investigador(int idInvestigador, String nombre) {
        this.idInvestigador = idInvestigador;
        this.nombre = nombre;
    }

    // Getters y Setters...
    public int getIdInvestigador() {
        return idInvestigador;
    }

    public void setIdInvestigador(int idInvestigador) {
        this.idInvestigador = idInvestigador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Proyecto> getProyectos() {
        return proyectos;
    }

    public void setProyectos(Set<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }

    public Set<asis_confe> getConferenciasAsistidas() {
        return conferenciasAsistidas;
    }

    public void setConferenciasAsistidas(Set<asis_confe> conferenciasAsistidas) {
        this.conferenciasAsistidas = conferenciasAsistidas;
    }

    public void agregarProyecto(Proyecto proyecto) {
        this.proyectos.add(proyecto);
        proyecto.getInvestigadores().add(this);
    }

    public void eliminarProyecto(Proyecto proyecto) {
        this.proyectos.remove(proyecto);
        proyecto.getInvestigadores().remove(this);
    }

    @Override
    public String toString() {
        return "Investigador [idInvestigador=" + idInvestigador + ", nombre=" + nombre + "]";
    }
}