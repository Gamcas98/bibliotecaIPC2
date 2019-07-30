/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblio.Files;

import biblio.Models.Estudiante;
import biblio.Models.Libro;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Gamcas
 */
public class Verificaciones {

    public static boolean verficarCodigoLibro(String codigo) throws NumberFormatException {
        String[] cod = codigo.split("-");

        if ((Integer.valueOf(cod[0]) >= 100 && Integer.valueOf(cod[0]) <= 1000)
                && (cod[1].length() == 3 && cod[1].matches("[A-Z]+"))) {
            return true;
        }

        return false;
    }

    public static boolean verificarExistenciaEstudiante(int carnet) {
        File file = new File("DB\\estudiantes");

        boolean b = false;

        for (String string : file.list()) {

            try {
                if (carnet == ObjectRead.readEstudiante(Integer.parseInt(string.substring(0, string.indexOf(".")))).getCarnet()) {
                    boolean aux = true;
                    b = aux;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
        // System.out.println(b);
        return b;
    }

    public static boolean verificarExistenciaLibro(String codigo) {
        File file = new File("DB\\libros");

        boolean b = false;

        for (String string : file.list()) {

            try {
                if (ObjectRead.readLibro(string.substring(0, string.indexOf("."))).getCodigo().equals(codigo)) {
                    boolean aux = true;
                    b = aux;
                }
            } catch (IOException ex) {
            }

        }
        //  System.out.println(b);
        return b;

    }

    public static boolean verficarCantidadLibro(String codigo) throws IOException {

        boolean b = false;

        if (ObjectRead.readLibro(codigo).getCantidadCopias() > 0) {

            b = true;
        }
        return b;
    }

    public static boolean prestamoLibro(String codigo, int carnet) {

        Estudiante est = new Estudiante();
        boolean b = false;

        if (verificarExistenciaLibro(codigo)) {
            try {

                est = ObjectRead.readEstudiante(carnet);

                if (verificarExistenciaEstudiante(carnet)) {

                    if (est.getLibrosPrestados() < 3) {

                        est.setLibrosPrestados(est.getLibrosPrestados() + 1);
                        ObjectWrite.saveEstudent(est);
                        boolean aux = true;
                        b = aux;
                        System.out.println(ObjectRead.readEstudiante(carnet).getLibrosPrestados());
                    }
                }

            } catch (IOException ex) {
            }

        }
        return b;
    }

    public static boolean prestamoLibroI(String codigo, int carnet) {

        Estudiante est = new Estudiante();
        Libro lib = new Libro();
        boolean b = false;

        if (verificarExistenciaLibro(codigo)) {
            try {

                est = ObjectRead.readEstudiante(carnet);
                lib = ObjectRead.readLibro(codigo);
                if (verificarExistenciaEstudiante(carnet) && verficarCantidadLibro(codigo)) {

                    if (est.getLibrosPrestados() < 3) {
                        lib.setCantidadCopias(lib.getCantidadCopias() - 1);
                        est.setLibrosPrestados(est.getLibrosPrestados() + 1);
                        ObjectWrite.saveEstudent(est);
                        ObjectWrite.saveLibro(lib);
                        boolean aux = true;
                        b = aux;
                    }

                }

            } catch (IOException ex) {
            }

        }

        return b;
    }

}
