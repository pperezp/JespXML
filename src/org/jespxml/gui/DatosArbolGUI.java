/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jespxml.gui;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JTree;

/**
 * Interface que permite obtener los datos más facilmente
 * @author Patricio Pérez Pinto
 */
public interface DatosArbolGUI {
    /**
     *
     * @return el arbol que queremos construir
     */
    public JTree getArbol();
    /**
     *
     * @return una imagen que representará el icono de un tag
     */
    public ImageIcon getIconoTag();
    /**
     *
     * @return una imagen que representará el icono de un comentario
     */
    public ImageIcon getIconoComentario();
    /**
     *
     * @return una imagen que representará el icono de un atributo
     */
    public ImageIcon getIconoAtributo();
    /**
     *
     * @return una imagen que representará el icono del texto
     */
    public ImageIcon getIconoTexto();
    /**
     *
     * @return el color de fondo del JTree
     */
    public Color getColorDeFondo();
}
