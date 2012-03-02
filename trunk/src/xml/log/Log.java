/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.log;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author Patricio Pérez Pinto
 */
public class Log {

    public static final Logger logger = Logger.getLogger("log");
    private static FileHandler fh;

    public static void newLog(File archivoLog) throws IOException {
        
        fh = new FileHandler(archivoLog.getAbsolutePath(), true);
        logger.addHandler(fh);
        logger.setLevel(Level.ALL);
        
        fh.setFormatter(new SimpleFormatter()); 
    }
}
