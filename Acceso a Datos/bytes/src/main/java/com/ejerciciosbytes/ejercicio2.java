package com.ejerciciosbytes;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class ejercicio2 {
    public static void main (String[] args){
        String ruta = "parejas.dat";
        try(DataOutputStream dos = new DataOutputStream(new FileOutputStream(ruta));
        Scanner sc = new Scanner(System.in)){
            while (true) {
                System.out.println("Dame el primer numero (Dale ENTER para  finalizar)");
                String linea = sc.nextLine().trim();
                
                if (linea.isEmpty()){
                    break;
                }
                int n1 = Integer.parseInt(linea);

                System.out.println("Dame el segundo numero");
                linea = sc.nextLine().trim();
                if (linea.isEmpty()){
                    break;
                }
                int n2 = Integer.parseInt(linea);

                dos.writeInt(n1);
                dos.writeInt(n2);

            }
                System.out.println("Archivo creado correctamente.");
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}





