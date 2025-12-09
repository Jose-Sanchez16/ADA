package examen.conada.operaciones;

import java.time.LocalDate;
import java.util.*;
import examen.conada.entidades.*;
import jakarta.persistence.*;

public class Main {

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static EntityTransaction tx;

    public static void main(String[] args) {

        try {
            emf = Persistence.createEntityManagerFactory("Uni");
            em = emf.createEntityManager();
            tx = em.getTransaction();

            // Insertar datos con la base de datos
            System.out.println("Inserta los siguientes registros en la base de datos:");
            insertarDatos();

            // Mostrar los datos requeridos
            System.out.println("Muestra los datos de los siguientes elementos de la basede datos:");
            mostrarDatos();

            // Realizar las consultas
            System.out.println("Realizando consultas:");
            realizarConsultas();

            // Realizar actualizaciones
            System.out.println("e) Realiza las siguientes actualizaciones en la base de datos:");
            realizarActualizaciones();

        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
        }
    }

    public static void insertarDatos() {
        tx.begin();
        try {
            // Crear profesores
            Profesor profesor1 = new Profesor(1, "Celso Espada Ferrando", "mañana");
            Profesor profesor2 = new Profesor(2, "Valeria Peiró Marquez", "mañana");
            Profesor profesor3 = new Profesor(3, "Primitivo Pizarro Luján", "tarde");
            Profesor profesor4 = new Profesor(4, "Nazario Anaya Aznar", "mañana");
            Profesor profesor5 = new Profesor(5, "Vilma Hervia Yáñez", "tarde");
            Profesor profesor6 = new Profesor(6, "Gertrudis Baeza Borrego", "tarde");

            em.persist(profesor1);
            em.persist(profesor2);
            em.persist(profesor3);
            em.persist(profesor4);
            em.persist(profesor5);
            em.persist(profesor6);

            // Crear asignaturas
            Asignatura asig1 = new Asignatura(1, "Sistemas Informáticos", "primero");
            Asignatura asig2 = new Asignatura(2, "Bases de Datos", "primero");
            Asignatura asig3 = new Asignatura(3, "Programación", "primero");
            Asignatura asig4 = new Asignatura(4, "Lenguajes de Marcas", "primero");
            Asignatura asig5 = new Asignatura(5, "Entornos de Desarrollo", "primero");
            Asignatura asig6 = new Asignatura(6, "Formación y Orientación Laboral", "primero");
            Asignatura asig7 = new Asignatura(7, "Sistemas de gestión empresarial", "segundo");
            Asignatura asig8 = new Asignatura(8, "Programación de servicios y procesos", "segundo");
            Asignatura asig9 = new Asignatura(9, "Acceso a datos", "segundo");
            Asignatura asig10 = new Asignatura(10, "Desarrollo de Interfaces", "segundo");
            Asignatura asig11 = new Asignatura(11, "Programación Multimedia y dispositivos móviles", "segundo");
            Asignatura asig12 = new Asignatura(12, "Empresa e iniciativa emprendedora", "segundo");

            // Asignar profesores a asignaturas
            profesor1.addAsignatura(asig1);
            profesor1.addAsignatura(asig5);

            profesor2.addAsignatura(asig4);
            profesor2.addAsignatura(asig8);

            profesor3.addAsignatura(asig2);
            profesor3.addAsignatura(asig3);

            profesor4.addAsignatura(asig7);
            profesor4.addAsignatura(asig11);

            profesor5.addAsignatura(asig9);
            profesor5.addAsignatura(asig10);

            profesor6.addAsignatura(asig6);
            profesor6.addAsignatura(asig12);

            // Persistir asignaturas
            em.persist(asig1);
            em.persist(asig2);
            em.persist(asig3);
            em.persist(asig4);
            em.persist(asig5);
            em.persist(asig6);
            em.persist(asig7);
            em.persist(asig8);
            em.persist(asig9);
            em.persist(asig10);
            em.persist(asig11);
            em.persist(asig12);

            // Crear alumnos
            Alumno alumno1 = new Alumno(1, "Silvio Núñez Silvestre",
                    LocalDate.of(2019, 7, 1), "Cádiz", "N");
            Alumno alumno2 = new Alumno(2, "Alexandra del Rosell",
                    LocalDate.of(2019, 7, 2), "Sevilla", "N");
            Alumno alumno3 = new Alumno(3, "María Pilar Robledo",
                    LocalDate.of(2019, 8, 12), "Cádiz", "S");
            Alumno alumno4 = new Alumno(4, "Severino Egea Tenorio",
                    LocalDate.of(2019, 9, 1), "Málaga", "S");
            Alumno alumno5 = new Alumno(5, "Rosa Serra Bárcena",
                    LocalDate.of(2019, 9, 1), "Cádiz", "N");
            Alumno alumno6 = new Alumno(6, "Ernesto Sedano Alonso",
                    LocalDate.of(2019, 10, 5), "Córdoba", "N");
            Alumno alumno7 = new Alumno(7, "Encarnación Nebot Heredia",
                    LocalDate.of(2020, 1, 10), "Granada", "S");
            Alumno alumno8 = new Alumno(8, "Fernando Riera Perera",
                    LocalDate.of(2020, 5, 6), "Granada", "N");

            // Persistir alumnos
            em.persist(alumno1);
            em.persist(alumno2);
            em.persist(alumno3);
            em.persist(alumno4);
            em.persist(alumno5);
            em.persist(alumno6);
            em.persist(alumno7);
            em.persist(alumno8);

            for (int i = 7; i <= 12; i++) {
                Asignatura a = em.find(Asignatura.class, i);
                alumno1.addAsignatura(a);
            }

            for (int i = 7; i <= 12; i++) {
                Asignatura a = em.find(Asignatura.class, i);
                alumno2.addAsignatura(a);
            }

            for (int i = 7; i <= 12; i++) {
                Asignatura a = em.find(Asignatura.class, i);
                alumno3.addAsignatura(a);
            }
            Asignatura asig2Obj = em.find(Asignatura.class, 2);
            alumno3.addAsignatura(asig2Obj);

            int[] asigsAlumno4 = { 2, 3, 8, 9, 10 };
            for (int codigo : asigsAlumno4) {
                Asignatura a = em.find(Asignatura.class, codigo);
                alumno4.addAsignatura(a);
            }

            int[] asigsAlumno5 = { 1, 2, 3, 10, 11 };
            for (int codigo : asigsAlumno5) {
                Asignatura a = em.find(Asignatura.class, codigo);
                alumno5.addAsignatura(a);
            }

            int[] asigsAlumno6 = { 3, 7, 8, 9, 12 };
            for (int codigo : asigsAlumno6) {
                Asignatura a = em.find(Asignatura.class, codigo);
                alumno6.addAsignatura(a);
            }

            for (int i = 1; i <= 6; i++) {
                Asignatura a = em.find(Asignatura.class, i);
                alumno7.addAsignatura(a);
            }

            for (int i = 1; i <= 6; i++) {
                Asignatura a = em.find(Asignatura.class, i);
                alumno8.addAsignatura(a);
            }

            tx.commit();
            System.out.println("Datos insertados correctamente.");

        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    private static void mostrarDatos() {
        // 1. Desde el alumno 7, muestra las asignaturas en las que está matriculado
        System.out.println("\n1. Alumno 7 - Asignaturas matriculadas:");
        Alumno alumno7 = em.find(Alumno.class, 7);
        System.out.println("Alumno: " + alumno7.getNoma());
        System.out.println("Asignaturas:");
        for (Asignatura asig : alumno7.getAsignaturas()) {
            System.out.println("  - " + asig.getDasig() + " (" + asig.getCur() + ")");
        }

        // 2. Desde la asignatura Acceso a datos, los alumnos matriculados en ella
        System.out.println("\n2. Asignatura 'Acceso a datos' - Alumnos matriculados:");
        Query queryAsig9 = em.createQuery(
                "SELECT a FROM Asignatura a WHERE a.dasig = :nombre", Asignatura.class);
        queryAsig9.setParameter("nombre", "Acceso a datos");
        Asignatura accesoDatos = (Asignatura) queryAsig9.getSingleResult();
        System.out.println("Asignatura: " + accesoDatos.getDasig());
        System.out.println("Alumnos:");
        for (Alumno alumno : accesoDatos.getAlumnos()) {
            System.out.println("  - " + alumno.getNoma());
        }

        // 3. Las asignaturas que da cada profesor
        System.out.println("\n3. Asignaturas por profesor:");
        List<Profesor> profesores = em.createQuery(
                "SELECT p FROM Profesor p ORDER BY p.cprof", Profesor.class).getResultList();

        for (Profesor prof : profesores) {
            System.out.println("\nProfesor: " + prof.getNomp());
            System.out.println("Asignaturas:");
            for (Asignatura asig : prof.getAsignaturas()) {
                System.out.println("  - " + asig.getDasig() + " (" + asig.getCur() + ")");
            }
        }
    }

    // d) Realizar consultas
    private static void realizarConsultas() {
        tx.begin();

        try {
            // 1. Obtener los nombres de los alumnos matriculados en "bases de datos"
            System.out.println("\n1. Alumnos matriculados en 'Bases de Datos':");
            Query query1 = em.createQuery(
                    "SELECT a.noma FROM Alumno a JOIN a.asignaturas asig " +
                            "WHERE asig.dasig = :nombreAsig ORDER BY a.noma");
            query1.setParameter("nombreAsig", "Bases de Datos");
            List<String> alumnosBD = query1.getResultList();
            for (String nombre : alumnosBD) {
                System.out.println("  - " + nombre);
            }

            // 2. Dado el nombre de un alumno, encontrar sus asignaturas
            System.out.println("\n2. Asignaturas del alumno 'Rosa Serra Bárcena':");
            Query query2 = em.createQuery(
                    "SELECT a FROM Alumno a WHERE a.noma = :nombre", Alumno.class);
            query2.setParameter("nombre", "Rosa Serra Bárcena");
            Alumno rosa = (Alumno) query2.getSingleResult();
            System.out.println("Alumno: " + rosa.getNoma());
            System.out.println("Asignaturas:");
            for (Asignatura asig : rosa.getAsignaturas()) {
                System.out.println("  - " + asig.getDasig() + " (" + asig.getCur() + ")");
            }

            // 3. Obtener el número de asignaturas que imparte un profesor con CPROF=3
            System.out.println("\n3. Número de asignaturas del profesor CPROF=3:");
            Query query3 = em.createQuery(
                    "SELECT COUNT(a) FROM Asignatura a WHERE a.profesor.cprof = :cprof");
            query3.setParameter("cprof", 3);
            Long numAsignaturas = (Long) query3.getSingleResult();
            System.out.println("El profesor con CPROF=3 imparte " + numAsignaturas + " asignaturas");

            // 4. Obtener la lista de los profesores que dan clase a Fernando Riera Perera
            System.out.println("\n4. Profesores del alumno 'Fernando Riera Perera':");
            Query query4 = em.createQuery(
                    "SELECT DISTINCT a.profesor FROM Asignatura a " +
                            "JOIN a.alumnos al " +
                            "WHERE al.noma = :nombreAlumno " +
                            "ORDER BY a.profesor.nomp");
            query4.setParameter("nombreAlumno", "Fernando Riera Perera");
            List<Profesor> profesoresFernando = query4.getResultList();
            System.out.println("Profesores:");
            for (Profesor prof : profesoresFernando) {
                System.out.println("  - " + prof.getNomp());
            }

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    // e) Realizar actualizaciones
    private static void realizarActualizaciones() {
        tx.begin();
        
        try {
            // 1. Modificar alumno 5 para que también se matricule de la asignatura 12
            System.out.println("\n1. Matriculando alumno 5 en asignatura 12...");
            Alumno alumno5 = em.find(Alumno.class, 5);
            Asignatura asig12 = em.find(Asignatura.class, 12);
            alumno5.addAsignatura(asig12);
            em.merge(alumno5);
            System.out.println("Alumno 5 ahora matriculado en asignatura: " + asig12.getDasig());
            
            // 2. Modificar nombre del profesor 4
            System.out.println("\n2. Modificando nombre del profesor 4...");
            Profesor profesor4 = em.find(Profesor.class, 4);
            String nombreAnterior = profesor4.getNomp();
            profesor4.setNomp("Nazario Anaya Aznar (Modificado)");
            em.merge(profesor4);
            System.out.println("Profesor 4: " + nombreAnterior + " -> " + profesor4.getNomp());
        
            tx.commit();
            System.out.println("\n--- Todas las actualizaciones completadas correctamente ---");
            
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }
}