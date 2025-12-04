package com.example.conada.entidades;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "proyecto")
public class Proyecto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;
    
    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;
    
    @OneToMany(mappedBy = "proyecto")
    private List<Investigador> investigadores = new ArrayList<>();
    
    // Constructores
    public Proyecto() {}
    
    public Proyecto(String nombre, LocalDate fechaInicio) {
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    
    public List<Investigador> getInvestigadores() { return investigadores; }
    public void setInvestigadores(List<Investigador> investigadores) { this.investigadores = investigadores; }

    @Override
    public String toString() {
        return "Proyecto [id=" + id + ", nombre=" + nombre + "]";
    }
}