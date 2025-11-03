package com.example.Eje2;

import java.sql.*;

public class Conexion {

    public static void main(String[] args) throws Exception{
        Conexion conexion = new Conexion();
        conexion.a();
    }

    public static Connection con()throws Exception {
        String user = "root";
        String passwd = "1234";
        String url = "jdbc:mysql://localhost:3306/Empresa";
        return DriverManager.getConnection(url, user, passwd);
    }

    public void a()throws Exception{
        Connection conexion = con ();
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery("Select comision, nombre , salario From EMPLEADOS Where num_hijos > 3 Order By comision, nombre;");

        while (rs.next()){
            int con = rs.getInt(1);
            String nom = rs.getString(2);
            int sal = rs.getInt(3);

            System.out.println("Comision: " + con + " Nombre: " + nom + " Salario: " + sal);
        }
    }
}
