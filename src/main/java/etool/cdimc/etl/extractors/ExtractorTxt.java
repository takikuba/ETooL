package etool.cdimc.etl.extractors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import etool.cdimc.models.CsvModel;
import etool.cdimc.models.TxtModel;
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

public class ExtractorTxt implements Extractor {
    @Override
    public DataExtractStream extract(File data) {
        logger.info("Run!");

        try {
            InputStream in = new FileInputStream(data);
            TxtModel txt = new TxtModel(true, ';', in );
            List<String> fieldNames = null;
            if (txt.hasNext()) fieldNames = new ArrayList<>(txt.next());
            List<HashMap<String, String>> list = new ArrayList<>();
            while (txt.hasNext()) {
                List <String> x = txt.next();
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
