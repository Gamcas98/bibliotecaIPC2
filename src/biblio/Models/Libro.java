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
public class Libro implements Serializable {
    
    private String codigo;
    private String titulo;
    private String autor;
    private int cantidadCopias;
    private LocalDate fecha;
    private String Editorial;
    
    
    public Libro(){
    }

    public Libro(String codigo, String titulo, String autor, int cantidadCopias, LocalDate fecha, String Editorial) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.cantidadCopias = cantidadCopias;
        this.fecha = fecha;
        this.Editorial = Editorial;
    }


    
    public String getCodigo() {
        return codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getCantidadCopias() {
        return cantidadCopias;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getEditorial() {
        return Editorial;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setCantidadCopias(int cantidadCopias) {
        this.cantidadCopias = cantidadCopias;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setEditorial(String Editorial) {
        this.Editorial = Editorial;
    }

}
