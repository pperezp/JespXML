/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.analizador.dom;

import java.io.*;
import java.net.URI;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import xml.analizador.dom.modelo.*;

/**
 *
 * @author Patricio Pérez Pinto
 */
public class JespXML extends File {

    private Tag raiz;
    private InputStream stream;
    
    public JespXML(String pathname) {
        super(pathname);
        stream = null;
    }

    public JespXML(URI uri) {
        super(uri);
        stream = null;
    }


    public JespXML(java.io.InputStream stream){
        super("");
        this.stream = stream;
    }
    
    /**
     *
     * @param archivoXML Es el archivo XML a analizar
     * @see xml.modelo.Tag
     * @see xml.modelo.ArchivoXML
     * @return el Tag raiz del documento
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public Tag leerXML() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
        DocumentBuilder generador = fabrica.newDocumentBuilder();
        Document doc;
        if(this.stream != null){
            doc = generador.parse(stream);
        }else{
            doc = generador.parse(this);
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
                ProcessingInstruction pi = (ProcessingInstruction)nodo;
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
            }case Node.DOCUMENT_TYPE_NODE:{
            }case Node.DOCUMENT_FRAGMENT_NODE:{
            }case Node.NOTATION_NODE:{
            }
        }

    }

    /**
     *
     * @param tagRaiz Es el tag raiz del arhivo JespXML. el tag raiz, puede tener
     * hijos Tag
     * @param nuevoArchivoXML Es el archivo nuevo que se generará
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
        //escribiendo Instrucciones de Procesamiento
        if(tagRaiz.isIps()){
            for(InstruccionDeProcesamiento ip : tagRaiz.getInstruccionesDeProcesamiento()){
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
        
        
    }
}
