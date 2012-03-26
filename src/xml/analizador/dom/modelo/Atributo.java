/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.analizador.dom.modelo;

/**
 *
 * @author Patricio Pérez Pinto
 */
public class Atributo {

    private String nombre;
    private String valor;

    public Atributo(String nombre, String valor) {
        this.nombre = nombre;
        this.valor = valor;
    }

    //este constructor es cuando el Tag tiene un valor CDATA
    public Atributo(String nombre) {
        this.nombre = nombre;
        this.valor = null;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return this.nombre + "='" + this.valor + "'";
    }
}
