package com.example.Eje2;

    import java.sql.*;
import java.util.Scanner;

public class Ejercicio5 {
    private static final String URL = "jdbc:mysql://localhost:3306/Empresa";
    private static final String USER = "tu_usuario";
    private static final String PASSWORD = "tu_password";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Scanner scanner = new Scanner(System.in)) {
            
            System.out.println("=== EJERCICIO 5 - TRANSACCIONES ===");
            
            // A. Cambiar director de funciones a propiedad y subir sueldo 20%
            System.out.println("\nA. Cambiar director de funciones a propiedad:");
            cambiarDirectorPropiedad(conn, 112); // Ejemplo: departamento 112
            
            // B. Cambiar nombre de centro y sus departamentos
            System.out.println("\nB. Cambiar nombre de centro y departamentos:");
            cambiarNombreCentroDepartamentos(conn, 10, "Nuevo Centro", "Nuevo Dept");
            
            // C. Eliminar empleado y disminuir presupuesto 5%
            System.out.println("\nC. Eliminar empleado y disminuir presupuesto:");
            eliminarEmpleadoDisminuirPresupuesto(conn, 123); // Ejemplo: empleado 123
            
            // D. Aumentar presupuesto 20% y contratar 4 empleados
            System.out.println("\nD. Aumentar presupuesto y contratar empleados:");
            aumentarPresupuestoContratar(conn, 112); // Ejemplo: departamento 112
            
            // E. Aumentar hijos y cambiar a director en propiedad
            System.out.println("\nE. Aumentar hijos y cambiar a director propiedad:");
            aumentarHijosDirectorPropiedad(conn, 124); // Ejemplo: empleado 124
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // A. Cambiar director de funciones a propiedad y subir sueldo 20%
    private static void cambiarDirectorPropiedad(Connection conn, int numeroDepartamento) throws SQLException {
        conn.setAutoCommit(false);
        try {
            // 1. Actualizar tipo de director a 'P' (Propiedad)
            String sql1 = "UPDATE DEPARTAMENTOS SET Tipo_dir = 'P' WHERE Numero = ? AND Tipo_dir = 'F'";
            try (PreparedStatement stmt1 = conn.prepareStatement(sql1)) {
                stmt1.setInt(1, numeroDepartamento);
                int filasActualizadas = stmt1.executeUpdate();
                
                if (filasActualizadas > 0) {
                    // 2. Obtener el director del departamento
                    String sql2 = "SELECT Director FROM DEPARTAMENTOS WHERE Numero = ?";
                    int directorId = 0;
                    try (PreparedStatement stmt2 = conn.prepareStatement(sql2)) {
                        stmt2.setInt(1, numeroDepartamento);
                        ResultSet rs = stmt2.executeQuery();
                        if (rs.next()) {
                            directorId = rs.getInt("Director");
                        }
                    }
                    
                    // 3. Aumentar salario 20%
                    String sql3 = "UPDATE EMPLEADOS SET Salario = Salario * 1.20 WHERE Cod = ?";
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
    
    // B. Cambiar nombre de centro y de todos sus departamentos
    private static void cambiarNombreCentroDepartamentos(Connection conn, int numeroCentro, String nuevoNombreCentro, String sufijoDept) throws SQLException {
        conn.setAutoCommit(false);
        try {
            // 1. Actualizar nombre del centro
            String sql1 = "UPDATE CENTROS SET Nombre = ? WHERE Numero = ?";
            try (PreparedStatement stmt1 = conn.prepareStatement(sql1)) {
                stmt1.setString(1, nuevoNombreCentro);
                stmt1.setInt(2, numeroCentro);
                stmt1.executeUpdate();
            }
            
            // 2. Actualizar nombres de departamentos del centro
            String sql2 = "UPDATE DEPARTAMENTOS SET Nombre = CONCAT(Nombre, ?) WHERE Centro = ?";
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
    
    // C. Eliminar empleado y disminuir presupuesto del departamento 5%
    private static void eliminarEmpleadoDisminuirPresupuesto(Connection conn, int codEmpleado) throws SQLException {
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
    
    // D. Aumentar presupuesto 20% y contratar 4 nuevos empleados
    private static void aumentarPresupuestoContratar(Connection conn, int numeroDepartamento) throws SQLException {
        conn.setAutoCommit(false);
        try {
            // 1. Aumentar presupuesto 20%
            String sql1 = "UPDATE DEPARTAMENTOS SET Presupuesto = Presupuesto * 1.20 WHERE Numero = ?";
            try (PreparedStatement stmt1 = conn.prepareStatement(sql1)) {
                stmt1.setInt(1, numeroDepartamento);
                stmt1.executeUpdate();
            }
            
            // 2. Contratar 4 nuevos empleados
            int[] nuevosEmpleados = {1001, 1002, 1003, 1004};
            String[] nombres = {"Juan Pérez", "María García", "Carlos López", "Ana Martínez"};
            
            String sql2 = "INSERT INTO EMPLEADOS (Cod, Departamento, Nombre, Fecha_ingreso, Salario) VALUES (?, ?, ?, CURDATE(), ?)";
            try (PreparedStatement stmt2 = conn.prepareStatement(sql2)) {
                for (int i = 0; i < nuevosEmpleados.length; i++) {
                    stmt2.setInt(1, nuevosEmpleados[i]);
                    stmt2.setInt(2, numeroDepartamento);
                    stmt2.setString(3, nombres[i]);
                    stmt2.setDouble(4, 1200.0); // Salario base
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
    
    // E. Aumentar número de hijos y cambiar a director en propiedad
    private static void aumentarHijosDirectorPropiedad(Connection conn, int codEmpleado) throws SQLException {
        conn.setAutoCommit(false);
        try {
            // 1. Verificar si el empleado es director en funciones
            String sql1 = "SELECT d.Numero FROM DEPARTAMENTOS d WHERE d.Director = ? AND d.Tipo_dir = 'F'";
            int departamentoId = 0;
            try (PreparedStatement stmt1 = conn.prepareStatement(sql1)) {
                stmt1.setInt(1, codEmpleado);
                ResultSet rs = stmt1.executeQuery();
                if (rs.next()) {
                    departamentoId = rs.getInt("Numero");
                }
            }
            
            if (departamentoId > 0) {
                // 2. Aumentar número de hijos
                String sql2 = "UPDATE EMPLEADOS SET Num_hijos = Num_hijos + 1 WHERE Cod = ?";
                try (PreparedStatement stmt2 = conn.prepareStatement(sql2)) {
                    stmt2.setInt(1, codEmpleado);
                    stmt2.executeUpdate();
                }
                
                // 3. Cambiar a director en propiedad
                String sql3 = "UPDATE DEPARTAMENTOS SET Tipo_dir = 'P' WHERE Numero = ?";
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