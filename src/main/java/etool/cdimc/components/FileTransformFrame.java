package etool.cdimc.components;

import etool.cdimc.Constants;
import etool.cdimc.etl.EtlActions;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class FileTransformFrame extends JFrame {

    private final EtlActions etlActions;
    private final Set<JCheckBox> loadingColumns = new HashSet<>();
    private final JButton loadButton = new JButton("Load");
    private final Logger logger = Constants.logger();
    private boolean valid = false;

    public FileTransformFrame(EtlActions etlActions) {
        this.etlActions = etlActions;
        this.etlActions.setFile(getFile());

        if(valid){
            setTitle("FileTransform");
            setSize(200, 200);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            setLayout(null);
            setBackground(Constants.WORKSPACE_COLOR);
            setResizable(false);
            setVisible(true);
            startEtl();
            addPanel();
        }
    }

    private File getFile() {
        JFileChooser chooser = new JFileChooser("src/test/resources/testFiles");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Repo files", "json", "xml", "txt", "csv");
        chooser.setFileFilter(filter);
        int retval = chooser.showOpenDialog(null);
        if (retval == JFileChooser.APPROVE_OPTION) {
            logger.info("You chose to open this file: " +
                    chooser.getSelectedFile().getName());
            valid = true;
            return chooser.getSelectedFile();
        }
        valid = false;
        return null;
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
