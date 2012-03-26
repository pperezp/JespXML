/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.analizador.dom.modelo;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;

/**
 *
 * @author Administrador
 */
public class CellRender extends JLabel implements TreeCellRenderer{
    private ImageIcon icono_tag = null;
    private ImageIcon icono_comentario = null;
    private ImageIcon icono_atributo = null;
    private ImageIcon icono_texto = null;
    private Color colorFondo;
    
    public CellRender(ImageIcon icono_tag, ImageIcon icono_comentario, ImageIcon icono_atributo, ImageIcon icono_texto, Color colorFondo){
        this.icono_atributo = icono_atributo;
        this.icono_comentario = icono_comentario;
        this.icono_tag = icono_tag;
        this.icono_texto = icono_texto;
        this.colorFondo = colorFondo;
    }
    
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        this.setOpaque(true);
        this.setFont(new Font("Verdana", Font.PLAIN, 12));
        
        DefaultMutableTreeNode v = (DefaultMutableTreeNode)value;
        Object ob = v.getUserObject();
        if(ob instanceof Tag){
            this.setFont(new Font("Verdana", Font.BOLD, 12));
            Tag t = (Tag)ob;
            this.setText(t.getNombre());
//            this.setIcon("/xml/images/16abierto.png");
            if (this.icono_tag != null) this.setIcon(this.icono_tag);
        }else if(ob instanceof Comentario){
            this.setFont(new Font("Verdana", Font.ITALIC, 12));
            Comentario c = (Comentario)ob;
            this.setText("/* "+c.getComentario()+" */");
//            this.setIcon("/xml/images/16comentario.png");
            if (this.icono_comentario != null) this.setIcon(this.icono_comentario);
        }else if(ob instanceof Atributo){
            Atributo a = (Atributo)ob;
            this.setText(a.getNombre()+" = "+a.getValor());
//            this.setIcon("/xml/images/16atributo.png");
            if (this.icono_atributo != null) this.setIcon(this.icono_atributo);
        }else{
            this.setText(ob.toString());
//            this.setIcon("/xml/images/16texto.png");
            if (this.icono_texto != null) this.setIcon(this.icono_texto);
        }
        
        if(selected){
            this.setForeground(Color.white);
            this.setBackground(new Color(51, 153, 255));
        }else{
            this.setForeground(Color.black);
            this.setBackground(colorFondo);
        }
        
        return this;
    }
    
    /**
     * 
     * @param rutaPaquete
     * "/xml/images/16atributo.png"
     * @return 
     */
    public static ImageIcon crearIcono(String rutaPaquete){
        return new ImageIcon(CellRender.class.getResource(rutaPaquete));
    }
}
