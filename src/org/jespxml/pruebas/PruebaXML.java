/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jespxml.pruebas;


import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.jespxml.JespXML;
import org.jespxml.modelo.Atributo;
import org.jespxml.modelo.Comentario;
import org.jespxml.modelo.InstruccionDeProcesamiento;
import org.jespxml.modelo.Tag;
import org.xml.sax.SAXException;
import org.jespxml.excepciones.*;


/**
 * Clase construida para hacer pruebas a JespXML
 * @author Patricio Pérez Pinto
 */
public class PruebaXML {

    /**
     * @param args the command line arguments
     * @throws ParserConfigurationException 
     * @throws SAXException 
     * @throws IOException 
     * @throws TransformerException
     * @throws TransformerConfigurationException
     * @throws FileNotFoundException  
     */
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, FileNotFoundException, TransformerException, TagHijoNotFoundException {
        
            JespXML j = new JespXML("ayuda.xml");
            Tag raiz = j.leerXML();
//        
        Tag t = raiz.getTagHijoByAtributo(new Atributo("id","while"));
        System.out.println(t.getTagHijoByName("definicion", Tag.Cantidad.PRIMERA_OCURRENCIA).get(0));
        //        Tag raiz = new Tag("raiz");
        //        raiz.addAtributo("wea", "muol");
        //        j.escribirXML(raiz);
        //        JespXML j = new JespXML("prueba.xml", Encoding.UTF_8);
        //        Tag raiz = j.leerXML();
        //        imprimirInformacion(raiz);
    }

    /**
     * Método para imprimir por pantalla todo un tag. Si este tag posee hijos, tambien serán impresos
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
