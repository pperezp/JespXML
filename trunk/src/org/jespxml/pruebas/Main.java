/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jespxml.pruebas;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.jespxml.JespXML;
import org.jespxml.modelo.Atributo;
import org.jespxml.modelo.Tag;

/**
 *
 * @author Patricio Pérez Pinto
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            //creo el objeto JespXML con el archivo que quiero crear
            JespXML archivo = new JespXML("clientes.xml");
            
            //declaro el Tag raiz, que en esta caso se llama bd
            Tag bd = new Tag("bd");
            //le agrego un atributo a ese Tag (clientes="3")
            bd.addAtributo(new Atributo("clientes", "1"));
            
            //creo el Tag cliente, que va a tener un nombre y un apellido
            Tag cliente = new Tag("cliente");
            Tag nombre, apellido;
            
            //construyo los Tags nombre y apellido y le agrego contenido
            nombre = new Tag("nombre");
            apellido = new Tag("apellido");
            nombre.addContenido("Juan");
            apellido.addContenido("Pérez");
            
            //agrego el Tag nombre y apellido al Tag cliente
            cliente.addTagHijo(nombre);
            cliente.addTagHijo(apellido);
            
            //finalmente agrego al Tag bd, el tag cliente
            bd.addTagHijo(cliente);
            //y escribo el archivo XML
            archivo.escribirXML(bd);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
