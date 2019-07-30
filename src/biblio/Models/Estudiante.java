/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblio.Models;

import biblio.Files.ObjectWrite;
import java.io.Serializable;

/**
 *
 * @author Gamcas
 */
public class Estudiante implements Serializable {

    private int carnet;
    private int carrera;
    private String nombre;
    private int fechaNacimiendo;
    private int librosPrestados;
    
    public Estudiante() {

    }

    public Estudiante(int carnet, String nombre, int carrera) {
        this.carnet = carnet;
        this.carrera = carrera;
        this.nombre = nombre;
        ObjectWrite.saveEstudent(this);
    }

    public Estudiante(int carnet, int carrera, String nombre, int fechaNacimiendo) {
        this.carnet = carnet;
        this.carrera = carrera;
        this.nombre = nombre;
        this.fechaNacimiendo = fechaNacimiendo;
    }

    public int getLibrosPrestados() {
        return librosPrestados;
    }

    public void setLibrosPrestados(int librosPrestados) {
        this.librosPrestados = librosPrestados;
    }
    
    

    public void setCarnet(int carnet) {
        this.carnet = carnet;
    }

    public void setCarrera(int carrera) {
        this.carrera = carrera;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFechaNacimiendo(int fechaNacimiendo) {
        this.fechaNacimiendo = fechaNacimiendo;
    }

    public int getCarnet() {
        return carnet;
    }

    public int getCarrera() {
        return carrera;
    }

    public String getNombre() {
        return nombre;
    }

    public int getFechaNacimiendo() {
        return fechaNacimiendo;
    }

}
