package etool.cdimc.etl;

import etool.cdimc.Constants;
import etool.cdimc.etl.extractors.*;
import etool.cdimc.etl.model.Table;
import etool.cdimc.etl.transformers.*;
import etool.cdimc.repository.Repository;
import etool.cdimc.repository.RepositoryManager;
import etool.cdimc.repository.Vendor;
import etool.cdimc.stream.DataExtractStream;
import etool.cdimc.stream.DataTransformStream;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EtlActions {

    public static void main(String[] args) {
        EtlActions etlActions = new EtlActions();
        JFileChooser chooser = new JFileChooser("src/test/resources/testFiles");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Repo files", "json", "xml", "txt", "csv");
        chooser.setFileFilter(filter);
        int retval = chooser.showOpenDialog(null);
        if(retval == JFileChooser.APPROVE_OPTION) {
            System.out.println("You chose to open this file: " +
                    chooser.getSelectedFile().getName());
            File file = chooser.getSelectedFile();
            Repository repository = new Repository("Repo2", Vendor.CSV, "Repo2_CSV");

            Vendor inputVendor = Vendor.valueOf((FilenameUtils.getExtension(file.getName())).toUpperCase());

            etlActions.extract(inputVendor, file, repository);
        }
    }

    private final Logger logger = Logger.getLogger("EtlActions");

    public void connectToDb(){
        logger.log(Level.INFO, "connectToDb");
    }

    public void extract(Vendor vendor, File data, Repository repository) {
        logger.log(Level.INFO, "extract");

        DataExtractStream output = new DataExtractStream();
        Extractor extractor;

        switch (vendor) {
            case XML -> {
                extractor = new ExtractorXml();
                output = extractor.extract(data);
            }
            case CSV -> {
                extractor = new ExtractorCsv();
                output = extractor.extract(data);
            }
            case TXT -> {
                extractor = new ExtractorTxt();
                output = extractor.extract(data);
            }
            case JSON -> {
                extractor = new ExtractorJson();
                output = extractor.extract(data);
            }
            case MYSQL -> {
                extractor = new ExtractorMysql();
                output = extractor.extract(data);
            }
        }

        transform(repository, output);

    }

    public void transform(Repository repository, DataExtractStream data) {
        logger.log(Level.INFO, "transform");
        DataTransformStream output = new DataTransformStream();
        Transformer transformer;

        switch (repository.getVendor()) {
            case XML -> {
                transformer = new TransformerXml();
                output = transformer.transform(data);
            }
            case CSV -> {
                transformer = new TransformerCsv();
                output = transformer.transform(data);
            }
            case TXT -> {
                transformer = new TransformerTxt();
                output = transformer.transform(data);
            }
            case JSON -> {
                transformer = new TransformerJson();
                output = transformer.transform(data);
            }
            case MYSQL -> {
                transformer = new TransformerMysql();
                output = transformer.transform(data);
            }
        }

        load(repository, output);
    }

    public void load(Repository repository, DataTransformStream output) {
        logger.log(Level.INFO, "load");
        File file = new File(Constants.REPOSITORIES_PATH + repository.getLocation());

        Set<String> rows = Set.of("mark", "model");
        Table table = new Table("cars.csv", rows);
        RepositoryManager.registerRepositoryTable(repository, table);

    }

}
