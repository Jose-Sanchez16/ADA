package com.example.conada.operaciones;

import org.hibernate.mapping.List;

import com.example.conada.entidades.Inv_pro;
import com.example.conada.entidades.Investigador;

import jakarta.persistence.*;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Confe");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
    }

    private static void mostrarDatos(EntityManager em) {

    }

    private static void mostrarProyectosPorInvestigador(EntityManager em) {
        List<Investigador> investigadores = em.createQuery(
                "Select i from Investigador i Oder By i.idInvestigador", Investigador.class)
                .getResultList();
        for (Investigador inv : investigadores) {
            System.out.print("Investigador " + inv.getIdInvestigador() + " (" + inv.getNombre() + ") trabaja en: ");

            if (inv.getProyectos().isEmpty()) {
                System.out.println("Ningún proyecto");
            } else {
                for (Inv_pro ip : inv.getProyectos()) {
                    System.out.print(ip.getProyecto().getNombre() + " ");
                }
                System.out.println();
            }
        }
    }

}