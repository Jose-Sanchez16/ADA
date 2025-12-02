package com.example.conada.entidades;

import jakarta.persistence.*;

public class asis_confe {
    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_investigador")
    private Investigador investigador;

    @ManyToOne
    @JoinColumn(name = "id_conferencia")
    private Conferencia conferencia;

    public asis_confe() {
    }

    public asis_confe(int id, Investigador investigador, Conferencia conferencia) {
        this.id = id;
        this.investigador = investigador;
        this.conferencia = conferencia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Investigador getInvestigador() {
        return investigador;
    }

    public void setInvestigador(Investigador investigador) {
        this.investigador = investigador;
    }

    public Conferencia getConferencia() {
        return conferencia;
    }

    public void setConferencia(Conferencia conferencia) {
        this.conferencia = conferencia;
    }

    @Override
    public String toString() {
        return "asis_confe [id=" + id + ", investigador=" + investigador + ", conferencia=" + conferencia + "]";
    }

    
}
