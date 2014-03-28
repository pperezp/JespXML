/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jespxml;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.jespxml.modelo.Atributo;
import org.jespxml.modelo.CData;
import org.jespxml.modelo.Comentario;
import org.jespxml.modelo.Encoding;
import org.jespxml.modelo.InstruccionDeProcesamiento;
import org.jespxml.modelo.Tag;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Clase principal para leer y escribir archivos XML
 *
 * @author Patricio Pérez Pinto
 */
public class JespXML extends File {

    private Tag raiz;
    private InputStream stream;
    private Encoding encoding;

    /**
     *
     * @param pathname la ruta del archivo xml que se desee procesar
     */
    public JespXML(String pathname) {
        super(pathname);
        stream = null;
        encoding = null;
    }

    /**
     *
     * @param uri Un objeto del tipo URI del archivo xml que se desee procesar
     * @deprecated Usar el constructor JespXML(URL url)
     */
    public JespXML(URI uri) {
        super(uri);
        stream = null;
        encoding = null;
    }

    public JespXML(URL url) throws IOException{
        this(url.openConnection().getInputStream());
    }
    
    /**
     *
     * @param stream un objeto InputStream del archivo xml que se desee procesar
     */
    public JespXML(java.io.InputStream stream) {
        super("");
        this.stream = stream;
        encoding = null;
    }

    /**
     *
     * @param pathname la ruta del archivo xml que se desee procesar
     * @param encoding el tipo de codificación encoding del archivo xml
     */
    public JespXML(String pathname, Encoding encoding) {
        super(pathname);
        stream = null;
        this.encoding = encoding;

    }

    /**
     *
     * @param uri Un objeto del tipo URI del archivo xml que se desee procesar
     * @param encoding el tipo de codificación encoding del archivo xml
     * @see Encoding
     */
    public JespXML(URI uri, Encoding encoding) {
        super(uri);
        stream = null;
        this.encoding = encoding;
    }

    /**
     *
     * @param stream un objeto InputStream del archivo xml que se desee procesar
     * @param encoding el tipo de codificación encoding del archivo xml
     */
    public JespXML(java.io.InputStream stream, Encoding encoding) {
        super("");
        this.stream = stream;
        this.encoding = encoding;
    }

    /**
     *
     * @param archivoXML El archivo xml a procesar
     */
    public JespXML(File archivoXML) {
        super(archivoXML.getPath());
        stream = null;
        encoding = null;
    }

    /**
     *
     * @param archivoXML el archivo xml a procesar
     * @param encoding el tipo de codificación encoding del archivo xml
     */
    public JespXML(File archivoXML, Encoding encoding) {
        super(archivoXML.getPath());
        stream = null;
        this.encoding = encoding;
    }

    public JespXML() {
        super("");
    }
    
    

    /**
     * Permite leer un archivo XML
     *
     * @see Tag
     * @return El tag raiz del documento xml. Este tag contiene a todos los
     * demás tags hijos
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public Tag leerXML() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
        DocumentBuilder generador = fabrica.newDocumentBuilder();
        Document doc;
        //si encoding es != null, quiere decir que hay que leer el archivo
        //con una codificación dada. Por ejemplo "UTF-8"
        if (this.encoding != null) {
            //si la lectura se va a realizar desde un stream
            if (this.stream != null) {
                InputSource is = new InputSource(stream);
                is.setEncoding(this.encoding.toString().replaceAll("_", "-"));
                doc = generador.parse(is);
            } else {//lectura desde archivo
                FileInputStream fis = new FileInputStream(this);
                InputSource is = new InputSource(fis);
                is.setEncoding(this.encoding.toString().replaceAll("_", "-"));
                doc = generador.parse(is);
            }
        } else {//sin un encoding especifico
            //si la lectura se va a realizar desde un stream sin encoding
            if (this.stream != null) {
                doc = generador.parse(stream);
            } else {//lectura desde archivo sin encoding
                doc = generador.parse(this);
            }
        }

        procesarNodo(raiz, doc);
        return raiz.getTagsHijos().get(0);
    }

//    public Tag leerXML() throws ParserConfigurationException, SAXException, IOException {
//        DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
//        DocumentBuilder generador = fabrica.newDocumentBuilder();
//        Document doc = generador.parse(stream);
//        
//        procesarNodo(raiz, doc);
//        return raiz.getTagsHijos().get(0);
//    }
    private void procesarNodo(Tag root, Node nodo) {
        short tipoNodo = nodo.getNodeType();
        switch (tipoNodo) {
            case Node.DOCUMENT_NODE: {
                NodeList hijos = nodo.getChildNodes();
                raiz = new Tag("Document");
                for (int i = 0; i < hijos.getLength(); i++) {
                    procesarNodo(raiz, hijos.item(i));
                }
                break;
            }
            case Node.ELEMENT_NODE: {
                Tag hijo = new Tag(nodo.getNodeName());

                analizarAtributos(nodo, hijo);
                root.addTagHijo(hijo);
                NodeList hijos = nodo.getChildNodes();

                if (hijos != null) {
                    for (int i = 0; i < hijos.getLength(); i++) {
                        procesarNodo(hijo, hijos.item(i));
                    }
                }
                break;
            }
            case Node.TEXT_NODE: {
                Text texto = (Text) nodo;
                if (!texto.getData().trim().equalsIgnoreCase("")) {
                    root.addContenido(texto.getData());
                }

                break;
            }
            case Node.PROCESSING_INSTRUCTION_NODE: {
                ProcessingInstruction pi = (ProcessingInstruction) nodo;
                root.addInstruccionDeProcesamiento(new InstruccionDeProcesamiento(pi.getTarget(), pi.getData()));
                break;
            }
            case Node.CDATA_SECTION_NODE: {
                CDATASection cData = (CDATASection) nodo;
                root.setValorCdata(new CData(cData.getTextContent()));
                break;
            }
            case Node.COMMENT_NODE: {
                Comment c = (Comment) nodo;
                root.addComentario(new Comentario(c.getTextContent()));
                break;
            }
            case Node.DOCUMENT_TYPE_NODE: {
            }
            case Node.DOCUMENT_FRAGMENT_NODE: {
            }
            case Node.NOTATION_NODE: {
            }
        }

    }

    /**
     *
     * @param tagRaiz Es el tag raiz del arhivo JespXML. el tag raiz, puede
     * tener hijos Tag
     * @throws ParserConfigurationException
     * @throws TransformerConfigurationException
     * @throws FileNotFoundException
     * @throws TransformerException
     */
    /**
     * Método para crear un archivo XML a partir de un Tag raiz
     *
     * @param tagRaiz el tag raiz que se creará como xml. Este tag puede
     * contener más tags hijos
     * @see Tag
     * @throws ParserConfigurationException
     * @throws TransformerConfigurationException
     * @throws FileNotFoundException
     * @throws TransformerException
     */
    public void escribirXML(Tag tagRaiz) throws ParserConfigurationException, TransformerConfigurationException, FileNotFoundException, TransformerException {
        DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
        DocumentBuilder generador = fabrica.newDocumentBuilder();
        Document doc = generador.newDocument();
        Element root = doc.createElement(tagRaiz.getNombre());
        doc.appendChild(root);
        crearArchivo(root, tagRaiz, doc);
        Transformer t = TransformerFactory.newInstance().newTransformer();

        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.setOutputProperty(OutputKeys.METHOD, "XML");

        if (encoding != null) {
            t.setOutputProperty(OutputKeys.ENCODING, encoding.toString().replace("_", "-"));
        }

        t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(this)));

    }

    private void analizarAtributos(Node nodo, Tag tag) {
        if (nodo.hasAttributes()) {
            for (int j = 0; j < nodo.getAttributes().getLength(); j++) {
                Node atributo = nodo.getAttributes().item(j);
                tag.addAtributo(atributo.getNodeName(), atributo.getNodeValue());
            }
        }
    }

    private void crearArchivo(Element root, Tag tagRaiz, Document doc) {
        try {
            //escribiendo Instrucciones de Procesamiento
            if (tagRaiz.isinstruccionesDeProcesamiento()) {
                for (InstruccionDeProcesamiento ip : tagRaiz.getInstruccionesDeProcesamiento()) {
                    ProcessingInstruction insPro = doc.createProcessingInstruction(ip.getTarget(), ip.getData());
                    root.appendChild(insPro);
                }
            }

            //escribiendo comentario
            if (tagRaiz.isComentario()) {
                for (Comentario c : tagRaiz.getComentarios()) {
                    Comment comentario = doc.createComment(c.getComentario());
                    root.appendChild(comentario);
                }
            }

            //escribiendo Valor del Tag
            if (tagRaiz.isContenidoCData()) {
                CDATASection cdata = doc.createCDATASection(tagRaiz.getValorCdata().toString());
                root.appendChild(cdata);
            } else if (tagRaiz.isContenidoNormal()) {
                Text valor = doc.createTextNode(tagRaiz.getContenido());
                root.appendChild(valor);
            }

            //escribiendo Atributos del Tag
            for (Atributo atr : tagRaiz.getAtributos()) {
                root.setAttribute(atr.getNombre(), atr.getValor());
            }

            //escribiendo hijos del Tag recursivamente
            for (Tag t : tagRaiz.getTagsHijos()) {
                Element hijo = doc.createElement(t.getNombre());
                root.appendChild(hijo);
                crearArchivo(hijo, t, doc);
            }
        } catch (DOMException dOMException) {
            System.out.println(dOMException.getMessage());
        }
    }

    public Encoding getEncoding() {
        return encoding;
    }

    public void setEncoding(Encoding encoding) {
        this.encoding = encoding;
    }

    /*para poder leer un xml estáticamente*/
    public static Tag leerXML(String pathname) throws ParserConfigurationException, SAXException, IOException {
        JespXML a = new JespXML(pathname);
        return a.leerXML();
    }

    public static Tag leerXML(URI uri) throws ParserConfigurationException, SAXException, IOException {
        JespXML a = new JespXML(uri);
        return a.leerXML();
    }

    public static Tag leerXML(InputStream stream) throws ParserConfigurationException, SAXException, IOException {
        JespXML a = new JespXML(stream);
        return a.leerXML();
    }

    public static Tag leerXML(File archivoXML) throws ParserConfigurationException, SAXException, IOException {
        JespXML a = new JespXML(archivoXML);
        return a.leerXML();
    }

    /*para crear archivos xml*/
    public static void escribirXML(Tag tagRaiz, String pathname) throws ParserConfigurationException, TransformerConfigurationException, FileNotFoundException, TransformerException {
        JespXML a = new JespXML(pathname);
        a.escribirXML(tagRaiz);
    }

    public static void escribirXML(Tag tagRaiz, URI uri) throws ParserConfigurationException, TransformerConfigurationException, FileNotFoundException, TransformerException {
        JespXML a = new JespXML(uri);
        a.escribirXML(tagRaiz);
    }

    public static void escribirXML(Tag tagRaiz, InputStream stream) throws ParserConfigurationException, TransformerConfigurationException, FileNotFoundException, TransformerException {
        JespXML a = new JespXML(stream);
        a.escribirXML(tagRaiz);
    }

    public static void escribirXML(Tag tagRaiz, File archivoXML) throws ParserConfigurationException, TransformerConfigurationException, FileNotFoundException, TransformerException {
        JespXML a = new JespXML(archivoXML);
        a.escribirXML(tagRaiz);
    }
    
    public static void escribirXML(Tag tagRaiz, String pathname, Encoding encoding) throws ParserConfigurationException, TransformerConfigurationException, FileNotFoundException, TransformerException {
        JespXML a = new JespXML(pathname);
        a.setEncoding(encoding);
        a.escribirXML(tagRaiz);
    }

    public static void escribirXML(Tag tagRaiz, URI uri, Encoding encoding) throws ParserConfigurationException, TransformerConfigurationException, FileNotFoundException, TransformerException {
        JespXML a = new JespXML(uri);
        a.setEncoding(encoding);
        a.escribirXML(tagRaiz);
    }

    public static void escribirXML(Tag tagRaiz, InputStream stream, Encoding encoding) throws ParserConfigurationException, TransformerConfigurationException, FileNotFoundException, TransformerException {
        JespXML a = new JespXML(stream);
        a.setEncoding(encoding);
        a.escribirXML(tagRaiz);
    }

    public static void escribirXML(Tag tagRaiz, File archivoXML, Encoding encoding) throws ParserConfigurationException, TransformerConfigurationException, FileNotFoundException, TransformerException {
        JespXML a = new JespXML(archivoXML);
        a.setEncoding(encoding);
        a.escribirXML(tagRaiz);
    }
}
