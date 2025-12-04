package com.example.conada.operaciones;

import java.util.List;
import java.time.LocalDate;
import com.example.conada.entidades.*;
import jakarta.persistence.*;

public class ConsultasJPQL {
    private EntityManager em;
    
    public ConsultasJPQL(EntityManager em) {
        this.em = em;
    }
    
    // ==================== CONSULTA 1 ====================
    // El nombre de las conferencias cuya duración sea superior a las dos horas.
    public List<String> consulta1_conferenciasMasDe2Horas() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        try {
            List<String> resultados = em.createQuery(
                "SELECT c.nombre FROM Conferencia c WHERE c.duracionHoras > 2.0", 
                String.class)
                .getResultList();
            
            tx.commit();
            return resultados;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }
    
    // ==================== CONSULTA 2 ====================
    // El nombre de los investigadores que participaron en la conferencia de la mayor duración.
    public List<String> consulta2_investigadoresConferenciaMayorDuracion() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        try {
            List<String> resultados = em.createQuery(
                "SELECT DISTINCT i.nombreCompleto " +
                "FROM Investigador i " +
                "JOIN i.asistencias a " +
                "JOIN a.conferencia c " +
                "WHERE c.duracionHoras = (SELECT MAX(c2.duracionHoras) FROM Conferencia c2)", 
                String.class)
                .getResultList();
            
            tx.commit();
            return resultados;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }
    
    // ==================== CONSULTA 3 ====================
    // Toda la información sobre la conferencia de menor duración.
    public List<Conferencia> consulta3_conferenciaMenorDuracion() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        try {
            List<Conferencia> resultados = em.createQuery(
                "SELECT c FROM Conferencia c " +
                "WHERE c.duracionHoras = (SELECT MIN(c2.duracionHoras) FROM Conferencia c2)", 
                Conferencia.class)
                .getResultList();
            
            tx.commit();
            return resultados;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }
    
    // ==================== CONSULTA 4 ====================
    // Los proyectos llevados a cabo por un determinado investigador.
    // Versión con parámetro para un investigador específico
    public Proyecto consulta4_proyectoPorInvestigador(String dniInvestigador) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        try {
            Proyecto resultado = em.createQuery(
                "SELECT i.proyecto FROM Investigador i WHERE i.dni = :dni", 
                Proyecto.class)
                .setParameter("dni", dniInvestigador)
                .getSingleResult();
            
            tx.commit();
            return resultado;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }
    
    // Versión que devuelve todos los proyectos (en caso de relación N:M, pero en nuestro caso es 1:1)
    public List<Proyecto> consulta4_proyectosPorInvestigador(String dniInvestigador) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        try {
            List<Proyecto> resultados = em.createQuery(
                "SELECT DISTINCT p FROM Proyecto p " +
                "JOIN p.investigadores i " +
                "WHERE i.dni = :dni", 
                Proyecto.class)
                .setParameter("dni", dniInvestigador)
                .getResultList();
            
            tx.commit();
            return resultados;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }
    
    // ==================== CONSULTA 5 ====================
    // El número de conferencias en las que ha participado un determinado investigador.
    public Long consulta5_numeroConferenciasPorInvestigador(String dniInvestigador) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        try {
            Long resultado = em.createQuery(
                "SELECT COUNT(DISTINCT a.conferencia) " +
                "FROM AsistenciaConferencia a " +
                "WHERE a.investigador.dni = :dni", 
                Long.class)
                .setParameter("dni", dniInvestigador)
                .getSingleResult();
            
            tx.commit();
            return resultado;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }
    
    // ==================== CONSULTA 6 ====================
    // Dni, nombre y apellidos de los investigadores que trabajan en el proyecto 4.
    public List<Object[]> consulta6_investigadoresProyecto4() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        try {
            List<Object[]> resultados = em.createQuery(
                "SELECT i.dni, i.nombreCompleto FROM Investigador i " +
                "WHERE i.proyecto.id = 4", 
                Object[].class)
                .getResultList();
            
            tx.commit();
            return resultados;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }
    
    // ==================== CONSULTA 7 ====================
    // Toda la información sobre los proyectos en los que trabajan los investigadores 
    // que participaron en la conferencia 5.
    public List<Proyecto> consulta7_proyectosInvestigadoresConferencia5() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        try {
            List<Proyecto> resultados = em.createQuery(
                "SELECT DISTINCT i.proyecto FROM Investigador i " +
                "WHERE i.dni IN (" +
                "    SELECT a.investigador.dni FROM AsistenciaConferencia a " +
                "    WHERE a.conferencia.id = 5" +
                ")", 
                Proyecto.class)
                .getResultList();
            
            tx.commit();
            return resultados;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }
    
    // ==================== CONSULTA 8 ====================
    // La misma información que el anterior, pero para una conferencia determinada.
    public List<Proyecto> consulta8_proyectosInvestigadoresConferencia(Long idConferencia) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        try {
            List<Proyecto> resultados = em.createQuery(
                "SELECT DISTINCT i.proyecto FROM Investigador i " +
                "WHERE i.dni IN (" +
                "    SELECT a.investigador.dni FROM AsistenciaConferencia a " +
                "    WHERE a.conferencia.id = :idConferencia" +
                ")", 
                Proyecto.class)
                .setParameter("idConferencia", idConferencia)
                .getResultList();
            
            tx.commit();
            return resultados;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }
    
    // ==================== CONSULTA 9 ====================
    // El dni de los investigadores que trabajen en un proyecto cuya fecha de inicio sea menor a una fecha dada.
    public List<String> consulta9_investigadoresProyectoFechaAnterior(LocalDate fechaLimite) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        try {
            List<String> resultados = em.createQuery(
                "SELECT i.dni FROM Investigador i " +
                "JOIN i.proyecto p " +
                "WHERE p.fechaInicio < :fechaLimite", 
                String.class)
                .setParameter("fechaLimite", fechaLimite)
                .getResultList();
            
            tx.commit();
            return resultados;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }
    
    // ==================== CONSULTA 10 ====================
    // Toda la información sobre las conferencias en las que haya participado 
    // un investigador con un apellido concreto.
    public List<Conferencia> consulta10_conferenciasPorApellidoInvestigador(String apellido) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        try {
            List<Conferencia> resultados = em.createQuery(
                "SELECT DISTINCT a.conferencia FROM AsistenciaConferencia a " +
                "WHERE a.investigador.nombreCompleto LIKE :apellidoPattern", 
                Conferencia.class)
                .setParameter("apellidoPattern", "%" + apellido + "%")
                .getResultList();
            
            tx.commit();
            return resultados;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }
    
    // Versión alternativa con JOIN explícito
    public List<Conferencia> consulta10_conferenciasPorApellidoInvestigadorJOIN(String apellido) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        try {
            List<Conferencia> resultados = em.createQuery(
                "SELECT DISTINCT c FROM Conferencia c " +
                "JOIN c.asistencias a " +
                "JOIN a.investigador i " +
                "WHERE i.nombreCompleto LIKE :apellidoPattern", 
                Conferencia.class)
                .setParameter("apellidoPattern", "%" + apellido + "%")
                .getResultList();
            
            tx.commit();
            return resultados;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }
}