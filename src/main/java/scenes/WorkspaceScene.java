package main.java.scenes;

import javax.swing.*;
import java.awt.*;

public class WorkspaceScene extends JPanel {

    public WorkspaceScene() {
        setBounds(200,0, 600, 600);
        setBackground(Color.GRAY);
    }

    public void setComponent(JComponent component) {
        this.add(component);

    }

    public void connectToDb() {

    }

    public void extract() {

    }

    public void transform() {
    }

    public void load() {

    }
}
