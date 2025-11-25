package conada.Operaciones;

import java.time.LocalDate;

import conada.entidades.Persona;
import jakarta.persistence.*;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("miBD");

        Persona p1 = new Persona(4, "Ana Gracia", "Calle Falsa 123", LocalDate.of(2000, 7, 15));
        Persona p2 = new Persona(5, "Agapito Gracia", "Calle Verdad 123", LocalDate.of(2001, 2, 1));

        borrarPersona(emf.createEntityManager(), 4);
        añadirPersona(emf.createEntityManager(), p2);
        añadirPersona(emf.createEntityManager(), p1);
    }

    public static void añadirPersona(EntityManager em, Persona p) {
        EntityTransaction et = em.getTransaction();
        et.begin();

        em.persist(p);

        et.commit();

        em.close();
    }
    public static void borrarPersona(EntityManager em , int id){
        EntityTransaction et = em.getTransaction();
        et.begin();

        Persona p = em.find (Persona.class, id);
        em.remove(p);
        et.commit();
        em.close();
    }
}
