package conada.entidades;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class prestamo {
    @Id
    private int id;
    @Column(length = 30)
    private int nombre;
    @Column(length = 20)
    private String apellido1;
    @Column(length = 20)
    private String apellido2;
    @Column(length = 20)
    private String ciudad;
    @Column(name = "categoria")
    private float categoria;
    @Column(name = "fehca_ingreso")
    private LocalDate fecha_ingreso;
    
    public prestamo() {
    }

    public prestamo(int id, int nombre, String apellido1, String apellido2, String ciudad, float categoria,
            LocalDate fecha_ingreso) {
        this.id = id;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.ciudad = ciudad;
        this.categoria = categoria;
        this.fecha_ingreso = fecha_ingreso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public float getCategoria() {
        return categoria;
    }

    public void setCategoria(float categoria) {
        this.categoria = categoria;
    }

    public LocalDate getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(LocalDate fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    
}
