package com.ejerciciosfile;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Movemos "rutaBase" al Escritorio
        String rutaBase = System.getProperty("user.home") + "/Escritorio";

        // Ejercicio 1
        crearEstructura("/home/alumnadotarde/Escritorio");

        // Ejercicio 2
        File raiz = new File(rutaBase, "d");
        System.out.println("\n Listado ");
        listarDirectorio(raiz, "");

        // Ejercicio 3
        System.out.println("LISTADO MODIFICADO");
        listarModificar(rutaBase + "/d");

        // Ejercicio 4
        System.out.println("Archivos .txt en d1 ");
        listarPorExtension(rutaBase + "/d/d1", "");

        // Ejercicio 5
        System.out.println("Borrando archivos .txt en d1 ");
        archivosBorrar(rutaBase + "/d/d1");
    }

    // Crear
    public static void crearEstructura(String rutaBase) {
        try {
            File d = new File(rutaBase, "d");
            d.mkdir();

            File d1 = new File(d, "d1");
            d1.mkdir();
            new File(d1, "f11.txt").createNewFile();
            new File(d1, "f12.txt").createNewFile();

            File d2 = new File(d, "d2");
            d2.mkdir();
            File d21 = new File(d2, "d21");
            d21.mkdir();
            new File(d2, "f21.txt").createNewFile();
            File d22 = new File(d2, "d22");
            d22.mkdir();
            new File(d22, "f222.txt").createNewFile();

            File d3 = new File(d, "d3");
            d3.mkdirs();
            File d31 = new File(d3, "d31");
            d31.mkdir();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Listar
    public static void listarDirectorio(File dir, String prefijo) {
        if (dir.isDirectory()) {
            System.out.println(prefijo + dir.getName());
            File[] hijos = dir.listFiles();
            if (hijos != null) {
                for (File f : hijos) {
                    listarDirectorio(f, prefijo + "         ");
                }
            }
        } else {
            System.out.println(prefijo + dir.getName());
        }
    }

    // Modificación del 2
    public static void listarModificar(String ruta) {
        File raiz = new File(ruta);
        if (raiz.exists()) {
            listarDirectorio(raiz, "");
        } else {
            System.out.println("El directorio no existe");
        }
    }

    // Mostrar archivos
    public static void listarPorExtension(String ruta, String extension) {
        System.out.println("Contenidos del directorio " + ruta);

        File raiz = new File(ruta);
        File[] hijos = raiz.listFiles();

        for (File f : hijos) {
            if (f.getName().contains(extension)) {
                System.out.println(f.getName());
            }
        }
    }

    // Eliminar archivos
    public static void archivosBorrar(String ruta) {
        File raiz = new File(ruta);
        File[] hijos = raiz.listFiles();

        for (File f : hijos) {
            if (f.getName().contains(".txt")) {
                f.delete();
            }
        }

        System.out.println("Los archivos se eliminaron correctamente.");
    }
}