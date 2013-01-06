/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jespxml.excepciones;

/**
 *
 * @author Patricio Pérez Pinto
 */
public class AtributoNotFoundException extends Exception {

    /**
     * Creates a new instance of
     * <code>AtributoNotFoundException</code> without detail message.
     */
    public AtributoNotFoundException() {
    }

    /**
     * Constructs an instance of
     * <code>AtributoNotFoundException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public AtributoNotFoundException(String msg) {
        super(msg);
    }
}
