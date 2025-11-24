package conada.Operaciones;

import java.time.LocalDate;

import conada.entidades.Persona;
import jakarta.persistence.*;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("miBD");
        EntityManager em = emf.createEntityManager();

        Persona persona1 = new Persona (101, "Ana Gracia", "Calle Falsa 123", LocalDate.of(2000, 7, 15));

        EntityTransaction et = em.getTransaction();
        et.begin();

        em.persist(persona1);
        et.commit();

        em.close();
    }
}
