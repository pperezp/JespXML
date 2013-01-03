/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jespxml.modelo;

/**
 * Clase para construir un objeto CData (Character Data). Es una construcción en XML para 
 * especificar datos utilizando cualquier carácter sin que se interprete 
 * como marcado XML. No confundir con 2(#PCDATA) que es para los elementos.
 * Permite que caracteres especiales no rompan la estructura. 
 * @author Patricio Pérez Pinto
 */
public class CData {
    private String valor;

    /**
     * Construye un objeto CData con un valor
     * @param valor
     */
    public CData(String valor) {
        this.valor = valor;
    }

    /**
     *
     * @return El valor del contenido CData
     */
    public String getValor() {
        return valor;
    }

    /**
     * Actualiza el valor de CData  
     * @param valor El nuevo valor
     */
    public void setValor(String valor) {
        this.valor = valor;
    }
    
    @Override
    public String toString(){
        return this.getValor();
    }
}
