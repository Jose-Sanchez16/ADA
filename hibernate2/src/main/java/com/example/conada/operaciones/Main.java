package com.example.conada.operaciones;

import java.time.LocalDate;
import java.util.*;

import com.example.conada.entidades.Conferencia;
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
            // C.i) Los proyectos en los que trabaja cada investigador
            System.out.println("C.i) Los proyectos en los que trabaja cada investigador:");
            mostrarProyectosPorInvestigador(em);

            // C.ii) Los investigadores que trabajan en cada proyecto
            System.out.println("C.ii) Los investigadores que trabajan en cada proyecto:");
            mostrarInvestigadoresPorProyecto(em);

            // C.iii) Los investigadores que han ido a cada conferencia
            System.out.println("C.iii) Los investigadores que han ido a cada conferencia:");
            mostrarInvestigadoresPorConferencia(em);

            // C.iv) Las conferencias en las que ha estado cada investigador
            System.out.println("C.iv) Las conferencias en las que ha estado cada investigador:");
            mostrarConferenciasPorInvestigador(em);

            // D.i) Haz que el investigador 2 haya asistido sólo a la conferencia 2
            System.out.println("D.i) Haz que el investigador 2 haya asistido sólo a la conferencia 2:");
            actualizarAsistenciaInvestigador2(em);
            // D.ii) Actualiza la fecha de la conferencia 4 poniendo la fecha actual
            System.out.println("D.ii) Actualiza la fecha de la conferencia 4 poniendo la fecha actual...");
            actualizarFechaConferencia4(em);

            // D.iii) Haz que todos los investigadores trabajen en el proyecto 3
            System.out.println("D.iii) Haz que todos los investigadores trabajen en el proyecto 3...");
            asignarTodosAlProyecto3(em);

            // Mostrar datos después de actualizaciones
            System.out.println("Proyectos en los que trabaja cada investigador (después):");
            mostrarProyectosPorInvestigador(em);
            System.out.println("Conferencias en las que ha estado cada investigador (después):");
            mostrarConferenciasPorInvestigador(em);

            // E.i) Eliminar investigador 2
            System.out.println("\nE.i) Eliminar investigador 2...");
            eliminarInvestigador2(em);

            // E.ii) Eliminar proyecto 1
            System.out.println("\nE.ii) Eliminar proyecto 1...");
            eliminarProyecto1(em);

            // E.iii) Eliminar conferencia 4
            System.out.println("\nE.iii) Eliminar conferencia 4...");
            eliminarConferencia4(em);

            // Mostrar datos finales
            System.out.println("\n=== DATOS FINALES ===");
            mostrarDatosFinales(em);

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
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        List<Investigador> investigadores = em.createQuery(
                "SELECT i FROM Investigador i ORDER BY i.idInvestigador", Investigador.class)
                .getResultList();

        for (Investigador inv : investigadores) {
            System.out.print("Investigador " + inv.getIdInvestigador() + " (" + inv.getNombre() + ") trabaja en: ");

            if (inv.getProyectos() == null || inv.getProyectos().isEmpty()) {
                System.out.println("Ningún proyecto");
            } else {
                for (Proyecto proyecto : inv.getProyectos()) {
                    System.out.print(proyecto.getNombre() + " ");
                }
                System.out.println();
            }
        }

        tx.commit();
    }

    private static void mostrarInvestigadoresPorProyecto(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        List<Proyecto> proyectos = em.createQuery(
                "SELECT p FROM Proyecto p ORDER BY p.idProyecto", Proyecto.class)
                .getResultList();

        for (Proyecto pro : proyectos) {
            System.out.print("Proyecto " + pro.getIdProyecto() + " (" + pro.getNombre() + ") investigadores: ");

            if (pro.getInvestigadores() == null || pro.getInvestigadores().isEmpty()) {
                System.out.println("Ninguno");
            } else {
                for (Investigador investigador : pro.getInvestigadores()) {
                    System.out.print(investigador.getNombre() + " ");
                }
                System.out.println();
            }
        }

        tx.commit();
    }

    private static void mostrarInvestigadoresPorConferencia(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        List<Conferencia> conferencias = em.createQuery(
                "SELECT c FROM Conferencia c ORDER BY c.idConferencia", Conferencia.class)
                .getResultList();

        for (Conferencia conf : conferencias) {
            System.out.print("Conferencia " + conf.getIdConferencia() + " (" + conf.getNombre() + "): ");

            if (conf.getAsistencias() == null || conf.getAsistencias().isEmpty()) {
                System.out.println("No hay asistentes");
            } else {
                System.out.print("Asistentes: ");
                for (asis_confe ac : conf.getAsistencias()) {
                    System.out.print(ac.getInvestigador().getNombre() + " ");
                }
                System.out.println();
            }
        }

        tx.commit();
    }

    private static void mostrarConferenciasPorInvestigador(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        List<Investigador> investigadores = em.createQuery(
                "SELECT i FROM Investigador i ORDER BY i.idInvestigador", Investigador.class)
                .getResultList();

        for (Investigador inve : investigadores) {
            System.out
                    .print("Investigador " + inve.getIdInvestigador() + " (" + inve.getNombre() + ") ha asistido a: ");

            if (inve.getConferenciasAsistidas() == null || inve.getConferenciasAsistidas().isEmpty()) {
                System.out.println("Ninguna conferencia");
            } else {
                for (asis_confe ac : inve.getConferenciasAsistidas()) {
                    System.out.print(ac.getConferencia().getNombre() + " ");
                }
                System.out.println();
            }
        }

        tx.commit();
    }

    private static void actualizarAsistenciaInvestigador2(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Investigador inv2 = em.find(Investigador.class, 2);
            if (inv2 == null) {
                System.out.println("   ERROR: Investigador 2 no encontrado");
                return;
            }

            List<asis_confe> asistencias = em.createQuery(
                    "SELECT a FROM asis_confe a WHERE a.investigador.idInvestigador = 2", asis_confe.class)
                    .getResultList();

            for (asis_confe ac : asistencias) {
                em.remove(ac);
            }

            Conferencia conf2 = em.find(Conferencia.class, 2);
            if (conf2 != null) {
                Query query = em.createNativeQuery("SELECT COALESCE(MAX(id), 0) + 1 FROM Asistencia_Conferencias");
                int nextId = ((Number) query.getSingleResult()).intValue();

                asis_confe nuevaAsistencia = new asis_confe();
                nuevaAsistencia.setId(nextId);
                nuevaAsistencia.setInvestigador(inv2);
                nuevaAsistencia.setConferencia(conf2);
                em.persist(nuevaAsistencia);

                System.out.println("   OK: Investigador 2 ahora asiste sólo a Conferencia 2");
            } else {
                System.out.println("   ERROR: Conferencia 2 no encontrada");
            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }

    private static void actualizarFechaConferencia4(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Conferencia conf4 = em.find(Conferencia.class, 4);
            if (conf4 != null) {
                conf4.setFecha(LocalDate.now());
                em.merge(conf4);
                System.out.println("   OK: Conferencia 4 actualizada con fecha: " + LocalDate.now());
            } else {
                System.out.println("   ERROR: Conferencia 4 no encontrada");
            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }

    private static void asignarTodosAlProyecto3(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // Buscar proyecto 3
            Proyecto proyecto3 = em.find(Proyecto.class, 3);
            if (proyecto3 == null) {
                System.out.println("   ERROR: Proyecto 3 no encontrado");
                return;
            }

            // Obtener todos los investigadores
            List<Investigador> investigadores = em.createQuery(
                    "SELECT i FROM Investigador i", Investigador.class)
                    .getResultList();

            int asignados = 0;
            for (Investigador inv : investigadores) {
                // Verificar si ya está en el proyecto 3
                boolean yaEnProyecto = false;
                if (inv.getProyectos() != null) {
                    yaEnProyecto = inv.getProyectos().stream()
                            .anyMatch(p -> p.getIdProyecto() == 3);
                }

                if (!yaEnProyecto) {
                    // Agregar proyecto al investigador
                    inv.getProyectos().add(proyecto3);
                    em.merge(inv);
                    asignados++;
                }
            }

            System.out.println("   OK: " + asignados + " investigadores asignados al Proyecto 3");

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }

    private static void eliminarInvestigador2(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // Buscar investigador 2
            Investigador inv2 = em.find(Investigador.class, 2);
            if (inv2 != null) {
                // Primero eliminar las asistencias a conferencias
                List<asis_confe> asistencias = em.createQuery(
                        "SELECT a FROM asis_confe a WHERE a.investigador.idInvestigador = 2", asis_confe.class)
                        .getResultList();

                for (asis_confe ac : asistencias) {
                    em.remove(ac);
                }

                // Remover de los proyectos (para mantener la relación consistente)
                if (inv2.getProyectos() != null) {
                    for (Proyecto p : inv2.getProyectos()) {
                        p.getInvestigadores().remove(inv2);
                        em.merge(p);
                    }
                }

                // Finalmente eliminar al investigador
                em.remove(inv2);
                System.out.println("   OK: Investigador 2 eliminado correctamente");
            } else {
                System.out.println("   ERROR: Investigador 2 no encontrado");
            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }

    private static void eliminarProyecto1(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // Buscar proyecto 1
            Proyecto pro1 = em.find(Proyecto.class, 1);
            if (pro1 != null) {
                // Remover de los investigadores
                if (pro1.getInvestigadores() != null) {
                    for (Investigador inv : pro1.getInvestigadores()) {
                        inv.getProyectos().remove(pro1);
                        em.merge(inv);
                    }
                }

                // Eliminar el proyecto
                em.remove(pro1);
                System.out.println("   OK: Proyecto 1 eliminado correctamente");
            } else {
                System.out.println("   ERROR: Proyecto 1 no encontrado");
            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }

    private static void eliminarConferencia4(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // Buscar conferencia 4
            Conferencia conf4 = em.find(Conferencia.class, 4);
            if (conf4 != null) {
                // Primero eliminar todas las asistencias
                List<asis_confe> asistencias = em.createQuery(
                        "SELECT a FROM asis_confe a WHERE a.conferencia.idConferencia = 4", asis_confe.class)
                        .getResultList();

                for (asis_confe ac : asistencias) {
                    em.remove(ac);
                }

                // Eliminar la conferencia
                em.remove(conf4);
                System.out.println("   OK: Conferencia 4 eliminada correctamente");
            } else {
                System.out.println("   ERROR: Conferencia 4 no encontrada");
            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }

    private static void mostrarDatosFinales(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        System.out.println("\n--- INVESTIGADORES RESTANTES ---");
        List<Investigador> investigadores = em.createQuery(
                "SELECT i FROM Investigador i ORDER BY i.idInvestigador", Investigador.class)
                .getResultList();

        for (Investigador inv : investigadores) {
            System.out.println("  - " + inv.getIdInvestigador() + ": " + inv.getNombre());
        }

        System.out.println("\n--- PROYECTOS RESTANTES ---");
        List<Proyecto> proyectos = em.createQuery(
                "SELECT p FROM Proyecto p ORDER BY p.idProyecto", Proyecto.class)
                .getResultList();

        for (Proyecto pro : proyectos) {
            System.out.println("  - " + pro.getIdProyecto() + ": " + pro.getNombre());
        }

        System.out.println("\n--- CONFERENCIAS RESTANTES ---");
        List<Conferencia> conferencias = em.createQuery(
                "SELECT c FROM Conferencia c ORDER BY c.idConferencia", Conferencia.class)
                .getResultList();

        for (Conferencia conf : conferencias) {
            System.out.println("  - " + conf.getIdConferencia() + ": " + conf.getNombre() +
                    " (Fecha: " + conf.getFecha() + ")");
        }

        tx.commit();
    }
}