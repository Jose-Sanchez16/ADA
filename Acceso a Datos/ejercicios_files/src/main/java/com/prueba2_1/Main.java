package com.prueba2_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) {
        String ruta = "ejemplo.txt";
        String rutaCopia = "ejemplo_copia.txt";

        // Ejercicio 1
        escribirHolaMundo(ruta);

        // Ejercicio 2
        anadirTexto(ruta, "Esta es una segunda línea");
        anadirTexto(ruta, "Java es divertido");

        // Ejercicio 3
        leerUnaLinea(ruta);

        // Ejercicio 4
        leerVariasLineas(ruta);

        // Ejercicio 5
        contarPalabras(ruta);

        // Ejercicio 6
        copiarTransformado(ruta, rutaCopia);
    }

    // 1. Escribir "Hola mundo" en un fichero
    public static void escribirHolaMundo(String ruta) {
        try (FileWriter fw = new FileWriter(ruta)) {
            fw.write("Hola mundo\n");
            System.out.println("Se escribió 'Hola mundo' en el fichero.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 2. Escribir en el fichero ya creado (modo append)
    public static void anadirTexto(String ruta, String texto) {
        try (FileWriter fw = new FileWriter(ruta, true);
                PrintWriter out = new PrintWriter(fw)) {
            out.println(texto);
            System.out.println("Se añadió texto al fichero.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 3. Leer un fichero (una sola línea)
    public static void leerUnaLinea(String ruta) {
        try (FileReader fr = new FileReader(ruta);
                BufferedReader br = new BufferedReader(fr)) {
            String linea = br.readLine();
            if (linea != null) {
                System.out.println("Primera línea: " + linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 4. Leer un fichero con varias líneas
    public static void leerVariasLineas(String ruta) {
        try (FileReader fr = new FileReader(ruta);
                BufferedReader br = new BufferedReader(fr)) {
            String linea;
            System.out.println("Contenido completo del fichero:");
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 5. Contar el número de palabras de un fichero
    public static void contarPalabras(String ruta) {
        int contador = 0;
        try (FileReader fr = new FileReader(ruta);
                BufferedReader br = new BufferedReader(fr)) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] palabras = linea.trim().split("\\s+");
                if (!linea.trim().isEmpty()) {
                    contador += palabras.length;
                }
            }
            System.out.println("Número de palabras en el fichero: " + contador);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 6. Copiar contenido eliminando espacios y convirtiendo a mayúsculas
    public static void copiarTransformado(String origen, String destino) {
        try (FileReader fr = new FileReader(origen);
                BufferedReader br = new BufferedReader(fr);
                FileWriter fw = new FileWriter(destino);
                PrintWriter out = new PrintWriter(fw)) {

            String linea;
            while ((linea = br.readLine()) != null) {
                String transformada = linea.replaceAll(" ", "").toUpperCase();
                out.println(transformada);
            }
            System.out.println("Se creó el fichero '" + destino + "' transformado.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}