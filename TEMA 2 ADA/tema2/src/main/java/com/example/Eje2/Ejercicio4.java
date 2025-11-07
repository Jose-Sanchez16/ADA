package com.example.Eje2;

import java.sql.*;
import java.util.Scanner;

public class Ejercicio4 {
    private static final String URL = "jdbc:mysql://localhost:3306/empresa_trabajadores";
    private static final String USER = "tu_usuario";
    private static final String PASSWORD = "tu_password";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Scanner scanner = new Scanner(System.in)) {
            
            System.out.println("=== EJERCICIO 4 ===");
            
            // a) Trabajadores con tarifa entre dos extremos
            System.out.println("\na) Trabajadores con tarifa entre 10 y 12 euros:");
            trabajadoresTarifaRango(conn, 10.0, 12.0);
            
            // b) Oficios en edificio 312
            System.out.println("\nb) Oficios en edificio 312:");
            oficiosEdificio(conn, 312);
            
            // c) Trabajador y supervisor
            System.out.println("\nc) Trabajadores y sus supervisores:");
            trabajadorSupervisor(conn);
            
            // d) Trabajadores en oficinas
            System.out.println("\nd) Trabajadores asignados a oficinas:");
            trabajadoresOficinas(conn);
            
            // e) Total días fontanería en edificio 312
            System.out.println("\ne) Total días de fontanería en edificio 312:");
            totalDiasActividadEdificio(conn, "FONTANERO", 312);
            
            // f) Número de oficios diferentes
            System.out.println("\nf) Número de oficios diferentes:");
            numeroOficios(conn);
            
            // g) Tarifa máxima por supervisor
            System.out.println("\ng) Tarifa máxima por supervisor:");
            tarifaMaximaSupervisor(conn);
            
            // h) Tarifa máxima por supervisor con múltiples trabajadores
            System.out.println("\nh) Tarifa máxima por supervisor con múltiples trabajadores:");
            tarifaMaximaSupervisorMultiple(conn);
            
            // i) Trabajadores con tarifa menor al promedio general
            System.out.println("\ni) Trabajadores con tarifa menor al promedio general:");
            tarifaMenorPromedioGeneral(conn);
            
            // j) Trabajadores con tarifa menor al promedio de su oficio
            System.out.println("\nj) Trabajadores con tarifa menor al promedio de su oficio:");
            tarifaMenorPromedioOficio(conn);
            
            // k) Trabajadores con tarifa menor al promedio de su supervisor
            System.out.println("\nk) Trabajadores con tarifa menor al promedio de su supervisor:");
            tarifaMenorPromedioSupervisor(conn);
            
            // l) Supervisores con trabajadores con tarifa > 14
            System.out.println("\nl) Supervisores con trabajadores con tarifa > 14:");
            supervisoresTarifaMinima(conn, 14.0);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Métodos para llamar a cada procedimiento almacenado
    private static void trabajadoresTarifaRango(Connection conn, double min, double max) throws SQLException {
        String sql = "CALL trabajadores_tarifa_rango(?, ?)";
        try (CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setDouble(1, min);
            stmt.setDouble(2, max);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Nombre: " + rs.getString("NOMBRE") + 
                                 ", Tarifa: " + rs.getDouble("TARIFA"));
            }
        }
    }
    
    private static void oficiosEdificio(Connection conn, int idEdificio) throws SQLException {
        String sql = "CALL oficios_edificio(?)";
        try (CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, idEdificio);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Oficio: " + rs.getString("OFICIO"));
            }
        }
    }
    
    private static void trabajadorSupervisor(Connection conn) throws SQLException {
        String sql = "CALL trabajador_supervisor()";
        try (CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Trabajador: " + rs.getString("Trabajador") + 
                                 ", Supervisor: " + rs.getString("Supervisor"));
            }
        }
    }
    
    private static void trabajadoresOficinas(Connection conn) throws SQLException {
        String sql = "CALL trabajadores_oficinas()";
        try (CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Trabajador: " + rs.getString("NOMBRE"));
            }
        }
    }
    
    private static void totalDiasActividadEdificio(Connection conn, String oficio, int idEdificio) throws SQLException {
        String sql = "CALL total_dias_actividad_edificio(?, ?)";
        try (CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, oficio);
            stmt.setInt(2, idEdificio);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Total días: " + rs.getInt("Total_Dias"));
            }
        }
    }
    
    private static void numeroOficios(Connection conn) throws SQLException {
        String sql = "CALL numero_oficios()";
        try (CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                System.out.println("Número de oficios: " + rs.getInt("Num_Oficios"));
            }
        }
    }
    
    private static void tarifaMaximaSupervisor(Connection conn) throws SQLException {
        String sql = "CALL tarifa_maxima_supervisor()";
        try (CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Supervisor: " + rs.getString("Supervisor") + 
                                 ", Tarifa Máxima: " + rs.getDouble("Tarifa_Maxima"));
            }
        }
    }
    
    private static void tarifaMaximaSupervisorMultiple(Connection conn) throws SQLException {
        String sql = "CALL tarifa_maxima_supervisor_multiple()";
        try (CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Supervisor: " + rs.getString("Supervisor") + 
                                 ", Tarifa Máxima: " + rs.getDouble("Tarifa_Maxima"));
            }
        }
    }
    
    private static void tarifaMenorPromedioGeneral(Connection conn) throws SQLException {
        String sql = "CALL tarifa_menor_promedio_general()";
        try (CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Nombre: " + rs.getString("NOMBRE") + 
                                 ", Tarifa: " + rs.getDouble("TARIFA"));
            }
        }
    }
    
    private static void tarifaMenorPromedioOficio(Connection conn) throws SQLException {
        String sql = "CALL tarifa_menor_promedio_oficio()";
        try (CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Nombre: " + rs.getString("NOMBRE") + 
                                 ", Oficio: " + rs.getString("OFICIO") + 
                                 ", Tarifa: " + rs.getDouble("TARIFA"));
            }
        }
    }
    
    private static void tarifaMenorPromedioSupervisor(Connection conn) throws SQLException {
        String sql = "CALL tarifa_menor_promedio_supervisor()";
        try (CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Trabajador: " + rs.getString("NOMBRE") + 
                                 ", Tarifa: " + rs.getDouble("TARIFA") + 
                                 ", Supervisor: " + rs.getString("Supervisor"));
            }
        }
    }
    
    private static void supervisoresTarifaMinima(Connection conn, double tarifaMinima) throws SQLException {
        String sql = "CALL supervisores_tarifa_minima(?)";
        try (CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setDouble(1, tarifaMinima);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Supervisor: " + rs.getString("Supervisor") + 
                                 ", Trabajador: " + rs.getString("Trabajador") + 
                                 ", Tarifa: " + rs.getDouble("TARIFA"));
            }
        }
    }
}

