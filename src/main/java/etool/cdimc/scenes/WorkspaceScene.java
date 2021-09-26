package etool.cdimc.scenes;

import etool.cdimc.Constants;
import etool.cdimc.repository.RepositoryLoader;

import javax.swing.*;
import java.awt.*;

public class WorkspaceScene extends JPanel {
    JPanel repositoryLoader;
    JPanel repositoryViewer;

    public WorkspaceScene() {
        setBounds(200,0, 600, 600);
        setBackground(Constants.WORKSPACE_COLOR);
        setLayout(null);
    }

    public void addRepositoryLoader(RepositoryLoader repositoryLoader){
        repositoryLoader.setBackground(Constants.mixColors(Constants.MENU_COLOR, Constants.WORKSPACE_COLOR));
        repaint();
        removeAll();
        repositoryLoader.setBounds(0,0, 200,600);
        add(repositoryLoader);
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
