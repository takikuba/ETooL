package main.java.scenes;

import main.java.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Settings extends JFrame {
    private final Logger logger = Constants.logger();

    Settings() {
        logger.log(Level.INFO, "Settings");
        setTitle("Settings");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setBackground(Constants.MENU_COLOR);
        setSize(400, 300);
        setVisible(true);
        setLocationRelativeTo(this.getParent());

        addMenuBar();
    }

    public void addMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu colorItem = new JMenu("Colors");
        colorItem.addItemListener(e -> chooseColors());
        JMenu fontItem = new JMenu("Font");
        fontItem.addItemListener(e -> chooseFont());

        menuBar.add(colorItem);
        menuBar.add(fontItem);

        this.setJMenuBar(menuBar);
        repaintFrame();
    }

    private void chooseColors() {
        logger.log(Level.INFO, "Color choose setting");

        JPanel panelColors = new JPanel();
        panelColors.setLayout(null);
        panelColors.setBounds(0, 0, 400, 300);

        JButton menuColor = new JButton("MenuColor");
        menuColor.setBounds(100, 100, 120, 20);
        menuColor.addActionListener( e -> {
            Color color = JColorChooser.showDialog(panelColors,"Select a menu color", Constants.MENU_COLOR);
            SceneManager.setMenuColor(color);
        });
        JButton workspaceColor = new JButton("WorkspaceColor");
        workspaceColor.setBounds(100, 150, 120, 20);
        workspaceColor.addActionListener( e -> {
            Color color = JColorChooser.showDialog(panelColors,"Select a workspace color", Constants.MENU_COLOR);
            SceneManager.setWorkspaceColor(color);
        });

        panelColors.add(menuColor);
        panelColors.add(workspaceColor);

        this.getContentPane().add(panelColors);
        repaintFrame();
    }

    private void chooseFont() {
        logger.log(Level.INFO, "Font choose setting");
    }

    public void repaintFrame() {
        this.revalidate();
        this.repaint();
    }

}
