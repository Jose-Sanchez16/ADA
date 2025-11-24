package conada.entidades;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "persona")
public class Persona {

    @Id
    private int id;

    @Column(length = 50)
    private String nombre;

    @Column(length = 150)
    private String Direccion;

    @Column(name = "fecha_nac")
    private LocalDate f_nac;

    public Persona() {

    }

    public Persona(int id, String nombre, String direccion, LocalDate f_nac) {
        this.id = id;
        this.nombre = nombre;
        Direccion = direccion;
        this.f_nac = f_nac;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public LocalDate getF_nac() {
        return f_nac;
    }

    public void setF_nac(LocalDate f_nac) {
        this.f_nac = f_nac;
    }

}
