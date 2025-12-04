package com.example.conada.entidades;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "conferencia")
public class Conferencia {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private LocalDate fecha;
    private String ciudad;
    private Double duracionHoras;
    
    // RELACIÓN CON INVESTIGADOR a través de AsistenciaConferencia (1:N)
    @OneToMany(mappedBy = "conferencia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AsistenciaConferencia> asistencias = new ArrayList<>();
    
    // Constructores
    public Conferencia() {}
    
    public Conferencia(String nombre, LocalDate fecha, String ciudad, Double duracionHoras) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.ciudad = ciudad;
        this.duracionHoras = duracionHoras;
    }
    
    // Método para obtener participantes directamente
    @Transient
    public List<Investigador> getParticipantes() {
        List<Investigador> participantes = new ArrayList<>();
        for (AsistenciaConferencia asistencia : this.asistencias) {
            participantes.add(asistencia.getInvestigador());
        }
        return participantes;
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    
    public Double getDuracionHoras() { return duracionHoras; }
    public void setDuracionHoras(Double duracionHoras) { this.duracionHoras = duracionHoras; }
    
    public List<AsistenciaConferencia> getAsistencias() { return asistencias; }
    public void setAsistencias(List<AsistenciaConferencia> asistencias) { this.asistencias = asistencias; }
}