package etool.cdimc.scenes;

import etool.cdimc.Constants;
import etool.cdimc.components.TableManager;
import etool.cdimc.repository.RepositoryManager;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuScene extends JPanel {
    private final Image logo = new ImageIcon(Constants.LOGO_PATH).getImage();
    private final Logger logger = Constants.logger();
    private JPanel menuButtons;
    private int yAlign = 420;

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
        menuButtons.setBounds(0, 80, 200, 500);
        menuButtons.setLayout(null);

        chooseRepository(menuButtons);
        addButton("Settings", menuButtons, action -> settings());
        addButton("Help", menuButtons, action -> help());

        addTableManager(menuButtons);

        logger.info("Buttons added properly!");

        return menuButtons;
    }

    private void addTableManager(JPanel parent) {
        TableManager tableManager = new TableManager();
        parent.add(tableManager);
        SceneManager.addTableManager(tableManager);
    }

    private void chooseRepository(Container container) {
        RepositoryManager repositoryManager = new RepositoryManager();
        container.add(repositoryManager);
    }

    private void addButton(String text, Container container, ActionListener actionListener){
        JButton jButton = new JButton(text);
        jButton.addActionListener(actionListener);
        jButton.setBackground(Constants.WORKSPACE_COLOR);
        jButton.setBounds(50, yAlign, 100, 20);
        incrementButtonAlign();

        container.add(jButton);
        container.add(Box.createVerticalGlue());
    }

    private void  incrementButtonAlign() {
        yAlign += 30;
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
