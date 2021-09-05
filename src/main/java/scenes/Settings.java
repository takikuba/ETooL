package main.java.scenes;

import javax.swing.*;

public class Settings extends JFrame {

    Settings() {
        setTitle("Settings");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setSize(400, 300);
        setVisible(true);

        addMenuBar();
    }

    public void addMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu colorItem = new JMenu("Colors");
        JMenu fontItem = new JMenu("Font");


        menuBar.add(colorItem);
        menuBar.add(fontItem);

        this.setJMenuBar(menuBar);
        repaintFrame();
    }

    public void repaintFrame() {
        this.revalidate();
        this.repaint();
    }

}
