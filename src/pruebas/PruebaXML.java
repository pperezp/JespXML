/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas;


import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;
import xml.analizador.dom.JespXML;
import xml.analizador.dom.modelo.*;


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
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, FileNotFoundException, TransformerException {
        
            JespXML j = new JespXML("ayuda.xml");
            Tag raiz = j.leerXML();
//        
        Tag t = raiz.getTagHijoByAtributo(new Atributo("id","if else"));
        System.out.println(t);
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
