/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.analizador.dom.modelo;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Patricio Pérez Pinto
 */
public class Tag {
    private Tag padre;
    private String contenido;
    private String nombre;
    private List<Atributo> atributos;
    private List<Tag> hijos;
    private List<Comentario> comentarios;
    private List<InstruccionDeProcesamiento> ips;
    private CData valorCdata;
    public static int TAG = 0;
    public static int COMENTARIO = 1;
    public static int ATRIBUTO = 2;
    public static int TEXTO = 3;
    
    public Tag(String nombre) {
        this.nombre = nombre;
        this.contenido = null;
        this.valorCdata = null;
        atributos = new ArrayList<>();
        hijos = new ArrayList<>();
        comentarios = new ArrayList<>();
        ips = new ArrayList<>();
    }

    public Tag(String nombre, String contenido) {
        this.nombre = nombre;
        this.contenido = contenido;
        this.valorCdata = null;
        atributos = new ArrayList<>();
        hijos = new ArrayList<>();
        comentarios = new ArrayList<>();
        ips = new ArrayList<>();
    }

    //constructor para CDATA
    public Tag(String nombre, CData contenido) {
        this.nombre = nombre;
        this.contenido = null;
        this.valorCdata = contenido;
        atributos = new ArrayList<>();
        hijos = new ArrayList<>();
        comentarios = new ArrayList<>();
        ips = new ArrayList<>();
    }
    
    public Tag(String nombre, CData contenido, Comentario comentario){
        this(nombre, contenido);
        atributos = new ArrayList<>();
        hijos = new ArrayList<>();
        comentarios = new ArrayList<>();
        comentarios.add(comentario);
        ips = new ArrayList<>();
    }
    
    public Tag(String nombre, String contenido, Comentario comentario){
        this(nombre, contenido);
        atributos = new ArrayList<>();
        hijos = new ArrayList<>();
        comentarios = new ArrayList<>();
        comentarios.add(comentario);
        ips = new ArrayList<>();
    }

    public List<Atributo> getAtributos() {
        return atributos;
    }

    public void setAtributos(List<Atributo> atributos) {
        this.atributos = atributos;
    }

    public void addAtributo(Atributo atributo) {
        atributos.add(atributo);
    }

    public void addAtributo(String nombre, String valor) {
        atributos.add(new Atributo(nombre, valor));
    }

    public boolean isAtributos() {
        return !atributos.isEmpty();
    }

    public void setPadre(Tag padre) {
        this.padre = padre;
    }

    public Tag getTagPadre() {
        return padre;
    }

    public void addTagHijo(Tag tagHijo) {
        hijos.add(tagHijo);
        tagHijo.setPadre(this);
    }

    public boolean isHijos() {
        return !hijos.isEmpty();
    }
    
    public boolean isIps(){
        return !ips.isEmpty();
    }

    public List<InstruccionDeProcesamiento> getInstruccionesDeProcesamiento() {
        return ips;
    }

    public Tag getTagHijo(Tag tagHijo) {
        for (Tag t : hijos) {
            if (t.equals(tagHijo)) {
                return t;
            }
        }
        return null;
    }

    public static Tag getTagHijoRecursivo(Tag root, Tag tagAbuscar) {
        if(root.equals(tagAbuscar)){
            return root;
        }
        for (Tag hijo : root.getTagsHijos()) {
            Tag t = getTagHijoRecursivo(hijo, tagAbuscar);//wea transfuga xD cara e cuea
            if(t != null){// si es distino de null, es porque lo encontro
                return t;
            }//y si es null, sigo buscando en el arbol
        }
        return null;
    }

    public List<Tag> getTagsHijos() {
        return hijos;
    }

    @Override
    public String toString() {
        return "<" + this.getNombre() + ">" + this.getContenido();
    }

    public void eliminarTodosLosTagHijos() {
        hijos = new ArrayList<>();
    }

    public void actualizarValorAtributo(String nombre, String valor) {
        for (Atributo a : this.atributos) {
            if (a.getNombre().equalsIgnoreCase(nombre)) {
                a.setValor(valor);
                break;
            }
        }
    }

    public void actualizarValorAtributo(String nombre, int valor) {
        for (Atributo a : this.atributos) {
            if (a.getNombre().equalsIgnoreCase(nombre)) {
                a.setValor(Integer.toString(valor));
                break;
            }
        }
    }

    public String getValorDeAtributo(String nombre) {
        for (Atributo a : this.atributos) {
            if (a.getNombre().equalsIgnoreCase(nombre)) {
                return a.getValor();
            }
        }
        return null;
    }

    public void eliminarTagHijoByName(String nombre) {
        for (Tag t : hijos) {
            if (t.getNombre().equalsIgnoreCase(nombre)) {
                hijos.remove(t);
                break;
            }
        }
    }

    //getter y setter CDATA
    public CData getValorCdata() {
        return valorCdata;
    }

    public void setValorCdata(CData valorCdata) {
        this.valorCdata = valorCdata;
        this.addContenido(null);
    }

    //getter y setter CDATA
    public boolean isContenidoCData() {
        return valorCdata != null;
    }

    public boolean isContenidoNormal() {
        return this.getContenido() != null;
    }
    
    public void addComentario(Comentario comentario) {
        comentarios.add(comentario);
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }
    
    
    public boolean isComentario(){
        return !comentarios.isEmpty();
    }
    
    public void construirArbol(javax.swing.JTree arbol, ImageIcon[] iconos, Color colorFondo){
        DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("raiz");
        procesar(this, raiz);
        
        arbol.setBackground(colorFondo);
        arbol.setCellRenderer(new CellRender(iconos[Tag.TAG], iconos[Tag.COMENTARIO], iconos[Tag.ATRIBUTO], iconos[Tag.TEXTO], colorFondo));
        arbol.setRootVisible(false);
        arbol.setModel(new javax.swing.tree.DefaultTreeModel(raiz));
        arbol.expandRow(0);
    }
    
    private void procesar(Tag tagRaiz, DefaultMutableTreeNode raiz){
        DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(tagRaiz);
        if(tagRaiz.isContenidoNormal()){
            DefaultMutableTreeNode v = new DefaultMutableTreeNode(tagRaiz.getContenido());
            if(!tagRaiz.getContenido().equalsIgnoreCase(""))
                nodo.add(v);
        }else if(tagRaiz.isContenidoCData()){
            DefaultMutableTreeNode v = new DefaultMutableTreeNode(tagRaiz.getValorCdata());
            if(!tagRaiz.getValorCdata().getValor().equalsIgnoreCase(""))
                nodo.add(v);
        }
        
        raiz.add(nodo);
        if(tagRaiz.isComentario()){
            for(Comentario c: tagRaiz.getComentarios()){
                nodo.add(new DefaultMutableTreeNode(c));
            }
        }
              
        for(Atributo atr:tagRaiz.getAtributos()){
            nodo.add(new DefaultMutableTreeNode(atr));
        }
        
        for(Tag t: tagRaiz.getTagsHijos()){
            procesar(t, nodo);
        }
    }

    public void construirArbolSoloTags(javax.swing.JTree arbol, ImageIcon[] iconos, Color colorFondo){
        DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("raiz");
        procesarSoloTags(this, raiz);

        arbol.setBackground(colorFondo);
        arbol.setCellRenderer(new CellRender(iconos[Tag.TAG], iconos[Tag.COMENTARIO], iconos[Tag.ATRIBUTO], iconos[Tag.TEXTO], colorFondo));
        arbol.setRootVisible(false);
        arbol.setModel(new javax.swing.tree.DefaultTreeModel(raiz));
        arbol.expandRow(0);
    }

    private void procesarSoloTags(Tag tagRaiz, DefaultMutableTreeNode raiz){
        DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(tagRaiz);
        raiz.add(nodo);
        for(Tag t: tagRaiz.getTagsHijos()){
            procesarSoloTags(t, nodo);
        }
    }

    /**
     * @return the contenido
     */
    public String getContenido() {
        if(this.contenido != null){
            return contenido;
        }else if(this.valorCdata != null){
            return this.valorCdata.getValor();
        }else{
            return "";
        }
    }

    /**
     * @param contenido the contenido to set
     */
    public void addContenido(String contenido) {
        if(this.contenido == null)
            this.contenido = contenido;
        else this.contenido += contenido;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void addInstruccionDeProcesamiento(InstruccionDeProcesamiento ip){
        ips.add(ip);
    }
    
    public void addInstruccionDeProcesamiento(String target, String data){
        ips.add(new InstruccionDeProcesamiento(target, data));
    }
}
