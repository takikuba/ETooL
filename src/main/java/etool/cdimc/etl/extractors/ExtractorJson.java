package etool.cdimc.etl.extractors;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import etool.cdimc.models.JsonModel;
import etool.cdimc.stream.DataExtractStream;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class ExtractorJson implements Extractor{
    @Override
    public DataExtractStream extract(File data) {
        logger.info("Run!");
        JsonModel model = new JsonModel(data);

        return new DataExtractStream(model.toString());
    }
}
