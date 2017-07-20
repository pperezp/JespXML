package cl.jespxml.pruebas;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Conf {

    public String nombre;
    public String valor;

    public Conf() {
    }

    public Conf(String nombre, String valor) {
        this.nombre = nombre;
        this.valor = valor;
    }

}
