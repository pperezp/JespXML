# <font color='red'>Descarga ya la versión 1.2! (10 de junio de 2014)</font> #
  * Agregando el constructor (URL url) en vez de URI para leer archivos XML desde una red
  * Ahora el contenido esta con trim()
  * Versión desde jdk 6 en adelante
  * Ahora se puede convertir objetos enteros a XML y leerlos con la ayuda de escribir y leer
  * El indentado es de 4 espacios al momento de crear un XML

Descargar --> https://drive.google.com/file/d/0B4D_KInO5V4jeEpiYWNFblZNeFU/edit?usp=sharing
# ¿Qué es JespXML? #
JespXML, es un conjunto de clases hecho en java para manipular fácilmente archivos XML. Esta en un proyecto netbeans. Es sin interfaz gráfica, solo son las clases necesarias para llevar a cabo el objetivo con archivos XML, tales como abrir o generar XML.

# ¿En qué versión de JDK está desarrollado? #
JespXML está desarrollado en JDK v6

# Ejemplo de LECTURA de un archivo XML (prueba.xml) #
Vamos a poner el siguiente archivo xml, llamado "prueba.xml" en la raiz de nuestro proyecto para poder leerlo:

```
<?xml version="1.0" encoding="UTF-8"?>
<biblioteca cantidadDeLibros='2'>
    <!-- Comentario-->
    <libro paginas='100'>
        <titulo>Libro Accion 1</titulo>
        <autor>Autor ACTION</autor>
    </libro>
</biblioteca>
```
Si queremos leer este xml, sería de la siguiente manera:

```
package org.jespxml.pruebas;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.jespxml.JespXML;
import org.jespxml.excepciones.AtributoNotFoundException;
import org.jespxml.excepciones.TagHijoNotFoundException;
import org.jespxml.modelo.Tag;
import org.xml.sax.SAXException;

public class Main {

    public static void main(String[] args) {
        try {
            //Cargo el archivo
            JespXML archivo = new JespXML("prueba.xml");
            //leo el archivo y me retorna el tag raiz, que en este caso
            // es biblioteca
            Tag biblioteca = archivo.leerXML();
            //Obtengo los tags que necesito, por el nombre
            Tag libro = biblioteca.getTagHijoByName("libro");
            Tag titulo = libro.getTagHijoByName("titulo");
            Tag autor = libro.getTagHijoByName("autor");
            
            //puedo obtener los valores de los atributos de un tag específico
            String paginas = libro.getValorDeAtributo("paginas");
            
            //imprimo la información requerida
            System.out.println("Páginas: "+paginas);
            System.out.println("Título: "+titulo.getContenido());
            System.out.println("Autor: "+autor.getContenido());
        } catch (AtributoNotFoundException ex) {
            //exception lanzada cuando no se encuentra el atributo
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TagHijoNotFoundException ex) {
            //exception lanzada cuando no se encuentra el tag hijo
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
```

Como resultado, nos arroja esta información por consola:

```

run:
Páginas: 100
Título: Libro Accion 1
Autor: Autor ACTION
BUILD SUCCESSFUL (total time: 1 second)
```
# Ejemplo de ESCRITURA de un archivo xml #
Con el siguiente código se creará un xml sencillo
```
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

public class Main {

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
```

El archivo xml generado será de la siguiente forma:
```
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<bd clientes="1">
    <cliente>
        <nombre>Juan</nombre>
        <apellido>Pérez</apellido>
    </cliente>
</bd>
```