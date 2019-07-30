/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblio.Files;

import biblio.Models.*;
import biblio.ui.FormLecturaArchivo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 *
 * @author Gamcas
 */
public class TextRead extends Thread {

    private String absolutePath;
    private String[] commands;
    private ArrayList<Estudiante> estudiantes = new ArrayList<>();
    private Estudiante estudiante;
    private ArrayList<Libro> libros = new ArrayList<>();
    private Libro libro;
    private ArrayList<Prestamo> prestamos = new ArrayList<>();
    private Prestamo prestamo;
    private String tipo = null;

    @Override
    //hilo para correr la lectura del archivo de texto
    public void run() {
        readText(absolutePath);
    }

    public TextRead(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    private static void esperar(int segundos) {
        try {
            Thread.sleep(segundos * 500);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public void readText(String path) {

        try {
            FileReader lector = null;
            BufferedReader buffer = null;

            lector = new FileReader(new File(path));

            buffer = new BufferedReader(lector);
            String data = buffer.readLine();
            //recorremos linea por linea el archivo de entrada
            while (data != null) {

                try {
                    //llamamos la funcion para ver que tipo de estructura contiene la linea
                    identificarComando(data);
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                    FormLecturaArchivo.areaTexto.append("No se reconoce el comando Null\n\n");
                } catch (IOException ex) {
                    FormLecturaArchivo.areaTexto.append("No se encontro el objeto IOe\n\n");
                }
                data = buffer.readLine();//leemos la siguiente linea
                esperar(0);

            }
            /*llamamos los metodos para persistir en archivos binarios las estructuras*/
            crearEstudiantes();
            crearLibros();
            crearPrestamos();
            /*Si la lectura se realizo con exito le informamos al usuario*/
            FormLecturaArchivo.areaTexto.append("Lectura finalizada " + " \n" + "\n");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    /*metodo para identificar las estructuras que contiene el archivo de texto*/
    public void identificarComando(String linea) throws IOException, NullPointerException {
        linea = linea.replace("\t", "");//nos remplaza los salto de linea por vacio
        commands = linea.split(":");//guarda un vector de 2 posiciones para las estructuras

        /*validamos palabras trae la linea leida
        *si contiene estudiante libro o prestamo
        *creamos el objeto de la palabra y le asignamos valir a tipo
         */
        if (linea.contains("ESTUDIANTE") && tipo == null) {
            tipo = "estudiante";
            estudiante = new Estudiante();
        } else if (linea.contains("LIBRO") && tipo == null) {
            tipo = "libro";
            libro = new Libro();
        } else if (linea.contains("PRESTAMO") && tipo == null) {
            tipo = "prestamo";
            prestamo = new Prestamo();
            /*si tipo ya tiene un valor y la linea que se esta leyendo trae prestamo libro o estudiante
            *no entra a un error ya que no cumpliria con la estructura
             */
        } else if (tipo != null && (linea.contains("PRESTAMO")
                || linea.equals("LIBRO") || linea.contains("ESTUDIANTE"))) {
            
            FormLecturaArchivo.areaTexto.append("Estructura incorrecta\n\n");
            tipo = null;
            estudiante = null;
            libro = null;
            prestamo = null;
        } else {
            /*si ya esta creado un estudiante 
            *se verifica que contenga una estructura correcta
            *y si todo esta correcto agregamos el estudiante al arrayList
             */
            if (estudiante != null) {
                if (commands[0].equals("CARNET")) {
                    try {
                        estudiante.setCarnet(Integer.valueOf(commands[1]));
                    } catch (NumberFormatException ex) {

                    }
                } else if (commands[0].equals("NOMBRE") && estudiante.getCarnet() > 0) {

                    estudiante.setNombre(commands[1]);

                } else if (commands[0].equals("CARRERA") && estudiante.getNombre() != null) {
                    try {
                        estudiante.setCarrera(Integer.valueOf(commands[1]));
                    } catch (NumberFormatException ex) {
                    }
                    estudiantes.add(estudiante);
                    estudiante = null;
                    tipo = null;
                } else {
                    FormLecturaArchivo.areaTexto.append("Error en la estructura de un estudiante\n\n");
                    estudiante = null;
                    tipo = null;
                }
                /*si esta creado un objeto libro 
                *verificamos la estructura del libro sea correcta
                *si es correcta lo agregamos a un array de libros para despues crearlos en archivos
                 */
            } else if (libro != null) {
                if (commands[0].equals("TITULO")) {

                    libro.setTitulo(commands[1]);

                } else if (commands[0].equals("AUTOR") && libro.getTitulo() != null) {

                    libro.setAutor(commands[1]);

                } else if (commands[0].equals("CODIGO") && libro.getAutor() != null) {
                    try {
                        if (Verificaciones.verficarCodigoLibro((commands[1]))) {
                            libro.setCodigo((commands[1]));
                        }
                    } catch (NumberFormatException ex) {
                    }
                } else if (commands[0].equals("CANTIDAD") && libro.getCodigo() != null) {
                    try {
                        libro.setCantidadCopias(Integer.valueOf(commands[1].replace(" ", "")));
                    } catch (NumberFormatException ex) {

                    }
                    libros.add(libro);
                    libro = null;
                    tipo = null;
                } else {
                    FormLecturaArchivo.areaTexto.append("Error en la estructura de un libro\n\n");
                    libro = null;
                    tipo = null;
                }
                /*si tenemos creado un objeto prestamo
                *verificamos que su estructura sea correcta 
                *y si todo es correcto lo agregamos a un array de prestamos para luego crear sus archivos binarios
                 */
            } else if (prestamo != null) {
                if (commands[0].equals("CODIGOLIBRO")) {

                    prestamo.setCodigoLibro(commands[1]);

                } else if (commands[0].equals("CARNET") && prestamo.getCodigoLibro() != null) {

                    prestamo.setCarnetEstudiante(Integer.valueOf(commands[1]));

                } else if (commands[0].equals("FECHA") && prestamo.getCarnetEstudiante() > 0) {

                    try {
                        prestamo.setFechaPrestamo((LocalDate.parse(commands[1],
                                DateTimeFormatter.ofPattern("yyyy-MM-dd"))));

                    } catch (DateTimeParseException ex) {

                    }
                    prestamos.add(prestamo);
                    prestamo = null;
                    tipo = null;

                } else {
                    FormLecturaArchivo.areaTexto.append("Error en la estructura de un libro\n\n");
                    prestamo = null;
                    tipo = null;
                }

            }//fin prestamo
        }//fin if verificar que tipo de estrucutra se va a crear

    }//metodo identificar

    /*metodo que recorre el array de libros que tenian una estructura correcta
    *verifica que no haya errores dentro de cada linea de la estructuar
    *y nos persiste el libro en archivo binario y muestra en pantalla 
     */
    public void crearLibros() {
        for (int i = 0; i < libros.size(); i++) {
            File file = new File("DB/libros/" + libros.get(i).getCodigo() + ".lb");

            if (file.exists()) {
                FormLecturaArchivo.areaTexto.append("El libro: " + libros.get(i).getTitulo().replace("\"", "").trim()
                        + ", ya existe" + " \n" + "\n");

            } else {
                if (libros.get(i).getCodigo() != null && libros.get(i).getCantidadCopias() >= 0) {
                    ObjectWrite.saveLibro(libros.get(i));
                    FormLecturaArchivo.areaTexto.append("Se creo el libro: "
                            + libros.get(i).getTitulo().replace("\"", "").trim()
                            + ", cantidad de copias disponibles: "
                            + libros.get(i).getCantidadCopias()
                            + " \n" + "\n");

                } else if (libros.get(i).getTitulo() == null) {
                    FormLecturaArchivo.areaTexto.append("El libro: 'sin nombre'"
                            + ", no cumple con la estructura" + " \n" + "\n");
                } else if (libros.get(i).getTitulo() != null) {
                    FormLecturaArchivo.areaTexto.append("El libro: "
                            + libros.get(i).getTitulo().replace("\"", "").trim()
                            + ", no cumple con la estructura"
                            + " \n" + "\n");
                }
            }
        }

    }

    /*metodo que recorre el array de estudiantes que tenian una estructura correcta
    *verifica que no haya errores dentro de cada linea de la estructuar
    *y nos persiste el estudiante en archivo binario y muestra en pantalla 
     */
    public void crearEstudiantes() {

        for (int i = 0; i < estudiantes.size(); i++) {
          
            File file = new File("DB/estudiantes/" + estudiantes.get(i).getCarnet() + ".est");
            
            if (!file.exists()) {
                
                if (estudiantes.get(i).getCarrera() > 0 && estudiantes.get(i).getCarrera() < 6) {
                    
                    ObjectWrite.saveEstudent(estudiantes.get(i));
                    
                    FormLecturaArchivo.areaTexto.append("Se creo el estudiante: "
                            + estudiantes.get(i).getNombre().replace("\"", "").trim() + " \n" + "\n");
                    
                } else if (estudiantes.get(i).getNombre() != null) {
                    FormLecturaArchivo.areaTexto.append("El estudiante: " + estudiantes.get(i).getNombre().replace("\"", "").trim()
                            + ", no cumple con la estructura" + " \n" + "\n");
                } else if (estudiantes.get(i).getNombre() == null) {
                    FormLecturaArchivo.areaTexto.append("El estudiante: 'sin nombre', no cumple con la estructura"
                            + " \n" + "\n");
                }
            } else {
                FormLecturaArchivo.areaTexto.append("El estudiante: " + estudiantes.get(i).getNombre().replace("\"", "").trim()
                        + ", ya existe" + " \n" + "\n");
            }

        }

    }

    /*Metodo culero*/
 /*
    *Metodo se tratara de explicar linea por linea de codigo
     */
    private void crearPrestamos() {

        //recorremos todos los prestamos que podrian cumplir para con la estructura
        for (int i = 0; i < prestamos.size(); i++) {

            /*Estos objetos nos sirven para recorrer las carpeta y archivos de los prestamos*/
            File file = new File("DB/prestamos/" + prestamos.get(i).getCarnetEstudiante());
            File fil = new File("DB/prestamos/" + prestamos.get(i).getCarnetEstudiante()
                    + "/" + prestamos.get(i).getCodigoLibro() + "-" + 1 + ".pres");

            /*si la carpeta no existe y si el archivo no existe
            *y si el objeto prestamo recibio la fecha correctamente
            *verificamos que se pueda prestar el libro con el metodo prestamo libro
            *si se puede prestar el libro persistimos el objeto prestamo en un archivo binario
             */
            if (!file.exists()) {
                if (!fil.exists()) {
                    if (prestamos.get(i).getFechaPrestamo() != null) {

                        if (Verificaciones.prestamoLibro(prestamos.get(i).getCodigoLibro(),
                                prestamos.get(i).getCarnetEstudiante())) {

                            ObjectWrite.savePrestamo(prestamo, prestamos.get(i).getCarnetEstudiante(),
                                    prestamos.get(i).getCodigoLibro(), 1);
                            /*fin de la explicacion*/
                            try {
                                //si el objeto fue guardado le informamos al usuario
                                FormLecturaArchivo.areaTexto.append("El estudiante: "
                                        + ObjectRead.readEstudiante(prestamos.get(i).getCarnetEstudiante())
                                                .getNombre() + " tiene en su poder el libro: "
                                        + ObjectRead.readLibro(prestamos.get(i).getCodigoLibro())
                                                .getTitulo()
                                        + "\n\n");
                            } catch (IOException ex) {
                                FormLecturaArchivo.areaTexto.append("Ocurrio un error en un prestamo\n\n");
                            }

                        } else {
                            FormLecturaArchivo.areaTexto.append("Ocurrio un error en un prestamo\n\n ");
                        }
                    }
                }

                /*Aqui verificamos si x estudiante ya posea un prestamos
                *si es asi quiere decir que ya existe una carpeta con su nombre
                *y luego se verifica que no exista un prestamo con el nombre del 
                *libro que se esta prestando
                 */
            } else {
                if (!fil.exists()) {
                    if (prestamos.get(i).getFechaPrestamo() != null) {

                        if (Verificaciones.prestamoLibro(prestamos.get(i).getCodigoLibro(),
                                prestamos.get(i).getCarnetEstudiante())) {

                            ObjectWrite.savePrestamo(prestamo, prestamos.get(i).getCarnetEstudiante(),
                                    prestamos.get(i).getCodigoLibro(), 1);
                            /*si el libro se podia prestar podemos persistir el prestamo
                            *y le informamos al usuario
                             */
                            try {
                                FormLecturaArchivo.areaTexto.append("El estudiante : "
                                        + ObjectRead.readEstudiante(prestamos.get(i).getCarnetEstudiante())
                                                .getNombre() + " tiene en su poder el libro: "
                                        + ObjectRead.readLibro(prestamos.get(i).getCodigoLibro())
                                                .getTitulo()
                                        + "\n\n");
                            } catch (IOException ex) {
                                FormLecturaArchivo.areaTexto.append("Ocurrio un error en un prestamo\n\n");
                            }

                        } else {
                            FormLecturaArchivo.areaTexto.append("Ocurrio un error en un prestamo\n\n");

                        }
                    } else {
                        FormLecturaArchivo.areaTexto.append("Ocurrio un error en un prestamo\n\n");

                    }

                    /*SI la carpeta con el carnet del estudiante ya existe 
                    *y tambien ya existe un archivo con el codigo de libro que se prestara
                    *se persistira un archivo sumandole 1 al identificador del archivo prestamo
                     */
                } else {
                    if (prestamos.get(i).getFechaPrestamo() != null) {

                        if (Verificaciones.prestamoLibro(prestamos.get(i).getCodigoLibro(),
                                prestamos.get(i).getCarnetEstudiante())) {

                            //Si el libro se puede prestar hacemos lo siguiebte
                           
                            /*
                            *PD: el prestamo se guarda dentro de una carpeta que contiene el carnet
                            *PD2:el archivo que se guarda dentro de la carpeta tiene el formato
                            *"101-BBB-1"
                            *el ultimo numero lo conoceremos como identificador
                             */
 
                            
                            /*Creamos un vector de String de 3 posiciones con el metodo split
                            *al codigo del libro que se prestara
                            *creamos un string que almacenara la pos 0 y 1 separada por un guion
                            *creamos un arraylist de string para almacenar los codigos de los libros
                            *sin el numero que los identifica
                            *luego recorremos la carpeta del estudiante para ver cuantos 
                            *libros de x tipo tiene actualmente
                            *al finalizar el ciclo agregamos al array los libros que ya tenia
                            *obtenemo el ultimo elemento del array y obtenemos su identificador
                            *y persistimos el prestamo sumandole 1 al identificador 
                            *para llevar un control del total de libros que presto el estudiante
                             */
                            String codigo[] = prestamos.get(i).getCodigoLibro().split("-");
                            String unir = codigo[0] + "-" + codigo[1];
                            ArrayList<String> codigos = new ArrayList<>();

                            for (String string : file.list()) {

                                String cadena = string.substring(0, string.indexOf("."));
                                String cad[] = cadena.split("-");
                                String cod = cad[0] + "-" + cad[1];

                                if (unir.equals(cod)) {
                                    codigos.add(cadena);
                                }
                            }
                            String numero[] = codigos.get(codigos.size() - 1).split("-");

                            ObjectWrite.savePrestamo(prestamo, prestamos.get(i).getCarnetEstudiante(),
                                    prestamos.get(i).getCodigoLibro(), Integer.valueOf(numero[2]) + 1);

                            /*Si todo se cumplio le informamos al usuario*/
                            try {
                                FormLecturaArchivo.areaTexto.append("El estudiante : "
                                        + ObjectRead.readEstudiante(prestamos.get(i).getCarnetEstudiante())
                                                .getNombre() + " tiene en su poder el libro: "
                                        + ObjectRead.readLibro(prestamos.get(i).getCodigoLibro())
                                                .getTitulo()
                                        + "\n\n");
                            } catch (IOException ex) {
                                FormLecturaArchivo.areaTexto.append("Ocurrio un error en un prestamo\n\n");
                            }

                        } else {
                            FormLecturaArchivo.areaTexto.append("Ocurrio un error en un prestamo\n\n");

                        }

                    } else {
                        FormLecturaArchivo.areaTexto.append("Ocurrio un error en un prestamo\n\n");
                    }
                }
            }
        }//ciclo del array de prestamos

    }//metodo crear prestamo
}
