package com.example.conada.entidades;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "asistencia_conferencia")
public class AsistenciaConferencia {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "investigador_dni", nullable = false)
    private Investigador investigador;
    
    @ManyToOne
    @JoinColumn(name = "conferencia_id", nullable = false)
    private Conferencia conferencia;
    
    // Atributos adicionales de la relación
    @Column(name = "fecha_inscripcion")
    private LocalDate fechaInscripcion;
    
    @Column(name = "asistio")
    private boolean asistio = true;  // Por defecto true si se registró
    
    @Column(name = "rol", length = 50)
    private String rol;  // "Ponente", "Asistente", "Organizador"
    
    // Constructor
    public AsistenciaConferencia() {}
    
    public AsistenciaConferencia(Investigador investigador, Conferencia conferencia) {
        this.investigador = investigador;
        this.conferencia = conferencia;
        this.fechaInscripcion = LocalDate.now();
        this.asistio = true;
        this.rol = "Asistente";
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Investigador getInvestigador() { return investigador; }
    public void setInvestigador(Investigador investigador) { this.investigador = investigador; }
    
    public Conferencia getConferencia() { return conferencia; }
    public void setConferencia(Conferencia conferencia) { this.conferencia = conferencia; }
    
    public LocalDate getFechaInscripcion() { return fechaInscripcion; }
    public void setFechaInscripcion(LocalDate fechaInscripcion) { this.fechaInscripcion = fechaInscripcion; }
    
    public boolean isAsistio() { return asistio; }
    public void setAsistio(boolean asistio) { this.asistio = asistio; }
    
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}