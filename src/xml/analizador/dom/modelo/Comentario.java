/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.analizador.dom.modelo;

/**
 * Clase para construir objetos Comentario. Los comentarios son frases o palabras
 * a modo informativo para el programador que han de ser ignorados por el procesador.
 * @author Patricio Pérez Pinto
 */
public class Comentario {
    private String comentario;

    /**
     * Construir un comentario
     * @param comentario comentario
     */
    public Comentario(String comentario) {
        this.comentario = comentario;
    }

    /**
     *
     * @return el comentario
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * Actualiza el comentario
     * @param comentario nuevo comentario
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    
    @Override
    public String toString(){
        return this.comentario;
    }
}
