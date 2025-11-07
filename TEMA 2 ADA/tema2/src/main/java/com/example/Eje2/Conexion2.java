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
        //E3("SE");
        //System.out.println("---------------------------------------");
        //E3("OS");
        //E4();
        //E5("ANTONIO");
        //E6();
        //E7(1000);
        //E8();
        //E9(1300);
        //E10(20);
        //System.out.println("---------------------------------------");
        //E10(30);
        E11(3, 1300);
        System.out.println("---------------------------------------");
        E11(11, 1500);
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

//IV. Obtener toda la información de un centro a partir de su número identificativo.
    public static void E4() throws Exception {
        Connection conexion = con();
        PreparedStatement ps = conexion.prepareStatement("Select * From CENTROS Where numero = 20;");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int n = rs.getInt(1);

            System.out.println(" Numero identificativo: " + n );
        }
    }

// V. Obtener toda la información de un empleado a partir de su nombre. 
    public static void E5(String nombre) throws Exception {
        Connection conexion = con();
        String nomb = "%" + nombre + "%";
        PreparedStatement ps = conexion.prepareStatement("Select * From EMPLEADOS Where nombre like ? ;");
        ps.setString(1, nomb);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int sal = rs.getInt(6);
            int nhijos = rs.getInt(8);
            String nom = rs.getString(9);

            System.out.println(" Nombre: " + nom + " Salario: " + sal + " Numero de hijos: " + nhijos);
        }
    }

//VI. Obtener el nombre de todos los empleados que cumplen años en los enero. Haz lo mismo para los empleados que cumplen años en noviembre. 
        public static void E6() throws Exception {
        Connection conexion = con();
        PreparedStatement ps = conexion.prepareStatement("Select nombre From EMPLEADOS Where month(f_nac) = 01;");
        ResultSet rs = ps.executeQuery();

        System.out.println("Empleados que cumplen año en Enero:");
        while (rs.next()) {
            String nom = rs.getString(1);
            System.out.println(" Nombre: " + nom );
        }
        PreparedStatement ps2 = conexion.prepareStatement("Select nombre From EMPLEADOS Where month(f_nac) = 11;");
        ResultSet rs2 = ps2.executeQuery();

        System.out.println("Empleados que cumplen año en Noviembre:");
        while (rs2.next()) {
            String nom2 = rs2.getString(1);
            System.out.println(" Nombre: " + nom2 );
        }
    }
// VII. Hallar, por orden de número de empleado, el nombre y el salario total (salario más comisión) de los empleados cuyo salario total supera un determinado valor en euros mensuales. Prueba con varios valores.  
        public static void E7(int valor) throws Exception {
        Connection conexion = con();
        PreparedStatement ps = conexion.prepareStatement("Select cod, nombre, (salario + comision) as 'salario_total' From EMPLEADOS Where (salario + comision) > ? Order By cod;");
        ps.setInt(1, valor);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String nom = rs.getString(2);
            int sal = rs.getInt(3);

            System.out.println(" Nombre: " + nom + " Salario total: " + sal );
        }
    }  
//VIII. Hallar el número de empleados y de extensiones telefónicas distintas de cada departamento.
        public static void E8() throws Exception {
        Connection conexion = con();
        PreparedStatement ps = conexion.prepareStatement("Select departamento, count(*) as 'num_empleados', Count(Distinct telefono) as 'extensiones_distintas' From EMPLEADOS Group By departamento;");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int dep = rs.getInt(1);
            int nEmp = rs.getInt(2);
            int nExt = rs.getInt(3);

            System.out.println(" Departamento: " + dep + " Numero de empleados: " + nEmp + " Extensiones distintas: " + nExt );
        }
    } 
//IX. Obtener el número de empleados y el nombre de los que cobras más de un determinado valor en cada departamento.
    public static void E9(int valor) throws Exception {
        Connection conexion = con();
        PreparedStatement ps = conexion.prepareStatement("Select departamento, count(*) as 'num_empleados' From EMPLEADOS Where salario > ? Group By departamento;");
        ps.setInt(1, valor);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int dep = rs.getInt(1);
            int nEmp = rs.getInt(2);

            System.out.println(" Departamento: " + dep + " Numero de empleados que cobran más de " + valor + ": " + nEmp );
        }
    }  
//X. Obtener el nombre el nombre, sueldo y fecha de nacimiento de los empleados que tenían más 20 años cuando entraron a la empresa. Haz los mismo para los que tenían más de 30 y más de 40.  
    public static void E10(int edad) throws Exception {
        Connection conexion = con();
        PreparedStatement ps = conexion.prepareStatement("Select nombre, salario, f_nac From EMPLEADOS Where (YEAR(f_ing) - YEAR(f_nac)) > ?");
        ps.setInt(1, edad);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String nom = rs.getString(1);
            int sal = rs.getInt(2);
            String fNac = rs.getString(3);

            System.out.println("Nombre: " + nom + " Salario: " + sal + " Fecha de nacimiento: " + fNac);
        }
    }

//XI. Hallar el nombre, código identificativo, sueldo y fecha de ingreso en la empresa de los empleados que lleven más de 3 años en la empresa y su sueldo sea menor de 1300. Haz lo mismo para los empleados que lleven más de 5 años y su sueldo sea menor de 1500. 
    public static void E11(int años, int sueldo) throws Exception {
        Connection conexion = con();
        PreparedStatement ps = conexion.prepareStatement("Select nombre, cod, salario, f_ing From EMPLEADOS Where (YEAR(CURDATE()) - YEAR(f_ing)) > ? AND salario < ?");
        ps.setInt(1, años);
        ps.setInt(2, sueldo);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String nom = rs.getString(1);
            int cod = rs.getInt(2);
            int sal = rs.getInt(3);
            String fIng = rs.getString(4);

            System.out.println("Nombre: " + nom + " Codigo: " + cod + " Salario: " + sal + " Fecha de ingreso: " + fIng);
        }
    }
}
