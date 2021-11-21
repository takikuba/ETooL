package etool.cdimc.etl.extractors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import etool.cdimc.db.DbFile;
import etool.cdimc.models.ColumnModel;
import etool.cdimc.models.CsvModel;
import etool.cdimc.stream.DataColumnStream;
import etool.cdimc.stream.DataExtractStream;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ExtractorMysql implements Extractor{
    @Override
    public DataExtractStream extract(File data) {
        logger.info("Run!");
        return null;
    }
}
