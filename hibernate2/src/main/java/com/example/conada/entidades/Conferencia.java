package com.example.conada.entidades;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "Conferencias")
public class Conferencia {

    @Id
    private int idConferencia;

    @Column(length = 100)
    private String nombre;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(length = 100)
    private String ubicacion;

    @Column(name = "duracion_horas")
    private double duracionHoras;

    @OneToMany(mappedBy = "conferencia", cascade = CascadeType.ALL)
    private List<asis_confe> asistencias ;

    public Conferencia() {
    }

    public Conferencia(int idConferencia, String nombre, LocalDate fecha, String ubicacion, double duracionHoras,
            List<asis_confe> asistencias) {
        this.idConferencia = idConferencia;
        this.nombre = nombre;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
        this.duracionHoras = duracionHoras;
        this.asistencias = asistencias;
    }

    public int getIdConferencia() {
        return idConferencia;
    }

    public void setIdConferencia(int idConferencia) {
        this.idConferencia = idConferencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public double getDuracionHoras() {
        return duracionHoras;
    }

    public void setDuracionHoras(double duracionHoras) {
        this.duracionHoras = duracionHoras;
    }

    public List<asis_confe> getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(List<asis_confe> asistencias) {
        this.asistencias = asistencias;
    }

    @Override
    public String toString() {
        return "Conferencia [idConferencia=" + idConferencia + ", nombre=" + nombre + ", fecha=" + fecha
                + ", ubicacion=" + ubicacion + ", duracionHoras=" + duracionHoras + ", asistencias=" + asistencias
                + "]";
    }
    


}
