package com.ejerciciosbytes;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String rutaOriginal ="/home/alumnadotarde/Escritorio/Antony.png"; 
        String rutaCopia ="/home/alumnadotarde/Escritorio/AntonioDeTriana.png";

        try (FileInputStream fis = new FileInputStream(rutaOriginal);
            FileOutputStream fos = new FileOutputStream(rutaCopia)){
            
                byte[] buffer = new byte[1024];
                int bytesLeidos;

                while ((bytesLeidos = fis.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesLeidos); 
                }
                System.out.println("Copia realizada con éxito.");
            } catch(IOException e){
                e.printStackTrace();
            }
    }
}
