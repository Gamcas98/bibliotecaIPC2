/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblio;

import biblio.ui.*;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Gamcas
 */
public class Biblio {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        initComponents();

    }

    public static void initComponents() {

        File estudiante = new File("DB\\estudiantes");
        File prestamo = new File("DB\\prestamos");
        File libro = new File("DB\\libros");
        String[] listadoEst = estudiante.list();
        String[] listadoLibro = libro.list();

        if (!estudiante.exists()) {
            estudiante.mkdir();

        } else {

        }
        if (!prestamo.exists()) {
            prestamo.mkdir();
        }
        if (!libro.exists()) {
            libro.mkdir();
        }

        if ((listadoEst == null || listadoEst.length == 0)
                && (listadoLibro == null || listadoLibro.length == 0)) {
            JOptionPane.showMessageDialog(null, "OH!, al parecer la base de datos esta vacia.\n"
                    + "Por favor ingrese datos al sistema mediante un archivo");
            FormLecturaArchivo f = new FormLecturaArchivo();
            f.setVisible(true);
            System.out.println("No hay elementos dentro de la carpeta actual");

        } else {
            FormLecturaArchivo f = new FormLecturaArchivo();
            f.setVisible(true);
//            FormPrincipal f = new FormPrincipal();
//            f.setVisible(true);
        }

    }

}
