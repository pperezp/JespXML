/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.analizador.dom.modelo;

/**
 *
 * @author Administrador
 */
public class CData {
    private String valor;

    public CData(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    
    @Override
    public String toString(){
        return this.getValor();
    }
}
