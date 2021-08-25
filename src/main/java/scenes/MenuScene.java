package main.java.scenes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;

public class MenuScene extends JPanel {

    public MenuScene() {
        setBounds(0, 0, 800, 600);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(new GridBagLayout());
        setBackground(Color.lightGray);

        JLabel label = new JLabel("Hello World!");
        label.setFont(new Font("Serif", Font.PLAIN, 40));
        label.setBounds(200, 200, 300, 100);

        SceneManager.getScene().getContentPane().add(label);
        SceneManager.getScene().repaint();
        SceneManager.getScene().setVisible(true);
        SceneManager.getScene().revalidate();
    }
}
