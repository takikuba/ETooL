package etool.cdimc.etl.extractors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import etool.cdimc.models.ColumnModel;
import etool.cdimc.models.CsvModel;
import etool.cdimc.stream.DataExtractStream;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ExtractorMysql implements Extractor{
    @Override
    public DataExtractStream extract(File data) {
        logger.info("Run!");

        try {
            InputStream in = new FileInputStream(data);
            ColumnModel columnsData = new ColumnModel(in);
            List<String> fieldNames = null;
            if (columnsData.hasNext())
                fieldNames = new ArrayList<>(columnsData.next());
            List<HashMap<String, String>> list = new ArrayList<>();
            while (columnsData.hasNext()) {
                List <String> x = columnsData.next();
                HashMap<String, String> obj = new LinkedHashMap<>();
                for (int i = 0; i < fieldNames.size(); i++) {
                    obj.put(fieldNames.get(i).replace("column.", ""), x.get(i));
                }
                list.add(obj);
            }
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            String output = mapper.writeValueAsString(list);

            output = "{ \"" + FilenameUtils.getBaseName(data.getName()) + "\":" + output + "}";
            System.out.println(output);
            return new DataExtractStream(output);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
