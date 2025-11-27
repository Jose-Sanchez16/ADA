package conada.operaciones;

import java.time.LocalDate;
import java.time.LocalDateTime;

import entidades.libro;
import entidades.prestamo;
import entidades.usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Biblioteca");

        libro libro1 = new libro(1, "Gabriel Garcia", "Cien años de soledad", "Disponible", 432);
        libro libro2 = new libro(2, "Jorge Luis Borges", "Ficciones", "Prestado", 176);
        libro libro3 = new libro(3, "Isabel Allende", "La casa de los espíritus", "Disponible", 488);
        libro libro4 = new libro(4, "Mario Vargas", "La ciudad y los perros", "En reparación", 368);
        libro libro5 = new libro(5, "Julio Cortázar", "Rayuela", "Disponible", 736);

        // Insertar 5 usuarios
        usuario usuario1 = new usuario(1, "Ana", "García", "López", "Madrid", 2.5, LocalDate.of(2020, 3, 15));
        usuario usuario2 = new usuario(2, "Carlos", "Martínez", "Ruiz", "Barcelona", 3.0, LocalDate.of(2021, 6, 20));
        usuario usuario3 = new usuario(3, "María", "Rodríguez", "Sánchez", "Valencia", 1.8, LocalDate.of(2019, 11, 5));
        usuario usuario4 = new usuario(4, "Pedro", "Fernández", "Gómez", "Sevilla", 2.2, LocalDate.of(2022, 1, 30));
        usuario usuario5 = new usuario(5, "Laura", "Díaz", "Hernández", "Bilbao", 4.0, LocalDate.of(2020, 9, 12));

        // Insertar 10 préstamos
        LocalDateTime ahora = LocalDateTime.now();

        prestamo prestamo1 = new prestamo(1, 10, ahora.minusDays(10), ahora.minusDays(5));
        prestamo prestamo2 = new prestamo(2, 9, ahora.minusDays(8), ahora.minusDays(1));
        prestamo prestamo3 = new prestamo(3, 8, ahora.minusDays(15), ahora.minusDays(7));
        prestamo prestamo4 = new prestamo(4, 7, ahora.minusDays(20), ahora.minusDays(10));
        prestamo prestamo5 = new prestamo(5, 6, ahora.minusDays(5), null);
        prestamo prestamo6 = new prestamo(6, 5, ahora.minusDays(3), null);
        prestamo prestamo7 = new prestamo(7, 4, ahora.minusDays(12), ahora.minusDays(4));
        prestamo prestamo8 = new prestamo(8, 3, ahora.minusDays(7), ahora.minusDays(2));
        prestamo prestamo9 = new prestamo(9, 2, ahora.minusDays(25), ahora.minusDays(15));
        prestamo prestamo10 = new prestamo(10, 1, ahora.minusDays(2), null);

        añadirl(emf.createEntityManager(), libro1);
        añadirl(emf.createEntityManager(), libro2);
        añadirl(emf.createEntityManager(), libro3);
        añadirl(emf.createEntityManager(), libro4);
        añadirl(emf.createEntityManager(), libro5);

        añadiru(emf.createEntityManager(), usuario1);
        añadiru(emf.createEntityManager(), usuario2);
        añadiru(emf.createEntityManager(), usuario3);
        añadiru(emf.createEntityManager(), usuario4);
        añadiru(emf.createEntityManager(), usuario5);

        añadirp(emf.createEntityManager(), prestamo1);
        añadirp(emf.createEntityManager(), prestamo2);
        añadirp(emf.createEntityManager(), prestamo3);
        añadirp(emf.createEntityManager(), prestamo4);
        añadirp(emf.createEntityManager(), prestamo5);
        añadirp(emf.createEntityManager(), prestamo6);
        añadirp(emf.createEntityManager(), prestamo7);
        añadirp(emf.createEntityManager(), prestamo8);
        añadirp(emf.createEntityManager(), prestamo9);
        añadirp(emf.createEntityManager(), prestamo10);

        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();

        // Ejercicio c
        libro le = em.find(libro.class, 1);
        le.setEstado("En reparación");

        // Ejercicio d
        usuario acu = em.find(usuario.class, 3);
        acu.setCategoria(acu.getCategoria() * 1.25);

        // Ejercicio e
        prestamo fp = em.find(prestamo.class, 1);
        fp.setFechaFin(null);

        // Ejercicio f
        em.remove(em.find(usuario.class, 5));
        for (int i = 1; i <= 10; i++) {
            prestamo ue = em.find(prestamo.class, i);
            if (ue.getIdUsuario() == 5) {
                em.remove(ue);
            }
        }

        // Ejercicio g
        for (int i = 1; i <= 10; i++) {
            prestamo pe = em.find(prestamo.class, i);
            if (pe == null) {
                continue;
            }else if (pe.getIdLibro() == 2) {
                em.remove(pe);
            }
        }
        et.commit();
        em.close();
        emf.close();
    }

    public static void añadirl(EntityManager em, libro l) {
        EntityTransaction et = em.getTransaction();
        et.begin();

        em.persist(l);

        et.commit();

        em.close();
    }

    public static void añadiru(EntityManager em, usuario u) {
        EntityTransaction et = em.getTransaction();
        et.begin();

        em.persist(u);

        et.commit();

        em.close();
    }

    public static void añadirp(EntityManager em, prestamo p) {
        EntityTransaction et = em.getTransaction();
        et.begin();

        em.persist(p);

        et.commit();

        em.close();
    }

}