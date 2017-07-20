package cl.jespxml.modelo;

/**
 * Clase para construir una instrucción de procesamiento.
 *
 * @author Patricio Pérez Pinto
 */
public class InstruccionDeProcesamiento {

    private String target;
    private String data;

    /**
     * Constuir un objeto instrucción de procesamiento
     *
     * @param target es el nombre de la instrucción.
     * @param data el valor o datos de la instrucción.
     */
    public InstruccionDeProcesamiento(String target, String data) {
        this.target = target;
        this.data = data;
    }

    /**
     *
     * @return el valor o datos de dicha instrucción
     */
    public String getData() {
        return data;
    }

    /**
     * Actualiza los datos por uno nuevo
     *
     * @param data nuevos datos
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     *
     * @return el nombre o taget de la instrucción de procesamiento
     */
    public String getTarget() {
        return target;
    }

    /**
     * Actualiza el nombre o target de la instrucción de procesamiento
     *
     * @param target el nuevo nombre o target de la instrucción de procesamiento
     */
    public void setTarget(String target) {
        this.target = target;
    }

}
