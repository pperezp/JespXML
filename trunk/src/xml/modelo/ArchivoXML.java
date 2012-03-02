/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.modelo;

import java.util.List;
import xml.analizador.XML;
import xml.excepciones.OpcionDeProcesamientoException;

/**
 *
 * @author Patricio Pérez Pinto
 */
public class ArchivoXML {

    private List<Tag> tags;
    private Tag raiz;
    private int opcionDeProcesamiento;

    public ArchivoXML(List<Tag> tags) {
        this.tags = tags;
        opcionDeProcesamiento = XML.OPCION_SIMPLE;
    }

    public ArchivoXML(Tag raiz) {
        this.raiz = raiz;
        opcionDeProcesamiento = XML.OPCION_COMPLEJA;
    }

//    public Tag getTag(int indice) throws OpcionDeProcesamientoException{
//        if(opcionDeProcesamiento != XML.OPCION_SIMPLE) throw new OpcionDeProcesamientoException("Para utilizar el método getTag(int indice), se debe indicar la opcion XML.OPCION_SIMPLE en el constructor de la clase ArchivoXML");
//        else return tags.get(indice);
//    }
//    
//    public int getNumeroDeTags() throws OpcionDeProcesamientoException{
//        if(opcionDeProcesamiento != XML.OPCION_SIMPLE) throw new OpcionDeProcesamientoException("Para utilizar el método getNumeroDeTags(), se debe indicar la opcion XML.OPCION_SIMPLE en el constructor de la clase ArchivoXML");
//        else return tags.size();
//    }
//    
//    public int getSizeOfTags() throws OpcionDeProcesamientoException{
//        if(opcionDeProcesamiento != XML.OPCION_SIMPLE) throw new OpcionDeProcesamientoException("Para utilizar el método getSizeOfTags(), se debe indicar la opcion XML.OPCION_SIMPLE en el constructor de la clase ArchivoXML");
//        else return tags.size();
//    }
//    
    /**
     * 
     * @return retornará una lista con todos los tags del archivo XML.
     * si usted procesa el archivo XML con la opcion XML.OPCION_COMPLEJA, se producira una "OpcionDeProcesamientoException"
     * ya que este método sólo se llama con la opcion XML.OPCION_SIMPLE
     * @throws OpcionDeProcesamientoException 
     */
    public List<Tag> getTodosLostags() throws OpcionDeProcesamientoException {
        if (opcionDeProcesamiento != XML.OPCION_SIMPLE) {
            throw new OpcionDeProcesamientoException("Para utilizar el método getTodosLostags(), se debe indicar la opcion XML.OPCION_SIMPLE en el constructor de la clase ArchivoXML");
        } else {
            return tags;
        }
    }
//    
//    public List<Tag> getTags(String nombreTag) throws OpcionDeProcesamientoException{
//        List<Tag> listaDeTags = new ArrayList<Tag>();
//        for (Tag tag : getTodosLostags()) {
//            if(tag.getNombre().equalsIgnoreCase(nombreTag)){
//                listaDeTags.add(tag);
//            }
//        }
//        return listaDeTags;
//    }
//    
//    public List<Tag> getTagsHijosDePadre(String nombreTagPadre) throws OpcionDeProcesamientoException{
//        List<Tag> listaDeTags = new ArrayList<Tag>();
//        for (Tag tag : getTodosLostags()) {
//            if(tag.getTagPadre().getNombre().equalsIgnoreCase(nombreTagPadre)){
//                listaDeTags.add(tag);
//            }
//        }
//        return listaDeTags;
//    }
//
//    public List<Tag> getTags() throws OpcionDeProcesamientoException {
//        if(opcionDeProcesamiento != XML.OPCION_SIMPLE) throw new OpcionDeProcesamientoException("Para utilizar el método getTags(), se debe indicar la opcion XML.OPCION_SIMPLE en el constructor de la clase ArchivoXML");
//        else return tags;
//    }

    /**
     * 
     * @return el Tag raiz del archivo XML. Este tag contiene a todos los demas
    //     * @throws OpcionDeProcesamientoException Si usted proceso el archivo XML con la XML.OPCION_SIMPLE, no va a poder obtener el tag raiz, y se lanzará una <strong>OpcionDeProcesamientoException</strong>
     */
    public Tag getTagRaiz() throws OpcionDeProcesamientoException {
        if (opcionDeProcesamiento != XML.OPCION_COMPLEJA) {
            throw new OpcionDeProcesamientoException("Para utilizar el método getTagRaiz(), se debe indicar la opcion XML.OPCION_COMPLEJA en el constructor de la clase ArchivoXML");
        } else {
            return raiz;
        }
    }
}
