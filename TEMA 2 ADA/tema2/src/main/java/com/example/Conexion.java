package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Conexion {
        private String user = "root";
        private String passwd = "1234";
        private String url = "jdbc:mysql://localhost:3306/tienda";
        private Connection con; 


        public Conexion()throws Exception{
            con = DriverManager.getConnection(url, user, passwd);
        }
        public void actualizar(String sql)throws Exception {
            Statement st=con.createStatement();
            int n=st.executeUpdate(sql);
            if(n > 0){
                System.out.println("Se ha realizado la operacion y ha afectado a " +n);
            }
            st.close();
        }
        public void insertar(String sql)throws Exception {
            Statement st=con.createStatement();
            int n=st.executeUpdate(sql);
            if(n > 0){
                System.out.println("Se ha realizado la operacion y ha afectado a " +n);
            }
            st.close();
        }
        public void borrar(String sql)throws Exception {
            Statement st=con.createStatement();
            int n=st.executeUpdate(sql);
            if(n > 0){
                System.out.println("Se ha realizado la operacion y ha afectado a " +n);
            }
            st.close();
        }
        public void cerrar() throws Exception{
            con.close();
        }
}
