package etool.cdimc.etl;

import etool.cdimc.Constants;
import etool.cdimc.db.DbFile;
import etool.cdimc.etl.extractors.*;
import etool.cdimc.etl.transformers.*;
import etool.cdimc.models.Table;
import etool.cdimc.repository.Repository;
import etool.cdimc.repository.RepositoryManager;
import etool.cdimc.repository.Vendor;
import etool.cdimc.stream.DataExtractStream;
import etool.cdimc.stream.DataTransformStream;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EtlActions {
    private final Logger logger = Logger.getLogger("EtlActions");
    private Repository repository;
    private File file;
    private Table table;
    private boolean valid = false;
    private DataExtractStream dataExtractStream = new DataExtractStream();
    private DataTransformStream dataTransformStream = new DataTransformStream();

    public EtlActions(Repository repository) {
        this.repository = repository;
        this.file = getFile();
    }

    public EtlActions(){}

    public boolean isValid() {
        return valid;
    }

    protected File getFile() {
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

    public void extract() throws IOException {
        logger.log(Level.INFO, "extract");

        Vendor vendor = Vendor.valueOf(FilenameUtils.getExtension(file.getName()).toUpperCase());
        Extractor extractor;

        switch (vendor) {
            case XML -> {
                extractor = new ExtractorXml();
                dataExtractStream = extractor.extract(file);
            }
            case CSV -> {
                extractor = new ExtractorCsv();
                dataExtractStream = extractor.extract(file);
            }
            case TXT -> {
                extractor = new ExtractorTxt();
                dataExtractStream = extractor.extract(file);
            }
            case JSON -> {
                extractor = new ExtractorJson();
                dataExtractStream = extractor.extract(file);
            }
            case MYSQL -> {
                extractor = new ExtractorMysql();
                dataExtractStream = extractor.extract(file);
            }
        }

        table = new Table(FilenameUtils.getBaseName(file.getName()), getColumns(), repository);
        DbFile.cleanRepository(repository);
    }

    public Set<String> getColumns() {
        return getColumns(dataExtractStream);
    }
    public Set<String> getColumns(DataExtractStream data) {

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

    public void filter(Set<String> selectedColumns) {
        table.setColumns(selectedColumns);
        dataExtractStream.filter(selectedColumns);
        transform();
        load();
    }
    private void transform() {
        logger.log(Level.INFO, "transform");
        Transformer transformer;

        switch (repository.getVendor()) {
            case XML -> {
                transformer = new TransformerXml();
                dataTransformStream = transformer.transform(dataExtractStream);
            }
            case CSV -> {
                transformer = new TransformerCsv();
                dataTransformStream = transformer.transform(dataExtractStream);
            }
            case TXT -> {
                transformer = new TransformerTxt();
                dataTransformStream = transformer.transform(dataExtractStream);
            }
            case JSON -> {
                transformer = new TransformerJson();
                dataTransformStream = transformer.transform(dataExtractStream);
            }
            case MYSQL -> {
                transformer = new TransformerMysql();
                dataTransformStream = transformer.transform(dataExtractStream);
            }
        }
    }

    public void load() {
        logger.log(Level.INFO, "load");
        File file = new File(Constants.REPOSITORIES_PATH + repository.getLocation());

        for(File f: Objects.requireNonNull(file.listFiles())){
            if(f.getName().equals(table.getLocation())){
                logger.warning("Table with this name already exist!");
                table = table.changeName();
            }
        }

        RepositoryManager.registerRepositoryTable(repository, table, dataTransformStream);
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }
}