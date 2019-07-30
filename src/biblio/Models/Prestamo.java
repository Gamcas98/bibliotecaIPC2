/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblio.Models;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author Gamcas
 */
public class Prestamo implements Serializable {

    private String codigoLibro;
    private int carnetEstudiante;
    private LocalDate fechaPrestamo;
    private String estado;
    private LocalDate fechaDevolucion;
    private int cuotaNormal;
    private int cuotaMora;
   

    public Prestamo() {
    }

    public void setCuotaNormal(int cuotaNormal) {
        this.cuotaNormal = cuotaNormal;
    }

    public void setCuotaMora(int cuotaMora) {
        this.cuotaMora = cuotaMora;
    }

    public int getCuotaNormal() {
        return cuotaNormal;
    }

    public int getCuotaMora() {
        return cuotaMora;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public String getEstado() {
        return estado;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setCodigoLibro(String codigoLibro) {
        this.codigoLibro = codigoLibro;
    }

    public void setCarnetEstudiante(int carnetEstudiante) {
        this.carnetEstudiante = carnetEstudiante;
    }

    public void setFecha(LocalDate fecha) {
        this.fechaPrestamo = fecha;
    }

    public String getCodigoLibro() {
        return codigoLibro;
    }

    public int getCarnetEstudiante() {
        return carnetEstudiante;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

}
