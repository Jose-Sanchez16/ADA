package com.example.conada.operaciones;

import java.util.List;

import org.hibernate.annotations.SourceType;

import java.time.LocalDate;
import com.example.conada.entidades.*;

import jakarta.persistence.*;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Confe");
        EntityManager em = emf.createEntityManager();
        
  try {
            System.out.println("=== SISTEMA DE GESTIÓN DE CONFERENCIAS ===\n");
            
            // ======================== INSERTAR DATOS INICIALES ========================
            System.out.println("=== INSERTANDO DATOS INICIALES ===\n");
            insertarDatosIniciales(em);
            
            // ======================== EJERCICIO 3: CONSULTAS JPQL ========================
            System.out.println("\n\n=== EJERCICIO 3: CONSULTAS JPQL ===\n");
            
            ConsultasJPQL consultas = new ConsultasJPQL(em);
            
            // CONSULTA 1: Conferencias con duración > 2 horas
            System.out.println("1. Conferencias con duración > 2 horas:");
            List<String> confMas2Horas = consultas.consulta1_conferenciasMasDe2Horas();
            confMas2Horas.forEach(nombre -> System.out.println("   - " + nombre));
            
            // CONSULTA 2: Investigadores en conferencia de mayor duración
            System.out.println("\n2. Investigadores en conferencia de mayor duración:");
            List<String> invConfMayor = consultas.consulta2_investigadoresConferenciaMayorDuracion();
            invConfMayor.forEach(nombre -> System.out.println("   - " + nombre));
            
            // CONSULTA 3: Conferencia de menor duración
            System.out.println("\n3. Conferencia de menor duración:");
            List<Conferencia> confMenor = consultas.consulta3_conferenciaMenorDuracion();
            confMenor.forEach(c -> System.out.println("   - " + c.getNombre() + " (" + c.getDuracionHoras() + " horas)"));
            
            // CONSULTA 4: Proyecto de un investigador específico (Juan Pérez)
            System.out.println("\n4. Proyecto del investigador 30487452M (Juan Pérez):");
            Proyecto proyectoJuan = consultas.consulta4_proyectoPorInvestigador("30487452M");
            if (proyectoJuan != null) {
                System.out.println("   - " + proyectoJuan.getNombre());
            }
            
            // CONSULTA 5: Número de conferencias de María Ruiz
            System.out.println("\n5. Número de conferencias del investigador 45642323B (María Ruiz):");
            Long numConferencias = consultas.consulta5_numeroConferenciasPorInvestigador("45642323B");
            System.out.println("   - Ha asistido a " + numConferencias + " conferencias");
            
            // CONSULTA 6: Investigadores del proyecto 4
            System.out.println("\n6. Investigadores del proyecto 4:");
            List<Object[]> invProyecto4 = consultas.consulta6_investigadoresProyecto4();
            for (Object[] inv : invProyecto4) {
                System.out.println("   - DNI: " + inv[0] + ", Nombre: " + inv[1]);
            }
            
            // CONSULTA 7: Proyectos de investigadores en conferencia 5
            System.out.println("\n7. Proyectos de investigadores que asistieron a la conferencia 5:");
            List<Proyecto> proysConf5 = consultas.consulta7_proyectosInvestigadoresConferencia5();
            proysConf5.forEach(p -> System.out.println("   - " + p.getNombre()));
            
            // CONSULTA 8: Proyectos de investigadores en conferencia específica (2)
            System.out.println("\n8. Proyectos de investigadores que asistieron a la conferencia 2:");
            List<Proyecto> proysConf2 = consultas.consulta8_proyectosInvestigadoresConferencia(2L);
            proysConf2.forEach(p -> System.out.println("   - " + p.getNombre()));
            
            // CONSULTA 9: Investigadores en proyectos con fecha anterior a 2020-08-01
            System.out.println("\n9. Investigadores en proyectos con fecha inicio < 2020-08-01:");
            List<String> invFechaAnt = consultas.consulta9_investigadoresProyectoFechaAnterior(LocalDate.of(2020, 8, 1));
            invFechaAnt.forEach(dni -> System.out.println("   - DNI: " + dni));
            
            // CONSULTA 10: Conferencias de investigadores con apellido "Fernández"
            System.out.println("\n10. Conferencias de investigadores con apellido 'Fernández':");
            List<Conferencia> confFernandez = consultas.consulta10_conferenciasPorApellidoInvestigador("Fernández");
            confFernandez.forEach(c -> System.out.println("   - " + c.getNombre()));
            
            /*// ======================== C) MOSTRAR DATOS ========================
            System.out.println("\n\n=== C) MOSTRAR DATOS ===");
            
            // C.i) Los proyectos en los que trabaja cada investigador
            System.out.println("\nC.i) Los proyectos en los que trabaja cada investigador:");
            mostrarProyectosPorInvestigador(em);
            
            // C.ii) Los investigadores que trabajan en cada proyecto
            System.out.println("\nC.ii) Los investigadores que trabajan en cada proyecto:");
            mostrarInvestigadoresPorProyecto(em);
            
            // C.iii) Los investigadores que han ido a cada conferencia
            System.out.println("\nC.iii) Los investigadores que han ido a cada conferencia:");
            mostrarInvestigadoresPorConferencia(em);
            
            // C.iv) Las conferencias en las que ha estado cada investigador
            System.out.println("\nC.iv) Las conferencias en las que ha estado cada investigador:");
            mostrarConferenciasPorInvestigador(em);
            
            // ======================== D) REALIZAR ACTUALIZACIONES ========================
            System.out.println("\n\n=== D) REALIZAR ACTUALIZACIONES ===");
            
            // D.i) Haz que el investigador 2 haya asistido sólo a la conferencia 2
            System.out.println("\nD.i) Haz que el investigador 2 haya asistido sólo a la conferencia 2...");
            actualizarAsistenciaInvestigador2(em);
            
            // D.ii) Actualiza la fecha de la conferencia 4 poniendo la fecha actual
            System.out.println("\nD.ii) Actualiza la fecha de la conferencia 4 poniendo la fecha actual...");
            actualizarFechaConferencia4(em);
            
            // D.iii) Haz que todos los investigadores trabajen en el proyecto 3
            System.out.println("\nD.iii) Haz que todos los investigadores trabajen en el proyecto 3...");
            asignarTodosAlProyecto3(em);
            
            // Mostrar datos después de actualizaciones
            System.out.println("\n=== DATOS DESPUÉS DE ACTUALIZACIONES ===");
            System.out.println("\nProyectos en los que trabaja cada investigador (después):");
            mostrarProyectosPorInvestigador(em);
            System.out.println("\nConferencias en las que ha estado cada investigador (después):");
            mostrarConferenciasPorInvestigador(em);
            
            // ======================== E) ELIMINAR REGISTROS ========================
            System.out.println("\n\n=== E) ELIMINAR REGISTROS ===");
            
            // E.i) Eliminar investigador 2 (Luisa Puertas Soto)
            System.out.println("\nE.i) Eliminar investigador 2 (Luisa Puertas Soto)...");
            eliminarInvestigador2(em);
            
            // E.ii) Eliminar proyecto 1
            System.out.println("\nE.ii) Eliminar proyecto 1...");
            eliminarProyecto1(em);
            
            // E.iii) Eliminar conferencia 4
            System.out.println("\nE.iii) Eliminar conferencia 4...");
            eliminarConferencia4(em);
            
            // Mostrar datos finales
            System.out.println("\n=== DATOS FINALES ===");
            mostrarDatosFinales(em);*/
            
            // ======================== CONSULTAS JPQL DESPUÉS DE MODIFICACIONES ========================
            System.out.println("\n\n=== CONSULTAS JPQL DESPUÉS DE MODIFICACIONES ===");
            
            // Ejecutar algunas consultas después de las modificaciones
            System.out.println("\n1. Conferencias con duración > 2 horas (después):");
            List<String> confMas2HorasDespues = consultas.consulta1_conferenciasMasDe2Horas();
            confMas2HorasDespues.forEach(nombre -> System.out.println("   - " + nombre));
            
            System.out.println("\n5. Número de conferencias del investigador 45642323B (después):");
            Long numConferenciasDespues = consultas.consulta5_numeroConferenciasPorInvestigador("45642323B");
            System.out.println("   - Ha asistido a " + numConferenciasDespues + " conferencias");
            
            System.out.println("\n9. Investigadores en proyectos con fecha inicio < 2020-08-01 (después):");
            List<String> invFechaAntDespues = consultas.consulta9_investigadoresProyectoFechaAnterior(LocalDate.of(2020, 8, 1));
            invFechaAntDespues.forEach(dni -> System.out.println("   - DNI: " + dni));
            
        } catch (Exception e) {
            System.err.println("Error en la ejecución: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
            if (emf != null && emf.isOpen()) {
                emf.close();
            }
        }
    }
    // ======================== MÉTODO PARA INSERTAR DATOS ========================
    
    private static void insertarDatosIniciales(EntityManager em) {
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            
            // ======================== VERIFICAR SI YA HAY DATOS ========================
            Long count = em.createQuery("SELECT COUNT(p) FROM Proyecto p", Long.class).getSingleResult();
            if (count > 0) {
                System.out.println("Ya hay datos en la base de datos. Usando datos existentes.");
                tx.commit();
                return;
            }
            
            // ======================== INSERTAR PROYECTOS ========================
            System.out.println("Insertando proyectos...");
            Proyecto proyecto1 = new Proyecto("Proyecto 1", LocalDate.of(2020, 5, 5));
            Proyecto proyecto2 = new Proyecto("Proyecto 2", LocalDate.of(2020, 6, 12));
            Proyecto proyecto3 = new Proyecto("Proyecto 3", LocalDate.of(2020, 8, 15));
            Proyecto proyecto4 = new Proyecto("Proyecto 4", LocalDate.of(2020, 11, 1));
            Proyecto proyecto5 = new Proyecto("Proyecto 5", LocalDate.of(2020, 12, 12));
            
            em.persist(proyecto1);
            em.persist(proyecto2);
            em.persist(proyecto3);
            em.persist(proyecto4);
            em.persist(proyecto5);
            
            // ======================== INSERTAR INVESTIGADORES ========================
            System.out.println("Insertando investigadores...");
            Investigador inv1 = new Investigador("30487452M", "Juan Pérez Martínez", "C./ Desengaño 21", "623423523", "Cádiz");
            Investigador inv2 = new Investigador("45768434R", "Luisa Puertas Soto", "C./ Falsa 123", "693543252", "Cádiz");
            Investigador inv3 = new Investigador("45642323B", "María Ruiz Sánchez", "C./ Almiel 12", "623234523", "Cádiz");
            Investigador inv4 = new Investigador("67534312A", "Pablo Fernández Feria", "Avd. Inventada 15", "613442323", "Cádiz");
            Investigador inv5 = new Investigador("65342316R", "Sofía Luque Conde", "C/ La Virtud 1", "664123623", "Cádiz");
            Investigador inv6 = new Investigador("67323452B", "José López", "C./ Almiel 15", "723234523", "Cádiz");
            Investigador inv7 = new Investigador("78953321A", "Andrés Fernán Noria", "Avd. Inventada 11", "713442323", "Cádiz");
            Investigador inv8 = new Investigador("98634571R", "Sofía Martín Luz", "C/ La Virtud 4", "764123623", "Cádiz");
            
            em.persist(inv1);
            em.persist(inv2);
            em.persist(inv3);
            em.persist(inv4);
            em.persist(inv5);
            em.persist(inv6);
            em.persist(inv7);
            em.persist(inv8);
            
            // ======================== ASIGNAR INVESTIGADORES A PROYECTOS ========================
            System.out.println("Asignando investigadores a proyectos...");
            // Proyecto 1: investigadores 1 y 5
            inv1.setProyecto(proyecto1);
            inv5.setProyecto(proyecto1);
            
            // Proyecto 2: investigadores 2 y 4
            inv2.setProyecto(proyecto2);
            inv4.setProyecto(proyecto2);
            
            // Proyecto 3: investigadores 3 y 7
            inv3.setProyecto(proyecto3);
            inv7.setProyecto(proyecto3);
            
            // Proyecto 4: investigadores 6 y 8
            inv6.setProyecto(proyecto4);
            inv8.setProyecto(proyecto4);
            
            // Actualizar investigadores
            em.merge(inv1);
            em.merge(inv2);
            em.merge(inv3);
            em.merge(inv4);
            em.merge(inv5);
            em.merge(inv6);
            em.merge(inv7);
            em.merge(inv8);
            
            // ======================== INSERTAR CONFERENCIAS ========================
            System.out.println("Insertando conferencias...");
            Conferencia conf1 = new Conferencia("Conferencia 1", LocalDate.of(2020, 11, 2), "San Fernando", 2.5);
            Conferencia conf2 = new Conferencia("Conferencia 2", LocalDate.of(2021, 1, 12), "Sevilla", 4.0);
            Conferencia conf3 = new Conferencia("Conferencia 3", LocalDate.of(2021, 7, 1), "San Fernando", 1.5);
            Conferencia conf4 = new Conferencia("Conferencia 4", LocalDate.of(2021, 11, 2), "Berlín", 3.0);
            Conferencia conf5 = new Conferencia("Conferencia 5", LocalDate.of(2021, 12, 15), "Madrid", 2.0);
            
            em.persist(conf1);
            em.persist(conf2);
            em.persist(conf3);
            em.persist(conf4);
            em.persist(conf5);
            
            // ======================== ASIGNAR ASISTENCIAS A CONFERENCIAS ========================
            System.out.println("Registrando asistencias a conferencias...");
            
            // Investigador 1: ha ido a la conferencia 2
            AsistenciaConferencia ac1 = new AsistenciaConferencia();
            ac1.setInvestigador(inv1);
            ac1.setConferencia(conf2);
            ac1.setRol("Asistente");
            em.persist(ac1);
            
            // Investigador 2: ha ido a las conferencias 1, 3 y 5
            AsistenciaConferencia ac2_1 = new AsistenciaConferencia();
            ac2_1.setInvestigador(inv2);
            ac2_1.setConferencia(conf1);
            ac2_1.setRol("Asistente");
            em.persist(ac2_1);
            
            AsistenciaConferencia ac2_3 = new AsistenciaConferencia();
            ac2_3.setInvestigador(inv2);
            ac2_3.setConferencia(conf3);
            ac2_3.setRol("Asistente");
            em.persist(ac2_3);
            
            AsistenciaConferencia ac2_5 = new AsistenciaConferencia();
            ac2_5.setInvestigador(inv2);
            ac2_5.setConferencia(conf5);
            ac2_5.setRol("Asistente");
            em.persist(ac2_5);
            
            // Investigador 3: ha ido a todas las conferencias (1, 2, 3, 4, 5)
            for (Conferencia conf : new Conferencia[]{conf1, conf2, conf3, conf4, conf5}) {
                AsistenciaConferencia ac = new AsistenciaConferencia();
                ac.setInvestigador(inv3);
                ac.setConferencia(conf);
                ac.setRol(conf.equals(conf1) ? "Ponente" : 
                         conf.equals(conf3) ? "Organizador" : 
                         conf.equals(conf5) ? "Ponente" : "Asistente");
                em.persist(ac);
            }
            
            // Investigador 4: ha ido a las conferencias 1 y 5
            AsistenciaConferencia ac4_1 = new AsistenciaConferencia();
            ac4_1.setInvestigador(inv4);
            ac4_1.setConferencia(conf1);
            ac4_1.setRol("Asistente");
            em.persist(ac4_1);
            
            AsistenciaConferencia ac4_5 = new AsistenciaConferencia();
            ac4_5.setInvestigador(inv4);
            ac4_5.setConferencia(conf5);
            ac4_5.setRol("Asistente");
            em.persist(ac4_5);
            
            // Investigador 5: ha ido a las conferencias 1, 2, 3 y 4
            for (Conferencia conf : new Conferencia[]{conf1, conf2, conf3, conf4}) {
                AsistenciaConferencia ac = new AsistenciaConferencia();
                ac.setInvestigador(inv5);
                ac.setConferencia(conf);
                ac.setRol("Asistente");
                em.persist(ac);
            }
            
            tx.commit();
            System.out.println("Datos insertados correctamente!");
            
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error insertando datos: " + e.getMessage(), e);
        }
    }
    
    // ======================== MÉTODOS C) MOSTRAR DATOS ========================
    
    private static void mostrarProyectosPorInvestigador(EntityManager em) {
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            
            List<Investigador> investigadores = em.createQuery(
                    "SELECT i FROM Investigador i ORDER BY i.dni", Investigador.class)
                    .getResultList();
                    
            for (Investigador inv : investigadores) {
                System.out.print("Investigador " + inv.getDni() + " (" + inv.getNombreCompleto() + ") trabaja en: ");
                
                if (inv.getProyecto() == null) {
                    System.out.println("Ningún proyecto");
                } else {
                    System.out.println(inv.getProyecto().getNombre());
                }
            }
            
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error mostrando proyectos por investigador: " + e.getMessage(), e);
        }
    }
    
    private static void mostrarInvestigadoresPorProyecto(EntityManager em) {
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            
            List<Proyecto> proyectos = em.createQuery(
                    "SELECT p FROM Proyecto p ORDER BY p.id", Proyecto.class)
                    .getResultList();
                    
            for (Proyecto pro : proyectos) {
                System.out.print("Proyecto " + pro.getId() + " (" + pro.getNombre() + ") investigadores: ");
                
                if (pro.getInvestigadores() == null || pro.getInvestigadores().isEmpty()) {
                    System.out.println("Ninguno");
                } else {
                    for (Investigador investigador : pro.getInvestigadores()) {
                        System.out.print(investigador.getNombreCompleto() + " ");
                    }
                    System.out.println();
                }
            }
            
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error mostrando investigadores por proyecto: " + e.getMessage(), e);
        }
    }
    
    private static void mostrarInvestigadoresPorConferencia(EntityManager em) {
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            
            List<Conferencia> conferencias = em.createQuery(
                    "SELECT c FROM Conferencia c ORDER BY c.id", Conferencia.class)
                    .getResultList();
                    
            for (Conferencia conf : conferencias) {
                System.out.print("Conferencia " + conf.getId() + " (" + conf.getNombre() + "): ");
                
                List<AsistenciaConferencia> asistencias = em.createQuery(
                    "SELECT a FROM AsistenciaConferencia a WHERE a.conferencia.id = :confId", AsistenciaConferencia.class)
                    .setParameter("confId", conf.getId())
                    .getResultList();
                
                if (asistencias.isEmpty()) {
                    System.out.println("No hay asistentes");
                } else {
                    System.out.print("Asistentes: ");
                    for (AsistenciaConferencia ac : asistencias) {
                        System.out.print(ac.getInvestigador().getNombreCompleto() + " ");
                    }
                    System.out.println();
                }
            }
            
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error mostrando investigadores por conferencia: " + e.getMessage(), e);
        }
    }
    
    private static void mostrarConferenciasPorInvestigador(EntityManager em) {
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            
            List<Investigador> investigadores = em.createQuery(
                    "SELECT i FROM Investigador i ORDER BY i.dni", Investigador.class)
                    .getResultList();
                    
            for (Investigador inve : investigadores) {
                System.out.print("Investigador " + inve.getDni() + " (" + inve.getNombreCompleto() + ") ha asistido a: ");
                
                List<AsistenciaConferencia> asistencias = em.createQuery(
                    "SELECT a FROM AsistenciaConferencia a WHERE a.investigador.dni = :dni", AsistenciaConferencia.class)
                    .setParameter("dni", inve.getDni())
                    .getResultList();
                
                if (asistencias.isEmpty()) {
                    System.out.println("Ninguna conferencia");
                } else {
                    for (AsistenciaConferencia ac : asistencias) {
                        System.out.print(ac.getConferencia().getNombre() + " ");
                    }
                    System.out.println();
                }
            }
            
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error mostrando conferencias por investigador: " + e.getMessage(), e);
        }
    }
    
    // ======================== MÉTODOS D) ACTUALIZACIONES ========================
    
    private static void actualizarAsistenciaInvestigador2(EntityManager em) {
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            
            // Buscar investigador 2 (Luisa Puertas Soto)
            Investigador inv2 = em.find(Investigador.class, "45768434R");
            if (inv2 == null) {
                System.out.println("   ERROR: Investigador 2 no encontrado");
                tx.rollback();
                return;
            }
            
            // Eliminar todas las asistencias actuales del investigador 2
            Query deleteQuery = em.createQuery(
                "DELETE FROM AsistenciaConferencia a WHERE a.investigador.dni = :dni");
            deleteQuery.setParameter("dni", "45768434R");
            int deleted = deleteQuery.executeUpdate();
            System.out.println("   Eliminadas " + deleted + " asistencias anteriores");
            
            // Buscar conferencia con id 2
            Conferencia conf2 = em.find(Conferencia.class, 2L);
            if (conf2 != null) {
                // Crear nueva asistencia
                AsistenciaConferencia nuevaAsistencia = new AsistenciaConferencia();
                nuevaAsistencia.setInvestigador(inv2);
                nuevaAsistencia.setConferencia(conf2);
                nuevaAsistencia.setRol("Asistente");
                em.persist(nuevaAsistencia);
                
                System.out.println("   OK: Investigador 2 ahora asiste sólo a Conferencia 2");
            } else {
                System.out.println("   ERROR: Conferencia 2 no encontrada");
            }
            
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error actualizando asistencia investigador 2: " + e.getMessage(), e);
        }
    }
    
    private static void actualizarFechaConferencia4(EntityManager em) {
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            
            Conferencia conf4 = em.find(Conferencia.class, 4L);
            if (conf4 != null) {
                conf4.setFecha(LocalDate.now());
                em.merge(conf4);
                System.out.println("   OK: Conferencia 4 actualizada con fecha: " + LocalDate.now());
            } else {
                System.out.println("   ERROR: Conferencia 4 no encontrada");
            }
            
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error actualizando fecha conferencia 4: " + e.getMessage(), e);
        }
    }
    
    private static void asignarTodosAlProyecto3(EntityManager em) {
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            
            // Buscar proyecto 3
            Proyecto proyecto3 = em.find(Proyecto.class, 3L);
            if (proyecto3 == null) {
                System.out.println("   ERROR: Proyecto 3 no encontrado");
                tx.rollback();
                return;
            }
            
            // Obtener todos los investigadores
            List<Investigador> investigadores = em.createQuery(
                "SELECT i FROM Investigador i", Investigador.class)
                .getResultList();
            
            int asignados = 0;
            for (Investigador inv : investigadores) {
                // Verificar si ya está en el proyecto 3
                boolean yaEnProyecto = inv.getProyecto() != null && 
                                       inv.getProyecto().getId() == 3L;
                
                if (!yaEnProyecto) {
                    // Asignar proyecto al investigador
                    inv.setProyecto(proyecto3);
                    em.merge(inv);
                    asignados++;
                }
            }
            
            System.out.println("   OK: " + asignados + " investigadores asignados al Proyecto 3");
            
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error asignando investigadores al proyecto 3: " + e.getMessage(), e);
        }
    }
    
    // ======================== MÉTODOS E) ELIMINACIONES ========================
    
    private static void eliminarInvestigador2(EntityManager em) {
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            
            // Buscar investigador 2 (Luisa Puertas Soto)
            Investigador inv2 = em.find(Investigador.class, "45768434R");
            if (inv2 != null) {
                // IMPORTANTE: Primero eliminar todas sus asistencias manualmente
                // para evitar problemas con las transacciones
                Query deleteQuery = em.createQuery(
                    "DELETE FROM AsistenciaConferencia a WHERE a.investigador.dni = :dni");
                deleteQuery.setParameter("dni", "45768434R");
                int asistenciasEliminadas = deleteQuery.executeUpdate();
                System.out.println("   Eliminadas " + asistenciasEliminadas + " asistencias del investigador 2");
                
                // Ahora eliminar al investigador
                em.remove(inv2);
                System.out.println("   OK: Investigador 2 (Luisa Puertas Soto) eliminado correctamente");
            } else {
                System.out.println("   ERROR: Investigador 2 no encontrado");
            }
            
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error eliminando investigador 2: " + e.getMessage(), e);
        }
    }
    
    private static void eliminarProyecto1(EntityManager em) {
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            
            // Buscar proyecto 1
            Proyecto pro1 = em.find(Proyecto.class, 1L);
            if (pro1 != null) {
                // Primero desasociar a los investigadores del proyecto 1
                List<Investigador> investigadores = pro1.getInvestigadores();
                if (investigadores != null && !investigadores.isEmpty()) {
                    for (Investigador inv : investigadores) {
                        inv.setProyecto(null);
                        em.merge(inv);
                    }
                    System.out.println("   Desasociados " + investigadores.size() + " investigadores del proyecto 1");
                }
                
                // Eliminar el proyecto
                em.remove(pro1);
                System.out.println("   OK: Proyecto 1 eliminado correctamente");
            } else {
                System.out.println("   ERROR: Proyecto 1 no encontrado");
            }
            
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error eliminando proyecto 1: " + e.getMessage(), e);
        }
    }
    
    private static void eliminarConferencia4(EntityManager em) {
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            
            // Buscar conferencia 4
            Conferencia conf4 = em.find(Conferencia.class, 4L);
            if (conf4 != null) {
                // Primero eliminar todas las asistencias a la conferencia 4
                Query deleteQuery = em.createQuery(
                    "DELETE FROM AsistenciaConferencia a WHERE a.conferencia.id = :confId");
                deleteQuery.setParameter("confId", 4L);
                int asistenciasEliminadas = deleteQuery.executeUpdate();
                System.out.println("   Eliminadas " + asistenciasEliminadas + " asistencias a la conferencia 4");
                
                // Ahora eliminar la conferencia
                em.remove(conf4);
                System.out.println("   OK: Conferencia 4 eliminada correctamente");
            } else {
                System.out.println("   ERROR: Conferencia 4 no encontrada");
            }
            
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error eliminando conferencia 4: " + e.getMessage(), e);
        }
    }
    
    // ======================== MÉTODO AUXILIAR ========================
    
    private static void mostrarDatosFinales(EntityManager em) {
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            
            System.out.println("\n--- INVESTIGADORES RESTANTES ---");
            List<Investigador> investigadores = em.createQuery(
                "SELECT i FROM Investigador i ORDER BY i.dni", Investigador.class)
                .getResultList();
                
            for (Investigador inv : investigadores) {
                String proyecto = (inv.getProyecto() != null) ? inv.getProyecto().getNombre() : "Sin proyecto";
                System.out.println("  - " + inv.getDni() + ": " + inv.getNombreCompleto() + " (" + proyecto + ")");
            }
            
            System.out.println("\n--- PROYECTOS RESTANTES ---");
            List<Proyecto> proyectos = em.createQuery(
                "SELECT p FROM Proyecto p ORDER BY p.id", Proyecto.class)
                .getResultList();
                
            for (Proyecto pro : proyectos) {
                System.out.println("  - " + pro.getId() + ": " + pro.getNombre());
            }
            
            System.out.println("\n--- CONFERENCIAS RESTANTES ---");
            List<Conferencia> conferencias = em.createQuery(
                "SELECT c FROM Conferencia c ORDER BY c.id", Conferencia.class)
                .getResultList();
                
            for (Conferencia conf : conferencias) {
                System.out.println("  - " + conf.getId() + ": " + conf.getNombre() + 
                                 " (Fecha: " + conf.getFecha() + ", Lugar: " + conf.getCiudad() + ")");
            }
            
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error mostrando datos finales: " + e.getMessage(), e);
        }
    }
}