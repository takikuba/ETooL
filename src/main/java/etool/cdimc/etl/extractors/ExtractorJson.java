package etool.cdimc.etl.extractors;

import etool.cdimc.models.JsonModel;
import etool.cdimc.stream.DataExtractStream;

import java.io.File;

public class ExtractorJson implements Extractor{
    @Override
    public DataExtractStream extract(File data) {
        logger.info("Run!");
        JsonModel model = new JsonModel(data);

        return new DataExtractStream(model.toString());
    }
}
