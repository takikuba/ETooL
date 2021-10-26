package etool.cdimc.etl;

import etool.cdimc.Constants;
import etool.cdimc.connectors.PostgreSQLConnector;
import etool.cdimc.etl.extractors.*;
import etool.cdimc.etl.transformers.*;
import etool.cdimc.models.Table;
import etool.cdimc.parser.PsqlParser;
import etool.cdimc.repository.Repository;
import etool.cdimc.repository.RepositoryManager;
import etool.cdimc.repository.Vendor;
import etool.cdimc.stream.DataExtractStream;
import etool.cdimc.stream.DataTransformStream;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EtlActions {
    private final Logger logger = Logger.getLogger("EtlActions");

    public static void main(String[] args) throws InterruptedException, IOException, SQLException, ClassNotFoundException {
        EtlActions etlActions = new EtlActions();
//        JFileChooser chooser = new JFileChooser("src/test/resources/testFiles");
//        FileNameExtensionFilter filter = new FileNameExtensionFilter("Repo files", "json", "xml", "txt", "csv");
//        chooser.setFileFilter(filter);
//        int retval = chooser.showOpenDialog(null);
//        if (retval == JFileChooser.APPROVE_OPTION) {
//            System.out.println("You chose to open this file: " +
//                    chooser.getSelectedFile().getName());
//            File file = chooser.getSelectedFile();
//    }

        Connection psql = PostgreSQLConnector.connect("jdbc:postgresql://localhost/etlTEST", "admin2", "admin");
        PsqlParser parser = new PsqlParser(psql);

//            File fileCsv = new File("src/test/resources/testFiles" + "/persons.csv");
            Repository repository = new Repository("Repo4", Vendor.JSON, "Repo4_JSON");
//
            Vendor inputVendor = Vendor.MYSQL;
//
//            etlActions.extract(inputVendor, fileCsv, repository);
    }

    public void connectToDb(){
        logger.log(Level.INFO, "connectToDb");
    }

    public void extract(Vendor vendor, File data, Repository repository) throws IOException {
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

        Table table = new Table(FilenameUtils.getBaseName(data.getName()), getColumns(output), repository);
        transform(repository, output, table);
    }

    Set<String> getColumns(DataExtractStream data) {

        String jsonArray = data.getData().toString().substring(data.getData().toString().indexOf('[')+1, data.getData().toString().indexOf(']'));
        String[] rows = jsonArray.split("},\\{");
        String[] columns = rows[0].split(",");

        Set<String> columnNames = new HashSet<>();
        for(String col: columns) {
            columnNames.add(col.substring(col.indexOf("\"")+1, col.indexOf("\":")));
        }

        logger.info("Extract following columns from source: " + columnNames);

        return columnNames;
    }

    private void transform(Repository repository, DataExtractStream data, Table table) {
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
                break;
            }
            case MYSQL -> {
                transformer = new TransformerMysql();
                output = transformer.transform(data);
            }
        }

        load(repository, output, table);
    }

    public void load(Repository repository, DataTransformStream data, Table table) {
        logger.log(Level.INFO, "load");
        File file = new File(Constants.REPOSITORIES_PATH + repository.getLocation());

        for(File f: Objects.requireNonNull(file.listFiles())){
            if(f.getName().equals(table.getLocation())){
                logger.warning("Table with this name already exist!");
                table = table.changeName();
            }
        }

        RepositoryManager.registerRepositoryTable(repository, table, data);

    }

}
