/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblio.Files;

import biblio.Models.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 *
 * @author Gamcas
 */
public class ObjectWrite {

    public static boolean saveEstudent(Estudiante estudiante) {

        File file = new File("DB/estudiantes/" + estudiante.getCarnet() + ".est");

        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
                ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);) {
            outputStream.writeObject(estudiante);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error de conexion con el archivo");
        }
        return true;
    }

    public static boolean saveLibro(Libro libro) {

        File file = new File("DB/libros/" + libro.getCodigo() + ".lb");

        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
                ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);) {
            outputStream.writeObject(libro);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error de conexion con el archivo");
        }
        return true;
    }

 
    public static boolean savePrestamo(Prestamo prestamo, int carnet, String libro,int numero) {

        File file = new File("DB/prestamos/" + carnet);
        if (!file.exists()) {
            file.mkdir();
        }

        File childFile = new File("DB/prestamos/" + carnet + "/" + libro +"-"+numero+ ".pres");

        try (FileOutputStream fileOutputStream = new FileOutputStream(childFile);
                ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);) {
            outputStream.writeObject(prestamo);
        } catch (IOException e) {
            System.out.println("Error de conexion con el archivo");
        }

        return true;
    }

}
