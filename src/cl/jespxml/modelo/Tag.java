package cl.jespxml.modelo;

import java.awt.Color;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
/*
 * Se usa esto en vez de ArrayList, porque si intento
 * borrar un elemento, lanza una ConcurrentModificationException
 */
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import cl.jespxml.excepciones.AtributoNotFoundException;
import cl.jespxml.excepciones.TagHijoNotFoundException;
import cl.jespxml.gui.CellRender;
import cl.jespxml.gui.DatosArbolGUI;

/**
 * Esta clase permite crear un elemento (Tag) en XML. Un elemento es un
 * componente lógico de la jerarquía de un documento, que a su vez puede
 * descomponer en otros elementos.
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
    private List<InstruccionDeProcesamiento> instruccionesDeProcesamiento;
    private CData valorCdata;
    /**
     *
     */
    public static int TAG = 0;
    /**
     *
     */
    public static int COMENTARIO = 1;
    /**
     *
     */
    public static int ATRIBUTO = 2;
    /**
     *
     */
    public static int TEXTO = 3;

    /**
     * Enumeracion para determinar la cantidad de tags que necesitas al momento
     * de rescatar hijos.
     *
     */
    public static enum Cantidad {
        /**
         *
         */
        TODOS_LOS_TAGS,
        /**
         *
         */
        PRIMERA_OCURRENCIA
    }

    /**
     * Construye un tag sólo con el nombre
     *
     * @param nombre nombre del tag
     */
    public Tag(String nombre) {
        this.nombre = nombre;
        this.contenido = null;
        this.valorCdata = null;
        atributos = new CopyOnWriteArrayList<Atributo>();
        hijos = new CopyOnWriteArrayList<Tag>();
        comentarios = new CopyOnWriteArrayList<Comentario>();
        instruccionesDeProcesamiento = new CopyOnWriteArrayList<InstruccionDeProcesamiento>();
    }

    /**
     * Construye un tag con un nombre y contenido
     *
     * @param nombre nombre del tag
     * @param contenido contenido del tag
     */
    public Tag(String nombre, String contenido) {
        this.nombre = nombre;
        this.contenido = contenido;
        this.valorCdata = null;
        atributos = new CopyOnWriteArrayList<Atributo>();
        hijos = new CopyOnWriteArrayList<Tag>();
        comentarios = new CopyOnWriteArrayList<Comentario>();
        instruccionesDeProcesamiento = new CopyOnWriteArrayList<InstruccionDeProcesamiento>();
    }

    //constructor para CDATA
    /**
     * Construye un tag con un nombre y contenido CData
     *
     * @see CData
     * @param nombre nombre del tag
     * @param contenido contenido del tag
     */
    public Tag(String nombre, CData contenido) {
        this.nombre = nombre;
        this.contenido = null;
        this.valorCdata = contenido;
        atributos = new CopyOnWriteArrayList<Atributo>();
        hijos = new CopyOnWriteArrayList<Tag>();
        comentarios = new CopyOnWriteArrayList<Comentario>();
        instruccionesDeProcesamiento = new CopyOnWriteArrayList<InstruccionDeProcesamiento>();
    }

    /**
     * Construye un tag con un nombre, un contenido CData y un comentario
     *
     * @see CData
     * @see Comentario
     * @param nombre nombre del tag
     * @param contenido contenido CData del tag
     * @param comentario comentario
     */
    public Tag(String nombre, CData contenido, Comentario comentario) {
        this(nombre, contenido);
        atributos = new CopyOnWriteArrayList<Atributo>();
        hijos = new CopyOnWriteArrayList<Tag>();
        comentarios = new CopyOnWriteArrayList<Comentario>();
        instruccionesDeProcesamiento = new CopyOnWriteArrayList<InstruccionDeProcesamiento>();
        comentarios.add(comentario);
    }

    /**
     * Construye un tag con un nombre, un contenido y un comentario
     *
     * @see Comentario
     * @param nombre nombre del tag
     * @param contenido contenido del tag
     * @param comentario comentarios
     */
    public Tag(String nombre, String contenido, Comentario comentario) {
        this(nombre, contenido);
        comentarios.add(comentario);
        atributos = new CopyOnWriteArrayList<Atributo>();
        hijos = new CopyOnWriteArrayList<Tag>();
        comentarios = new CopyOnWriteArrayList<Comentario>();
        instruccionesDeProcesamiento = new CopyOnWriteArrayList<InstruccionDeProcesamiento>();
    }

    /**
     *
     * @return una lista de Atributos
     * @see Atributo
     */
    public List<Atributo> getAtributos() {
        return atributos;
    }

    /**
     * Actualiza la lista de atributos
     *
     * @return
     * @see Atributo
     * @param atributos la lista de atributos nueva
     */
    public Tag setAtributos(List<Atributo> atributos) {
        this.atributos = atributos;
        return this;
    }

    /**
     * Añade un atributo a la lista de atributos
     *
     * @return El objeto Tag en sí mismo.
     * @see Atributo
     * @param atributo el atributo a añadir
     */
    public Tag addAtributo(Atributo atributo) {
        atributos.add(atributo);
        return this;
    }

    /**
     * Añade un atributo a la lista de atributos
     *
     * @see Atributo
     * @param nombre
     * @param valor
     * @deprecated Método deprecado. En su reemplazo utilice el método public
     * void addAtributo(Atributo atributo)
     */
    public void addAtributo(String nombre, String valor) {
        atributos.add(new Atributo(nombre, valor));
    }

    /**
     * Revisa si existen atributos en este Tag
     *
     * @return true o false dependiendo si existen o no atributos
     */
    public boolean isAtributos() {
        return !atributos.isEmpty();
    }

    /**
     * Actualizar el padre de este tag
     *
     * @param padre Objeto tag que representa el padre jerarquicamente
     * @return
     * @see Tag
     */
    public Tag setPadre(Tag padre) {
        this.padre = padre;
        return this;
    }

    /**
     *
     * @return el tag padre de este tag
     * @see Tag
     */
    public Tag getTagPadre() {
        return padre;
    }

    /**
     * Añade un tag a la lista de hijos de este Tag
     *
     * @param tagHijo
     * @return
     * @see Tag
     */
    public Tag addTagHijo(Tag tagHijo) {
        hijos.add(tagHijo);
        tagHijo.setPadre(this);
        return this;
    }

    /**
     * Verifica si este tag posee tags hijos
     *
     * @return true o false dependiendo si tiene tags hijos o no
     */
    public boolean isHijos() {
        return !hijos.isEmpty();
    }

    /**
     * Verifica si esta tags posee instrucciones de procesamiento
     *
     * @return true o false dependiendo si tiene instrucciones de procesamiento
     * @see InstruccionDeProcesamiento
     */
    public boolean isinstruccionesDeProcesamiento() {
        return !instruccionesDeProcesamiento.isEmpty();
    }

    /**
     *
     * @return una lista con todas las intrucciones de procesamiento
     * @see InstruccionDeProcesamiento
     */
    public List<InstruccionDeProcesamiento> getInstruccionesDeProcesamiento() {
        return instruccionesDeProcesamiento;
    }

    /**
     * Obtiene un hijo. Lo obtienes en una lista de tags
     *
     * @param nombre el nombre del tag a buscar
     * @param cantidad la cantidad de hijos que quieres.
     * @return Puede obtener todos los tags que coincidan con el nombreTag o
     * solo obtener la primera ocurrencia, o sea una lista con un solo hijo Tag
     *
     * @see Cantidad
     */
    public List<Tag> getTagHijoByName(String nombre, Tag.Cantidad cantidad) {
        List<Tag> tagHijos = new CopyOnWriteArrayList<Tag>();
        for (Tag t : hijos) {
            if (t.getNombre().equalsIgnoreCase(nombre)) {
                tagHijos.add(t);
                if (cantidad == Tag.Cantidad.PRIMERA_OCURRENCIA) {
                    return tagHijos;
                }
            }
        }
        return tagHijos;
    }

    public Tag getTagHijoByName(String nombreTag) throws TagHijoNotFoundException {
        List<Tag> tags = getTagHijoByName(nombreTag, Tag.Cantidad.PRIMERA_OCURRENCIA);
        if (tags.isEmpty()) {
            throw new TagHijoNotFoundException("No se encuentra un tag con el nombre:  <" + nombreTag + ">");
        } else {
            return tags.get(0);
        }
    }

    /**
     * Método para buscar tags hijos, por varios nombres
     *
     * @param nombreTag nombres de los tags a buscar. Ej: ("libros", "autores");
     * @return una lista con los tags encontrados
     */
    public List<Tag> getTagHijoByName(String... nombreTag) {
        List<Tag> tags = new CopyOnWriteArrayList<Tag>();
        for (String n : nombreTag) {
            for (Tag t : getTagHijoByName(n, Tag.Cantidad.TODOS_LOS_TAGS)) {
                tags.add(t);
            }
        }

        return tags;
    }

    /**
     * Permite obtener un tag buscándolo en todo el arbol
     *
     * @param root raiz del documento xml en un Tag
     * @param tagAbuscar tag a buscar
     * @return el tag encontrado
     *
     */
//    public static Tag findTag(Tag root, Tag tagAbuscar) {
//        if(root.equals(tagAbuscar)){
//            return root;
//        }
//        for (Tag hijo : root.getTagsHijos()) {
//            Tag t = findTag(hijo, tagAbuscar);//wea transfuga xD cara e cuea
//            if(t != null){// si es distino de null, es porque lo encontro
//                return t;
//            }//y si es null, sigo buscando en el arbol
//        }
//        return null;
//    }
    /**
     *
     * @return una lista de todos los hijos de esta tag
     * @see Tag
     */
    public List<Tag> getTagsHijos() {
        return hijos;
    }

    @Override
    public String toString() {
        return "<" + this.getNombre() + ">" + this.getContenido();
    }

    /**
     * Elimina todos los hijos tag
     *
     * @return
     */
    public Tag eliminarTodosLosTagHijos() {
        hijos = new CopyOnWriteArrayList<Tag>();
        return this;
    }

    /**
     * Elimina un hijo por su nombre
     *
     * @param nombre nombre del tag a eliminar
     * @return
     */
    public Tag eliminarTagHijoByName(String nombre) {
        for (Tag t : hijos) {
            if (t.getNombre().equalsIgnoreCase(nombre)) {
                hijos.remove(t);
            }
        }
        return this;
    }

    /**
     * Elimina el primer tag que coincida con ese atributo
     *
     * @param atr
     * @return
     */
    public Tag eliminarTagHijoByAtributo(Atributo atr) {
        for (Tag t : hijos) {
            for (Atributo a : t.atributos) {
                if (a.equals(atr)) {
                    hijos.remove(t);
                    break;
                }
            }
        }

        return this;
    }

    /**
     * Actualizar el valor de un atributo ya existente
     *
     * @param nombre el nombre del atributo al cual se le va a cambiar su valor
     * @param valor el nuevo valor del atributo
     * @return retorna true o false dependiendo si se acutliza el valor o no
     */
    public Tag actualizarValorAtributo(String nombre, Object valor) {
        for (Atributo a : this.atributos) {
            if (a.getNombre().equalsIgnoreCase(nombre)) {
                a.setValor(valor);
                break;
            }
        }
        return this;
    }

    /**
     * Actualizar el valor de un atributo ya existente
     *
     * @param nombre el nombre del atributo al cual se le va a cambiar su valor
     * @param valor el nuevo valor del atributo
     * @return retorna true o false dependiendo si se actualiza el valor o no
     * @deprecated Usar {@link #actualizarValorAtributo(java.lang.String, java.lang.Object)
     * }
     */
    public boolean actualizarValorAtributo(String nombre, int valor) {
        for (Atributo a : this.atributos) {
            if (a.getNombre().equalsIgnoreCase(nombre)) {
                a.setValor(Integer.toString(valor));
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param nombre nombre del atributo para buscar su valor
     * @return el valor del atributo especificado y si no se encuentra el
     * atributo retornará null
     * @throws cl.jespxml.excepciones.AtributoNotFoundException
     */
    public Object getValorDeAtributo(String nombre) throws AtributoNotFoundException {
        for (Atributo a : this.atributos) {
            if (a.getNombre().equalsIgnoreCase(nombre)) {
                return a.getValor();
            }
        }
        throw new AtributoNotFoundException("Atributo no encontrado. Nombre: " + nombre);
    }

    /**
     *
     * @param nombre se llamará al método toString de ese objeto
     * @return el valor del atributo
     * @throws cl.jespxml.excepciones.AtributoNotFoundException
     */
    public Object getValorDeAtributo(Object nombre) throws AtributoNotFoundException {
        return getValorDeAtributo(nombre.toString());
    }

    /**
     *
     * @return un objeto CData que contiene el valor
     * @see CData
     * @deprecated Utilice el método {@link #getContenido() }
     * @see Tag#getContenido()
     */
    public CData getValorCdata() {
        return valorCdata;
    }

    /**
     * Reemplaza el valor CData actual por uno nuevo
     *
     * @param valorCdata objeto CData
     * @return
     * @see CData
     */
    public Tag setValorCdata(CData valorCdata) {
        this.valorCdata = valorCdata;
        this.addContenido(null);
        return this;
    }

    /**
     * Verificar si el tag posee un contenido CData
     *
     * @return true o false dependiendo si posee un contenido CData
     * @see CData
     */
    public boolean isContenidoCData() {
        return valorCdata != null;
    }

    /**
     * Verificar si el tag posee un contenido distinto de CData
     *
     * @return true o false dependiendo si posee un contenido distinto de CData
     */
    public boolean isContenidoNormal() {
        return this.getContenido() != null;
    }

    /**
     * Agrega comentario al tag
     *
     * @param comentario Comentario a agregar
     * @return
     * @see Comentario
     */
    public Tag addComentario(Comentario comentario) {
        comentarios.add(comentario);
        return this;
    }

    /**
     *
     * @return una lista de todos los comentario de este tag
     * @see Comentario
     */
    public List<Comentario> getComentarios() {
        return comentarios;
    }

    /**
     * Verifica si hay comentarios o no
     *
     * @return true o false dependiendo si hay comentarios o no
     */
    public boolean isComentario() {
        return !comentarios.isEmpty();
    }

    /**
     * Método para construir un arbol gráfico del tag
     *
     * @param arbol objeto JTree
     * @param iconos un arreglo de iconos (icono tag, icono comentario, icono
     * atributos e icono texto)
     * @param colorFondo el color de fondo del arbol JTree
     * @deprecated Método deprecado. Utilizar el método {@link #construirArbol(org.jespxml.gui.DatosArbolGUI)
     * }
     * @see JTree
     * @see ImageIcon
     * @see Color
     */
    public void construirArbol(javax.swing.JTree arbol, ImageIcon[] iconos, Color colorFondo) {
        DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("raiz");
        procesar(this, raiz);

        arbol.setBackground(colorFondo);
        arbol.setCellRenderer(new CellRender(iconos[Tag.TAG], iconos[Tag.COMENTARIO], iconos[Tag.ATRIBUTO], iconos[Tag.TEXTO], colorFondo));
        arbol.setRootVisible(false);
        arbol.setModel(new javax.swing.tree.DefaultTreeModel(raiz));
        arbol.expandRow(0);
    }

    /**
     * Método para construir un arbol gráfico del tag
     *
     * @param datos un objeto del tipo DatosArbolGUI
     * @see DatosArbolGUI
     */
    public void construirArbol(DatosArbolGUI datos) {
        DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("raiz");
        procesar(this, raiz);

        JTree arbol = datos.getArbol();

        arbol.setBackground(datos.getColorDeFondo());
        arbol.setCellRenderer(new CellRender(datos));
        arbol.setRootVisible(false);
        arbol.setModel(new javax.swing.tree.DefaultTreeModel(raiz));
        arbol.expandRow(0);
    }

    private void procesar(Tag tagRaiz, DefaultMutableTreeNode raiz) {
        DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(tagRaiz);
        if (tagRaiz.isContenidoNormal()) {
            DefaultMutableTreeNode v = new DefaultMutableTreeNode(tagRaiz.getContenido());
            if (!tagRaiz.getContenido().equalsIgnoreCase("")) {
                nodo.add(v);
            }
        } else if (tagRaiz.isContenidoCData()) {
            DefaultMutableTreeNode v = new DefaultMutableTreeNode(tagRaiz.getValorCdata());
            if (!tagRaiz.getValorCdata().getValor().equalsIgnoreCase("")) {
                nodo.add(v);
            }
        }

        raiz.add(nodo);
        if (tagRaiz.isComentario()) {
            for (Comentario c : tagRaiz.getComentarios()) {
                nodo.add(new DefaultMutableTreeNode(c));
            }
        }

        for (Atributo atr : tagRaiz.getAtributos()) {
            nodo.add(new DefaultMutableTreeNode(atr));
        }

        for (Tag t : tagRaiz.getTagsHijos()) {
            procesar(t, nodo);
        }
    }

    /**
     *
     * @param arbol
     * @param iconos
     * @param colorFondo
     * @deprecated Utilizar el método {@link #construirArbolSoloTags(org.jespxml.gui.DatosArbolGUI)
     * }
     */
    public void construirArbolSoloTags(javax.swing.JTree arbol, ImageIcon[] iconos, Color colorFondo) {
        DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("raiz");
        procesarSoloTags(this, raiz);

        arbol.setBackground(colorFondo);
        arbol.setCellRenderer(new CellRender(iconos[Tag.TAG], iconos[Tag.COMENTARIO], iconos[Tag.ATRIBUTO], iconos[Tag.TEXTO], colorFondo));
        arbol.setRootVisible(false);
        arbol.setModel(new javax.swing.tree.DefaultTreeModel(raiz));
        arbol.expandRow(0);
    }

    /**
     * Método para construir un arbol gráfico del tag. Pero solamente los tags,
     * sin su contenido, ni atributos, ni comentarios
     *
     * @param datos un objeto del tipo DatosArbolGUI
     * @see DatosArbolGUI
     */
    public void construirArbolSoloTags(DatosArbolGUI datos) {
        DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("raiz");
        procesarSoloTags(this, raiz);

        JTree arbol = datos.getArbol();

        arbol.setBackground(datos.getColorDeFondo());
        arbol.setCellRenderer(new CellRender(datos));
        arbol.setRootVisible(false);
        arbol.setModel(new javax.swing.tree.DefaultTreeModel(raiz));
        arbol.expandRow(0);
    }

    private void procesarSoloTags(Tag tagRaiz, DefaultMutableTreeNode raiz) {
        DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(tagRaiz);
        raiz.add(nodo);
        for (Tag t : tagRaiz.getTagsHijos()) {
            procesarSoloTags(t, nodo);
        }
    }

    /**
     * @return el contenido de este tag, ya sea CData o contenido normal
     */
    public String getContenido() {
        if (this.contenido != null) {
            return contenido.trim();
        } else if (this.valorCdata != null) {
            return this.valorCdata.getValor().trim();
        } else {
            return "";
        }
    }

    /**
     * Añade contenido al contenido del tag
     *
     * @param contenido el contenido
     * @return
     */
    public Tag addContenido(String contenido) {
        if (this.contenido == null) {
            this.contenido = contenido;
        } else {
            this.contenido += contenido;
        }

        return this;
    }

    /**
     * Este método cambia totalmente el contenido del tag
     *
     * @param contenido el contenido como String
     * @return
     */
    public Tag setContenido(String contenido) {
        this.contenido = contenido;
        return this;
    }

    /**
     * @return el nombre del tag
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Actualiza el nombre del tag
     *
     * @param nombre el nuevo nombre del tag
     * @return
     */
    public Tag setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    /**
     * Añade una instruccion de procesamiento al tag
     *
     * @param ip Instruccion de procesamiento
     * @return
     * @see InstruccionDeProcesamiento
     */
    public Tag addInstruccionDeProcesamiento(InstruccionDeProcesamiento ip) {
        instruccionesDeProcesamiento.add(ip);
        return this;
    }

    /**
     * Añade una instruccion de procesamiento al tag
     *
     * @param target El nombre de la instrucción de procesamiento o target
     * @param data los datos de dicha instrucción
     * @deprecated Utilice el método {@link #addInstruccionDeProcesamiento(org.jespxml.modelo.InstruccionDeProcesamiento)
     * }
     * @see InstruccionDeProcesamiento
     */
    public void addInstruccionDeProcesamiento(String target, String data) {
        instruccionesDeProcesamiento.add(new InstruccionDeProcesamiento(target, data));
    }

    /**
     *
     * @param atributo el atributo guia para buscar el tag
     * @return la primera ocurrencia que encuentre en tags hijos
     */
    public Tag getTagHijoByAtributo(Atributo atributo) throws TagHijoNotFoundException {
        return getTagHijoByAtributo(atributo, Tag.Cantidad.PRIMERA_OCURRENCIA).get(0);
    }

    public List<Tag> getTagHijoByAtributo(Atributo atributo, Tag.Cantidad cantidad) throws TagHijoNotFoundException {
        List<Tag> tags = new CopyOnWriteArrayList();

        for (Tag t : this.getTagsHijos()) {
            for (Atributo atr : t.getAtributos()) {
                if (atributo.equals(atr)) {
                    tags.add(t);
                    if (cantidad == Tag.Cantidad.PRIMERA_OCURRENCIA) {
                        return tags;
                    }
                }
            }
        }

        if (tags.isEmpty()) {
            throw new TagHijoNotFoundException("No se encuentran tags que coincidan con el atributo: " + atributo);
        }

        return tags;
    }

    public void print() {
        print(this);
    }

    private void print(Tag tag) {
        System.out.println(tag + " ");
        for (InstruccionDeProcesamiento ip : tag.getInstruccionesDeProcesamiento()) {
            System.out.println("<?" + ip.getTarget() + " " + ip.getData() + "?>");
        }
        for (Comentario c : tag.getComentarios()) {
            System.out.println("Comentario: <" + c + ">");
        }
        for (Atributo atr : tag.getAtributos()) {
            System.out.println(atr);
        }
        for (Tag t : tag.getTagsHijos()) {
            print(t);
        }
    }
}
