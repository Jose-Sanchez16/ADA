package examen.conada.entidades;

import java.time.LocalDate;
import java.util.*;

import jakarta.persistence.*;

@Entity
@Table(name = "alumno")
public class Alumno {

    @Id
    @Column(name = "NMAT")
    private Integer nmat;

    @Column(name = "NOMA", nullable = false, length = 100)
    private String noma;

    @Column(name = "FN")
    private LocalDate fn;

    @Column(name = "PROV", length = 50)
    private String prov;

    @Column(name = "BECA", length = 1)
    private String beca;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "alumno_asignatura", joinColumns = @JoinColumn(name = "alumno_nmat"), inverseJoinColumns = @JoinColumn(name = "asignatura_casig"))
    private Set<Asignatura> asignaturas = new HashSet<>();

    // Constructores
    public Alumno() {
    }

    public Alumno(Integer nmat, String noma, LocalDate fn, String prov, String beca) {
        this.nmat = nmat;
        this.noma = noma;
        this.fn = fn;
        this.prov = prov;
        this.beca = beca;
    }

    // Getters y Setters
    public Integer getNmat() {
        return nmat;
    }

    public void setNmat(Integer nmat) {
        this.nmat = nmat;
    }

    public String getNoma() {
        return noma;
    }

    public void setNoma(String noma) {
        this.noma = noma;
    }

    public LocalDate getFn() {
        return fn;
    }

    public void setFn(LocalDate fn) {
        this.fn = fn;
    }

    public String getProv() {
        return prov;
    }

    public void setProv(String prov) {
        this.prov = prov;
    }

    public String getBeca() {
        return beca;
    }

    public void setBeca(String beca) {
        this.beca = beca;
    }

    public Set<Asignatura> getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(Set<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
    }

    public void addAsignatura(Asignatura asignatura) {
        this.asignaturas.add(asignatura);
        asignatura.getAlumnos().add(this);
    }

    public void removeAsignatura(Asignatura asignatura) {
        this.asignaturas.remove(asignatura);
        asignatura.getAlumnos().remove(this);
    }

    @Override
    public String toString() {
        return "Alumno [nmat=" + nmat +
                ", noma=" + noma +
                ", fn=" + fn +
                ", prov=" + prov +
                ", beca=" + beca +
                ", asignaturas=" + asignaturas +
                "]";
    }

}
