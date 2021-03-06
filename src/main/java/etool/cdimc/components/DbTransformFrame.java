package etool.cdimc.components;

import etool.cdimc.Constants;
import etool.cdimc.connectors.PostgreSQLConnector;
import etool.cdimc.db.DbFile;
import etool.cdimc.etl.EtlActions;
import etool.cdimc.parser.Parser;
import etool.cdimc.parser.PsqlParser;
import etool.cdimc.repository.Repository;
import etool.cdimc.repository.Vendor;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class DbTransformFrame extends JFrame {

    private final EtlActions etlActions;
    private final Set<JCheckBox> loadingColumns = new HashSet<>();
    private final JButton loadButton = new JButton("Load");
    private Parser parser;

    public DbTransformFrame(EtlActions etlActions){
        this.etlActions = etlActions;
        DbConnectionFrame connectionFrame = new DbConnectionFrame(this);
    }

    public void startTransform(Parser parser) {
        this.parser = parser;

        setTitle("DbTransform");
        setSize(200, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setLayout(null);
        setBackground(Constants.WORKSPACE_COLOR);
        setResizable(false);
        setVisible(true);

        extractFromDB(parser);
    }

    private synchronized void extractFromDB(Parser parser) {
        JPanel panel = new JPanel();
        panel.setBounds( 0, 0, 184, 161);
        panel.setBackground(Constants.MENU_COLOR);
        panel.setBorder(new CompoundBorder(new TitledBorder("Choose schema:"), new EmptyBorder(8, 0, 0, 0)));
        for(String schema: parser.getSchemas()){
            JButton sB = new JButton(schema);
            sB.setBackground(Constants.WORKSPACE_COLOR);
            sB.addActionListener( e -> {
                getContentPane().remove(panel);
                getContentPane().add(getTablePanel(parser, schema));
                repaint();
                revalidate();
            });
            panel.add(sB);
        }
        this.getContentPane().add(panel);
    }

    private JPanel getTablePanel(Parser parser, String schema) {
        JPanel panel2 = new JPanel();
        panel2.setBounds( 0, 0, 184, 161);
        panel2.setBackground(Constants.MENU_COLOR);
        panel2.setBorder(new CompoundBorder(new TitledBorder("Choose table: " + schema), new EmptyBorder(8, 0, 0, 0)));
        for(String table: parser.getTables(schema)) {
            addColumnSelector(table, panel2, parser);
        }
        return panel2;
    }

    private void addColumnSelector(String table, JPanel panel, Parser parser) {
        JButton st = new JButton(table);
        st.setBackground(Constants.WORKSPACE_COLOR);
        st.addActionListener( f -> {
            panel.removeAll();
            panel.setBorder(new CompoundBorder(new TitledBorder("Columns in table: " + table), new EmptyBorder(8, 0, 0, 0)));
            for(String column: parser.getColumns(table)){
                JCheckBox sc = new JCheckBox(column);
                sc.setBackground(Constants.WORKSPACE_COLOR);
                loadingColumns.add(sc);
                panel.add(sc);
                repaint();
                revalidate();
            }
            setUpLoadButton(panel);
        });
        panel.add(st);
    }

    private void setUpLoadButton(JPanel panel) {
        loadButton.setBounds(30, 135, 120, 20);
        loadButton.setBackground(Constants.WORKSPACE_COLOR);
        loadButton.addActionListener( k -> {
            etlActions.filter(getSelectedColumns());
            this.dispose();
        });
        panel.add(loadButton);
    }

    private void startEtl() {
        try {
            etlActions.extract();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Set<String> getSelectedColumns() {
        Set<String> retval = new HashSet<>();
        for(JCheckBox box: loadingColumns) {
            if(box.isSelected()){
                retval.add(box.getText());
            }
        }
        if(retval.isEmpty()){
            new DbTransformFrame(etlActions);
        }
        parser.getColumnValues(retval);
        try {
            DbFile.DbTableFile tableFile = DbFile.getDbFile(parser.getOutput(), etlActions.getRepository());
            this.etlActions.setTable(tableFile.getTable());
            this.etlActions.setFile(tableFile.getFile());
            startEtl();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return retval;
    }
}
