package etool.cdimc.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import etool.cdimc.Constants;
import etool.cdimc.components.DbTransformFrame;
import etool.cdimc.components.FileTransformFrame;
import etool.cdimc.etl.EtlActions;
import etool.cdimc.models.ColumnModel;
import etool.cdimc.scenes.SceneManager;
import etool.cdimc.stream.DataExtractStream;
import etool.cdimc.tables.TableViewer;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.io.*;
import java.util.*;
import java.util.logging.Logger;


public class RepositoryLoader extends JPanel {
    private final Logger logger = Constants.logger();
    private final Repository repository;
    private final JButton getFileButton = new JButton("Load data from file");
    private final JButton getDbButton = new JButton("Load data from db");
    private TableViewer tableViewer;

    public RepositoryLoader(Repository repository){
        this.repository = repository;
        setBorder(new CompoundBorder(new TitledBorder("Repository: " + repository.getName()), new EmptyBorder(8, 0, 0, 0)));
        try {
            loadTables();
//            loadAll(true);
            logger.info("Tables read successfully.");
        } catch (FileNotFoundException e) {
            logger.warning("Error when read tables!");
            e.printStackTrace();
        }
    }

    // Only for test
    private void loadAll(boolean forTest) throws FileNotFoundException{
        if(!forTest) return;
        Constants.timerStart();
        File file = new File(Constants.REPOSITORIES_PATH + repository.getLocation() + "/tables.etl");
        logger.info("Find: " + (Objects.requireNonNull(file.getParentFile().listFiles()).length - 1) + " tables.");
        File[] tablesLocation = file.getParentFile().listFiles();
        ArrayList<String> loc = new ArrayList<>();
        for(File f: tablesLocation) {
            if(!f.getName().equals("tables.etl")){
                loc.add(f.getName());
            }
        }

        int colXPosition = 50;
        int tabXPosition = 10;
        int i = 1;
        JPanel pane = new JPanel();
        pane.setBackground(Constants.MENU_COLOR);
        pane.setBounds(15, 20, 170, 465);
        Scanner sc = new Scanner(file);
        sc.useDelimiter(";");
        String element;
        while (sc.hasNext()){
            element = sc.next();
            if(element.startsWith("column")){
                element = "   " + element.split("\\.")[1];
                pane.add(getButton(element, i++, colXPosition, false));
            } else {
                element = element.split("\\.")[0];
                if(!element.isBlank()){
                    pane.add(getButton(element, i++, tabXPosition, true));
                }
            }
        }

        for(String path: loc) {
            StringBuilder contentBuilder = new StringBuilder();
            boolean header = true;
            String[] headers = new String[0];
            List<String> headersS = new ArrayList<>();
            String[][] data = new String[0][];
            List<String[]> dataS = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(Constants.REPOSITORIES_PATH + repository.getLocation() + '\\' + path)))
            {
                String sCurrentLine;
                while ((sCurrentLine = br.readLine()) != null) {
                    if(header) {
                        headers = sCurrentLine.split("/$");
                        header = false;
                    } else {
                        dataS.add(sCurrentLine.split("/$"));
                    }

                    contentBuilder.append(sCurrentLine).append("\n");
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        Constants.timerStop();
    }

    private void loadTables() throws FileNotFoundException {
        File file = new File(Constants.REPOSITORIES_PATH + repository.getLocation() + "/tables.etl");
        logger.info("Find: " + (Objects.requireNonNull(file.getParentFile().listFiles()).length - 1) + " tables.");
        Scanner sc = new Scanner(file);
        sc.useDelimiter(";");
        int colXPosition = 50;
        int tabXPosition = 10;
        int i = 1;
        JPanel pane = new JPanel();
        pane.setBackground(Constants.MENU_COLOR);
        pane.setBounds(15, 20, 170, 465);
        addLoadButtons();
        String element;
        while (sc.hasNext()){
            element = sc.next();
            if(element.startsWith("column")){
                element = "   " + element.split("\\.")[1];
                pane.add(getButton(element, i++, colXPosition, false));
            } else {
                element = element.split("\\.")[0];
                if(!element.isBlank()){
                    element = element.trim();
                    pane.add(getButton(element, i++, tabXPosition, true));
                }
            }
        }
        sc.close();
        add(pane);
        revalidate();
        repaint();
    }

    private JButton getButton(String name, int i, int xPosition, boolean enabled) {
        JButton b = new JButton(name);
        b.setBounds(xPosition, 25 * i, 100, 20);
        b.setBackground(Constants.WORKSPACE_COLOR);
        if(enabled){
            b.addActionListener( e -> {
//                Constants.timerStart();
                tableViewer = new TableViewer(repository, name);
                SceneManager.addTableViewer(tableViewer);
//                Constants.timerStop();
            });
        }
        b.setEnabled(enabled);
        return b;
    }

    private void addLoadButtons() {
        getFileButton.setBounds(25, 500, 150, 25);
        getFileButton.addActionListener(e -> new FileTransformFrame(new EtlActions(repository)));
        getDbButton.setBounds(25, 530, 150, 25);
        getDbButton.addActionListener(e -> new DbTransformFrame(new EtlActions(repository)));

        add(getFileButton);
        add(getDbButton);
    }

}
