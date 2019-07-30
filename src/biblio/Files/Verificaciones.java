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

    //Metodo que valida que el codigo del libro cumpla con la estructura correcta
    public static boolean verficarCodigoLibro(String codigo) throws NumberFormatException {
        
        String[] cod = codigo.split("-");

        if ((Integer.valueOf(cod[0]) >= 100 && Integer.valueOf(cod[0]) <= 1000)
                && (cod[1].length() == 3 && cod[1].matches("[A-Z]+"))) {
            return true;
        }

        return false;
    }

    /*Recorre la carpeta donde persisten los estudiantes
    *si encuentra un carnet igual al que se le envia
    *devuelve true indicando que el estudiante existe
    */
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
       
        return b;
    }

    /*Recorre la carpeta de libros 
    *si encuentra un codigo igual al que se le envia 
    *devuelve true indicando asi la existencia de dicho libro
    */
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
       
        return b;

    }

    /*Verifca que un libro tenga copias disponibles para realizar un prestamo
    *esto nos sirve en la interfaz grafica
    */
    public static boolean verficarCantidadLibro(String codigo) throws IOException {

        boolean b = false;

        if (ObjectRead.readLibro(codigo).getCantidadCopias() > 0) {

            b = true;
        }
        return b;
    }

    
    /*Metodo que verifica si existe el libro a prestar
    *si existe verfica que exista el estudiante
    *si existe verifica que el estudiante no tenga mas de 3 libros
    *si todo se cumple devuelve true y modifica el atributo de librosprestados del estudiante
    */
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
                    }
                }

            } catch (IOException ex) {
            }

        }
        return b;
    }

    
    /*Verifica que exista el libro
    *si el libro existe y el estudiante tambien existe
    *y el libro tiene copias disponibles
    *retorna true y modifica los atributos del libro y del estudiante
    *le suma uno a la cantidad de libros que posee el estudiante y le resta a la cant de libros disponibles
    */
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
