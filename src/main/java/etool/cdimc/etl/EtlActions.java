package etool.cdimc.etl;

import etool.cdimc.Constants;
import etool.cdimc.db.DbFile;
import etool.cdimc.etl.extractors.*;
import etool.cdimc.etl.transformers.*;
import etool.cdimc.models.Table;
import etool.cdimc.repository.Repository;
import etool.cdimc.repository.RepositoryManager;
import etool.cdimc.repository.Vendor;
import etool.cdimc.stream.DataColumnStream;
import etool.cdimc.stream.DataExtractStream;
import etool.cdimc.stream.DataTransformStream;
import etool.cdimc.stream.StreamTransformer;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
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
    private DataExtractStream dataExtractStream = new DataExtractStream();
    private DataColumnStream dcs;
    private Vendor vendor;

    public EtlActions(Repository repository) {
        this.repository = repository;
    }

    public EtlActions(){}

    public void setFile(File file) {
        this.file = file;
    }

    public void extract() throws IOException {
        logger.log(Level.INFO, "extract");

        String vendorString = FilenameUtils.getExtension(file.getName()).toUpperCase();
        if(vendorString.equals("CEF")) {
            vendorString = "MYSQL";
        }
        vendor = Vendor.valueOf(vendorString);

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
        if(!vendor.equals(Vendor.MYSQL))
            table = new Table(FilenameUtils.getBaseName(file.getName()), getColumns());
        DbFile.cleanRepository(repository);
    }

    public Set<String> getColumns() {
        if(dataExtractStream!=null) {
            return getColumns(dataExtractStream);
        }
        return getSqlColumns();
    }

    private Set<String> getSqlColumns() {
        try (BufferedReader br = new BufferedReader(new FileReader(file.getPath()))) {
            Set<String> retval = new HashSet<>();
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.contains("column.")) {
                    sCurrentLine = sCurrentLine.replace("column.", "");
                    retval.addAll(Arrays.asList(sCurrentLine.split("\\$")));
                }
            }
            return retval;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
        transform();
        if(!vendor.equals(Vendor.MYSQL)){
            dcs = dcs.getFilteredDataStream(selectedColumns);
        }
        load();
    }
    private void transform() {
        logger.log(Level.INFO, "transform");
        if(!vendor.equals(Vendor.MYSQL)){
            dcs = StreamTransformer.transformJsonToDataStream(dataExtractStream.toString());
            dcs.setName(repository.getName());
        }
    }

    public void load() {
        logger.log(Level.INFO, "load");

        if(!vendor.equals(Vendor.MYSQL)) {
            File file = new File(Constants.REPOSITORIES_PATH + repository.getLocation());

            for(File f: Objects.requireNonNull(file.listFiles())){
                if(f.getName().equals(table.getLocation())){
                    logger.warning("Table with this name already exist!");
                    table = table.changeName();
                }
            }
        }



        RepositoryManager.registerRepositoryTable(repository, table, dcs, vendor);
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Repository getRepository() {
        return repository;
    }
}