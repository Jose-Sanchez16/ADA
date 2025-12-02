package com.example.conada.entidades;

import jakarta.persistence.*;

public class Inv_pro {

    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_investigador")
    private Investigador investigador;

    @ManyToOne
    @JoinColumn(name = "id_proyecto")
    private Proyecto proyecto;

    public Inv_pro() {
    }

    public Inv_pro(int id, Investigador investigador, Proyecto proyecto) {
        this.id = id;
        this.investigador = investigador;
        this.proyecto = proyecto;
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

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    @Override
    public String toString() {
        return "Inv_pro [id=" + id + ", investigador=" + investigador + ", proyecto=" + proyecto + "]";
    }

    
}
