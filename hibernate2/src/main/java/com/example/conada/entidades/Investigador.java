package com.example.conada.entidades;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "investigador")
public class Investigador {
    
    @Id
    @Column(name = "dni", length = 9)
    private String dni;
    
    @Column(name = "nombre_completo")
    private String nombreCompleto;
    
    private String direccion;
    private String telefono;
    private String ciudad;
    
    // RELACIÓN CON PROYECTO (N:1)
    @ManyToOne
    @JoinColumn(name = "proyecto_id")
    private Proyecto proyecto;
    
    // RELACIÓN CON CONFERENCIA a través de AsistenciaConferencia (1:N)
    @OneToMany(mappedBy = "investigador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AsistenciaConferencia> asistencias = new ArrayList<>();
    
    // Constructores
    public Investigador() {}
    
    public Investigador(String dni, String nombreCompleto, String direccion, String telefono, String ciudad) {
        this.dni = dni;
        this.nombreCompleto = nombreCompleto;
        this.direccion = direccion;
        this.telefono = telefono;
        this.ciudad = ciudad;
    }
    public void addConferencia(Conferencia conferencia) {
        AsistenciaConferencia asistencia = new AsistenciaConferencia(this, conferencia);
        this.asistencias.add(asistencia);
        conferencia.getAsistencias().add(asistencia);
    }
    
    public void removeConferencia(Conferencia conferencia) {
        AsistenciaConferencia asistencia = this.asistencias.stream()
            .filter(a -> a.getConferencia().equals(conferencia))
            .findFirst()
            .orElse(null);
        
        if (asistencia != null) {
            this.asistencias.remove(asistencia);
            conferencia.getAsistencias().remove(asistencia);
            asistencia.setInvestigador(null);
            asistencia.setConferencia(null);
        }
    }
    
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    
    public Proyecto getProyecto() { return proyecto; } 
    public void setProyecto(Proyecto proyecto) { this.proyecto = proyecto; } 
    
    public List<AsistenciaConferencia> getAsistencias() { return asistencias; }
    public void setAsistencias(List<AsistenciaConferencia> asistencias) { this.asistencias = asistencias; }
    
    @Transient
    public List<Conferencia> getConferencias() {
        List<Conferencia> conferencias = new ArrayList<>();
        for (AsistenciaConferencia asistencia : this.asistencias) {
            conferencias.add(asistencia.getConferencia());
        }
        return conferencias;
    }
    
    @Override
    public String toString() {
        return "Investigador [dni=" + dni + ", nombreCompleto=" + nombreCompleto + "]";
    }
}