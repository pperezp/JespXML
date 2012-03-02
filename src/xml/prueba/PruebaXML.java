/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.prueba;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import xml.analizador.XML;
import xml.excepciones.OpcionDeProcesamientoException;
import xml.modelo.ArchivoXML;
import xml.modelo.Atributo;
import xml.modelo.Tag;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import xml.modelo.*;


/**
 *
 * @author Patricio Pérez Pinto
 */
public class PruebaXML {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, OpcionDeProcesamientoException, TransformerConfigurationException, FileNotFoundException, TransformerException {
        
//        File f = new File("src/xml/archivoXML/prueba.xml");
//        ArchivoXML archivoXml;
        
        
//        /*------------------------------CODIGO PARA OPCION SIMPLE-----------------------------------------*/
//        archivoXml = XML.procesarArchivoXML(f, XML.OPCION_SIMPLE);
//        /*CODIGO PARA OBTENER TODOS LOS TAGS DEL ARCHIVO XML*/
//        System.out.println("----------------------------------------");
//        System.out.println("TODOS LOS TAGS");
//        System.out.println("----------------------------------------");
//        for (Tag tag : archivoXml.getTodosLostags()) {
//            System.out.println("----------------------------------------");
//            System.out.println("<"+tag.getNombre() + "> - " + tag.getValor());
//            if (tag.isAtributos()) {
//                System.out.print("Atributos del tag <"+tag.getNombre()+">");
//                for (Atributo atr : tag.getAtributos()) {
//                    System.out.println("\n\t" + atr.getNombre() + "='" + atr.getValor()+"'");
//                }
//            }
//        }
//        /*------------------------------CODIGO PARA OPCION SIMPLE-----------------------------------------*/
        
        
        
        
        
        
        
        /*------------------------------CODIGO PARA OPCION COMPLEJA---------------------------------------*/
//        archivoXml = XML.procesarArchivoXML(f, XML.OPCION_COMPLEJA);
//        Tag tagRaiz = archivoXml.getTagRaiz();
//        for(int i = 0; i<20; i++){
//            System.out.println();
//        }
//        imprimirInformacion(tagRaiz);
        /*------------------------------CODIGO PARA OPCION COMPLEJA---------------------------------------*/
        
        
        
        
        
        
        
        
        
        
        
//        /*------------------------------CODIGO PARA CREAR UN XML---------------------------------------*/
        File archivoXmlNuevo = new File("src/xml/archivoXML/archivoGeneradoAutomaticamente.xml");
        Tag raiz = new Tag("raiz","");
        raiz.addAtributo("tags","2");
        
        Tag arbol = new Tag("arbol","");
        arbol.addAtributo("hojas", "3");
        
        Tag hoja = new Tag("hoja", "esta es una hoja del arbol");
//        hoja.addComentario(new Comentario("Tag Hoja"));
        
        arbol.addTagHijo(hoja);
        arbol.addTagHijo(hoja);
        arbol.addTagHijo(hoja);
        
        raiz.addTagHijo(arbol);
        raiz.addTagHijo(arbol);
        Tag tagVacio = new Tag("tagCerrado", "", new Comentario("Este tag es Cerrado y esto es un comentario"));
        tagVacio.addAtributo(new Atributo("fontColor","Red"));
        tagVacio.addAtributo(new Atributo("font","Helvetica"));
        raiz.addTagHijo(tagVacio);
        Tag tagCDATA = new Tag("tagData", new CData("<html>HOJA</html>"));
        raiz.addTagHijo(tagCDATA);
        
        raiz.addComentario(new Comentario("Tag Raiz"));
        XML.crearArchivoXML(raiz, archivoXmlNuevo);
        imprimirInformacion(raiz);
//        /*------------------------------CODIGO PARA CREAR UN XML---------------------------------------*/
        
//        File f = new File("src/xml/archivoXML/archivoGeneradoAutomaticamente.xml");
//        ArchivoXML archivoXml = XML.procesarArchivoXML(f, XML.OPCION_COMPLEJA);
//        imprimirInformacion(archivoXml.getTagRaiz());
    }

    /**
     * Esta es la forma 
     * @param tagRaiz 
     */
    private static void imprimirInformacion(Tag tagRaiz) {
        System.out.println(tagRaiz);
        if(tagRaiz.isComentario())
            for(Comentario c: tagRaiz.getComentarios()){
                System.out.println("Comentario: <"+c+">");
            }
        for(Atributo atr:tagRaiz.getAtributos()){
            System.out.println(atr);
        }
        for(Tag t: tagRaiz.getTagsHijos()){
            imprimirInformacion(t);
        }
    }
}
