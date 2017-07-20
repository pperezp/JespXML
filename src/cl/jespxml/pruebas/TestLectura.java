package cl.jespxml.pruebas;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import cl.jespxml.JespXML;
import cl.jespxml.modelo.Tag;
import org.xml.sax.SAXException;

/**
 *
 * @author Patricio Pérez Pinto
 */
public class TestLectura {

    public static void main(String[] args) {
        try {
            JespXML xml = new JespXML("test_01.xml");

            Tag root = xml.leerXML();

            for (Tag t : root.getTagsHijos()) {
                System.out.println(t.getAtributos());
            }
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(TestLectura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(TestLectura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TestLectura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
