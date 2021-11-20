package etool.cdimc.components;

import etool.cdimc.Constants;
import etool.cdimc.models.Table;
import etool.cdimc.repository.RepositoryManager;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class TableManager extends JPanel {

    private final JButton exportButton = new JButton("Export");
    private final JButton deleteButton = new JButton("Delete");
    private String tableName;

    public TableManager() {
        setLayout(null);
        setBorder(new CompoundBorder(new TitledBorder("Table Manager"), new EmptyBorder(8, 0, 0, 0)));
        setBounds(15, 250, 170, 100);
        setBackground(Constants.WORKSPACE_COLOR);

        addTableButtons();
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    private void addTableButtons() {
        exportButton.setBounds(30, 30, 100, 20);
        exportButton.setBackground(Constants.MENU_COLOR);
        exportButton.setEnabled(false);
        exportButton.addActionListener( e -> {

        });
        add(exportButton);

        deleteButton.setBounds(30, 60, 100, 20);
        deleteButton.setBackground(new Color(189, 101, 112));
        deleteButton.setEnabled(false);
        deleteButton.addActionListener( e -> RepositoryManager.deleteRepositoryTable());
        add(deleteButton);
    }

    public void setEnabledTableManager(boolean enable) {
        exportButton.setEnabled(enable);
        deleteButton.setEnabled(enable);
    }

}
