package cl.jespxml.pruebas;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import cl.jespxml.JespXML;
import cl.jespxml.modelo.Atributo;
import cl.jespxml.modelo.Tag;

public class Test {

    /*
    Método main para escribir un archivo XML
     */
    public static void main(String[] args) {
        try {
//            //creo el objeto JespXML con el archivo que quiero crear
//            JespXML archivo = new JespXML("pruebaIndent.xml");
//            
//            //declaro el Tag raiz, que en esta caso se llama bd
//            Tag bd = new Tag("bd");
//            //le agrego un atributo a ese Tag (clientes="3")
//            bd.addAtributo(new Atributo("clientes", "1"));
//            
//            //creo el Tag cliente, que va a tener un nombre y un apellido
//            Tag cliente = new Tag("cliente");
//            Tag nombre, apellido;
//            
//            //construyo los Tags nombre y apellido y le agrego contenido
//            nombre = new Tag("nombre");
//            apellido = new Tag("apellido");
//            nombre.addContenido("Juan");
//            apellido.addContenido("Pérez");
//            
//            //agrego el Tag nombre y apellido al Tag cliente
//            cliente.addTagHijo(nombre);
//            cliente.addTagHijo(apellido);
//            
//            //finalmente agrego al Tag bd, el tag cliente
//            bd.addTagHijo(cliente);
//            //y escribo el archivo XML
//            archivo.escribirXML(bd);

            JespXML xml = new JespXML("test_01.xml");

            Tag root = new Tag("root");

            Tag conf = new Tag("config");
            conf.addAtributo(new Atributo("id", "2"));
            conf.addTagHijo(new Tag("color", "verde"));
            conf.addTagHijo(new Tag("ancho", "100"));
            conf.addTagHijo(new Tag("alto", "200"));

            root.addTagHijo(conf);

            conf = new Tag("config");
            conf.addAtributo(new Atributo("id", "1"));
            conf.addTagHijo(new Tag("color", "azul"));
            conf.addTagHijo(new Tag("ancho", "300"));
            conf.addTagHijo(new Tag("alto", "400"));

            root.addTagHijo(conf);

            xml.escribirXML(root);

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

    /*
    Método main para probar la serializacion de objetos con JespXML
     */
//    public static void main(String[] args) {
//        try {
//            //creo el objeto JespXML con el archivo que quiero crear
//            JespXML archivo = new JespXML("conf.xml");
//            
//            Conf conf = new Conf("BD", "Test");
//            
//            archivo.escribir(conf, conf.getClass());
//            
//            Conf c = (Conf)archivo.leer(Conf.class);
//            
//            System.out.println(c.nombre);
//            System.out.println(c.valor);
//            
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (JAXBException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
