package main.java.scenes;

import main.java.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

public class MenuScene extends JPanel {
    private final Image logo = new ImageIcon(Constants.LOGO_PATH).getImage();
    private final Logger logger = Constants.logger();
    private final WorkspaceScene workspaceScene = new WorkspaceScene();

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

        addButton("ConnectToDb", menuButtons, action -> workspaceScene.connectToDb());
        addButton("Extract", menuButtons, action -> workspaceScene.extract());
        addButton("Transform", menuButtons, action -> workspaceScene.transform());
        addButton("Load", menuButtons, action -> workspaceScene.load());
        addButton("Settings", menuButtons, action -> settings());
        addButton("Help", menuButtons, action -> help());

        logger.info("Buttons added properly!");

        return menuButtons;
    }

    private void addButton(String string, Container container, ActionListener actionListener){
        JButton jButton = new JButton(string);
        jButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        jButton.setBackground(Color.GRAY);
        jButton.addActionListener(actionListener);

        container.add(jButton);
        container.add(Box.createVerticalGlue());
    }

    private void settings() {
        new Settings();
    }

    private void help() {
        JOptionPane.showMessageDialog(this, "In case of any questions: contact us!" + '\n' +
                "Application directed by: Jakub Klimek");
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(logo, -15, -5, null);
    }

}
