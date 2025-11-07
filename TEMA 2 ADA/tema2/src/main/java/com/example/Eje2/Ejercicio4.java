package com.example.Eje2;

import java.sql.*;
import java.util.Scanner;

public class Ejercicio4 {
    private static final String URL = "jdbc:mysql://localhost:3306/empresa_trabajadores";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                Scanner scanner = new Scanner(System.in)) {

            // a(conn, 10.0, 12.0);
        System.out.println("---------------------------------------");
            //b(conn, 312);
        System.out.println("---------------------------------------");
            //c(conn);
        System.out.println("---------------------------------------");
            //d(conn);
        System.out.println("---------------------------------------");
            //e(conn, "FONTANERO", 312);
        System.out.println("---------------------------------------");
            //f(conn);
        System.out.println("---------------------------------------");
            //g(conn);
        System.out.println("---------------------------------------");
            //h(conn);
        System.out.println("---------------------------------------");
            //i(conn);
        System.out.println("---------------------------------------");
            //j(conn);
        System.out.println("---------------------------------------");
            //k(conn);
        System.out.println("---------------------------------------");
            //l(conn, 14.0);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // a) Nombre de los trabajadores cuya tarifa este entre dos extremos. Por
    // ejemplo, prueba con entre 10 y 12 euros.
    private static void a(Connection conn, double min, double max) throws SQLException {
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

    // b) ¿Cuáles son los oficios de los trabajadores asignados un edificio?
    private static void b(Connection conn, int idEdificio) throws SQLException {
        String sql = "CALL oficios_edificio(?)";
        try (CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, idEdificio);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Oficio: " + rs.getString("OFICIO"));
            }
        }
    }

    // c) Indicar el nombre del trabajador y el de su supervisor.
    private static void c(Connection conn) throws SQLException {
        String sql = "CALL trabajador_supervisor()";
        try (CallableStatement stmt = conn.prepareCall(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Trabajador: " + rs.getString("Trabajador") +
                        ", Supervisor: " + rs.getString("Supervisor"));
            }
        }
    }

    // d) Nombre de los trabajadores asignados a oficinas.
    private static void d(Connection conn) throws SQLException {
        String sql = "CALL trabajadores_oficinas()";
        try (CallableStatement stmt = conn.prepareCall(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Trabajador: " + rs.getString("NOMBRE"));
            }
        }
    }

    // e) ¿Cuál es el número total de días que se han dedicado a una actividad
    // determinada (por ejemplo, fontanería) en un edificio concreto (por ejemplo,
    // el 312)?
    private static void e(Connection conn, String oficio, int idEdificio) throws SQLException {
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

    // f) ¿Cuántos tipos de oficios diferentes hay?
    private static void f(Connection conn) throws SQLException {
        String sql = "CALL numero_oficios()";
        try (CallableStatement stmt = conn.prepareCall(sql);
                ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                System.out.println("Número de oficios: " + rs.getInt("Num_Oficios"));
            }
        }
    }

    // g)Para cada supervisor, ¿Cuál es la tarifa por hora más alta que se paga a un
    // trabajador que informa a ese supervisor?
    private static void g(Connection conn) throws SQLException {
        String sql = "CALL tarifa_maxima_supervisor()";
        try (CallableStatement stmt = conn.prepareCall(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Supervisor: " + rs.getString("Supervisor") +
                        ", Tarifa Máxima: " + rs.getDouble("Tarifa_Maxima"));
            }
        }
    }

    // h) Para cada supervisor que supervisa a más de un trabajador, ¿cuál es la
    // tarifa más alta que se para a un trabajador que informa a ese supervisor?
    private static void h(Connection conn) throws SQLException {
        String sql = "CALL tarifa_maxima_supervisor_multiple()";
        try (CallableStatement stmt = conn.prepareCall(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Supervisor: " + rs.getString("Supervisor") +
                        ", Tarifa Máxima: " + rs.getDouble("Tarifa_Maxima"));
            }
        }
    }

    // i) ¿Qué trabajadores reciben una tarifa por hora menor que la del promedio?
    private static void i(Connection conn) throws SQLException {
        String sql = "CALL tarifa_menor_promedio_general()";
        try (CallableStatement stmt = conn.prepareCall(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Nombre: " + rs.getString("NOMBRE") +
                        ", Tarifa: " + rs.getDouble("TARIFA"));
            }
        }
    }

    // j) ¿Qué trabajadores reciben una tarifa por hora menor que la del promedio de
    // los trabajadores que tienen su mismo oficio?
    private static void j(Connection conn) throws SQLException {
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

    // k) ¿Qué trabajadores reciben una tarifa por hora menor que la del promedio de
    // los trabajadores que dependen del mismo supervisor que él?
    private static void k(Connection conn) throws SQLException {
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

    // l) ¿Qué supervisores tienen trabajadores que tienen una tarifa por hora por
    // encima un determinado valor?
    private static void l(Connection conn, double tarifaMinima) throws SQLException {
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
