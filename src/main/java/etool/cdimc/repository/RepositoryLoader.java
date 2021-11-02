package etool.cdimc.repository;

import etool.cdimc.Constants;
import etool.cdimc.components.FileTransformFrame;
import etool.cdimc.etl.EtlActions;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Logger;


public class RepositoryLoader extends JPanel {
    private final Logger logger = Constants.logger();
    private final Repository repository;
    private final JButton getFileButton = new JButton("Load data from file");
    private final JButton getDbButton = new JButton("Load data from db");

    public RepositoryLoader(Repository repository){
        this.repository = repository;
        setBorder(new CompoundBorder(new TitledBorder("Repository: " + repository.getName()), new EmptyBorder(8, 0, 0, 0)));
        try {
            loadTables();
            logger.info("Tables read successfully.");
        } catch (FileNotFoundException e) {
            logger.warning("Error when read tables!");
            e.printStackTrace();
        }
    }

    private void loadTables() throws FileNotFoundException {
        File file = new File(Constants.REPOSITORIES_PATH + repository.getLocation() + "/tables.etl");
        logger.info("Find: " + (file.getParentFile().listFiles().length - 1) + " tables.");
        Scanner sc = new Scanner(file);
        sc.useDelimiter(";");
        JTextArea pane = new JTextArea();
        pane.setBackground(Constants.MENU_COLOR);
        pane.setBounds(15, 20, 170, 465);
        addLoadButtons();
        String element;
        while (sc.hasNext()){
            element = sc.next();
            if(element.startsWith("column")){
                element = "   " + element.split("\\.")[1];
            } else {
                element = element.split("\\.")[0];
            }
            pane.setText(pane.getText() + '\n' + element);
        }
        sc.close();
        add(pane);
    }

    private void addLoadButtons() {
        getFileButton.setBounds(25, 500, 150, 25);
        getFileButton.addActionListener(e -> new FileTransformFrame(new EtlActions(repository)));
        getDbButton.setBounds(25, 530, 150, 25);

        add(getFileButton);
        add(getDbButton);
    }

    private void loadData() {

    }

}
