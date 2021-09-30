package etool.cdimc.scenes;

import etool.cdimc.Constants;
import etool.cdimc.repository.RepositoryManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuScene extends JPanel {
    private final Image logo = new ImageIcon(Constants.LOGO_PATH).getImage();
    private final Logger logger = Constants.logger();
    private JPanel menuButtons;

    public MenuScene() {
        setBounds(0, 0, 200, 600);
        setLayout(null);
        setBackground(Color.lightGray);

        add(addMenuButtons());
    }

    public void setMenuColor(Color color) {
        setBackground(color);
        menuButtons.setBackground(color);
    }

    public void setWorkspaceColor(Color color) {
        for(Component button: menuButtons.getComponents()){
            button.setBackground(color);
        }
    }

    private JPanel addMenuButtons() {
        menuButtons = new JPanel();
        menuButtons.setBackground(Constants.MENU_COLOR);
        menuButtons.setBounds(
                50, 100, 100, 400);
        menuButtons.setLayout(new BoxLayout(menuButtons, BoxLayout.Y_AXIS));

        chooseRepository(menuButtons);
//        addButton("ConnectToDb", menuButtons, action -> workspaceScene.connectToDb());
//        addButton("Extract", menuButtons, action -> workspaceScene.extract());
//        addButton("Transform", menuButtons, action -> workspaceScene.transform());
//        addButton("Load", menuButtons, action -> workspaceScene.load());
        addButton("Settings", menuButtons, action -> settings());
        addButton("Help", menuButtons, action -> help());

        logger.info("Buttons added properly!");

        return menuButtons;
    }

    private void chooseRepository(Container container) {
        RepositoryManager repositoryManager = new RepositoryManager();
        container.add(repositoryManager);
        container.add(Box.createVerticalGlue());
    }

    private void addButton(String text, Container container, ActionListener actionListener){
        JButton jButton = new JButton(text);
        jButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        jButton.setBackground(Constants.WORKSPACE_COLOR);
        jButton.addActionListener(actionListener);

        container.add(jButton);
        container.add(Box.createVerticalGlue());
    }

    private void settings() {
        new Settings();
    }

    private void help() {
        logger.log(Level.INFO, "Go to help");
        JOptionPane.showMessageDialog(null, "In case of any questions: contact us!" + '\n' +
                "Application directed by: Jakub Klimek");
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(logo, -15, -5, null);
    }

}
