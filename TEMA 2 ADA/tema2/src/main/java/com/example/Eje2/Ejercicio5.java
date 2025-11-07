package com.example.Eje2;

import java.sql.*;
import java.util.Scanner;

public class Ejercicio5 {
    private static final String URL = "jdbc:mysql://localhost:3306/Empresa";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                Scanner scanner = new Scanner(System.in)) {

            //a(conn, 111);
        System.out.println("---------------------------------------");
            //b(conn, 20, "Nuevo Centro", "Nuevo Dept");
        System.out.println("---------------------------------------");
            //c(conn, 110);
        System.out.println("---------------------------------------");
            //d(conn, 112);

            e(conn, 110);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // A. Cambiar un director de en funciones a en propiedad, se le debe subir el
    // sueldo un 20%.
    private static void a(Connection conn, int numeroDepartamento) throws SQLException {
        conn.setAutoCommit(false);
        try {
            // 1. Actualizar tipo de director a 'P' (Propiedad)
            String sql1 = "UPDATE DEPARTAMENTOS SET tipo_dir = 'P' WHERE numero = ? AND tipo_dir = 'F'";
            try (PreparedStatement stmt1 = conn.prepareStatement(sql1)) {
                stmt1.setInt(1, numeroDepartamento);
                int filasActualizadas = stmt1.executeUpdate();

                if (filasActualizadas > 0) {
                    // 2. Obtener el director del departamento
                    String sql2 = "SELECT director FROM DEPARTAMENTOS WHERE numero = ?";
                    int directorId = 0;
                    try (PreparedStatement stmt2 = conn.prepareStatement(sql2)) {
                        stmt2.setInt(1, numeroDepartamento);
                        ResultSet rs = stmt2.executeQuery();
                        if (rs.next()) {
                            directorId = rs.getInt("director");
                        }
                    }

                    // 3. Aumentar salario 20%
                    String sql3 = "UPDATE EMPLEADOS SET salario = salario * 1.20 WHERE cod = ?";
                    try (PreparedStatement stmt3 = conn.prepareStatement(sql3)) {
                        stmt3.setInt(1, directorId);
                        stmt3.executeUpdate();
                    }

                    conn.commit();
                    System.out.println("Director cambiado a propiedad y salario aumentado 20%");
                } else {
                    conn.rollback();
                    System.out.println("No se encontró departamento con director en funciones");
                }
            }
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    // B. Cambiar el nombre de un centro y de todos los departamentos que se
    // encuentran en ese centro.
    private static void b(Connection conn, int numeroCentro, String nuevoNombreCentro, String sufijoDept) throws SQLException {
        conn.setAutoCommit(false);
        try {
            // 1. Actualizar nombre del centro
            String sql1 = "UPDATE CENTROS SET nombre = ? WHERE numero = ?";
            try (PreparedStatement stmt1 = conn.prepareStatement(sql1)) {
                stmt1.setString(1, nuevoNombreCentro);
                stmt1.setInt(2, numeroCentro);
                stmt1.executeUpdate();
            }

            // 2. Actualizar nombres de departamentos del centro
            String sql2 = "UPDATE DEPARTAMENTOS SET nombre = CONCAT(nombre, ?) WHERE centro = ?";
            try (PreparedStatement stmt2 = conn.prepareStatement(sql2)) {
                stmt2.setString(1, " - " + sufijoDept);
                stmt2.setInt(2, numeroCentro);
                stmt2.executeUpdate();
            }

            conn.commit();
            System.out.println("Nombre de centro y departamentos actualizados");
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    // C. Eliminar un empleado y disminuir el presupuesto del departamento donde
    // trabajaba un 5%.
    private static void c(Connection conn, int codEmpleado) throws SQLException {
        conn.setAutoCommit(false);
        try {
            // 1. Obtener departamento del empleado
            String sql1 = "SELECT Departamento FROM EMPLEADOS WHERE Cod = ?";
            int departamentoId = 0;
            try (PreparedStatement stmt1 = conn.prepareStatement(sql1)) {
                stmt1.setInt(1, codEmpleado);
                ResultSet rs = stmt1.executeQuery();
                if (rs.next()) {
                    departamentoId = rs.getInt("Departamento");
                }
            }

            if (departamentoId > 0) {
                // 2. Eliminar empleado
                String sql2 = "DELETE FROM EMPLEADOS WHERE Cod = ?";
                try (PreparedStatement stmt2 = conn.prepareStatement(sql2)) {
                    stmt2.setInt(1, codEmpleado);
                    stmt2.executeUpdate();
                }

                // 3. Disminuir presupuesto del departamento 5%
                String sql3 = "UPDATE DEPARTAMENTOS SET Presupuesto = Presupuesto * 0.95 WHERE Numero = ?";
                try (PreparedStatement stmt3 = conn.prepareStatement(sql3)) {
                    stmt3.setInt(1, departamentoId);
                    stmt3.executeUpdate();
                }

                conn.commit();
                System.out.println("Empleado eliminado y presupuesto disminuido 5%");
            } else {
                conn.rollback();
                System.out.println("No se encontró el empleado");
            }
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    // D. Aumentar el presupuesto de departamento un 20% y contratar a 4 nuevos
    // empleados que trabajen en ese departamento.
    private static void d(Connection conn, int numeroDepartamento) throws SQLException {
        conn.setAutoCommit(false);
        try {
            // 1. Aumentar presupuesto 20%
            String sql1 = "UPDATE DEPARTAMENTOS SET presupuesto = presupuesto * 1.20 WHERE numero = ?";
            try (PreparedStatement stmt1 = conn.prepareStatement(sql1)) {
                stmt1.setInt(1, numeroDepartamento);
                stmt1.executeUpdate();
            }

            // 2. Contratar 4 nuevos empleados
            int[] nuevosEmpleados = { 1001, 1002, 1003, 1004 };
            String[] nombres = { "Juan Pérez", "María García", "Carlos López", "Ana Martínez" };

            String sql2 = "INSERT INTO EMPLEADOS (cod, departamento, nombre, f_ing, salario) VALUES (?, ?, ?, CURDATE(), ?)";
            try (PreparedStatement stmt2 = conn.prepareStatement(sql2)) {
                for (int i = 0; i < nuevosEmpleados.length; i++) {
                    stmt2.setInt(1, nuevosEmpleados[i]);
                    stmt2.setInt(2, numeroDepartamento);
                    stmt2.setString(3, nombres[i]);
                    stmt2.setDouble(4, 1200.0); 
                    stmt2.addBatch();
                }
                stmt2.executeBatch();
            }

            conn.commit();
            System.out.println("Presupuesto aumentado 20% y 4 empleados contratados");
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    // E. Aumentar el número de hijos de un empleado, que es director del
    // departamento en funciones, hace que pase a ser director en propiedad.
    private static void e(Connection conn, int codEmpleado) throws SQLException {
        conn.setAutoCommit(false);
        try {
            // 1. Verificar si el empleado es director en funciones
            String sql1 = "SELECT d.numero FROM DEPARTAMENTOS d WHERE d.director = ? AND d.tipo_dir = 'F'";
            int departamentoId = 0;
            try (PreparedStatement stmt1 = conn.prepareStatement(sql1)) {
                stmt1.setInt(1, codEmpleado);
                ResultSet rs = stmt1.executeQuery();
                if (rs.next()) {
                    departamentoId = rs.getInt("numero");
                }
            }

            if (departamentoId > 0) {
                // 2. Aumentar número de hijos
                String sql2 = "UPDATE EMPLEADOS SET num_hijos = num_hijos + 1 WHERE cod = ?";
                try (PreparedStatement stmt2 = conn.prepareStatement(sql2)) {
                    stmt2.setInt(1, codEmpleado);
                    stmt2.executeUpdate();
                }

                // 3. Cambiar a director en propiedad
                String sql3 = "UPDATE DEPARTAMENTOS SET tipo_dir = 'P' WHERE numero = ?";
                try (PreparedStatement stmt3 = conn.prepareStatement(sql3)) {
                    stmt3.setInt(1, departamentoId);
                    stmt3.executeUpdate();
                }

                conn.commit();
                System.out.println("Número de hijos aumentado y director cambiado a propiedad");
            } else {
                conn.rollback();
                System.out.println("El empleado no es director en funciones");
            }
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }
}