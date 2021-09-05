package main.java.etl;

import java.util.logging.Level;
import java.util.logging.Logger;

public class EtlActions {
    private final Logger logger = Logger.getLogger("EtlActions");

    public void connectToDb(){
        logger.log(Level.INFO, "connectToDb");
    }

    public void extract() {
        logger.log(Level.INFO, "extract");
    }

    public void transform() {
        logger.log(Level.INFO, "transform");
    }

    public void load() {
        logger.log(Level.INFO, "load");
    }

}
