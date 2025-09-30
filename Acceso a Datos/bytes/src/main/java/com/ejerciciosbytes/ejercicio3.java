package com.ejerciciosbytes;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class ejercicio3 {
    public static void main (String[] args){
        String ruta = "parejas.dat";

        int sumaCol1 = 0;
        int contador = 0;
         long sumaPonderada = 0;
        long sumaPesos = 0;

        try (DataInputStream dis = new DataInputStream(new FileInputStream(ruta))) {
            while (true) {
                int col1 = dis.readInt();
                int col2 = dis.readInt();

                sumaCol1 += col1;
                contador++;

                sumaPonderada += (long) col1 * col2;
                sumaPesos += col2;
            }
        } catch (IOException e) {
        }

        if (contador > 0) {
            double mediaArit = (double) sumaCol1 / contador;
            double mediaPond = (sumaPesos > 0) ? (double) sumaPonderada / sumaPesos : 0;

            System.out.println("Media aritmética columna 1: " + mediaArit);
            System.out.println("Media ponderada columna 1: " + mediaPond);
        } else {
            System.out.println("No se encontraron datos.");
        }
    }
}