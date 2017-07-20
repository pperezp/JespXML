package cl.jespxml.excepciones;

/**
 *
 * @author Patricio Pérez Pinto
 */
public class TagHijoNotFoundException extends Exception {

    /**
     * Creates a new instance of
     * <code>TagHijoNotFoundException</code> without detail message.
     */
    public TagHijoNotFoundException() {
    }

    /**
     * Constructs an instance of
     * <code>TagHijoNotFoundException</code> with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public TagHijoNotFoundException(String msg) {
        super(msg);
    }
}
