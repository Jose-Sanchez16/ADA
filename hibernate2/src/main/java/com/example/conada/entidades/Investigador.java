package com.example.conada.entidades;


import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Investigadores")
public class Investigador {

    @Id
    private int idInvestigador;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @OneToMany(mappedBy = "investigador", cascade = CascadeType.ALL)
    private List<asis_confe> conferenciasAsistidas ;

    public Investigador() {
    }

    public Investigador(int idInvestigador, String nombre,
            List<asis_confe> conferenciasAsistidas) {
        this.idInvestigador = idInvestigador;
        this.nombre = nombre;
        this.conferenciasAsistidas = conferenciasAsistidas;
    }

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

    public List<asis_confe> getConferenciasAsistidas() {
        return conferenciasAsistidas;
    }

    public void setConferenciasAsistidas(List<asis_confe> conferenciasAsistidas) {
        this.conferenciasAsistidas = conferenciasAsistidas;
    }

    @Override
    public String toString() {
        return "Investigador [idInvestigador=" + idInvestigador + ", nombre=" + nombre + ", conferenciasAsistidas="
                + conferenciasAsistidas + "]";
    }


    
}