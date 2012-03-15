/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.analizador;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import xml.modelo.*;

/**
 *
 * @author Patricio Pérez Pinto
 */
public class XML {

    private static ArchivoXML ar;
    private static List<Tag> tags;
    private static Tag raiz;
    private static int cont;
    public static final int OPCION_SIMPLE = 1;
    public static final int OPCION_COMPLEJA = 2;

    /**
     * 
     * @param archivoXML Es el archivo XML a analizar
     * @param opcionDeProcesamiento
     * Este parametro se encuentra en las constantes de la clase XML:
     * <br>Parametro <strong>XML.OPCION_SIMPLE</strong>
     * <br>La opción simple indica analizar el documento XML y dejarlo expresado<br>
     * como una lista de tags, sin jerarquía
     * <br>Parametro <strong>XML.OPCION_COMPLEJA</strong>
     * <br>La opción compleja indica analizar el documento XML y dejarlo expresado<br>
     * como un conjunto de tags jerarquicamente ordenados. Cada Tag tiene un padre (Tag getTagPadre())<br0>
     * como tambien tiene una lista de Tags Hijos (List<Tag> getTagsHijos()) 
     * @see xml.modelo.Tag
     * @see xml.modelo.ArchivoXML
     * @return un Objeto del tipo ArchivoXML
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException 
     */
    public static ArchivoXML procesarArchivoXML(File archivoXML, int opcionDeProcesamiento) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
        DocumentBuilder generador = fabrica.newDocumentBuilder();
        Document doc = generador.parse(archivoXML);
        NodeList list = doc.getChildNodes();
        tags = new ArrayList<Tag>();
//        Log.newLog(new File("logAnalizadorXML.log"));
        cont = 0;
        if (opcionDeProcesamiento == OPCION_SIMPLE) {
            procesarXML(list);
            ar = new ArchivoXML(tags);
        } else {
            raiz = new Tag(doc.getFirstChild().getNodeName(), "");
            procesarXMLRecursivamente(raiz, list);
            ar = new ArchivoXML(raiz);
        }
        return ar;
    }

    private static void procesarXML(NodeList list) {
        Tag t = null;
        for (int i = 0; i < list.getLength(); i++) {
            Node nodo = list.item(i);
            if (nodo instanceof Element) {
                Element hijo = (Element) nodo;
                t = new Tag(hijo.getTagName(), "");
                t.setPadre(new Tag(nodo.getParentNode().getNodeName(), nodo.getParentNode().getNodeValue()));
                if (nodo.hasAttributes()) {
                    for (int j = 0; j < nodo.getAttributes().getLength(); j++) {
                        Node atributo = nodo.getAttributes().item(j);
                        t.addAtributo(atributo.getNodeName(), atributo.getNodeValue());
                    }
                }
            }
            if (nodo.getChildNodes().getLength() > 1) {
                tags.add(t);
                procesarXML(nodo.getChildNodes());
            } else if (nodo instanceof Element) {
                t.setValor(nodo.getTextContent());
                tags.add(t);
            }

        }
    }

    private static void procesarXMLRecursivamente(Tag root, NodeList list) {
        for (int i = 0; i < list.getLength(); i++) {
            Node nodo = list.item(i);

            if (nodo instanceof Element) {
                if (cont == 0) {//es la raiz
//                    root = new Tag(nodo.getNodeName(), "");
//                    System.out.println("<" + root.getNombre() + ">");
//                    System.out.println("Analizando raiz: "+root.toString().toUpperCase());
//                    Log.logger.log(Level.INFO, "Analizando raiz: {0}", root.toString().toUpperCase());
                    cont++;

                    analizarAtributos(nodo, root);
                    if (nodo.hasChildNodes()) {
//                        System.out.println("La raiz "+root.toString().toUpperCase()+" tiene hijos. LLamando Recursivamente con root = "+root);
//                        Log.logger.log(Level.INFO, "La raiz {0} tiene hijos. LLamando Recursivamente con root = {1}", new Object[]{root.toString().toUpperCase(), root});
                        procesarXMLRecursivamente(root, nodo.getChildNodes());
                    } else {
                        root.setValor(nodo.getTextContent());
//                        Log.logger.log(Level.INFO, "La raiz {0} NO tiene hijos.", new Object[]{root.toString().toUpperCase()});
                    }
                } else {//si no es raiz, es hijo
//                    System.out.println("Analizando hijo: "+root.toString().toUpperCase());
//                    Log.logger.log(Level.INFO, "Analizando hijo: {0}", root.toString().toUpperCase());
                    if (nodo.hasChildNodes()) {
//                        System.out.println("El hijo "+root.toString().toUpperCase()+" tiene hijos ("+nodo.getChildNodes().getLength()+" hijos)");
//                        Log.logger.log(Level.INFO, "El hijo {0} tiene hijos ({1} hijos)", new Object[]{root.toString().toUpperCase(), nodo.getChildNodes().getLength()});
                        Tag hijo = new Tag(nodo.getNodeName(), "");
                        root.addTagHijo(hijo);
//                        System.ouCt.println("Agregando a "+root.toString().toUpperCase()+" un nuevo hijo ("+nodo.getNodeName()+")");
//                        Log.logger.log(Level.INFO, "Agregando a {0} un nuevo hijo ({1})", new Object[]{root.toString().toUpperCase(), nodo.getNodeName()});
                        hijo = root.getTagHijo(hijo);
//                        System.out.pCrintln("Recuperando al hijo agregado: hijo recuperado -->"+hijo);
//                        System.out.println("<" + hijo.getNombre() + ">");
//                        Log.logger.log(Level.INFO, "Recuperando al hijo agregado: hijo recuperado -->{0}", hijo);
                        analizarAtributos(nodo, hijo);
//                        System.outC.println("LLamando Recursivamente con root = "+hijo);
//                        Log.logger.log(Level.INFO, "LLamando Recursivamente con root = {0}", hijo);
                        procesarXMLRecursivamente(hijo, nodo.getChildNodes());
                    }else{
                        Tag hijo = new Tag(nodo.getNodeName(), "");
                        root.addTagHijo(hijo);
//                        Log.logger.log(Level.INFO, "Agregando a {0} un nuevo hijo ({1})---> es un tag asi </>", new Object[]{root.toString().toUpperCase(), nodo.getNodeName()});
                        hijo = root.getTagHijo(hijo);
//                        System.out.pCrintln("Recuperando al hijo agregado: hijo recuperado -->"+hijo);
//                        System.out.println("<" + hijo.getNombre() + ">");
//                        Log.logger.log(Level.INFO, "Recuperando al hijo agregado: hijo recuperado -->{0}", hijo);
                        analizarAtributos(nodo, hijo);
                    } //System.out.println(root+": "+nodo.getTextContent()+"  -   "+nodo.getNodeName()+" atri-"+nodo.getAttributes().getLength());
                }
            } else if (nodo instanceof Comment){
                Comment c = (Comment)nodo;
                root.addComentario(new Comentario(c.getTextContent()));
//                System.out.println("lenyendo comentario");
            }else if(nodo instanceof CDATASection){
                CDATASection cData = (CDATASection)nodo;
                root.setValorCdata(new CData(cData.getTextContent()));
            }else if(nodo instanceof Text){
                //System.out.println("Elemento NODO = "+nodo.getNodeName()+" NO ES UN ELEMENTO! (VALOR = "+nodo.getNodeValue()+")");
                Text texto = (Text) nodo;
                if (!texto.getData().trim().equalsIgnoreCase("")) {
//                    System.out.printlCn("AGREGANDO VALOR "+texto.getData()+" a tag "+root);
//                    Log.logger.log(Level.INFO, "AGREGANDO VALOR {0} a tag {1}", new Object[]{texto.getData(), root});
                    root.setValor(texto.getData());
                }

            }
        }
    }

    /**
     * 
     * @param tagRaiz
     * Es el tag raiz del arhivo XML. el tag raiz, puede tener hijos Tag
     * @param nuevoArchivoXML
     * Es el archivo nuevo que se generará
     * @throws ParserConfigurationException
     * @throws TransformerConfigurationException
     * @throws FileNotFoundException
     * @throws TransformerException 
     */
    public static void crearArchivoXML(Tag tagRaiz, File nuevoArchivoXML) throws ParserConfigurationException, TransformerConfigurationException, FileNotFoundException, TransformerException {
        DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
        DocumentBuilder generador = fabrica.newDocumentBuilder();
        
        
        Document doc = generador.newDocument();
        
        Element root = doc.createElement(tagRaiz.getNombre());
        
        doc.appendChild(root);
        crearArchivo(root, tagRaiz, doc);

        doc.normalizeDocument();
        Transformer t = TransformerFactory.newInstance().newTransformer();
        
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.setOutputProperty(OutputKeys.METHOD, "XML");
        t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(nuevoArchivoXML)));

    }
    
    public static void agregarTag(Tag tagRaiz, File archivoXML) throws ParserConfigurationException, TransformerConfigurationException, FileNotFoundException, TransformerException {
        DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
        DocumentBuilder generador = fabrica.newDocumentBuilder();
        Document doc = generador.newDocument();
        Element root = doc.createElement(tagRaiz.getNombre());
        doc.appendChild(root);
        crearArchivo(root, tagRaiz, doc);
        
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(archivoXML, true)));

    }

    private static void analizarAtributos(Node nodo, Tag tagRaiz) {

//        System.out.priCntln("---------------------------------------------------------");
//        System.out.printCln("Analizando si raiz "+tagRaiz.toString().toUpperCase()+" posee atributos...");
//        Log.logger.log(Level.INFO, "---------------------------------------------------------");
//        Log.logger.log(Level.INFO, "Analizando si raiz {0} posee atributos...", tagRaiz.toString().toUpperCase());
        if (nodo.hasAttributes()) {
//            System.out.pCrintln("Raiz "+tagRaiz.toString().toUpperCase()+" tiene "+nodo.getAttributes().getLength()+ " atributos!");
//            System.out.println("ATRIBUTOS DE " + raiz.getNombre());
//            Log.logger.log(Level.INFO, "Raiz {0} tiene {1} atributos!", new Object[]{tagRaiz.toString().toUpperCase(), nodo.getAttributes().getLength()});
            for (int j = 0; j < nodo.getAttributes().getLength(); j++) {
                Node atributo = nodo.getAttributes().item(j);
                tagRaiz.addAtributo(atributo.getNodeName(), atributo.getNodeValue());
//                System.out.Cprintln("\tAgregando atributo ("+atributo.getNodeName()+"="+atributo.getNodeValue()+") a raiz "+tagRaiz.toString().toUpperCase());
//                System.out.println(atributo.getNodeName() + "='" + atributo.getNodeValue() + "'");
//                Log.logger.log(Level.INFO, "\tAgregando atributo ({0}={1}) a raiz {2}", new Object[]{atributo.getNodeName(), atributo.getNodeValue(), tagRaiz.toString().toUpperCase()});
            }
        } else {
//            System.outC.println("Raiz "+tagRaiz.toString().toUpperCase()+" NO posee atributos");
//            Log.logger.log(Level.INFO, "Raiz {0} NO posee atributos", tagRaiz.toString().toUpperCase());
        }

//        System.out.prinCtln("---------------------------------------------------------");
//        Log.logger.log(Level.INFO, "---------------------------------------------------------");
    }

    private static void crearArchivo(Element root, Tag tagRaiz, Document doc) {
        
        /*if (tagRaiz.getValor() != null) {
            Text text = doc.createTextNode(tagRaiz.getValor());
            root.appendChild(text);
        }
        //TAG CON VALOR CDATA
        if (tagRaiz.getValorCdata() != null) {
            CDATASection cdata = doc.createCDATASection(tagRaiz.getValorCdata().toString());
            root.appendChild(cdata);
        }*/
        
        //escribiendo comentario
        if(tagRaiz.isComentario()){
            for(Comentario c : tagRaiz.getComentarios()){
                Comment comentario = doc.createComment(c.getComentario());
                root.appendChild(comentario);
            }
        }
        
        //escribiendo Valor del Tag
        if(tagRaiz.isValorCData()){
            CDATASection cdata = doc.createCDATASection(tagRaiz.getValorCdata().toString());
            root.appendChild(cdata);
        }else if(tagRaiz.isValorNormal()){
            Text valor = doc.createTextNode(tagRaiz.getValor());
            root.appendChild(valor);
        }
        
        //escribiendo Atributos del Tag
        for (Atributo atr : tagRaiz.getAtributos()) {
            root.setAttribute(atr.getNombre(), atr.getValor());
        }
        
        //escribiendo hijos del Tag recursivamente
        for (Tag t : tagRaiz.getTagsHijos()) {
//            System.out.println(t.getNombre());
            Element hijo = doc.createElement(t.getNombre());

            root.appendChild(hijo);
            crearArchivo(hijo, t, doc);
        }
    }
}

