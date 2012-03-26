/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.prueba;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;
import xml.analizador.dom.DOM;
import xml.analizador.dom.modelo.*;


/**
 *
 * @author Patricio Pérez Pinto
 */
public class PruebaXML {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, FileNotFoundException, TransformerException {
        
//        File f = new File("src/xml/archivoXML/prueba.xml");
        
        /*------------------------------CODIGO PARA LEER UN DOCUMENTOS XML CON DOM---------------------------------------*/
//        Tag tagRaiz = DOM.procesarArchivoXMLDom(f);
//        for(int i = 0; i<20; i++){
//            System.out.println();
//        }
//        imprimirInformacion(tagRaiz);
        /*------------------------------CODIGO PARA LEER UN DOCUMENTOS XML CON DOM---------------------------------------*/
        
//        /*------------------------------CODIGO PARA CREAR UN DOCUMENTOS XML CON DOM---------------------------------------*/
//        File archivoXmlNuevo = new File("src/xml/archivoXML/archivoGeneradoAutomaticamente.xml");
//        Tag raiz = new Tag("raiz","");
//        raiz.addAtributo("tags","2");
//        
//        Tag hijos = new Tag("arbol","");
//        hijos.addAtributo("hojas", "3");
//        
//        Tag hoja = new Tag("hoja", "esta es una hoja del arbol");
////        hoja.addComentario(new Comentario("Tag Hoja"));
//        
//        hijos.addTagHijo(hoja);
//        hijos.addTagHijo(hoja);
//        hijos.addTagHijo(hoja);
//        
//        raiz.addTagHijo(hijos);
//        raiz.addTagHijo(hijos);
//        Tag tagVacio = new Tag("tagVacio");
//        tagVacio.addAtributo(new Atributo("fontColor","Red"));
//        tagVacio.addAtributo(new Atributo("font","Helvetica"));
//        raiz.addTagHijo(tagVacio);
//        Tag tagCDATA = new Tag("tagData", new CData("<html>HOJA</html>"));
//        raiz.addTagHijo(tagCDATA);
//        
//        raiz.addComentario(new Comentario("Tag Raiz"));
//        DOM.crearArchivoXML(raiz, archivoXmlNuevo);
//        imprimirInformacion(raiz);
//        /*------------------------------CODIGO PARA CREAR UN DOCUMENTOS XML CON DOM---------------------------------------*/
        
//        File f = new File("src/xml/archivoXML/archivoGeneradoAutomaticamente.xml");
//        ArchivoXML archivoXml = DOM.procesarArchivoXMLDom(f, DOM.OPCION_COMPLEJA);
//        imprimirInformacion(archivoXml.getTagRaiz());
    }

    /**
     * Esta es la forma 
     * @param tagRaiz 
     */
    private static void imprimirInformacion(Tag tagRaiz) {
        System.out.println(tagRaiz+" ");
        for(InstruccionDeProcesamiento ip: tagRaiz.getInstruccionesDeProcesamiento()){
            System.out.println("<?"+ip.getTarget()+" "+ip.getData()+"?>");
        }
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
