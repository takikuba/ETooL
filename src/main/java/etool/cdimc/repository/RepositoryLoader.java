package etool.cdimc.repository;

import etool.cdimc.Constants;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.io.File;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;


public class RepositoryLoader extends JPanel {
    private final Logger logger = Constants.logger();
    private final Repository repository;

    public RepositoryLoader(Repository repository){
        this.repository = repository;
        setBorder(new CompoundBorder(new TitledBorder("Repository: " + repository.getName()), new EmptyBorder(8, 0, 0, 0)));
        loadTables();
    }

    private void loadTables() {
        File file = new File(Constants.REPOSITORIES_PATH + repository.getLocation() + "/tables.txt");
        logger.info("Find: " + (file.getParentFile().listFiles().length - 1) + " tables.");

    }

    private void loadData() {

    }

}
