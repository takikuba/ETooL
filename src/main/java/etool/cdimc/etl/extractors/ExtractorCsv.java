package etool.cdimc.etl.extractors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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

public class ExtractorCsv implements Extractor{
    @Override
    public DataExtractStream extract(File data) {
        logger.info("Run!");

        try {
            InputStream in = new FileInputStream(data);
            CsvModel csv = new CsvModel(in);
            List<String> fieldNames = null;
            if (csv.hasNext()) fieldNames = new ArrayList<>(csv.next());
            List<HashMap<String, String>> list = new ArrayList<>();
            while (csv.hasNext()) {
                List <String> x = csv.next();
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
            return new DataExtractStream(output);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
