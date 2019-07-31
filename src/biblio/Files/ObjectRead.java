/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblio.Files;

import biblio.Models.Estudiante;
import biblio.Models.Libro;
import biblio.Models.Prestamo;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 *
 * @author Gamcas
 */
public class ObjectRead {

    /*Esta clase contiene metodos que nos leen los archvivos binarios guardados
    *son similares por que no me funciono el casteo
    *juas juas juas
     */
    public static Estudiante readEstudiante(int estudiante) throws IOException {

        File file = new File("DB/estudiantes/" + estudiante + ".est");
        try (FileInputStream fileInputStream = new FileInputStream(file);
                ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);) {
            return (Estudiante) inputStream.readObject();
        } catch (IOException e) {
            System.out.println("no tiene la forma de estudiante");
        } catch (ClassNotFoundException e) {
            System.out.println("El objeto no tiene la forma de un estudiante");
        }
        return null;
    }

    public static Libro readLibro(String libro) throws IOException {
        File file = new File("DB/libros/" + libro + ".lb");
        try (FileInputStream fileInputStream = new FileInputStream(file);
                ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);) {
            return (Libro) inputStream.readObject();
        } catch (IOException e) {
            throw new IOException();

        } catch (ClassNotFoundException e) {
            System.out.println("El objeto no tiene la forma de un usuario");
        }
        return null;
    }

    public static Prestamo readPrestamo(int carnet, String codigo) throws IOException {

        File fil = new File("DB/prestamos/" + carnet + "/" + codigo + "-" + 1 + ".pres");
                try (FileInputStream fileInputStream = new FileInputStream(fil);
                ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);) {
            return (Prestamo) inputStream.readObject();
        } catch (IOException e) {
            throw new IOException();

        } catch (ClassNotFoundException e) {
            System.out.println("El objeto no tiene la forma de un usuario");
        }
        return null;
    }

}
