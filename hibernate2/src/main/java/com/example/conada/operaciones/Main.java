package com.example.conada.operaciones;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import com.example.conada.entidades.*;

public class Main {
    
    private static EntityManagerFactory emf;
    private static EntityManager em;
    
    public static void main(String[] args) {
        emf = Persistence.createEntityManagerFactory("Confe");
        em = emf.createEntityManager();
        
        try {
            System.out.println("=== EJERCICIO 2 - JPA/HIBERNATE ===\n");
            
            // ========== B) INSERCIÓN DE DATOS ==========
            System.out.println("=== B) INSERCIÓN DE DATOS ===");
            insertarDatos();
            
            // ========== C) CONSULTAS ==========
            System.out.println("\n=== C) CONSULTAS ===");
            consultarDatos();
            
            // ========== D) ACTUALIZACIONES ==========
            System.out.println("\n=== D) ACTUALIZACIONES ===");
            realizarActualizaciones();
            
            // ========== E) ELIMINACIONES ==========
            System.out.println("\n=== E) ELIMINACIONES ===");
            realizarEliminaciones();
            
            System.out.println("\n=== EJERCICIO COMPLETADO ===");
            
        } finally {
            em.close();
            emf.close();
        }
    }
    
    // ========== B) MÉTODO DE INSERCIÓN ==========
    private static void insertarDatos() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        try {
            System.out.println("Creando investigadores...");
            // Crear 8 Investigadores
            Investigador inv1 = new Investigador("30487452M", "Juan Pérez Martínez", "C./ Desengaño 21", "623423523", "Cádiz");
            Investigador inv2 = new Investigador("45768434R", "Luisa Puertas Soto", "C./ Falsa 123", "693543252", "Cádiz");
            Investigador inv3 = new Investigador("45642323B", "María Ruiz Sánchez", "C./ Almiel 12", "623234523", "Cádiz");
            Investigador inv4 = new Investigador("67534312A", "Pablo Fernández Feria", "Avd. Inventada 15", "613442323", "Cádiz");
            Investigador inv5 = new Investigador("65342316R", "Sofía Luque Conde", "C/ La Virtud 1", "664123623", "Cádiz");
            Investigador inv6 = new Investigador("67323452B", "José López", "C./ Almiel 15", "723234523", "Cádiz");
            Investigador inv7 = new Investigador("78953321A", "Andrés Fernán Noria", "Avd. Inventada 11", "713442323", "Cádiz");
            Investigador inv8 = new Investigador("98634571R", "Sofía Martín Luz", "C/ La Virtud 4", "764123623", "Cádiz");
            
            System.out.println("Creando proyectos...");
            // Crear 5 Proyectos
            Proyecto p1 = new Proyecto("Proyecto 1", LocalDate.of(2020, 5, 5));
            Proyecto p2 = new Proyecto("Proyecto 2", LocalDate.of(2020, 6, 12));
            Proyecto p3 = new Proyecto("Proyecto 3", LocalDate.of(2020, 8, 15));
            Proyecto p4 = new Proyecto("Proyecto 4", LocalDate.of(2020, 11, 1));
            Proyecto p5 = new Proyecto("Proyecto 5", LocalDate.of(2020, 12, 12));
            
            System.out.println("Creando conferencias...");
            // Crear 4 Conferencias
            Conferencia c1 = new Conferencia("Conferencia 1", LocalDate.of(2020, 11, 2), "San Fernando", 2.5);
            Conferencia c2 = new Conferencia("Conferencia 2", LocalDate.of(2021, 1, 12), "Sevilla", 4.0);
            Conferencia c3 = new Conferencia("Conferencia 3", LocalDate.of(2021, 7, 1), "San Fernando", 1.5);
            Conferencia c4 = new Conferencia("Conferencia 4", LocalDate.of(2021, 11, 2), "Berlín", 3.0);
            
            // Persistir todo primero
            em.persist(inv1); em.persist(inv2); em.persist(inv3); em.persist(inv4);
            em.persist(inv5); em.persist(inv6); em.persist(inv7); em.persist(inv8);
            
            em.persist(p1); em.persist(p2); em.persist(p3); em.persist(p4); em.persist(p5);
            
            em.persist(c1); em.persist(c2); em.persist(c3); em.persist(c4);
            
            System.out.println("Asignando investigadores a proyectos...");
            // Asignar investigadores a proyectos (relación N:1)
            // Proyecto 1: investigadores 1 y 5
            inv1.setProyecto(p1);
            inv5.setProyecto(p1);
            
            // Proyecto 2: investigadores 2 y 4
            inv2.setProyecto(p2);
            inv4.setProyecto(p2);
            
            // Proyecto 3: investigadores 3 y 7
            inv3.setProyecto(p3);
            inv7.setProyecto(p3);
            
            // Proyecto 4: investigadores 6 y 8
            inv6.setProyecto(p4);
            inv8.setProyecto(p4);
            
            // El proyecto 5 no tiene investigadores asignados
            
            System.out.println("Registrando asistencias a conferencias...");
            // REGISTRAR ASISTENCIAS A CONFERENCIAS USANDO AsistenciaConferencia
            
            // Investigador 1 va a conferencia 2
            AsistenciaConferencia ac1 = new AsistenciaConferencia(inv1, c2);
            ac1.setRol("Asistente");
            em.persist(ac1);
            
            // Investigador 2 va a conferencias 1, 3 y 4
            AsistenciaConferencia ac2_1 = new AsistenciaConferencia(inv2, c1);
            ac2_1.setRol("Ponente");
            AsistenciaConferencia ac2_3 = new AsistenciaConferencia(inv2, c3);
            ac2_3.setRol("Asistente");
            AsistenciaConferencia ac2_4 = new AsistenciaConferencia(inv2, c4);
            ac2_4.setRol("Organizador");
            em.persist(ac2_1);
            em.persist(ac2_3);
            em.persist(ac2_4);
            
            // Investigador 3 va a todas las conferencias
            AsistenciaConferencia ac3_1 = new AsistenciaConferencia(inv3, c1);
            AsistenciaConferencia ac3_2 = new AsistenciaConferencia(inv3, c2);
            AsistenciaConferencia ac3_3 = new AsistenciaConferencia(inv3, c3);
            AsistenciaConferencia ac3_4 = new AsistenciaConferencia(inv3, c4);
            ac3_1.setRol("Ponente");
            ac3_2.setRol("Asistente");
            ac3_3.setRol("Asistente");
            ac3_4.setRol("Organizador");
            em.persist(ac3_1);
            em.persist(ac3_2);
            em.persist(ac3_3);
            em.persist(ac3_4);
            
            // Investigador 4 va a conferencias 1 y 4
            AsistenciaConferencia ac4_1 = new AsistenciaConferencia(inv4, c1);
            AsistenciaConferencia ac4_4 = new AsistenciaConferencia(inv4, c4);
            ac4_1.setRol("Asistente");
            ac4_4.setRol("Asistente");
            em.persist(ac4_1);
            em.persist(ac4_4);
            
            // Investigador 5 va a conferencias 1, 2, 3 y 4
            AsistenciaConferencia ac5_1 = new AsistenciaConferencia(inv5, c1);
            AsistenciaConferencia ac5_2 = new AsistenciaConferencia(inv5, c2);
            AsistenciaConferencia ac5_3 = new AsistenciaConferencia(inv5, c3);
            AsistenciaConferencia ac5_4 = new AsistenciaConferencia(inv5, c4);
            ac5_1.setRol("Asistente");
            ac5_2.setRol("Ponente");
            ac5_3.setRol("Asistente");
            ac5_4.setRol("Asistente");
            em.persist(ac5_1);
            em.persist(ac5_2);
            em.persist(ac5_3);
            em.persist(ac5_4);
            
            tx.commit();
            System.out.println("Datos insertados correctamente.");
            
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            System.err.println("Error al insertar datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // ========== C) MÉTODOS DE CONSULTA ==========
    private static void consultarDatos() {
        System.out.println("\n--- C-i) Proyectos en los que trabaja cada investigador ---");
        // i) Proyectos en los que trabaja cada investigador
        String jpql1 = "SELECT i.nombreCompleto, p.nombre " +
                      "FROM Investigador i LEFT JOIN i.proyecto p " +
                      "ORDER BY i.nombreCompleto";
        List<Object[]> resultados1 = em.createQuery(jpql1, Object[].class).getResultList();
        for (Object[] row : resultados1) {
            String proyecto = (row[1] != null) ? row[1].toString() : "Sin proyecto";
            System.out.println("  " + row[0] + " -> " + proyecto);
        }
        
        System.out.println("\n--- C-ii) Investigadores que trabajan en cada proyecto ---");
        // ii) Investigadores que trabajan en cada proyecto
        String jpql2 = "SELECT p.nombre, i.nombreCompleto " +
                      "FROM Proyecto p LEFT JOIN p.investigadores i " +
                      "ORDER BY p.nombre, i.nombreCompleto";
        List<Object[]> resultados2 = em.createQuery(jpql2, Object[].class).getResultList();
        String proyectoActual = "";
        for (Object[] row : resultados2) {
            if (!proyectoActual.equals(row[0].toString())) {
                proyectoActual = row[0].toString();
                System.out.println("  " + proyectoActual + ":");
            }
            if (row[1] != null) {
                System.out.println("    - " + row[1]);
            } else {
                System.out.println("    - Sin investigadores");
            }
        }
        
        System.out.println("\n--- C-iii) Investigadores que han ido a cada conferencia ---");
        // iii) Investigadores que han ido a cada conferencia
        String jpql3 = "SELECT c.nombre, i.nombreCompleto, ac.rol " +
                      "FROM AsistenciaConferencia ac " +
                      "JOIN ac.conferencia c " +
                      "JOIN ac.investigador i " +
                      "ORDER BY c.nombre, i.nombreCompleto";
        List<Object[]> resultados3 = em.createQuery(jpql3, Object[].class).getResultList();
        String conferenciaActual = "";
        for (Object[] row : resultados3) {
            if (!conferenciaActual.equals(row[0].toString())) {
                conferenciaActual = row[0].toString();
                System.out.println("  " + conferenciaActual + ":");
            }
            System.out.println("    - " + row[1] + " [" + row[2] + "]");
        }
        
        System.out.println("\n--- C-iv) Conferencias en las que ha estado cada investigador ---");
        // iv) Conferencias en las que ha estado cada investigador
        String jpql4 = "SELECT i.nombreCompleto, c.nombre, ac.rol " +
                      "FROM AsistenciaConferencia ac " +
                      "JOIN ac.investigador i " +
                      "JOIN ac.conferencia c " +
                      "ORDER BY i.nombreCompleto, c.fecha";
        List<Object[]> resultados4 = em.createQuery(jpql4, Object[].class).getResultList();
        String investigadorActual = "";
        for (Object[] row : resultados4) {
            if (!investigadorActual.equals(row[0].toString())) {
                investigadorActual = row[0].toString();
                System.out.println("  " + investigadorActual + ":");
            }
            System.out.println("    - " + row[1] + " [" + row[2] + "]");
        }
    }
    
    // ========== D) MÉTODOS DE ACTUALIZACIÓN ==========
    private static void realizarActualizaciones() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        try {
            System.out.println("\n--- D-i) Investigador 2 asiste sólo a conferencia 2 ---");
            // i) Investigador 2 asiste sólo a conferencia 2
            
            // Buscar investigador 2
            Investigador inv2 = em.find(Investigador.class, "45768434R");
            
            // Buscar conferencia 2
            TypedQuery<Conferencia> queryC2 = em.createQuery(
                "SELECT c FROM Conferencia c WHERE c.nombre = 'Conferencia 2'", Conferencia.class);
            Conferencia c2 = queryC2.getSingleResult();
            
            // Eliminar todas las asistencias actuales del investigador 2
            TypedQuery<AsistenciaConferencia> queryAsistencias = em.createQuery(
                "SELECT ac FROM AsistenciaConferencia ac WHERE ac.investigador.dni = '45768434R'", 
                AsistenciaConferencia.class);
            List<AsistenciaConferencia> asistenciasActuales = queryAsistencias.getResultList();
            
            for (AsistenciaConferencia ac : asistenciasActuales) {
                em.remove(ac);
            }
            
            // Crear nueva asistencia solo a conferencia 2
            AsistenciaConferencia nuevaAsistencia = new AsistenciaConferencia(inv2, c2);
            nuevaAsistencia.setRol("Asistente");
            nuevaAsistencia.setFechaInscripcion(LocalDate.now());
            em.persist(nuevaAsistencia);
            
            System.out.println("Investigador 2 ahora asiste solo a Conferencia 2");
            
            System.out.println("\n--- D-ii) Actualizar fecha de conferencia 4 ---");
            // ii) Actualizar fecha de conferencia 4 a fecha actual
            TypedQuery<Conferencia> queryC4 = em.createQuery(
                "SELECT c FROM Conferencia c WHERE c.nombre = 'Conferencia 4'", Conferencia.class);
            Conferencia c4 = queryC4.getSingleResult();
            c4.setFecha(LocalDate.now());
            em.merge(c4);
            System.out.println("Conferencia 4 actualizada a fecha actual: " + LocalDate.now());
            
            System.out.println("\n--- D-iii) Todos los investigadores trabajan en proyecto 3 ---");
            // iii) Todos los investigadores trabajan en proyecto 3
            
            // Buscar proyecto 3
            Proyecto p3 = em.find(Proyecto.class, 3L);
            
            // Obtener todos los investigadores
            List<Investigador> todosInvestigadores = em.createQuery(
                "SELECT i FROM Investigador i", Investigador.class).getResultList();
            
            // Asignar todos los investigadores al proyecto 3
            for (Investigador investigador : todosInvestigadores) {
                investigador.setProyecto(p3);
                em.merge(investigador);
            }
            
            System.out.println("Todos los investigadores asignados al Proyecto 3");
            
            tx.commit();
            
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            System.err.println("Error en actualizaciones: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // ========== E) MÉTODOS DE ELIMINACIÓN ==========
    private static void realizarEliminaciones() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        try {
            System.out.println("\n--- E-i) Eliminar investigador 2 ---");
            // i) Eliminar investigador 2
            
            // Primero eliminar todas sus asistencias a conferencias
            TypedQuery<AsistenciaConferencia> queryAsistenciasInv2 = em.createQuery(
                "SELECT ac FROM AsistenciaConferencia ac WHERE ac.investigador.dni = '45768434R'", 
                AsistenciaConferencia.class);
            List<AsistenciaConferencia> asistenciasInv2 = queryAsistenciasInv2.getResultList();
            
            for (AsistenciaConferencia ac : asistenciasInv2) {
                em.remove(ac);
            }
            
            // Ahora eliminar el investigador
            Investigador inv2 = em.find(Investigador.class, "45768434R");
            if (inv2 != null) {
                em.remove(inv2);
                System.out.println("Investigador 2 eliminado");
            } else {
                System.out.println("Investigador 2 no encontrado");
            }
            
            System.out.println("\n--- E-ii) Eliminar proyecto 1 ---");
            // ii) Eliminar proyecto 1
            
            // Primero desasignar a todos los investigadores del proyecto 1
            TypedQuery<Investigador> queryInvProyecto1 = em.createQuery(
                "SELECT i FROM Investigador i WHERE i.proyecto.id = 1", Investigador.class);
            List<Investigador> investigadoresProyecto1 = queryInvProyecto1.getResultList();
            
            for (Investigador investigador : investigadoresProyecto1) {
                investigador.setProyecto(null);
                em.merge(investigador);
            }
            
            // Ahora eliminar el proyecto
            Proyecto p1 = em.find(Proyecto.class, 1L);
            if (p1 != null) {
                em.remove(p1);
                System.out.println("Proyecto 1 eliminado");
            } else {
                System.out.println("Proyecto 1 no encontrado");
            }
            
            System.out.println("\n--- E-iii) Eliminar conferencia 4 ---");
            // iii) Eliminar conferencia 4
            
            // Primero eliminar todas las asistencias a la conferencia 4
            TypedQuery<AsistenciaConferencia> queryAsistenciasC4 = em.createQuery(
                "SELECT ac FROM AsistenciaConferencia ac WHERE ac.conferencia.id = 4", 
                AsistenciaConferencia.class);
            List<AsistenciaConferencia> asistenciasC4 = queryAsistenciasC4.getResultList();
            
            for (AsistenciaConferencia ac : asistenciasC4) {
                em.remove(ac);
            }
            
            // Ahora eliminar la conferencia
            Conferencia c4 = em.find(Conferencia.class, 4L);
            if (c4 != null) {
                em.remove(c4);
                System.out.println("Conferencia 4 eliminada");
            } else {
                System.out.println("Conferencia 4 no encontrada");
            }
            
            tx.commit();
            
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            System.err.println("Error en eliminaciones: " + e.getMessage());
            e.printStackTrace();
        }
    }
}