package conada.entidades;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class usuario {
    @Id
    private int id_libro;
    @Id
    private int id_usuario;
    @Column(name = "fecha_inicio")
    private LocalTime fecha_inicio;
    @Column(name = "fecha_fin")
    private LocalTime fecha_fin;

    public usuario() {
    }
    public usuario(int id_libro, int id_usuario, LocalTime fecha_inicio, LocalTime fecha_fin) {
        this.id_libro = id_libro;
        this.id_usuario = id_usuario;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
    }
    
    public int getId_libro() {
        return id_libro;
    }
    public void setId_libro(int id_libro) {
        this.id_libro = id_libro;
    }
    public int getId_usuario() {
        return id_usuario;
    }
    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
    public LocalTime getFecha_inicio() {
        return fecha_inicio;
    }
    public void setFecha_inicio(LocalTime fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }
    public LocalTime getFecha_fin() {
        return fecha_fin;
    }
    public void setFecha_fin(LocalTime fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

}
