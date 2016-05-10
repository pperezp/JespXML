/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jespxml.modelo;

import java.util.Objects;

/**
 * Clase para construir un Atributo. Un atributo en XML es una propiedad en un Tag determinado.
 * Ejemplo: <tag atributo="valor"></tag>
 * @author Patricio Pérez Pinto
 */
public class Atributo {

    private String nombre;//nombre
    private String valor;//valor

    /**
     * Constructor de Atributo
     * @param nombre nombre del atributo
     * @param valor valor del atributo
     */
    public Atributo(String nombre, String valor) {
        this.nombre = nombre;
        this.valor = valor;
    }

    /**
     * Constructor de Atributo. Sólo se construye con un nombre. Ej: atributo=""
     * @param nombre nombre del atributo
     */
    public Atributo(String nombre) {
        this.nombre = nombre;
        this.valor = null;
    }

    /**
     *
     * @return El nombre del atributo
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * cambia el actual nombre del Atributo
     * @param nombre El nuevo nombre del Atributo
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return El valor del Atributo
     */
    public String getValor() {
        return valor;
    }

    /**
     * Reemplaza el valor actual del Atributo
     * @param valor El nuevo valor del Atributo
     */
    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return this.nombre + "='" + this.valor + "'";
    }
    
    @Override
    public boolean equals(Object o){
        if(o instanceof Atributo){
            Atributo a = (Atributo)o;
            if(this.getNombre().equalsIgnoreCase(a.getNombre()) && this.getValor().equalsIgnoreCase(a.getValor())){
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.nombre);
        hash = 23 * hash + Objects.hashCode(this.valor);
        return hash;
    }
}
