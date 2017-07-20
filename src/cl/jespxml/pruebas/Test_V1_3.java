package cl.jespxml.pruebas;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import cl.jespxml.JespXML;
import cl.jespxml.modelo.Atributo;
import cl.jespxml.modelo.Tag;
import org.xml.sax.SAXException;

/**
 *
 * @author Patricio Pérez Pinto
 */
public class Test_V1_3 {

    public static void main(String[] args) {
        final int LEER = 1;
        final int ESCRIBIR = 2;
        
        Scanner scan = new Scanner(System.in);
        
        System.out.println("1.-Leer");
        System.out.println("2.-Escribir");
        System.out.print("OP:");
        
        int opcion = scan.nextInt();

        try {
            JespXML xml = new JespXML("test_01.html");
            if (opcion == ESCRIBIR) {
                // Nueva forma de construir un XML en v1.3

                xml.escribirXML(new Tag("html")
                        .addTagHijo(new Tag("head")
                                .addTagHijo(new Tag("meta")
                                        .addAtributo(new Atributo("charset", "utf-8")))
                                .addTagHijo(new Tag("title").setContenido("Hola mundo JespXML")))
                        .addTagHijo(new Tag("body")
                                .addTagHijo(new Tag("h1")
                                        .setContenido("Hola Mundo desde JespXML!"))
                                .addTagHijo(new Tag("input")
                                        .addAtributo(new Atributo("type", "button"))
                                        .addAtributo(new Atributo("value", "Click aquí"))
                                        .addAtributo(new Atributo("onclick", "mostrarMensaje()")))
                                .addTagHijo(new Tag("a")
                                        .addAtributo(new Atributo("href", "http://www.google.cl"))
                                        .addAtributo(new Atributo("target", "_blank"))
                                        .setContenido("Click Aquí"))
                                .addTagHijo(new Tag("script")
                                        .setContenido("function mostrarMensaje(){"
                                                + "alert(\"Hola desde JS!\");"
                                                + "}")))
                );
            } else {
                try {
                    xml.leerXML().print();
                } catch (SAXException ex) {
                    Logger.getLogger(Test_V1_3.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
