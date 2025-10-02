package ejerciciosficheros;

import java.io.*;

public class Ejercicio1 {
    public static void main(String[] args) {
        if (args.length < 1){
            System.out.println("Uso java EliminarEspacios <ficheros>");
            return;
        }
        String nombreFcihero = args[0];

        try(RandomAccessFile raf = new RandomAccessFile(nombreFcihero, "rw")){
            long length = raf.length();
            long posEscritura = 0;

            for (long i = 0; i < length; i++){
                raf.seek(i);
                char c = (char) raf.readByte();
                if (c != ' ') {
                    c = Character.toUpperCase(c);
                    raf.seek(posEscritura);
                    raf.writeByte(c);
                    posEscritura++;
                }
            }

            raf.setLength(posEscritura);

            System.out.println("Fichero procesado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al modificar el fichero: " + e.getMessage());
        }
    }
}






 