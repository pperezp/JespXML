/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.analizador.dom.modelo;

/**
 *
 * @author Administrador
 */
public class InstruccionDeProcesamiento {
    private String target;
    private String data;

    public InstruccionDeProcesamiento(String target, String data) {
        this.target = target;
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
    
    
}
