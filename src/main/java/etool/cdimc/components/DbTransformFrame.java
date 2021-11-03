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

    private EtlActions etlActions;
    private DbConnectionFrame connectionFrame;
    private final Set<JCheckBox> loadingColumns = new HashSet<>();
    private final JButton loadButton = new JButton("Load");

    public DbTransformFrame(EtlActions etlActions){
        this.etlActions = etlActions;
        this.connectionFrame = new DbConnectionFrame(this);
    }

    public void setFile(Parser parser) {
        setTitle("FileTransform");
        setSize(200, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setLayout(null);
        setBackground(Constants.WORKSPACE_COLOR);
        setResizable(false);
        setVisible(true);
        try {
            extractFromDB(parser);
            File file = DbFile.getDbFile(parser.getOutput(), etlActions.getRepository());
            this.etlActions.setFile(file);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        startEtl();
//        addPanel();
    }

    private void extractFromDB(Parser parser) {
        JPanel panel = new JPanel();
        panel.setBounds( 0, 0, 184, 161);
        panel.setBackground(Constants.MENU_COLOR);
        panel.setBorder(new CompoundBorder(new TitledBorder("Choose schema:"), new EmptyBorder(8, 0, 0, 0)));
        for(String schema: parser.getSchemas()){
            JButton sB = new JButton(schema);
            sB.setBackground(Constants.WORKSPACE_COLOR);
            sB.addActionListener( e -> {
                JPanel panel2 = new JPanel();
                panel2.setBounds( 0, 0, 184, 161);
                panel2.setBackground(Constants.MENU_COLOR);
                panel2.setBorder(new CompoundBorder(new TitledBorder("Choose table: " + sB.getText()), new EmptyBorder(8, 0, 0, 0)));
                for(String table: parser.getTables(schema)) {
                    JButton st = new JButton(table);
                    st.setBackground(Constants.WORKSPACE_COLOR);
                    st.addActionListener( f -> {
                        panel2.removeAll();
                        panel2.setBorder(new CompoundBorder(new TitledBorder("Columns in table: " + table), new EmptyBorder(8, 0, 0, 0)));
                        for(String column: parser.getColumns(table)){
                            System.out.println(column);
                            JButton sc = new JButton(column);
                            sc.setBackground(Constants.WORKSPACE_COLOR);
                            sc.addActionListener( c -> {

                            });
                            panel2.add(sc);
                            repaint();
                            revalidate();
                            }
                    });
                    panel2.add(st);
                }
                getContentPane().remove(panel);
                getContentPane().add(panel2);
                repaint();
                revalidate();
            });
            panel.add(sB);
        }
        this.getContentPane().add(panel);
    }

    private void startEtl() {
        try {
            etlActions.extract();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Constants.MENU_COLOR);
        panel.setLayout(null);
        panel.setBounds(0, 0, 200, 200);

        loadButton.setBounds(30, 135, 120, 20);
        loadButton.setBackground(Constants.WORKSPACE_COLOR);
        loadButton.addActionListener( e -> {
            etlActions.filter(getSelectedColumns());
            this.dispose();
        });

        addColumnChecker(panel);
        panel.add(loadButton);
        add(panel);
    }

    private Set<String> getSelectedColumns() {
        Set<String> retval = new HashSet<>();
        for(JCheckBox box: loadingColumns) {
            if(box.isSelected()){
                retval.add(box.getText());
            }
        }
        if(retval.isEmpty()){
            new FileTransformFrame(etlActions);
        }
        return retval;
    }

    private void addColumnChecker(JPanel parent) {
        JPanel panel = new JPanel();
        panel.setBorder(new CompoundBorder(new TitledBorder("Extracted columns: "), new EmptyBorder(8, 0, 0, 0)));
        panel.setBackground(Constants.WORKSPACE_COLOR);
        panel.setBounds(10, 7, 165, 125);

        Set<String> extractedColumns = etlActions.getColumns();
        for(String col: extractedColumns) {
            JCheckBox cb = new JCheckBox(col);
            cb.setBackground(Constants.WORKSPACE_COLOR);
            loadingColumns.add(cb);
            panel.add(cb);
        }
        parent.add(panel);
    }

}
