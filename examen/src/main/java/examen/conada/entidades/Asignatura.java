package examen.conada.entidades;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "asignatura")
public class Asignatura {

    @Id
    @Column(name = "CASIG")
    private Integer casig;

    @Column(name = "DASIG", nullable = false, length = 100)
    private String dasig;

    @Column(name = "CUR", length = 20)
    private String cur;

    @ManyToMany(mappedBy = "asignaturas", fetch = FetchType.LAZY)
    private Set<Alumno> alumnos = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "profesor_cprof",  referencedColumnName = "CPROF")
    private Profesor profesor;

    // Constructores
    public Asignatura() {
    }

    public Asignatura(Integer casig, String dasig, String cur) {
        this.casig = casig;
        this.dasig = dasig;
        this.cur = cur;
    }

    // Getters y Setters
    public Integer getCasig() {
        return casig;
    }

    public void setCasig(Integer casig) {
        this.casig = casig;
    }

    public String getDasig() {
        return dasig;
    }

    public void setDasig(String dasig) {
        this.dasig = dasig;
    }

    public String getCur() {
        return cur;
    }

    public void setCur(String cur) {
        this.cur = cur;
    }

    public Set<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(Set<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    @Override
    public String toString() {
        return "Asignatura{" +
                "casig=" + casig +
                ", dasig='" + dasig + 
                ", cur='" + cur + 
                '}';
    }
}
