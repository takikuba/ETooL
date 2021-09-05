package main.java.etl;

import main.java.scenes.SceneManager;
import main.java.scenes.WorkspaceScene;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EtlActions extends JPanel {
    private final Logger logger = Logger.getLogger("EtlActions");

    public void connectToDb(){
        logger.log(Level.INFO, "connectToDb");
        setBackground(Color.BLUE);
        SceneManager.repaint();
    }

    public void extract() {

    }

    public void transform() {

    }

    public void load() {

    }

}
