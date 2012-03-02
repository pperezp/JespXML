/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.modelo;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Patricio Pérez Pinto
 */
public class Tag extends Atributo {

    private List<Atributo> atributos;
    private Tag padre;
    private List<Tag> hijos;
    private CData valorCdata;
    private List<Comentario> comentarios;
    public static int TAG = 0;
    public static int COMENTARIO = 1;
    public static int ATRIBUTO = 2;
    public static int TEXTO = 3;

    public Tag(String nombre, String valor) {
        super(nombre, valor);
        this.valorCdata = null;
        atributos = new ArrayList<Atributo>();
        hijos = new ArrayList<Tag>();
        comentarios = new ArrayList<Comentario>();
    }

    //constructor para CDATA
    public Tag(String nombre, CData valor) {
        super(nombre);
        this.valorCdata = valor;
        atributos = new ArrayList<Atributo>();
        hijos = new ArrayList<Tag>();
        comentarios = new ArrayList<Comentario>();
    }
    
    public Tag(String nombre, CData valor, Comentario comentario){
        this(nombre, valor);
        atributos = new ArrayList<Atributo>();
        hijos = new ArrayList<Tag>();
        comentarios = new ArrayList<Comentario>();
        comentarios.add(comentario);
    }
    
    public Tag(String nombre, String valor, Comentario comentario){
        this(nombre, valor);
        atributos = new ArrayList<Atributo>();
        hijos = new ArrayList<Tag>();
        comentarios = new ArrayList<Comentario>();
        comentarios.add(comentario);
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
//        if (this.getValor() != null) {
//            return "<" + this.getNombre() + ">" + this.getValor();
//        } else {
//            return "<" + this.getNombre() + ">" + this.getValorCdata();
//        }
        return this.getNombre()+" (Padre directo: "+(this.getTagPadre() == null? "Ninguno":this.getTagPadre().getNombre())+")";
    }

    public void eliminarTodosLosTagHijos() {
        hijos = new ArrayList<Tag>();
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
        super.setValor(null);
    }

    //getter y setter CDATA
    public boolean isValorCData() {
        return valorCdata != null;
    }

    public boolean isValorNormal() {
        return super.getValor() != null;
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
        if(tagRaiz.isValorNormal()){
            DefaultMutableTreeNode v = new DefaultMutableTreeNode(tagRaiz.getValor());
            if(!tagRaiz.getValor().equalsIgnoreCase(""))
                nodo.add(v);
        }else if(tagRaiz.isValorCData()){
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
//        if(tagRaiz.isValorNormal()){
//            DefaultMutableTreeNode v = new DefaultMutableTreeNode(tagRaiz.getValor());
//            if(!tagRaiz.getValor().equalsIgnoreCase(""))
//                nodo.add(v);
//        }else if(tagRaiz.isValorCData()){
//            DefaultMutableTreeNode v = new DefaultMutableTreeNode(tagRaiz.getValorCdata());
//            if(!tagRaiz.getValorCdata().getValor().equalsIgnoreCase(""))
//                nodo.add(v);
//        }

        raiz.add(nodo);
//        if(tagRaiz.isComentario()){
//            nodo.add(new DefaultMutableTreeNode(tagRaiz.getComentario()));
//        }
//
//        for(Atributo atr:tagRaiz.getAtributos()){
//            nodo.add(new DefaultMutableTreeNode(atr));
//        }

        for(Tag t: tagRaiz.getTagsHijos()){
            procesarSoloTags(t, nodo);
        }
    }
}
