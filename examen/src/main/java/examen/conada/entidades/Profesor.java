package examen.conada.entidades;

import java.util.*;

import jakarta.persistence.*;

@Entity
@Table(name = "profesor")
public class Profesor {
    
    @Id
    @Column(name = "CPROF")
    private Integer cprof;
    
    @Column(name = "NOMP", nullable = false, length = 100)
    private String nomp;
    
    @Column(name = "TURNO", length = 20)
    private String turno;
    
    @OneToMany(mappedBy = "profesor", fetch = FetchType.LAZY, 
               cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Asignatura> asignaturas = new HashSet<>();
    
    // Constructores
    public Profesor() {}
    
    public Profesor(Integer cprof, String nomp, String turno) {
        this.cprof = cprof;
        this.nomp = nomp;
        this.turno = turno;
    }
    
    // Getters y Setters
    public Integer getCprof() { return cprof; }
    public void setCprof(Integer cprof) { this.cprof = cprof; }
    
    public String getNomp() { return nomp; }
    public void setNomp(String nomp) { this.nomp = nomp; }
    
    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }
    
    public Set<Asignatura> getAsignaturas() { return asignaturas; }
    public void setAsignaturas(Set<Asignatura> asignaturas) { this.asignaturas = asignaturas; }
    
    // Métodos auxiliares
    public void addAsignatura(Asignatura asignatura) {
        asignaturas.add(asignatura);
        asignatura.setProfesor(this);
    }
    
    public void removeAsignatura(Asignatura asignatura) {
        asignaturas.remove(asignatura);
        asignatura.setProfesor(null);
    }
    
    @Override
    public String toString() {
        return "Profesor{" +
                "cprof=" + cprof +
                ", nomp='" + nomp + '\'' +
                ", turno='" + turno + '\'' +
                '}';
    }
}