package com.example.Eje2;

import java.sql.*;

public class Conexion {

    public static void main(String[] args) throws Exception {
        // a();
        // b();
        // c();
        // d();
        // e();
        // f();
        // g();
        // h();
        // i();
        // j();
        // k();
        l();    
    }

    public static Connection con() throws Exception {
        String user = "root";
        String passwd = "1234";
        String url = "jdbc:mysql://localhost:3306/Empresa";
        return DriverManager.getConnection(url, user, passwd);
    }
// Hallar la comisión, el nombre y el salario de los empleados con más de tres hijos, ordenados por comisión y, dentro de comisión, alfabéticamente. 
    public static void a() throws Exception {
        Connection conexion = con();
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery("Select comision, nombre , salario From EMPLEADOS Where num_hijos > 3 Order By comision, nombre;");

        while (rs.next()) {
            int con = rs.getInt(1);
            String nom = rs.getString(2);
            int sal = rs.getInt(3);

            System.out.println("Comision: " + con + " Nombre: " + nom + " Salario: " + sal);
        }
    }
// Obtener los nombres de los departamentos que no dependen de otros. 
    public static void b() throws Exception {
        Connection conexion = con();
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery("Select nombre From DEPARTAMENTOS Where depto_jefe is null;");

        while (rs.next()) {
            String nom = rs.getString(1);

            System.out.println(nom);
        }
    }

// Obtener, por orden alfabético, los nombres y los salarios de los empleados cuyo salario esté comprendido entre 1250 y 1300 euros.
    public static void c() throws Exception {
        Connection conexion = con();
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery("Select nombre, salario From EMPLEADOS Where salario Between 1250 and 1300 Order By nombre;");

        while (rs.next()) {
            String nom = rs.getString(1);
            int sal = rs.getInt(2);

            System.out.println(nom + ", " + sal);
        }
    }

// Datos de los empleados que cumplen la condición anterior o tienen al menos un hijo. 
    public static void d() throws Exception {
        Connection conexion = con();
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery("Select nombre, salario From EMPLEADOS Where salario Between 1250 and 1300 Or num_hijos >= 1 Order By nombre;");

        while (rs.next()) {
            String nom = rs.getString(1);
            int sal = rs.getInt(2);

            System.out.println(nom + ", " + sal);
        }
    }

// Obtener, por orden alfabético, los nombres de los departamentos que no contengan la palabra 'Dirección' ni 'Sector'. 
    public static void e() throws Exception {
        Connection conexion = con();
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery("Select nombre From DEPARTAMENTOS Where nombre not like 'DIRECCION%' and nombre not like 'SECTOR%' order by nombre ;");

        while (rs.next()) {
            String nom = rs.getString(1);

            System.out.println(nom);
        }
    }

// Obtener, por orden alfabético, los nombres de los departamentos que, o bien tienen directores en funciones y su presupuesto no excede los 5 mil euros, o bien no dependen de ningún otro departamento. 
    public static void f() throws Exception {
        Connection conexion = con();
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery("Select nombre From DEPARTAMENTOS Where (tipo_dir = 'F' and presupuesto <= 5) or depto_jefe is null order by nombre;");

        while (rs.next()) {
            String nom = rs.getString(1);

            System.out.println(nom);
        }
    }

// Hallar, por orden de número de empleado, el nombre y el salario total (salario más comisión) de los empleados cuyo salario total supera los 1300 euros mensuales. 
    public static void g() throws Exception {
        Connection conexion = con();
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery("Select nombre, (salario + comision) as salario_total From EMPLEADOS Where (salario + comision) > 1300 Order By cod;");

        while (rs.next()) {
            String nom = rs.getString(1);
            int salTotal = rs.getInt(2);

            System.out.println(nom);
            System.out.println(salTotal);
        }
    }

// Hallar el número de empleados de toda la empresa. 
    public static void h() throws Exception {
        Connection conexion = con();
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery("Select count(*) as total_empleados From EMPLEADOS;");

        while (rs.next()) {
            int n = rs.getInt(1);

            System.out.println(n);
        }
    }

// Hallar cuántos departamentos existen y el presupuesto anual medio de la empresa para el global de todos los departamentos. 
    public static void i() throws Exception {
        Connection conexion = con();
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery("Select count(*) as num_departamentos, AVG(presupuesto) as presupuesto_medio From DEPARTAMENTOS ;");

        while (rs.next()) {
            int n = rs.getInt(1);
            double sal = rs.getInt(2);

            System.out.println("Numero de departamentos: " + n);
            System.out.println("Presupuesto medio: " + sal);
        }
    }

// Hallar el número de empleados y de extensiones telefónicas distintas del departamento 112. 
    public static void j() throws Exception {
        Connection conexion = con();
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery("Select count(*) as num_empleados, COUNT(DISTINCT telefono) as extensiones_distintas From EMPLEADOS Where departamento = 112;");

        while (rs.next()) {
            int n = rs.getInt(1);
            int num = rs.getInt(2);

            System.out.println("Numero de empleados: " + n);
            System.out.println("Distintos al 112: " + num);
        }
    }
    
// Utilice las operaciones de conjuntos para extraer los códigos de los departamentos que no hacen de departamento jefe. 
    public static void k() throws Exception {
        Connection conexion = con();
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery("Select numero From DEPARTAMENTOS Where numero not in (Select distinct depto_jefe From DEPARTAMENTOS Where depto_jefe is not null);");

        while (rs.next()) {
            int n = rs.getInt(1);

            System.out.println("Departamentos no jefe: " + n);
        }
    }

// Ídem pero que sí hacen de departamento jefe de algún otro departamento.
    public static void l() throws Exception {
        Connection conexion = con();
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery("Select numero From DEPARTAMENTOS Where numero in (Select distinct depto_jefe From DEPARTAMENTOS Where depto_jefe is not null);");

        while (rs.next()) {
            int n = rs.getInt(1);

            System.out.println("Departamentos jefe: " + n);
        }
    }
}

 








