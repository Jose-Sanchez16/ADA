package ejerciciosficheros;
import java.io.*;
import java.util.Scanner;

public class Ejercicio2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String fichero = "numeros.dat";

        try (RandomAccessFile raf = new RandomAccessFile(fichero, "rw")) {
            System.out.println("Introduce números enteros (escribe 'fin' para terminar):");
            while (true) {
                String entrada = sc.nextLine();
                if (entrada.equalsIgnoreCase("fin")) break;
                try {
                    int num = Integer.parseInt(entrada);
                    raf.writeInt(num);
                } catch (NumberFormatException e) {
                    System.out.println("Número no válido");
                }
            }
        } catch (IOException e) {
            System.out.println("Error al guardar los números: " + e.getMessage());
        }

        try (RandomAccessFile raf = new RandomAccessFile(fichero, "rw")) {
            long length = raf.length();
            raf.seek(0);

            while (raf.getFilePointer() < length) {
                long pos = raf.getFilePointer();
                int num = raf.readInt();
                if (num == 5) {
                    raf.seek(pos);  
                    raf.writeInt(0);  
                }
            }

            System.out.println("Se han reemplazado los 5 por 0 en el fichero.");
        } catch (IOException e) {
            System.out.println("Error al modificar el fichero: " + e.getMessage());
        }
        sc.close();
    }
}