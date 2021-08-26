package main.java.scenes;

import main.java.Constants;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MenuScene extends JPanel {
    private final Image logo = new ImageIcon(Constants.LOGO_PATH).getImage();

    public MenuScene() {
        setBounds(0, 0, 200, 600);
        setLayout(null);
        setBackground(Color.lightGray);

        add(addMenuButtons());
    }

    private JPanel addMenuButtons() {
        JPanel menuButtons = new JPanel();
        menuButtons.setBackground(Color.lightGray);
        menuButtons.setBounds(
                50, 200, 100, 400);
        menuButtons.setLayout(new BoxLayout(menuButtons, BoxLayout.Y_AXIS));

        addButton("Extract", menuButtons);
        addButton("Transform", menuButtons);
        addButton("Load", menuButtons);
        addButton("Settings", menuButtons);
        addButton("Help", menuButtons);

        return menuButtons;
    }

    private void addButton(String string, Container container){
        JButton jButton = new JButton(string);
        jButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        jButton.setBackground(Color.GRAY);

        container.add(jButton);
        container.add(Box.createVerticalGlue());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(logo, -15, -5, null);
    }
}
