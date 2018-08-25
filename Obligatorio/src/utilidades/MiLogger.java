/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author leo
 */
public class MiLogger {
     
    
    public static void loggearError(Exception e){
        Logger logger = Logger.getLogger("miLogger");
        FileHandler fh;
        try {
            fh = new FileHandler("mylog.txt",true);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
            logger.setLevel(Level.ALL);
            logger.log(Level.ALL, "trouble", e);
            fh.close();
        } catch (IOException | SecurityException ex) {
            Logger.getLogger(MiLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void loggearError(String mensaje){
        Logger logger = Logger.getLogger("miLogger");
        FileHandler fh;
        try {
            fh = new FileHandler("mylog.txt",true);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
            logger.setLevel(Level.ALL);
            logger.log(Level.ALL, "trouble", mensaje);
            fh.close();
        } catch (IOException | SecurityException ex) {
            Logger.getLogger(MiLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
