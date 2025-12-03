package com.example.conada.operaciones;

import java.util.*;

import com.example.conada.entidades.Conferencia;
import com.example.conada.entidades.Inv_pro;
import com.example.conada.entidades.Investigador;
import com.example.conada.entidades.Proyecto;
import com.example.conada.entidades.asis_confe;

import jakarta.persistence.*;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Confe");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            // i) Mostrar los investigadores por cada proyecto
            System.out.println("Proyectos en lo¡5895s que trabaja cada investigador ");
            tx.begin();
            mostrarProyectosPorInvestigador(em);
            tx.commit();

            // ii) Mostrar los proyectos por cada investigador
            System.out.println("Investigadores que trabajan en cada proyecto");
            tx.begin();
            mostrarInvestigadorPorProyecto(em);
            tx.commit();

            // iii) Los investigadores que han ido a cada conferencia
            System.out.println("Los investigadores que han ido a cada conferencia");
            tx.begin();
            mostrarInvestigadoresPorConferencia(em);
            tx.commit();

            // C.iv) Las conferencias en las que ha estado cada investigador
            System.out.println("Las conferencias en las que ha estado cada investigador");
            tx.begin();
            mostrarConferenciasPorInvestigador(em);
            tx.commit();

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }

    private static void mostrarProyectosPorInvestigador(EntityManager em) {
        List<Investigador> investigadores = em.createQuery(
                "Select i from Investigador i Order By i.idInvestigador", Investigador.class)
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

    private static void mostrarInvestigadorPorProyecto(EntityManager em) {
        List<Proyecto> proyectos = em.createQuery(
                "Select p from Proyecto p Order By p.idProyecto", Proyecto.class)
                .getResultList();
        for (Proyecto pro : proyectos) {
            System.out.print("Proyecto " + pro.getIdProyecto() + " (" + pro.getNombre() + ") Investigador: ");

            if (pro.getInvestigadores().isEmpty()) {
                System.out.println("Ninguno");
            } else {
                for (Inv_pro ip : pro.getInvestigadores()) {
                    System.out.print(ip.getInvestigador().getNombre() + " ");
                }
                System.out.println();
            }
        }
    }

    private static void mostrarInvestigadoresPorConferencia(EntityManager em) {
        List<Conferencia> conferencias = em.createQuery(
                "Select c from Conferencia c Order By c.idConferencia", Conferencia.class)
                .getResultList();

        for (Conferencia conf : conferencias) {
            System.out.println("Conferencia " + conf.getIdConferencia() +
                    " (" + conf.getNombre() + ") Asistencia: ");

            if (conf.getAsistencias().isEmpty()) {
                System.out.println("Ninguno");
            } else {
                for (asis_confe ac : conf.getAsistencias()) {
                    System.out.println(ac.getInvestigador().getNombre() + " ");
                }
                System.out.println();
            }
        }
    }

    private static void mostrarConferenciasPorInvestigador(EntityManager em) {
        List<Investigador> investigadores = em.createQuery(
                "Select i from Investigador i Order By i.idInvestigador", Investigador.class)
                .getResultList();

        for (Investigador inve : investigadores) {
            System.out.println("Investigador " + inve.getIdInvestigador() +
                    " (" + inve.getNombre() + ") ha asistido a: ");

            if (inve.getConferenciasAsistidas().isEmpty()) {
                System.out.println("Ninguna conferencia");
            } else {
                for (asis_confe ac : inve.getConferenciasAsistidas()) {
                    System.out.println(ac.getInvestigador().getNombre() + " ");
                }
                System.out.println();
            }
        }
    }

}