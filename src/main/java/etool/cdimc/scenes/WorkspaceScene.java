package etool.cdimc.scenes;

import etool.cdimc.Constants;
import etool.cdimc.repository.RepositoryLoader;
import etool.cdimc.tables.TableViewer;

import javax.swing.*;
import java.awt.*;

public class WorkspaceScene extends JPanel {

    private TableViewer tableViewer;

    public WorkspaceScene() {
        setBounds(200,0, 600, 600);
        setBackground(Constants.WORKSPACE_COLOR);
        setLayout(null);
    }

    public void addRepositoryLoader(RepositoryLoader repositoryLoader) {
        repositoryLoader.setBackground(Constants.mixColors(Constants.MENU_COLOR, Constants.WORKSPACE_COLOR));
        repaint();
        removeAll();
        repositoryLoader.setBounds(0,0, 200,600);
        add(repositoryLoader);
    }

    public void addTableViewer(TableViewer tableViewer) {
        if(this.tableViewer != null) remove(this.tableViewer);
        this.tableViewer = tableViewer;
        tableViewer.setBackground(Constants.WORKSPACE_COLOR);
        tableViewer.setBounds(200, 0, 400, 600);
        add(tableViewer);
        repaint();
    }

    public void setComponent(JComponent component) {
        this.add(component);
    }

}
