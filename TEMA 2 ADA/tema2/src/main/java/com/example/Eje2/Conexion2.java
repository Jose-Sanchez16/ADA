package com.example.Eje2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Conexion2 {
    public static void main(String[] args) throws Exception{
        //E1();
        //E2(1100, 1200);
        //System.out.println("---------------------------------------");
        //E2(1300, 1400);
        E3("SE");
        System.out.println("---------------------------------------");
        E3("OS");
        
    }
    public static Connection con() throws Exception {
        String user = "root";
        String passwd = "1234";
        String url = "jdbc:mysql://localhost:3306/Empresa";
        return DriverManager.getConnection(url, user, passwd);
    }
// I. Hallar la comisión, el nombre y el salario de los empleados con más de 1, 2 ,3, 4 y 5 hijos, ordenados por comisión y, dentro de comisión, alfabéticamente. 
    public static void E1() throws Exception {
        Connection conexion = con();
        PreparedStatement ps = conexion.prepareStatement("Select comision, nombre , salario From EMPLEADOS Where num_hijos > 1 Order By comision, nombre;");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int con = rs.getInt(1);
            String nom = rs.getString(2);
            int sal = rs.getInt(3);

            System.out.println("Comision: " + con + " Nombre: " + nom + " Salario: " + sal);
        }
    }
// II. Obtener, por orden alfabético, los nombres y los salarios de los empleados cuyo salario esté comprendido entre dos extremos. Probar con varios valores para ambos extremos.  
    public static void E2(int s1, int s2) throws Exception {
        Connection conexion = con();
        PreparedStatement ps = conexion.prepareStatement("Select nombre , salario From EMPLEADOS Where salario between ? and ? Order By nombre;");
        ps.setInt(1, s1);
        ps.setInt(2, s2);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String nom = rs.getString(1);
            int sal = rs.getInt(2);

            System.out.println(" Nombre: " + nom + " Salario: " + sal);
        }
    }
    
// III. Obtener, por orden alfabético, los nombres de los departamentos que contengan la palabra una determinada palabra. Prueba con varias palabras.  
    public static void E3(String palabra) throws Exception {
        Connection conexion = con();
        String palabra2 = "%" + palabra + "%";
        PreparedStatement ps = conexion.prepareStatement("Select nombre From DEPARTAMENTOS Where nombre Like ? Order By nombre;");
        ps.setString(1, palabra2);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String nom = rs.getString(1);

            System.out.println(" Nombre: " + nom );
        }
    }
}
