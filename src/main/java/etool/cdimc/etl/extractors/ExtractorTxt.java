package etool.cdimc.etl.extractors;

import etool.cdimc.stream.DataExtractStream;

import java.io.File;

public class ExtractorTxt implements Extractor {
    @Override
    public DataExtractStream extract(File data) {
        logger.info("Run!");

        return null;
    }
}
